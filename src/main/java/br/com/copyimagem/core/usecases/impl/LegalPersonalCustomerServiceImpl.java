package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.Address;
import br.com.copyimagem.core.domain.entities.LegalPersonalCustomer;
import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.dtos.LegalPersonalCustomerDTO;
import br.com.copyimagem.core.exceptions.DataIntegrityViolationException;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.interfaces.LegalPersonalCustomerService;
import br.com.copyimagem.infra.persistence.repositories.AddressRepository;
import br.com.copyimagem.infra.persistence.repositories.LegalPersonalCustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
        log.info("[ INFO ] Finding all LegalPersonalCustomers.");
        List<LegalPersonalCustomer> legalPersonalCustumerList = legalPersonalCustomerRepository.findAll();
        return legalPersonalCustumerList.stream()
                .map(convertObjectToObjectDTOService::convertToLegalPersonalCustomerDTO).toList();
    }
    @Transactional
    @Override
    public LegalPersonalCustomerDTO saveLegalPersonalCustomer(LegalPersonalCustomerDTO legalPersonalCustomerDTO) throws NoSuchMethodException {
        log.info("[ INFO ] Saving LegalPersonalCustomer");
        legalPersonalCustomerDTO.setId(null);
        Address address = addressRepository.save(legalPersonalCustomerDTO.getAddress());
        legalPersonalCustomerDTO.setAddress(address);
        validateCnpjAndEmail(legalPersonalCustomerDTO);
        legalPersonalCustomerDTO.setStartContract(LocalDate.now(ZoneId.of("America/Sao_Paulo")));
        LegalPersonalCustomer saveLegalPersonalCustomer = legalPersonalCustomerRepository
                .save(convertObjectToObjectDTOService.convertToLegalPersonalCustomer(legalPersonalCustomerDTO));
        return convertObjectToObjectDTOService.convertToLegalPersonalCustomerDTO(saveLegalPersonalCustomer);
    }

    @Override
    public CustomerResponseDTO findByCnpj(String cnpj) {
        log.info("[ INFO ] Finding LegalPersonalCustomer by CNPJ.");
        Optional<LegalPersonalCustomer> legalPersonalCustomer = legalPersonalCustomerRepository.findByCnpj(cnpj);
        if (legalPersonalCustomer.isEmpty()) {
            log.error("[ ERROR ] Exception : Customer not found :  {}.", NoSuchElementException.class);
            throw new NoSuchElementException("Customer not found");
        }
        return convertObjectToObjectDTOService.convertToCustomerResponseDTO(legalPersonalCustomer.get());
    }


    private void validateCnpjAndEmail(LegalPersonalCustomerDTO legalPersonalCustomerDTO) throws NoSuchMethodException {
        validateField(legalPersonalCustomerDTO.getCnpj(),
                       legalPersonalCustomerRepository::findByCnpj,
                 "CNPJ already exists!");
        validateField(legalPersonalCustomerDTO.getPrimaryEmail(),
                        legalPersonalCustomerRepository::findByPrimaryEmail,
                  "Email already exists!");
    }

    private <T> void validateField(
            T field, Function<T, Optional<LegalPersonalCustomer>> buscaRepositorio, String message) throws NoSuchMethodException {
        log.info("[ INFO ] Checking if the " + field.toString().toUpperCase() + " " + buscaRepositorio.getClass().getMethod("apply", Object.class).getParameters()[0].getName() + " already exists.");
        if(buscaRepositorio.apply(field).isPresent()){
            log.error("[ ERROR ] Exception : {} : {}.", message, DataIntegrityViolationException.class);
            throw new DataIntegrityViolationException(message);
        }
    }
}
