package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.LegalPersonalCustomer;
import br.com.copyimagem.core.dtos.LegalPersonalCustomerDTO;
import br.com.copyimagem.core.exceptions.DataIntegrityViolationException;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.interfaces.LegalPersonalCustomerService;
import br.com.copyimagem.infra.repositories.LegalPersonalCustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class LegalPersonalCustomerServiceImpl implements LegalPersonalCustomerService {

    private final LegalPersonalCustomerRepository legalPersonalCustomerRepository;

    private final ConvertObjectToObjectDTOService convertObjectToObjectDTOService;

    public LegalPersonalCustomerServiceImpl(
            LegalPersonalCustomerRepository legalPersonalCustomerRepository,
            ConvertObjectToObjectDTOService convertObjectToObjectDTOService) {
        this.legalPersonalCustomerRepository = legalPersonalCustomerRepository;
        this.convertObjectToObjectDTOService = convertObjectToObjectDTOService;
    }



    @Override
    public LegalPersonalCustomerDTO findLegalPersonalCustomerById(Long id) {
        log.info("[ INFO ] Finding LegalPersonalCustomer by id: {}", id);
        LegalPersonalCustomer legalPersonalCustomer = legalPersonalCustomerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        return convertObjectToObjectDTOService.convertToLegalPersonalCustomerDTO(legalPersonalCustomer);
    }

    @Override
    public LegalPersonalCustomerDTO saveLegalPersonalCustomer(LegalPersonalCustomerDTO legalPersonalCustomer) {
        return null;
    }

    @Override
    public List<LegalPersonalCustomerDTO> findAllLegalPersonalCustomer() {
        return null;
    }

}
