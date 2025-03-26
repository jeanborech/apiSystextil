package com.projeto_systextl.demo.cliente;

import com.projeto_systextl.demo.endereco.Endereco;
import com.projeto_systextl.demo.endereco.EnderecoRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ClienteService service;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping
    @Transactional // Para evitar LazyInitializationException
    public List<Cliente> listarClientes() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Cliente> criarCliente(
            @RequestParam("nomeEmpresa") String nomeEmpresa,
            @RequestParam("cnpj") String cnpj,
            @RequestParam(value = "telefone", required = false) String telefone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "regimeTributario", required = false) String regimeTributario,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam("idEndereco") Integer idEndereco) {

        try {
            Endereco endereco = enderecoRepository.findById(idEndereco)
                    .orElseThrow(() -> new IllegalArgumentException("Endereço com ID " + idEndereco + " não encontrado."));
            Cliente novoCliente = service.criarCliente(nomeEmpresa, cnpj, telefone, email, regimeTributario, status, endereco);
            return ResponseEntity.status(201).body(novoCliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<Cliente> atualizarCliente(
            @PathVariable int idCliente,
            @RequestParam(value = "nomeEmpresa", required = false) String nomeEmpresa,
            @RequestParam(value = "cnpj", required = false) String cnpj,
            @RequestParam(value = "telefone", required = false) String telefone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "regimeTributario", required = false) String regimeTributario,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "idEndereco", required = false) Integer idEndereco) {

        try {
            Endereco endereco = null;
            if (idEndereco != null) {
                endereco = enderecoRepository.findById(idEndereco)
                        .orElseThrow(() -> new IllegalArgumentException("Endereço com ID " + idEndereco + " não encontrado."));
            }
            Cliente clienteAtualizado = service.atualizarCliente(idCliente, nomeEmpresa, cnpj, telefone, email, 
                                                                regimeTributario, status, endereco);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PatchMapping("/{idCliente}")
    public ResponseEntity<Cliente> atualizarParcialCliente(
            @PathVariable int idCliente,
            @RequestParam(value = "nomeEmpresa", required = false) String nomeEmpresa,
            @RequestParam(value = "cnpj", required = false) String cnpj,
            @RequestParam(value = "telefone", required = false) String telefone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "regimeTributario", required = false) String regimeTributario,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "idEndereco", required = false) Integer idEndereco) {

        try {
            Endereco endereco = null;
            if (idEndereco != null) {
                endereco = enderecoRepository.findById(idEndereco)
                        .orElseThrow(() -> new IllegalArgumentException("Endereço com ID " + idEndereco + " não encontrado."));
            }
            Cliente clienteAtualizado = service.atualizarParcialCliente(idCliente, nomeEmpresa, cnpj, telefone, email, 
                                                                       regimeTributario, status, endereco);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}