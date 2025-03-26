package com.projeto_systextl.demo.pedidovenda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_systextl.demo.cliente.ClienteService;

@Service
public class PedidoVendaService {

    @Autowired
    private PedidoVendaRepository repository;

    @Autowired
    private ClienteService clienteService;

    // GET (Listar todos)
    public List<PedidoVenda> listarPedidosVenda() {
        return repository.findAll();
    }

    // GET (Buscar por ID)
    public PedidoVenda findById(Integer id) { // Alterado de int para Integer
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido de venda com ID " + id + " não encontrado"));
    }

    // POST (Criar)
    public PedidoVenda criarPedidoVenda(PedidoVenda pedidoVenda) {
        // Verificar se o cliente existe
        clienteService.findById(pedidoVenda.getCliente().getIdCliente());

        // Gerar número do pedido automaticamente
        pedidoVenda.gerarNumeroPedidoVenda();

        // Verificar se o número do pedido já existe
        if (repository.existsByNumeroPedidoVenda(pedidoVenda.getNumeroPedidoVenda())) {
            throw new IllegalArgumentException("Já existe um pedido de venda com o número " + pedidoVenda.getNumeroPedidoVenda());
        }

        // Definir data do pedido como a data atual
        pedidoVenda.setDataPedido(LocalDate.now());
        pedidoVenda.setDataAtualizacao(LocalDateTime.now());
        return repository.save(pedidoVenda);
    }

    // PUT (Atualizar completo)
    public PedidoVenda atualizarPedidoVenda(Integer id, PedidoVenda pedidoVenda) { // Alterado de int para Integer
        PedidoVenda pedidoExistente = findById(id);

        // Verificar se o cliente existe
        clienteService.findById(pedidoVenda.getCliente().getIdCliente());

        pedidoExistente.setCliente(pedidoVenda.getCliente());
        pedidoExistente.setDataPedido(pedidoVenda.getDataPedido());
        pedidoExistente.setStatus(pedidoVenda.getStatus());
        pedidoExistente.setDataAtualizacao(LocalDateTime.now());
        pedidoExistente.recalcularValorTotal();
        return repository.save(pedidoExistente);
    }

    // PATCH (Atualizar parcial)
    public PedidoVenda atualizarParcialPedidoVenda(Integer id, PedidoVenda pedidoVenda) { // Alterado de int para Integer
        PedidoVenda pedidoExistente = findById(id);

        if (pedidoVenda.getCliente() != null) {
            clienteService.findById(pedidoVenda.getCliente().getIdCliente());
            pedidoExistente.setCliente(pedidoVenda.getCliente());
        }
        if (pedidoVenda.getDataPedido() != null) {
            pedidoExistente.setDataPedido(pedidoVenda.getDataPedido());
        }
        if (pedidoVenda.getStatus() != null) {
            pedidoExistente.setStatus(pedidoVenda.getStatus());
        }
        pedidoExistente.setDataAtualizacao(LocalDateTime.now());
        pedidoExistente.recalcularValorTotal();
        return repository.save(pedidoExistente);
    }

    // Método auxiliar para atualizar o valor total
    public void atualizarValorTotal(Integer id) { // Alterado de int para Integer
        PedidoVenda pedidoVenda = findById(id);
        pedidoVenda.recalcularValorTotal();
        pedidoVenda.setDataAtualizacao(LocalDateTime.now());
        repository.save(pedidoVenda);
    }
}
