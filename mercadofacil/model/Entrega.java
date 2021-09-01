package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;

public abstract class Entrega {

    private BigDecimal valor;

    public abstract BigDecimal valorDaEntrega(String tipo);

    public BigDecimal getValorEntrega(){
        return this.valor;
    }

}
