package com.ufcg.psoft.mercadofacil.DTO;

public class CompraDTO {

    private int pagamento;

    public CompraDTO() {}

    public CompraDTO(int pagamento){
        super();
        this.pagamento = pagamento;
    }

    public int getPagamento() {
        return pagamento;
    }

    public void setPagamento(int pagamento) {
        this.pagamento = pagamento;
    }
}
