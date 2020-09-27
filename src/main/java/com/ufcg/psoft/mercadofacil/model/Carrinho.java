package com.ufcg.psoft.mercadofacil.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrinho {
    @Id
    private long id;

    @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Pedido> pedidos = new ArrayList<Pedido>();

    @OneToOne
    @MapsId
    private Usuario usuario;

    public Carrinho() {
        super();
    }

    public Carrinho(long id) {
        super();
        this.id = id;
    }

    public List<Pedido> getPedidos() {
        return this.pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos.clear();
        this.pedidos.addAll(pedidos);
    }

    public List<Pedido> adicionaPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        return this.pedidos;
    }

    public double calculaTotal() {
        double total = 0;
        for (Pedido p: this.pedidos){
            double preco = p.getProduto().getPreco().doubleValue();
            total = preco * p.getQuantidade();
        }
        return total;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
