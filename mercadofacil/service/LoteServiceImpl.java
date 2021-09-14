package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.DTO.LoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;

@Service
public class LoteServiceImpl implements LoteService {

	@Autowired
	private LoteRepository loteRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Lote> listarLotes() {
		return loteRepository.findAll();
	}

	public void salvarLote(Lote lote) {
		loteRepository.save(lote);
	}

	public Lote criaLote(LoteDTO loteDTO, Produto produto) {
		Lote lote = new Lote(produto,loteDTO.getNumItens(), loteDTO.getValidade());
		return lote;
		
	}
	
	@Override
	public void aumentaQuantidade(Long id, LoteDTO loteDTO) {
		Optional<Lote> loteOptional = loteRepository.findById(id);
		Lote lote = loteOptional.get();
		lote.setNumeroDeItens(loteDTO.getNumItens());

		loteRepository.save(lote);

	}

	@Override
	public void notificaInteressados(long idItem) {

		List<Cliente> clientes = loteRepository.findById(idItem).get().getInteressados();

		String mensagem = " · O Produto que você demonstrou Interesse está em disponível! Dados do Produto - > ";
		mensagem += loteRepository.findById(idItem).get().toString();

		for (Cliente cliente: clientes){

			cliente.adicionaNotificação(mensagem);
			clienteRepository.save(cliente);
		}
	}
	public String exibeValidade(long id) {
		return loteRepository.findById(id).get().getValidade();
	}

	public void editarValidade(long id, LoteDTO loteDTO) {
		Optional<Lote> loteOptional = loteRepository.findById(id);
		Lote lote = loteOptional.get();
		lote.setValidade(loteDTO.getValidade());

		loteRepository.save(lote);

	}
}