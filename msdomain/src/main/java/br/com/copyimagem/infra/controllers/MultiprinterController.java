package br.com.copyimagem.infra.controllers;

import br.com.copyimagem.core.dtos.MultiPrinterDTO;
import br.com.copyimagem.core.usecases.interfaces.MultiPrinterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping( "/api/v1/multi-printer" )
public class MultiprinterController {


    private final MultiPrinterService multiPrinterService;


    public MultiprinterController( MultiPrinterService multiPrinterService ) {

        this.multiPrinterService = multiPrinterService;
    }

    @GetMapping
    public ResponseEntity< List< MultiPrinterDTO > > findAllMultiPrinters() {

        return ResponseEntity.ok( multiPrinterService.findAllMultiPrinters() );
    }

    @GetMapping( "/{id}" )
    public ResponseEntity< MultiPrinterDTO > findMultiPrinterById( @PathVariable Integer id ) {

        return ResponseEntity.ok( multiPrinterService.findMultiPrinterById( id ) );
    }

    @GetMapping( "/customer/{customerId}" )
    public ResponseEntity< List< MultiPrinterDTO > > findAllMultiPrintersByCustomerId( @PathVariable Long customerId ) {

        return ResponseEntity.ok( multiPrinterService.findAllMultiPrintersByCustomerId( customerId ) );
    }

    @PostMapping( value = "/save")
    public ResponseEntity< MultiPrinterDTO > saveMultiPrinter( @RequestBody MultiPrinterDTO multiPrinterDTO ) {

        return ResponseEntity.ok( multiPrinterService.saveMultiPrinter( multiPrinterDTO ) );
    }

    @PatchMapping( "/set-customer" )
    public ResponseEntity< MultiPrinterDTO > setUpClientOnAMultiPrinter( @RequestParam Integer id,
                                                                         @RequestParam Long customerId ) {

        return ResponseEntity.ok( multiPrinterService.setUpClientOnAMultiPrinter( id, customerId ) );
    }

    @PatchMapping( "/set-customer/{id}" )
    public ResponseEntity< Void > deleteCustomerFromMultiPrinter( @PathVariable Integer id ) {
        multiPrinterService.deleteCustomerFromMultiPrinter( id );
        return ResponseEntity.noContent().build();
    }


    @PatchMapping( "/status" )
    public ResponseEntity< MultiPrinterDTO > setMachineStatus( @RequestParam Integer id,
                                                               @RequestParam String status ) {

        return ResponseEntity.ok( multiPrinterService.setMachineStatus( id, status ) );
    }

    @PatchMapping( "/impression-counter" )
    public ResponseEntity< MultiPrinterDTO > setImpressionCounter( @RequestParam Integer id,
                                                                   @RequestParam Integer counter,
                                                                   @RequestParam String attribute ) {

        return ResponseEntity.ok( multiPrinterService.setImpressionCounter( id, counter, attribute ) );
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity< Void > deleteMultiPrinter( @PathVariable Integer id ) {

        multiPrinterService.deleteMultiPrinter( id );
        return ResponseEntity.noContent().build();
    }


}
