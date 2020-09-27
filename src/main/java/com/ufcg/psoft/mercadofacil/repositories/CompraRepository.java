package com.ufcg.psoft.mercadofacil.repositories;

import com.ufcg.psoft.mercadofacil.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findCompraByUsuarioId(long id);
}
