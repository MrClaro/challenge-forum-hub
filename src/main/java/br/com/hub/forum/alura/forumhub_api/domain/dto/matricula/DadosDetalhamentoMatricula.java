package br.com.hub.forum.alura.forumhub_api.domain.dto.matricula;

import java.time.LocalDateTime;

import br.com.hub.forum.alura.forumhub_api.domain.entity.Matricula;
import br.com.hub.forum.alura.forumhub_api.domain.entity.enums.StatusMatricula;

public record DadosDetalhamentoMatricula(
    String id,
    String usuarioId,
    String cursoId,
    LocalDateTime dataMatricula,
    StatusMatricula status,
    double progresso) {

  public DadosDetalhamentoMatricula(Matricula matricula) {
    this(matricula.getId(),
        matricula.getUsuario().getId(),
        matricula.getCurso().getId(),
        matricula.getDataMatricula(),
        matricula.getStatus(),
        matricula.getProgresso());
  }

}
