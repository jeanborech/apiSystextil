package com.projeto_systextl.demo.itempedidocompra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoCompraRepository extends JpaRepository<ItemPedidoCompra, Integer> {
}
