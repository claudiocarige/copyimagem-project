package br.com.copyimagem.core.dtos;

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

    private Integer quantityPrintsPB;

    private Integer quantityPrintsColor;

    private Integer printingFranchisePB;

    private Integer printingFranchiseColor;

    private String invoiceNumber;

    private String ticketNumber;

    private Double amountPrinter;

    private Double monthlyAmount;

    private Double excessValuePrintsPB;

    private Double excessValuePrintsColor;

    private Double rateExcessColorPrinting;

    private Double rateExcessBlackAndWhitePrinting;

    @JsonFormat( pattern = "dd-MM-yyyy" )
    private LocalDate expirationDate;

    @JsonFormat( pattern = "dd-MM-yyyy" )
    private LocalDate paymentDate;

    private String paymentStatus;

    private Long customerId;

}
