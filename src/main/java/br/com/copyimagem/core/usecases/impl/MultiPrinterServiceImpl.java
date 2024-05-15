package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.interfaces.MultiPrinterService;
import br.com.copyimagem.infra.persistence.repositories.MultiPrinterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MultiPrinterServiceImpl implements MultiPrinterService {

    private final MultiPrinterRepository multiPrinterRepository;

    public MultiPrinterServiceImpl(MultiPrinterRepository multiPrinterRepository) {
        this.multiPrinterRepository = multiPrinterRepository;
    }

    @Override
    public MultiPrinter findMultiPrinterById(Integer id) {
        MultiPrinter multiPrinter = multiPrinterRepository.findById(id).orElseThrow(() -> new NoSuchElementException("MultiPrint not found"));
        return multiPrinter;
    }
}
