package br.com.copyimagem.core.controllers;

import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.interfaces.NaturalPersonCustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import org.springframework.http.MediaType;
import static br.com.copyimagem.core.domain.builders.CustomerBuilder.oneCustomer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class NaturalPersonCustomerControllerTest {

    public static final long ID1L = 1L;
    public static final String CPF = "123.456.789-01";
    private NaturalPersonCustomer customerPf;
    @Mock
    private NaturalPersonCustomerService naturalPersonCustomerService;
    @InjectMocks
    private NaturalPersonCustomerController naturalPersonCustomerController;

    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(naturalPersonCustomerController).build();
        start();
    }

    @Test
    @DisplayName("Should return a list of NaturalPersonCustomers")
    void shouldReturnAListOfNaturalPersonCustomers() throws Exception {
        String cpf = "894.965.315-04";
        String email = "julio@mail.com";
        when(naturalPersonCustomerService.findAllNaturalPersonCustomer()).thenReturn(List.of(customerPf,
                oneCustomer().withCpf(cpf).withPrimaryEmail(email).nowCustomerPF()));
        ResponseEntity<List<NaturalPersonCustomer>> allNaturalPersonCustomers = naturalPersonCustomerController.getAllNaturalPersonCustomers();
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
        when(naturalPersonCustomerService.findNaturalPersonCustomerById(ID1L)).thenReturn(customerPf);
        ResponseEntity<NaturalPersonCustomer> naturalPersonCustomerById = naturalPersonCustomerController.getNaturalPersonCustomerById(ID1L);
        assertNotNull(naturalPersonCustomerById);

        mockMvc.perform(get("/api/v1/customers/pf/{id}", ID1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
        verify(naturalPersonCustomerService, times(2)).findNaturalPersonCustomerById(ID1L);

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
        NaturalPersonCustomer customerSalvo = new NaturalPersonCustomer();
        customerSalvo.setId(1L);
        when(naturalPersonCustomerService.saveNaturalPersonCustomer(customerPf)).thenReturn(customerSalvo);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/pf/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(customerPf)))
                .andExpect(status().isCreated())
                .andReturn();

        String locationUri = mvcResult.getResponse().getHeader("Location");
        assertNotNull(locationUri);
        assertTrue(locationUri.startsWith("http://localhost"));
        assertTrue(locationUri.endsWith("pf/save/1"));
    }

    private static String toJsonString(final NaturalPersonCustomer obj) throws JsonProcessingException {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
    }

    private void start() {
        customerPf = oneCustomer().withCpf(CPF).nowCustomerPF();
    }
}