package br.com.copyimagem.core.domain.entities;

import br.com.copyimagem.core.domain.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MonthlyPaymentTest {

    private MonthlyPayment monthlyPayment;

    @BeforeEach
    void setUp(){
        startMonthlyPayment();
    }

    @Test
    @DisplayName("You must create a valid monthly payment")
    void youMustCreateAValidMonthlyPayment() {

        assertAll("MonthlyPayment",
                () -> assertEquals(1L, monthlyPayment.getId()),
                () -> assertEquals(11, monthlyPayment.getMonthPayment()),
                () -> assertEquals(2022, monthlyPayment.getYearPayment()),
                () -> assertEquals(100.0, monthlyPayment.getMonthlyAmount()),
                () -> assertEquals(10000, monthlyPayment.getImpressionsCounter()),
                () -> assertEquals(1500, monthlyPayment.getQuantityPrints()),
                () -> assertEquals(2035.25, monthlyPayment.getExcessValuePrints()),
                () -> assertEquals(3525.20, monthlyPayment.getAmountPrinter()),
                () -> assertEquals(LocalDate.of(2023,11,30), monthlyPayment.getExpirationDate()),
                () -> assertEquals(LocalDate.of(2022, 12, 10), monthlyPayment.getPaymentDate()),
                () -> assertEquals(PaymentStatus.PENDENTE, monthlyPayment.getPaymentStatus()),
                () -> assertEquals(MonthlyPayment.class, monthlyPayment.getClass()),
                () -> assertEquals(1L, monthlyPayment.getCustomer().getId()),
                () -> assertEquals(MonthlyPayment.class, monthlyPayment.getClass())
        );
    }


    private void startMonthlyPayment() {
        monthlyPayment = new MonthlyPayment();
        monthlyPayment.setId(1L);
        monthlyPayment.setMonthPayment(11);
        monthlyPayment.setYearPayment(2022);
        monthlyPayment.setMonthlyAmount(100.0);
        monthlyPayment.setImpressionsCounter(10000);
        monthlyPayment.setQuantityPrints(1500);
        monthlyPayment.setExcessValuePrints(2035.25);
        monthlyPayment.setAmountPrinter(3525.20);
        monthlyPayment.setExpirationDate(LocalDate.of(2023, 11, 30));
        monthlyPayment.setPaymentDate(LocalDate.of(2022, 12, 10));
        monthlyPayment.setPaymentStatus(PaymentStatus.PENDENTE);
        NaturalPersonCustomer naturalPersonCustomer = new NaturalPersonCustomer("123.456.789-01");
        naturalPersonCustomer.setId(1L);
        monthlyPayment.setCustomer(naturalPersonCustomer);
    }
}