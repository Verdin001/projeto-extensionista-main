package br.com.botaopanico.api.repository;

import br.com.botaopanico.api.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findByStatusAtendimento(String status);

    // NOVO: Busca todos os registros e ordena pelo ID decrescente (mais novos
    // primeiro)
    List<Alerta> findAllByOrderByIdDesc();
}