package br.com.copyimagem.infra.repositories;

import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NaturalPersonCustomerRepository extends JpaRepository<NaturalPersonCustomer, Long>{
}
