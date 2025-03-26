package com.projeto_systextl.demo.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    // Método para verificar se existe um produto com o código fornecido
    boolean existsByCodigoProduto(String codigoProduto);
}