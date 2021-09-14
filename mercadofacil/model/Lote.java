package com.ufcg.psoft.mercadofacil.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
   
    @OneToMany
    private List<Cliente> interessados;

    public Lote(){}

    public Lote(Produto produto, int numeroDeItens) {
        this.produto = produto;
        this.numeroDeItens = numeroDeItens;
    }
    public Lote(Produto produto, int numeroDeItens, String validade) {
        this.produto = produto;
        this.numeroDeItens = numeroDeItens;
        this.validade = validade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public void adicionaInteresse(Cliente cliente) {
        this.interessados.add(cliente);
    }
    public List<Cliente> getInteressados(){
        return this.interessados;
    }
    @Override
    public String toString() {
        return "Lote{" +
                "id=" + id +
                ", produto=" + produto.getId() +
                ", numeroDeItens=" + numeroDeItens + '\'' +
                '}';
    }
}