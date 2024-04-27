package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import br.com.copyimagem.core.dtos.NaturalPersonCustomerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ConvertObjectToObjectDTOService {

    private final ModelMapper modelMapper;

    public ConvertObjectToObjectDTOService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public NaturalPersonCustomerDTO convertToNaturalPersonCustomerDTO(NaturalPersonCustomer naturalPersonCustomer) {
        return modelMapper.map(naturalPersonCustomer, NaturalPersonCustomerDTO.class);
    }

    public NaturalPersonCustomer convertToNaturalPersonCustomer(NaturalPersonCustomerDTO naturalPersonCustomerDTO) {
        return modelMapper.map(naturalPersonCustomerDTO, NaturalPersonCustomer.class);
    }

}
