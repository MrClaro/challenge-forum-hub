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

import br.com.hub.forum.alura.forumhub_api.domain.dto.matricula.DadosAtualizacaoMatricula;
import br.com.hub.forum.alura.forumhub_api.domain.dto.matricula.DadosCadastroMatricula;
import br.com.hub.forum.alura.forumhub_api.domain.dto.matricula.DadosDetalhamentoMatricula;
import br.com.hub.forum.alura.forumhub_api.services.MatriculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

  private final MatriculaService matriculaService;

  @PostMapping
  public ResponseEntity<DadosDetalhamentoMatricula> cadastrarMatricula(
      @RequestBody @Valid DadosCadastroMatricula dados,
      UriComponentsBuilder uriBuilder) {

    var matricula = matriculaService.cadastrarMatricula(dados);
    var uri = uriBuilder
        .path("/matriculas/{id}")
        .buildAndExpand(matricula.id())
        .toUri();

    return ResponseEntity.created(uri).body(matricula);
  }

  @GetMapping
  public ResponseEntity<Page<DadosDetalhamentoMatricula>> listarMatriculas(
      @PageableDefault(size = 10, sort = { "id" }) Pageable pageable) {
    var matriculas = matriculaService.listarMatriculas(pageable);
    return ResponseEntity.ok(matriculas);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DadosDetalhamentoMatricula> listarMatriculaPorId(
      @PathVariable String id) {
    var matricula = matriculaService.obterPorId(id);
    return ResponseEntity.ok(matricula);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<DadosDetalhamentoMatricula> atualizarMatricula(
      @PathVariable String id,
      @RequestBody @Valid DadosAtualizacaoMatricula dados) {
    var matriculaAtualizada = matriculaService.atualizarMatricula(dados, id);
    return ResponseEntity.ok(matriculaAtualizada);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> cancelarMatricula(@PathVariable String id) {
    matriculaService.cancelarMatricula(id);
    return ResponseEntity.noContent().build();
  }
}
