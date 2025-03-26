package com.projeto_systextl.demo.pedidovenda;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projeto_systextl.demo.cliente.Cliente;
import com.projeto_systextl.demo.itempedidovenda.ItensPedidoVenda;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PEDIDOS_VENDA")
public class PedidoVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido_venda")
    private Integer idPedidoVenda; // Confirme que é Integer, não int

    @Column(name = "numero_pedido_venda")
    private String numeroPedidoVenda;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    @Column(name = "valor_total")
    private double valorTotal;

    @Column(name = "status")
    private String status;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "pedidoVenda", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ItensPedidoVenda> itens = new ArrayList<>();

    // Construtores
    public PedidoVenda() {}

    public PedidoVenda(Cliente cliente, LocalDate dataPedido, String status) {
        this.cliente = cliente;
        this.dataPedido = dataPedido;
        this.status = status;
        this.valorTotal = 0.0;
    }

    // Método auxiliar para gerar o número do pedido
    public void gerarNumeroPedidoVenda() {
        this.numeroPedidoVenda = "VENDA" + System.currentTimeMillis();
    }

    // Método auxiliar para recalcular o valor total
    public void recalcularValorTotal() {
        this.valorTotal = itens.stream().mapToDouble(ItensPedidoVenda::getValorTotal).sum();
    }

    // Método para adicionar um item
    public void adicionarItem(ItensPedidoVenda item) {
        item.setPedidoVenda(this);
        this.itens.add(item);
        recalcularValorTotal();
    }

    // Getters e Setters
    public Integer getIdPedidoVenda() {
        return idPedidoVenda;
    }

    public void setIdPedidoVenda(Integer idPedidoVenda) {
        this.idPedidoVenda = idPedidoVenda;
    }

    public String getNumeroPedidoVenda() {
        return numeroPedidoVenda;
    }

    public void setNumeroPedidoVenda(String numeroPedidoVenda) {
        this.numeroPedidoVenda = numeroPedidoVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public List<ItensPedidoVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItensPedidoVenda> itens) {
        this.itens = itens;
        recalcularValorTotal();
    }
}