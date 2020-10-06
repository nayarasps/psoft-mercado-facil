package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.DTO.LoteDTO;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repositories.LoteRepository;
import com.ufcg.psoft.mercadofacil.repositories.ProdutoRepository;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import exceptions.ObjetoInvalidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin

public class LoteController {

    // JPAS Repository
    @Autowired
    private LoteRepository loteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @RequestMapping(value = "/produto/{id}/lote", method = RequestMethod.POST)
    public ResponseEntity<?> criarLote(@PathVariable("id") long id, @RequestBody LoteDTO loteDTO) {

        Optional<Produto> optionalProduto = produtoRepository.findById(id);

        if (!optionalProduto.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto with id " + id + " not found"),
                    HttpStatus.NOT_FOUND);
        }

        Produto product = optionalProduto.get();
        Lote lote = new Lote(product, loteDTO.getNumeroDeItens(), loteDTO.getDataDeValidade());

        loteRepository.save(lote);

        try {
            if (product.getSituacao() == Produto.INDISPONIVEL) {
                if (loteDTO.getNumeroDeItens() > 0) {
                    Produto produtoDisponivel = product;
                    produtoDisponivel.situacao = Produto.DISPONIVEL;
                    produtoRepository.save(produtoDisponivel);
                }
            }
        } catch (ObjetoInvalidoException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(lote, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/lotes", method = RequestMethod.GET)
    public ResponseEntity<?> listarLotes() {

        List<Lote> lotes = loteRepository.findAll();

        if (lotes.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // Outro código de erro pode ser retornado HttpStatus.NOT_FOUND
        }

        return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
    }

}
