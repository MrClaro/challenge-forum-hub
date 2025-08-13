package br.com.hub.forum.alura.forumhub_api.domain.dto.topico;

import org.hibernate.validator.constraints.UUID;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroTopico(
    @NotBlank(message = "O titulo não pode ser nulo") String titulo,
    @NotBlank(message = "A mensagem não pode ser nulo") String mensagem,
    @NotBlank(message = "O ID do autor é obrigatório") @UUID(message = "O ID do autor deve ser um UUID") String autorId,
    @NotBlank(message = "O ID do curso é obrigatório") @UUID(message = "O ID do curso deve ser um UUID") String cursoId) {
}
