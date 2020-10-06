package com.ufcg.psoft.mercadofacil.repositories;

import com.ufcg.psoft.mercadofacil.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.mercadofacil.model.Lote;

import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long>{
    Lote findLoteByProdutoId(long id);
}