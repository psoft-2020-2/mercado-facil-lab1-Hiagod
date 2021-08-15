package com.ufcg.psoft.mercadofacil.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.repository.ItemCarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.model.ItemCarrinho;
import com.ufcg.psoft.mercadofacil.repository.CarrinhoRepository;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

	@Autowired
	private CarrinhoRepository carrinhoRepository;

	@Autowired
	private ItemCarrinhoRepository itemCarrinhoRepository;
	
	@Override
	public List<ItemCarrinho> listarItensCarrinho(long idUser) {
		return carrinhoRepository.findByIdUser(idUser).get(0).listaCarrinho();
	}
	
	@Override
	public ItemCarrinho getItemCarrinhoById(long idUser, long idItem) {
		return carrinhoRepository.findByIdUser(idUser).get(0).listaItemCarrinho(idItem);
	}

	@Override
	public void atualizaCarrinho(long idUser, ItemCarrinho itemCarrinho) {
		Carrinho carrinho = carrinhoRepository.findByIdUser(idUser).get(0);
		carrinho.adicionaItemCarrinho(itemCarrinho);
		carrinhoRepository.save(carrinho);
	}

	@Override
	public void removerItemCarrinho(long idUser, ItemCarrinho itemCarrinho) {
		carrinhoRepository.findByIdUser(idUser).get(0).removeItemCarrinho(itemCarrinho);
	}

	@Override
	public ItemCarrinho criarItemCarrinho(long idItem, int quantidadeItem, String nomeItem, BigDecimal precoItem) {
		ItemCarrinho item = new ItemCarrinho(idItem, quantidadeItem, nomeItem, precoItem);
		itemCarrinhoRepository.save(item);
		return item;
	}

	@Override
	public ItemCarrinho atualizaItemCarrinho(long idUser, long idItem, int numItens) {
		Carrinho carrinho = carrinhoRepository.findByIdUser(idUser).get(0);
		ItemCarrinho item = carrinho.atualizaItemCarrinho(idItem, numItens);
		itemCarrinhoRepository.save(item);
		carrinhoRepository.save(carrinho);
		return item;
	}

	@Override
	public void limparCarrinho(long idUser) {
		Carrinho carrinho = carrinhoRepository.findByIdUser(idUser).get(0);
		carrinho.limparCarrinho();
		carrinhoRepository.save(carrinho);
	}

	@Override
	public boolean isCarrinhoLimpo(long idUser) {
		return carrinhoRepository.findByIdUser(idUser).get(0).isEmpty();
	}

	@Override
	public Carrinho criarCarrinho(long idUser) {
		Carrinho carrinho = new Carrinho(idUser, new ArrayList<ItemCarrinho>());
		carrinhoRepository.save(carrinho);
		return carrinho;
	}

	@Override
	public void removerCarrinho(long idUser) {
		carrinhoRepository.delete(carrinhoRepository.findByIdUser(idUser).get(0));
	}
}
