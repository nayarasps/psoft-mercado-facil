package com.ufcg.psoft.mercadofacil.model;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToOne
    private Produto produto;
    private int quantidade;

    public Pedido() {
        super();
    }

    public Pedido(long id, Produto produto, int quantidade) {
        super();
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public long getId() {
        return this.id;
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return produto.getNome();
    }

}
