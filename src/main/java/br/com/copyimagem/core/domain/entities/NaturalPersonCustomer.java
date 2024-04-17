package br.com.copyimagem.core.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NaturalPersonCustomer extends Customer{

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() { return super.hashCode(); }
}
