package com.ufcg.psoft.mercadofacil.model.Pagamento;

import org.springframework.stereotype.Component;

@Component
public class Boleto extends Pagamento {

    @Override
    public double getValor(double valor) {
        return valor;
    }
}
