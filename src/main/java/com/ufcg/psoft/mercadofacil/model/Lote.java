package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Produto produto;
    private int numeroDeItens;
    private String validade;

    private Lote() { }

    public Lote(Produto produto, int numeroDeItens, String validade) {
        this.produto = produto;
        this.numeroDeItens = numeroDeItens;
        this.validade = validade;
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getNumeroDeItens() {
        return numeroDeItens;
    }

    public String getValidade() {
        return validade;
    }
    public void setValidade(String validade){
        this.validade = validade;
    }

    public void setNumeroDeItens(int numeroDeItens) {
        this.numeroDeItens = numeroDeItens;
    }

    @Override
    public String toString() {
        return "Lote{" +
                "id=" + id +
                ", produto=" + produto.getId() +
                ", numeroDeItens=" + numeroDeItens +
                ", validade="+ validade + '\'' +
                '}';
    }
}