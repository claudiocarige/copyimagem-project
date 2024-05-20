package br.com.copyimagem.infra.persistence.repositories;

import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.domain.enums.MachineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface MultiPrinterRepository extends JpaRepository<MultiPrinter, Integer>{
    boolean existsBySerialNumber(String serialNumber);

    @Transactional
    @Modifying
    @Query("UPDATE MultiPrinter mp SET mp.machineStatus = :status WHERE mp.id = :id")
    MultiPrinter updateMachineStatusById(Integer id, MachineStatus status);
}
