package com.projeto_systextl.demo.produto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projeto_systextl.demo.itempedidocompra.ItemPedidoCompra;

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
    @JoinColumn(name = "ID_ITEM_PEDIDO_COMPRA", nullable = false)
    @JsonBackReference // Evita serializar o ItemPedidoCompra
    private ItemPedidoCompra itemPedidoCompra;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PRODUTO", nullable = false)
    private Produto produto;

    @Column(name = "QUANTIDADE", nullable = false)
    private int quantidade;

    public ItemProduto() {}

    public ItemProduto(ItemPedidoCompra itemPedidoCompra, Produto produto, int quantidade) {
        this.itemPedidoCompra = itemPedidoCompra;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public int getIdItemProduto() { return idItemProduto; }
    public void setIdItemProduto(int idItemProduto) { this.idItemProduto = idItemProduto; }
    public ItemPedidoCompra getItemPedidoCompra() { return itemPedidoCompra; }
    public void setItemPedidoCompra(ItemPedidoCompra itemPedidoCompra) { this.itemPedidoCompra = itemPedidoCompra; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}