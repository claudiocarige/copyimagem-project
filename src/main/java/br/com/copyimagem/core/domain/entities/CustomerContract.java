package br.com.copyimagem.core.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public static CustomerContract generateBasicContract(){
        CustomerContract customerContract = new CustomerContract();
        basicContract(customerContract);
        return customerContract;
    }

    private static void basicContract(CustomerContract customerContract) {
        customerContract.printingFranchise = 2000;
        customerContract.monthlyAmount = 300.0;
        customerContract.contractTime = 6;
        customerContract.startContract = LocalDate.now();
    }
}