package com.ufcg.psoft.mercadofacil.repositories;

import com.ufcg.psoft.mercadofacil.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findUsuarioById(long id);
}
