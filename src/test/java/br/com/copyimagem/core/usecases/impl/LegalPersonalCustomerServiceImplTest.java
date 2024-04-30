package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.LegalPersonalCustomer;
import br.com.copyimagem.core.domain.entities.MonthlyPayment;
import br.com.copyimagem.core.domain.entities.MultiPrinter;
import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.dtos.LegalPersonalCustomerDTO;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.infra.repositories.LegalPersonalCustomerRepository;
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

import static br.com.copyimagem.core.domain.builders.LegalPersonalCustomerBuilder.oneCustomer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LegalPersonalCustomerServiceImplTest {

    public static final long ID1L = 1L;
    public static final String CNPJ = "12.345.678/0001-01";

    private LegalPersonalCustomer customerPj;

    private LegalPersonalCustomerDTO customerPjDTO;
    @Mock
    private LegalPersonalCustomerRepository legalPersonalCustomerRepository;

    @Mock
    private ConvertObjectToObjectDTOService convertObjectToObjectDTOService;

    @InjectMocks
    private LegalPersonalCustomerServiceImpl legalPersonalCustomerService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    @DisplayName("Must return a LegalPersonalCustomerDTO by Id")
    void shoulReturnALegalPersonalCustomerDTOById() {
        when(legalPersonalCustomerRepository.findById(ID1L)).thenReturn(Optional.of(customerPj));
        when(convertObjectToObjectDTOService.convertToLegalPersonalCustomerDTO(customerPj)).thenReturn(customerPjDTO);

        LegalPersonalCustomerDTO legalPersonalCustomerDTO = legalPersonalCustomerService.findLegalPersonalCustomerById(ID1L);

        assertAll("LegalPersonalCustomer",
                () -> assertNotNull(legalPersonalCustomerDTO),
                () -> assertEquals(ID1L, legalPersonalCustomerDTO.getId()),
                () -> assertEquals(legalPersonalCustomerDTO, customerPjDTO),
                () -> assertEquals(LegalPersonalCustomerDTO.class, legalPersonalCustomerDTO.getClass())
        );
    }

    @Test
    @DisplayName("shoul return a empty when LegalPersonalCustomer not found")
    void shoulReturnEmptyWhenNaturalPersonCustomerNotFound() {
        when(legalPersonalCustomerRepository.findById(ID1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> legalPersonalCustomerService.findLegalPersonalCustomerById(ID1L));
        try {
            legalPersonalCustomerService.findLegalPersonalCustomerById(ID1L);
        } catch (NoSuchElementException message) {
            assertEquals("Customer not found", message.getMessage());
            assertEquals(NoSuchElementException.class, message.getClass());
        }
    }

    @Test
    @DisplayName("should return a list of legalPersonalCustomer")
    void shouldReturnAListfLegalPersonalCustomer() {
        when(legalPersonalCustomerRepository.findAll()).thenReturn(List.of(customerPj));
        when(convertObjectToObjectDTOService.convertToLegalPersonalCustomerDTO(customerPj)).thenReturn(customerPjDTO);

        List<LegalPersonalCustomerDTO> legalPersonalCustomerList = legalPersonalCustomerService.findAllLegalPersonalCustomer();
        assertAll("LegalPersonalCustomerLis",
                () -> assertNotNull(legalPersonalCustomerList),
                () -> assertEquals(1, legalPersonalCustomerList.size()),
                () -> assertEquals(LegalPersonalCustomerDTO.class, legalPersonalCustomerList.get(0).getClass()),
                () -> {
                    assertAll("MultPrint",
                            () -> assertNotNull(legalPersonalCustomerList.get(0).getMultiPrinterList()),
                            () -> assertEquals(MultiPrinter.class, legalPersonalCustomerList.get(0).getMultiPrinterList().get(0).getClass()),
                            () -> assertEquals(1, legalPersonalCustomerList.get(0).getMultiPrinterList().size())
                    );
                },
                () -> {
                    assertAll("MonthlyPayment",
                            () -> assertEquals(1, legalPersonalCustomerList.get(0).getMonthlyPaymentList().size()),
                            () -> assertEquals(MonthlyPayment.class, legalPersonalCustomerList.get(0).getMonthlyPaymentList().get(0).getClass())
                    );
                }
        );

    }

    @Test
    @DisplayName("should save a LegalPersonalCustomer")
    void shouldSaveALegalPersonalCustomer() {
        when(legalPersonalCustomerRepository.findByPrimaryEmail(customerPjDTO.getPrimaryEmail()))
                .thenReturn(Optional.empty());
        when(legalPersonalCustomerRepository.findByCnpj(CNPJ)).thenReturn(Optional.empty());
        when(legalPersonalCustomerRepository.save(customerPj)).thenReturn(customerPj);
        when(convertObjectToObjectDTOService.convertToLegalPersonalCustomerDTO(customerPj)).thenReturn(customerPjDTO);
        when(convertObjectToObjectDTOService.convertToLegalPersonalCustomer(customerPjDTO)).thenReturn(customerPj);

        LegalPersonalCustomerDTO legalPersonalCustomerDTO = legalPersonalCustomerService
                .saveLegalPersonalCustomer(customerPjDTO);
        assertAll("LegalPersonalCustomer",
                () -> assertNotNull(legalPersonalCustomerDTO),
                () -> assertEquals(customerPjDTO.getId(), legalPersonalCustomerDTO.getId()),
                () -> assertEquals(LegalPersonalCustomerDTO.class, legalPersonalCustomerDTO.getClass()),
                () -> assertEquals(customerPjDTO, legalPersonalCustomerDTO),
                () -> assertEquals(customerPjDTO.getPrimaryEmail(), legalPersonalCustomerDTO.getPrimaryEmail()),
                () -> assertEquals(customerPjDTO.getClass(), legalPersonalCustomerDTO.getClass()),
                () -> {
                    assertAll("MultPrint",
                            () -> assertNotNull(legalPersonalCustomerDTO.getMultiPrinterList()),
                            () -> assertEquals(MultiPrinter.class, legalPersonalCustomerDTO.getMultiPrinterList().get(0).getClass()),
                            () -> assertEquals(1, legalPersonalCustomerDTO.getMultiPrinterList().size())
                    );
                },
                () -> {
                    assertAll("MonthlyPayment",
                            () -> assertEquals(1, legalPersonalCustomerDTO.getMonthlyPaymentList().size()),
                            () -> assertEquals(MonthlyPayment.class, legalPersonalCustomerDTO.getMonthlyPaymentList().get(0).getClass())
                    );
                }
        );
        verify(convertObjectToObjectDTOService, times(1))
                .convertToLegalPersonalCustomerDTO(customerPj);
        verify(convertObjectToObjectDTOService, times(1))
                .convertToLegalPersonalCustomer(customerPjDTO);
        verify(legalPersonalCustomerRepository, times(1))
                .findByPrimaryEmail(customerPjDTO.getPrimaryEmail());
        verify(legalPersonalCustomerRepository, times(1)).findByCnpj(customerPj.getCnpj());
    }

    @Test
    @DisplayName("Must retunr a Customer Response DTO By CNPJ")
    void mustReturnACustomerResponseDTOByCpnj(){
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setId(ID1L);
        customerResponseDTO.setCpfOrCnpj(CNPJ);
        customerResponseDTO.setClientName(customerPj.getClientName());
        customerResponseDTO.setAddress(customerPj.getAdress());
        when(legalPersonalCustomerRepository.findByCnpj(CNPJ)).thenReturn(Optional.of(customerPj));
        when(convertObjectToObjectDTOService.convertToCustomerResponseDTO(customerPj)).thenReturn(customerResponseDTO);
        CustomerResponseDTO responseDTO = legalPersonalCustomerService.findByCnpj(CNPJ);
        assertAll("CustomerResponseDTO",
                () -> assertNotNull(responseDTO),
                () -> assertEquals(customerResponseDTO, responseDTO),
                () -> assertEquals(CustomerResponseDTO.class, responseDTO.getClass()),
                () -> assertEquals(ID1L, responseDTO.getId()),
                () -> assertEquals(CNPJ, responseDTO.getCpfOrCnpj()),
                () -> assertEquals(customerResponseDTO.getClientName(), responseDTO.getClientName()),
                () -> assertEquals(customerResponseDTO.getAddress(), responseDTO.getAddress())
        );
    }

    @Test
    @DisplayName("must return a empty when CNPJ not found")
    void mustReturnEmptyWhenCnpjNotFound(){
        when(legalPersonalCustomerRepository.findByCnpj(CNPJ)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> legalPersonalCustomerService.findByCnpj(CNPJ));
        try {
            legalPersonalCustomerService.findByCnpj(CNPJ);
        } catch (NoSuchElementException message) {
            assertEquals("Customer not found", message.getMessage());
            assertEquals(NoSuchElementException.class, message.getClass());
        }
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource(value = "mandatoryscenarios")
    void mustValidateMandatoryFieldsWhenSaving(Long id, String cnpj, String message) {
            String exMessage = assertThrows(IllegalArgumentException.class, () -> {
            LegalPersonalCustomer legalPersonalCustomer = oneCustomer().withId(id).withCnpj(cnpj).nowCustomerPJ();
            LegalPersonalCustomerDTO legalPersonalCustomerDTO  =  convertObjectToObjectDTOService.convertToLegalPersonalCustomerDTO(legalPersonalCustomer);
            legalPersonalCustomerService.saveLegalPersonalCustomer(legalPersonalCustomerDTO);
        }).getMessage();
            assertEquals(message, exMessage);
    }

    private static Stream<Arguments> mandatoryscenarios(){
        return Stream.of(
          Arguments.of(ID1L, null, "Invalid CNPJ")
        );
    }

    private void start() {
        customerPj = oneCustomer().withId(ID1L).withCnpj(CNPJ).withPrimaryEmail("carige@mail.com").nowCustomerPJ();
        customerPjDTO = new LegalPersonalCustomerDTO();
        customerPjDTO.setId(ID1L);
        customerPjDTO.setCnpj(CNPJ);
        customerPjDTO.setPrimaryEmail(customerPj.getPrimaryEmail());
        customerPjDTO.setEmailList(customerPj.getEmailList());
        customerPjDTO.setPhoneNumber(customerPj.getPhoneNumber());
        customerPjDTO.setAdress(customerPj.getAdress());
        customerPjDTO.setClientName(customerPj.getClientName());
        customerPjDTO.setBankCode(customerPj.getBankCode());
        customerPjDTO.setFinancialSituation(customerPj.getFinancialSituation().toString());
        customerPjDTO.setPayDay(customerPj.getPayDay());
        customerPjDTO.setStartContract(customerPj.getStartContract());
        customerPjDTO.setMonthlyPaymentList(customerPj.getMonthlyPaymentList());
        customerPjDTO.setMultiPrinterList(customerPj.getMultiPrinterList());
    }
}