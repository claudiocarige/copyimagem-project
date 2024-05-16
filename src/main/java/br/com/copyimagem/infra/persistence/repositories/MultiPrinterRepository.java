package br.com.copyimagem.infra.persistence.repositories;

import br.com.copyimagem.core.domain.entities.MultiPrinter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MultiPrinterRepository extends JpaRepository<MultiPrinter, Integer>{
}
