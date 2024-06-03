package br.com.copyimagem.infra.persistence.repositories;

import br.com.copyimagem.core.domain.entities.MonthlyPayment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MonthlyPaymentRepository extends JpaRepository< MonthlyPayment, Long > {
}
