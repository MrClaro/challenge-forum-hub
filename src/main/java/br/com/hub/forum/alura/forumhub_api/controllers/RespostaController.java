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

import br.com.hub.forum.alura.forumhub_api.domain.dto.resposta.DadosCadastroResposta;
import br.com.hub.forum.alura.forumhub_api.domain.dto.resposta.DadosDetalhamentoResposta;
import br.com.hub.forum.alura.forumhub_api.infra.exception.resposta.DadosAtualizacaoResposta;
import br.com.hub.forum.alura.forumhub_api.services.RespostaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/respostas")
@RequiredArgsConstructor
public class RespostaController {

  private final RespostaService respostaService;

  @PostMapping
  public ResponseEntity<DadosDetalhamentoResposta> cadastrarResposta(
      @RequestBody @Valid DadosCadastroResposta dados,
      UriComponentsBuilder uriBuilder) {

    var resposta = respostaService.cadastrarResposta(dados);
    var uri = uriBuilder
        .path("/respostas/{id}")
        .buildAndExpand(resposta.id())
        .toUri();

    return ResponseEntity.created(uri).body(resposta);
  }

  @GetMapping
  public ResponseEntity<Page<DadosDetalhamentoResposta>> listarRespostas(
      @PageableDefault(size = 10, sort = "dataCriacao") Pageable paginacao) {
    var respostas = respostaService.listarRespostas(paginacao);
    return ResponseEntity.ok(respostas);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DadosDetalhamentoResposta> listarRespostaPorId(
      @PathVariable @Valid String id) {
    var resposta = respostaService.listarRespostaPorId(id);
    return ResponseEntity.ok(resposta);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<DadosDetalhamentoResposta> atualizarResposta(
      @PathVariable @Valid String id,
      @RequestBody @Valid DadosAtualizacaoResposta dados) {
    var respostaAtualizada = respostaService.atualizarResposta(id, dados);
    return ResponseEntity.ok(respostaAtualizada);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluirResposta(@PathVariable @Valid String id) {
    respostaService.excluirResposta(id);
    return ResponseEntity.noContent().build();
  }
}
