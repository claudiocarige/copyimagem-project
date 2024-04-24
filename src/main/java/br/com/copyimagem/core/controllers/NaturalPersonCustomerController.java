package br.com.copyimagem.core.controllers;

import br.com.copyimagem.core.domain.entities.NaturalPersonCustomer;
import br.com.copyimagem.core.usecases.interfaces.NaturalPersonCustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers/pf")
public class NaturalPersonCustomerController {

    private final NaturalPersonCustomerService naturalPersonCustomerService;

    public NaturalPersonCustomerController(NaturalPersonCustomerService naturalPersonCustomerService) {
        this.naturalPersonCustomerService = naturalPersonCustomerService;
    }


    @GetMapping(value = "/all")
    public ResponseEntity<List<NaturalPersonCustomer>> getAllNaturalPersonCustomers(){
        return  ResponseEntity.ok().body(naturalPersonCustomerService.findAllNaturalPersonCustomer());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<NaturalPersonCustomer> getNaturalPersonCustomerById(@PathVariable Long id){
        return ResponseEntity.ok().body(naturalPersonCustomerService.findNaturalPersonCustomerById(id));
    }

}
