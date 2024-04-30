package br.com.copyimagem.core.usecases.interfaces;

import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.dtos.LegalPersonalCustomerDTO;

import java.util.List;

public interface LegalPersonalCustomerService {

    List<LegalPersonalCustomerDTO> findAllLegalPersonalCustomer();
    LegalPersonalCustomerDTO findLegalPersonalCustomerById(Long id);
    LegalPersonalCustomerDTO saveLegalPersonalCustomer(LegalPersonalCustomerDTO legalPersonalCustomer);
    CustomerResponseDTO findByCnpj(String cnpj);
}
