package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long CPF;
	
	private String nome;

	private Integer idade;

	private String endereco;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	private TipoPerfil tipo;

	private Cliente() {}

	public Cliente(Long CPF, String nome, Integer idade, String endereco, String tipoPerfil) {
		this.CPF = CPF;
		this.nome = nome;
		this.idade = idade;
		this.endereco = endereco;
		this.tipo = new TipoPerfil(tipoPerfil);

	}

	public Long getId() {
		return id;
	}

	public Long getCpf() {
		return CPF;
	}
	
	public void setCPF(Long cpf) {
        this.CPF = cpf;
    }

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
        this.nome = nome;
    }

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public TipoPerfil getTipo() {
		return tipo;
	}

	public void setTipo(String perfil) {
		getTipo().setPerfil(perfil);
	}
}
