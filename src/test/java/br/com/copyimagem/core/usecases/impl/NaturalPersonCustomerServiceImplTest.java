package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.dtos.NaturalPersonCustomerDTO;
import br.com.copyimagem.core.exceptions.DataIntegrityViolationException;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.infra.repositories.NaturalPersonCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static br.com.copyimagem.core.domain.builders.NaturalPersonCustomerBuilder.oneCustomer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NaturalPersonCustomerServiceImplTest {

    public static final long ID1L = 1L;
    public static final String CPF = "123.456.789-01";
    private NaturalPersonCustomer customerPf;
    private NaturalPersonCustomerDTO customerPfDTO;
    @Mock
    private NaturalPersonCustomerRepository naturalPersonCustomerRepository;
    @Mock
    private ConvertObjectToObjectDTOService convertObjectToObjectDTOService;
    @InjectMocks
    private NaturalPersonCustomerServiceImpl naturalPersonCustomerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    @DisplayName("Must return a NaturalPersonCustomerDTO by Id")
    void youMustReturnANaturalPersonCustomerDTOById(){
        when(naturalPersonCustomerRepository.findById(ID1L)).thenReturn(Optional.of(customerPf));

        when(convertObjectToObjectDTOService.convertToNaturalPersonCustomerDTO(customerPf)).thenReturn(customerPfDTO);
        NaturalPersonCustomerDTO expectedDTO = naturalPersonCustomerService.findNaturalPersonCustomerById(1L);

        assertAll("NaturalPersonCustomer",
                () -> assertNotNull(expectedDTO),
                () -> assertEquals(ID1L, expectedDTO.getId()),
                () -> assertEquals(expectedDTO, customerPfDTO),
                () -> assertEquals(NaturalPersonCustomerDTO.class, expectedDTO.getClass())
        );
    }

    @Test
    @DisplayName("Must return a empty when NaturalPersonCustomer not found")
    void youMustReturnEmptyWhenNaturalPersonCustomerNotFound(){
        when(naturalPersonCustomerRepository.findById(11L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> naturalPersonCustomerService
                .findNaturalPersonCustomerById(11L));
        verify(naturalPersonCustomerRepository, times(1)).findById(11L);
        try {
            naturalPersonCustomerService.findNaturalPersonCustomerById(11L);
        } catch (Exception ex) {
            assertEquals(NoSuchElementException.class, ex.getClass());
            assertEquals("Customer not found", ex.getMessage());
        }
    }

    @Test
    @DisplayName("Must return a list of NaturalPersonCustomer")
    void youMustReturnAListOfNaturalPersonCustomer(){
        String email = "carige@mail.com";
        when(naturalPersonCustomerRepository.findAll()).thenReturn(List.of(customerPf));
        when(convertObjectToObjectDTOService.convertToNaturalPersonCustomerDTO(customerPf)).thenReturn(customerPfDTO);
        List<NaturalPersonCustomerDTO> natural = naturalPersonCustomerService.findAllNaturalPersonCustomer();
        assertAll("NaturalPersonCustomer",
                () -> assertNotNull(natural),
                () -> assertEquals(1, natural.size()),
                () -> assertEquals(NaturalPersonCustomerDTO.class, natural.get(0).getClass()),
                () -> assertEquals(customerPfDTO, natural.get(0)),
                () -> assertEquals(email, natural.get(0).getPrimaryEmail()),
                () -> assertEquals(CPF, natural.get(0).getCpf())
        );

    }

    @Test
    @DisplayName("Must save a NaturalPersonCustomer")
    void youMustSaveANaturalPersonCustomer(){
        when(convertObjectToObjectDTOService.convertToNaturalPersonCustomer(customerPfDTO)).thenReturn(customerPf);
        when(naturalPersonCustomerRepository.findByPrimaryEmail(customerPfDTO.getPrimaryEmail())).thenReturn(Optional.empty());
        when(naturalPersonCustomerRepository.findByCpf(customerPfDTO.getCpf())).thenReturn(Optional.empty());
        when(naturalPersonCustomerRepository.save(customerPf)).thenReturn(customerPf);
        when(convertObjectToObjectDTOService.convertToNaturalPersonCustomerDTO(customerPf)).thenReturn(customerPfDTO);
        NaturalPersonCustomerDTO natural = naturalPersonCustomerService.saveNaturalPersonCustomer(customerPfDTO);
        assertAll("NaturalPersonCustomerDTO",
                () -> assertNotNull(natural),
                () -> assertEquals(customerPfDTO, natural),
                () ->  assertEquals(customerPfDTO.getId(), natural.getId()),
                () ->  assertEquals(customerPfDTO.getCpf(), natural.getCpf()),
                () ->  assertEquals(customerPfDTO.getClass(), natural.getClass())
        );
        verify(convertObjectToObjectDTOService, times(1)).convertToNaturalPersonCustomer(customerPfDTO);
        verify(naturalPersonCustomerRepository, times(1)).findByPrimaryEmail(customerPfDTO.getPrimaryEmail());
        verify(naturalPersonCustomerRepository, times(1)).findByCpf(customerPfDTO.getCpf());
        verify(naturalPersonCustomerRepository, times(1)).save(customerPf);
    }
    @Test
    @DisplayName("Must throw return NoSuchElementException when EMAIL already exists")
    void youMustReturnThrowNoSuchElementExceptionWhenEmailAlreadyExists() {
        when(naturalPersonCustomerRepository.findByPrimaryEmail(customerPf.getPrimaryEmail()))
                .thenReturn(Optional.of(customerPf));
        DataIntegrityViolationException dataException = assertThrows(DataIntegrityViolationException.class,() ->
                naturalPersonCustomerService.saveNaturalPersonCustomer(customerPfDTO));
        assertTrue(dataException.getMessage().startsWith("Email"));
    }

    @Test
    @DisplayName("Must throw return NoSuchElementException when CPF already exists")
    void youMustReturnThrowNoSuchElementExceptionWhenCpfAlreadyExists() {
        when(naturalPersonCustomerRepository.findByCpf(customerPf.getCpf()))
                .thenReturn(Optional.of(customerPf));
        DataIntegrityViolationException dataException = assertThrows(DataIntegrityViolationException.class,() ->
                naturalPersonCustomerService.saveNaturalPersonCustomer(customerPfDTO));
        assertTrue(dataException.getMessage().startsWith("CPF"));
    }

    @Test
    @DisplayName("must return a NaturalPersonCustomer by CPF.")
    void mustReturnANaturalPersonCustomerByCPF() {
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setId(ID1L);
        customerResponseDTO.setCpfOrCnpj(CPF);
        customerResponseDTO.setClientName(customerPf.getClientName());
        customerResponseDTO.setAddress(customerPf.getAdress());
        when(naturalPersonCustomerRepository.findByCpf(customerPf.getCpf()))
                .thenReturn(Optional.of(customerPf));
        when(convertObjectToObjectDTOService.convertToCustomerResponseDTO(customerPf))
                .thenReturn(customerResponseDTO);
        CustomerResponseDTO customerDTO = naturalPersonCustomerService.findByCpf(customerPf.getCpf());
        assertNotNull(customerDTO);
        assertEquals(customerResponseDTO, customerDTO);
        assertEquals(customerResponseDTO.getId(), customerDTO.getId());
        assertEquals(CustomerResponseDTO.class, customerDTO.getClass());
        assertEquals(CPF, customerDTO.getCpfOrCnpj());
    }
    @Test
    @DisplayName("must return a empty when CPF not found.")
    void mustReturnEmptyWhenCpfNotFound() {
        when(naturalPersonCustomerRepository.findByCpf(customerPf.getCpf()))
                .thenReturn(Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                naturalPersonCustomerService.findByCpf(customerPf.getCpf()));
        assertEquals("Customer not found", exception.getMessage());
        verify(naturalPersonCustomerRepository, times(1)).findByCpf(customerPf.getCpf());
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource(value = "mandatoryscenarios")
    void mustValidateMandatoryFieldsWhenSaving(Long id, String cpf, String message) {
        String exMessage = assertThrows(IllegalArgumentException.class, () -> {
            NaturalPersonCustomer naturalPersonCustomer = oneCustomer().withId(id).withCpf(cpf).nowCustomerPF();
            NaturalPersonCustomerDTO naturalPersonCustomerDTO  =  convertObjectToObjectDTOService.convertToNaturalPersonCustomerDTO(naturalPersonCustomer);
            naturalPersonCustomerService.saveNaturalPersonCustomer(naturalPersonCustomerDTO);
        }).getMessage();
        assertEquals(message, exMessage);
    }

    private static Stream<Arguments> mandatoryscenarios(){
        return Stream.of(
                Arguments.of(ID1L, null, "Invalid CPF")
        );
    }

    private void start() {
        customerPf = oneCustomer().withId(ID1L).withCpf(CPF).withPrimaryEmail("carige@mail.com").nowCustomerPF();
        customerPfDTO = new NaturalPersonCustomerDTO();
        customerPfDTO.setId(ID1L);
        customerPfDTO.setCpf(CPF);
        customerPfDTO.setPrimaryEmail(customerPf.getPrimaryEmail());
        customerPfDTO.setEmailList(customerPf.getEmailList());
        customerPfDTO.setPhoneNumber(customerPf.getPhoneNumber());
        customerPfDTO.setAdress(customerPf.getAdress());
        customerPfDTO.setClientName(customerPf.getClientName());
        customerPfDTO.setBankCode(customerPf.getBankCode());
        customerPfDTO.setFinancialSituation(customerPf.getFinancialSituation().toString());
        customerPfDTO.setPayDay(customerPf.getPayDay());
        customerPfDTO.setStartContract(customerPf.getStartContract());
        customerPfDTO.setMonthlyPaymentList(customerPf.getMonthlyPaymentList());
        customerPfDTO.setMultiPrinterList(customerPf.getMultiPrinterList());
    }
}