package br.com.copyimagem.core.controllers;

import br.com.copyimagem.core.dtos.CustomerResponseDTO;
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

import static br.com.copyimagem.core.domain.builders.CustomerResponseDTOBuilder.oneCustomerResponseDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CustomerControllerTest {

    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String NUMBER_1 = "1";
    public static final String CPF = "156.258.240-29";
    public static final String CPF_STRING = "cpf";
    public static final String ID_TYPEPARAM = "id";
    private CustomerResponseDTO customerResponseDTO;
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
        when(customerService.searchCliente(ID_TYPEPARAM, NUMBER_1)).thenReturn(
                customerResponseDTO);
        mockMvc.perform(get("/api/v1/customers/search-client")
                        .param("typeParam", ID_TYPEPARAM)
                        .param("valueParam", NUMBER_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Must return a exception when customer not found.")
    void mustReturnAExceptionWhenCustomerNotFound() throws Exception {
        when(customerService.searchCliente(ID_TYPEPARAM, NUMBER_1))
                .thenThrow(new NoSuchElementException("Customer not found"));
        mockMvc.perform(get("/api/v1/customers/search-client")
                        .param("typeParam", ID_TYPEPARAM)
                        .param("valueParam", NUMBER_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Must return a customer by CPF")
    void mustReturnACustomerByCPF() throws Exception {
        when(customerService.searchCliente(CPF_STRING, CPF)).thenReturn(
                customerResponseDTO);

        ResponseEntity<CustomerResponseDTO> responseEntity = customerController.searchCliente(CPF_STRING, CPF);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerResponseDTO, responseEntity.getBody());
        assertEquals(customerResponseDTO.getCpfOrCnpj(), responseEntity.getBody().getCpfOrCnpj());
    }

    @Test
    @DisplayName("Must return a exception when customer not found.")
    void mustReturnAExceptionWhenCustomerNotFoundByCPF() throws Exception {
        when(customerService.searchCliente(CPF_STRING, CPF))
                .thenThrow(new NoSuchElementException(CUSTOMER_NOT_FOUND));

        ResponseEntity<CustomerResponseDTO> responseEntity = customerController.searchCliente(CPF_STRING, CPF);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        RuntimeException exception = assertThrows(NoSuchElementException.class, () -> customerService.searchCliente(CPF_STRING, CPF));
        assertEquals(CUSTOMER_NOT_FOUND, exception.getMessage());

        verify(customerService, Mockito.times(2)).searchCliente(CPF_STRING, CPF);
    }

    private void start() {
        customerResponseDTO = oneCustomerResponseDTO().withCpfOrCnpj(CPF).now();
    }
}