package com.ufcg.psoft.mercadofacil.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.model.*;
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
public class CarrinhoApiController {

    @Autowired
    LoteService loteService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    CarrinhoService carrinhoService;

    @Autowired
    CompraService compraService;

    @Autowired
    ClienteService clienteService;

    @RequestMapping(value = "/{idUser}/carrinho", method = RequestMethod.GET)
    public ResponseEntity<?> listarItensCarrinho(@PathVariable("idUser") long idUser){

        Optional<Cliente> optionalCliente = clienteService.listaClienteById(idUser);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEncontrado(idUser);
        }

        List<ItemCarrinho> carrinho = carrinhoService.listarItensCarrinho(idUser);

        if (carrinho.isEmpty()) {
            return ErroCarrinho.erroCarrinhoVazio(idUser);
        }

        return new ResponseEntity<List<ItemCarrinho>>(carrinho, HttpStatus.OK);
    }

    @RequestMapping(value = "/{idUser}/carrinho/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> adicionarItemCarrinho(@PathVariable("idUser") long idUser, @PathVariable("id") long idItem, @RequestBody int numItens) {

        Optional<Cliente> optionalCliente = clienteService.listaClienteById(idUser);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEncontrado(idUser);
        }

        Optional<Produto> optionalProduto = produtoService.getProdutoById(idItem);

        if (!optionalProduto.isPresent()) {
            return ErroProduto.erroProdutoNaoEncontrado(idItem);
        }

        List<Lote> lotes = loteService.listarLotes();

        if (lotes.isEmpty()) {
            return ErroLote.erroSemLotesCadastrados();
        }

        List<Lote> lotesDoProduto = new ArrayList<Lote>();

        for (Lote lote: lotes) {
            long id = lote.getProduto().getId();
            if (idItem == id) {
                lotesDoProduto.add(lote);
            }
        }

        if (lotesDoProduto.isEmpty()) {
            return ErroLote.erroSemLotesCadastrados();
        }

        ItemCarrinho ProdutoCarrinho = carrinhoService.getItemCarrinhoById(idUser, idItem);

        if (ProdutoCarrinho != null) {
            return ErroCarrinho.erroProdutoJaExisteNoCarrinho(optionalProduto.get());
        }

        Produto produto = optionalProduto.get();
        ItemCarrinho produtoNoCarrinho = carrinhoService.criarItemCarrinho(idItem, numItens, produto.getNome(), produto.getPreco());
        carrinhoService.atualizaCarrinho(idUser, produtoNoCarrinho);

        return new ResponseEntity<ItemCarrinho>(produtoNoCarrinho, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{idUser}/carrinho/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removerItemCarrinho(@PathVariable("idUser") long idUser, @PathVariable("id") long idItem) {

        Optional<Cliente> optionalCliente = clienteService.listaClienteById(idUser);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEncontrado(idUser);
        }

        Optional<Produto> optionalProduto = produtoService.getProdutoById(idItem);

        if (!optionalProduto.isPresent()) {
            return ErroProduto.erroProdutoNaoEncontrado(idItem);
        }

        ItemCarrinho produtoC = carrinhoService.getItemCarrinhoById(idUser, idItem);

        if (produtoC == null) {
            return ErroCarrinho.erroItemNaoExisteNoCarrinho(idItem);
        }

        carrinhoService.removerItemCarrinho(idUser, produtoC);

        return new ResponseEntity<ItemCarrinho>(produtoC,HttpStatus.OK);
    }

    @RequestMapping(value = "/{idUser}/carrinho/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> atualizarItemCarrinho(@PathVariable("idUser") long idUser, @PathVariable("id") long idItem, @RequestBody int numItens) {

        Optional<Cliente> optionalCliente = clienteService.listaClienteById(idUser);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEncontrado(idUser);
        }

        Optional<Produto> optionalProduto = produtoService.getProdutoById(idItem);

        if (!optionalProduto.isPresent()) {
            return ErroProduto.erroProdutoNaoEncontrado(idItem);
        }

        ItemCarrinho produtoCarrinho = carrinhoService.getItemCarrinhoById(idUser, idItem);

        if (produtoCarrinho == null) {
            return ErroCarrinho.erroItemNaoExisteNoCarrinho(idItem);
        }

        List<Lote> lotes = loteService.listarLotes();
        List<Lote> lotesDoProduto = new ArrayList<Lote>();

        for (Lote lote: lotes) {
            long id = lote.getProduto().getId();
            if (idItem == id) {
                lotesDoProduto.add(lote);
            }
        }

        int quantMax = 0;

        for (Lote lote : lotesDoProduto) {
            quantMax += lote.getNumeroDeItens();
        }

        if (quantMax < numItens) {
            return ErroCarrinho.erroQuantidadeNaoDisponivel();
        }

        ItemCarrinho produtoC = carrinhoService.atualizaItemCarrinho(idUser, idItem, numItens);

        return new ResponseEntity<ItemCarrinho>(produtoC, HttpStatus.OK);
    }

    @RequestMapping(value = "/{idUser}/carrinho", method = RequestMethod.DELETE)
    public ResponseEntity<?> limparCarrinho(@PathVariable("idUser") long idUser) {

        Optional<Cliente> optionalCliente = clienteService.listaClienteById(idUser);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEncontrado(idUser);
        }

        if (carrinhoService.isCarrinhoLimpo(idUser)) {
            return ErroCarrinho.erroCarrinhoVazio(idUser);
        }

        carrinhoService.limparCarrinho(idUser);

        return new ResponseEntity<ItemCarrinho>(HttpStatus.NO_CONTENT);
    }



    @RequestMapping(value = "/{idUser}/carrinho/entrega/{tipoEntrega}", method = RequestMethod.POST)
    public ResponseEntity<?> defineEntrega (@PathVariable("tipoEntrega") String tipoEntrega ,@RequestBody String endereco, @PathVariable("idUser") long idUser) {

        Optional<Cliente> optionalCliente = clienteService.listaClienteById(idUser);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEncontrado(idUser);
        }

        if (carrinhoService.isCarrinhoLimpo(idUser)) {
            return ErroCarrinho.erroCarrinhoVazio(idUser);
        }
        if(!tipoEntrega.equals("express") && !tipoEntrega.equals("padrao") && !tipoEntrega.equals("retirada")) {
            return new ResponseEntity<String>("Tipo de Entrega não existente", HttpStatus.OK);
        }
        carrinhoService.defineEntrega(idUser,tipoEntrega,endereco);

        return new ResponseEntity<String>("Definido com Sucesso", HttpStatus.OK);
    }

    @RequestMapping(value = "/{idUser}/carrinho/entrega/{tipoEntrega}", method = RequestMethod.PUT)
    public ResponseEntity<?> editaEntrega (@PathVariable("tipoEntrega") String tipoEntrega,@PathVariable("idUser") long idUser, @RequestBody String endereco ) {

        Optional<Cliente> optionalCliente = clienteService.listaClienteById(idUser);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEncontrado(idUser);
        }

        if (carrinhoService.isCarrinhoLimpo(idUser)) {
            return ErroCarrinho.erroCarrinhoVazio(idUser);
        }
        if(!tipoEntrega.equals("express") && !tipoEntrega.equals("padrao") && !tipoEntrega.equals("retirada")) {
            return new ResponseEntity<String>("Tipo de Entrega não existente", HttpStatus.OK);
        }
        carrinhoService.defineEntrega(idUser,tipoEntrega, endereco);

        return new ResponseEntity<String>("Alterado com Sucesso", HttpStatus.OK);

    }
    @RequestMapping(value = "/{idUser}/carrinho", method = RequestMethod.PUT)
    public ResponseEntity<?> finalizarCompra(@PathVariable("idUser") long idUser, @RequestBody String metodoCompra,UriComponentsBuilder ucBuilder) {
            Optional<Cliente> optionalCliente = clienteService.listaClienteById(idUser);

            if (!optionalCliente.isPresent()) {
                return ErroCliente.erroClienteNaoEncontrado(idUser);
            }

            if (carrinhoService.isCarrinhoLimpo(idUser)) {
                return ErroCarrinho.erroCarrinhoVazio(idUser);
            }
            EntregaExpress tipoEntrega1 = new EntregaExpress(carrinhoService.getEndereco(idUser));
            EntregaPadrao tipoEntrega2 = new EntregaPadrao(carrinhoService.getEndereco(idUser));
            Retirada tipoEntrega3 = new Retirada();


            String mensagemSaida = compraService.imprimeCabecalho();
            BigDecimal precoFinal = new BigDecimal(0);

            List<ItemCarrinho> carrinho = carrinhoService.listarItensCarrinho(idUser);
            List<ItemVenda> listaVenda = new ArrayList<>();
            Integer quantIntens = 0;
            List<Lote> lotes = loteService.listarLotes();

            BigDecimal precoEntrega = new BigDecimal(0);

           for (ItemCarrinho item: carrinho) {

                precoFinal = precoFinal.add(item.getPrecoItem().multiply(new BigDecimal(item.getQuantidadeItem())));

               if (carrinhoService.getEntrega(idUser).equals("express")){
                   precoEntrega = precoEntrega.add(tipoEntrega1.valorDaEntrega(produtoService.getProdutoById(item.getIdItem()).get().getTipo()));
               } else if (carrinhoService.getEntrega(idUser).equals("padrao")){
                    precoEntrega = precoEntrega.add(tipoEntrega2.valorDaEntrega(produtoService.getProdutoById(item.getIdItem()).get().getTipo()));
               } else {
                   precoEntrega = precoEntrega.add(tipoEntrega3.valorDaEntrega(produtoService.getProdutoById(item.getIdItem()).get().getTipo()));
               }
                compraService.diminuiNoLote(lotes,item);

                mensagemSaida += compraService.imprimirDetalhesDoProduto(item);
                listaVenda.add(compraService.criarItemVenda(item.getQuantidadeItem(), item.getNomeItem(), item.getPrecoItem()));
                quantIntens += item.getQuantidadeItem();
            }

            Compra compra;

            try {
                compra = compraService.criarCompra(precoFinal.add(precoEntrega), metodoCompra, listaVenda);
                compraService.atualizaPrecoCompra(compra, clienteService.aplicaDesconto(optionalCliente.get(), compra.getValor(), quantIntens));
            }
            catch (IllegalArgumentException e) {
                return ErroPagamento.erroTipoPagamentoInvalido();
            }

        mensagemSaida += compraService.imprimirDetalhesDoPagamento(compra);

        compraService.salvarCompra(compra);

        carrinhoService.limparCarrinho(idUser);

            return new ResponseEntity<String>(mensagemSaida, HttpStatus.OK);

    }


}

