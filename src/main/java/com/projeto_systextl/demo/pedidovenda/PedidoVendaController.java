package com.projeto_systextl.demo.pedidovenda;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos-venda")
public class PedidoVendaController {

    @Autowired
    private PedidoVendaService service;

    // GET (Listar todos)
    @GetMapping
    public ResponseEntity<List<PedidoVenda>> listarPedidosVenda() {
        List<PedidoVenda> pedidos = service.listarPedidosVenda();
        return ResponseEntity.ok(pedidos);
    }

    // GET (Buscar por ID)
    @GetMapping("/{id}")
    public ResponseEntity<PedidoVenda> buscarPorId(@PathVariable Integer id) {
        try {
            PedidoVenda pedido = service.findById(id);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // POST (Criar)
    @PostMapping
    public ResponseEntity<?> criarPedidoVenda(@RequestBody PedidoVenda pedidoVenda) {
        try {
            // Validar campos obrigatórios
            if (pedidoVenda.getCliente() == null || pedidoVenda.getCliente().getIdCliente() == null) {
                return ResponseEntity.status(400).body("O campo 'cliente' e 'idCliente' são obrigatórios.");
            }
            if (pedidoVenda.getStatus() == null || pedidoVenda.getStatus().trim().isEmpty()) {
                return ResponseEntity.status(400).body("O campo 'status' é obrigatório.");
            }

            PedidoVenda novoPedido = service.criarPedidoVenda(pedidoVenda);
            return ResponseEntity.status(201).body(novoPedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
        }
    }

    // PUT (Atualizar completo)
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPedidoVenda(@PathVariable Integer id, @RequestBody PedidoVenda pedidoVenda) {
        try {
            PedidoVenda pedidoAtualizado = service.atualizarPedidoVenda(id, pedidoVenda);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
        }
    }

    // PATCH (Atualizar parcial)
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcialPedidoVenda(@PathVariable Integer id, @RequestBody PedidoVenda pedidoVenda) {
        try {
            PedidoVenda pedidoAtualizado = service.atualizarParcialPedidoVenda(id, pedidoVenda);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
        }
    }
}