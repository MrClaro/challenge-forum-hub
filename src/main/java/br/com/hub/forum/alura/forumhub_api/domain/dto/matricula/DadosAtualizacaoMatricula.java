package br.com.hub.forum.alura.forumhub_api.domain.dto.matricula;

import br.com.hub.forum.alura.forumhub_api.domain.entity.enums.StatusMatricula;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record DadosAtualizacaoMatricula(
    StatusMatricula status,
    @Min(value = 0, message = "O progresso deve começar pelo 0%") @Max(value = 100, message = "O progresso tem o limite de 100%") double progresso,
    @Min(value = 0, message = "A avaliação começar pela nota 0") @Max(value = 5, message = "A avaliação não pode exceder a nota 5") Integer avaliacaoAluno,
    String comentario) {
}
