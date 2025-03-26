package com.projeto_systextl.demo.itempedidocompra;

import java.util.HashMap;
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
@RequestMapping("/itens-pedido-compra")
public class ItemPedidoCompraController {

    @Autowired
    private ItemPedidoCompraService service;

    // GET (Listar todos)
    @GetMapping
    public ResponseEntity<List<ItemPedidoCompra>> listarItens() {
        List<ItemPedidoCompra> itens = service.listarItens();
        return ResponseEntity.ok(itens);
    }

    // GET (Buscar por ID)
    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoCompra> buscarPorId(@PathVariable int id) {
        try {
            ItemPedidoCompra item = service.findById(id);
            return ResponseEntity.ok(item);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // POST (Criar)
    @PostMapping
    public ResponseEntity<?> criarItemPedidoCompra(
            @RequestBody Map<String, Object> request) {
        try {
            // Validar campos obrigatórios
            if (!request.containsKey("idPedidoCompra") || request.get("idPedidoCompra") == null) {
                return ResponseEntity.status(400).body("O campo 'idPedidoCompra' é obrigatório.");
            }
            if (!request.containsKey("produtosQuantidades") || request.get("produtosQuantidades") == null) {
                return ResponseEntity.status(400).body("O campo 'produtosQuantidades' é obrigatório.");
            }

            // Converter idPedidoCompra de forma segura
            Integer idPedidoCompra;
            Object idPedidoCompraObj = request.get("idPedidoCompra");
            if (idPedidoCompraObj instanceof Integer) {
                idPedidoCompra = (Integer) idPedidoCompraObj;
            } else if (idPedidoCompraObj instanceof String) {
                try {
                    idPedidoCompra = Integer.parseInt((String) idPedidoCompraObj);
                } catch (NumberFormatException e) {
                    return ResponseEntity.status(400).body("O campo 'idPedidoCompra' deve ser um número inteiro válido.");
                }
            } else {
                return ResponseEntity.status(400).body("O campo 'idPedidoCompra' deve ser um número inteiro.");
            }

            // Converter produtosQuantidades de forma segura
            @SuppressWarnings("unchecked")
            Map<String, Object> produtosQuantidadesRaw = (Map<String, Object>) request.get("produtosQuantidades");

            // Validar se produtosQuantidades não está vazio
            if (produtosQuantidadesRaw.isEmpty()) {
                return ResponseEntity.status(400).body("O campo 'produtosQuantidades' não pode estar vazio.");
            }

            // Converter as chaves de String para Integer
            Map<Integer, Integer> produtosQuantidades = new HashMap<>();
            for (Map.Entry<String, Object> entry : produtosQuantidadesRaw.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                Integer idProduto;
                Integer quantidade;

                // Converter a chave (idProduto) de String para Integer
                try {
                    idProduto = Integer.parseInt(key);
                } catch (NumberFormatException e) {
                    return ResponseEntity.status(400).body("As chaves de 'produtosQuantidades' devem ser números inteiros válidos: " + key + " não é válido.");
                }

                // Converter o valor (quantidade) de Object para Integer
                if (value instanceof Integer) {
                    quantidade = (Integer) value;
                } else if (value instanceof String) {
                    try {
                        quantidade = Integer.parseInt((String) value);
                    } catch (NumberFormatException e) {
                        return ResponseEntity.status(400).body("Os valores de 'produtosQuantidades' devem ser números inteiros válidos: " + value + " não é válido.");
                    }
                } else {
                    return ResponseEntity.status(400).body("Os valores de 'produtosQuantidades' devem ser números inteiros: " + value + " não é válido.");
                }

                produtosQuantidades.put(idProduto, quantidade);
            }

            ItemPedidoCompra item = service.criarItemPedidoCompra(idPedidoCompra, produtosQuantidades);
            return ResponseEntity.status(201).body(item);
        } catch (ClassCastException e) {
            return ResponseEntity.status(400).body("Formato inválido para os dados fornecidos: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
        }
    }

    // PUT (Atualizar completo)
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarItemPedidoCompra(
            @PathVariable int id,
            @RequestBody Map<Integer, Integer> produtosQuantidades) {
        try {
            ItemPedidoCompra item = service.atualizarItemPedidoCompra(id, produtosQuantidades);
            return ResponseEntity.ok(item);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
        }
    }

    // PATCH (Atualizar parcial)
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcialItemPedidoCompra(
            @PathVariable int id,
            @RequestBody Map<Integer, Integer> produtosQuantidades) {
        try {
            ItemPedidoCompra item = service.atualizarParcialItemPedidoCompra(id, produtosQuantidades);
            return ResponseEntity.ok(item);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
        }
    }
}