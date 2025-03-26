package com.projeto_systextl.demo.itempedidocompra;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projeto_systextl.demo.pedidocompra.PedidoCompra;
import com.projeto_systextl.demo.produto.ItemProduto;
import com.projeto_systextl.demo.produto.Produto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;

@Entity
@Table(name = "ITENS_PEDIDO_COMPRA")
public class ItemPedidoCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ITEM_PEDIDO_COMPRA")
    private int idItemPedidoCompra;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PEDIDO_COMPRA", nullable = false)
    @JsonBackReference
    private PedidoCompra pedidoCompra;

    @OneToMany(mappedBy = "itemPedidoCompra", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ItemProduto> itensProdutos = new ArrayList<>();

    @Column(name = "QUANTIDADE_TOTAL", nullable = false)
    private int quantidadeTotal;

    @Column(name = "VALOR_TOTAL", nullable = false)
    private Double valorTotal;

    public ItemPedidoCompra() {}

    public ItemPedidoCompra(PedidoCompra pedidoCompra, List<ItemProduto> itensProdutos) {
        this.pedidoCompra = pedidoCompra;
        this.itensProdutos = itensProdutos != null ? itensProdutos : new ArrayList<>();
        for (ItemProduto item : this.itensProdutos) {
            item.setItemPedidoCompra(this);
        }
        calcularTotais();
    }

    public void calcularTotais() {
        this.quantidadeTotal = itensProdutos.stream()
            .mapToInt(ItemProduto::getQuantidade)
            .sum();
        this.valorTotal = itensProdutos.stream()
            .mapToDouble(item -> item.getProduto().getValorUnitario() * item.getQuantidade())
            .sum();
    }

    @PostLoad
    public void recalcularTotais() {
        calcularTotais();
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        ItemProduto itemProduto = new ItemProduto(this, produto, quantidade);
        this.itensProdutos.add(itemProduto);
        calcularTotais();
    }

    // Getters e Setters
    public int getIdItemPedidoCompra() { return idItemPedidoCompra; }
    public void setIdItemPedidoCompra(int idItemPedidoCompra) { this.idItemPedidoCompra = idItemPedidoCompra; }
    public PedidoCompra getPedidoCompra() { return pedidoCompra; }
    public void setPedidoCompra(PedidoCompra pedidoCompra) { this.pedidoCompra = pedidoCompra; }
    public List<ItemProduto> getItensProdutos() { return itensProdutos; }
    public void setItensProdutos(List<ItemProduto> itensProdutos) {
        this.itensProdutos = itensProdutos;
        for (ItemProduto item : this.itensProdutos) {
            item.setItemPedidoCompra(this);
        }
        calcularTotais();
    }
    public int getQuantidadeTotal() { return quantidadeTotal; }
    public void setQuantidadeTotal(int quantidadeTotal) { this.quantidadeTotal = quantidadeTotal; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
}