package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.DTO.ClienteDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Optional<Cliente> listaClienteById(long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Optional<Cliente> getClienteByCpf(Long cpf) {
        return clienteRepository.findByCPF(cpf);
    }

    @Override
    public void removerCliente(Cliente cliente) {
        clienteRepository.delete(cliente);
    }

    @Override
    public void salvarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente criarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente(clienteDTO.getCPF(), clienteDTO.getNome(), clienteDTO.getIdade(), clienteDTO.getEndereco(), clienteDTO.getTipoUsuario());

        return cliente;
    }

    @Override
    public Cliente atualizaCliente(ClienteDTO clienteDTO, Cliente cliente) {
        cliente.setIdade(clienteDTO.getIdade());
        cliente.setEndereco(clienteDTO.getEndereco());

        return cliente;
    }

    public BigDecimal aplicaDesconto(Cliente cliente, BigDecimal valor, Integer quantItens) {
        BigDecimal valorComDesconto = new BigDecimal(0);

        switch (cliente.getTipo().getUsuario()) {
            case "normal":
                valorComDesconto = valor;
                break;
            case "especial":
                if (quantItens > 10)
                    valorComDesconto = valor.multiply(new BigDecimal("0.9"));
                else
                    valorComDesconto = valor;
                break;
            case "premium":
                if (quantItens > 5)
                    valorComDesconto = valor.multiply(new BigDecimal("0.9"));
                else
                    valorComDesconto = valor;
                break;
        }

        return valorComDesconto;
    }



}

