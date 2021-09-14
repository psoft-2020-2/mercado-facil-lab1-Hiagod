package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;

public class EntregaExpress extends Entrega {

    private BigDecimal valor;


    public EntregaExpress(String endereco){
        if(endereco.length() < 10){
            this.valor  = new BigDecimal(5);
        } else {
            this.valor = new BigDecimal(6);
        }

    }

    @Override
    public BigDecimal valorDaEntrega(String tipo) {

        if (tipo.equals("refrigerado")) {
            return this.valor = valor.add( new BigDecimal(0.5));
        } else if (tipo.equals("fragil")) {
            return this.valor = valor.add(new BigDecimal(1));
        } else {
            return this.valor= valor.add(new BigDecimal(0));
        }
    }

}
