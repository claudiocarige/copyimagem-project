package br.com.copyimagem.core.usecases.interfaces;

import br.com.copyimagem.core.dtos.CustomerResponseDTO;


public interface CustomerService {
    CustomerResponseDTO searchCliente(String type, String value);

}
