package br.com.copyimagem.core.domain.entities;

import br.com.copyimagem.core.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class MonthlyPayment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column( nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer month;
    private Integer year;
    private Double monthlyAmount;
    private Integer impressionsCounter;
    private Integer quantityPrints;
    private Double excessValuePrints;
    private Double amountPrinter;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date expirationDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}