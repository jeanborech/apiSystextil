package com.projeto_systextl.demo.itempedidovenda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_systextl.demo.pedidovenda.PedidoVenda;
import com.projeto_systextl.demo.pedidovenda.PedidoVendaService;
import com.projeto_systextl.demo.produto.ItemProduto;
import com.projeto_systextl.demo.produto.Produto;
import com.projeto_systextl.demo.produto.ProdutoService;

@Service
public class ItensPedidoVendaService {

    @Autowired
    private ItensPedidoVendaRepository repository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidoVendaService pedidoVendaService;

    // POST (Criar)
    public ItensPedidoVenda criarItemPedidoVenda(Integer idPedidoVenda, Map<Integer, Integer> produtosQuantidades) { // Alterado de int para Integer
        // Buscar o pedido de venda
        PedidoVenda pedidoVenda = pedidoVendaService.findById(idPedidoVenda);

        // Criar a lista de itens de produtos
        List<ItemProduto> itensProdutos = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : produtosQuantidades.entrySet()) {
            int idProduto = entry.getKey();
            int quantidade = entry.getValue();

            if (quantidade <= 0) {
                throw new IllegalArgumentException("A quantidade do produto ID " + idProduto + " deve ser maior que zero");
            }

            Produto produto = produtoService.findById(idProduto);
            ItemProduto itemProduto = new ItemProduto(produto, quantidade);
            itensProdutos.add(itemProduto);
        }

        // Criar o item de pedido de venda
        ItensPedidoVenda item = new ItensPedidoVenda(itensProdutos);

        // Salvar o item
        ItensPedidoVenda savedItem = repository.save(item);

        // Associar ao pedido de venda e atualizar o valor total
        pedidoVenda.adicionarItem(savedItem);
        pedidoVendaService.atualizarValorTotal(idPedidoVenda);

        return savedItem;
    }
}