package com.ufcg.psoft.mercadofacil.model;

import exceptions.PagamentoInvalidoException;

import javax.persistence.*;
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

    public int pagamento; // usa variaveis estaticas abaixo
    /* formas de pagamento */
    public static final int BOLETO = 1;
    public static final int PAYPAL = 2;
    public static final int CARTAO = 3;

    public double valor;

    public Compra() {super();}

    public Compra(long id, String data, int pagamento, Set<Produto> produtos, double valor) {
        super();
        this.id = id;
        this.data = data;
        this.pagamento = pagamento;
        this.produtos = produtos;
        this.valor = valor;
    }

    public void setPagamento(int pagamento) throws PagamentoInvalidoException {
        switch (pagamento) {
            case 1:
                this.pagamento = Compra.BOLETO;
                break;
            case 2:
                this.pagamento = Compra.PAYPAL;
                break;
            case 3:
                this.pagamento = Compra.CARTAO;
                break;
            default:
                throw new PagamentoInvalidoException("Forma de Pagamento Invalida");
    }
}
    public int getPagamento() {
        return this.pagamento;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Set<Produto> getProdutos() {
        return this.produtos;
    }

    public void setValor(double valor) {
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

    public double getValor() {
        return this.valor;
    }

    public String stringPagamento(int pagamento) {
        if (pagamento == 1) {
            return "BOLETO";
        }
        if (pagamento == 2) {
            return "PAYPAL";
        }
        if (pagamento == 3) {
            return "CARTAO";
        }
        return "invalido";
    }
}
