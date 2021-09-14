package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.DTO.ClienteDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.service.CarrinhoService;
import com.ufcg.psoft.mercadofacil.service.ClienteService;
import com.ufcg.psoft.mercadofacil.util.ErroCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClienteApiController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    CarrinhoService carrinhoService;

    @RequestMapping(value = "/cliente", method = RequestMethod.GET)
    public ResponseEntity<?> listarClientes() {

        List<Cliente> clientes = clienteService.listarClientes();

        if (clientes.isEmpty()) {
            return ErroCliente.erroSemClientesCadastrados();
        }

        return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
    }

    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> consultarCliente(@PathVariable("id") long id) {

        Optional<Cliente> optionalCliente = clienteService.listaClienteById(id);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEncontrado(id);
        }

        return new ResponseEntity<Cliente>(optionalCliente.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/cliente/", method = RequestMethod.POST)
    public ResponseEntity<?> cadastraCliente(@RequestBody ClienteDTO clienteDTO, UriComponentsBuilder ucBuilder) {

        Optional<Cliente> clienteTemp = clienteService.getClienteByCpf(clienteDTO.getCPF());

        if (!clienteTemp.isEmpty()) {
            return ErroCliente.erroClienteJaCadastrado(clienteDTO);
        }

        Cliente cliente;

        cliente = clienteService.criarCliente(clienteDTO);

        clienteService.salvarCliente(cliente);
        carrinhoService.criarCarrinho(cliente.getId());

        return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
    }

    @RequestMapping(value = "cliente/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> atualizaCliente(@PathVariable("id") long id, @RequestBody ClienteDTO clienteDTO) {

        Optional<Cliente> optionalCliente = clienteService.listaClienteById(id);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEncontrado(id);
        }

        Optional<Cliente> clienteTemp = clienteService.getClienteByCpf(clienteDTO.getCPF());

        if (!clienteTemp.isEmpty() && !optionalCliente.get().getCpf().equals(clienteTemp.get().getCpf())) {
            return ErroCliente.erroClienteJaCadastrado(clienteDTO);
        }

        Cliente cliente = optionalCliente.get();

        clienteService.atualizaCliente(clienteDTO, cliente);

        clienteService.salvarCliente(cliente);

        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeCliente(@PathVariable("id") long id) {

        Optional<Cliente> optionalCliente = clienteService.listaClienteById(id);

        if (!optionalCliente.isPresent()) {
            return ErroCliente.erroClienteNaoEncontrado(id);
        }

        carrinhoService.removerCarrinho(optionalCliente.get().getId());
        clienteService.removerCliente(optionalCliente.get());

        return new ResponseEntity<Cliente>(HttpStatus.OK);
    }
}
