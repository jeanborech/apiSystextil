package com.projeto_systextl.demo.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = repository.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        try {
        	Usuario novoUsuario = service.criarUsuario(usuario);
            return ResponseEntity.status(201).body(novoUsuario);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(null);
        }
    }

    // PATCH (Atualizar parcial com MultipartFile)
//    @PatchMapping(value = "/{id}")
//    public ResponseEntity<?> atualizarParcialUsuario(
//            @PathVariable Integer idUsuario, // Alterado de int para Integer
//            @RequestParam(value = "usuario", required = false) String usuario,
//            @RequestParam(value = "senha", required = false) String senha,
//            @RequestParam(value = "email", required = false) String email){
//        try {
//        	Usuario usuarioAtualizado = service.atualizarParcialUsuario(idUsuario, usuario, senha, email);
//            return ResponseEntity.ok(usuarioAtualizado);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(400).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
//        }
//    }
    
    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> atualizarParcialUsuario(
            @PathVariable Integer idUsuario,
            @RequestBody Usuario usuarioAtualizado) { // Alterado para @RequestBody
        try {
            // Passa os campos do JSON diretamente para o serviço
            Usuario usuario = service.atualizarParcialUsuario(
                    idUsuario,
                    usuarioAtualizado.getUsuario(),
                    usuarioAtualizado.getSenha(),
                    usuarioAtualizado.getEmail()
            );
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
        }
    }
}
