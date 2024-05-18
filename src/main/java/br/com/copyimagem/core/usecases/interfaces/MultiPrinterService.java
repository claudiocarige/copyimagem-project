package br.com.copyimagem.core.usecases.interfaces;

import br.com.copyimagem.core.dtos.MultiPrinterDTO;

import java.util.List;

public interface MultiPrinterService {

    MultiPrinterDTO findMultiPrinterById(Integer id);
    List<MultiPrinterDTO> findAllMultiPrinters();
    MultiPrinterDTO saveMultiPrinter(MultiPrinterDTO multiPrinterDTO);
    MultiPrinterDTO setUpClientOnAMultiPrinter(Integer id, Long customer_Id);
}
