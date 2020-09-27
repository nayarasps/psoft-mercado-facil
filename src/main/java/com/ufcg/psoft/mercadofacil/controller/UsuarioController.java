package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.Usuario;
import com.ufcg.psoft.mercadofacil.repositories.*;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.yaml.snakeyaml.emitter.ScalarAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin

public class UsuarioController {

    // JPAS Repository
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CarrinhoRepository carrinhoRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    CompraRepository compraRepository;

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

    @RequestMapping(value = "/usuario/{id}/compras", method = RequestMethod.GET)
    public ResponseEntity<?> listarCompras(@PathVariable("id") long id) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (!optionalUsuario.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuario nao encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        List<Compra> compras = new ArrayList<Compra>();
        compras = compraRepository.findCompraByUsuarioId(id);

        if (compras.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        Usuario usuario = optionalUsuario.get();

        List<String> simples = usuario.compraSimples(compras);

        return new ResponseEntity<List<String>>(simples, HttpStatus.OK);
    }

    @RequestMapping(value = "/usuario/{id}/compra/{id_compra}", method = RequestMethod.GET)
    public ResponseEntity<?> consultarProduto(@PathVariable("id") long id, @PathVariable("id_compra") long id_compra) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (!optionalUsuario.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuario nao encontrado"),
                    HttpStatus.NOT_FOUND);
        }
        Usuario usuario = optionalUsuario.get();

        Optional<Compra> optionalCompra = compraRepository.findById(id_compra);
        if (!optionalCompra.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Compra nao encontrada"),
                    HttpStatus.NOT_FOUND);
        }

        List<Compra> compras = new ArrayList<Compra>();
        compras = compraRepository.findCompraByUsuarioId(id);
        if (!compras.contains(optionalCompra.get())) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Compra nao pertence a esse usuario"),
                    HttpStatus.NOT_FOUND);
        }

        List<String> detalhada = usuario.compraDetalhada(optionalCompra.get());

        return new ResponseEntity<List<String>>(detalhada, HttpStatus.OK);
    }

}
