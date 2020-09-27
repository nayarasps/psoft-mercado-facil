package com.ufcg.psoft.mercadofacil.repositories;

import com.ufcg.psoft.mercadofacil.model.Pedido;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long>  {
    Pedido findPedidoByProdutoId(long id);

}
