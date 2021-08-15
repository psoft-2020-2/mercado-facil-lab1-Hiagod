package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufcg.psoft.mercadofacil.model.Produto;

public class ErroCarrinho {
	
	static final String CARRINHO_VAZIO = "O carrinho está vazio";
	
	static final String PRODUTO_JA_EXISTE = "O produto %s do frabricante %s ja esta no carrinho";
	
	static final String ITEM_NAO_EXISTE = "Produto com id %s não está no carrinho";
	
	static final String QUANTIDADE_NAO_DISPONIVEL = "Quantidade insuficiente";
	
	static final String CARRINHO_JA_DESCARTADO = "O carrinho do usuário de id %¨s já está vazio";

	public static ResponseEntity<CustomErrorType> erroCarrinhoVazio(long idUser) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroCarrinho.CARRINHO_VAZIO, idUser)),
				HttpStatus.NO_CONTENT);
	}

	public static ResponseEntity<?> erroProdutoJaExisteNoCarrinho(Produto produto) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroCarrinho.PRODUTO_JA_EXISTE,
				produto.getNome(), produto.getFabricante())), HttpStatus.CONFLICT);
	}
	
	public static ResponseEntity<CustomErrorType> erroItemNaoExisteNoCarrinho(long id) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroCarrinho.ITEM_NAO_EXISTE, id)),
				HttpStatus.NOT_FOUND);
	}
	
	public static ResponseEntity<CustomErrorType> erroQuantidadeNaoDisponivel() {		
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCarrinho.QUANTIDADE_NAO_DISPONIVEL),
				HttpStatus.NOT_MODIFIED);
	}

	public static ResponseEntity<CustomErrorType> erroCarinhoJaDescartado(long idUser) {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroCarrinho.CARRINHO_JA_DESCARTADO, idUser)),
				HttpStatus.NO_CONTENT);
	}
}
