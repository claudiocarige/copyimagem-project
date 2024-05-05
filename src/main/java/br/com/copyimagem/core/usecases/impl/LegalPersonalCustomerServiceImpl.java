package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.Address;
import br.com.copyimagem.core.domain.entities.LegalPersonalCustomer;
import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.dtos.LegalPersonalCustomerDTO;
import br.com.copyimagem.core.exceptions.DataIntegrityViolationException;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.interfaces.LegalPersonalCustomerService;
import br.com.copyimagem.infra.persistence.repositories.AddressRepository;
import br.com.copyimagem.infra.persistence.repositories.CustomerRepository;
import br.com.copyimagem.infra.persistence.repositories.LegalPersonalCustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class LegalPersonalCustomerServiceImpl implements LegalPersonalCustomerService {

    private final LegalPersonalCustomerRepository legalPersonalCustomerRepository;

    private final AddressRepository addressRepository;

    private final ConvertObjectToObjectDTOService convertObjectToObjectDTOService;

    public LegalPersonalCustomerServiceImpl(
            LegalPersonalCustomerRepository legalPersonalCustomerRepository,
            AddressRepository addressRepository, ConvertObjectToObjectDTOService convertObjectToObjectDTOService) {
        this.legalPersonalCustomerRepository = legalPersonalCustomerRepository;
        this.addressRepository = addressRepository;
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
    public List<LegalPersonalCustomerDTO> findAllLegalPersonalCustomer() {
        log.info("[ INFO ] Finding all LegalPersonalCustomers");
        List<LegalPersonalCustomer> legalPersonalCustumerList = legalPersonalCustomerRepository.findAll();
        return legalPersonalCustumerList.stream()
                .map(convertObjectToObjectDTOService::convertToLegalPersonalCustomerDTO).toList();
    }
    @Transactional
    @Override
    public LegalPersonalCustomerDTO saveLegalPersonalCustomer(LegalPersonalCustomerDTO legalPersonalCustomerDTO) {
        log.info("[ INFO ] Saving LegalPersonalCustomer. {}", legalPersonalCustomerDTO.getClass());
        legalPersonalCustomerDTO.setId(null);
        Address address = addressRepository.save(legalPersonalCustomerDTO.getAddress());
        legalPersonalCustomerDTO.setAddress(address);
        checkEmail(legalPersonalCustomerDTO);
        checkCnpj(legalPersonalCustomerDTO);
        legalPersonalCustomerDTO.setStartContract(LocalDate.now(ZoneId.of("America/Sao_Paulo")));
        LegalPersonalCustomer saveLegalPersonalCustomer = legalPersonalCustomerRepository
                .save(convertObjectToObjectDTOService.convertToLegalPersonalCustomer(legalPersonalCustomerDTO));
        return convertObjectToObjectDTOService.convertToLegalPersonalCustomerDTO(saveLegalPersonalCustomer);
    }

    @Override
    public CustomerResponseDTO findByCnpj(String cnpj) {
        Optional<LegalPersonalCustomer> legalPersonalCustomer = legalPersonalCustomerRepository.findByCnpj(cnpj);
        if (legalPersonalCustomer.isEmpty()) {
            log.error("[ ERROR ] Exception :  {}.", NoSuchElementException.class);
            throw new NoSuchElementException("Customer not found");
        }
        return convertObjectToObjectDTOService.convertToCustomerResponseDTO(legalPersonalCustomer.get());
    }

    private void checkEmail(LegalPersonalCustomerDTO legalPersonalCustomerDTO) {
        log.info("[ INFO ] Checking if the email already exists.");
        if (legalPersonalCustomerRepository.findByPrimaryEmail(
                legalPersonalCustomerDTO.getPrimaryEmail()).isPresent()) {
            log.error("[ ERROR ] Exception :  {}.", DataIntegrityViolationException.class);
            throw new DataIntegrityViolationException("Email already exists!");
        }
    }

    private void checkCnpj(LegalPersonalCustomerDTO legalPersonalCustomerDTO) {
        log.info("[ INFO ] Checking if the CPF already exists.");
        if ( legalPersonalCustomerRepository.findByCnpj(
                legalPersonalCustomerDTO.getCnpj()).isPresent()) {
            log.error("[ ERROR ] Exception :  {}.", DataIntegrityViolationException.class);
            throw new DataIntegrityViolationException("CPF already exists!");
        }
    }
}
