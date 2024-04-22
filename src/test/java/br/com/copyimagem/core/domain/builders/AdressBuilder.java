package br.com.copyimagem.core.domain.builders;
import br.com.copyimagem.core.domain.entities.Adress;

public class AdressBuilder {
    private Long id;
    private String street;
    private String number;
    private String city;
    private String state;
    private String country;

    private AdressBuilder(){}

    public static AdressBuilder oneAdress() {
        AdressBuilder builder = new AdressBuilder();
        initializeDefaultData(builder);
        return builder;
    }

    private static void initializeDefaultData(AdressBuilder builder) {
        builder.id = 1L;
        builder.street = "Rua Estevam Barbosa Alves";
        builder.number = "12";
        builder.city = "Salvador";
        builder.state = "Bahia";
        builder.country = "Brasil";
    }

    public AdressBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AdressBuilder withStreet(String street) {
        this.street = street;
        return this;
    }

    public AdressBuilder withNumber(String number) {
        this.number = number;
        return this;
    }

    public AdressBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public AdressBuilder withState(String state) {
        this.state = state;
        return this;
    }

    public AdressBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public Adress now() {
        return new Adress(id, street, number, city, state, country);
    }
}