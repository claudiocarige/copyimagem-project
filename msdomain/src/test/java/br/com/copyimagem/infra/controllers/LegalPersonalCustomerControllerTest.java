package br.com.copyimagem.infra.controllers;

import br.com.copyimagem.core.domain.entities.LegalPersonalCustomer;
import br.com.copyimagem.core.dtos.LegalPersonalCustomerDTO;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.impl.ConvertObjectToObjectDTOService;
import br.com.copyimagem.core.usecases.interfaces.LegalPersonalCustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static br.com.copyimagem.core.domain.builders.AddressBuilder.oneAddress;
import static br.com.copyimagem.core.domain.builders.LegalPersonalCustomerBuilder.oneLegalPersonalCustomer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Log4j2
class LegalPersonalCustomerControllerTest {

    public static final long ID1L = 1L;
    public static final String CNPJ = "14.124.420/0001-94";
    private LegalPersonalCustomer customerPj;
    private LegalPersonalCustomerDTO customerPjDTO;
    @Mock
    private ConvertObjectToObjectDTOService convertObjectToObjectDTOService;
    @Mock
    private LegalPersonalCustomerService legalPersonalCustomerService;
    @InjectMocks
    private LegalPersonalCustomerController LegalPersonalCustomerController;
    @Mock
    private ModelMapper modelMapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(LegalPersonalCustomerController).build();
        modelMapper = new ModelMapper();
        convertObjectToObjectDTOService = new ConvertObjectToObjectDTOService(modelMapper);
        start();
    }

    @Test
    @DisplayName("Should return a list of LegalPersonalCustomers")
    void shouldReturnAListOfLegalPersonalCustomers() throws Exception {
        when(legalPersonalCustomerService.findAllLegalPersonalCustomer()).thenReturn(List.of(customerPjDTO));
        ResponseEntity<List<LegalPersonalCustomerDTO>> allLegalPersonalCustomerDTOs = LegalPersonalCustomerController
                                                                                        .getAllLegalPersonalCustomers();
        assertNotNull(allLegalPersonalCustomerDTOs);
        mockMvc.perform(get("/api/v1/customers/pj/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("Should return a LegalPersonalCustomer by id")
    void shouldReturnALegalPersonalCustomerById() throws Exception {
        LegalPersonalCustomerDTO expectedCustomerDto = convertObjectToObjectDTOService
                                                            .convertToLegalPersonalCustomerDTO(customerPj);
        when(legalPersonalCustomerService.findLegalPersonalCustomerById(expectedCustomerDto.getId()))
                .thenReturn(expectedCustomerDto);

        mockMvc.perform(get("/api/v1/customers/pj/{id}", expectedCustomerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedCustomerDto.getId()))
                .andExpect(jsonPath("$.clientName").value(expectedCustomerDto.getClientName()));
        verify(legalPersonalCustomerService, times(1))
                                            .findLegalPersonalCustomerById(expectedCustomerDto.getId());
    }

    @Test
    @DisplayName("Should return a exception when LegalPersonalCustomer not found")
    void shouldReturnAExceptionWhenLegalPersonalCustomerNotFound() {
        when(legalPersonalCustomerService.findLegalPersonalCustomerById(anyLong()))
                .thenThrow(new NoSuchElementException("Customer not found"));

        assertThrows(NoSuchElementException.class,
                () ->  LegalPersonalCustomerController.getLegalPersonalCustomerById(ID1L));
        String legalPersonalCustomerById = assertThrows(NoSuchElementException.class,
                () -> LegalPersonalCustomerController.getLegalPersonalCustomerById(ID1L)).getMessage();
        assertEquals("Customer not found", legalPersonalCustomerById);
        verify(legalPersonalCustomerService, times(2)).findLegalPersonalCustomerById(ID1L);
    }
    @Test
    @DisplayName("Should save a LegalPersonalCustomer")
    void shouldSaveALegalPersonalCustomer() throws Exception {
        LegalPersonalCustomerDTO requestDTO = newLegalPersonalCustomer();
        LegalPersonalCustomerDTO savedDTO = newLegalPersonalCustomer();

        when(legalPersonalCustomerService.saveLegalPersonalCustomer(requestDTO)).thenReturn(savedDTO);

        mockMvc.perform(post("/api/v1/customers/pj/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/v1/customers/pj/save/1"));

        verify(legalPersonalCustomerService, times(1)).saveLegalPersonalCustomer(requestDTO);
    }

    private static LegalPersonalCustomerDTO newLegalPersonalCustomer() {
        LegalPersonalCustomerDTO newObj = new LegalPersonalCustomerDTO();
        newObj.setId(1L);
        newObj.setClientName("Claudio Carigé");
        newObj.setCnpj("14.124.420/0001-94");
        newObj.setPrimaryEmail("carige@mail.com");
        newObj.setPhoneNumber("71991125697");
        newObj.setWhatsapp("71991125697");
        newObj.setBankCode("123");
        newObj.setAddress(oneAddress().now());
        newObj.setFinancialSituation("PAGO");
        newObj.setPayDay((byte) 5);
        return newObj;
    }

    private static String toJsonString(LegalPersonalCustomerDTO obj) throws JsonProcessingException {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(obj);
    }

    private void start() {
        customerPj = oneLegalPersonalCustomer().withId(ID1L).withCnpj(CNPJ).nowCustomerPJ();
        customerPjDTO = convertObjectToObjectDTOService
                .convertToLegalPersonalCustomerDTO(oneLegalPersonalCustomer().withId(1L).withCnpj(CNPJ).nowCustomerPJ());
    }
}