package br.com.copyimagem.infra.repositories;

import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NaturalPersonCustomerRepository extends JpaRepository<NaturalPersonCustomer, Long>{
    Optional<NaturalPersonCustomer> findByPrimaryEmail(String primaryEmail);
}
