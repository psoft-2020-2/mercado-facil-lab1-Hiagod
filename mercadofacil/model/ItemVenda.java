package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int quantidadeItem;

    private String nomeItem;

    private BigDecimal precoItem;

    public ItemVenda() { }

    public ItemVenda(int quantidadeItem, String nomeItem, BigDecimal precoItem) {
        super();
        this.quantidadeItem = quantidadeItem;
        this.nomeItem = nomeItem;
        this.precoItem = precoItem;
    }

    public Long getId() {
        return id;
    }

    public int getQuantidadeItem() {
        return quantidadeItem;
    }

    public void setQuantidadeItem(int quantidadeItem) {
        this.quantidadeItem = quantidadeItem;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public BigDecimal getPrecoItem() {
        return precoItem;
    }

    public void setPrecoItem(BigDecimal precoItem) {
        this.precoItem = precoItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemVenda itemVenda = (ItemVenda) o;
        return quantidadeItem == itemVenda.quantidadeItem && id.equals(itemVenda.id) && nomeItem.equals(itemVenda.nomeItem) && precoItem.equals(itemVenda.precoItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantidadeItem, nomeItem, precoItem);
    }

    @Override
    public String toString() {
        return "{\n" +
                "     \"id\": " + getId() + ",\n" +
                "     \"quantidadeItem\": " + getQuantidadeItem() + ",\n" +
                "     \"nomeItem\": \"" + getNomeItem() + "\",\n" +
                "     \"precoItem\": " + getPrecoItem() + "\n" +
                "    }";
    }
}
