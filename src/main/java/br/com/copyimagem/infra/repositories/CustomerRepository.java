package br.com.copyimagem.infra.repositories;

import br.com.copyimagem.core.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPrimaryEmail(String email);
}
