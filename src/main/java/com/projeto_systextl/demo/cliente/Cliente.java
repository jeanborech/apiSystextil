package com.projeto_systextl.demo.cliente;

import com.projeto_systextl.demo.endereco.Endereco;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "CLIENTES")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq_generator")
    @SequenceGenerator(name = "cliente_seq_generator", sequenceName = "cliente_seq", allocationSize = 1)
    @Column(name = "ID_CLIENTE")
    private int idCliente;

    @Column(name = "NOME_EMPRESA")
    private String nomeEmpresa;

    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "TELEFONE")
    private String telefone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "REGIME_TRIBUTARIO")
    private String regimeTributario;

    @Column(name = "DATA_CRIACAO")
    private Timestamp dataCriacao;

    @Column(name = "DATA_ATUALIZACAO")
    private Timestamp dataAtualizacao;

    @Column(name = "STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ENDERECO")
    private Endereco endereco;

    // Construtor padrão
    public Cliente() {
    }

    // Construtor completo
    public Cliente(String nomeEmpresa, String cnpj, String telefone, String email, String regimeTributario, 
                   Integer status, Endereco endereco) {
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.regimeTributario = regimeTributario;
        this.status = status;
        this.endereco = endereco;
    }

    // Métodos para preencher automaticamente as datas
    @PrePersist
    protected void onCreate() {
        this.dataCriacao = new Timestamp(System.currentTimeMillis());
        this.dataAtualizacao = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = new Timestamp(System.currentTimeMillis());
    }

    // Getters e Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
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

    public String getRegimeTributario() {
        return regimeTributario;
    }

    public void setRegimeTributario(String regimeTributario) {
        this.regimeTributario = regimeTributario;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return idCliente == cliente.idCliente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente);
    }
}