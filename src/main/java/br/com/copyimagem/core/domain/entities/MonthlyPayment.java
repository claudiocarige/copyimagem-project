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
                          Double amountPrinter, String invoiceNumber,
                          String ticketNumber, Integer printingFranchise,
                          LocalDate expirationDate, LocalDate paymentDate,
                          PaymentStatus paymentStatus, Customer customer) {
        this.id = id;
        this.monthPayment = monthPayment;
        this.yearPayment = yearPayment;
        this.monthlyAmount = monthlyAmount;
        this.impressionsCounter = impressionsCounter;
        this.quantityPrints = quantityPrints;
        this.excessValuePrints = excessValuePrints;
        this.amountPrinter = amountPrinter;
        this.invoiceNumber = invoiceNumber;
        this.ticketNumber = ticketNumber;
        this.printingFranchise = printingFranchise;
        this.expirationDate = expirationDate;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.customer = customer;
    }

}