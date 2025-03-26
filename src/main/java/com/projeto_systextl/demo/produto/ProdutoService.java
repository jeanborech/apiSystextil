package com.projeto_systextl.demo.produto;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    // GET (Listar todos)
    public List<Produto> listarProdutos() {
        return repository.findAll();
    }

    // GET (Buscar por ID)
    public Produto findById(int id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto com ID " + id + " não encontrado"));
    }

    // POST (Criar com MultipartFile)
    public Produto criarProduto(String codigoProduto, String nomeProduto, String descricao,
                                MultipartFile imagem, double valorUnitario, String statusProduto) throws Exception {
        // Verificar se o código do produto já existe
        if (repository.existsByCodigoProduto(codigoProduto)) {
            throw new IllegalArgumentException("Já existe um produto com o código " + codigoProduto);
        }

        Produto produto = new Produto();
        produto.setCodigoProduto(codigoProduto);
        produto.setNomeProduto(nomeProduto);
        produto.setDescricao(descricao);
        produto.setValorUnitario(valorUnitario);
        produto.setStatusProduto(statusProduto);
        produto.setDataCriacao(LocalDateTime.now());
        produto.setDataAtualizacao(LocalDateTime.now());

        // Tratar a imagem (se fornecida)
        if (imagem != null && !imagem.isEmpty()) {
            Session session = entityManager.unwrap(Session.class);
            Blob blob = session.getLobHelper().createBlob(imagem.getBytes());
            produto.setImagem(blob);
        }

        return repository.save(produto);
    }

    // PUT (Atualizar completo com MultipartFile)
    public Produto atualizarProduto(int id, String codigoProduto, String nomeProduto, String descricao,
                                    MultipartFile imagem, double valorUnitario, String statusProduto) throws Exception {
        Produto produtoExistente = findById(id);

        // Verificar se o código do produto já existe (exceto para o próprio produto)
        if (!produtoExistente.getCodigoProduto().equals(codigoProduto) &&
            repository.existsByCodigoProduto(codigoProduto)) {
            throw new IllegalArgumentException("Já existe um produto com o código " + codigoProduto);
        }

        produtoExistente.setCodigoProduto(codigoProduto);
        produtoExistente.setNomeProduto(nomeProduto);
        produtoExistente.setDescricao(descricao);
        produtoExistente.setValorUnitario(valorUnitario);
        produtoExistente.setStatusProduto(statusProduto);
        produtoExistente.setDataAtualizacao(LocalDateTime.now());

        // Tratar a imagem (se fornecida)
        if (imagem != null && !imagem.isEmpty()) {
            Session session = entityManager.unwrap(Session.class);
            Blob blob = session.getLobHelper().createBlob(imagem.getBytes());
            produtoExistente.setImagem(blob);
        }

        return repository.save(produtoExistente);
    }

    // PATCH (Atualizar parcial com MultipartFile)
    public Produto atualizarParcialProduto(int id, String codigoProduto, String nomeProduto, String descricao,
                                           MultipartFile imagem, Double valorUnitario, String statusProduto) throws Exception {
        Produto produtoExistente = findById(id);

        // Verificar se o código do produto já existe (exceto para o próprio produto)
        if (codigoProduto != null &&
            !produtoExistente.getCodigoProduto().equals(codigoProduto) &&
            repository.existsByCodigoProduto(codigoProduto)) {
            throw new IllegalArgumentException("Já existe um produto com o código " + codigoProduto);
        }

        if (codigoProduto != null) {
            produtoExistente.setCodigoProduto(codigoProduto);
        }
        if (nomeProduto != null) {
            produtoExistente.setNomeProduto(nomeProduto);
        }
        if (descricao != null) {
            produtoExistente.setDescricao(descricao);
        }
        if (imagem != null && !imagem.isEmpty()) {
            Session session = entityManager.unwrap(Session.class);
            Blob blob = session.getLobHelper().createBlob(imagem.getBytes());
            produtoExistente.setImagem(blob);
        }
        if (valorUnitario != null && valorUnitario > 0) {
            produtoExistente.setValorUnitario(valorUnitario);
        }
        if (statusProduto != null) {
            produtoExistente.setStatusProduto(statusProduto);
        }
        produtoExistente.setDataAtualizacao(LocalDateTime.now());

        return repository.save(produtoExistente);
    }
}