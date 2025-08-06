package br.com.hub.forum.alura.forumhub_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.hub.forum.alura.forumhub_api.domain.dto.usuario.DadosCadastroUsuario;
import br.com.hub.forum.alura.forumhub_api.infra.exception.ErrorResponse;
import br.com.hub.forum.alura.forumhub_api.services.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @PostMapping
  @Transactional
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dados,
      UriComponentsBuilder uriBuilder) {
    try {
      var usuarioCadastrado = usuarioService.cadastrarUsuario(dados);
      var uri = uriBuilder
          .path("/usuarios/{id}")
          .buildAndExpand(usuarioCadastrado.id())
          .toUri();

      return ResponseEntity.created(uri).body(usuarioCadastrado);
    } catch (IllegalArgumentException e) {
      var errorResponse = new ErrorResponse("Conflito de dados",
          e.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
  }

}
