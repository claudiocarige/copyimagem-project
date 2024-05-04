package br.com.copyimagem.infra.controllers;

import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.exceptions.NoSuchElementException;
import br.com.copyimagem.core.usecases.interfaces.CustomerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/search-client")
    public ResponseEntity<CustomerResponseDTO> searchCliente(
            @RequestParam("typeParam") String typeParam,
            @RequestParam("valueParam") String valueParam) {
        log.info(String.format("[ INFO ] Search for customers by :{ %s } -- with value : { %s } --- { %s }", typeParam, valueParam, CustomerController.class));
        try{
            CustomerResponseDTO response = customerService.searchCliente(typeParam, valueParam);
            return ResponseEntity.ok().body(response);
        }catch (NoSuchElementException ex){
            log.error("[ ERROR ] Exception (searchCliente() method in CustomerController class):  {}.", ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(value = "/search-client-all")
    public ResponseEntity<List<CustomerResponseDTO>> searchClientAll() {
        log.info(String.format("[ INFO ] Search for all customers --- { %s }", CustomerController.class));
        return ResponseEntity.ok().body(customerService.searchClientAll());
    }

    //TODO Create search for all Customers by FinancialSituation
    //TODO Create search for all defaulting Customers(Create Record Object for this)

}
