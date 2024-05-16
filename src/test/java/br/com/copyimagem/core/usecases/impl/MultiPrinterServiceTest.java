package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.dtos.MultiPrinterDTO;
import br.com.copyimagem.infra.persistence.repositories.MultiPrinterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.copyimagem.core.domain.builders.MultiPrinterBuilder.oneMultiPrinter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MultiPrinterServiceTest {

    private MultiPrinter multiPrinter;

    private MultiPrinterDTO multiPrinterDTO;
    @Mock
    private MultiPrinterRepository multiPrinterRepository;

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

    void start(){
        multiPrinter = oneMultiPrinter().now();
        multiPrinterDTO = new MultiPrinterDTO();
        multiPrinterDTO.setId(multiPrinter.getId());
        multiPrinterDTO.setBrand(multiPrinter.getBrand());
        multiPrinterDTO.setModel(multiPrinter.getModel());
        multiPrinterDTO.setSerialNumber(multiPrinter.getSerialNumber());
        multiPrinterDTO.setMachineValue(multiPrinter.getMachineValue());
        multiPrinterDTO.setMachineStatus(multiPrinter.getMachineStatus());
        multiPrinterDTO.setImpressionCounter(multiPrinter.getImpressionCounter());
    }
}
