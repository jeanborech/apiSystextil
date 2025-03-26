package com.projeto_systextl.demo.fornecedor;

import com.projeto_systextl.demo.endereco.Endereco;
import com.projeto_systextl.demo.endereco.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository repository;

    @Autowired
    private FornecedorService service;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping

    public List<Fornecedor> listarFornecedores() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Fornecedor> criarFornecedor(
            @RequestParam("nomeFornecedor") String nomeFornecedor,
            @RequestParam("cnpj") String cnpj,
            @RequestParam(value = "telefone", required = false) String telefone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam("idEndereco") Integer idEndereco) {

        try {
            Endereco endereco = enderecoRepository.findById(idEndereco)
                    .orElseThrow(() -> new IllegalArgumentException("Endereço com ID " + idEndereco + " não encontrado."));
            Fornecedor novoFornecedor = service.criarFornecedor(nomeFornecedor, cnpj, telefone, email, status, endereco);
            return ResponseEntity.status(201).body(novoFornecedor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{idFornecedor}")
    public ResponseEntity<Fornecedor> atualizarFornecedor(
            @PathVariable int idFornecedor,
            @RequestParam(value = "nomeFornecedor", required = false) String nomeFornecedor,
            @RequestParam(value = "cnpj", required = false) String cnpj,
            @RequestParam(value = "telefone", required = false) String telefone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "idEndereco", required = false) Integer idEndereco) {

        try {
            Endereco endereco = null;
            if (idEndereco != null) {
                endereco = enderecoRepository.findById(idEndereco)
                        .orElseThrow(() -> new IllegalArgumentException("Endereço com ID " + idEndereco + " não encontrado."));
            }
            Fornecedor fornecedorAtualizado = service.atualizarFornecedor(idFornecedor, nomeFornecedor, cnpj, telefone, 
                                                                         email, status, endereco);
            return ResponseEntity.ok(fornecedorAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PatchMapping("/{idFornecedor}")
    public ResponseEntity<Fornecedor> atualizarParcialFornecedor(
            @PathVariable int idFornecedor,
            @RequestParam(value = "nomeFornecedor", required = false) String nomeFornecedor,
            @RequestParam(value = "cnpj", required = false) String cnpj,
            @RequestParam(value = "telefone", required = false) String telefone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "idEndereco", required = false) Integer idEndereco) {

        try {
            Endereco endereco = null;
            if (idEndereco != null) {
                endereco = enderecoRepository.findById(idEndereco)
                        .orElseThrow(() -> new IllegalArgumentException("Endereço com ID " + idEndereco + " não encontrado."));
            }
            Fornecedor fornecedorAtualizado = service.atualizarParcialFornecedor(idFornecedor, nomeFornecedor, cnpj, 
                                                                                telefone, email, status, endereco);
            return ResponseEntity.ok(fornecedorAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}