package br.com.copyimagem.core.domain.builders;

import java.util.List;
import java.util.Arrays;
import java.time.LocalDate;
import br.com.copyimagem.core.domain.entities.Adress;
import br.com.copyimagem.core.domain.entities.MonthlyPayment;
import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.domain.enums.FinancialSituation;
import br.com.copyimagem.core.dtos.LegalPersonalCustomerDTO;

import static br.com.copyimagem.core.domain.builders.AdressBuilder.oneAdress;
import static br.com.copyimagem.core.domain.builders.MonthlyPaymentBuilder.oneMonthlyPayment;
import static br.com.copyimagem.core.domain.builders.MultiPrinterBuilder.oneMultiPrinter;

public class LegalPersonalCustomerDTOBuilder {
    private Long id;
    private String clientName;
    private String cnpj;
    private String primaryEmail;
    private List<String> emailList;
    private String phoneNumber;
    private String whatsapp;
    private String bankCode;
    private Adress adress;
    private LocalDate startContract;
    private String financialSituation;
    private byte payDay;
    private List<MultiPrinter> multiPrinterList;
    private List<MonthlyPayment> monthlyPaymentList;

    private LegalPersonalCustomerDTOBuilder(){}

    public static LegalPersonalCustomerDTOBuilder oneLegalPersonalCustomerDTO() {
        LegalPersonalCustomerDTOBuilder builder = new LegalPersonalCustomerDTOBuilder();
        initializeDefaultData(builder);
        return builder;
    }

    private static void initializeDefaultData(LegalPersonalCustomerDTOBuilder builder) {
        builder.id = 1L;
        builder.clientName = "Claudio Carigé";
        builder.cnpj = "14.124.420/0001-94";
        builder.primaryEmail = "carige@mail.com";
        builder.emailList = Arrays.asList("mail1@mail.com","mail2@mail.com");
        builder.phoneNumber = "7132104567";
        builder.whatsapp = "71998987878";
        builder.bankCode = "123";
        builder.adress = oneAdress().now();
        builder.startContract = LocalDate.of(2022, 1, 1);
        builder.financialSituation = FinancialSituation.PAGO.toString();
        builder.payDay = 5;
        builder.multiPrinterList = Arrays.asList(oneMultiPrinter().now());
        builder.monthlyPaymentList = Arrays.asList(oneMonthlyPayment().now());
    }

    public LegalPersonalCustomerDTOBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public LegalPersonalCustomerDTOBuilder withClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public LegalPersonalCustomerDTOBuilder withCnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public LegalPersonalCustomerDTOBuilder withPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
        return this;
    }

    public LegalPersonalCustomerDTOBuilder withEmailList(String... emailList) {
        this.emailList = Arrays.asList(emailList);
        return this;
    }

    public LegalPersonalCustomerDTOBuilder withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public LegalPersonalCustomerDTOBuilder withWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
        return this;
    }

    public LegalPersonalCustomerDTOBuilder withBankCode(String bankCode) {
        this.bankCode = bankCode;
        return this;
    }

    public LegalPersonalCustomerDTOBuilder withAdress(Adress adress) {
        this.adress = adress;
        return this;
    }

    public LegalPersonalCustomerDTOBuilder withStartContract(LocalDate startContract) {
        this.startContract = startContract;
        return this;
    }

    public LegalPersonalCustomerDTOBuilder withFinancialSituation(String financialSituation) {
        this.financialSituation = financialSituation;
        return this;
    }

    public LegalPersonalCustomerDTOBuilder withPayDay(byte payDay) {
        this.payDay = payDay;
        return this;
    }

    public LegalPersonalCustomerDTOBuilder withMultiPrinterList(MultiPrinter... multiPrinterList) {
        this.multiPrinterList = Arrays.asList(multiPrinterList);
        return this;
    }

    public LegalPersonalCustomerDTOBuilder withMonthlyPaymentList(MonthlyPayment... monthlyPaymentList) {
        this.monthlyPaymentList = Arrays.asList(monthlyPaymentList);
        return this;
    }

    public LegalPersonalCustomerDTO now() {
        LegalPersonalCustomerDTO customerDTO = new LegalPersonalCustomerDTO();
        customerDTO.setId(id);
        customerDTO.setClientName(clientName);
        customerDTO.setCnpj(cnpj);
        customerDTO.setPrimaryEmail(primaryEmail);
        customerDTO.setEmailList(emailList);
        customerDTO.setPhoneNumber(phoneNumber);
        customerDTO.setWhatsapp(whatsapp);
        customerDTO.setBankCode(bankCode);
        customerDTO.setAdress(adress);
        customerDTO.setStartContract(startContract);
        customerDTO.setFinancialSituation(financialSituation);
        customerDTO.setPayDay(payDay);
        customerDTO.setMultiPrinterList(multiPrinterList);
        customerDTO.setMonthlyPaymentList(monthlyPaymentList);
        return customerDTO;
    }
}
