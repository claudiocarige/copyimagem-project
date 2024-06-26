package br.com.copyimagem.infra.persistence.repositories;

import br.com.copyimagem.core.domain.entities.LegalPersonalCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LegalPersonalCustomerRepository extends JpaRepository< LegalPersonalCustomer, Long > {


    Optional< LegalPersonalCustomer > findByPrimaryEmail( String primaryEmail );

    Optional< LegalPersonalCustomer > findByCnpj( String cpf );

    boolean existsLegalPersonalCustomerByCnpj( String cnpj );

}
