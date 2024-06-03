package br.com.copyimagem.core.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class AddressTest {


    private Address adress;

    @BeforeEach
    void setUp() {

        startAdress();
    }

    @Test
    @DisplayName( "You must create a valid adress" )
    void youMustCreateAValidAdress() {

        assertAll( "Adress",
                () -> assertEquals( 1L, adress.getId() ),
                () -> assertEquals( "123", adress.getStreet() ),
                () -> assertEquals( "Bairro 1", adress.getNumber() ),
                () -> assertEquals( "Cidade 1", adress.getCity() ),
                () -> assertEquals( "Estado 1", adress.getState() ),
                () -> assertEquals( "12345678", adress.getCountry() ),
                () -> assertEquals( Address.class, adress.getClass() )
        );
    }

    private void startAdress() {

        adress = new Address();
        adress.setId( 1L );
        adress.setStreet( "123" );
        adress.setNumber( "Bairro 1" );
        adress.setCity( "Cidade 1" );
        adress.setState( "Estado 1" );
        adress.setCountry( "12345678" );
    }

}