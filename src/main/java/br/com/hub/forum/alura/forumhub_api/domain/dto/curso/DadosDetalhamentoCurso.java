package br.com.hub.forum.alura.forumhub_api.domain.dto.curso;

import br.com.hub.forum.alura.forumhub_api.domain.dto.usuario.DadosDetalhamentoUsuario;
import br.com.hub.forum.alura.forumhub_api.domain.entity.Curso;

public record DadosDetalhamentoCurso(
    String id,
    String nome,
    String categoria,
    Boolean ativo,
    int duracaoHoras,
    double avaliacaoMedia,
    int totalAvaliacoes,
    DadosDetalhamentoUsuario instrutor) {

  public DadosDetalhamentoCurso(Curso curso) {
    this(curso.getId(),
        curso.getNome(),
        curso.getCategoria().name(),
        curso.getAtivo(),
        curso.getDuracaoHoras(),
        curso.getAvaliacaoMedia(),
        curso.getTotalAvaliacoes(),
        new DadosDetalhamentoUsuario(curso.getInstrutor()));
  }
}
