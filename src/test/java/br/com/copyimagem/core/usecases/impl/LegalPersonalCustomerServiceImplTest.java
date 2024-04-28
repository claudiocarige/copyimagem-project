package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.LegalPersonalCustomer;
import br.com.copyimagem.core.dtos.LegalPersonalCustomerDTO;
import br.com.copyimagem.infra.repositories.LegalPersonalCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.copyimagem.core.domain.builders.LegalPersonalCustomerBuilder.oneCustomer;


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
    private  LegalPersonalCustomerServiceImpl legalPersonalCustomerService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
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