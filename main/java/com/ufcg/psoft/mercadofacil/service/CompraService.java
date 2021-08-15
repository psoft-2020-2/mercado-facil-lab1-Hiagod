package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CompraService {

    public Optional<Compra> getCompraById(long id);

    public List<Compra> listarCompras();

    public void salvarCompra(Compra compra);

    public Compra criarCompra(BigDecimal precoFinal, String metodoCompra, List<ItemVenda> carrinho);
    
    public void atualizaPrecoCompra(Compra compra, BigDecimal novoValor);

    public ItemVenda criarItemVenda(int quantidadeItem, String nomeItem, BigDecimal precoItem);
}
