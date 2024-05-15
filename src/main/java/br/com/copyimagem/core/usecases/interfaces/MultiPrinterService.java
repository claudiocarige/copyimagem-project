package br.com.copyimagem.core.usecases.interfaces;

import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.dtos.MultiPrinterDTO;

public interface MultiPrinterService {

    MultiPrinterDTO findMultiPrinterById(Integer id);
}
