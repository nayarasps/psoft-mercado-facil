package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Carrinho {
    @Id
    private long id;

    @OneToMany
    private List<Pedido> pedidos;

    @OneToOne
    @MapsId
    private Usuario usuario;

    public Carrinho() {
        super();
    }

    public Carrinho(long id) {
        super();
        this.id = id;
        this.pedidos = new ArrayList<Pedido>();
    }

    public List<Pedido> getPedidos() {
        return this.pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Transient
    public List<Pedido> adicionaPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        return this.pedidos;
    }

    @Transient
    public BigDecimal calculaTotal() {
        BigDecimal total = new BigDecimal(0);
        for (Pedido p: this.pedidos){
            total.add(p.getProduto().getPreco().multiply(new BigDecimal(p.getQuantidade())));
        }
        return total;
    }

    public void reiniciaCarrinho() {
        List<Pedido> pedidos = new ArrayList<Pedido>();
        this.pedidos = pedidos;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }



}
