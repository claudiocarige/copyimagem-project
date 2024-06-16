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

    private PrinterType printType;

    private Integer impressionCounterInitial;

    private Integer impressionCounterBefore;

    private Integer impressionCounterNow;

    private Integer printingFranchise;

    private Double amountPrinter;

    private Double monthlyPrinterAmount;

    private String customer_id;

    public int sumQuantityPrints() {

        if( this.impressionCounterBefore != null ) {
            var sum = this.impressionCounterNow - this.impressionCounterBefore;
            this.impressionCounterBefore = this.impressionCounterNow;
            return sum;
        } else if(this.impressionCounterNow != null){
            return this.impressionCounterNow - this.impressionCounterInitial;
        }
        return 0;
    }


}
