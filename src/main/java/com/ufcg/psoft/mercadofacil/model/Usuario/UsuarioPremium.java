package com.ufcg.psoft.mercadofacil.model.Usuario;

import com.ufcg.psoft.mercadofacil.model.Compra;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value="Premium")
public class UsuarioPremium extends Usuario{

    public UsuarioPremium(long id) {
        super();
        super.setId(id);
    }

    public UsuarioPremium() {
        super();
    }

    @Override
    public double descontoCompras(double valor, int quantidade) {
        if (quantidade > 5) {
            return valor * 0.9;
        }
        return valor;
    }

    @Override
    public String toString() {
        return "Premium";
    }
}
