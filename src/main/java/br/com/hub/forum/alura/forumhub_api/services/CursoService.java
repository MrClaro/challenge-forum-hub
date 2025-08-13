package br.com.hub.forum.alura.forumhub_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hub.forum.alura.forumhub_api.domain.dto.curso.DadosAtualizacaoCurso;
import br.com.hub.forum.alura.forumhub_api.domain.dto.curso.DadosCadastroCurso;
import br.com.hub.forum.alura.forumhub_api.domain.dto.curso.DadosDetalhamentoCurso;
import br.com.hub.forum.alura.forumhub_api.domain.entity.Curso;
import br.com.hub.forum.alura.forumhub_api.infra.exception.curso.CursoInativoException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.curso.CursoJaExisteException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.curso.CursoNotFoundException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.usuario.UsuarioInativoException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.usuario.UsuarioNotFoundException;
import br.com.hub.forum.alura.forumhub_api.repositories.CursoRepository;
import br.com.hub.forum.alura.forumhub_api.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CursoService {

  private final CursoRepository cursoRepository;
  private final UsuarioRepository usuarioRepository;

  @Transactional
  public DadosDetalhamentoCurso cadastrarCurso(@Valid DadosCadastroCurso dados) {
    if (cursoRepository.existsByNomeIgnoreCase(dados.nome())) {
      throw new CursoJaExisteException("Curso já cadastrado com o nome: " + dados.nome());
    }

    var usuario = usuarioRepository.findById(dados.instrutorId())
        .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado: " + dados.instrutorId()));

    var curso = new Curso(dados, usuario);
    var cursoSalvo = cursoRepository.save(curso);
    return new DadosDetalhamentoCurso(cursoSalvo);
  }

  public Page<DadosDetalhamentoCurso> listarCursos(Pageable paginacao) {
    var cursos = cursoRepository.findAllByAtivoTrue(paginacao).map(DadosDetalhamentoCurso::new);
    return cursos;
  }

  public DadosDetalhamentoCurso obterPorId(String id) {
    var curso = cursoRepository.findById(id)
        .orElseThrow(() -> new CursoNotFoundException("Curso não encontrado com o ID: " + id));
    if (!curso.getAtivo()) {
      throw new CursoInativoException("Curso está inativo: " + id);
    }
    return new DadosDetalhamentoCurso(curso);
  }

  @Transactional
  public DadosDetalhamentoCurso atualizarCurso(String id, @Valid DadosAtualizacaoCurso dados) {
    var curso = cursoRepository.findById(id)
        .orElseThrow(() -> new CursoNotFoundException("Curso não encontrado com o ID: " + id));
    if (!curso.getAtivo()) {
      throw new CursoInativoException("Curso está inativo: " + id);
    }

    if (!curso.getNome().equalsIgnoreCase(dados.nome())
        && cursoRepository.existsByNomeIgnoreCase(dados.nome())) {
      throw new CursoJaExisteException("Curso já cadastrado com o nome: " + dados.nome());
    }

    var usuario = usuarioRepository.findById(dados.instrutorId())
        .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado: " + dados.instrutorId()));
    if (!usuario.getAtivo()) {
      throw new UsuarioInativoException("Usuário inativo: " + dados.instrutorId());
    }

    curso.atualizarDados(dados, usuario);
    return new DadosDetalhamentoCurso(curso);
  }

  @Transactional
  public void excluirCurso(String id) {
    var curso = cursoRepository.findById(id)
        .orElseThrow(() -> new CursoNotFoundException("Curso não encontrado com o ID: " + id));
    if (!curso.getAtivo()) {
      throw new CursoInativoException("Curso já está inativo: " + id);
    }
    curso.desativar();
    cursoRepository.save(curso);
  }

}
