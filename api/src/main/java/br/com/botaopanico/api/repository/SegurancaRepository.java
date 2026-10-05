package br.com.botaopanico.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.botaopanico.api.model.Seguranca;

public interface SegurancaRepository extends JpaRepository<Seguranca, Long> {
}