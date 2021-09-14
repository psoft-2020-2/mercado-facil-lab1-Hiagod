package com.ufcg.psoft.mercadofacil.service;

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

    @Autowired
    private PagamentoRepository pagamentoRepository;

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

    @Override
    public Compra criarCompra(BigDecimal precoFinal, String metodoCompra, List<ItemVenda> carrinho) {
        Compra compra = new Compra(precoFinal, metodoCompra, carrinho);
        pagamentoRepository.save(compra.getPagamento());
        return compra;
    }

    @Override
    public ItemVenda criarItemVenda(int quantidadeItem, String nomeItem, BigDecimal precoItem) {
        ItemVenda item = new ItemVenda(quantidadeItem, nomeItem, precoItem);
        itemVendaRepository.save(item);
        return item;
    }
    @Override
    public void atualizaPrecoCompra(Compra compra, BigDecimal novoValor) {
        compra.setValor(novoValor);
        pagamentoRepository.save(compra.getPagamento());
    }
    @Override
    public void diminuiNoLote(List<Lote> lotes, ItemCarrinho item) {
        for (Lote lote: lotes) {
            long id = lote.getProduto().getId();
            if (item.getIdItem() == id) {
                lote.setNumeroDeItens(lote.getNumeroDeItens()- item.getQuantidadeItem());
            }
        }
    }
    
    @Override
    public String imprimirDetalhesDoProduto(ItemCarrinho produto) {
        return "Produto: " + produto.getNomeItem() + "     R$" + produto.getPrecoItem() + " x Quantidade: " + produto.getQuantidadeItem() + "\n";
    }

    @Override
    public String imprimeCabecalho() {
        return "REALIZAÇÃO DE COMPRA NO MERCADOFACIL\n" + "============================================================\n" + "LISTA DE PRODUTOS:\n";
    }

    @Override
    public String imprimirDetalhesDoPagamento(Compra compra) {
        String mensagemSaida = "";
        mensagemSaida += "------------------------------------------------------------\n" + "PREÇO FINAL DA COMPRA: R$" + compra.getValor() + "\n";
        mensagemSaida += "-------------------------------------------------------------=\n\n" + "   OBRIGADO POR COMPRAR NO MERCADOFACIL!\n";

        return mensagemSaida;
    }
}
