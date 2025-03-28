package com.projeto_systextl.demo.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    // GET (Listar todos)
    public List<Usuario> listarProdutos() {
        return repository.findAll();
    }

    // GET (Buscar por ID)
    public Usuario findById(int id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuario com ID " + id + " não encontrado"));
    }
    //post
    public Usuario criarUsuario(Usuario usuario) throws Exception {
        if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("Usuario não pode ser nulo ou vazio");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nulo ou vazio");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }

        return repository.save(usuario);
    }
    
    //put
    public Usuario atualizarUsuario(int id, String usuario, String senha, String email) throws Exception {
        Usuario usuarioExistente = findById(id);

        if (usuario == null || usuario.trim().isEmpty()) {
            throw new IllegalArgumentException("Usuario não pode ser nulo ou vazio");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }

        usuarioExistente.setUsuario(usuario);
        usuarioExistente.setSenha(senha);
        usuarioExistente.setEmail(email);

        return repository.save(usuarioExistente);
    }

    //patch (Atualizar parcial)
	public Usuario atualizarParcialUsuario(int id, String usuario, String senha, String email) throws Exception {
		Usuario usuarioExistente = findById(id);

		if (usuario != null) {
			usuarioExistente.setUsuario(usuario);
        }
		if (senha != null) {
			usuarioExistente.setSenha(senha);
        }
		if (email != null) {
			usuarioExistente.setEmail(email);
        }
        return repository.save(usuarioExistente);
	}
}