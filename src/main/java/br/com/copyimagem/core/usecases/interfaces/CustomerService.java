package br.com.copyimagem.core.usecases.interfaces;

import br.com.copyimagem.core.domain.entities.CustomerContract;
import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.dtos.UpdateCustomerDTO;

import java.util.List;


public interface CustomerService {
    CustomerResponseDTO searchCustomer(String type, String value);

    List<CustomerResponseDTO> searchAllCustomers();

    List<CustomerResponseDTO> searchFinancialSituation(String situation);

    CustomerContract getCustomerContract(Long id);

    UpdateCustomerDTO updateCustomerAttribute(String attribute, String value, Long id);


}