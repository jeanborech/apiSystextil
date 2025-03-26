package com.projeto_systextl.demo.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_systextl.demo.endereco.Endereco;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Método findById (adicionado para ser usado pelo PedidoVendaService)
    public Cliente findById(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente com ID " + id + " não encontrado"));
    }

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

    public Cliente atualizarCliente(Integer idCliente, String nomeEmpresa, String cnpj, String telefone, String email, 
                                    String regimeTributario, Integer status, Endereco endereco) { // Alterado de int para Integer
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        if (nomeEmpresa != null && !nomeEmpresa.trim().isEmpty()) cliente.setNomeEmpresa(nomeEmpresa);
        if (cnpj != null && !cnpj.trim().isEmpty()) {
            Cliente existente = clienteRepository.findByCnpj(cnpj);
            if (existente != null && !existente.getIdCliente().equals(idCliente)) { // Ajustado para usar equals
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

    public Cliente atualizarParcialCliente(Integer idCliente, String nomeEmpresa, String cnpj, String telefone, 
                                           String email, String regimeTributario, Integer status, Endereco endereco) { // Alterado de int para Integer
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        if (nomeEmpresa != null) cliente.setNomeEmpresa(nomeEmpresa);
        if (cnpj != null) {
            Cliente existente = clienteRepository.findByCnpj(cnpj);
            if (existente != null && !existente.getIdCliente().equals(idCliente)) { // Ajustado para usar equals
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