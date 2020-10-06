package com.ufcg.psoft.mercadofacil.model.Usuario;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Compra;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO")
public abstract class Usuario {
    @Id
    private long id;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Carrinho carrinho;

    @OneToMany
    private List<Compra> compras;

    public Usuario() {
        super();
        this.compras = new ArrayList<Compra>();
    }

    public Usuario(long id, Carrinho carrinho, List<Compra> compras) {
        super();
        this.id = id;
        this.carrinho = carrinho;
        this.compras = compras;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Carrinho getCarrinho() {
        return this.carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public List<Compra> getCompras() {
        return this.compras;
    }

    public List<Compra> adicionaCompra(Compra compra) {
        this.compras.add(compra);
        return this.compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public List<String> compraSimples(List<Compra> compra) {
        List<String> simples = new ArrayList<>();
        for (Compra c : compra) {
            simples.add(c.getId() + " - " + c.getData() + " - " + c.getProdutos().toString() + " - " + c.getValor());
        }
        return simples;
    }

    public List<String> compraDetalhada(Compra compra) {
        List<String> detalhada = new ArrayList<>();
        detalhada.add(compra.getId() + " - " + compra.getData() + " - " + compra.getProdutos()+ " - " +
                compra.getValor() + " - " + compra.getPagamento().toString());

        return detalhada;
    }

    public abstract double descontoCompras(double valor, int quantidade);

}
