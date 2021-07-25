package com.ufcg.psoft.mercadofacil.model;

import com.ufcg.psoft.mercadofacil.util.ErroCompra;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;

    private String data;

    public Pagamento() { }

    public Pagamento(BigDecimal valor, String data) {
        this.valor = valor;
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal novoValor) {
        this.valor = novoValor;
    }

    public String getData() {
        return data;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return id.equals(pagamento.id) && valor.equals(pagamento.valor) && data.equals(pagamento.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valor, data);
    }

    @Override
    public String toString() {
        return  "{\n" +
                "    \"valor\": "  + valor + ",\n" +
                "    \"data\": \"" +  getData() + "\"\n" +
                "  }";
    }
}
