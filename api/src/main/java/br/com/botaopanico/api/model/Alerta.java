package br.com.botaopanico.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuarioAd;
    private String nomeMaquina;

    private LocalDateTime dataHora = LocalDateTime.now();

    private String statusAtendimento = "PENDENTE";

    // Unidade Organizacional (OU) lida do AD
    private String setor;

    // Nome de quem atendeu o alerta
    private String agenteAtendimento;

    // ---------- Getters e Setters ----------

    // Novas variáveis para o cálculo de SLA
    private LocalDateTime dataHoraAtendimento;
    private Long tempoRespostaSegundos;

    public LocalDateTime getDataHoraAtendimento() {
        return dataHoraAtendimento;
    }

    public void setDataHoraAtendimento(LocalDateTime dataHoraAtendimento) {
        this.dataHoraAtendimento = dataHoraAtendimento;
    }

    public Long getTempoRespostaSegundos() {
        return tempoRespostaSegundos;
    }

    public void setTempoRespostaSegundos(Long tempoRespostaSegundos) {
        this.tempoRespostaSegundos = tempoRespostaSegundos;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuarioAd() {
        return usuarioAd;
    }

    public void setUsuarioAd(String usuarioAd) {
        this.usuarioAd = usuarioAd;
    }

    public String getNomeMaquina() {
        return nomeMaquina;
    }

    public void setNomeMaquina(String nomeMaquina) {
        this.nomeMaquina = nomeMaquina;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatusAtendimento() {
        return statusAtendimento;
    }

    public void setStatusAtendimento(String statusAtendimento) {
        this.statusAtendimento = statusAtendimento;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getAgenteAtendimento() {
        return agenteAtendimento;
    }

    public void setAgenteAtendimento(String agenteAtendimento) {
        this.agenteAtendimento = agenteAtendimento;
    }
}