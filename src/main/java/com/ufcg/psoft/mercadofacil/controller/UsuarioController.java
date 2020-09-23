package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.Usuario;
import com.ufcg.psoft.mercadofacil.repositories.CarrinhoRepository;
import com.ufcg.psoft.mercadofacil.repositories.UsuarioRepository;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.yaml.snakeyaml.emitter.ScalarAnalysis;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin

public class UsuarioController {

    // JPAS Repository
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @RequestMapping(value = "/usuario/", method = RequestMethod.POST)
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario, UriComponentsBuilder ucBuilder) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuario.getId());

        if (optionalUsuario.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("O usuario " + usuario.getId()
                    + " ja esta cadastrado!"), HttpStatus.CONFLICT);
        }

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(usuario);
        carrinhoRepository.save(carrinho);
        usuarioRepository.save(usuario);




        return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
    }


}
