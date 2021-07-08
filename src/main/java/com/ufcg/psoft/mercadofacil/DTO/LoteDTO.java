package com.ufcg.psoft.mercadofacil.DTO;

import com.ufcg.psoft.mercadofacil.model.Produto;


public class LoteDTO {

    private Produto produto;
    private int numItens;
    private String validade;

    public String getValidade() {
        return validade;
    }

    public int getNumItens() {
        return numItens;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Produto getProduto(){
        return produto;
    }
}
