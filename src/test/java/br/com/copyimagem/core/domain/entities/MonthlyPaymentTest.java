package br.com.copyimagem.core.domain.entities;

import br.com.copyimagem.core.domain.enums.PaymentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static br.com.copyimagem.core.domain.builders.MonthlyPaymentBuilder.oneMonthlyPayment;
import static org.junit.jupiter.api.Assertions.*;

class MonthlyPaymentTest {

    private MonthlyPayment monthlyPayment;

    @Test
    @DisplayName("You must create a valid monthly payment")
    void youMustCreateAValidMonthlyPayment() {
        monthlyPayment = oneMonthlyPayment().now();
        assertAll("MonthlyPayment",
                () -> assertEquals(1L, monthlyPayment.getId()),
                () -> assertEquals(1, monthlyPayment.getMonthPayment()),
                () -> assertEquals(2024, monthlyPayment.getYearPayment()),
                () -> assertEquals(200.0, monthlyPayment.getMonthlyAmount()),
                () -> assertEquals(1000, monthlyPayment.getImpressionsCounter()),
                () -> assertEquals(2000, monthlyPayment.getQuantityPrints()),
                () -> assertEquals(2035.25, monthlyPayment.getExcessValuePrints()),
                () -> assertEquals(3525.20, monthlyPayment.getAmountPrinter()),
                () -> assertEquals(LocalDate.of(2023,11,30), monthlyPayment.getExpirationDate()),
                () -> assertEquals(LocalDate.of(2022, 12, 10), monthlyPayment.getPaymentDate()),
                () -> assertEquals(PaymentStatus.PENDENTE, monthlyPayment.getPaymentStatus()),
                () -> assertEquals(MonthlyPayment.class, monthlyPayment.getClass()),
                () -> assertEquals(2000, monthlyPayment.getPrintingFranchise()),
                () -> assertEquals("0001", monthlyPayment.getInvoiceNumber())
        );
    }
}