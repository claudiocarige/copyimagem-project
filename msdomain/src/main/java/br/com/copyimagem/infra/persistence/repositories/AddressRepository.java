package br.com.copyimagem.infra.persistence.repositories;

import br.com.copyimagem.core.domain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository< Address, Long > {
}
