package br.com.hub.forum.alura.forumhub_api.domain.dto.matricula;

import org.hibernate.validator.constraints.UUID;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroMatricula(
    @NotBlank(message = "O ID do aluno é obrigatório") @UUID(message = "O ID do aluno deve ser um UUID") String alunoId,
    @NotBlank(message = "O ID do curso é obrigatório") @UUID(message = "O ID do curso deve ser um UUID") String cursoId) {
}
