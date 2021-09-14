package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;

public class EntregaPadrao  extends Entrega {

    private BigDecimal valor;

    public EntregaPadrao(String endereco){
        if(endereco.length() < 10){
            this.valor  = new BigDecimal(2);
        } else {
            this.valor = new BigDecimal(3);
        }

    }

    @Override
    public BigDecimal valorDaEntrega(String tipo) {

        if (tipo.equals("refrigerado")) {
             return this.valor= this.valor.add( new BigDecimal(0.2));
        } else if (tipo.equals("fragil")) {
            return this.valor = this.valor.add(new BigDecimal(0.3));
        } else {
            return this.valor = this.valor.add(new BigDecimal(0));
        }
    }

}
