package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idUser;

    @OneToMany
    private List<ItemCarrinho> carrinhoList;

    public Carrinho() { }

    public Carrinho(long idUser, List<ItemCarrinho> carrinho) {
        this.idUser = idUser;
        this.carrinhoList = carrinho;
    }

    public List<ItemCarrinho> listaCarrinho() {
        return carrinhoList;
    }

    public ItemCarrinho listaItemCarrinho(long idItem) {
        ItemCarrinho itemRetorno = null;
        for (ItemCarrinho item: carrinhoList) {
            if (item.getIdItem() == idItem) {
                itemRetorno = item;
            }
        }

        return itemRetorno;
    }

    public void adicionaItemCarrinho(ItemCarrinho item) {
        carrinhoList.add(item);
    }

    public void removeItemCarrinho(ItemCarrinho item) {
        carrinhoList.remove(item);
    }

    public ItemCarrinho atualizaItemCarrinho(long idItem, int numItens) {
        ItemCarrinho item = listaItemCarrinho(idItem);
        removeItemCarrinho(item);
        item.setQuantidadeItem(numItens);
        adicionaItemCarrinho(item);
        return item;
    }

    public void limparCarrinho() {
        carrinhoList.clear();
    }

    public boolean isEmpty() {
        return carrinhoList.isEmpty();
    }
}
