package com.ufcg.psoft.mercadofacil.service;

import java.math.BigDecimal;
import java.util.List;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.ItemCarrinho;

public interface CarrinhoService {

	public List<ItemCarrinho> listarItensCarrinho(long idUser);
	
	public ItemCarrinho getItemCarrinhoById(long idUser, long idItem);

	public void atualizaCarrinho(long idUser, ItemCarrinho itemCarrinho);

	public ItemCarrinho criarItemCarrinho(long idItem, int quantidadeItem, String nomeItem, BigDecimal precoItem);

	public ItemCarrinho atualizaItemCarrinho(long idUser, long idItem, int numItens);
	
	public void removerItemCarrinho(long idUser, ItemCarrinho itemCarrinho);
	
	public void limparCarrinho(long idUser);

	public boolean isCarrinhoLimpo(long idUser);

	public Carrinho criarCarrinho(long idUser);

	public void removerCarrinho(long idUser);
}
