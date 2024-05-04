package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.builders.CustomerResponseDTOBuilder;
import br.com.copyimagem.core.domain.builders.LegalPersonalCustomerBuilder;
import br.com.copyimagem.core.domain.builders.NaturalPersonCustomerBuilder;
import br.com.copyimagem.core.domain.entities.LegalPersonalCustomer;
import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.infra.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    public static final long ID1L = 1L;
    public static final String CNPJ = "14.124.420/0001-94";
    public static final String CPF = "156.258.240-29";
    public static final String EMAIL = "claudio@mail.com.br";
    private LegalPersonalCustomer legalPersonalCustomer;
    private NaturalPersonCustomer naturalPersonCustomer;
    private CustomerResponseDTO customerResponseDTOPJ;
    private CustomerResponseDTO customerResponseDTOPF;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ConvertObjectToObjectDTOService convertObjectToObjectDTOService;
    @Mock
    private LegalPersonalCustomerServiceImpl legalPersonalCustomerService;
    @Mock
    private NaturalPersonCustomerServiceImpl naturalPersonCustomerService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    @DisplayName("Must return a Customer by ID ")
    void mustReturnACustomerByID() {
        when(customerRepository.findById(ID1L)).thenReturn(Optional.of(legalPersonalCustomer));
        when(convertObjectToObjectDTOService.convertToCustomerResponseDTO(legalPersonalCustomer)).thenReturn(customerResponseDTOPJ);
        CustomerResponseDTO customerResponseDTO = customerService.searchCliente("id", "1");
        assertEquals(customerResponseDTOPJ, customerResponseDTO);
        assertEquals(CustomerResponseDTO.class, customerResponseDTOPJ.getClass());
        assertEquals(customerResponseDTO.getCpfOrCnpj(),  customerResponseDTOPJ.getCpfOrCnpj());
    }

    @Test
    @DisplayName("Must return a empty when Customer not found by ID")
    void mustReturnAEmptyWhenCustomerNotFoundById() {
        when(customerRepository.findById(ID1L)).thenReturn(Optional.empty());
        String message = assertThrows(NoSuchElementException.class,
                () -> customerService.searchCliente("id", "1")).getMessage();
        assertEquals("Customer not found", message);

    }

    @Test
    @DisplayName("Must return a Customer by CPF")
    void mustReturnACustomerByCPF() {
        when(naturalPersonCustomerService.findByCpf(CPF)).thenReturn(customerResponseDTOPF);
        CustomerResponseDTO customerResponseDTO = customerService.searchCliente("cpf", CPF);
        assertEquals(customerResponseDTOPF, customerResponseDTO);
        assertEquals(CustomerResponseDTO.class, customerResponseDTOPF.getClass());
        assertEquals(customerResponseDTO.getCpfOrCnpj(), customerResponseDTOPF.getCpfOrCnpj());
    }

    @Test
    @DisplayName("Must return a empty when Customer not found by CPF")
    void mustReturnAEmptyWhenCustomerNotFoundByCPF() {
        when(naturalPersonCustomerService.findByCpf(CPF)).thenReturn(null);
        try{
            customerService.searchCliente("cpf", CPF);
        }catch (NoSuchElementException ex){
            assertEquals("Customer not found", ex.getMessage());
            assertEquals(NoSuchElementException.class, ex.getClass());
        }
    }

    @Test
    @DisplayName("Must return a Customer by CNPJ")
    void mustReturnACustomerByCNPJ(){
        when(legalPersonalCustomerService.findByCnpj(CNPJ)).thenReturn(customerResponseDTOPJ);
        CustomerResponseDTO customerResponseDTO = customerService.searchCliente("cnpj", CNPJ);
        assertEquals(customerResponseDTOPJ, customerResponseDTO);
        assertEquals(CustomerResponseDTO.class, customerResponseDTO.getClass());
        assertEquals(customerResponseDTOPJ.getCpfOrCnpj(), customerResponseDTO.getCpfOrCnpj());
    }

    @Test
    @DisplayName("Must return a empty when Customer not found by CNPJ")
    void mustReturnAEmptyWhenCustomerNotFounfByCNPJ(){
        when(legalPersonalCustomerService.findByCnpj(CNPJ)).thenReturn(null);
        try{
            customerService.searchCliente("cnpj", CNPJ);
        }catch (NoSuchElementException ex){
            assertEquals("Customer not found", ex.getMessage());
            assertEquals(NoSuchElementException.class, ex.getClass());
        }
    }

    @Test
    @DisplayName("Must return a Customer by Email")
    void mustReturnACustomerByEmail() {
        when(customerRepository.findByPrimaryEmail(EMAIL)).thenReturn(Optional.of(naturalPersonCustomer));
        when(convertObjectToObjectDTOService.convertToCustomerResponseDTO(naturalPersonCustomer)).thenReturn(customerResponseDTOPF);
        CustomerResponseDTO customerResponseDTO = customerService.searchCliente("email", EMAIL);
        assertEquals(customerResponseDTOPF, customerResponseDTO);
        assertEquals(CustomerResponseDTO.class, customerResponseDTOPF.getClass());
        assertEquals(customerResponseDTO.getCpfOrCnpj(), customerResponseDTOPF.getCpfOrCnpj());
    }
    @Test
    @DisplayName("Muts return a not found Customer by Email")
    void mustReturnAEmptyWhenCustomerNotFoundByEmail() {
        when(customerRepository.findByPrimaryEmail(EMAIL)).thenReturn(Optional.empty());
        String message = assertThrows(NoSuchElementException.class,
                () -> customerService.searchCliente("email", EMAIL)).getMessage();
        assertEquals("Customer not found", message);

    }
    @Test
    @DisplayName("Must return all customers")
    void mustReturnAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(legalPersonalCustomer));
        when(convertObjectToObjectDTOService.convertToCustomerResponseDTO(legalPersonalCustomer)).thenReturn(customerResponseDTOPJ);
        List<CustomerResponseDTO> customerResponseDTO = customerService.searchClientAll();
        assertEquals(1, customerResponseDTO.size());
        assertEquals(legalPersonalCustomer.getId(), customerResponseDTO.get(0).getId());
        assertEquals(CustomerResponseDTO.class, customerResponseDTO.get(0).getClass());
    }

    private void start() {
        legalPersonalCustomer = LegalPersonalCustomerBuilder.oneCustomer().nowCustomerPJ();
        naturalPersonCustomer = NaturalPersonCustomerBuilder.oneCustomer().nowCustomerPF();
        customerResponseDTOPJ = CustomerResponseDTOBuilder.oneCustomerResponseDTO().withCpfOrCnpj(CNPJ).now();
        customerResponseDTOPF = CustomerResponseDTOBuilder.oneCustomerResponseDTO().withCpfOrCnpj(CPF).now();
    }
}
