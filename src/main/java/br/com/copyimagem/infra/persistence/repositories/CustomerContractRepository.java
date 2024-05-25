package br.com.copyimagem.infra.persistence.repositories;

import br.com.copyimagem.core.domain.entities.CustomerContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerContractRepository extends JpaRepository<CustomerContract, Long>{
}
