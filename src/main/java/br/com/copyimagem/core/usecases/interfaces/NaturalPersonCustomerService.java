package br.com.copyimagem.core.usecases.interfaces;

import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.dtos.NaturalPersonCustomerDTO;

import java.util.List;

public interface NaturalPersonCustomerService {

    List<NaturalPersonCustomerDTO> findAllNaturalPersonCustomer();
    NaturalPersonCustomerDTO findNaturalPersonCustomerById(Long id);
    NaturalPersonCustomerDTO saveNaturalPersonCustomer(NaturalPersonCustomerDTO naturalPersonCustomer);
    CustomerResponseDTO findByCpf(String cpf);
}
