package com.projeto_systextl.demo.endereco;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco criarEndereco(String rua, Integer numero, String complemento, String bairro, 
                                  String cidade, String estado, String cep, String pais) {
        // Validações básicas
        if (rua == null || rua.trim().isEmpty()) {
            throw new IllegalArgumentException("Rua é obrigatória.");
        }
        if (bairro == null || bairro.trim().isEmpty()) {
            throw new IllegalArgumentException("Bairro é obrigatório.");
        }
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade é obrigatória.");
        }
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("Estado é obrigatório.");
        }
        if (cep == null || cep.trim().isEmpty()) {
            throw new IllegalArgumentException("CEP é obrigatório.");
        }
        if (pais == null || pais.trim().isEmpty()) {
            throw new IllegalArgumentException("País é obrigatório.");
        }

        // Criação do objeto Endereco
        Endereco endereco = new Endereco(rua, numero, complemento, bairro, cidade, estado, cep, pais);
        return enderecoRepository.save(endereco);
    }

    public Endereco atualizarEndereco(Integer idEndereco, String rua, Integer numero, String complemento, 
                                      String bairro, String cidade, String estado, String cep, String pais) {
        Endereco endereco = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado"));

        if (rua != null && !rua.trim().isEmpty()) endereco.setRua(rua);
        if (numero != null) endereco.setNumero(numero);
        if (complemento != null) endereco.setComplemento(complemento);
        if (bairro != null && !bairro.trim().isEmpty()) endereco.setBairro(bairro);
        if (cidade != null && !cidade.trim().isEmpty()) endereco.setCidade(cidade);
        if (estado != null && !estado.trim().isEmpty()) endereco.setEstado(estado);
        if (cep != null && !cep.trim().isEmpty()) endereco.setCep(cep);
        if (pais != null && !pais.trim().isEmpty()) endereco.setPais(pais);

        return enderecoRepository.save(endereco);
    }
}