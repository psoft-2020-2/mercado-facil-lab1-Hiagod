package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class SucessCompra {

    static final String COMPRA_REALIZADA = "Essa compra foi realizada com sucesso";
    

    public static ResponseEntity<CustomSucessType> sucessCompraRealizada() {
        return new ResponseEntity<CustomSucessType>(new CustomSucessType(SucessCompra.COMPRA_REALIZADA),
                HttpStatus.OK); 
    }
    
}
