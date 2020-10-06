package com.ufcg.psoft.mercadofacil.model.Usuario;

import com.ufcg.psoft.mercadofacil.model.Compra;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue(value="Normal")
public class UsuarioNormal extends Usuario{

    public UsuarioNormal(long id) {
        super();
        super.setId(id);
    }

    public UsuarioNormal() {
        super();
    }

    @Override
    public String toString() {
        return "Normal";
    }

    @Override
    public double descontoCompras(double valor, int quantidade) {
        return valor;
    }

}
