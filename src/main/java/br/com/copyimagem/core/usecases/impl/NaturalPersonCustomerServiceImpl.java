package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.interfaces.NaturalPersonCustomerService;
import br.com.copyimagem.infra.repositories.NaturalPersonCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NaturalPersonCustomerServiceImpl implements NaturalPersonCustomerService {

    private final NaturalPersonCustomerRepository naturalPersonCustomerRepository;

    public NaturalPersonCustomerServiceImpl(NaturalPersonCustomerRepository naturalPersonCustomerRepository) {
        this.naturalPersonCustomerRepository = naturalPersonCustomerRepository;
    }

    @Override
    public NaturalPersonCustomer findNaturalPersonCustomerById(Long id) {
        return naturalPersonCustomerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
    }

    @Override
    public List<NaturalPersonCustomer> findAllNaturalPersonCustomer() {
        return naturalPersonCustomerRepository.findAll();
    }

    @Override
    public NaturalPersonCustomer saveNaturalPersonCustomer(NaturalPersonCustomer naturalPersonCustomer) {
        return naturalPersonCustomerRepository.save(naturalPersonCustomer);
    }
}
