package br.com.copyimagem.core.domain.builders;

import br.com.copyimagem.core.domain.entities.Customer;
import br.com.copyimagem.core.domain.enums.MachineStatus;
import br.com.copyimagem.core.domain.entities.MultiPrinter;

public class MultiPrinterBuilder {
    private Integer id;
    private String brand;
    private String model;
    private String serialNumber;
    private Double machineValue;
    private MachineStatus machineStatus;

    private Integer impressionCounter;
    private Customer customer;

    private MultiPrinterBuilder(){}

    public static MultiPrinterBuilder oneMultiPrinter() {
        MultiPrinterBuilder builder = new MultiPrinterBuilder();
        initializeDefaultData(builder);
        return builder;
    }

    private static void initializeDefaultData(MultiPrinterBuilder builder) {
        builder.id = 1;
        builder.brand = "Epson";
        builder.model = "L5290";
        builder.serialNumber = "x1x2x3";
        builder.machineValue = 1000.0;
        builder.machineStatus = MachineStatus.DISPONIVEL;
        builder.impressionCounter = 1000;
    }


    public MultiPrinterBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public MultiPrinterBuilder withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public MultiPrinterBuilder withModel(String model) {
        this.model = model;
        return this;
    }

    public MultiPrinterBuilder withSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public MultiPrinterBuilder withMachineValue(Double machineValue) {
        this.machineValue = machineValue;
        return this;
    }

    public MultiPrinterBuilder withMachineStatus(MachineStatus machineStatus) {
        this.machineStatus = machineStatus;
        return this;
    }

    public MultiPrinterBuilder withImpressionCounter(Integer impressionCounter) {
        this.impressionCounter = impressionCounter;
        return this;
    }

    public MultiPrinterBuilder withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public MultiPrinter now() {
        return new MultiPrinter(id, brand, model, serialNumber, machineValue, machineStatus, impressionCounter, customer);
    }
}