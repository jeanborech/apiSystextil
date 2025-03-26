package com.projeto_systextl.demo.pedidovenda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoVendaRepository extends JpaRepository<PedidoVenda, Integer> {
    boolean existsByNumeroPedidoVenda(String numeroPedidoVenda);
}
