package br.com.copyimagem.core.dtos;

import br.com.copyimagem.core.domain.entities.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class MonthlyPaymentDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer monthPayment;
    private Integer yearPayment;
    private Double monthlyAmount;
    private Integer impressionsCounter;
    private Integer quantityPrints;
    private Double excessValuePrints;
    private Double amountPrinter;
    private String invoiceNumber;
    private String ticketNumber;
    private Integer printingFranchise;
    private Double rateExcessColorPrinting;
    private Double rateExcessBlackAndWhitePrinting;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate paymentDate;
    private String paymentStatus;
    private Long customerId;
}
