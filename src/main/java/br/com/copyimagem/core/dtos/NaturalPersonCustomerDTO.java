package br.com.copyimagem.core.dtos;

import br.com.copyimagem.core.domain.entities.Adress;
import br.com.copyimagem.core.domain.entities.MonthlyPayment;
import br.com.copyimagem.core.domain.entities.MultiPrinter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
public class NaturalPersonCustomerDTO implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String clientName;
    private String cpf;
    private String primaryEmail;
    private List<String> emailList = new ArrayList<>();
    private String phoneNumber;
    private String whatsapp;
    private String bankCode;
    private Adress adress;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startContract;
    private String financialSituation;
    private byte payDay;

    private List<MultiPrinter> multiPrinterList = new LinkedList<>();

    private List<MonthlyPayment> monthlyPaymentList = new LinkedList<>();
}
