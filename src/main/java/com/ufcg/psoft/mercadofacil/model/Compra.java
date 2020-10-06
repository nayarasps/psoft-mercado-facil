package com.ufcg.psoft.mercadofacil.model;

import com.ufcg.psoft.mercadofacil.model.Usuario.Usuario;
import exceptions.PagamentoInvalidoException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String data;

    @ManyToMany
    private Set<Produto> produtos = new HashSet<Produto>();

    @ManyToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

    private String pagamento;

    public BigDecimal valor;

    public Compra() {super();}

    public Compra(long id, String data, String pagamento, Set<Produto> produtos, BigDecimal valor) {
        super();
        this.id = id;
        this.data = data;
        this.pagamento = pagamento;
        this.produtos = produtos;
        this.valor = valor;
    }

    public void setPagamento(String pagamento) throws PagamentoInvalidoException {
        this.pagamento = pagamento;
    }

    public String getPagamento() {
        return this.pagamento;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Set<Produto> getProdutos() {
        return this.produtos;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public long getId() {
        return this.id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public String gerarData() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        return formatter.format(data);
    }

    public Set<Produto> adicionaProduto(Produto produto) {
        this.produtos.add(produto);
        return this.produtos;
    }

    public String getData() {
        return this.data;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    @Override
    public String toString() {
        return "Data: " + getData() + "\n" + "Valor: " + getValor().toString() + "\n" + "Produtos: " + getProdutos();
    }
}
