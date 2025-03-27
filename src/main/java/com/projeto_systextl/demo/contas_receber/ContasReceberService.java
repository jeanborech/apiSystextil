package com.projeto_systextl.demo.contas_receber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContasReceberService {

    @Autowired
    private ContasReceberRepository contasReceberRepository;

    public ContasReceber criarContaReceber(Integer idPedidoVenda, LocalDateTime dataEmissao, LocalDate dataVencimento,
                                           BigDecimal valor, String situacao) {
        ContasReceber contaReceber = new ContasReceber();
        contaReceber.setIdPedidoVenda(idPedidoVenda);
        contaReceber.setDataEmissao(dataEmissao);
        contaReceber.setDataVencimento(dataVencimento);
        contaReceber.setValor(valor);
        contaReceber.setSituacao(situacao);
        contaReceber.setDataCriacao(LocalDateTime.now());
        contaReceber.setDataAtualizacao(LocalDateTime.now());
        return contasReceberRepository.save(contaReceber);
    }

    public List<ContasReceber> listarTodos() {
        return contasReceberRepository.findAll();
    }

    public ContasReceber buscarPorId(Integer id) {
        Optional<ContasReceber> contaReceber = contasReceberRepository.findById(id);
        if (contaReceber.isPresent()) {
            return contaReceber.get();
        } else {
            throw new RuntimeException("Conta a receber não encontrada com ID: " + id);
        }
    }

    public ContasReceber atualizarContaReceber(Integer id, Integer idPedidoVenda, LocalDateTime dataEmissao,
                                               LocalDate dataVencimento, BigDecimal valor, String situacao) {
        ContasReceber contaReceber = buscarPorId(id);
        contaReceber.setIdPedidoVenda(idPedidoVenda);
        contaReceber.setDataEmissao(dataEmissao);
        contaReceber.setDataVencimento(dataVencimento);
        contaReceber.setValor(valor);
        contaReceber.setSituacao(situacao);
        contaReceber.setDataAtualizacao(LocalDateTime.now());
        return contasReceberRepository.save(contaReceber);
    }

    public ContasReceber atualizarParcialmenteContaReceber(Integer id, Integer idPedidoVenda, LocalDateTime dataEmissao,
                                                           LocalDate dataVencimento, BigDecimal valor, String situacao) {
        ContasReceber contaReceber = buscarPorId(id);
        if (idPedidoVenda != null) {
            contaReceber.setIdPedidoVenda(idPedidoVenda);
        }
        if (dataEmissao != null) {
            contaReceber.setDataEmissao(dataEmissao);
        }
        if (dataVencimento != null) {
            contaReceber.setDataVencimento(dataVencimento);
        }
        if (valor != null) {
            contaReceber.setValor(valor);
        }
        if (situacao != null) {
            contaReceber.setSituacao(situacao);
        }
        contaReceber.setDataAtualizacao(LocalDateTime.now());
        return contasReceberRepository.save(contaReceber);
    }

    public void deletarContaReceber(Integer id) {
        ContasReceber contaReceber = buscarPorId(id);
        contasReceberRepository.delete(contaReceber);
    }
}