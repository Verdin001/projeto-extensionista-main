package br.com.botaopanico.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.botaopanico.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Busca um usuário específico pelo login digitado na tela
    Optional<Usuario> findByLogin(String login);
    
    // Verifica se um login já existe (útil para não deixar cadastrar dois iguais)
    boolean existsByLogin(String login);
}