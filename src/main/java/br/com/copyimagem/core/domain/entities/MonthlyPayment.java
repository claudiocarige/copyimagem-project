package br.com.copyimagem.core.domain.entities;

import br.com.copyimagem.core.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

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
    private Integer monthPayment;
    private Integer yearPayment;
    private Double monthlyAmount;
    private Integer impressionsCounter;
    private Integer quantityPrints;
    private Double excessValuePrints;
    private Double amountPrinter;
    //new attributes
    private String invoiceNumber;
    private String ticketNumber;
    private Integer printingFranchise;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public MonthlyPayment(Long id, Integer monthPayment, Integer yearPayment,
                          Double monthlyAmount, Integer impressionsCounter,
                          Integer quantityPrints, Double excessValuePrints,
                          Double amountPrinter, LocalDate expirationDate,
                          LocalDate paymentDate, PaymentStatus paymentStatus,
                          Integer printingFranchise, String invoiceNumber,
                          String ticketNumber, Customer customer) {
    }
}