package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.infra.persistence.repositories.MultiPrinterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static br.com.copyimagem.core.domain.builders.MultiPrinterBuilder.oneMultiPrinter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MultiPrinterServiceTest {

    private MultiPrinter multiPrinter;
    @Mock
    private MultiPrinterRepository multiPrinterRepository;

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
        MultiPrinter multiPrinterDto = multiPrinterServiceImpl.findMultiPrinterById(1);

        assertEquals(multiPrinter, multiPrinterDto);
        assertEquals(multiPrinter.getId(), multiPrinterDto.getId());
        assertEquals(MultiPrinter.class, multiPrinterDto.getClass());
    }

    void start(){
        multiPrinter = oneMultiPrinter().now();
    }
}
