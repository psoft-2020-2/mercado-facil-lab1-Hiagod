package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.DTO.ClienteDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ClienteService {

    Optional<Cliente> listaClienteById(long id);

    Optional<Cliente> getClienteByCpf(Long cpf);

    void removerCliente(Cliente cliente);

    void salvarCliente(Cliente cliente);

    List<Cliente> listarClientes();

    Cliente criarCliente(ClienteDTO clienteDTO);

    Cliente atualizaCliente(ClienteDTO clienteDTO, Cliente cliente);

    BigDecimal aplicaDesconto(Cliente cliente, BigDecimal valor, Integer quantItens);

}
