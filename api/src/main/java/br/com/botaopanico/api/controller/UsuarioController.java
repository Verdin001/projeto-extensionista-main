package br.com.botaopanico.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.botaopanico.api.model.Usuario;
import br.com.botaopanico.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // Permite que o seu HTML acesse essa rota
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> fazerLogin(@RequestBody Usuario dadosLogin) {
        // Busca no banco de dados o usuário que tenha o login digitado
        Optional<Usuario> usuarioEncontrado = repository.findByLogin(dadosLogin.getLogin());

        if (usuarioEncontrado.isPresent()) {
            Usuario usuario = usuarioEncontrado.get();
            // Verifica se a senha digitada é igual à salva no banco
            if (usuario.getSenha().equals(dadosLogin.getSenha())) {
                return ResponseEntity.ok(usuario); // Senha certa! Retorna os dados (incluindo o Perfil MASTER)
            }
        }

        // Se não achou o usuário ou a senha estiver errada, retorna erro 401 (Não Autorizado)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login ou senha incorretos.");
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario novoUsuario) {
        // Verifica se o login já está em uso para evitar duplicidade
        if (repository.existsByLogin(novoUsuario.getLogin())) {
            return ResponseEntity.badRequest().body("Este login já está em uso.");
        }

        // Se o perfil não for enviado, define como AGENTE por padrão
        if (novoUsuario.getPerfil() == null || novoUsuario.getPerfil().isEmpty()) {
            novoUsuario.setPerfil("AGENTE");
        }

        return ResponseEntity.ok(repository.save(novoUsuario));
    }
    @PutMapping("/{id}/senha")
    public ResponseEntity<?> mudarSenha(@PathVariable Long id, @RequestParam String novaSenha) {
        return repository.findById(id).map(usuario -> {
            usuario.setSenha(novaSenha);
            repository.save(usuario);
            return ResponseEntity.ok("Senha atualizada com sucesso!");
        }).orElse(ResponseEntity.notFound().build());
    }
}