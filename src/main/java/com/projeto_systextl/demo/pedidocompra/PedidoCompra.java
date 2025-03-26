package com.projeto_systextl.demo.pedidocompra;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projeto_systextl.demo.itempedidocompra.ItemPedidoCompra;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "PEDIDO_COMPRA")
public class PedidoCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PEDIDO_COMPRA")
    private int idPedidoCompra;

    @Column(name = "NUMERO_PEDIDO", unique = true)
    private String numeroPedido;

    @Column(name = "ID_FORNECEDOR", nullable = false)
    private int idFornecedor;

    @Column(name = "DATA_PEDIDO")
    private LocalDate dataPedido;

    @Column(name = "VALOR_TOTAL")
    private Double valorTotal;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DATA_ATUALIZACAO")
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "pedidoCompra", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ItemPedidoCompra> itens = new ArrayList<>();

    public PedidoCompra() {}

    public PedidoCompra(String numeroPedido, int idFornecedor, String status) {
        this.numeroPedido = numeroPedido;
        this.idFornecedor = idFornecedor;
        this.dataPedido = LocalDate.now();
        this.status = status;
        this.valorTotal = 0.0;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void calcularValorTotal() {
        this.valorTotal = itens.stream()
            .mapToDouble(item -> item.getValorTotal() != null ? item.getValorTotal() : 0.0)
            .sum();
    }

    public void adicionarItem(ItemPedidoCompra item) {
        item.setPedidoCompra(this);
        this.itens.add(item);
        calcularValorTotal();
    }

    // Getters e Setters
    public int getIdPedidoCompra() { return idPedidoCompra; }
    public void setIdPedidoCompra(int idPedidoCompra) { this.idPedidoCompra = idPedidoCompra; }
    public String getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }
    public int getIdFornecedor() { return idFornecedor; }
    public void setIdFornecedor(int idFornecedor) { this.idFornecedor = idFornecedor; }
    public LocalDate getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDate dataPedido) { this.dataPedido = dataPedido; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
    public List<ItemPedidoCompra> getItens() { return itens; }
    public void setItens(List<ItemPedidoCompra> itens) {
        this.itens = itens;
        calcularValorTotal();
    }
}