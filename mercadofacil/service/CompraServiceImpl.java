package com.ufcg.psoft.mercadofacil.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CompraServiceImpl implements CompraService{

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private  ItemVendaRepository itemVendaRepository;

    @Override
    public Optional<Compra> getCompraById(long id) {
    	return compraRepository.findById(id);
    }

    @Override
    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    @Override
    public void salvarCompra(Compra compra) {
        compraRepository.save(compra);
    }

    public Compra criarCompra(BigDecimal precoFinal, List<ItemVenda> carrinho) {
        Compra compra = new Compra(precoFinal, carrinho);
       
        return compra;
    }

    public ItemVenda criarItemVenda(int quantidadeItem, String nomeItem, BigDecimal precoItem) {
        ItemVenda item = new ItemVenda(quantidadeItem, nomeItem, precoItem);
        itemVendaRepository.save(item);
        return item;
    }
}
