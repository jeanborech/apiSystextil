package com.projeto_systextl.demo.produto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projeto_systextl.demo.itempedidocompra.ItemPedidoCompra;
import com.projeto_systextl.demo.itempedidovenda.ItensPedidoVenda;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ITEM_PRODUTO")
public class ItemProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ITEM_PRODUTO")
    private int idItemProduto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ITEM_PEDIDO_COMPRA")
    @JsonBackReference("item-pedido-compra")
    private ItemPedidoCompra itemPedidoCompra;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ITEM_PEDIDO_VENDA")
    @JsonBackReference("item-pedido-venda")
    private ItensPedidoVenda itensPedidoVenda; // Novo campo para pedidos de venda

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PRODUTO")
    private Produto produto;

    @Column(name = "QUANTIDADE")
    private int quantidade;

    // Construtores
    public ItemProduto() {}

    public ItemProduto(ItemPedidoCompra itemPedidoCompra, Produto produto, int quantidade) {
        this.itemPedidoCompra = itemPedidoCompra;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ItemProduto(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public int getIdItemProduto() { return idItemProduto; }
    public void setIdItemProduto(int idItemProduto) { this.idItemProduto = idItemProduto; }

    public ItemPedidoCompra getItemPedidoCompra() { return itemPedidoCompra; }
    public void setItemPedidoCompra(ItemPedidoCompra itemPedidoCompra) { this.itemPedidoCompra = itemPedidoCompra; }

    public ItensPedidoVenda getItensPedidoVenda() { return itensPedidoVenda; }
    public void setItensPedidoVenda(ItensPedidoVenda itensPedidoVenda) { this.itensPedidoVenda = itensPedidoVenda; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}