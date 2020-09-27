package com.ufcg.psoft.mercadofacil.repositories;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

}
