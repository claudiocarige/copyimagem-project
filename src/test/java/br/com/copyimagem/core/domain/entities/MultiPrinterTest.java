package br.com.copyimagem.core.domain.entities;

import br.com.copyimagem.core.domain.enums.MachineStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.copyimagem.core.domain.builders.MultiPrinterBuilder.oneMultiPrinter;
import static org.junit.jupiter.api.Assertions.*;

class MultiPrinterTest {

    private MultiPrinter multiPrinter;

    @BeforeEach
    void setUp() {
        startMultiPrint();
    }
    @Test
    @DisplayName("Must create a valid MultiPrinter")
    void youMustCreateAValidMultiPrinter() {
        assertAll("MultiPrinter",
                () -> assertEquals(1, multiPrinter.getId()),
                () -> assertEquals("HP", multiPrinter.getBrand()),
                () -> assertEquals("LaserJet Pro MFP M227fdw", multiPrinter.getModel()),
                () -> assertEquals("1234567890", multiPrinter.getSerialNumber()),
                () -> assertEquals(1000.0, multiPrinter.getMachineValue()),
                () -> assertEquals(MachineStatus.DISPONIVEL, multiPrinter.getMachineStatus()),
                () -> assertEquals(1L, multiPrinter.getCustomer().getId()),
                () -> assertEquals(MultiPrinter.class, multiPrinter.getClass()),
                () -> assertEquals(1000, multiPrinter.getImpressionCounter())
        );
    }

    private void startMultiPrint(){
        multiPrinter = oneMultiPrinter().now();
        multiPrinter.setId(1);
        multiPrinter.setBrand("HP");
        multiPrinter.setModel("LaserJet Pro MFP M227fdw");
        multiPrinter.setSerialNumber("1234567890");
        multiPrinter.setMachineValue(1000.0);
        multiPrinter.setMachineStatus(MachineStatus.DISPONIVEL);
        multiPrinter.setImpressionCounter(1000);
        LegalPersonalCustomer legalPersonalCustomer = new LegalPersonalCustomer("12.123.123/0001-12");
        legalPersonalCustomer.setId(1L);
        multiPrinter.setCustomer(legalPersonalCustomer);
    }
}