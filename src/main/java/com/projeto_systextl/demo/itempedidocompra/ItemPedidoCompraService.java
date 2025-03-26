package com.projeto_systextl.demo.itempedidocompra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_systextl.demo.pedidocompra.PedidoCompra;
import com.projeto_systextl.demo.pedidocompra.PedidoCompraService;
import com.projeto_systextl.demo.produto.ItemProduto;
import com.projeto_systextl.demo.produto.Produto;
import com.projeto_systextl.demo.produto.ProdutoService;

@Service
public class ItemPedidoCompraService {

    @Autowired
    private ItemPedidoCompraRepository repository;

    @Autowired
    private PedidoCompraService pedidoCompraService;

    @Autowired
    private ProdutoService produtoService;

    // GET (Listar todos)
    public List<ItemPedidoCompra> listarItens() {
        List<ItemPedidoCompra> itens = repository.findAll();
        for (ItemPedidoCompra item : itens) {
            item.recalcularTotais();
        }
        return itens;
    }

    // GET (Buscar por ID)
    public ItemPedidoCompra findById(int id) {
        ItemPedidoCompra item = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ItemPedidoCompra com ID " + id + " não encontrado"));
        item.recalcularTotais();
        return item;
    }

    // POST (Criar)
    public ItemPedidoCompra criarItemPedidoCompra(int idPedidoCompra, Map<Integer, Integer> produtosQuantidades) {
        System.out.println("Criando ItemPedidoCompra para idPedidoCompra: " + idPedidoCompra);
        System.out.println("Produtos e quantidades: " + produtosQuantidades);

        PedidoCompra pedidoCompra = pedidoCompraService.findById(idPedidoCompra);
        System.out.println("PedidoCompra encontrado: " + pedidoCompra);

        List<ItemProduto> itensProdutos = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : produtosQuantidades.entrySet()) {
            int idProduto = entry.getKey();
            int quantidade = entry.getValue();
            System.out.println("Processando produto ID: " + idProduto + ", quantidade: " + quantidade);

            if (quantidade <= 0) {
                throw new IllegalArgumentException("Quantidade deve ser maior que zero para o produto ID " + idProduto);
            }
            Produto produto = produtoService.findById(idProduto);
            System.out.println("Produto encontrado: " + produto);

            itensProdutos.add(new ItemProduto(null, produto, quantidade));
        }

        ItemPedidoCompra item = new ItemPedidoCompra(pedidoCompra, itensProdutos);
        System.out.println("ItemPedidoCompra criado: " + item);

        item = repository.save(item);
        System.out.println("ItemPedidoCompra salvo: " + item);

        pedidoCompra.adicionarItem(item);
        pedidoCompra.calcularValorTotal();
        pedidoCompraService.atualizarValorTotal(idPedidoCompra);
        System.out.println("PedidoCompra atualizado: " + pedidoCompra);

        return item;
    }

    // PUT (Atualizar completo)
    public ItemPedidoCompra atualizarItemPedidoCompra(int id, Map<Integer, Integer> produtosQuantidades) {
        ItemPedidoCompra item = findById(id);
        PedidoCompra pedidoCompra = item.getPedidoCompra();

        List<ItemProduto> novosItensProdutos = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : produtosQuantidades.entrySet()) {
            int idProduto = entry.getKey();
            int quantidade = entry.getValue();
            if (quantidade <= 0) {
                throw new IllegalArgumentException("Quantidade deve ser maior que zero para o produto ID " + idProduto);
            }
            Produto produto = produtoService.findById(idProduto);
            novosItensProdutos.add(new ItemProduto(null, produto, quantidade));
        }

        item.setItensProdutos(novosItensProdutos);
        item.recalcularTotais();
        item = repository.save(item);

        pedidoCompra.calcularValorTotal();
        pedidoCompraService.atualizarValorTotal(pedidoCompra.getIdPedidoCompra());

        return item;
    }

    // PATCH (Atualizar parcial)
    public ItemPedidoCompra atualizarParcialItemPedidoCompra(int id, Map<Integer, Integer> produtosQuantidades) {
        ItemPedidoCompra item = findById(id);
        PedidoCompra pedidoCompra = item.getPedidoCompra();

        List<ItemProduto> novosItensProdutos = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : produtosQuantidades.entrySet()) {
            int idProduto = entry.getKey();
            int quantidade = entry.getValue();
            if (quantidade <= 0) {
                throw new IllegalArgumentException("Quantidade deve ser maior que zero para o produto ID " + idProduto);
            }
            Produto produto = produtoService.findById(idProduto);
            novosItensProdutos.add(new ItemProduto(null, produto, quantidade));
        }

        item.setItensProdutos(novosItensProdutos);
        item.recalcularTotais();
        item = repository.save(item);

        pedidoCompra.calcularValorTotal();
        pedidoCompraService.atualizarValorTotal(pedidoCompra.getIdPedidoCompra());

        return item;
    }
}