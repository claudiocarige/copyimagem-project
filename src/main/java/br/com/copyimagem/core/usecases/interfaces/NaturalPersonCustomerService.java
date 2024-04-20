package br.com.copyimagem.core.usecases.interfaces;

import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;

import java.util.List;

public interface NaturalPersonCustomerService {

    List<NaturalPersonCustomer> findAllNaturalPersonCustomer();
    NaturalPersonCustomer findNaturalPersonCustomerById(Long id);
    NaturalPersonCustomer saveNaturalPersonCustomer(NaturalPersonCustomer naturalPersonCustomer);
}
