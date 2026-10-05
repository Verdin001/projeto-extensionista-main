package br.com.botaopanico.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.botaopanico.api.model.Usuario;
import br.com.botaopanico.api.repository.UsuarioRepository;

@Component // Diz ao Spring para carregar este arquivo ao ligar
public class InicializadorMaster implements CommandLineRunner {

    private final UsuarioRepository repository;

    public InicializadorMaster(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        // Verifica se o usuário 'admin' já existe no banco. Se não existir, ele cria.
        if (!repository.existsByLogin("admin")) {
            Usuario master = new Usuario();
            master.setNome("Administrador do Sistema");
            master.setLogin("admin");
            master.setSenha("123456"); // Senha padrão inicial
            master.setPerfil("MASTER");
            
            repository.save(master);
            System.out.println("⚠️ ATENÇÃO: Usuário MASTER criado com sucesso no banco de dados.");
        }
    }
}