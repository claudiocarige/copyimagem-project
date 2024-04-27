package br.com.copyimagem.core.domain.builders;

import br.com.copyimagem.core.domain.entities.*;
import br.com.copyimagem.core.domain.enums.FinancialSituation;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.com.copyimagem.core.domain.builders.AdressBuilder.oneAdress;
import static br.com.copyimagem.core.domain.builders.MonthlyPaymentBuilder.oneMonthlyPayment;
import static br.com.copyimagem.core.domain.builders.MultiPrinterBuilder.oneMultiPrinter;

public class LegalPersonalCustomerBuilder implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String clientName;
    private String primaryEmail;
    private List<String> emailList = new ArrayList<>();
    private String phoneNumber;
    private String whatsapp;
    private String bankCode;
    private Adress adress;
    private LocalDate startContract;
    private FinancialSituation financialSituation;
    private byte payDay;
    private List<MultiPrinter> multiPrinterList = new ArrayList<>();
    private List<MonthlyPayment> monthlyPaymentList = new ArrayList<>();
    private String cnpj;

    private LegalPersonalCustomerBuilder(){}

    public static LegalPersonalCustomerBuilder oneCustomer() {
        LegalPersonalCustomerBuilder builder = new LegalPersonalCustomerBuilder();
        initializeDefaultData(builder);
        return builder;
    }

    private static void initializeDefaultData(LegalPersonalCustomerBuilder builder) {
        builder.id = 1L;
        builder.clientName = "Claudio Carigé";
        builder.primaryEmail = "carige@mail.com";
        builder.emailList = Arrays.asList("mail1@mail.com","mail2@mail.com");
        builder.phoneNumber = "7132104567";
        builder.whatsapp = "71998987878";
        builder.bankCode = "123";
        builder.adress = oneAdress().now();
        builder.startContract = LocalDate.of(2022, 1, 1);
        builder.financialSituation = FinancialSituation.PAGO;
        builder.payDay = 5;
        builder.multiPrinterList = Arrays.asList(oneMultiPrinter().now());
        builder.monthlyPaymentList = Arrays.asList(oneMonthlyPayment().now());
    }

    public LegalPersonalCustomerBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public LegalPersonalCustomerBuilder withClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public LegalPersonalCustomerBuilder withPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
        return this;
    }

    public LegalPersonalCustomerBuilder withListaEmailList(String... emailList) {
        this.emailList = Arrays.asList(emailList);
        return this;
    }

    public LegalPersonalCustomerBuilder withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public LegalPersonalCustomerBuilder withWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
        return this;
    }

    public LegalPersonalCustomerBuilder withBankCode(String bankCode) {
        this.bankCode = bankCode;
        return this;
    }

    public LegalPersonalCustomerBuilder withAdress(Adress adress) {
        this.adress = adress;
        return this;
    }

    public LegalPersonalCustomerBuilder withStartContract(LocalDate startContract) {
        this.startContract = startContract;
        return this;
    }

    public LegalPersonalCustomerBuilder withFinancialSituation(FinancialSituation financialSituation) {
        this.financialSituation = financialSituation;
        return this;
    }

    public LegalPersonalCustomerBuilder withPayDay(byte payDay) {
        this.payDay = payDay;
        return this;
    }

    public LegalPersonalCustomerBuilder withListaMultiPrinterList(MultiPrinter... multiPrinterList) {
        this.multiPrinterList = Arrays.asList(multiPrinterList);
        return this;
    }

    public LegalPersonalCustomerBuilder withListaMonthlyPaymentList(MonthlyPayment... monthlyPaymentList) {
        this.monthlyPaymentList = Arrays.asList(monthlyPaymentList);
        return this;
    }

    public LegalPersonalCustomerBuilder withCnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public LegalPersonalCustomer nowCustomerPJ() {
        LegalPersonalCustomer customer = new LegalPersonalCustomer(cnpj);
        customer.setId(id);
        customer.setClientName(clientName);
        customer.setPrimaryEmail(primaryEmail);
        customer.setEmailList(emailList);
        customer.setPhoneNumber(phoneNumber);
        customer.setWhatsapp(whatsapp);
        customer.setBankCode(bankCode);
        customer.setAdress(adress);
        customer.setStartContract(startContract);
        customer.setFinancialSituation(financialSituation);
        customer.setPayDay(payDay);
        multiPrinterList.forEach(multiPrinter -> multiPrinter.setCustomer(customer));
        customer.addMultiPrinter(multiPrinterList);
        monthlyPaymentList.forEach(monthlyPayment -> monthlyPayment.setCustomer(customer));
        customer.addMonthlyPayment(monthlyPaymentList);
        return customer;
    }
}
