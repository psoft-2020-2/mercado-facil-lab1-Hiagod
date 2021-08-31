package com.ufcg.psoft.mercadofacil.DTO;

public class ClienteDTO {

	private Long cpf;
	
	private String nome;

	private Integer idade;

	private String endereco;
	
	private String tipoUsuario;

	public String getNome() {
		return nome;
	}
    public void setNome(String nome) {
        this.nome = nome;
    }
	
	public Long getCPF() {
		return cpf;
	}
    public void setCPF(Long cpf) {
        this.cpf = cpf;
    }
	public Integer getIdade() {
		return idade;
	}

	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
	public String getTipoUsuario() {
	    return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
}
