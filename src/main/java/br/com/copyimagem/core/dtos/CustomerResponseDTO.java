package br.com.copyimagem.core.dtos;

import br.com.copyimagem.core.domain.entities.Adress;
import lombok.Data;

@Data
public class CustomerResponseDTO {

    private Long id;
    private String clientName;
    private String primaryEmail;
    private String phoneNumber;
    private Adress address;
    private String cpfOrCnpj;
    private String financialSituation;
}
