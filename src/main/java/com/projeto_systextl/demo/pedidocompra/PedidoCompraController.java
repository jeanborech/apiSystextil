package com.projeto_systextl.demo.pedidocompra;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos-compra")
public class PedidoCompraController {

    @Autowired
    private PedidoCompraService service;

    // GET (Listar todos)
    @GetMapping
    public ResponseEntity<List<PedidoCompra>> listarPedidos() {
        List<PedidoCompra> pedidos = service.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    // GET (Buscar por ID)
    @GetMapping("/{id}")
    public ResponseEntity<PedidoCompra> buscarPorId(@PathVariable int id) {
        try {
            PedidoCompra pedido = service.findById(id);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // POST (Criar)
    @PostMapping
    public ResponseEntity<PedidoCompra> criarPedidoCompra(
            @RequestBody Map<String, Object> request) {
        try {
            String numeroPedido = (String) request.get("numeroPedido");
            Integer idFornecedor = (Integer) request.get("idFornecedor");
            String status = (String) request.get("status");
            @SuppressWarnings("unchecked")
            List<Map<Integer, Integer>> itens = (List<Map<Integer, Integer>>) request.get("itens");
            PedidoCompra pedido = service.criarPedidoCompra(numeroPedido, idFornecedor, status, itens);
            return ResponseEntity.status(201).body(pedido);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    // PUT (Atualizar completo)
    @PutMapping("/{id}")
    public ResponseEntity<PedidoCompra> atualizarPedidoCompra(
            @PathVariable int id,
            @RequestBody Map<String, Object> request) {
        try {
            String numeroPedido = (String) request.get("numeroPedido");
            Integer idFornecedor = (Integer) request.get("idFornecedor");
            String status = (String) request.get("status");
            @SuppressWarnings("unchecked")
            List<Map<Integer, Integer>> itens = (List<Map<Integer, Integer>>) request.get("itens");
            PedidoCompra pedido = service.atualizarPedidoCompra(id, numeroPedido, idFornecedor, status, itens);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    // PATCH (Atualizar parcial)
    @PatchMapping("/{id}")
    public ResponseEntity<PedidoCompra> atualizarParcialPedidoCompra(
            @PathVariable int id,
            @RequestBody Map<String, Object> updates) {
        try {
            PedidoCompra pedido = service.atualizarParcialPedidoCompra(id, updates);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}