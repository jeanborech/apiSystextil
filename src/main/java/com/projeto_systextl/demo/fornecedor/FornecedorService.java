package com.projeto_systextl.demo.fornecedor;

import com.projeto_systextl.demo.endereco.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public Fornecedor criarFornecedor(String nomeFornecedor, String cnpj, String telefone, String email, 
                                      Integer status, Endereco endereco) {
        if (nomeFornecedor == null || nomeFornecedor.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do fornecedor é obrigatório.");
        }
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ é obrigatório.");
        }
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço é obrigatório.");
        }
        if (fornecedorRepository.findByCnpj(cnpj) != null) {
            throw new IllegalArgumentException("CNPJ já cadastrado.");
        }

        Fornecedor fornecedor = new Fornecedor(nomeFornecedor, cnpj, telefone, email, status, endereco);
        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor atualizarFornecedor(int idFornecedor, String nomeFornecedor, String cnpj, String telefone, 
                                          String email, Integer status, Endereco endereco) {
        Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));

        if (nomeFornecedor != null && !nomeFornecedor.trim().isEmpty()) {
            fornecedor.setNomeFornecedor(nomeFornecedor);
        }
        if (cnpj != null && !cnpj.trim().isEmpty()) {
            Fornecedor existente = fornecedorRepository.findByCnpj(cnpj);
            if (existente != null && existente.getIdFornecedor() != idFornecedor) {
                throw new IllegalArgumentException("CNPJ já cadastrado para outro fornecedor.");
            }
            fornecedor.setCnpj(cnpj);
        }
        if (telefone != null) {
            fornecedor.setTelefone(telefone);
        }
        if (email != null) {
            fornecedor.setEmail(email);
        }
        if (status != null) {
            fornecedor.setStatus(status);
        }
        if (endereco != null) {
            fornecedor.setEndereco(endereco);
        }

        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor atualizarParcialFornecedor(int idFornecedor, String nomeFornecedor, String cnpj, String telefone, 
                                                 String email, Integer status, Endereco endereco) {
        Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));

        if (nomeFornecedor != null) {
            fornecedor.setNomeFornecedor(nomeFornecedor);
        }
        if (cnpj != null) {
            Fornecedor existente = fornecedorRepository.findByCnpj(cnpj);
            if (existente != null && existente.getIdFornecedor() != idFornecedor) {
                throw new IllegalArgumentException("CNPJ já cadastrado para outro fornecedor.");
            }
            fornecedor.setCnpj(cnpj);
        }
        if (telefone != null) {
            fornecedor.setTelefone(telefone);
        }
        if (email != null) {
            fornecedor.setEmail(email);
        }
        if (status != null) {
            fornecedor.setStatus(status);
        }
        if (endereco != null) {
            fornecedor.setEndereco(endereco);
        }

        return fornecedorRepository.save(fornecedor);
    }

	public void findById(int idFornecedor) {
		// TODO Auto-generated method stub
		
	}
}