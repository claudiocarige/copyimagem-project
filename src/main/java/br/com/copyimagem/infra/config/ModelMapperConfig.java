package br.com.copyimagem.infra.config;

import br.com.copyimagem.core.domain.entities.LegalPersonalCustomer;
import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.addMappings(new PropertyMap<LegalPersonalCustomer, CustomerResponseDTO>() {
            @Override
            protected void configure(){
                map().setCpfOrCnpj(source.getCnpj());
            }
        });
        modelMapper.addMappings(new PropertyMap<NaturalPersonCustomer, CustomerResponseDTO>() {
            @Override
            protected void configure(){
                map().setCpfOrCnpj(source.getCpf());
                map().setAddress(source.getAdress());
            }
        });
        return modelMapper;
    }
}
