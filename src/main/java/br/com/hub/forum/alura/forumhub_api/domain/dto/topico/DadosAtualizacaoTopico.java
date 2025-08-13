package br.com.hub.forum.alura.forumhub_api.domain.dto.topico;

import br.com.hub.forum.alura.forumhub_api.domain.entity.enums.Status;

public record DadosAtualizacaoTopico(
    String titulo,
    String mensagem,
    Status status,
    Boolean ativo) {
}
