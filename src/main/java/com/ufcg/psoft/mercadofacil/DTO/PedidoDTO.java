package com.ufcg.psoft.mercadofacil.DTO;

public class PedidoDTO {

    private long produto;
    private int quantidade;

    public PedidoDTO() {}

    public PedidoDTO(long produto, int quantidade) {
        super();
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public long getProduto() {
        return produto;
    }

    public void setProduto(long produto) {
        this.produto = produto;
    }
}
