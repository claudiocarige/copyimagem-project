package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.domain.enums.MachineStatus;
import br.com.copyimagem.core.dtos.MultiPrinterDTO;
import br.com.copyimagem.core.exceptions.IllegalArgumentException;
import br.com.copyimagem.infra.persistence.repositories.CustomerRepository;
import br.com.copyimagem.infra.persistence.repositories.MultiPrinterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.copyimagem.core.domain.builders.LegalPersonalCustomerBuilder.oneLegalPersonalCustomer;
import static br.com.copyimagem.core.domain.builders.MultiPrinterBuilder.oneMultiPrinter;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MultiPrinterServiceTest {

    private MultiPrinter multiPrinter;

    private MultiPrinterDTO multiPrinterDTO;
    @Mock
    private MultiPrinterRepository multiPrinterRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ConvertObjectToObjectDTOService convertObjectToObjectDTOService;

    @InjectMocks
    private MultiPrinterServiceImpl multiPrinterServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    @DisplayName("Must return a MultiPrinter by Id")
    void mustReturnMultiPrinterById(){
        when(multiPrinterRepository.findById(1)).thenReturn(Optional.ofNullable(multiPrinter));
        when(convertObjectToObjectDTOService.convertToMultiPrinterDTO(multiPrinter)).thenReturn(multiPrinterDTO);
        MultiPrinterDTO multiPrinterDto = multiPrinterServiceImpl.findMultiPrinterById(1);

        assertEquals(multiPrinterDTO, multiPrinterDto);
        assertEquals(multiPrinterDTO.getId(), multiPrinterDto.getId());
        assertEquals(MultiPrinterDTO.class, multiPrinterDto.getClass());
    }
    @Test
    @DisplayName("Must return a List of MultiPrinterDTO")
    void mustReturnListOfMultiPrinterDTO(){
        when(multiPrinterRepository.findAll()).thenReturn(Collections.singletonList(multiPrinter));
        when(convertObjectToObjectDTOService.convertToMultiPrinterDTO(multiPrinter)).thenReturn(multiPrinterDTO);
        List<MultiPrinterDTO> listMultiPrinterDto = multiPrinterServiceImpl.findAllMultiPrinters();

        assertEquals(multiPrinterDTO, listMultiPrinterDto.get(0));
        assertEquals(multiPrinterDTO.getId(), listMultiPrinterDto.get(0).getId());
        assertEquals(MultiPrinterDTO.class, listMultiPrinterDto.get(0).getClass());
    }

    @Test
    @DisplayName("Must save a MultiPrinter With success")
    void mustSaveAMultiPrinterWithSuccess(){
        multiPrinter.setCustomer(oneLegalPersonalCustomer().nowCustomerPJ());
        when(multiPrinterRepository.existsBySerialNumber(multiPrinterDTO.getSerialNumber())).thenReturn(false);
        when(convertObjectToObjectDTOService.convertToMultiPrinterDTO(multiPrinter)).thenReturn(multiPrinterDTO);
        when(convertObjectToObjectDTOService.convertToMultiPrinter(multiPrinterDTO)).thenReturn(multiPrinter);
        when(multiPrinterRepository.save(multiPrinter)).thenReturn(multiPrinter);

        MultiPrinterDTO multiPrinterDto = multiPrinterServiceImpl.saveMultiPrinter(multiPrinterDTO);
        assertEquals(multiPrinterDTO, multiPrinterDto);
        assertEquals(multiPrinterDTO.getId(), multiPrinterDto.getId());
        assertEquals(MultiPrinterDTO.class, multiPrinterDto.getClass());
        assertEquals(multiPrinterDTO.getCustomer().getClientName(),multiPrinterDto.getCustomer().getClientName());
    }

    @Test
    @DisplayName("You should not save a MultiPrint")
    void mustNotSaveAMultiPrinter(){
        multiPrinter.setCustomer(oneLegalPersonalCustomer().nowCustomerPJ());
        when(multiPrinterRepository.existsBySerialNumber(multiPrinterDTO.getSerialNumber())).thenReturn(true);
        when(convertObjectToObjectDTOService.convertToMultiPrinterDTO(multiPrinter)).thenReturn(multiPrinterDTO);
        when(convertObjectToObjectDTOService.convertToMultiPrinter(multiPrinterDTO)).thenReturn(multiPrinter);
        when(multiPrinterRepository.save(multiPrinter)).thenReturn(multiPrinter);

        String message = assertThrows(IllegalArgumentException.class,
                                   () -> multiPrinterServiceImpl.saveMultiPrinter(multiPrinterDTO)).getMessage();
        assertEquals("Serial number already exists", message);
    }

    @Test
    @DisplayName("Must set up a client on a MultiPrinter")
    void mustSetUpClientOnAMultiPrinter(){
        multiPrinterDTO.setCustomer(null);
        when(multiPrinterRepository.findById(1)).thenReturn(Optional.ofNullable(multiPrinter));
        when(multiPrinterRepository.save(multiPrinter)).thenReturn(multiPrinter);
        when(customerRepository.findById(1L))
                                    .thenReturn(Optional.ofNullable(oneLegalPersonalCustomer().nowCustomerPJ()));
        when(convertObjectToObjectDTOService.convertToMultiPrinterDTO(multiPrinter)).thenReturn(multiPrinterDTO);
        when(convertObjectToObjectDTOService.convertToMultiPrinter(multiPrinterDTO)).thenReturn(multiPrinter);

        MultiPrinterDTO multiPrinterDto = multiPrinterServiceImpl.setUpClientOnAMultiPrinter(1, 1L);
        assertEquals(multiPrinterDTO, multiPrinterDto);
        assertEquals(multiPrinterDTO.getId(), multiPrinterDto.getId());
        assertEquals(MultiPrinterDTO.class, multiPrinterDto.getClass());
        assertEquals(multiPrinterDTO.getCustomer().getClientName(), multiPrinterDto.getCustomer().getClientName());
    }

    @Test
    @DisplayName("Must throw an exception with existing Custome")
    void mustThrowAnExceptionWithExistingCustomerOnMultiPrinter(){
        when(multiPrinterRepository.findById(1)).thenReturn(Optional.ofNullable(multiPrinter));
        when(customerRepository.findById(1L))
                                    .thenReturn(Optional.ofNullable(oneLegalPersonalCustomer().nowCustomerPJ()));
        when(convertObjectToObjectDTOService.convertToMultiPrinterDTO(multiPrinter)).thenReturn(multiPrinterDTO);
        when(convertObjectToObjectDTOService.convertToMultiPrinter(multiPrinterDTO)).thenReturn(multiPrinter);
        String message = assertThrows(IllegalArgumentException.class,
                () -> multiPrinterServiceImpl.setUpClientOnAMultiPrinter(1, 1L)).getMessage();
        assertEquals("This printer is already Customer.", message);
    }

    @Test
    @DisplayName("Must delete a MultiPrinter")
    void mustDeleteAMultiPrinter(){
        multiPrinter.setCustomer(null);
        when(multiPrinterRepository.findById(1)).thenReturn(Optional.ofNullable(multiPrinter));
        multiPrinterServiceImpl.deleteMultiPrinter(1);
        verify(multiPrinterRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Must throw an exception with existing Custome")
    void mustThrowAnExceptionWithExistingCustomer(){
        when(multiPrinterRepository.findById(1)).thenReturn(Optional.ofNullable(multiPrinter));
        String message = assertThrows(IllegalArgumentException.class,
                () -> multiPrinterServiceImpl.deleteMultiPrinter(1)).getMessage();
        assertEquals("This printer cannot be deleted.", message);
    }

    @Test
    @DisplayName("Must delete Customer From Multiprinter")
    void mustDeleteCustomerFromMultiPrinter(){
        when(multiPrinterRepository.findById(1)).thenReturn(Optional.ofNullable(multiPrinter));
        when(multiPrinterRepository.save(multiPrinter)).thenReturn(multiPrinter);
        when(convertObjectToObjectDTOService.convertToMultiPrinterDTO(multiPrinter)).thenReturn(multiPrinterDTO);
        when(convertObjectToObjectDTOService.convertToMultiPrinter(multiPrinterDTO)).thenReturn(multiPrinter);
        MultiPrinterDTO multiPrinterDto = multiPrinterServiceImpl.deleteCustomerFromMultiPrinter(1);
        assertEquals(multiPrinterDTO, multiPrinterDto);
        assertEquals(multiPrinterDTO.getId(), multiPrinterDto.getId());
        assertEquals(MultiPrinterDTO.class, multiPrinterDto.getClass());
        assertNull(multiPrinterDto.getCustomer());
    }

    @Test
    @DisplayName("Must SET Machine Status")
    void mustSetMAchineStatus(){
        when(multiPrinterRepository.updateMachineStatusById(1, MachineStatus.MANUTENCAO)).thenReturn(multiPrinter);
        when(convertObjectToObjectDTOService.convertToMultiPrinterDTO(multiPrinter)).thenReturn(multiPrinterDTO);
        multiPrinterDTO.setMachineStatus(MachineStatus.MANUTENCAO);
        MultiPrinterDTO multiPrinterDto = multiPrinterServiceImpl.setMachineStatus(1,"MANUTENCAO");
        assertEquals(multiPrinterDTO, multiPrinterDto);
        assertEquals(MachineStatus.MANUTENCAO, multiPrinterDto.getMachineStatus());
    }

    void start(){
        multiPrinter = oneMultiPrinter().withCustomer(oneLegalPersonalCustomer().nowCustomerPJ()).now();
        multiPrinterDTO = new MultiPrinterDTO();
        multiPrinterDTO.setId(multiPrinter.getId());
        multiPrinterDTO.setBrand(multiPrinter.getBrand());
        multiPrinterDTO.setModel(multiPrinter.getModel());
        multiPrinterDTO.setSerialNumber(multiPrinter.getSerialNumber());
        multiPrinterDTO.setMachineValue(multiPrinter.getMachineValue());
        multiPrinterDTO.setMachineStatus(multiPrinter.getMachineStatus());
        multiPrinterDTO.setImpressionCounter(multiPrinter.getImpressionCounter());
        multiPrinterDTO.setCustomer(multiPrinter.getCustomer());
    }
}
