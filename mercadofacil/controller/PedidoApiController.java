package com.ufcg.psoft.mercadofacil.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import com.ufcg.psoft.mercadofacil.service.*;
import com.ufcg.psoft.mercadofacil.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PedidoApiController {

    @Autowired
    LoteService loteService;

    @Autowired
    LoteRepository loteRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ClienteService clienteService;

    @RequestMapping(value = "/{idUser}/{idItem}/listaDeInteresse/disponivel", method = RequestMethod.POST)
    public ResponseEntity<?> adicionarInteresseProdutoEsgotado(@PathVariable("idUser") long idUser, @PathVariable("idItem") long idItem) {

        Optional<Cliente> optionalCliente = clienteService.listaClienteById(idUser);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEncontrado(idUser);
        }

        Optional<Produto> optionalProduto = produtoService.getProdutoById(idItem);

        if (!optionalProduto.isPresent()) {
            return ErroProduto.erroProdutoNaoEncontrado(idItem);
        }

        List<Lote> lotes = loteService.listarLotes();

        for (Lote lote : lotes) {
            long id = lote.getProduto().getId();
            if (idItem == id) {
                if (lote.getNumeroDeItens() <= 0) {
                    lote.adicionaInteresse(clienteService.listaClienteById(idUser).get());
                    loteRepository.save(lote);
                    return new ResponseEntity<>("Produto não disponível, adicionado seu interesse! Quando mais produtos estiverem disponívéis " +
                            "você será notificado", HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>("Produto não está fora de estoque!", HttpStatus.OK);

    }

    @RequestMapping(value = "/{idUser}/{idItem}/listaDeInteresse/promocao", method = RequestMethod.POST)
    public ResponseEntity<?> adicionarInteressePromocaoProduto(@PathVariable("idUser") long idUser, @PathVariable("idItem") long idItem) {

        Optional<Cliente> optionalCliente = clienteService.listaClienteById(idUser);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEncontrado(idUser);
        }

        Optional<Produto> optionalProduto = produtoService.getProdutoById(idItem);

        if (!optionalProduto.isPresent()) {
            return ErroProduto.erroProdutoNaoEncontrado(idItem);
        }

        Produto produto = produtoService.getProdutoById(idItem).get();
        produto.adicionaInteressadoPromocao(clienteService.listaClienteById(idUser).get());

        produtoRepository.save(produto);

        return new ResponseEntity<>("Adicionado Interesse na Promocação do Produto!", HttpStatus.OK);

    }
}