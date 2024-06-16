package br.com.copyimagem.infra.controllers;

import br.com.copyimagem.core.dtos.MultiPrinterDTO;
import br.com.copyimagem.core.usecases.interfaces.MultiPrinterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping( "/api/v1/multiprinter" )
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
    public ResponseEntity< MultiPrinterDTO > findMultiPrinterById(@PathVariable Integer id ) {
        return ResponseEntity.ok( multiPrinterService.findMultiPrinterById( id ) );
    }

    @GetMapping( "/customer/{customerId}" )
    public ResponseEntity< List< MultiPrinterDTO > > findAllMultiPrintersByCustomerId(@PathVariable Long customerId ) {
        return ResponseEntity.ok( multiPrinterService.findAllMultiPrintersByCustomerId( customerId ) );
    }

    @PostMapping( "/save" )
    public ResponseEntity< MultiPrinterDTO > saveMultiPrinter(@RequestBody MultiPrinterDTO multiPrinterDTO ) {
        return ResponseEntity.ok( multiPrinterService.saveMultiPrinter( multiPrinterDTO ) );
    }

}
