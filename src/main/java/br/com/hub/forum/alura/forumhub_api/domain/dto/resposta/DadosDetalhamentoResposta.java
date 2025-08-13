package br.com.hub.forum.alura.forumhub_api.domain.dto.resposta;

import java.time.LocalDateTime;

import br.com.hub.forum.alura.forumhub_api.domain.entity.Resposta;

public record DadosDetalhamentoResposta(
    String id,
    String mensagem,
    String topicoId,
    String autorId,
    LocalDateTime dataCriacao,
    LocalDateTime dataAtualizacao,
    Boolean solucao) {

  public DadosDetalhamentoResposta(Resposta resposta) {
    this(
        resposta.getId(),
        resposta.getMensagem(),
        resposta.getTopico().getId(),
        resposta.getAutor().getId(),
        resposta.getDataCriacao(),
        resposta.getDataAtualizacao(),
        resposta.getSolucao());
  }
}
