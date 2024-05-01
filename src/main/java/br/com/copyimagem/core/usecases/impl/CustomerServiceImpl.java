package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.Customer;
import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.interfaces.CustomerService;
import br.com.copyimagem.core.usecases.interfaces.LegalPersonalCustomerService;
import br.com.copyimagem.core.usecases.interfaces.NaturalPersonCustomerService;
import br.com.copyimagem.infra.repositories.CustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ConvertObjectToObjectDTOService convertObjectToObjectDTOService;

    private final LegalPersonalCustomerService legalPersonalCustomerService;

    private final NaturalPersonCustomerService naturalPersonCustomerService;

    public CustomerServiceImpl(CustomerRepository customerRepository, ConvertObjectToObjectDTOService convertObjectToObjectDTOService, LegalPersonalCustomerServiceImpl legalPersonalCustomerService, NaturalPersonCustomerServiceImpl naturalPersonCustomerService) {
        this.customerRepository = customerRepository;
        this.convertObjectToObjectDTOService = convertObjectToObjectDTOService;
        this.legalPersonalCustomerService = legalPersonalCustomerService;
        this.naturalPersonCustomerService = naturalPersonCustomerService;
    }

    @Override
    public CustomerResponseDTO searchCliente(String typeParam, String valueParam){
        return
            switch (typeParam.toLowerCase()) {
                case "id" -> findById(Long.parseLong(valueParam));
                case "cpf" -> findByCpf(valueParam);
                case "cnpj" -> findByCnpj(valueParam);
                case "email" -> findByPrimaryEmail(valueParam);
                default -> null;
            };
    }

    private CustomerResponseDTO findById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()){
            log.error("[ ERROR ] Exception (findById() method in CustomerServiceImpl class):  {}.",
                                                                                NoSuchElementException.class);
            throw new NoSuchElementException("Customer not found");
        }
        return convertObjectToObjectDTOService.convertToCustomerResponseDTO(customerOptional.get());
    }

    private CustomerResponseDTO  findByPrimaryEmail(String email) {
        Optional<Customer> customerOptional = customerRepository.findByPrimaryEmail(email);
        if (customerOptional.isEmpty()){
            log.error("[ ERROR ] Exception (findByPrimaryEmail() method in CustomerServiceImpl class) :  {}.",
                                                                                NoSuchElementException.class);
            throw new NoSuchElementException("Customer not found");
        }
        return convertObjectToObjectDTOService.convertToCustomerResponseDTO(customerOptional.get());
    }

    private CustomerResponseDTO findByCnpj(String valueParam) {
        return legalPersonalCustomerService.findByCnpj(valueParam);
    }

    private CustomerResponseDTO findByCpf(String valueParam) {
        return naturalPersonCustomerService.findByCpf(valueParam);
    }
}
