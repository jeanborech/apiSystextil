package com.projeto_systextl.demo.contas_pagar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContasPagarService {

    @Autowired
    private ContasPagarRepository contasPagarRepository;

    public ContasPagar criarContaPagar(Integer idFornecedor, LocalDate dataEmissao, LocalDate dataVencimento,
                                       BigDecimal valor, String situacao) {
        ContasPagar contaPagar = new ContasPagar();
        contaPagar.setIdFornecedor(idFornecedor);
        contaPagar.setDataEmissao(dataEmissao);
        contaPagar.setDataVencimento(dataVencimento);
        contaPagar.setValor(valor);
        contaPagar.setSituacao(situacao);
        contaPagar.setDataCriacao(LocalDateTime.now());
        contaPagar.setDataAtualizacao(LocalDateTime.now());
        return contasPagarRepository.save(contaPagar);
    }

    public List<ContasPagar> listarTodos() {
        return contasPagarRepository.findAll();
    }

    public ContasPagar buscarPorId(Integer id) {
        Optional<ContasPagar> contaPagar = contasPagarRepository.findById(id);
        if (contaPagar.isPresent()) {
            return contaPagar.get();
        } else {
            throw new RuntimeException("Conta a pagar não encontrada com ID: " + id);
        }
    }

    public ContasPagar atualizarContaPagar(Integer id, Integer idFornecedor, LocalDate dataEmissao,
                                           LocalDate dataVencimento, BigDecimal valor, String situacao) {
        ContasPagar contaPagar = buscarPorId(id);
        contaPagar.setIdFornecedor(idFornecedor);
        contaPagar.setDataEmissao(dataEmissao);
        contaPagar.setDataVencimento(dataVencimento);
        contaPagar.setValor(valor);
        contaPagar.setSituacao(situacao);
        contaPagar.setDataAtualizacao(LocalDateTime.now());
        return contasPagarRepository.save(contaPagar);
    }

    public ContasPagar atualizarParcialmenteContaPagar(Integer id, Integer idFornecedor, LocalDate dataEmissao,
                                                       LocalDate dataVencimento, BigDecimal valor, String situacao) {
        ContasPagar contaPagar = buscarPorId(id);
        if (idFornecedor != null) {
            contaPagar.setIdFornecedor(idFornecedor);
        }
        if (dataEmissao != null) {
            contaPagar.setDataEmissao(dataEmissao);
        }
        if (dataVencimento != null) {
            contaPagar.setDataVencimento(dataVencimento);
        }
        if (valor != null) {
            contaPagar.setValor(valor);
        }
        if (situacao != null) {
            contaPagar.setSituacao(situacao);
        }
        contaPagar.setDataAtualizacao(LocalDateTime.now());
        return contasPagarRepository.save(contaPagar);
    }

    public void deletarContaPagar(Integer id) {
        ContasPagar contaPagar = buscarPorId(id);
        contasPagarRepository.delete(contaPagar);
    }
}