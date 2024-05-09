package br.com.copyimagem.infra.controllers;

import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.dtos.UpdateCustomerDTO;
import br.com.copyimagem.core.exceptions.IllegalArgumentException;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.interfaces.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Objects;

import static br.com.copyimagem.core.domain.builders.CustomerResponseDTOBuilder.oneCustomerResponseDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.boot.context.annotation.Configurations.getClasses;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CustomerControllerTest {

    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String NUMBER_1 = "1";
    public static final String CPF = "156.258.240-29";
    public static final String CPF_PARAM = "cpf";
    public static final String ID_TYPE_PARAM = "id";
    private CustomerResponseDTO customerResponseDTO;
    private UpdateCustomerDTO updateCustomerDTO;
    @Mock
    private CustomerService customerService;
    private MockMvc mockMvc;
    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        start();
    }

    @Test
    @DisplayName("Must return a customer by id")
    void mustReturnACustomerById() throws Exception {
        when(customerService.searchCliente(ID_TYPE_PARAM, NUMBER_1)).thenReturn(
                customerResponseDTO);
        mockMvc.perform(get("/api/v1/customers/search-client")
                        .param("typeParam", ID_TYPE_PARAM)
                        .param("valueParam", NUMBER_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Must return a exception when customer not found.")
    void mustReturnAExceptionWhenCustomerNotFound(){
        when(customerService.searchCliente(ID_TYPE_PARAM, NUMBER_1))
                .thenThrow(new NoSuchElementException("Customer not found"));
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> customerService.searchCliente(ID_TYPE_PARAM, NUMBER_1));
        assertEquals(CUSTOMER_NOT_FOUND, exception.getMessage());

        verify(customerService, Mockito.times(1)).searchCliente(ID_TYPE_PARAM, NUMBER_1);
    }

    @Test
    @DisplayName("Must return a customer by CPF")
    void mustReturnACustomerByCPF(){
        when(customerService.searchCliente(CPF_PARAM, CPF)).thenReturn(
                customerResponseDTO);

        ResponseEntity<CustomerResponseDTO> responseEntity = customerController.searchCustomerByParams(CPF_PARAM, CPF);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerResponseDTO, responseEntity.getBody());
    }

    @Test
    @DisplayName("Must return a exception when customer not found.")
    void mustReturnAExceptionWhenCustomerNotFoundByCPF(){
        when(customerService.searchCliente(CPF_PARAM, CPF))
                .thenThrow(new NoSuchElementException(CUSTOMER_NOT_FOUND));
        RuntimeException exception = assertThrows(NoSuchElementException.class,
                () -> customerService.searchCliente(CPF_PARAM, CPF));
        assertEquals(CUSTOMER_NOT_FOUND, exception.getMessage());

        verify(customerService, Mockito.times(1)).searchCliente(CPF_PARAM, CPF);
    }

    @Test
    @DisplayName("Must return all customers by FinancialSituation")
    void mustReturnAllCustomersByFinancialSituation(){
        String situation = "PAGO";
        when(customerService.searchFinancialSituation(situation)).thenReturn(List.of(customerResponseDTO));
        ResponseEntity<List<CustomerResponseDTO>> responseEntity =
                customerController.searchFinancialSituation(situation);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CustomerResponseDTO.class, Objects.requireNonNull(responseEntity.getBody()).get(0).getClass());
        assertEquals(1, responseEntity.getBody().size());
    }
    @Test
    @DisplayName("Must return a exception when param invalid")
    void mustReturnAExceptionWhenParamInvalidByFinancialSituation(){
        String situation = "INVALID";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                                              () ->customerController.searchFinancialSituation(situation));
        assertEquals("The argument is not correct", exception.getMessage());
        verify(customerService, never()).searchFinancialSituation(situation);
    }

    @Test
    @DisplayName("Must return a ResponseEntity from UpdateCustomerDTO")
    void mustReturnAResponseEntityFromUpdateCustomerDTO(){

        String attribute = "cnpj";
        String value = "123.456.789-10";
        when(customerService.updateCustomerAttribute(attribute, value, 1L)).thenReturn(updateCustomerDTO);
        ResponseEntity<UpdateCustomerDTO> responseEntity =
                customerController.updateCustomerAttribute(attribute, value, 1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(UpdateCustomerDTO.class, responseEntity.getBody().getClass());
        assertEquals(customerResponseDTO.getId(), responseEntity.getBody().getId());
    }

    private void start() {
        customerResponseDTO = oneCustomerResponseDTO().withCpfOrCnpj(CPF).now();
        updateCustomerDTO = new UpdateCustomerDTO();
        updateCustomerDTO.setPrimaryEmail("ccarige@gmail.com");
        updateCustomerDTO.setId(1L);
        updateCustomerDTO.setCpfOrCnpj("14.124.420/0001-94");
    }
}