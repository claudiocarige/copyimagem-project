package br.com.copyimagem.infra.config;

import br.com.copyimagem.core.domain.entities.LegalPersonalCustomer;
import br.com.copyimagem.core.domain.entities.MonthlyPayment;
import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.dtos.MultiPrinterDTO;
import br.com.copyimagem.core.dtos.UpdateCustomerDTO;
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
                map().setCpfOrCnpj(source.getCnpj());            }
        });
        modelMapper.addMappings(new PropertyMap<NaturalPersonCustomer, CustomerResponseDTO>() {
            @Override
            protected void configure(){
                map().setCpfOrCnpj(source.getCpf());
            }
        });
        modelMapper.addMappings(new PropertyMap<LegalPersonalCustomer, UpdateCustomerDTO>() {
            @Override
            protected void configure(){
                map().setCpfOrCnpj(source.getCnpj());           }
        });
        modelMapper.addMappings(new PropertyMap<NaturalPersonCustomer, UpdateCustomerDTO>() {
            @Override
            protected void configure(){
                map().setCpfOrCnpj(source.getCpf());
            }
        });
        modelMapper.addMappings(new PropertyMap<MultiPrinter, MultiPrinterDTO>() {
            @Override
            protected void configure() {map().setCustomer_id(source.getCustomer().getId().toString());}});
        modelMapper.addMappings(new PropertyMap<MonthlyPayment, MultiPrinterDTO>() {
            @Override
            protected void configure() {map().setCustomer_id(source.getCustomer().getId().toString());}});

        return modelMapper;
    }
}
