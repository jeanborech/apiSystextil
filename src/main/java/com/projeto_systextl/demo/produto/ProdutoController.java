package com.projeto_systextl.demo.produto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    // GET (Listar todos)
    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = service.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    // GET (Buscar por ID)
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable int id) {
        try {
            Produto produto = service.findById(id);
            return ResponseEntity.ok(produto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // POST (Criar com MultipartFile)
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> criarProduto(
            @RequestParam("codigoProduto") String codigoProduto,
            @RequestParam("nomeProduto") String nomeProduto,
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem,
            @RequestParam("valorUnitario") double valorUnitario,
            @RequestParam("statusProduto") String statusProduto) {
        try {
            // Validar campos obrigatórios
            if (codigoProduto == null || codigoProduto.trim().isEmpty()) {
                return ResponseEntity.status(400).body("O campo 'codigoProduto' é obrigatório.");
            }
            if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
                return ResponseEntity.status(400).body("O campo 'nomeProduto' é obrigatório.");
            }
            if (valorUnitario <= 0) {
                return ResponseEntity.status(400).body("O campo 'valorUnitario' deve ser maior que zero.");
            }
            if (statusProduto == null || statusProduto.trim().isEmpty()) {
                return ResponseEntity.status(400).body("O campo 'statusProduto' é obrigatório.");
            }

            Produto novoProduto = service.criarProduto(codigoProduto, nomeProduto, descricao, imagem, valorUnitario, statusProduto);
            return ResponseEntity.status(201).body(novoProduto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
        }
    }

    // PUT (Atualizar completo com MultipartFile)
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> atualizarProduto(
            @PathVariable int id,
            @RequestParam("codigoProduto") String codigoProduto,
            @RequestParam("nomeProduto") String nomeProduto,
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem,
            @RequestParam("valorUnitario") double valorUnitario,
            @RequestParam("statusProduto") String statusProduto) {
        try {
            Produto produtoAtualizado = service.atualizarProduto(id, codigoProduto, nomeProduto, descricao, imagem, valorUnitario, statusProduto);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
        }
    }

    // PATCH (Atualizar parcial com MultipartFile)
    @PatchMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> atualizarParcialProduto(
            @PathVariable int id,
            @RequestParam(value = "codigoProduto", required = false) String codigoProduto,
            @RequestParam(value = "nomeProduto", required = false) String nomeProduto,
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem,
            @RequestParam(value = "valorUnitario", required = false) Double valorUnitario,
            @RequestParam(value = "statusProduto", required = false) String statusProduto) {
        try {
            Produto produtoAtualizado = service.atualizarParcialProduto(id, codigoProduto, nomeProduto, descricao, imagem, valorUnitario, statusProduto);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
        }
    }
    
 // GET (Baixar a imagem do produto)
    @GetMapping("/{id}/imagem")
    public ResponseEntity<byte[]> baixarImagem(@PathVariable int id) {
        try {
            Produto produto = service.findById(id);
            if (produto.getImagem() == null) {
                return ResponseEntity.status(404).body(null);
            }
            byte[] imagemBytes = produto.getImagem().getBytes(1, (int) produto.getImagem().length());
            return ResponseEntity.ok()
                    .header("Content-Type", "image/jpeg") // Ajuste o tipo de conteúdo conforme o formato da imagem
                    .body(imagemBytes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}