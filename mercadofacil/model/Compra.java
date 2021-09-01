package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Objects;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Pagamento pagamento;

    @OneToMany
    private List<ItemVenda> itensDaVenda;

    public Compra() { }

    public Compra(BigDecimal valorDaVenda, String pagamento, List<ItemVenda> itensDaVenda) {
        this.itensDaVenda = itensDaVenda;
        this.pagamento = new Pagamento(pagamento, valorDaVenda);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<ItemVenda> getItensDaVenda() {
        return itensDaVenda;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public BigDecimal getValor() {
        return getPagamento().getValor();
    }

    public void setValor(BigDecimal novoValor) {
        this.getPagamento().setValor(novoValor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compra compra = (Compra) o;
        return  pagamento.equals(compra.pagamento) && itensDaVenda.equals(compra.itensDaVenda);
    }

    @Override
    public int hashCode() {
        return Objects.hash( pagamento, itensDaVenda);
    }

    @Override
    public String toString() {
        String intermed = "";
        for (ItemVenda item: getItensDaVenda()) {
            intermed += "    " + item.toString() + ",\n";
        }
        return "{\n" +
                "  \"id\": " +  getId() + ",\n" +
                "  \"pagamento\": " +  this.pagamento + ",\n" +
                "  \"itensDaVenda\": [\n" +
                intermed.substring(0, intermed.length()-2) +
                "\n  ]\n" +
                "}";
    }
}