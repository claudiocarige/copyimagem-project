package br.com.copyimagem.core.domain.builders;
import br.com.copyimagem.core.domain.entities.Customer;
import java.time.LocalDate;
import br.com.copyimagem.core.domain.enums.PaymentStatus;
import br.com.copyimagem.core.domain.entities.MonthlyPayment;

public class MonthlyPaymentBuilder {
    private Long id;
    private Integer monthPayment;
    private Integer yearPayment;
    private Double monthlyAmount;
    private Integer impressionsCounter;
    private Integer quantityPrints;
    private Double excessValuePrints;
    private Double amountPrinter;
    private LocalDate expirationDate;
    private LocalDate paymentDate;
    private PaymentStatus paymentStatus;
    private Customer customer;
    private String invoiceNumber;
    private String ticketNumber;
    private Integer printingFranchise;

    private MonthlyPaymentBuilder(){}

    public static MonthlyPaymentBuilder oneMonthlyPayment() {
        MonthlyPaymentBuilder builder = new MonthlyPaymentBuilder();
        initializeDefaultData(builder);
        return builder;
    }

    private static void initializeDefaultData(MonthlyPaymentBuilder builder) {
        builder.id = 1L;
        builder.monthPayment = 1;
        builder.yearPayment = 2024;
        builder.monthlyAmount = 200.0;
        builder.impressionsCounter = 1000;
        builder.quantityPrints = 2000;
        builder.excessValuePrints = 20.0;
        builder.amountPrinter = 220.0;
        builder.expirationDate = LocalDate.of(2022, 2, 1);
        builder.paymentDate = LocalDate.of(2022, 1, 1);
        builder.paymentStatus = PaymentStatus.PAGO;
        builder.printingFranchise = 2000;
        builder.invoiceNumber = "0001";
        builder.ticketNumber = "0002";
    }

    public MonthlyPaymentBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public MonthlyPaymentBuilder withMonthPayment(Integer monthPayment) {
        this.monthPayment = monthPayment;
        return this;
    }

    public MonthlyPaymentBuilder withYearPayment(Integer yearPayment) {
        this.yearPayment = yearPayment;
        return this;
    }

    public MonthlyPaymentBuilder withMonthlyAmount(Double monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
        return this;
    }

    public MonthlyPaymentBuilder withImpressionsCounter(Integer impressionsCounter) {
        this.impressionsCounter = impressionsCounter;
        return this;
    }

    public MonthlyPaymentBuilder withQuantityPrints(Integer quantityPrints) {
        this.quantityPrints = quantityPrints;
        return this;
    }

    public MonthlyPaymentBuilder withExcessValuePrints(Double excessValuePrints) {
        this.excessValuePrints = excessValuePrints;
        return this;
    }

    public MonthlyPaymentBuilder withAmountPrinter(Double amountPrinter) {
        this.amountPrinter = amountPrinter;
        return this;
    }

    public MonthlyPaymentBuilder withExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public MonthlyPaymentBuilder withPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public MonthlyPaymentBuilder withPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public MonthlyPaymentBuilder withPrintingFranchise(Integer printingFranchise) {
        this.printingFranchise = printingFranchise;
        return this;
    }
    public MonthlyPaymentBuilder withInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }
    public MonthlyPaymentBuilder withTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
        return this;
    }


    public MonthlyPaymentBuilder withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public MonthlyPayment now() {
        return new MonthlyPayment(
                id, monthPayment, yearPayment, monthlyAmount, impressionsCounter,
                quantityPrints, excessValuePrints, amountPrinter, expirationDate,
                paymentDate, paymentStatus, printingFranchise, invoiceNumber, ticketNumber ,customer);
    }
}
