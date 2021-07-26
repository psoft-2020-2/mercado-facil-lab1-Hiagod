package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.service.*;
import com.ufcg.psoft.mercadofacil.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CompraApiController {

    @Autowired
    CompraService compraService;
    
    
    @RequestMapping(value = "/compras/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> listarVenda(@PathVariable("id") long id) {

        Optional<Compra> compraOptional = compraService.getCompraById(id);

        if (!compraOptional.isPresent()) {
            return ErroCompra.erroCompraNaoExiste();
        }

        Compra compra = compraOptional.get();

        return new ResponseEntity<Compra>(compra, HttpStatus.OK);
    }
    @RequestMapping(value = "/compras", method = RequestMethod.GET)
    public ResponseEntity<?> listarCompras(){

        List<Compra> compras = compraService.listarCompras();

        if (compras.isEmpty()) {
            return ErroCompra.erroComprasInexistentes();
        }

        return new ResponseEntity<List<Compra>>(compras, HttpStatus.OK);
    }

    
}
