package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;

public class Retirada extends Entrega {

    private BigDecimal valor;

    @Override
    public BigDecimal valorDaEntrega(String tipo) {
        return this.valor = new BigDecimal(0);
    }
}
