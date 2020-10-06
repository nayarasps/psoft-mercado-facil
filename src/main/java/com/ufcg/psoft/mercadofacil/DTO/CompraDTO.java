package com.ufcg.psoft.mercadofacil.DTO;

public class CompraDTO {

    /*
    Pagamentos em:
    - Boleto
    - CartaoCredito
    - PayPal
     */
    private String pagamento;

    public CompraDTO() {}

    public CompraDTO(String pagamento){
        super();
        this.pagamento = pagamento;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }
}
