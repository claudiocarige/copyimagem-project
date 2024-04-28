package br.com.copyimagem.core.dtos;

import br.com.copyimagem.core.domain.entities.Adress;
import br.com.copyimagem.core.domain.entities.MonthlyPayment;
import br.com.copyimagem.core.domain.entities.MultiPrinter;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
public class LegalPersonalCustomerDTO implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(message = "Name is required")
    private String clientName;

    @CNPJ(message = "CNPJ format is invalid")
    @NotNull(message = "CNPJ is required")
    private String cnpj;

    @NotNull(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._+-]+@[a-z]+\\.[a-z]{2,}(?:\\.[a-z]{2,3})?$", message = "Email format is invalid")
    private String primaryEmail;

    private List<String> emailList = new ArrayList<>();

    @NotNull(message = "Phone number is required")
    private String phoneNumber;

    @NotNull(message = "Whatsapp is required")
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
