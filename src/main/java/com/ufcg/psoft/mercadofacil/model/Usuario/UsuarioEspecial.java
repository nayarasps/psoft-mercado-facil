package com.ufcg.psoft.mercadofacil.model.Usuario;

import com.ufcg.psoft.mercadofacil.model.Compra;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value="Especial")
public class UsuarioEspecial extends Usuario {

    public UsuarioEspecial(long id) {
        super();
        super.setId(id);
    }

    public UsuarioEspecial() {
        super();
    }

    @Override
    public double descontoCompras(double valor, int quantidade) {
        if (quantidade > 10) {
            return valor * 0.9;
        }
        return valor;
    }

    @Override
    public String toString() {
        return "Especial";
    }

}
