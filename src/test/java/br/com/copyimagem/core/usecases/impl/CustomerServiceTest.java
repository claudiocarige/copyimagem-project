package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.Customer;
import br.com.copyimagem.core.domain.entities.LegalPersonalCustomer;
import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.dtos.UpdateCustomerDTO;
import br.com.copyimagem.core.exceptions.IllegalArgumentException;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.infra.persistence.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static br.com.copyimagem.core.domain.builders.CustomerResponseDTOBuilder.oneCustomerResponseDTO;
import static br.com.copyimagem.core.domain.builders.LegalPersonalCustomerBuilder.oneLegalPersonalCustomer;

import static br.com.copyimagem.core.domain.builders.NaturalPersonCustomerBuilder.oneNaturalPersonCustomer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    public static final long ID1L = 1L;
    public static final String CNPJ = "14.124.420/0001-94";
    public static final String CPF = "156.258.240-29";
    public static final String EMAIL = "claudio@mail.com.br";
    private Customer customer;
    private LegalPersonalCustomer legalPersonalCustomer;
    private NaturalPersonCustomer naturalPersonCustomer;
    private CustomerResponseDTO customerResponseDTOPJ;
    private CustomerResponseDTO customerResponseDTOPF;
    private UpdateCustomerDTO updateCustomerDTOPJ;
    private UpdateCustomerDTO updateCustomerDTOPF;

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

    @Test
    @DisplayName("Must return all customers by FinancialSituation")
    void mustReturnAllCustomersByFinancialSituation() {
        String situation = "PAGO";
        when(customerRepository.findAllByFinancialSituation(any())).
                thenReturn(Collections.singletonList(legalPersonalCustomer));
        when(convertObjectToObjectDTOService.convertToCustomerResponseDTO(legalPersonalCustomer))
                .thenReturn(customerResponseDTOPJ);
        List<CustomerResponseDTO> customerResponseDTO = customerService.searchFinancialSituation(situation);
        assertEquals(1, customerResponseDTO.size());
        assertEquals(legalPersonalCustomer.getId(), customerResponseDTO.get(0).getId());
        assertEquals(CustomerResponseDTO.class, customerResponseDTO.get(0).getClass());
        assertEquals(situation, customerResponseDTO.get(0).getFinancialSituation());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "cpf, 156.258.240-29, 2",
            "cnpj, 14.124.420/0001-94, 1",
            "primaryEmail, claudio@mail.com.br, 1"
    })
    @DisplayName("Must update the Customer by attribute")
    void mustUpdateTheCustomerByAttribute(String attribute, String val, String id){
        when(customerRepository.findById(1L)).thenReturn(Optional.of(legalPersonalCustomer));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(naturalPersonCustomer));
        when(customerRepository.save(legalPersonalCustomer)).thenReturn(legalPersonalCustomer);
        when(customerRepository.save(naturalPersonCustomer)).thenReturn(naturalPersonCustomer);
        when(convertObjectToObjectDTOService.convertToUpdateCustomerDTO(legalPersonalCustomer)).thenReturn(updateCustomerDTOPJ);
        when(convertObjectToObjectDTOService.convertToUpdateCustomerDTO(naturalPersonCustomer)).thenReturn(updateCustomerDTOPF);
        UpdateCustomerDTO updateCustomerResultDTO = customerService.updateCustomerAttribute(attribute, val, Long.parseLong(id));

        assertNotNull(updateCustomerResultDTO);
        assertEquals(UpdateCustomerDTO.class, updateCustomerResultDTO.getClass());

        switch (attribute) {
            case "cnpj":
                assertEquals(val, updateCustomerDTOPJ.getCpfOrCnpj());
                break;
            case "cpf":
                assertEquals(val, updateCustomerDTOPF.getCpfOrCnpj());
                break;
            case "primaryEmail":
                assertEquals(val, updateCustomerDTOPJ.getPrimaryEmail());
                break;
        }
    }

    @Test
    @DisplayName("must return true when the attribute is in the list")
    void mustReturnTrueWhenTheAttributeIsInTheList() {
        String attribute = "emailList";
        when(customerRepository.findById(ID1L)).thenReturn(Optional.of(customer));
        String message = assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomerAttribute(attribute, "mail@mail.com", ID1L)).getMessage();
        assertEquals("This attribute cannot be changed on this endpoint.", message);
    }
    @Test
    @DisplayName("Must return a exception when attribute not found")
    void mustReturnAExceptionWhenAttributeNotFound(){
        String attribute = "homeNumber";
        when(customerRepository.findById(ID1L)).thenReturn(Optional.of(customer));

        String message = assertThrows(IllegalArgumentException.class,
                        () -> customerService.updateCustomerAttribute(attribute, "5", ID1L)).getMessage();
        assertEquals("Attribute not found.", message);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "primaryEmail,Email format is invalid,ccarige.mail ",
            "cnpj, CNPJ format is invalid, il.123.com/1234-br",
            "cpf, CPF format is invalid, 894.965.31-02"})
    @DisplayName("Must return a exception when Attributes are invalid")
    void mustReturnAExceptionWhenAttributesAreInvalid(String attribute,String messages, String val ){
        when(customerRepository.findById(ID1L)).thenReturn(Optional.of(customer));
        String message = assertThrows(IllegalArgumentException.class,
                () -> customerService.updateCustomerAttribute(attribute, val, ID1L)).getMessage();
        assertEquals(messages, message);
        verify(customerRepository, times(1)).findById(ID1L);
    }


    private void start() {
        customer = oneLegalPersonalCustomer().nowCustomerPJ();
        legalPersonalCustomer = oneLegalPersonalCustomer().nowCustomerPJ();
        naturalPersonCustomer = oneNaturalPersonCustomer().nowCustomerPF();
        customerResponseDTOPJ = oneCustomerResponseDTO().withCpfOrCnpj(CNPJ).now();
        customerResponseDTOPF = oneCustomerResponseDTO().withCpfOrCnpj(CPF).now();
        updateCustomerDTOPJ = new UpdateCustomerDTO();
        updateCustomerDTOPJ.setPrimaryEmail(EMAIL);
        updateCustomerDTOPJ.setId(ID1L);
        updateCustomerDTOPJ.setCpfOrCnpj(CNPJ);
        updateCustomerDTOPF = new UpdateCustomerDTO();
        updateCustomerDTOPF.setPrimaryEmail(EMAIL);
        updateCustomerDTOPF.setId(2L);
        updateCustomerDTOPF.setCpfOrCnpj(CPF);

    }
}
