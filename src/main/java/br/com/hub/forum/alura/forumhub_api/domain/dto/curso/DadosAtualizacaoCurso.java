package br.com.hub.forum.alura.forumhub_api.domain.dto.curso;

import br.com.hub.forum.alura.forumhub_api.domain.entity.enums.Categoria;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoCurso(
    @Size(min = 3, max = 200, message = "O nome deve ter entre 3 e 200 caracteres") String nome,
    Categoria categoria,
    @Min(value = 1, message = "A duração deve ser pelo menos 1 hora") @Max(value = 1000, message = "A duração não pode exceder 1000 horas") Integer duracaoHoras,
    String instrutorId) {
}
