package com.projeto_systextl.demo.ordensproducao;

import com.projeto_systextl.demo.produto.Produto;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "ordens_producao")
public class OrdensProducao {
    @Id
    @Column(name = "id_ordem_producao")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordem_producao_seq")
    @SequenceGenerator(name = "ordem_producao_seq", sequenceName = "SEQ_ORDEM_PRODUCAO", allocationSize = 1)
    private Integer idOrdemProducao;

    @Column(name = "numero_ordem")
    private Integer numeroOrdem;

    @ManyToOne
    @JoinColumn(name = "id_produto", referencedColumnName = "id_produto")
    private Produto produto;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "status")
    private String status;

    @Column(name = "data_criacao")
    private Date dataCriacao;

    @Column(name = "data_atualizacao")
    private Date dataAtualizacao;

    @Column(name = "id_pedido_venda")
    private Integer idPedidoVenda;

    public OrdensProducao() {}

    public OrdensProducao(Integer numeroOrdem, Produto produto, Integer quantidade,
                          String status, Integer idPedidoVenda) {
        this.numeroOrdem = numeroOrdem;
        this.produto = produto;
        this.quantidade = quantidade;
        this.status = status;
        this.idPedidoVenda = idPedidoVenda;
    }

    public Integer getIdOrdemProducao() {
        return idOrdemProducao;
    }

    public void setIdOrdemProducao(Integer idOrdemProducao) {
        this.idOrdemProducao = idOrdemProducao;
    }

    public Integer getIdPedidoVenda() {
        return idPedidoVenda;
    }

    public void setIdPedidoVenda(Integer idPedidoVenda) {
        this.idPedidoVenda = idPedidoVenda;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getNumeroOrdem() {
        return numeroOrdem;
    }

    public void setNumeroOrdem(Integer numeroOrdem) {
        this.numeroOrdem = numeroOrdem;
    }
}
