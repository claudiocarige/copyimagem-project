package br.com.copyimagem.infra.repositories;

import br.com.copyimagem.core.domain.entities.LegalPersonalCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalPersonalCustomerRepository extends JpaRepository<LegalPersonalCustomer, Long>{
}
