package br.com.copyimagem.core.domain.entities;

import br.com.copyimagem.core.domain.enums.MachineStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MultiPrinter implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String brand;
    private String model;
    @Column(unique = true)
    private String serialNumber;
    private Double machineValue;
    @Enumerated(EnumType.STRING)
    private MachineStatus machineStatus;
    private Integer impressionCounter;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}