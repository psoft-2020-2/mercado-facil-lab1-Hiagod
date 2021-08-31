package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroCompra {

    static final String COMPRA_INEXISTENTE = "Essa compra não foi realizada";
    static final String SEM_REGISTROS = "Nenhuma compra realizada ainda";

    public static ResponseEntity<CustomErrorType> erroCompraNaoExiste() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCompra.COMPRA_INEXISTENTE),
                HttpStatus.NOT_FOUND);
    }
    
    public static ResponseEntity<CustomErrorType> erroComprasInexistentes() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCompra.SEM_REGISTROS),
                HttpStatus.NOT_FOUND);
    }
}
