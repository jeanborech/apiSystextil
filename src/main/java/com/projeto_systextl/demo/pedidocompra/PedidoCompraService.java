package com.projeto_systextl.demo.pedidocompra;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_systextl.demo.fornecedor.FornecedorService;
import com.projeto_systextl.demo.itempedidocompra.ItemPedidoCompra;
import com.projeto_systextl.demo.itempedidocompra.ItemPedidoCompraRepository;
import com.projeto_systextl.demo.produto.ItemProduto;
import com.projeto_systextl.demo.produto.Produto;
import com.projeto_systextl.demo.produto.ProdutoService;

@Service
public class PedidoCompraService {

    @Autowired
    private PedidoCompraRepository repository;

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoCompraRepository itemPedidoCompraRepository;

    // GET (Listar todos)
    public List<PedidoCompra> listarPedidos() {
        List<PedidoCompra> pedidos = repository.findAll();
        for (PedidoCompra pedido : pedidos) {
            for (ItemPedidoCompra item : pedido.getItens()) {
                item.recalcularTotais(); // Recalcula os totais apenas em memória
            }
            pedido.calcularValorTotal(); // Recalcula o valorTotal apenas em memória
        }
        return pedidos;
    }

    // GET (Buscar por ID)
    public PedidoCompra findById(int id) {
        PedidoCompra pedido = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("PedidoCompra com ID " + id + " não encontrado"));
        for (ItemPedidoCompra item : pedido.getItens()) {
            item.recalcularTotais(); // Recalcula os totais apenas em memória
        }
        pedido.calcularValorTotal(); // Recalcula o valorTotal apenas em memória
        return pedido;
    }

    // POST (Criar)
    public PedidoCompra criarPedidoCompra(String numeroPedido, int idFornecedor, String status,
                                          List<Map<Integer, Integer>> itens) {
        fornecedorService.findById(idFornecedor);
        PedidoCompra pedido = new PedidoCompra(numeroPedido, idFornecedor, status);
        pedido = repository.save(pedido);

        for (Map<Integer, Integer> produtosQuantidades : itens) {
            List<ItemProduto> itensProdutos = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : produtosQuantidades.entrySet()) {
                int idProduto = entry.getKey();
                int quantidade = entry.getValue();
                if (quantidade <= 0) {
                    throw new IllegalArgumentException("Quantidade deve ser maior que zero para o produto ID " + idProduto);
                }
                Produto produto = produtoService.findById(idProduto);
                itensProdutos.add(new ItemProduto(null, produto, quantidade));
            }
            ItemPedidoCompra item = new ItemPedidoCompra(pedido, itensProdutos);
            itemPedidoCompraRepository.save(item);
            pedido.adicionarItem(item);
        }

        pedido.calcularValorTotal();
        return repository.save(pedido);
    }

    // PUT (Atualizar completo)
    public PedidoCompra atualizarPedidoCompra(int id, String numeroPedido, int idFornecedor, String status,
                                              List<Map<Integer, Integer>> itens) {
        PedidoCompra pedido = findById(id);
        fornecedorService.findById(idFornecedor);
        pedido.setNumeroPedido(numeroPedido);
        pedido.setIdFornecedor(idFornecedor);
        pedido.setStatus(status);
        pedido.setDataPedido(LocalDate.now());
        pedido.setDataAtualizacao(LocalDateTime.now());

        List<ItemPedidoCompra> novosItens = new ArrayList<>();
        for (Map<Integer, Integer> produtosQuantidades : itens) {
            List<ItemProduto> itensProdutos = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : produtosQuantidades.entrySet()) {
                int idProduto = entry.getKey();
                int quantidade = entry.getValue();
                if (quantidade <= 0) {
                    throw new IllegalArgumentException("Quantidade deve ser maior que zero para o produto ID " + idProduto);
                }
                Produto produto = produtoService.findById(idProduto);
                itensProdutos.add(new ItemProduto(null, produto, quantidade));
            }
            ItemPedidoCompra item = new ItemPedidoCompra(pedido, itensProdutos);
            itemPedidoCompraRepository.save(item);
            novosItens.add(item);
        }
        pedido.setItens(novosItens);
        pedido.calcularValorTotal();
        return repository.save(pedido);
    }

    // PATCH (Atualizar parcial)
    public PedidoCompra atualizarParcialPedidoCompra(int id, Map<String, Object> updates) {
        PedidoCompra pedido = findById(id);

        if (updates.containsKey("numeroPedido")) {
            pedido.setNumeroPedido((String) updates.get("numeroPedido"));
        }

        if (updates.containsKey("idFornecedor")) {
            Integer idFornecedor = (Integer) updates.get("idFornecedor");
            fornecedorService.findById(idFornecedor);
            pedido.setIdFornecedor(idFornecedor);
        }

        if (updates.containsKey("status")) {
            pedido.setStatus((String) updates.get("status"));
        }

        if (updates.containsKey("itens")) {
            @SuppressWarnings("unchecked")
            List<Map<String, Integer>> itens = (List<Map<String, Integer>>) updates.get("itens");
            if (itens != null && !itens.isEmpty()) {
                List<ItemPedidoCompra> novosItens = new ArrayList<>();
                for (Map<String, Integer> produtosQuantidades : itens) {
                    List<ItemProduto> itensProdutos = new ArrayList<>();
                    for (Map.Entry<String, Integer> entry : produtosQuantidades.entrySet()) {
                        int idProduto = Integer.parseInt(entry.getKey());
                        int quantidade = entry.getValue();
                        if (quantidade <= 0) {
                            throw new IllegalArgumentException("Quantidade deve ser maior que zero para o produto ID " + idProduto);
                        }
                        Produto produto = produtoService.findById(idProduto);
                        itensProdutos.add(new ItemProduto(null, produto, quantidade));
                    }
                    ItemPedidoCompra item = new ItemPedidoCompra(pedido, itensProdutos);
                    itemPedidoCompraRepository.save(item);
                    novosItens.add(item);
                }
                pedido.setItens(novosItens);
            }
        }

        pedido.setDataAtualizacao(LocalDateTime.now());
        pedido.calcularValorTotal();
        return repository.save(pedido);
    }

    public void atualizarValorTotal(int id) {
        PedidoCompra pedido = findById(id);
        pedido.calcularValorTotal();
        repository.save(pedido);
    }
}