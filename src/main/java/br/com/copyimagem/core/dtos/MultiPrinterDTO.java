package br.com.copyimagem.core.dtos;

import br.com.copyimagem.core.domain.enums.MachineStatus;
import br.com.copyimagem.core.domain.enums.PrinterType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MultiPrinterDTO {

    private Integer id;
    private String brand;
    private String model;
    private String serialNumber;
    private Double machineValue;
    private MachineStatus machineStatus;
    private Integer impressionCounterInitial;
    private PrinterType printType;
    private Integer impressionCounterNow;
    private String customer_id;
}
