package br.com.copyimagem.infra.controllers;

import br.com.copyimagem.core.domain.builders.MultiPrinterBuilder;
import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.dtos.MultiPrinterDTO;
import br.com.copyimagem.core.usecases.interfaces.MultiPrinterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class MultiprinterControllerTest {


    private MultiPrinter multiPrinter;

    private MultiPrinterDTO multiPrinterDTO;

    @Mock
    private MultiPrinterService multiPrinterService;

    @InjectMocks
    private MultiprinterController multiprinterController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(multiprinterController).build();
        start();

    }


    @Test
    @DisplayName("Should return a list of MultiPrinter")
    void shouldReturnAListOfMultiPrinters() throws Exception {
        when(multiPrinterService.findAllMultiPrinters()).thenReturn( List.of(multiPrinterDTO));
        ResponseEntity<List<MultiPrinterDTO>> multiPrinterDTOList = multiprinterController.findAllMultiPrinters();
        assertNotNull(multiPrinterDTOList);
        assertEquals( 1, multiPrinterDTOList.getBody().size());
        assertEquals( multiPrinterDTO, multiPrinterDTOList.getBody().get(0));

        mockMvc.perform(get("/api/v1/multi-printer"))
                .andExpect(status().isOk())
                .andExpect(content().contentType( MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Should return a MultiPrinter by id")
    void shouldReturnAMultiPrinterById() throws Exception {
        when(multiPrinterService.findMultiPrinterById(1)).thenReturn(multiPrinterDTO);
        ResponseEntity<MultiPrinterDTO> multiPrinterDTOResponse = multiprinterController.findMultiPrinterById(1);
        assertNotNull(multiPrinterDTO);
        assertEquals( multiPrinterDTO, multiPrinterDTOResponse.getBody());

        mockMvc.perform(get("/api/v1/multi-printer/1", multiPrinterDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(multiPrinterDTOResponse.getBody().getId()));

    }

    @Test
    @DisplayName("Should return a list of MultiPrinter by customer id")
    void shouldReturnAllMultiPrintersByCustomerId() throws Exception {
        when(multiPrinterService.findAllMultiPrintersByCustomerId(1L)).thenReturn(List.of(multiPrinterDTO));
        ResponseEntity<List<MultiPrinterDTO>> multiPrinterDTOList = multiprinterController.findAllMultiPrintersByCustomerId(1L);
        assertNotNull(multiPrinterDTOList);
        assertEquals( 1, multiPrinterDTOList.getBody().size());
        assertEquals( multiPrinterDTO, multiPrinterDTOList.getBody().get(0));

        mockMvc.perform(get("/api/v1/multi-printer/customer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Should save a new MultiPrinter")
    void shouldSaveANewMultiPrinter() throws Exception {
        when(multiPrinterService.saveMultiPrinter(multiPrinterDTO)).thenReturn(multiPrinterDTO);
        ResponseEntity<MultiPrinterDTO> multiPrinterDTOResponse = multiprinterController.saveMultiPrinter(multiPrinterDTO);
        assertNotNull(multiPrinterDTOResponse);
        assertEquals( multiPrinterDTO, multiPrinterDTOResponse.getBody());

        mockMvc.perform(get("/api/v1/multi-printer/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(multiPrinterDTO.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void setUpClientOnAMultiPrinter() {

    }

    @Test
    void deleteCustomerFromMultiPrinter() {

    }

    @Test
    void setMachineStatus() {

    }

    @Test
    void setImpressionCounter() {

    }

    @Test
    void deleteMultiPrinter() {

    }

    private void start() {
        multiPrinter = MultiPrinterBuilder.oneMultiPrinter().now();
        multiPrinterDTO = new MultiPrinterDTO(
                multiPrinter.getId(),
                multiPrinter.getBrand(),
                multiPrinter.getModel(),
                multiPrinter.getSerialNumber(),
                multiPrinter.getMachineValue(),
                multiPrinter.getMachineStatus(),
                multiPrinter.getPrintType(),
                multiPrinter.getImpressionCounterInitial(),
                multiPrinter.getImpressionCounterBefore(),
                multiPrinter.getImpressionCounterNow(),
                multiPrinter.getPrintingFranchise(),
                multiPrinter.getAmountPrinter(),
                multiPrinter.getMonthlyPrinterAmount(),
               "1"
        );

    }
}