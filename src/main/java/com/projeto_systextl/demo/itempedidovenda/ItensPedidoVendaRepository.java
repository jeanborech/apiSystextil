package com.projeto_systextl.demo.itempedidovenda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensPedidoVendaRepository extends JpaRepository<ItensPedidoVenda, Integer> {
}
