package com.projeto_systextl.demo.ordensproducao;

import com.projeto_systextl.demo.produto.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ordens_producao")
public class OrdensProducaoController {
    @Autowired
    private OrdensProducaoRepository repository;

    @Autowired
    private OrdensProducaoService service;

    @GetMapping
    public List<OrdensProducao> listarOrdensProducao(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdensProducao> buscarOrdemProducaoPorId(@PathVariable Integer id) {
        Optional<OrdensProducao> ordemProducao = repository.findById(id);
        return ordemProducao.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrdensProducao> criarOrdemProducao(@RequestBody OrdensProducao ordemProducao) {
        try {
            OrdensProducao novaOrdemProducao = service.criarOrdemProducao(ordemProducao);
            return ResponseEntity.status(201).body(novaOrdemProducao);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrdensProducao> atualizarOrdemProducao(@PathVariable Integer id, @RequestBody OrdensProducao ordemProducao) {
        try {
            Optional<OrdensProducao> ordemProducaoExistente = repository.findById(id);
            if (!ordemProducaoExistente.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            OrdensProducao ordemProducaoAtualizada = ordemProducaoExistente.get();

            if (ordemProducao.getNumeroOrdem() != null) {
                ordemProducaoAtualizada.setNumeroOrdem(ordemProducao.getNumeroOrdem());
            }

            if (ordemProducao.getProduto() != null && ordemProducao.getProduto().getIdProduto() != null) {
                Optional<Produto> produtoOpt = service.getProdutoRepository().findById(ordemProducao.getProduto().getIdProduto());
                if (!produtoOpt.isPresent()) {
                    return ResponseEntity.badRequest().build();
                }
                ordemProducaoAtualizada.setProduto(produtoOpt.get());
            }

            if (ordemProducao.getQuantidade() != null && ordemProducao.getQuantidade() > 0) {
                ordemProducaoAtualizada.setQuantidade(ordemProducao.getQuantidade());
            }

            if (ordemProducao.getStatus() != null && Arrays.asList("EM_ANDAMENTO", "CONCLUIDA", "CANCELADA").contains(ordemProducao.getStatus())) {
                ordemProducaoAtualizada.setStatus(ordemProducao.getStatus());
            }


            if (ordemProducao.getIdPedidoVenda() != null) {
                ordemProducaoAtualizada.setIdPedidoVenda(ordemProducao.getIdPedidoVenda());
            }

            ordemProducaoAtualizada.setDataAtualizacao(new Date(System.currentTimeMillis()));

            return ResponseEntity.ok(repository.save(ordemProducaoAtualizada));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}