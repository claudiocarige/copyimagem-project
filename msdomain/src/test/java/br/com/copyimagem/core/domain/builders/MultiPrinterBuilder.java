package br.com.copyimagem.core.domain.builders;

import br.com.copyimagem.core.domain.entities.Customer;
import br.com.copyimagem.core.domain.enums.MachineStatus;
import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.domain.enums.PrinterType;

public class MultiPrinterBuilder {
    private Integer id;
    private String brand;
    private String model;
    private String serialNumber;
    private Double machineValue;
    private MachineStatus machineStatus;
    private Integer impressionCounterInitial;
    private PrinterType printType;
    private Integer impressionCounterNow;
    private Integer printingFranchise;
    private Double amountPrinter;
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
        builder.impressionCounterInitial = 1000;
        builder.printType = PrinterType.LASER_COLOR_EASY;
        builder.impressionCounterNow = 10000;
        builder.printingFranchise = 2000;
        builder.amountPrinter = 3525.20;
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

    public MultiPrinterBuilder withImpressionCounterInitial(Integer impressionCounterInitial) {
        this.impressionCounterInitial = impressionCounterInitial;
        return this;
    }
    public MultiPrinterBuilder withPrintType(PrinterType printType) {
        this.printType = printType;
        return this;
    }

    public MultiPrinterBuilder withImpressionCounterNow(Integer impressionCounterNow) {
        this.impressionCounterNow = impressionCounterNow;
        return this;
    }

    public MultiPrinterBuilder withPrintingFranchise(Integer printingFranchise) {
        this.printingFranchise = printingFranchise;
        return this;
    }

    public MultiPrinterBuilder withAmountPrinter(Double amountPrinter) {
        this.amountPrinter = amountPrinter;
        return this;
    }

    public MultiPrinterBuilder withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public MultiPrinter now() {
        return new MultiPrinter(id, brand, model, serialNumber, machineValue, machineStatus,
                impressionCounterInitial, printType, impressionCounterNow, printingFranchise, amountPrinter, customer);
    }
}