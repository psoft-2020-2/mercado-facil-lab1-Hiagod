package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class TipoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    public TipoProduto() { }

    public TipoProduto(String tipo) {
        if (!((tipo.equals("refrigerado") || tipo.equals("fragil")))) {
            this.tipo = "comum";
        }
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoProduto that = (TipoProduto) o;
        return id.equals(that.id) && tipo.equals(that.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipo);
    }

    @Override
    public String toString() {
        return "TipoProduto{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}