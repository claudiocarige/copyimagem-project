package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import br.com.copyimagem.core.exceptions.DataIntegrityViolationException;
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
        naturalPersonCustomer.setId(null);
        checkEmail(naturalPersonCustomer);
        checkCpf(naturalPersonCustomer);
        return naturalPersonCustomerRepository.save(naturalPersonCustomer);
    }
    private void checkEmail(NaturalPersonCustomer naturalPersonCustomer) {
        Optional<NaturalPersonCustomer> customer = naturalPersonCustomerRepository.findByPrimaryEmail(
                naturalPersonCustomer.getPrimaryEmail());
        if (customer.isPresent() && customer.get().getPrimaryEmail().equals(naturalPersonCustomer.getPrimaryEmail())) {
            throw new DataIntegrityViolationException(String.format("Email %s already exists!", naturalPersonCustomer.getPrimaryEmail()));
        }
    }

    private void checkCpf(NaturalPersonCustomer naturalPersonCustomer) {
        Optional<NaturalPersonCustomer> customer = naturalPersonCustomerRepository.findByCpf(
                naturalPersonCustomer.getCpf());
        if (customer.isPresent() && customer.get().getCpf().equals(naturalPersonCustomer.getCpf())) {
            throw new DataIntegrityViolationException(String.format("CPF %s already exists!", naturalPersonCustomer.getCpf()));
        }
    }
}
