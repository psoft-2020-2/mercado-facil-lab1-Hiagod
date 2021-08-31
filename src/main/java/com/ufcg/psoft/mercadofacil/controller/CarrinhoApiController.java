package com.ufcg.psoft.mercadofacil.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.service.*;
import com.ufcg.psoft.mercadofacil.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CarrinhoApiController {

	@Autowired
	LoteService loteService;
	@Autowired
	ProdutoService produtoService;
	@Autowired
	CarrinhoService carrinhoService;
	@Autowired
	CompraService compraService;
	@Autowired
	ClienteService clienteService;

	@RequestMapping(value = "/{idUser}/carrinho", method = RequestMethod.GET)
	public ResponseEntity<?> listarItensCarrinho(@PathVariable("idUser") long idUser){

		Optional<Cliente> optionalCliente = clienteService.getClienteById(idUser);

		if (!optionalCliente.isPresent()) {
			return ErroCliente.erroClienteNaoEncontrado(idUser);
		}

		List<ItemCarrinho> carrinho = carrinhoService.listarItensCarrinho(idUser);

		if (carrinho.isEmpty()) {
			return ErroCarrinho.erroCarrinhoVazio(idUser);
		}

		List<Lote> lista = new ArrayList<Lote>();
		Long i = 1L;

		for (ItemCarrinho item: carrinho) {
			Lote temp = new Lote(produtoService.getProdutoById(item.getIdItem()).get(), item.getQuantidadeItem());
			temp.setId(i++);
			lista.add(temp);
		}

		return new ResponseEntity<List<Lote>>(lista, HttpStatus.OK);
	}

	@RequestMapping(value = "/{idUser}/carrinho/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> adicionarItemCarrinho(@PathVariable("idUser") long idUser, @PathVariable("id") long idItem, @RequestBody int numItens) {

		Optional<Cliente> optionalCliente = clienteService.getClienteById(idUser);

		if (!optionalCliente.isPresent()) {
			return ErroCliente.erroClienteNaoEncontrado(idUser);
		}

		Optional<Produto> optionalProduto = produtoService.getProdutoById(idItem);

		if (!optionalProduto.isPresent()) {
			return ErroProduto.erroProdutoNaoEncontrado(idItem);
		}

		List<Lote> lotes = loteService.listarLotes();

		if (lotes.isEmpty()) {
			return ErroLote.erroSemLotesCadastrados();
		}

		List<Lote> lotesDoProduto = new ArrayList<Lote>();

		for (Lote lote: lotes) {
			long id = lote.getProduto().getId();
			if (idItem == id) {
				lotesDoProduto.add(lote);
			}
		}

		if (lotesDoProduto.isEmpty()) {
			return ErroLote.erroSemLotesCadastrados();
		}

		ItemCarrinho itemNoCarrinho = carrinhoService.getItemCarrinhoById(idUser, idItem);

		if (itemNoCarrinho != null) {
			return ErroCarrinho.erroProdutoJaExisteNoCarrinho(optionalProduto.get());
		}

		Produto produto = optionalProduto.get();
		ItemCarrinho item = carrinhoService.criarItemCarrinho(idItem, numItens, produto.getNome(), produto.getPreco());
		carrinhoService.atualizaCarrinho(idUser, item);

		return new ResponseEntity<ItemCarrinho>(item, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{idUser}/carrinho/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removerItemCarrinho(@PathVariable("idUser") long idUser, @PathVariable("id") long idItem) {

		Optional<Cliente> optionalCliente = clienteService.getClienteById(idUser);

		if (!optionalCliente.isPresent()) {
			return ErroCliente.erroClienteNaoEncontrado(idUser);
		}

		Optional<Produto> optionalProduto = produtoService.getProdutoById(idItem);

		if (!optionalProduto.isPresent()) {
			return ErroProduto.erroProdutoNaoEncontrado(idItem);
		}

		ItemCarrinho itemCarrinho = carrinhoService.getItemCarrinhoById(idUser, idItem);

		if (itemCarrinho == null) {
			return ErroCarrinho.erroItemNaoExisteNoCarrinho(idItem);
		}

		carrinhoService.removerItemCarrinho(idUser, itemCarrinho);

		return new ResponseEntity<ItemCarrinho>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{idUser}/carrinho/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizarItemCarrinho(@PathVariable("idUser") long idUser, @PathVariable("id") long idItem, @RequestBody int numItens) {

		Optional<Cliente> optionalCliente = clienteService.getClienteById(idUser);

		if (!optionalCliente.isPresent()) {
			return ErroCliente.erroClienteNaoEncontrado(idUser);
		}

		Optional<Produto> optionalProduto = produtoService.getProdutoById(idItem);

		if (!optionalProduto.isPresent()) {
			return ErroProduto.erroProdutoNaoEncontrado(idItem);
		}

		ItemCarrinho itemCarrinho = carrinhoService.getItemCarrinhoById(idUser, idItem);

		if (itemCarrinho == null) {
			return ErroCarrinho.erroItemNaoExisteNoCarrinho(idItem);
		}

		List<Lote> lotes = loteService.listarLotes();
		List<Lote> lotesDoProduto = new ArrayList<Lote>();

		for (Lote lote: lotes) {
			long id = lote.getProduto().getId();
			if (idItem == id) {
				lotesDoProduto.add(lote);
			}
		}

		int quantMax = 0;

		for (Lote lote : lotesDoProduto) {
			quantMax += lote.getNumeroDeItens();
		}

		if (quantMax < numItens) {
			return ErroCarrinho.erroQuantidadeNaoDisponivel();
		}

		ItemCarrinho item = carrinhoService.atualizaItemCarrinho(idUser, idItem, numItens);

		return new ResponseEntity<ItemCarrinho>(item, HttpStatus.OK);
	}

	@RequestMapping(value = "/{idUser}/carrinho", method = RequestMethod.DELETE)
	public ResponseEntity<?> limparCarrinho(@PathVariable("idUser") long idUser) {

		Optional<Cliente> optionalCliente = clienteService.getClienteById(idUser);

		if (!optionalCliente.isPresent()) {
			return ErroCliente.erroClienteNaoEncontrado(idUser);
		}

		if (carrinhoService.isCarrinhoLimpo(idUser)) {
			return ErroCarrinho.erroCarrinhoVazio(idUser);
		}

		carrinhoService.limparCarrinho(idUser);

		return new ResponseEntity<ItemCarrinho>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{idUser}/carrinho", method = RequestMethod.PUT)
	public ResponseEntity<?> finalizarCompra(@PathVariable("idUser") long idUser, @RequestBody String metodoCompra, UriComponentsBuilder ucBuilder) {

		Optional<Cliente> optionalCliente = clienteService.getClienteById(idUser);

		if (!optionalCliente.isPresent()) {
			return ErroCliente.erroClienteNaoEncontrado(idUser);
		}

		if (carrinhoService.isCarrinhoLimpo(idUser)) {
			return ErroCarrinho.erroCarrinhoVazio(idUser);
		}

		String mensagemSaida = "COMPRA NO MERCADOFACIL\n" + "-------------------------------------------------------------\n" + "PRODUTOS:\n";
		BigDecimal precoFinal = new BigDecimal(0);

		List<ItemCarrinho> carrinho = carrinhoService.listarItensCarrinho(idUser);
		List<ItemVenda> listaCompra = new ArrayList<>();
		Integer quantIntens = 0;

		for (ItemCarrinho item: carrinho) {
			precoFinal = precoFinal.add(item.getPrecoItem().multiply(new BigDecimal(item.getQuantidadeItem())));
			mensagemSaida += "Produto: " + item.getNomeItem() + "     R$" + item.getPrecoItem() + " x Quantidade: " + item.getQuantidadeItem() + "\n";
			listaCompra.add(compraService.criarItemVenda(item.getQuantidadeItem(), item.getNomeItem(), item.getPrecoItem()));
			quantIntens += item.getQuantidadeItem();
		}

		Compra compra;

		try {
			compra = compraService.criarCompra(precoFinal, metodoCompra, listaCompra);
			compraService.atualizaPrecoCompra(compra, clienteService.aplicaDesconto(optionalCliente.get(), compra.getValor(), quantIntens));
		}
		catch (IllegalArgumentException e) {
			return ErroPagamento.erroTipoPagamentoInvalido();
		}

		mensagemSaida += "PREÇO FINAL DA COMPRA: R$" + compra.getValor() + "\n";
		mensagemSaida += "------------------------------------------------------------\n\n";

		compraService.salvarCompra(compra);

		carrinhoService.limparCarrinho(idUser);

		return new ResponseEntity<String>(mensagemSaida, HttpStatus.OK);
	}
}
