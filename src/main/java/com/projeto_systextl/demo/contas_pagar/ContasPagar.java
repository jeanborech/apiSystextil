package com.projeto_systextl.demo.contas_pagar;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "CONTAS_PAGAR")
public class ContasPagar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_contas_pagar")
    @SequenceGenerator(name = "seq_contas_pagar", sequenceName = "SEQ_CONTAS_PAGAR", allocationSize = 1)
    @Column(name = "ID_CONTA_PAGAR")
    private Integer idContaPagar;

    @Column(name = "ID_FORNECEDOR", nullable = false)
    private Integer idFornecedor;

    @Column(name = "DATA_EMISSAO")
    private LocalDate dataEmissao;

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
    public Integer getIdContaPagar() {
        return idContaPagar;
    }

    public void setIdContaPagar(Integer idContaPagar) {
        this.idContaPagar = idContaPagar;
    }

    public Integer getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
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