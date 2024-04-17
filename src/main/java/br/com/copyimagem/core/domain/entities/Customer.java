package br.com.copyimagem.core.domain.entities;

import br.com.copyimagem.core.domain.enums.FinancialSituation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorColumn(name = "dtype")
public abstract class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false)
    private Long id;
    private String clientName;
    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private String whatsapp;
    @OneToOne
    @JoinColumn(name = "adress_id")
    private Adress adress;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date startContract;
    @Enumerated(EnumType.STRING)
    private FinancialSituation financialSituation;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private List<MultiPrinter> multiPrinterList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch =FetchType.EAGER  ,mappedBy = "customer")
    private List<MonthlyPayment> monthlyPaymentList = new ArrayList<>();
}
