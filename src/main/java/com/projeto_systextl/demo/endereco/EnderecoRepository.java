package com.projeto_systextl.demo.endereco;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    // Métodos personalizados podem ser adicionados aqui, se necessário
}
