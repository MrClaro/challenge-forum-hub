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

import br.com.hub.forum.alura.forumhub_api.domain.dto.curso.DadosAtualizacaoCurso;
import br.com.hub.forum.alura.forumhub_api.domain.dto.curso.DadosCadastroCurso;
import br.com.hub.forum.alura.forumhub_api.domain.dto.curso.DadosDetalhamentoCurso;
import br.com.hub.forum.alura.forumhub_api.services.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {

  private final CursoService cursoService;

  @PostMapping
  public ResponseEntity<DadosDetalhamentoCurso> cadastrarCurso(@RequestBody @Valid DadosCadastroCurso dados,
      UriComponentsBuilder uriBuilder) {
    var curso = cursoService.cadastrarCurso(dados);
    var uri = uriBuilder.path("/cursos/{id}")
        .buildAndExpand(curso.id())
        .toUri();
    return ResponseEntity.created(uri).body(curso);
  }

  @GetMapping
  public ResponseEntity<Page<DadosDetalhamentoCurso>> listarCursos(
      @PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
    var cursos = cursoService.listarCursos(paginacao);
    return ResponseEntity.ok(cursos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DadosDetalhamentoCurso> listarCursoPorId(@PathVariable String id) {
    var curso = cursoService.obterPorId(id);
    return ResponseEntity.ok(curso);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<DadosDetalhamentoCurso> atualizarCurso(
      @PathVariable String id,
      @RequestBody @Valid DadosAtualizacaoCurso dados) {
    var cursoAtualizado = cursoService.atualizarCurso(id, dados);
    return ResponseEntity.ok(cursoAtualizado);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluirCurso(@PathVariable String id) {
    cursoService.excluirCurso(id);
    return ResponseEntity.noContent().build();
  }

}
