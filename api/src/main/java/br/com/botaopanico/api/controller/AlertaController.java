package br.com.botaopanico.api.controller;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.botaopanico.api.model.Alerta;
import br.com.botaopanico.api.repository.AlertaRepository;

@RestController
@RequestMapping("/api/alertas")
@CrossOrigin(origins = "*")
public class AlertaController {

    private final AlertaRepository repository;
    private final JdbcTemplate jdbcTemplate; // Adicionado para fazer a tradução do local

    // O Spring Boot injeta o repository e o jdbcTemplate automaticamente aqui
    public AlertaController(AlertaRepository repository, JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    public Alerta receberAlerta(@RequestBody Alerta alerta) {
        // Verifica se veio alguma OU (setor) na requisição
        if (alerta.getSetor() != null && !alerta.getSetor().isEmpty()) {
            String ouFormatada = alerta.getSetor().toLowerCase().trim();
            String sql = "SELECT descricao_local_fisico FROM local_fisico_ad WHERE nome_ou_ad = ?";
            
            try {
                // Tenta buscar o nome traduzido no banco de dados
                String localTraduzido = jdbcTemplate.queryForObject(sql, String.class, ouFormatada);
                // Substitui "getin" por "TI administrativa" antes de salvar
                alerta.setSetor(localTraduzido); 
            } catch (EmptyResultDataAccessException e) {
                // Se não achar, mantém o nome original (ex: "getin") e adiciona um aviso
                alerta.setSetor(alerta.getSetor() + " (Local não mapeado)");
            }
        }

        // Salva no banco de dados com a informação já traduzida
        return repository.save(alerta);
    }

    @GetMapping("/pendentes")
    public List<Alerta> listarPendentes() {
        return repository.findByStatusAtendimento("PENDENTE");
    }

    @PutMapping("/{id}/atender")
    public Alerta atenderAlerta(@PathVariable Long id, @RequestParam String agente) {
        Alerta alerta = repository.findById(id).orElseThrow();

        alerta.setStatusAtendimento("ATENDIDO");
        alerta.setAgenteAtendimento(agente);
        
        // Registra o momento exato da resolução
        alerta.setDataHoraAtendimento(java.time.LocalDateTime.now());

        // Calcula a diferença em segundos entre a criação do alerta e agora
        long segundos = java.time.Duration.between(alerta.getDataHora(), alerta.getDataHoraAtendimento()).getSeconds();
        alerta.setTempoRespostaSegundos(segundos);

        return repository.save(alerta);
    }

    @GetMapping("/historico")
    public List<Alerta> listarHistorico() {
        return repository.findAllByOrderByIdDesc();
    }
}