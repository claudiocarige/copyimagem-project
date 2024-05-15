package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.builders.MultiPrinterBuilder;
import br.com.copyimagem.core.domain.entities.MultiPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiPrinterServiceTest {

    private MultiPrinter multiPrinter;
    private MultiPrinterBuilder multiPrinterBuilder;
    @Mock
    private MultiPrinterRepository multiPrinterRepository;

    @InjectMocks
    private MultiPrinterService multiPrinterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Must return a MultiPrinter by Id")
    void mustReturnMultiPrinterById(){
        Mockito.when(multiPrinterRepository.findById(1L)).thenReturn(Optional.of(multiPrinter));
        Optional<MultiPrinter> multiPrinterDto = multiPrinterService.findById(1L);

        assertTrue(multiPrinterDto.isPresent());
        assertEquals(multiPrinter, multiPrinterDto.get());
        assertEquals(multiPrinter.getId(), multiPrinterDto.get().getId());
        assertEquals(MultiPrinter.class, multiPrinterDto.get().getClass());
    }
}
