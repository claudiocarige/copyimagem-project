package br.com.copyimagem.core.controllers;

import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import br.com.copyimagem.core.dtos.NaturalPersonCustomerDTO;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.impl.ConvertObjectToObjectDTOService;
import br.com.copyimagem.core.usecases.interfaces.NaturalPersonCustomerService;
import br.com.copyimagem.infra.repositories.NaturalPersonCustomerRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.MediaType;

import static br.com.copyimagem.core.domain.builders.AdressBuilder.oneAdress;
import static br.com.copyimagem.core.domain.builders.NaturalPersonCustomerBuilder.oneCustomer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Log4j2
class NaturalPersonCustomerControllerTest {

    public static final long ID1L = 1L;
    public static final String CPF = "156.258.240-29";
    private NaturalPersonCustomer customerPf;
    private NaturalPersonCustomerDTO customerPfDTO;
    @Mock
    private ConvertObjectToObjectDTOService convertObjectToObjectDTOService;
    @Mock
    private NaturalPersonCustomerService naturalPersonCustomerService;
    @InjectMocks
    private NaturalPersonCustomerController naturalPersonCustomerController;
    @Mock
    private ModelMapper modelMapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(naturalPersonCustomerController).build();
        modelMapper = new ModelMapper();
        convertObjectToObjectDTOService = new ConvertObjectToObjectDTOService(modelMapper);
        start();
    }

    @Test
    @DisplayName("Should return a list of NaturalPersonCustomers")
    void shouldReturnAListOfNaturalPersonCustomers() throws Exception {
        when(naturalPersonCustomerService.findAllNaturalPersonCustomer()).thenReturn(List.of(customerPfDTO));
        ResponseEntity<List<NaturalPersonCustomerDTO>> allNaturalPersonCustomers = naturalPersonCustomerController
                                                                                        .getAllNaturalPersonCustomers();
        assertNotNull(allNaturalPersonCustomers);
        mockMvc.perform(get("/api/v1/customers/pf/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("Should return a NaturalPersonCustomer by id")
    void shouldReturnANaturalPersonCustomerById() throws Exception {
        NaturalPersonCustomerDTO expectedCustomerDto = convertObjectToObjectDTOService
                                                                    .convertToNaturalPersonCustomerDTO(customerPf);
        when(naturalPersonCustomerService.findNaturalPersonCustomerById(expectedCustomerDto.getId()))
                .thenReturn(expectedCustomerDto);

        mockMvc.perform(get("/api/v1/customers/pf/{id}", expectedCustomerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedCustomerDto.getId()))
                .andExpect(jsonPath("$.clientName").value(expectedCustomerDto.getClientName())); // Add assertions for other fields
        verify(naturalPersonCustomerService, times(1))
                                        .findNaturalPersonCustomerById(expectedCustomerDto.getId());
    }

    @Test
    @DisplayName("Should return a exception when NaturalPersonCustomer not found")
    void shouldReturnAExceptionWhenNaturalPersonCustomerNotFound() {
        when(naturalPersonCustomerService.findNaturalPersonCustomerById(anyLong()))
                .thenThrow(new NoSuchElementException("Customer not found"));

        assertThrows(NoSuchElementException.class,
                () ->  naturalPersonCustomerController.getNaturalPersonCustomerById(ID1L));
        String naturalPersonCustomerById = assertThrows(NoSuchElementException.class,
                () -> naturalPersonCustomerController.getNaturalPersonCustomerById(ID1L)).getMessage();
        assertEquals("Customer not found", naturalPersonCustomerById);
        verify(naturalPersonCustomerService, times(2)).findNaturalPersonCustomerById(ID1L);
    }
    @Test
    @DisplayName("Should save a NaturalPersonCustomer")
    void shouldSaveANaturalPersonCustomer() throws Exception {
        NaturalPersonCustomerDTO requestDTO = newNaturalCustomer();
        NaturalPersonCustomerDTO savedDTO = newNaturalCustomer();

        when(naturalPersonCustomerService.saveNaturalPersonCustomer(requestDTO)).thenReturn(savedDTO);
        mockMvc.perform(post("/api/v1/customers/pf/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/v1/customers/pf/save/1"));

        verify(naturalPersonCustomerService, times(1)).saveNaturalPersonCustomer(requestDTO);
    }

    private static NaturalPersonCustomerDTO newNaturalCustomer() {
        NaturalPersonCustomerDTO requestDTO = new NaturalPersonCustomerDTO();
        requestDTO.setId(1L);
        requestDTO.setClientName("Claudio Carigé");
        requestDTO.setCpf("156.258.240-29");
        requestDTO.setPrimaryEmail("carige@mail.com");
        requestDTO.setPhoneNumber("71991125697");
        requestDTO.setWhatsapp("71991125697");
        requestDTO.setBankCode("123");
        requestDTO.setAdress(oneAdress().now());
        requestDTO.setStartContract(LocalDate.of(2023, 1, 1));
        requestDTO.setFinancialSituation("PAGO");
        requestDTO.setPayDay((byte) 5);
        return requestDTO;
    }

    private static String toJsonString(NaturalPersonCustomerDTO obj) throws JsonProcessingException {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(obj);
    }

    private void start() {
        customerPf = oneCustomer().withId(ID1L).withCpf(CPF).nowCustomerPF();
        customerPfDTO = convertObjectToObjectDTOService
                .convertToNaturalPersonCustomerDTO(oneCustomer()
                                        .withId(1L).withCpf(CPF).nowCustomerPF());
    }
}