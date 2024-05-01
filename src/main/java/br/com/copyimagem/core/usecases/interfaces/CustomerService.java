package br.com.copyimagem.core.usecases.interfaces;

import br.com.copyimagem.core.domain.entities.Customer;
import br.com.copyimagem.core.dtos.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {
    CustomerResponseDTO searchCliente(String type, String value);

}
