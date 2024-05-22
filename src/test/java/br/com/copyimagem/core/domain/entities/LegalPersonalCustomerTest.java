package br.com.copyimagem.core.domain.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static br.com.copyimagem.core.domain.builders.LegalPersonalCustomerBuilder.oneLegalPersonalCustomer;
import static org.junit.jupiter.api.Assertions.*;

class LegalPersonalCustomerTest {

    private LegalPersonalCustomer legalPersonalCustomer;

    @BeforeEach
    void setUp() {
        startLegalPersonalCustomer();
    }

    @Test
    @DisplayName("You must create a valid legal personal customer")
    void youMustCreateAValidLegalPersonalCustomer() {
        assertAll("LegalPersonalCustomer",
                () -> assertEquals(1L, legalPersonalCustomer.getId()),
                () -> assertEquals("Copy", legalPersonalCustomer.getClientName()),
                () -> assertEquals("12.123.123/0001-12", legalPersonalCustomer.getCnpj()),
                () -> assertEquals(Byte.parseByte("5"), legalPersonalCustomer.getPayDay()),
                () -> assertTrue(legalPersonalCustomer.getEmailList().contains("mail1@mail.com")),
                () -> assertEquals(2, legalPersonalCustomer.getEmailList().size()),
                () -> assertEquals(LegalPersonalCustomer.class, legalPersonalCustomer.getClass()),
                () -> assertEquals(1, legalPersonalCustomer.getMultiPrinterList().get(0).getId()),
                () -> assertEquals(1, legalPersonalCustomer.getMonthlyPaymentList().get(0).getId())
        );
    }

    @Test
    @DisplayName("You must reject a user without a CNPJ")
    void youMustRejectALegalPersonalCustomerWithoutACPF(){
        assertAll("NaturalPersonCustomer NULL CNPJ",
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> oneLegalPersonalCustomer().withCnpj(null).nowCustomerPJ()
        ));
    }

    private void startLegalPersonalCustomer() {
        legalPersonalCustomer = oneLegalPersonalCustomer()
                .withCnpj("12.123.123/0001-12")
                .withClientName("Copy")
                .nowCustomerPJ();
    }
}