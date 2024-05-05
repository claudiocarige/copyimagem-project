package br.com.copyimagem.core.domain.builders;

import java.util.List;
import java.util.Arrays;
import java.time.LocalDate;
import br.com.copyimagem.core.domain.entities.Address;
import br.com.copyimagem.core.domain.entities.MonthlyPayment;
import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.domain.enums.FinancialSituation;
import br.com.copyimagem.core.dtos.NaturalPersonCustomerDTO;

import static br.com.copyimagem.core.domain.builders.AddressBuilder.oneAddress;
import static br.com.copyimagem.core.domain.builders.MonthlyPaymentBuilder.oneMonthlyPayment;
import static br.com.copyimagem.core.domain.builders.MultiPrinterBuilder.oneMultiPrinter;

public class NaturalPersonCustomerDTOBuilder {
    private Long id;
    private String clientName;
    private String cpf;
    private String primaryEmail;
    private List<String> emailList;
    private String phoneNumber;
    private String whatsapp;
    private String bankCode;
    private Address address;
    private LocalDate startContract;
    private String financialSituation;
    private byte payDay;
    private List<MultiPrinter> multiPrinterList;
    private List<MonthlyPayment> monthlyPaymentList;

    private NaturalPersonCustomerDTOBuilder(){}

    public static NaturalPersonCustomerDTOBuilder oneNaturalPersonCustomerDTO() {
        NaturalPersonCustomerDTOBuilder builder = new NaturalPersonCustomerDTOBuilder();
        initializeDefaultData(builder);
        return builder;
    }

    private static void initializeDefaultData(NaturalPersonCustomerDTOBuilder builder) {
        builder.id = 1L;
        builder.clientName = "Claudio Carigé";
        builder.cpf = "156.258.240-29";
        builder.primaryEmail = "carige@mail.com";
        builder.emailList = Arrays.asList("mail1@mail.com","mail2@mail.com");
        builder.phoneNumber = "7132104567";
        builder.whatsapp = "71998987878";
        builder.bankCode = "123";
        builder.address = oneAddress().now();
        builder.startContract = LocalDate.of(2022, 1, 1);
        builder.financialSituation = FinancialSituation.PAGO.toString();
        builder.payDay = 5;
        builder.multiPrinterList = Arrays.asList(oneMultiPrinter().now());
        builder.monthlyPaymentList = Arrays.asList(oneMonthlyPayment().now());
    }

    public NaturalPersonCustomerDTOBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public NaturalPersonCustomerDTOBuilder withClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public NaturalPersonCustomerDTOBuilder withCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public NaturalPersonCustomerDTOBuilder withPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
        return this;
    }

    public NaturalPersonCustomerDTOBuilder withEmailList(String... emailList) {
        this.emailList = Arrays.asList(emailList);
        return this;
    }

    public NaturalPersonCustomerDTOBuilder withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public NaturalPersonCustomerDTOBuilder withWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
        return this;
    }

    public NaturalPersonCustomerDTOBuilder withBankCode(String bankCode) {
        this.bankCode = bankCode;
        return this;
    }

    public NaturalPersonCustomerDTOBuilder withAddress(Address adress) {
        this.address = adress;
        return this;
    }

    public NaturalPersonCustomerDTOBuilder withStartContract(LocalDate startContract) {
        this.startContract = startContract;
        return this;
    }

    public NaturalPersonCustomerDTOBuilder withFinancialSituation(String financialSituation) {
        this.financialSituation = financialSituation;
        return this;
    }

    public NaturalPersonCustomerDTOBuilder withPayDay(byte payDay) {
        this.payDay = payDay;
        return this;
    }

    public NaturalPersonCustomerDTOBuilder withMultiPrinterList(MultiPrinter... multiPrinterList) {
        this.multiPrinterList = Arrays.asList(multiPrinterList);
        return this;
    }

    public NaturalPersonCustomerDTOBuilder withMonthlyPaymentList(MonthlyPayment... monthlyPaymentList) {
        this.monthlyPaymentList = Arrays.asList(monthlyPaymentList);
        return this;
    }

    public NaturalPersonCustomerDTO now() {
        NaturalPersonCustomerDTO customerDTO = new NaturalPersonCustomerDTO();
        customerDTO.setId(id);
        customerDTO.setClientName(clientName);
        customerDTO.setCpf(cpf);
        customerDTO.setPrimaryEmail(primaryEmail);
        customerDTO.setEmailList(emailList);
        customerDTO.setPhoneNumber(phoneNumber);
        customerDTO.setWhatsapp(whatsapp);
        customerDTO.setBankCode(bankCode);
        customerDTO.setAddress(address);
        customerDTO.setStartContract(startContract);
        customerDTO.setFinancialSituation(financialSituation);
        customerDTO.setPayDay(payDay);
        customerDTO.setMultiPrinterList(multiPrinterList);
        customerDTO.setMonthlyPaymentList(monthlyPaymentList);
        return customerDTO;
    }
}
