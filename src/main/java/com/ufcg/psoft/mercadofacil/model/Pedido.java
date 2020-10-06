package com.ufcg.psoft.mercadofacil.model;


import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;

import javax.persistence.*;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Produto produto;
    private int quantidade;

    @ManyToOne
    private Carrinho carrinho;

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

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    @Override
    public String toString() {
        return produto.getNome();
    }

}
