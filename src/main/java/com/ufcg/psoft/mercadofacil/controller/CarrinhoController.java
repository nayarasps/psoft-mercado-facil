package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.DTO.CompraDTO;
import com.ufcg.psoft.mercadofacil.DTO.PedidoDTO;
import com.ufcg.psoft.mercadofacil.model.*;

import com.ufcg.psoft.mercadofacil.repositories.*;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import exceptions.PagamentoInvalidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    // id produto gerado aleatoriamente(opcional)
    @RequestMapping(value = "/usuario/{id}/pedido", method = RequestMethod.POST)
    public ResponseEntity<?> adicionarPedido(@PathVariable("id") long id,
                                             @RequestBody PedidoDTO pedidoDTO) {

        // Verifica se carrinho/usuario existe
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(id);
        Carrinho carrinho = optionalCarrinho.get();

        if (!optionalCarrinho.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuario nao encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        // Valida Quantidade
        if (pedidoDTO.getQuantidade() <= 0) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Quantidade invalida"),
                    HttpStatus.CONFLICT);
        }

        Pedido pedido = new Pedido();
        pedido.setCarrinho(carrinho);
        pedido.setQuantidade(pedidoDTO.getQuantidade());

        // Verifica se produto existe
        Optional<Produto> optionalProduto = produtoRepository.findById(pedidoDTO.getProduto());
        if (!optionalProduto.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto nao encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        // Verifica se o produto ja está no carrinho
        Produto produto = optionalProduto.get();
        pedido.setProduto(produto);
        Lote lote = loteRepository.findLoteByProdutoId(produto.getId());
        Pedido existePedido = pedidoRepository.findPedidoByProdutoId(produto.getId());

        // Verifica Quantidade valida de produto - Produto ja no carrinho
        if (existePedido != null){
            int soma = pedido.getQuantidade() + existePedido.getQuantidade();
            existePedido.setQuantidade(soma);

            if (lote.getNumeroDeItens() < existePedido.getQuantidade()) {
                return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto insuficiente"),
                        HttpStatus.NOT_FOUND);
            }

            pedidoRepository.save(existePedido);

            return new ResponseEntity<>(existePedido, HttpStatus.OK);
        }

        // Verifica Quantidade valida de produto
        if (lote.getNumeroDeItens() < pedido.getQuantidade()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Produto insuficiente"),
                    HttpStatus.NOT_FOUND);
        }

        pedido.setProduto(produto);
        pedidoRepository.save(pedido);
        carrinho.setPedidos(new ArrayList<>(carrinho.adicionaPedido(pedido)));
        carrinhoRepository.save(carrinho);

        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    // Desfaz(Reinicia) o carrinho sem salvar a compra
    @RequestMapping(value = "/usuario/{id}/desfaz", method = RequestMethod.PUT)
    public ResponseEntity<?> desfazCarrinho(@PathVariable("id") long id) {
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(id);

        if (!optionalCarrinho.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuario nao encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        carrinhoRepository.deleteById(id);
        Carrinho car = new Carrinho(id);
        carrinhoRepository.save(car);

        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @RequestMapping(value = "/usuario/{id}/compra", method = RequestMethod.POST)
    public ResponseEntity<?> compraCarrinho(@PathVariable("id") long id, @RequestBody CompraDTO compraDTO) throws PagamentoInvalidoException {

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

        Compra compra = new Compra();
        String data = compra.gerarData();
        compra.setData(data);
        compra.setPagamento(compraDTO.getPagamento());

        List<Pedido> pedidos = carrinho.getPedidos();
        compra.setValor(carrinho.calculaTotal());

        for (Pedido p : pedidos) {
            Lote lote = loteRepository.findLoteByProdutoId(p.getProduto().getId());
            int numeroItens = lote.getNumeroDeItens() - p.getQuantidade();
            lote.setNumeroDeItens(numeroItens);
            loteRepository.save(lote);

            if (numeroItens == 0) {
                Produto produto = p.getProduto();
                produto.situacao = Produto.INDISPONIVEL;
                produtoRepository.save(produto);
            }
            compra.setProdutos(new HashSet<>(compra.adicionaProduto(p.getProduto())));
        }
        compra.setUsuario(usuarioRepository.findUsuarioById(id));
        compraRepository.save(compra);
        usuario.setCompras(usuario.adicionaCompra(compra));
        usuarioRepository.save(usuario);

        carrinhoRepository.deleteById(id);
        Carrinho car = new Carrinho(id);
        carrinhoRepository.save(car);

        return new ResponseEntity<>(compra, HttpStatus.CREATED);
    }



}
