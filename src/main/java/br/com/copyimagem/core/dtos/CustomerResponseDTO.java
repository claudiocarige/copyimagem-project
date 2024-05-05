package br.com.copyimagem.core.dtos;

import br.com.copyimagem.core.domain.entities.Address;
import lombok.Data;

@Data
public class CustomerResponseDTO {

    private Long id;
    private String clientName;
    private String primaryEmail;
    private String phoneNumber;
    private Address address;
    private String cpfOrCnpj;
    private String financialSituation;
}
