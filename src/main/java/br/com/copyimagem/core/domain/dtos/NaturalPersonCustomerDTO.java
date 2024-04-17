package br.com.copyimagem.core.domain.dtos;

import br.com.copyimagem.core.domain.entities.Adress;
import br.com.copyimagem.core.domain.entities.MonthlyPayment;
import br.com.copyimagem.core.domain.entities.MultiPrinter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
public class NaturalPersonCustomerDTO implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String clientName;
    private String primaryEmail;
    @Setter(AccessLevel.NONE)
    private List<String> emailList = new ArrayList<>();
    private String phoneNumber;
    private String whatsapp;
    private String bankCode;
    private Adress adress;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date startContract;
    private String financialSituation;
    private byte payDay;
    @Setter(AccessLevel.NONE)
    private LinkedList<MultiPrinter> multiPrinterList = new LinkedList<>();
    @Setter(AccessLevel.NONE)
    private LinkedList<MonthlyPayment> monthlyPaymentList = new LinkedList<>();
}
