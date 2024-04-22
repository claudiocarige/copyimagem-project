package br.com.copyimagem.core.domain.builders;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import br.com.copyimagem.core.domain.entities.*;
import br.com.copyimagem.core.domain.enums.FinancialSituation;
import java.time.LocalDate;

import static br.com.copyimagem.core.domain.builders.AdressBuilder.oneAdress;
import static br.com.copyimagem.core.domain.builders.MonthlyPaymentBuilder.oneMonthlyPayment;
import static br.com.copyimagem.core.domain.builders.MultiPrinterBuilder.oneMultiPrinter;

public class CustomerBuilder {
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
    private String cpf;
    private String cnpj;

    private CustomerBuilder(){}

    public static CustomerBuilder oneCustomer() {
        CustomerBuilder builder = new CustomerBuilder();
        initializeDefaultData(builder);
        return builder;
    }

    private static void initializeDefaultData(CustomerBuilder builder) {
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

    public CustomerBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CustomerBuilder withClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public CustomerBuilder withPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
        return this;
    }

    public CustomerBuilder withListaEmailList(String... emailList) {
        this.emailList = Arrays.asList(emailList);
        return this;
    }

    public CustomerBuilder withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public CustomerBuilder withWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
        return this;
    }

    public CustomerBuilder withBankCode(String bankCode) {
        this.bankCode = bankCode;
        return this;
    }

    public CustomerBuilder withAdress(Adress adress) {
        this.adress = adress;
        return this;
    }

    public CustomerBuilder withStartContract(LocalDate startContract) {
        this.startContract = startContract;
        return this;
    }

    public CustomerBuilder withFinancialSituation(FinancialSituation financialSituation) {
        this.financialSituation = financialSituation;
        return this;
    }

    public CustomerBuilder withPayDay(byte payDay) {
        this.payDay = payDay;
        return this;
    }

    public CustomerBuilder withListaMultiPrinterList(MultiPrinter... multiPrinterList) {
        this.multiPrinterList = Arrays.asList(multiPrinterList);
        return this;
    }

    public CustomerBuilder withListaMonthlyPaymentList(MonthlyPayment... monthlyPaymentList) {
        this.monthlyPaymentList = Arrays.asList(monthlyPaymentList);
        return this;
    }

    public CustomerBuilder withCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public CustomerBuilder withCnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public NaturalPersonCustomer nowCustomerPF() {
        NaturalPersonCustomer customer = new NaturalPersonCustomer(cpf);
        now(customer);
        return customer;
    }

    public LegalPersonalCustomer nowCustomerPJ() {
        LegalPersonalCustomer customer = new LegalPersonalCustomer(cnpj);
        now(customer);
        return customer;    }

    private void now(Customer customer) {
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
    }
}
