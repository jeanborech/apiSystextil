package com.projeto_systextl.demo.contas_receber;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contas-receber")
public class ContasReceberController {

    @Autowired
    private ContasReceberService contasReceberService;

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            List<ContasReceber> contasReceber = contasReceberService.listarTodos();
            return ResponseEntity.ok(contasReceber);
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
            ContasReceber contaReceber = contasReceberService.buscarPorId(id);
            return ResponseEntity.ok(contaReceber);
        } catch (Exception e) {
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(500).body(responseList);
        }
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> criarContaReceber(
            @NotNull @RequestPart("idPedidoVenda") String idPedidoVendaStr,
            @RequestPart(value = "dataEmissao", required = false) String dataEmissaoStr,
            @RequestPart(value = "dataVencimento", required = false) String dataVencimentoStr,
            @NotNull @RequestPart("valor") String valorStr,
            @RequestPart(value = "situacao", required = false) String situacao) {
        try {
            // Conversões
            Integer idPedidoVenda = Integer.parseInt(idPedidoVendaStr);
            LocalDateTime dataEmissao = dataEmissaoStr != null ? LocalDateTime.parse(dataEmissaoStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;
            LocalDate dataVencimento = dataVencimentoStr != null ? LocalDate.parse(dataVencimentoStr) : null;
            BigDecimal valor = new BigDecimal(valorStr);

            ContasReceber novaContaReceber = contasReceberService.criarContaReceber(idPedidoVenda, dataEmissao, dataVencimento, valor, situacao);
            return ResponseEntity.ok(novaContaReceber);
        } catch (IllegalArgumentException e) {
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", "Entrada inválida: " + e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(400).body(responseList);
        } catch (Exception e) {
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(500).body(responseList);
        }
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> atualizarContaReceber(
            @PathVariable Integer id,
            @NotNull @RequestPart("idPedidoVenda") String idPedidoVendaStr,
            @RequestPart(value = "dataEmissao", required = false) String dataEmissaoStr,
            @RequestPart(value = "dataVencimento", required = false) String dataVencimentoStr,
            @NotNull @RequestPart("valor") String valorStr,
            @RequestPart(value = "situacao", required = false) String situacao) {
        try {
            // Conversões
            Integer idPedidoVenda = Integer.parseInt(idPedidoVendaStr);
            LocalDateTime dataEmissao = dataEmissaoStr != null ? LocalDateTime.parse(dataEmissaoStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;
            LocalDate dataVencimento = dataVencimentoStr != null ? LocalDate.parse(dataVencimentoStr) : null;
            BigDecimal valor = new BigDecimal(valorStr);

            ContasReceber contaReceberAtualizada = contasReceberService.atualizarContaReceber(id, idPedidoVenda, dataEmissao, dataVencimento, valor, situacao);
            return ResponseEntity.ok(contaReceberAtualizada);
        } catch (IllegalArgumentException e) {
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", "Entrada inválida: " + e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(400).body(responseList);
        } catch (Exception e) {
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(500).body(responseList);
        }
    }

    @PatchMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> atualizarParcialmenteContaReceber(
            @PathVariable Integer id,
            @RequestPart(value = "idPedidoVenda", required = false) String idPedidoVendaStr,
            @RequestPart(value = "dataEmissao", required = false) String dataEmissaoStr,
            @RequestPart(value = "dataVencimento", required = false) String dataVencimentoStr,
            @RequestPart(value = "valor", required = false) String valorStr,
            @RequestPart(value = "situacao", required = false) String situacao) {
        try {
            // Conversões
            Integer idPedidoVenda = idPedidoVendaStr != null ? Integer.parseInt(idPedidoVendaStr) : null;
            LocalDateTime dataEmissao = dataEmissaoStr != null ? LocalDateTime.parse(dataEmissaoStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;
            LocalDate dataVencimento = dataVencimentoStr != null ? LocalDate.parse(dataVencimentoStr) : null;
            BigDecimal valor = valorStr != null ? new BigDecimal(valorStr) : null;

            ContasReceber contaReceberAtualizada = contasReceberService.atualizarParcialmenteContaReceber(id, idPedidoVenda, dataEmissao, dataVencimento, valor, situacao);
            return ResponseEntity.ok(contaReceberAtualizada);
        } catch (IllegalArgumentException e) {
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", "Entrada inválida: " + e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(400).body(responseList);
        } catch (Exception e) {
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(500).body(responseList);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarContaReceber(@PathVariable Integer id) {
        try {
            contasReceberService.deletarContaReceber(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("mensagem", e.getMessage());
            List<Map<String, Object>> responseList = Collections.singletonList(responseMap);
            return ResponseEntity.status(500).body(responseList);
        }
    }
}