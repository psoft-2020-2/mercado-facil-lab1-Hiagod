package com.ufcg.psoft.mercadofacil.service;

import java.math.BigDecimal;
import java.util.List;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.ItemCarrinho;

public interface CarrinhoService {

    List<ItemCarrinho> listarItensCarrinho(long idUser);

    ItemCarrinho getItemCarrinhoById(long idUser, long idItem);

    void atualizaCarrinho(long idUser, ItemCarrinho ItemCarrinho);

    ItemCarrinho criarItemCarrinho(long idItem, int quantidadeItem, String nomeItem, BigDecimal precoItem);

    ItemCarrinho atualizaItemCarrinho(long idUser, long idItem, int numItens);

    void removerItemCarrinho(long idUser, ItemCarrinho ItemCarrinho);

    void limparCarrinho(long idUser);

    boolean isCarrinhoLimpo(long idUser);

    Carrinho criarCarrinho(long idUser);

    void removerCarrinho(long idUser);

    void defineEntrega(long idUser, String tipoEntrega, String endereco);

    String getEntrega(long idUser);

    String getEndereco(long idUser);
}

