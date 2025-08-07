package br.com.hub.forum.alura.forumhub_api.domain.dto.curso;

import br.com.hub.forum.alura.forumhub_api.domain.entity.enums.Categoria;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCadastroCurso(
    @NotBlank(message = "O nome do curso é obrigatório") @Size(min = 3, max = 200, message = "O nome deve ter entre 3 e 200 caracteres") String nome,

    @NotNull(message = "A categoria do curso é obrigatória") Categoria categoria,

    @NotBlank(message = "O ID do instrutor é obrigatório") String instrutorId,

    @NotNull(message = "A duração do curso é obrigatória") @Min(value = 1, message = "A duração deve ser pelo menos 1 hora") @Max(value = 1000, message = "A duração não pode exceder 1000 horas") Integer duracaoHoras) {
}
