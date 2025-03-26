package com.projeto_systextl.demo.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Método personalizado para buscar por CNPJ
    Cliente findByCnpj(String cnpj);
}