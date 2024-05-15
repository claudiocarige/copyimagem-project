package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.dtos.MultiPrinterDTO;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.interfaces.MultiPrinterService;
import br.com.copyimagem.infra.persistence.repositories.MultiPrinterRepository;
import org.springframework.stereotype.Service;


@Service
public class MultiPrinterServiceImpl implements MultiPrinterService {

    private final MultiPrinterRepository multiPrinterRepository;

    private final ConvertObjectToObjectDTOService convertObjectToObjectDTOService;

    public MultiPrinterServiceImpl(MultiPrinterRepository multiPrinterRepository, ConvertObjectToObjectDTOService convertObjectToObjectDTOService) {
        this.multiPrinterRepository = multiPrinterRepository;
        this.convertObjectToObjectDTOService = convertObjectToObjectDTOService;
    }

    @Override
    public MultiPrinterDTO findMultiPrinterById(Integer id) {
        MultiPrinter multiPrint = multiPrinterRepository.findById(id).orElseThrow(() -> new NoSuchElementException("MultiPrint not found"));
        return convertObjectToObjectDTOService.convertToMultiPrinterDTO(multiPrint);
    }
}
