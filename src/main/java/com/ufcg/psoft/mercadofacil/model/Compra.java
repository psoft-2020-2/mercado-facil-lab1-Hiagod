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

    private String data;
    private BigDecimal valorDaVenda;
    
    @OneToOne
    private Pagamento pagamento;

    
    @OneToMany
    private List<ItemVenda> itensDaCompra;
    
   

    public Compra() { }

    public Compra(BigDecimal valorDaVenda, String pagamento, List<ItemVenda> itensDaVenda) {
        this.itensDaCompra = itensDaCompra;
        this.valorDaVenda = valorDaVenda;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        this.data = df.format(new Date());
        
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public List<ItemVenda> getItensDaVenda() {
        return itensDaCompra;
    }
   

    public BigDecimal getValor() {
        return valorDaVenda;
    }
    
    public void setValor(BigDecimal novoValor) {
        this.getPagamento().setValor(novoValor);
    }
        
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compra compra = (Compra) o;
        return data.equals(compra.data) && itensDaCompra.equals(compra.itensDaCompra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, itensDaCompra);
    }

    @Override
    public String toString() {
        String intermed = "";
        for (ItemVenda item: getItensDaVenda()) {
            intermed += "    " + item.toString() + ",\n";
        }
        return "{\n" +
                "  \"id\": " +  getId() + ",\n" +
                "  \"data\": \""+  getData() + "\",\n" +
                "  \"itensDaVenda\": [\n" +
                intermed.substring(0, intermed.length()-2) +
                "\n  ]\n" +
                "}";
    }
}