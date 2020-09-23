package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.model.*;

import com.ufcg.psoft.mercadofacil.repositories.*;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CarrinhoController {

    // JPAS Repository
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CarrinhoRepository carrinhoRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    LoteRepository loteRepository;
    @Autowired
    CompraRepository compraRepository;

    // Recebe o objeto produto(apenas id) em pedido
    // "produto": { "id": x }
    // id produto gerado aleatoriamente(opcional)
    @RequestMapping(value = "/usuario/{id}/pedido", method = RequestMethod.POST)
    public ResponseEntity<?> adicionarPedido(@PathVariable("id") long id,
                                             @RequestBody Pedido pedido) {

        // Verifica se carrinho/usuario existe
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(id);

        if (!optionalCarrinho.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuario nao encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        // Valida Quantidade
        if (pedido.getQuantidade() <= 0) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Quantidade invalida"),
                    HttpStatus.CONFLICT);
        }

        // Verifica se produto existe
        Optional<Produto> optionalProduto = produtoRepository.findById(pedido.getProduto().getId());
        if (!optionalProduto.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto nao encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        // Verifica se o produto ja está no carrinho
        // Verifica disponibilidade/quantidade do produto
        Produto produto = optionalProduto.get();
        Pedido existePedido = pedidoRepository.findPedidoByProdutoId(produto.getId());
        Lote lote = loteRepository.findLoteByProdutoId(produto.getId());

        if (existePedido != null){
            int soma = pedido.getQuantidade() + existePedido.getQuantidade();
            existePedido.setQuantidade(soma);

            if (lote.getNumeroDeItens() < existePedido.getQuantidade()) {
                return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto insuficiente"),
                        HttpStatus.NOT_FOUND);
            }
            pedidoRepository.save(existePedido);
            return new ResponseEntity<>(existePedido, HttpStatus.ACCEPTED);
        }

        // Verifica disponibilidade/quantidade de produto inexistente
        if (lote.getNumeroDeItens() < pedido.getQuantidade()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto insuficiente"),
                    HttpStatus.NOT_FOUND);
        }
        pedido.setProduto(produto);
        pedidoRepository.save(pedido);
        Carrinho carrinho = optionalCarrinho.get();
        carrinho.setPedidos(carrinho.adicionaPedido(pedido));
        carrinhoRepository.save(carrinho);

        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    // Desfaz(Reinicia) o caarrinho sem salvar a compra
    @RequestMapping(value = "/usuario/{id}/desfaz", method = RequestMethod.PUT)
    public ResponseEntity<?> desfazCarrinho(@PathVariable("id") long id) {
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(id);

        if (!optionalCarrinho.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuario nao encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        Carrinho carrinho = optionalCarrinho.get();
        carrinho.reiniciaCarrinho();
        carrinhoRepository.save(carrinho);

        return new ResponseEntity<>(carrinho, HttpStatus.OK);
    }

    @RequestMapping(value = "/usuario/{id}/compra", method = RequestMethod.POST)
    public ResponseEntity<?> compraCarrinho(@PathVariable("id") long id, @RequestBody Compra compra,
                                            UriComponentsBuilder ucBuilder) {
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(id);

        if (!optionalCarrinho.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuario nao encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        Usuario usuario = optionalUsuario.get();
        Carrinho carrinho = optionalCarrinho.get();
        // Verifica Carrinho vazio
        if (carrinho.getPedidos().isEmpty()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("O carrinho esta vazio"),
                    HttpStatus.NOT_FOUND);
        }

        List<Pedido> pedidos = carrinho.getPedidos();
        compra.setPedidos(pedidos);
        BigDecimal total = carrinho.calculaTotal();
        compra.setValor(total);

        for (Pedido p : pedidos) {
            Lote lote = loteRepository.findLoteByProdutoId(p.getProduto().getId());
            int numeroItens = lote.getNumeroDeItens() - p.getQuantidade();
            lote.setNumeroDeItens(numeroItens);
            loteRepository.save(lote);

            if (numeroItens == 0) {
                Produto produto = p.getProduto();
                produto.situacao = 2;
                produtoRepository.save(produto);
            }

        }

        compraRepository.save(compra);
        usuario.setCompras(usuario.adicionaCompra(compra));
        usuarioRepository.save(usuario);

        carrinho.reiniciaCarrinho();
        carrinhoRepository.save(carrinho);
        return new ResponseEntity<>(compra, HttpStatus.CREATED);
    }



}
