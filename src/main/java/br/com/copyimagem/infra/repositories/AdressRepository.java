package br.com.copyimagem.infra.repositories;

import br.com.copyimagem.core.domain.entities.Adress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepository extends JpaRepository<Adress, Long>{
}
