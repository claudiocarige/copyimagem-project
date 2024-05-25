package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.Customer;
import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.domain.enums.MachineStatus;
import br.com.copyimagem.core.dtos.MultiPrinterDTO;
import br.com.copyimagem.core.exceptions.IllegalArgumentException;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.interfaces.MultiPrinterService;
import br.com.copyimagem.infra.persistence.repositories.CustomerRepository;
import br.com.copyimagem.infra.persistence.repositories.MultiPrinterRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MultiPrinterServiceImpl implements MultiPrinterService {

    private final MultiPrinterRepository multiPrinterRepository;

    private final CustomerRepository customerRepository;

    private final ConvertObjectToObjectDTOService convertObjectToObjectDTOService;

    public MultiPrinterServiceImpl(MultiPrinterRepository multiPrinterRepository,
                                   CustomerRepository customerRepository,
                                   ConvertObjectToObjectDTOService convertObjectToObjectDTOService) {
        this.multiPrinterRepository = multiPrinterRepository;
        this.customerRepository = customerRepository;
        this.convertObjectToObjectDTOService = convertObjectToObjectDTOService;
    }

    @Override
    public MultiPrinterDTO findMultiPrinterById(Integer id) {
        MultiPrinter multiPrint = multiPrinterRepository.findById(id)
                                         .orElseThrow(() -> new NoSuchElementException("MultiPrint not found"));
        return convertObjectToObjectDTOService.convertToMultiPrinterDTO(multiPrint);
    }

    @Override
    public List<MultiPrinterDTO>  findAllMultiPrinters(){
        List<MultiPrinter> multiPrinterList = multiPrinterRepository.findAll();
        return multiPrinterList.stream().map(convertObjectToObjectDTOService::convertToMultiPrinterDTO).toList();
    }

    @Override
    public MultiPrinterDTO saveMultiPrinter(MultiPrinterDTO multiPrinterDTO) {
        checkSerialNumber(multiPrinterDTO.getSerialNumber());
        MultiPrinter multiPrinter = multiPrinterRepository.save(convertObjectToObjectDTOService
                                                                       .convertToMultiPrinter(multiPrinterDTO));
        return convertObjectToObjectDTOService.convertToMultiPrinterDTO(multiPrinter);
    }

    @Override
    public MultiPrinterDTO setUpClientOnAMultiPrinter(Integer id, Long customer_Id) {
        Customer customer = customerRepository.findById(customer_Id)
                                           .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        MultiPrinterDTO multiPrinterDTO = findMultiPrinterById(id);
        if (multiPrinterDTO.getCustomer_id() != null){
            throw new IllegalArgumentException("This printer is already Customer.");
        }
        multiPrinterDTO.setCustomer_id(customer.getId().toString());
        multiPrinterDTO.setMachineStatus(MachineStatus.LOCADA);
        MultiPrinter multiPrinter = convertObjectToObjectDTOService.convertToMultiPrinter(multiPrinterDTO);
        multiPrinter.setCustomer(customer);
        multiPrinterRepository.save(multiPrinter);
        return multiPrinterDTO;
    }


    @Override
    public void deleteMultiPrinter(Integer id) {
        MultiPrinter multiPrinter = multiPrinterRepository.findById(id)
                                        .orElseThrow(() -> new NoSuchElementException("MultiPrint not found"));
        if (multiPrinter.getCustomer() != null) {
            throw new IllegalArgumentException("This printer cannot be deleted.");
        }
        multiPrinterRepository.deleteById(id);
    }

    @Override
    public MultiPrinterDTO deleteCustomerFromMultiPrinter(Integer id){
        MultiPrinterDTO multiPrinterDTO = findMultiPrinterById(id);
        multiPrinterDTO.setCustomer_id(null);
        multiPrinterDTO.setMachineStatus(MachineStatus.DISPONIVEL);
        multiPrinterRepository.save(convertObjectToObjectDTOService.convertToMultiPrinter(multiPrinterDTO));
        return multiPrinterDTO;
    }

    @Override
    public MultiPrinterDTO setMachineStatus(Integer id, String status) {
        MultiPrinter multiPrinter;
        switch (status) {
            case "DISPONIVEL", "MANUTENCAO", "LOCADA", "INATIVA" ->
                    multiPrinter = multiPrinterRepository.updateMachineStatusById(id, MachineStatus.valueOf(status));
            default ->
                    throw new IllegalArgumentException("Invalid Status: " + status);
        }
        return convertObjectToObjectDTOService.convertToMultiPrinterDTO(multiPrinter);
    }

    @Override
    public MultiPrinterDTO setImpressionCounter(Integer id, Integer counter){
        MultiPrinter multiPrinter = multiPrinterRepository.updateImpressionCounterById(id, counter);
        return convertObjectToObjectDTOService.convertToMultiPrinterDTO(multiPrinter);
    }

    private void checkSerialNumber(String serialNumber) {
        if (multiPrinterRepository.existsBySerialNumber(serialNumber)) {
            throw new IllegalArgumentException("Serial number already exists");
        }
    }
}
