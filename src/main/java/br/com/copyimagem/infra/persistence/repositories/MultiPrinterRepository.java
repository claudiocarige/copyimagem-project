package br.com.copyimagem.infra.persistence.repositories;

import br.com.copyimagem.core.domain.entities.MultiPrinter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MultiPrinterRepository extends JpaRepository<MultiPrinter, Integer>{
}
