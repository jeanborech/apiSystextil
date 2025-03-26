package com.projeto_systextl.demo.pedidocompra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoCompraRepository extends JpaRepository<PedidoCompra, Integer> {

    PedidoCompra findByNumeroPedido(String numeroPedido);
}