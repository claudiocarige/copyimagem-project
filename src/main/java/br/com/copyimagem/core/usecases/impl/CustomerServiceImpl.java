package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.Customer;
import br.com.copyimagem.core.domain.entities.LegalPersonalCustomer;
import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import br.com.copyimagem.core.domain.enums.FinancialSituation;
import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.dtos.LegalPersonalCustomerDTO;
import br.com.copyimagem.core.dtos.NaturalPersonCustomerDTO;
import br.com.copyimagem.core.dtos.UpdateCustomerDTO;
import br.com.copyimagem.core.exceptions.IllegalArgumentException;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.interfaces.CustomerService;
import br.com.copyimagem.core.usecases.interfaces.LegalPersonalCustomerService;
import br.com.copyimagem.core.usecases.interfaces.NaturalPersonCustomerService;
import br.com.copyimagem.infra.persistence.repositories.CustomerRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
                //TODO Test and Case - Search by clientName
                //TODO Test and Case - Search by clientPhone
                default -> throw new IllegalArgumentException("Parameter [ " + typeParam + " ] type not accepted.");
            };
    }

    @Override
    public List<CustomerResponseDTO> searchClientAll() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream()
                .map(convertObjectToObjectDTOService::convertToCustomerResponseDTO).toList();
    }

    @Override
    public List<CustomerResponseDTO> searchFinancialSituation(String situation) {
        FinancialSituation financialSituation = FinancialSituation.valueOf(situation);
        List<Customer> customerList = customerRepository.findAllByFinancialSituation(financialSituation);
        return customerList.stream().
                map(convertObjectToObjectDTOService::convertToCustomerResponseDTO).toList();
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

    @Override
    public UpdateCustomerDTO updateCustomerAttribute(String attribute, String value, Long id) {
        log.info("[ INFO ] Updating LegalPersonalCustomer attribute : {}", attribute);
        isNotNull(attribute, value);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        isContainsAnyOfTheAttributes(attribute);
        needsValidations(attribute, value);
        return getUpdateCustomerAttribute(attribute, value, customer);
    }

    private void isNotNull(String attribute, String value){
        String [] attributes = {"cpf", "cnpj", "primaryEmail", "phoneNumber", "clientName", "whatsapp"};
        for (String attribute1 : attributes) {
            if(attribute1.equals(attribute)){
                if(value == null){
                    throw new IllegalArgumentException(attribute.toUpperCase() +" cannot be null.");
                }
            }
        }
    }

    private UpdateCustomerDTO getUpdateCustomerAttribute(String attribute, String value, Customer customer) {
        switch (attribute) {
            case "cpf" -> {
                NaturalPersonCustomer naturalPersonCustomer = (NaturalPersonCustomer) customer;
                naturalPersonCustomer.setCpf(value);
                return convertObjectToObjectDTOService
                        .convertToUpdateCustomerDTO(customerRepository.save(naturalPersonCustomer));
            }
            case "cnpj" -> {
                LegalPersonalCustomer legalPersonalCustomer = (LegalPersonalCustomer) customer;
                legalPersonalCustomer.setCnpj(value);
                return convertObjectToObjectDTOService
                        .convertToUpdateCustomerDTO(customerRepository.save(legalPersonalCustomer));
            }
            case "primaryEmail" -> {
                customer.setPrimaryEmail(value);
                return convertObjectToObjectDTOService
                        .convertToUpdateCustomerDTO(customerRepository.save(customer));
            }
            case "phoneNumber" -> {
                customer.setPhoneNumber(value);
                return convertObjectToObjectDTOService
                        .convertToUpdateCustomerDTO(customerRepository.save(customer));
            }
            case "clientName" -> {
                customer.setClientName(value);
                return convertObjectToObjectDTOService
                        .convertToUpdateCustomerDTO(customerRepository.save(customer));
            }
            case "whatsapp" -> {
                customer.setWhatsapp(value);
                return convertObjectToObjectDTOService
                        .convertToUpdateCustomerDTO(customerRepository.save(customer));
            }
            case "bankCode" -> {
                customer.setBankCode(value);
                return convertObjectToObjectDTOService
                        .convertToUpdateCustomerDTO(customerRepository.save(customer));
            }
            case "financialSituation" -> {
                customer.setFinancialSituation(FinancialSituation.valueOf(value));
                return convertObjectToObjectDTOService
                        .convertToUpdateCustomerDTO(customerRepository.save(customer));
            }
            case "payDay" -> {
                customer.setPayDay(Byte.parseByte(value));
                return convertObjectToObjectDTOService
                        .convertToUpdateCustomerDTO(customerRepository.save(customer));
            }
            case "startContract" -> {
                customer.setStartContract(LocalDate.parse(value));
                return convertObjectToObjectDTOService
                        .convertToUpdateCustomerDTO(customerRepository.save(customer));
            }
            default -> throw new IllegalArgumentException("Attribute not found.");
        }
    }

    private void needsValidations(String attribute,String value) {
        if(List.of("cpf", "cnpj", "primaryEmail").contains(attribute)){
            validateAttribute(attribute, value);
        }
    }

    private void isContainsAnyOfTheAttributes(String attribute) {
        if(List.of("emailList", "multiPrinterList", "monthlyPaymentList").contains(attribute)) {
            throw new IllegalArgumentException("This attribute cannot be changed on this endpoint.");
        }
    }

    private void validateAttribute(String attribute, String value) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        switch (attribute) {
            case "cnpj" -> {
                Set<ConstraintViolation<LegalPersonalCustomerDTO>> cnpjViolations = validator.validateValue(LegalPersonalCustomerDTO.class, attribute, value);
                if (!cnpjViolations.isEmpty()) {
                    throw new IllegalArgumentException(cnpjViolations.iterator().next().getMessage());
                }
            }
            case "primaryEmail" -> {
                Set<ConstraintViolation<NaturalPersonCustomerDTO>> emailViolations = validator.validateValue(NaturalPersonCustomerDTO.class, attribute, value);
                log.info("[ INFO ] emailViolations : {}", emailViolations);
                if (!emailViolations.isEmpty()) {
                    throw new IllegalArgumentException(emailViolations.iterator().next().getMessage());
                }
            }
            case "cpf" -> {
                Set<ConstraintViolation<NaturalPersonCustomerDTO>> cpfViolations = validator.validateValue(NaturalPersonCustomerDTO.class, attribute, value);
                if (!cpfViolations.isEmpty()) {
                    throw new IllegalArgumentException(cpfViolations.iterator().next().getMessage());
                }
            }
        }
    }

    private CustomerResponseDTO findByCnpj(String valueParam) {
        return legalPersonalCustomerService.findByCnpj(valueParam);
    }

    private CustomerResponseDTO findByCpf(String valueParam) {
        return naturalPersonCustomerService.findByCpf(valueParam);
    }

}