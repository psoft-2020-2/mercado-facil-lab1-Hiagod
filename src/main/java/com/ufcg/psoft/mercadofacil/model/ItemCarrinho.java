package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class ItemCarrinho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long idItem;

	private int quantidadeItem;

	private String nomeItem;

	private BigDecimal precoItem;

	public ItemCarrinho() { }

	public ItemCarrinho(long id, long idItem, int quantidadeItem, String nomeItem, BigDecimal precoItem) {
		super();
		this.id = id;
		this.idItem = idItem;
		this.quantidadeItem = quantidadeItem;
		this.nomeItem = nomeItem;
		this.precoItem = precoItem;
	}

	public ItemCarrinho(long idItem, int quantidadeItem, String nomeItem, BigDecimal precoItem) {
		super();
		this.idItem = idItem;
		this.quantidadeItem = quantidadeItem;
		this.nomeItem = nomeItem;
		this.precoItem = precoItem;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public int getQuantidadeItem() {
		return quantidadeItem;
	}

	public void setQuantidadeItem(int quantidadeItem) {
		this.quantidadeItem = quantidadeItem;
	}

	public String getNomeItem() {
		return nomeItem;
	}

	public void setNomeItem(String nomeItem) {
		this.nomeItem = nomeItem;
	}

	public BigDecimal getPrecoItem() {
		return precoItem;
	}

	public void setPrecoItem(BigDecimal precoItem) {
		this.precoItem = precoItem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idItem == null) ? 0 : idItem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemCarrinho other = (ItemCarrinho) obj;
		if (idItem == null) {
			if (other.idItem != null)
				return false;
		} else if (!idItem.equals(other.idItem))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "ItemCarrinho [idItem=" + idItem + ", quantidadeItem=" + quantidadeItem + "]";
	}


}