package br.com.copyimagem.core.usecases.interfaces;

import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.dtos.UpdateCustomerDTO;

import java.util.List;


public interface CustomerService {
    CustomerResponseDTO searchCliente(String type, String value);

    List<CustomerResponseDTO> searchClientAll();

    List<CustomerResponseDTO> searchFinancialSituation(String situation);

    UpdateCustomerDTO updateCustomerAttribute(String attribute, String value, Long id);

}