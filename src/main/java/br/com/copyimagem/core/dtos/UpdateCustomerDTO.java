package br.com.copyimagem.core.dtos;

import br.com.copyimagem.core.domain.entities.Address;
import br.com.copyimagem.core.domain.entities.MonthlyPayment;
import br.com.copyimagem.core.domain.entities.MultiPrinter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Data
public class UpdateCustomerDTO implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String clientName;
    private String cpfOrCnpj;
    private String primaryEmail;
    private List<String> emailList = new ArrayList<>();
    private String phoneNumber;
    private String whatsapp;
    private String bankCode;
    private Address address;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startContract;
    private String financialSituation;
    private byte payDay;
    private List<MultiPrinter> multiPrinterList = new LinkedList<>();
    private List<MonthlyPayment> monthlyPaymentList = new LinkedList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateCustomerDTO that = (UpdateCustomerDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(primaryEmail, that.primaryEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, primaryEmail);
    }
}
