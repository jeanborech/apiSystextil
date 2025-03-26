package com.projeto_systextl.demo.fornecedor;

import com.projeto_systextl.demo.endereco.Endereco;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "FORNECEDORES")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FORNECEDOR")
    private int idFornecedor;

    @Column(name = "NOME_FORNECEDOR")
    private String nomeFornecedor;

    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "TELEFONE")
    private String telefone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DATA_CRIACAO")
    private Timestamp dataCriacao;

    @Column(name = "DATA_ATUALIZACAO")
    private Timestamp dataAtualizacao;

    @Column(name = "STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ENDERECO")
    private Endereco endereco;

    // Construtores
    public Fornecedor() {
    }

    public Fornecedor(String nomeFornecedor, String cnpj, String telefone, String email, Integer status, Endereco endereco) {
        this.nomeFornecedor = nomeFornecedor;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.status = status;
        this.endereco = endereco;
        this.dataCriacao = new Timestamp(System.currentTimeMillis());
        this.dataAtualizacao = new Timestamp(System.currentTimeMillis());
    }

    // Getters e Setters
    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Timestamp getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Timestamp dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    // Atualizar dataAtualizacao antes de cada atualização
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = new Timestamp(System.currentTimeMillis());
    }

    // Definir dataCriacao e dataAtualizacao na criação
    @PrePersist
    public void prePersist() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        this.dataCriacao = now;
        this.dataAtualizacao = now;
    }
}
