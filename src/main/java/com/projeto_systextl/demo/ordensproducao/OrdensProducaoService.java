package com.projeto_systextl.demo.ordensproducao;

import com.projeto_systextl.demo.produto.Produto;
import com.projeto_systextl.demo.produto.ProdutoRepository;
import com.projeto_systextl.demo.produto.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;

@Service
public class OrdensProducaoService {

    @Autowired
    private OrdensProducaoRepository ordensProducaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public OrdensProducao criarOrdemProducao(OrdensProducao ordensProducao) throws Exception {
        if (ordensProducao.getProduto() == null || ordensProducao.getProduto().getIdProduto() == null){
            throw new IllegalArgumentException("Produto é obrigatório");
        }
        Optional<Produto> produtoOpt = produtoRepository.findById(ordensProducao.getProduto().getIdProduto());
        if (!produtoOpt.isPresent()){
            throw new IllegalArgumentException("Produto não encontrado: " + ordensProducao.getProduto().getIdProduto());
        }
        ordensProducao.setProduto(produtoOpt.get());

        if (ordensProducao.getStatus() != null && !Arrays.asList("EM_ANDAMENTO", "CONCLUIDA", "CANCELADA").contains(ordensProducao.getStatus())) {
            throw new IllegalArgumentException("Status inválido. Valores permitidos: EM_ANDAMENTO, CONCLUIDA, CANCELADA");
        }
        Date now = new java.sql.Date(System.currentTimeMillis());
        if (ordensProducao.getDataCriacao() == null) {
            ordensProducao.setDataCriacao(now);
        }
        ordensProducao.setDataAtualizacao(now);

        return ordensProducaoRepository.save(ordensProducao);
    }

    public ProdutoRepository getProdutoRepository() {
        return produtoRepository;
    }
}
