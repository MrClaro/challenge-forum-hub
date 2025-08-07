package br.com.hub.forum.alura.forumhub_api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.hub.forum.alura.forumhub_api.domain.dto.usuario.DadosAtualizacaoUsuario;
import br.com.hub.forum.alura.forumhub_api.domain.dto.usuario.DadosCadastroUsuario;
import br.com.hub.forum.alura.forumhub_api.domain.dto.usuario.DadosDetalhamentoUsuario;
import br.com.hub.forum.alura.forumhub_api.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

  private final UsuarioService usuarioService;

  @PostMapping
  public ResponseEntity<DadosDetalhamentoUsuario> cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dados,
      UriComponentsBuilder uriBuilder) {
    var usuarioCadastrado = usuarioService.cadastrarUsuario(dados);
    var uri = uriBuilder
        .path("/usuarios/{id}")
        .buildAndExpand(usuarioCadastrado.id())
        .toUri();

    return ResponseEntity.created(uri).body(usuarioCadastrado);
  }

  @GetMapping
  public ResponseEntity<Page<DadosDetalhamentoUsuario>> listarUsuarios(
      @PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
    var usuarios = usuarioService.listarUsuarios(paginacao);
    return ResponseEntity.ok(usuarios);

  }

  @GetMapping("/{id}")
  public ResponseEntity<DadosDetalhamentoUsuario> listarUsuarioPorId(@PathVariable String id) {
    var usuario = usuarioService.obterPorId(id);
    return ResponseEntity.ok(usuario);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<DadosDetalhamentoUsuario> atualizarUsuario(
      @PathVariable String id,
      @RequestBody @Valid DadosAtualizacaoUsuario dados) {
    var usuarioAtualizado = usuarioService.atualizarUsuario(id, dados);
    return ResponseEntity.ok(usuarioAtualizado);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarUsuario(@PathVariable String id) {
    usuarioService.deletarUsuario(id);
    return ResponseEntity.noContent().build();
  }
}
