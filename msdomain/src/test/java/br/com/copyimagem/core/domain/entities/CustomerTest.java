package br.com.copyimagem.core.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.copyimagem.core.domain.builders.LegalPersonalCustomerBuilder.oneLegalPersonalCustomer;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @BeforeEach
    void setUp() {}

    @Test
    @DisplayName("You must create customer natural person customer")
    void youMustCreateCustomerNaturalPersonCustomer() {
        NaturalPersonCustomer customer =
                br.com.copyimagem.core.domain.builders.NaturalPersonCustomerBuilder.oneNaturalPersonCustomer()
                        .withCpf("123.456.789.10").nowCustomerPF();
        assertNotNull(customer);
        assertEquals("Claudio Carigé", customer.getClientName());
        assertEquals("123.456.789.10", customer.getCpf());
        assertEquals(NaturalPersonCustomer.class, customer.getClass());
    }
    @Test
    @DisplayName("You must create customer legal person customer")
    void youMustCreateCustomerLegalPersonCustomer() {
        LegalPersonalCustomer customer = oneLegalPersonalCustomer().withCnpj("23.456.789/0001-10").withClientName("CopyImagem").nowCustomerPJ();
        assertNotNull(customer);
        assertEquals("CopyImagem", customer.getClientName());
        assertEquals("23.456.789/0001-10", customer.getCnpj());
        assertEquals(LegalPersonalCustomer.class, customer.getClass());
    }
}