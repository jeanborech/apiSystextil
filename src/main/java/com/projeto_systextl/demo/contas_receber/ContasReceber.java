package com.projeto_systextl.demo.contas_receber;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "CONTAS_RECEBER")
public class ContasReceber {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_contas_receber")
    @SequenceGenerator(name = "seq_contas_receber", sequenceName = "SEQ_CONTAS_RECEBER", allocationSize = 1)
    @Column(name = "ID_CONTA_RECEBER")
    private Integer idContaReceber;

    @Column(name = "ID_PEDIDO_VENDA", nullable = false)
    private Integer idPedidoVenda;

    @Column(name = "DATA_EMISSAO")
    private LocalDateTime dataEmissao;

    @Column(name = "DATA_VENCIMENTO")
    private LocalDate dataVencimento;

    @Column(name = "VALOR", nullable = false)
    private BigDecimal valor;

    @Column(name = "SITUACAO", length = 20)
    private String situacao;

    @Column(name = "DATA_CRIACAO", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "DATA_ATUALIZACAO")
    private LocalDateTime dataAtualizacao;

    // Getters e Setters
    public Integer getIdContaReceber() {
        return idContaReceber;
    }

    public void setIdContaReceber(Integer idContaReceber) {
        this.idContaReceber = idContaReceber;
    }

    public Integer getIdPedidoVenda() {
        return idPedidoVenda;
    }

    public void setIdPedidoVenda(Integer idPedidoVenda) {
        this.idPedidoVenda = idPedidoVenda;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}