package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroPagamento {

    static final String PAGAMENTO_INVALIDO = "Método de pagamento inválido";

    public static ResponseEntity<CustomErrorType> erroTipoPagamentoInvalido() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroPagamento.PAGAMENTO_INVALIDO),
                HttpStatus.NOT_FOUND);
    }
}
