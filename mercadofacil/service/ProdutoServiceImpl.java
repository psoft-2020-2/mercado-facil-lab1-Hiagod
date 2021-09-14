package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.DTO.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ClienteRepository clienteRepository;


	public Optional<Produto> getProdutoById(long id) {
		return produtoRepository.findById(id);
	}

	public List<Produto> getProdutoByCodigoBarra(String codigo) {
		return produtoRepository.findByCodigoBarra(codigo);
	}

	public void removerProdutoCadastrado(Produto produto) {
		produtoRepository.delete(produto);
	}

	public void salvarProdutoCadastrado(Produto produto) {
		produtoRepository.save(produto);
	}

	public List<Produto> listarProdutos() {
		return produtoRepository.findAll();
	}

	public Produto criaProduto(ProdutoDTO produtoDTO) {
		Produto produto = new Produto(produtoDTO.getNome(),produtoDTO.getPreco(), produtoDTO.getCodigoBarra(), produtoDTO.getFabricante(),
				produtoDTO.getCategoria(), produtoDTO.getDescricao(), produtoDTO.getTipo());

		produto.tornaDisponivel();
		return produto;
	}
	public Produto atualizaProduto(ProdutoDTO produtoDTO, Produto produto) {
		produto.setNome(produtoDTO.getNome());
		produto.setPreco(produtoDTO.getPreco());
		produto.setCodigoBarra(produtoDTO.getCodigoBarra());
		produto.mudaFabricante(produtoDTO.getFabricante());
		produto.mudaCategoria(produtoDTO.getCategoria());
		produto.mudaDescricao(produtoDTO.getDescricao());

		return produto;
	}

	public String descricaoProduto(Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		return produto.get().getDescricao();
	}

	public void editarDescricao(Long id, ProdutoDTO produtoDTO) {
		Optional<Produto> production = produtoRepository.findById(id);
		Produto produto = production.get();
		produto.mudaDescricao(produtoDTO.getDescricao());

		produtoRepository.save(produto);
	}

	public String exibeDescricao(Long id) {
		return produtoRepository.findById(id).get().getDescricao();
	}
	
	public void adicionaPromocaoProduto(Long id, ProdutoDTO produtoDTO) {
		Optional<Produto> production = produtoRepository.findById(id);
		Produto produto = production.get();
		produto.setPreco(produtoDTO.getPreco());

		produtoRepository.save(produto);

	}

	@Override
	public void notificaInteressados(long idItem) {

		List<Cliente> clientes = produtoRepository.findById(idItem).get().interessadosPromocao();

		String mensagem = " · O Produto que você demonstrou Interesse está em promoção! Dados do Produto - > ";
		mensagem += produtoRepository.findById(idItem).get().toString();

		for (Cliente cliente: clientes){

			cliente.adicionaNotificação(mensagem);
			clienteRepository.save(cliente);
		}
	}
}