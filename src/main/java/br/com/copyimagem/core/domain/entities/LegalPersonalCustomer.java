package br.com.copyimagem.core.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serial;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LegalPersonalCustomer extends Customer{

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(unique = true,length = 18)
    private String cnpj;

    @Override
    public int hashCode() { return super.hashCode(); }

    @Override
    public boolean equals(Object obj) { return super.equals(obj); }
}
