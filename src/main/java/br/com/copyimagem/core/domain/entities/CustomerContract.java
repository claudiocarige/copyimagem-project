package br.com.copyimagem.core.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class CustomerContract implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer printingFranchise;
    private Double monthlyAmount;
    private Short contractTime;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startContract;

    public CustomerContract(){
        basicContract();
    }

    private void basicContract() {
        this.printingFranchise = 2000;
        this.monthlyAmount = 300.0;
        this.contractTime = 6;
        this.startContract = LocalDate.now();
    }
}
