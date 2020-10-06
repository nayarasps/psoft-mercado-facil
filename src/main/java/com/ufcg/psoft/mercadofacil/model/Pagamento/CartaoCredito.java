package com.ufcg.psoft.mercadofacil.model.Pagamento;

import org.springframework.stereotype.Component;

@Component
public class CartaoCredito extends Pagamento{

    @Override
    public double getValor(double valor) {
        return valor * 1.05;
    }
}
