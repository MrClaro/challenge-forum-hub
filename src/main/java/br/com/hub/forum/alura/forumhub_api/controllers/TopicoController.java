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

import br.com.hub.forum.alura.forumhub_api.domain.dto.topico.DadosAtualizacaoTopico;
import br.com.hub.forum.alura.forumhub_api.domain.dto.topico.DadosCadastroTopico;
import br.com.hub.forum.alura.forumhub_api.domain.dto.topico.DadosDetalhamentoTopico;
import br.com.hub.forum.alura.forumhub_api.services.TopicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
public class TopicoController {

  private final TopicoService topicoService;

  @PostMapping
  public ResponseEntity<DadosDetalhamentoTopico> cadastrarTopico(@RequestBody @Valid DadosCadastroTopico dados,
      UriComponentsBuilder uriBuilder) {
    var topico = topicoService.cadastrarTopico(dados);
    var uri = uriBuilder.path("/topicos/{id}")
        .buildAndExpand(topico.id())
        .toUri();
    return ResponseEntity.created(uri).body(topico);
  }

  @GetMapping
  public ResponseEntity<Page<DadosDetalhamentoTopico>> listarTopicos(
      @PageableDefault(size = 10, sort = { "titulo" }) Pageable paginacao) {
    var topicos = topicoService.listarTopicos(paginacao);
    return topicos;
  }

  @GetMapping("/{id}")
  public ResponseEntity<DadosDetalhamentoTopico> listarTopicoPorId(@PathVariable String id) {
    var topico = topicoService.obterPorId(id);
    return ResponseEntity.ok(topico);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<DadosDetalhamentoTopico> atualizarTopico(
      @PathVariable String id,
      @RequestBody @Valid DadosAtualizacaoTopico dados) {
    var topicoAtualizado = topicoService.atualizarTopico(id, dados);
    return ResponseEntity.ok(topicoAtualizado);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluirTopico(@PathVariable String id) {
    topicoService.excluirTopico(id);
    return ResponseEntity.noContent().build();
  }

}
