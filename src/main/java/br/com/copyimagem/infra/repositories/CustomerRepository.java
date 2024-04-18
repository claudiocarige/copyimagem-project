package br.com.copyimagem.infra.repositories;

import br.com.copyimagem.core.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
