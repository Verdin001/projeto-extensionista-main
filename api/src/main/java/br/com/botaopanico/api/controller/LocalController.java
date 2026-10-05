package br.com.botaopanico.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LocalController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/local")
    public Map<String, String> obterLocalFisico(@RequestParam String ou) {
        Map<String, String> resposta = new HashMap<>();
        
        // Converte o texto recebido para minúsculo para garantir que vai bater com o banco de dados
        String ouFormatada = ou.toLowerCase().trim();
        
        String sql = "SELECT descricao_local_fisico FROM local_fisico_ad WHERE nome_ou_ad = ?";
        
        try {
            // Executa a consulta no MySQL
            String localFisico = jdbcTemplate.queryForObject(sql, String.class, ouFormatada);
            resposta.put("local", localFisico);
        } catch (EmptyResultDataAccessException e) {
            // Se a OU não for encontrada na tabela, retorna um aviso padrão
            resposta.put("local", "Local não mapeado no banco de dados");
        }
        
        return resposta;
    }
}