package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.Adress;
import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.dtos.NaturalPersonCustomerDTO;
import br.com.copyimagem.core.exceptions.DataIntegrityViolationException;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.interfaces.NaturalPersonCustomerService;
import br.com.copyimagem.infra.persistence.repositories.AdressRepository;
import br.com.copyimagem.infra.persistence.repositories.NaturalPersonCustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class NaturalPersonCustomerServiceImpl implements NaturalPersonCustomerService {

    private final NaturalPersonCustomerRepository naturalPersonCustomerRepository;
    private final AdressRepository adressRepository;

    private final ConvertObjectToObjectDTOService convertObjectToObjectDTOService;

    public NaturalPersonCustomerServiceImpl(
            NaturalPersonCustomerRepository naturalPersonCustomerRepository,
            AdressRepository adressRepository, ConvertObjectToObjectDTOService convertObjectToObjectDTOService) {
        this.naturalPersonCustomerRepository = naturalPersonCustomerRepository;
        this.adressRepository = adressRepository;
        this.convertObjectToObjectDTOService = convertObjectToObjectDTOService;
    }

    @Override
    public NaturalPersonCustomerDTO findNaturalPersonCustomerById(Long id) {
        log.info("[ INFO ] Finding customer by id: {}", id);
        NaturalPersonCustomer naturalPersonCustomer = naturalPersonCustomerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        return convertObjectToObjectDTOService.convertToNaturalPersonCustomerDTO(naturalPersonCustomer);
    }

    @Override
    public List<NaturalPersonCustomerDTO> findAllNaturalPersonCustomer() {
        log.info("[ INFO ] Finding all customers");
        List<NaturalPersonCustomer> custumerList = naturalPersonCustomerRepository.findAll();
        return custumerList.stream().map(convertObjectToObjectDTOService::convertToNaturalPersonCustomerDTO).toList();
    }

    @Transactional
    @Override
    public NaturalPersonCustomerDTO saveNaturalPersonCustomer(NaturalPersonCustomerDTO naturalPersonCustomerDTO) {
        log.info("[ INFO ] Saving customer. {}", naturalPersonCustomerDTO.getClass());
        naturalPersonCustomerDTO.setId(null);
        Adress adress = adressRepository.save(naturalPersonCustomerDTO.getAdress());
        naturalPersonCustomerDTO.setAdress(adress);
        checkEmail(naturalPersonCustomerDTO);
        checkCpf(naturalPersonCustomerDTO);
        NaturalPersonCustomer savedNaturalCustomer = naturalPersonCustomerRepository.save(convertObjectToObjectDTOService.convertToNaturalPersonCustomer(naturalPersonCustomerDTO));
        return convertObjectToObjectDTOService.convertToNaturalPersonCustomerDTO(savedNaturalCustomer);
    }

    @Override
    public CustomerResponseDTO findByCpf(String cpf) {
        Optional<NaturalPersonCustomer> naturalPersonCustomer = naturalPersonCustomerRepository.findByCpf(cpf);
            if (naturalPersonCustomer.isEmpty()) {
                log.error("[ ERROR ] Exception :  {}.", NoSuchElementException.class);
                throw new NoSuchElementException("Customer not found");
            }
        return convertObjectToObjectDTOService.convertToCustomerResponseDTO(naturalPersonCustomer.get());
    }

    private void checkEmail(NaturalPersonCustomerDTO naturalPersonCustomerDTO) {
        log.info("[ INFO ] Checking if the email already exists.");
        if (naturalPersonCustomerRepository.findByPrimaryEmail(
                naturalPersonCustomerDTO.getPrimaryEmail()).isPresent()) {
            log.error("[ ERROR ] Exception :  {}.", DataIntegrityViolationException.class);
            throw new DataIntegrityViolationException("Email already exists!");
        }
    }

    private void checkCpf(NaturalPersonCustomerDTO naturalPersonCustomerDTO) {
        log.info("[ INFO ] Checking if the CPF already exists.");
        if ( naturalPersonCustomerRepository.findByCpf(
                naturalPersonCustomerDTO.getCpf()).isPresent()) {
            log.error("[ ERROR ] Exception :  {}.", DataIntegrityViolationException.class);
            throw new DataIntegrityViolationException("CPF already exists!");
        }
    }
}
