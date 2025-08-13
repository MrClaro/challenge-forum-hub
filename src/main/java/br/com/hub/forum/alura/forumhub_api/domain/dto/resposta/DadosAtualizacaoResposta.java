package br.com.hub.forum.alura.forumhub_api.domain.dto.resposta;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoResposta(
    @NotBlank(message = "A mensgem não pode estar nula") String mensagem,
    Boolean solucao) {
}
