package br.com.botaopanico.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.botaopanico.api.model.Seguranca;
import br.com.botaopanico.api.repository.SegurancaRepository;

@RestController
@RequestMapping("/api/segurancas")
@CrossOrigin(origins = "*")
public class SegurancaController {

    private final SegurancaRepository repository;

    public SegurancaController(SegurancaRepository repository) {
        this.repository = repository;
    }

    // Lista todos os seguranças cadastrados
    @GetMapping
    public List<Seguranca> listar() {
        return repository.findAll();
    }

    // Salva um novo segurança
    @PostMapping
    public Seguranca cadastrar(@RequestBody Seguranca seguranca) {
        return repository.save(seguranca);
    }
}