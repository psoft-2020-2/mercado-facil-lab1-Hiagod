package com.ufcg.psoft.mercadofacil.service;

import java.util.List;

import com.ufcg.psoft.mercadofacil.DTO.LoteDTO;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;

public interface LoteService {

	public List<Lote> listarLotes();
	
	void aumentaQuantidade(Long id, LoteDTO loteDTO);

    void notificaInteressados(long idItem);

	public void salvarLote(Lote lote);

	public Lote criaLote(LoteDTO loteDTO, Produto produto);

	public String exibeValidade(long id);

	public void editarValidade(long id, LoteDTO loteDTO);
}