package com.projeto_systextl.demo.cliente;

import com.projeto_systextl.demo.endereco.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente criarCliente(String nomeEmpresa, String cnpj, String telefone, String email, 
                                String regimeTributario, Integer status, Endereco endereco) {
        if (nomeEmpresa == null || nomeEmpresa.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da empresa é obrigatório.");
        }
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ é obrigatório.");
        }
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço é obrigatório.");
        }
        if (clienteRepository.findByCnpj(cnpj) != null) {
            throw new IllegalArgumentException("CNPJ já cadastrado.");
        }

        Cliente cliente = new Cliente(nomeEmpresa, cnpj, telefone, email, regimeTributario, status, endereco);
        return clienteRepository.save(cliente);
    }

    public Cliente atualizarCliente(int idCliente, String nomeEmpresa, String cnpj, String telefone, String email, 
                                    String regimeTributario, Integer status, Endereco endereco) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        if (nomeEmpresa != null && !nomeEmpresa.trim().isEmpty()) cliente.setNomeEmpresa(nomeEmpresa);
        if (cnpj != null && !cnpj.trim().isEmpty()) {
            Cliente existente = clienteRepository.findByCnpj(cnpj);
            if (existente != null && existente.getIdCliente() != idCliente) {
                throw new IllegalArgumentException("CNPJ já cadastrado para outro cliente.");
            }
            cliente.setCnpj(cnpj);
        }
        if (telefone != null) cliente.setTelefone(telefone);
        if (email != null) cliente.setEmail(email);
        if (regimeTributario != null) cliente.setRegimeTributario(regimeTributario);
        if (status != null) cliente.setStatus(status);
        if (endereco != null) cliente.setEndereco(endereco);

        return clienteRepository.save(cliente);
    }

    public Cliente atualizarParcialCliente(int idCliente, String nomeEmpresa, String cnpj, String telefone, 
                                           String email, String regimeTributario, Integer status, Endereco endereco) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        if (nomeEmpresa != null) cliente.setNomeEmpresa(nomeEmpresa);
        if (cnpj != null) {
            Cliente existente = clienteRepository.findByCnpj(cnpj);
            if (existente != null && existente.getIdCliente() != idCliente) {
                throw new IllegalArgumentException("CNPJ já cadastrado para outro cliente.");
            }
            cliente.setCnpj(cnpj);
        }
        if (telefone != null) cliente.setTelefone(telefone);
        if (email != null) cliente.setEmail(email);
        if (regimeTributario != null) cliente.setRegimeTributario(regimeTributario);
        if (status != null) cliente.setStatus(status);
        if (endereco != null) cliente.setEndereco(endereco);

        return clienteRepository.save(cliente);
    }
}