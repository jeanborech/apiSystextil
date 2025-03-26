package com.projeto_systextl.demo.itempedidovenda;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projeto_systextl.demo.pedidovenda.PedidoVenda;
import com.projeto_systextl.demo.produto.ItemProduto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "itens_pedido_venda")
public class ItensPedidoVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_pedido_venda")
    private Integer idItemPedidoVenda;

    @ManyToOne
    @JoinColumn(name = "id_pedido_venda")
    @JsonBackReference
    private PedidoVenda pedidoVenda;

    @OneToMany(mappedBy = "itensPedidoVenda", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("item-pedido-venda")
    private List<ItemProduto> itensProdutos = new ArrayList<>();

    @Column(name = "quantidade_total")
    private int quantidadeTotal;

    @Column(name = "valor_total")
    private double valorTotal;

    // Construtores
    public ItensPedidoVenda() {}

    public ItensPedidoVenda(List<ItemProduto> itensProdutos) {
        this.itensProdutos = itensProdutos;
        for (ItemProduto item : itensProdutos) {
            item.setItensPedidoVenda(this); // Associa cada ItemProduto ao ItensPedidoVenda
        }
        calcularTotais();
    }

    // Métodos auxiliares
    public void adicionarItem(ItemProduto item) {
        item.setItensPedidoVenda(this);
        itensProdutos.add(item);
        calcularTotais();
    }

    public void calcularTotais() {
        this.quantidadeTotal = itensProdutos.stream().mapToInt(ItemProduto::getQuantidade).sum();
        this.valorTotal = itensProdutos.stream()
                .mapToDouble(item -> item.getProduto().getValorUnitario() * item.getQuantidade())
                .sum();
    }

    // Getters e Setters
    public Integer getIdItemPedidoVenda() {
        return idItemPedidoVenda;
    }

    public void setIdItemPedidoVenda(Integer idItemPedidoVenda) {
        this.idItemPedidoVenda = idItemPedidoVenda;
    }

    public PedidoVenda getPedidoVenda() {
        return pedidoVenda;
    }

    public void setPedidoVenda(PedidoVenda pedidoVenda) {
        this.pedidoVenda = pedidoVenda;
    }

    public List<ItemProduto> getItensProdutos() {
        return itensProdutos;
    }

    public void setItensProdutos(List<ItemProduto> itensProdutos) {
        this.itensProdutos = itensProdutos;
        for (ItemProduto item : itensProdutos) {
            item.setItensPedidoVenda(this);
        }
        calcularTotais();
    }

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(int quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}