package com.projeto_systextl.demo.itempedidovenda;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itens-pedido-venda")
public class ItensPedidoVendaController {

    @Autowired
    private ItensPedidoVendaService service;

    // POST (Criar)
    @PostMapping
    public ResponseEntity<?> criarItemPedidoVenda(@RequestBody Map<String, Object> requestBody) {
        try {
            Integer idPedidoVenda = (Integer) requestBody.get("idPedidoVenda");
            if (idPedidoVenda == null) {
                return ResponseEntity.status(400).body("O campo 'idPedidoVenda' é obrigatório.");
            }

            @SuppressWarnings("unchecked")
            Map<String, Integer> produtosQuantidadesStr = (Map<String, Integer>) requestBody.get("produtosQuantidades");
            if (produtosQuantidadesStr == null || produtosQuantidadesStr.isEmpty()) {
                return ResponseEntity.status(400).body("O campo 'produtosQuantidades' é obrigatório e não pode estar vazio.");
            }

            // Converter as chaves de String para Integer
            Map<Integer, Integer> produtosQuantidades = produtosQuantidadesStr.entrySet().stream()
                    .collect(java.util.stream.Collectors.toMap(
                            entry -> Integer.parseInt(entry.getKey()),
                            entry -> entry.getValue()
                    ));

            ItensPedidoVenda item = service.criarItemPedidoVenda(idPedidoVenda, produtosQuantidades);
            return ResponseEntity.status(201).body(item);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
        }
    }
}