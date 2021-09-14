package com.ufcg.psoft.mercadofacil.model;


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

    private String formatoDePagamento;

    private BigDecimal valor;


    public Pagamento() { }

    public Pagamento(String formatoDePagamento, BigDecimal valor) {
        if (!(formatoDePagamento.equals("boleto") || formatoDePagamento.equals("paypal") || formatoDePagamento
                .equals("cartão de crédito"))) {
            throw new IllegalArgumentException("método de pagamento inválido");
        }
        this.formatoDePagamento = formatoDePagamento;
        this.valor = valor.add(getAcrescimo(valor));
    }

    public String getFormatoDePagamento() {
        return formatoDePagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal novoValor) {
        this.valor = novoValor;
    }


    public BigDecimal getAcrescimo(BigDecimal valor) {
        BigDecimal acrescimo = new BigDecimal(0);

        switch (formatoDePagamento) {
            case "paypal":
                acrescimo = valor.multiply(new BigDecimal("0.02"));
                break;
            case "cartão de crédito":
                acrescimo = valor.multiply(new BigDecimal("0.05"));
                break;
            default:
                break;
        }

        return acrescimo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return id.equals(pagamento.id) && formatoDePagamento.equals(pagamento.formatoDePagamento) && valor.equals(pagamento.valor) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, formatoDePagamento, valor);
    }

    @Override
    public String toString() {
        return  "{\n" +
                "    \"formatoDePagamento\": \"" +  formatoDePagamento + "\",\n" +
                "    \"valor\": "  + valor + ",\n" +
                "  }";
    }
}
