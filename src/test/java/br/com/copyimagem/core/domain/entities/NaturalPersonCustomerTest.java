package br.com.copyimagem.core.domain.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.copyimagem.core.domain.builders.CustomerBuilder.oneCustomer;
import static org.junit.jupiter.api.Assertions.*;

class NaturalPersonCustomerTest {

    private NaturalPersonCustomer naturalPersonCustomer;

    @BeforeEach
    void setUp(){
        startNaturalPersonCustomer();

    }

    @Test
    @DisplayName("You must create a valid natural person customer")
    void youMustCreateAValidNaturalPersonCustomer() {

        assertAll("NaturalPersonCustomer",
                () -> assertEquals(1L, naturalPersonCustomer.getId()),
                () -> assertEquals("Claudio Carigé", naturalPersonCustomer.getClientName()),
                () -> assertEquals("123.456.789-01", naturalPersonCustomer.getCpf()),
                () -> assertEquals(Byte.parseByte("5"), naturalPersonCustomer.getPayDay()),
                () -> assertEquals(2, naturalPersonCustomer.getEmailList().size()),
                () -> assertEquals(NaturalPersonCustomer.class, naturalPersonCustomer.getClass())
        );
    }

    @Test
    @DisplayName("You must reject a user without a CPF")
    void youMustRejectANaturalPersonCustomerWithoutACPF(){
        assertAll("NaturalPersonCustomer NULL CPF",
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> oneCustomer().withCpf(null).nowCustomerPF()));
    }

    private void startNaturalPersonCustomer() {
        naturalPersonCustomer = oneCustomer().withCpf("123.456.789-01").nowCustomerPF();
    }
}
