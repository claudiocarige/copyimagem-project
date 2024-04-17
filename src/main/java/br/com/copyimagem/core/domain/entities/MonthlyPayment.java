package br.com.copyimagem.core.domain.entities;

import br.com.copyimagem.core.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class MonthlyPayment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column( nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer mes;
    private Integer ano;
    private Double valorMensalidade;
    private Integer contadorImpressoes;
    private Integer quantidadeImpressoes;
    private Double valorExcedente;
    private Double valorTotal;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dataVencimento;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dataPagamento;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}