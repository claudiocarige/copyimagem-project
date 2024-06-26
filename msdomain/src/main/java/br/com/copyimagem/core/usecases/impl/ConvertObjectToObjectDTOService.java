package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.*;
import br.com.copyimagem.core.dtos.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class ConvertObjectToObjectDTOService {


    private final ModelMapper modelMapper;

    public ConvertObjectToObjectDTOService( ModelMapper modelMapper ) {

        this.modelMapper = modelMapper;
    }

    public NaturalPersonCustomerDTO convertToNaturalPersonCustomerDTO( NaturalPersonCustomer naturalPersonCustomer ) {

        return modelMapper.map( naturalPersonCustomer, NaturalPersonCustomerDTO.class );
    }

    public NaturalPersonCustomer convertToNaturalPersonCustomer( NaturalPersonCustomerDTO naturalPersonCustomerDTO ) {

        return modelMapper.map( naturalPersonCustomerDTO, NaturalPersonCustomer.class );
    }

    public LegalPersonalCustomerDTO convertToLegalPersonalCustomerDTO( LegalPersonalCustomer legalPersonalCustomer ) {

        return modelMapper.map( legalPersonalCustomer, LegalPersonalCustomerDTO.class );
    }

    public LegalPersonalCustomer convertToLegalPersonalCustomer( LegalPersonalCustomerDTO legalPersonalCustomerDTO ) {

        return modelMapper.map( legalPersonalCustomerDTO, LegalPersonalCustomer.class );
    }

    public CustomerResponseDTO convertToCustomerResponseDTO( Customer customer ) {

        return modelMapper.map( customer, CustomerResponseDTO.class );
    }

    UpdateCustomerDTO convertToUpdateCustomerDTO( Customer customer ) {

        return modelMapper.map( customer, UpdateCustomerDTO.class );
    }

    public Customer convertToCustomer( UpdateCustomerDTO updateCustomerDTO ) {

        return modelMapper.map( updateCustomerDTO, Customer.class );
    }

    public MultiPrinterDTO convertToMultiPrinterDTO( MultiPrinter multiPrinter ) {

        return modelMapper.map( multiPrinter, MultiPrinterDTO.class );
    }

    public MultiPrinter convertToMultiPrinter( MultiPrinterDTO multiPrinterDTO ) {

        return modelMapper.map( multiPrinterDTO, MultiPrinter.class );
    }

    public MonthlyPaymentDTO convertToMonthlyPaymentDTO( MonthlyPayment monthlyPayment ) {

        return modelMapper.map( monthlyPayment, MonthlyPaymentDTO.class );
    }

}
