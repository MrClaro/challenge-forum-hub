package br.com.hub.forum.alura.forumhub_api.domain.dto.usuario;

import br.com.hub.forum.alura.forumhub_api.domain.entity.enums.Cargo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DadosCadastroUsuario(

    @NotNull @Size(min = 3, max = 200, message = "O nome deve ter no minímo 3 caracteres") String nome,

    @Email(message = "Insira um email válido") @NotNull @Size(max = 255, message = "O email deve ter no máximo 255 caracteres") String email,

    @NotNull @Size(min = 8, max = 255) @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "A senha deve ter pelo menos 8 caracteres e conter pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial.") String senha,

    Cargo cargo

) {
}
