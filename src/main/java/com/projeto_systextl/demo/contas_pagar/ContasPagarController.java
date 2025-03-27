package com.projeto_systextl.demo.contas_pagar;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contas-pagar")
public class ContasPagarController {

    @Autowired
    private ContasPagarService contasPagarService;

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            List<ContasPagar> contasPagar = contasPagarService.listarTodos();
            return ResponseEntity.ok(contasPagar);
        } catch (Exception e) {
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(500).body(responseList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            ContasPagar contaPagar = contasPagarService.buscarPorId(id);
            return ResponseEntity.ok(contaPagar);
        } catch (Exception e) {
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(500).body(responseList);
        }
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> criarContaPagar(
            @NotNull @RequestPart("idFornecedor") String idFornecedorStr,
            @RequestPart(value = "dataEmissao", required = false) String dataEmissaoStr,
            @RequestPart(value = "dataVencimento", required = false) String dataVencimentoStr,
            @NotNull @RequestPart("valor") String valorStr,
            @RequestPart(value = "situacao", required = false) String situacao) {
        try {
            // Conversões
            Integer idFornecedor = Integer.parseInt(idFornecedorStr);
            LocalDate dataEmissao = dataEmissaoStr != null ? LocalDate.parse(dataEmissaoStr) : null;
            LocalDate dataVencimento = dataVencimentoStr != null ? LocalDate.parse(dataVencimentoStr) : null;
            BigDecimal valor = new BigDecimal(valorStr);

            ContasPagar novaContaPagar = contasPagarService.criarContaPagar(idFornecedor, dataEmissao, dataVencimento, valor, situacao);
            return ResponseEntity.ok(novaContaPagar);
        } catch (IllegalArgumentException e) {
            // Erros de entrada do usuário (Bad Request), incluindo NumberFormatException
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", "Entrada inválida: " + e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(400).body(responseList);
        } catch (Exception e) {
            // Erros internos (Internal Server Error)
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(500).body(responseList);
        }
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> atualizarContaPagar(
            @PathVariable Integer id,
            @NotNull @RequestPart("idFornecedor") String idFornecedorStr,
            @RequestPart(value = "dataEmissao", required = false) String dataEmissaoStr,
            @RequestPart(value = "dataVencimento", required = false) String dataVencimentoStr,
            @NotNull @RequestPart("valor") String valorStr,
            @RequestPart(value = "situacao", required = false) String situacao) {
        try {
            // Conversões
            Integer idFornecedor = Integer.parseInt(idFornecedorStr);
            LocalDate dataEmissao = dataEmissaoStr != null ? LocalDate.parse(dataEmissaoStr) : null;
            LocalDate dataVencimento = dataVencimentoStr != null ? LocalDate.parse(dataVencimentoStr) : null;
            BigDecimal valor = new BigDecimal(valorStr);

            ContasPagar contaPagarAtualizada = contasPagarService.atualizarContaPagar(id, idFornecedor, dataEmissao, dataVencimento, valor, situacao);
            return ResponseEntity.ok(contaPagarAtualizada);
        } catch (IllegalArgumentException e) {
            // Erros de entrada do usuário (Bad Request), incluindo NumberFormatException
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", "Entrada inválida: " + e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(400).body(responseList);
        } catch (Exception e) {
            // Erros internos (Internal Server Error)
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(500).body(responseList);
        }
    }

    @PatchMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> atualizarParcialmenteContaPagar(
            @PathVariable Integer id,
            @RequestPart(value = "idFornecedor", required = false) String idFornecedorStr,
            @RequestPart(value = "dataEmissao", required = false) String dataEmissaoStr,
            @RequestPart(value = "dataVencimento", required = false) String dataVencimentoStr,
            @RequestPart(value = "valor", required = false) String valorStr,
            @RequestPart(value = "situacao", required = false) String situacao) {
        try {
            // Conversões
            Integer idFornecedor = idFornecedorStr != null ? Integer.parseInt(idFornecedorStr) : null;
            LocalDate dataEmissao = dataEmissaoStr != null ? LocalDate.parse(dataEmissaoStr) : null;
            LocalDate dataVencimento = dataVencimentoStr != null ? LocalDate.parse(dataVencimentoStr) : null;
            BigDecimal valor = valorStr != null ? new BigDecimal(valorStr) : null;

            ContasPagar contaPagarAtualizada = contasPagarService.atualizarParcialmenteContaPagar(id, idFornecedor, dataEmissao, dataVencimento, valor, situacao);
            return ResponseEntity.ok(contaPagarAtualizada);
        } catch (IllegalArgumentException e) {
            // Erros de entrada do usuário (Bad Request), incluindo NumberFormatException
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", "Entrada inválida: " + e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(400).body(responseList);
        } catch (Exception e) {
            // Erros internos (Internal Server Error)
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(500).body(responseList);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarContaPagar(@PathVariable Integer id) {
        try {
            contasPagarService.deletarContaPagar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Erros internos (Internal Server Error)
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(500).body(responseList);
        }
    }
}