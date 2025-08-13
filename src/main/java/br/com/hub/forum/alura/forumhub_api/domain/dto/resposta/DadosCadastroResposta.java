package br.com.hub.forum.alura.forumhub_api.domain.dto.resposta;

import org.hibernate.validator.constraints.UUID;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroResposta(
    @NotBlank(message = "A mensagem não pode ser nula") String mensagem,
    @NotBlank(message = "O ID do tópico é obrigatório") @UUID(message = "O ID do tópico deve ser um UUID") String topicoId,
    @NotBlank(message = "O ID do autor da resposta é obrigatório") @UUID(message = "O ID do autor da resposta deve ser um UUID") String autorId) {
}
