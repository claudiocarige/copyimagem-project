package br.com.copyimagem.core.usecases.interfaces;

import br.com.copyimagem.core.dtos.MultiPrinterDTO;

import java.util.List;

public interface MultiPrinterService {

    MultiPrinterDTO findMultiPrinterById(Integer id);
    List<MultiPrinterDTO> findAllMultiPrinters();
    MultiPrinterDTO saveMultiPrinter(MultiPrinterDTO multiPrinterDTO);
    MultiPrinterDTO setUpClientOnAMultiPrinter(Integer id, Long customer_Id);
    void deleteMultiPrinter(Integer id);
    MultiPrinterDTO deleteCustomerFromMultiPrinter(Integer id);
    MultiPrinterDTO setMachineStatus(Integer id, String status);
    MultiPrinterDTO setImpressionCounter(Integer id, Integer counter);
}
