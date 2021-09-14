package com.ufcg.psoft.mercadofacil.model;

import java.util.List;
import javax.persistence.*;


@Entity
public class Avisos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;


    public Avisos(String mensagem) {
    }

    public Avisos() {

    }

    public String getMensagem() {
        return mensagem;
    }

    @Override
    public String toString() {
        return "Avisos{" +
                ", mensagem='" + mensagem + '\'' +
                '}';
    }
}

