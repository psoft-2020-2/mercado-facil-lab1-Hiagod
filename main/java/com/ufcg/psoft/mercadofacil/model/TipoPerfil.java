package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class TipoPerfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;

    public TipoPerfil() { }

    public TipoPerfil(String perfil) {
        if (!((perfil.equals("normal") || perfil.equals("especial") || perfil.equals("premium")))) {
            throw  new IllegalArgumentException("tipo de usuário inválido");
        }
        this.usuario = perfil;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setPerfil(String perfil) {
        if (!((perfil.equals("normal") || perfil.equals("especial") || perfil.equals("premium")))) {
            throw  new IllegalArgumentException("tipo de usuário inválido");
        }
        this.usuario = perfil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoPerfil that = (TipoPerfil) o;
        return id.equals(that.id) && usuario.equals(that.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario);
    }

    @Override
    public String toString() {
        return "TipoUsuario{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}
