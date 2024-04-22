package br.com.copyimagem.core.usecases.impl;

import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import br.com.copyimagem.infra.repositories.NaturalPersonCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static br.com.copyimagem.core.domain.builders.CustomerBuilder.oneCustomer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NaturalPersonCustomerServiceImplTest {

    public static final long ID1L = 1L;
    public static final String CPF = "123.456.789-01";
    private NaturalPersonCustomer customerPf;
    @Mock
    private NaturalPersonCustomerRepository naturalPersonCustomerRepository;

    @InjectMocks
    private NaturalPersonCustomerServiceImpl naturalPersonCustomerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    @DisplayName("Must return a NaturalPersonCustomer by Id")
    void youMustReturnANaturalPersonCustomerById(){
        when(naturalPersonCustomerRepository.findById(ID1L)).thenReturn(Optional.of(customerPf));
        NaturalPersonCustomer natural = naturalPersonCustomerService.findNaturalPersonCustomerById(1L);

        assertAll("NaturalPersonCustomer",
                () -> assertNotNull(natural),
                () -> assertEquals(ID1L, natural.getId()),
                () -> assertEquals(natural, customerPf),
                () -> assertEquals(NaturalPersonCustomer.class, natural.getClass())
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
            assertEquals(RuntimeException.class, ex.getClass());
            assertEquals("Customer not found", ex.getMessage());
        }
    }

    @Test
    @DisplayName("Must return a list of NaturalPersonCustomer")
    void youMustReturnAListOfNaturalPersonCustomer(){
        String cpf = "894.965.315-04";
        String email = "julio@mail.com";
        when(naturalPersonCustomerRepository.findAll()).thenReturn(List.of(customerPf,
                oneCustomer().withCpf(cpf).withPrimaryEmail(email).nowCustomerPF()));
        List<NaturalPersonCustomer> natural = naturalPersonCustomerService.findAllNaturalPersonCustomer();
        assertAll("NaturalPersonCustomer",
                () -> assertNotNull(natural),
                () -> assertEquals(2, natural.size()),
                () -> assertEquals(NaturalPersonCustomer.class, natural.get(0).getClass()),
                () -> assertEquals(natural.get(0), customerPf),
                () -> assertEquals(natural.get(1).getPrimaryEmail(), email),
                () -> assertEquals(CPF, natural.get(0).getCpf()),
                () -> assertEquals(cpf, natural.get(1).getCpf())
        );

    }

    @Test
    @DisplayName("Must save a NaturalPersonCustomer")
    void youMustSaveANaturalPersonCustomer(){
        when(naturalPersonCustomerRepository.save(customerPf)).thenReturn(customerPf);
        NaturalPersonCustomer natural = naturalPersonCustomerService.saveNaturalPersonCustomer(customerPf);
        assertAll("NaturalPersonCustomer",
                () -> assertNotNull(natural),
                () -> assertEquals(natural, customerPf),
                () -> assertEquals(NaturalPersonCustomer.class, natural.getClass())
        );
    }

    private void start() {
        customerPf =  oneCustomer().withCpf(CPF).nowCustomerPF();
    }

}