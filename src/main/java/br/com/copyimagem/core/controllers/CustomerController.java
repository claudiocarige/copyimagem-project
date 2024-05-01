package br.com.copyimagem.core.controllers;

import br.com.copyimagem.core.dtos.CustomerResponseDTO;
import br.com.copyimagem.core.usecases.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/search-cliente")
    public ResponseEntity<CustomerResponseDTO> searchCliente(
            @RequestParam("typeParam") String typeParam,
            @RequestParam("valueParam") String valueParam
    ) {
        return ResponseEntity.ok().body(customerService.searchCliente(typeParam, valueParam));
    }
}
