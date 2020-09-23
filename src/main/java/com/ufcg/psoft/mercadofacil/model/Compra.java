package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import exceptions.PagamentoInvalidoException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private String data;

    @OneToMany
    private List<Pedido> pedidos;

    public int pagamento; // usa variaveis estaticas abaixo
    /* formas de pagamento */
    public static final int BOLETO = 1;
    public static final int PAYPAL = 2;
    public static final int CARTAO = 3;

    public BigDecimal valor;

    public Compra() {super();}

    public Compra(long id, String data, int pagamento, List<Pedido> pedidos, BigDecimal valor) {
        super();
        this.id = id;
        this.data = data;
        this.pagamento = pagamento;
        this.pedidos = pedidos;
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

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Set<String> getPedidos() {
        Set<String> compras = new HashSet<String>();
        for (Pedido p: pedidos) {
            compras.add(p.getProduto().getNome() + " - " + p.getProduto().getFabricante());
        }
        return compras;
    }

    public long getId() {
        return this.id;
    }

    public String getData() {
        return this.data;
    }




}
