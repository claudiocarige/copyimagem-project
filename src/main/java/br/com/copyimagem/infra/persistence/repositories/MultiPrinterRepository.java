package br.com.copyimagem.infra.persistence.repositories;

import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.domain.enums.MachineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface MultiPrinterRepository extends JpaRepository<MultiPrinter, Integer>{
    boolean existsBySerialNumber(String serialNumber);

    @Transactional
    @Modifying
    @Query("UPDATE MultiPrinter mp SET mp.machineStatus = :status WHERE mp.id = :id")
    MultiPrinter updateMachineStatusById(@Param(value = "id") Integer id, @Param(value="status") MachineStatus status);


    @Transactional
    @Modifying
    @Query("UPDATE MultiPrinter mp SET " +
            "mp.impressionCounter = :counter WHERE mp.id = :id AND :counter > mp.impressionCounter")
    MultiPrinter updateImpressionCounterById(
                         @Param(value = "id") Integer id, @Param(value = "counter") Integer counter);
}
