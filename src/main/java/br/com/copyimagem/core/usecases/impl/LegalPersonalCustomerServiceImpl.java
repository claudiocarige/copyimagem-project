package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.Adress;
import br.com.copyimagem.core.domain.entities.LegalPersonalCustomer;
import br.com.copyimagem.core.dtos.LegalPersonalCustomerDTO;
import br.com.copyimagem.core.exceptions.DataIntegrityViolationException;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.interfaces.LegalPersonalCustomerService;
import br.com.copyimagem.infra.repositories.AdressRepository;
import br.com.copyimagem.infra.repositories.LegalPersonalCustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class LegalPersonalCustomerServiceImpl implements LegalPersonalCustomerService {

    private final LegalPersonalCustomerRepository legalPersonalCustomerRepository;

    private final AdressRepository adressRepository;

    private final ConvertObjectToObjectDTOService convertObjectToObjectDTOService;

    public LegalPersonalCustomerServiceImpl(
            LegalPersonalCustomerRepository legalPersonalCustomerRepository,
            AdressRepository adressRepository, ConvertObjectToObjectDTOService convertObjectToObjectDTOService) {
        this.legalPersonalCustomerRepository = legalPersonalCustomerRepository;
        this.adressRepository = adressRepository;
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
    @Override
    public LegalPersonalCustomerDTO saveLegalPersonalCustomer(LegalPersonalCustomerDTO legalPersonalCustomerDTO) {
        log.info("[ INFO ] Saving LegalPersonalCustomer. {}", legalPersonalCustomerDTO.getClass());
        legalPersonalCustomerDTO.setId(null);
        Adress adress = adressRepository.save(legalPersonalCustomerDTO.getAdress());
        checkEmail(legalPersonalCustomerDTO);
        checkCnpj(legalPersonalCustomerDTO);
        LegalPersonalCustomer saveLegalPersonalCustomer = legalPersonalCustomerRepository
                .save(convertObjectToObjectDTOService.convertToLegalPersonalCustomer(legalPersonalCustomerDTO));
        saveLegalPersonalCustomer.setAdress(adress);
        return convertObjectToObjectDTOService.convertToLegalPersonalCustomerDTO(saveLegalPersonalCustomer);
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
