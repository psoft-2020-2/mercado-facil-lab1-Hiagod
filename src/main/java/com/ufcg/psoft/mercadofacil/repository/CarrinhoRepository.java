package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CarrinhoRepository extends JpaRepository<Carrinho, Long>{

	List<Carrinho> findByIdUser(long idUser);
}
