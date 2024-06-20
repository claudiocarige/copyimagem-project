package br.com.copyimagem.infra.controllers;

import br.com.copyimagem.core.domain.builders.MultiPrinterBuilder;
import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.domain.enums.MachineStatus;
import br.com.copyimagem.core.dtos.MultiPrinterDTO;
import br.com.copyimagem.core.usecases.interfaces.MultiPrinterService;
import br.com.copyimagem.infra.persistence.repositories.MultiPrinterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


class MultiprinterControllerTest {


    private MultiPrinter multiPrinter;

    private MultiPrinterDTO multiPrinterDTO;

    @Mock
    private MultiPrinterRepository multiPrinterRepository;

    @Mock
    private MultiPrinterService multiPrinterService;

    @InjectMocks
    private MultiprinterController multiprinterController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks( this );
        mockMvc = MockMvcBuilders.standaloneSetup( multiprinterController ).build();
        start();
    }

    @Test
    @DisplayName( "Should return a list of MultiPrinter" )
    void shouldReturnAListOfMultiPrinters() throws Exception {

        when( multiPrinterService.findAllMultiPrinters() ).thenReturn( List.of( multiPrinterDTO ) );
        ResponseEntity< List< MultiPrinterDTO > > multiPrinterDTOList = multiprinterController.findAllMultiPrinters();
        assertNotNull( multiPrinterDTOList );
        assertEquals( 1, Objects.requireNonNull( multiPrinterDTOList.getBody() ).size() );
        assertEquals( multiPrinterDTO, multiPrinterDTOList.getBody().get( 0 ) );

        mockMvc.perform( get( "/api/v1/multi-printer" ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) );
    }

    @Test
    @DisplayName( "Should return a MultiPrinter by id" )
    void shouldReturnAMultiPrinterById() throws Exception {

        when( multiPrinterService.findMultiPrinterById( 1 ) ).thenReturn( multiPrinterDTO );
        ResponseEntity< MultiPrinterDTO > multiPrinterDTOResponse = multiprinterController.findMultiPrinterById( 1 );
        assertNotNull( multiPrinterDTO );
        assertEquals( multiPrinterDTO, multiPrinterDTOResponse.getBody() );

        mockMvc.perform( get( "/api/v1/multi-printer/{id}", multiPrinterDTO.getId() )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( jsonPath( "$.id" )
                                     .value( Objects.requireNonNull( multiPrinterDTOResponse.getBody() ).getId() ) );
    }

    @Test
    @DisplayName( "Should return a list of MultiPrinter by customer id" )
    void shouldReturnAllMultiPrintersByCustomerId() throws Exception {

        when( multiPrinterService.findAllMultiPrintersByCustomerId( 1L ) )
                                                                            .thenReturn( List.of( multiPrinterDTO ) );
        ResponseEntity< List< MultiPrinterDTO > > multiPrinterDTOList =
                                                        multiprinterController.findAllMultiPrintersByCustomerId( 1L );
        assertNotNull( multiPrinterDTOList );
        assertEquals( 1, Objects.requireNonNull( multiPrinterDTOList.getBody() ).size() );
        assertEquals( multiPrinterDTO, multiPrinterDTOList.getBody().get( 0 ) );

        mockMvc.perform( get( "/api/v1/multi-printer/customer/{customerId}", multiPrinterDTO.getId() )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) );
    }

    @Test
    @DisplayName( "Should save a MultiPrinter" )
    void saveMultiPrinter() throws Exception {

        when( multiPrinterService.saveMultiPrinter( multiPrinterDTO ) ).thenReturn( multiPrinterDTO );
        ResponseEntity< MultiPrinterDTO > multiPrinterDTOResponse =
                                                            multiprinterController.saveMultiPrinter( multiPrinterDTO );
        assertEquals( multiPrinterDTO, multiPrinterDTOResponse.getBody() );
        assertNotNull( multiPrinterDTOResponse );
        mockMvc.perform( post( "/api/v1/multi-printer/save" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( asJsonString( multiPrinterDTO ) )
                        .accept( MediaType.APPLICATION_JSON ) )
              //  .andDo( result -> System.out.println( result.getResponse().getContentAsString() ) )
                .andExpect( status().isOk() );
    }

    @Test
    @DisplayName( "Should set up a client on a MultiPrinter" )
    void shouldSetUpClientOnAMultiPrinter() throws Exception {
        when( multiPrinterService.setUpClientOnAMultiPrinter( 1, 1L ) ).thenReturn( multiPrinterDTO );

        ResponseEntity< MultiPrinterDTO > multiPrinterDTOResponse = multiprinterController.setUpClientOnAMultiPrinter( 1, 1L );
        assertNotNull( multiPrinterDTOResponse );
        assertEquals( multiPrinterDTO, multiPrinterDTOResponse.getBody() );
        mockMvc.perform( patch( "/api/v1/multi-printer/set-customer?id=1&customerId=1" ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) );
    }

    @Test
    @DisplayName( "Should delete a customer from a MultiPrinter by id" )
    void shouldDeleteACustomerFromAMultiPrinterById() {

        ResponseEntity<Void> response = multiprinterController.deleteCustomerFromMultiPrinter(1);
        verify(multiPrinterService).deleteCustomerFromMultiPrinter(1);
        assertThat(response.getStatusCode()).isEqualTo( HttpStatus.NO_CONTENT);
    }


    @Test
    @DisplayName( "Should set the machine status" )
    void shouldSetMachineStatus() throws Exception {

        when( multiPrinterService.setMachineStatus( 1, MachineStatus.LOCADA.toString() ) ).thenReturn( multiPrinterDTO );
        ResponseEntity< MultiPrinterDTO > multiPrinterDTOResponse =
                multiprinterController.setMachineStatus( 1, MachineStatus.LOCADA.toString() );
        assertNotNull( multiPrinterDTOResponse );
        assertEquals( multiPrinterDTO, multiPrinterDTOResponse.getBody() );
        mockMvc.perform( patch( "/api/v1/multi-printer/status?id=1&status=status" ) )
                .andExpect( status().isOk() );
    }

    @Test
    @DisplayName( "Should set the impression counter")
    void shouldSetImpressionCounter() throws Exception {

        when( multiPrinterService.setImpressionCounter( 1, 1000, "impressionCounterInitial" ) )
                .thenReturn( multiPrinterDTO );
        ResponseEntity< MultiPrinterDTO > multiPrinterDTOResponse =
                multiprinterController.setImpressionCounter( 1, 1000, "impressionCounterInitial" );
        assertNotNull( multiPrinterDTOResponse );
        assertEquals( multiPrinterDTO, multiPrinterDTOResponse.getBody() );
        mockMvc.perform( patch(
          "/api/v1/multi-printer/impression-counter?id=1&counter=1000&attribute=impressionCounterInitial" ) )
                .andExpect( status().isOk() );
    }

    @Test
    @DisplayName( "Should delete a MultiPrinter" )
    void shouldDeleteAMultiPrinter() throws Exception {

        ResponseEntity<Void> response = multiprinterController.deleteMultiPrinter(1);
        verify(multiPrinterService).deleteMultiPrinter(1);
        assertThat(response.getStatusCode()).isEqualTo( HttpStatus.NO_CONTENT);
        mockMvc.perform( delete( "/api/v1/multi-printer/{id}", 1 ) )
                .andExpect( status().isNoContent() );

    }


    private static String asJsonString( MultiPrinterDTO obj ) throws JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule( new JavaTimeModule() );
        return mapper.writeValueAsString( obj );
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