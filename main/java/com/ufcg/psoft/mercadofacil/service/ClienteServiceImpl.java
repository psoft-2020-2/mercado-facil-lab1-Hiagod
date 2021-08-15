package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.DTO.ClienteDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.TipoPerfilRepository;

import java.math.BigDecimal;


@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
    TipoPerfilRepository tipoPerfilRepository;
	
	
	public Optional<Cliente> getClienteById(Long id) {
		return clienteRepository.findById(id);
	}
	
	public Optional<Cliente> getClienteByCPF(Long cpf) {
		return clienteRepository.findByCPF(cpf);
	}
	
	public void removerClienteCadastrado(Cliente cliente) {
		clienteRepository.delete(cliente);
	}

	public void salvarClienteCadastrado(Cliente cliente) {
		clienteRepository.save(cliente);		
	}

	public List<Cliente> listarClientes() {
		return clienteRepository.findAll();
	}

	public Cliente criaCliente(ClienteDTO clienteDTO) {
		Cliente cliente = new Cliente(clienteDTO.getCPF(), clienteDTO.getNome(), 
				clienteDTO.getIdade(), clienteDTO.getEndereco(), clienteDTO.getTipoUsuario());
		
		return cliente;
	}

	public Cliente atualizaCliente(ClienteDTO clienteDTO, Cliente cliente) {
		cliente.setIdade(clienteDTO.getIdade());
		cliente.setEndereco(clienteDTO.getEndereco());
		cliente.setNome(clienteDTO.getNome());
        cliente.setTipo(clienteDTO.getTipoUsuario());
        tipoPerfilRepository.save(cliente.getTipo());
		
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
