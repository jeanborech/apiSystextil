package com.projeto_systextl.demo.endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private EnderecoService service;

    // Endpoint para listar todos os endereços
    @GetMapping
    public List<Endereco> listarEnderecos() {
        return repository.findAll();
    }

    // Endpoint para criar um novo endereço
    @PostMapping
    public ResponseEntity<Endereco> criarEndereco(
            @RequestParam("rua") String rua,
            @RequestParam(value = "numero", required = false) Integer numero,
            @RequestParam(value = "complemento", required = false) String complemento,
            @RequestParam("bairro") String bairro,
            @RequestParam("cidade") String cidade,
            @RequestParam("estado") String estado,
            @RequestParam("cep") String cep,
            @RequestParam("pais") String pais) {

        try {
            Endereco novoEndereco = service.criarEndereco(rua, numero, complemento, bairro, cidade, estado, cep, pais);
            return ResponseEntity.status(201).body(novoEndereco);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Endpoint para atualizar um endereço existente
    @PutMapping("/{idEndereco}")
    public ResponseEntity<Endereco> atualizarEndereco(
            @PathVariable Integer idEndereco,
            @RequestParam(value = "rua", required = false) String rua,
            @RequestParam(value = "numero", required = false) Integer numero,
            @RequestParam(value = "complemento", required = false) String complemento,
            @RequestParam(value = "bairro", required = false) String bairro,
            @RequestParam(value = "cidade", required = false) String cidade,
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "cep", required = false) String cep,
            @RequestParam(value = "pais", required = false) String pais) {

        try {
            Endereco enderecoAtualizado = service.atualizarEndereco(idEndereco, rua, numero, complemento, bairro, 
                                                                   cidade, estado, cep, pais);
            return ResponseEntity.ok(enderecoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
