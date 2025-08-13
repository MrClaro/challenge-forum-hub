package br.com.hub.forum.alura.forumhub_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hub.forum.alura.forumhub_api.domain.dto.matricula.DadosAtualizacaoMatricula;
import br.com.hub.forum.alura.forumhub_api.domain.dto.matricula.DadosCadastroMatricula;
import br.com.hub.forum.alura.forumhub_api.domain.dto.matricula.DadosDetalhamentoMatricula;
import br.com.hub.forum.alura.forumhub_api.domain.entity.Matricula;
import br.com.hub.forum.alura.forumhub_api.domain.entity.enums.StatusMatricula;
import br.com.hub.forum.alura.forumhub_api.infra.exception.curso.CursoInativoException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.curso.CursoNotFoundException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.matricula.MatriculaCanceladaException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.matricula.MatriculaJaExisteException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.matricula.MatriculaNotFoundException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.usuario.UsuarioNotFoundException;
import br.com.hub.forum.alura.forumhub_api.repositories.CursoRepository;
import br.com.hub.forum.alura.forumhub_api.repositories.MatriculaRepository;
import br.com.hub.forum.alura.forumhub_api.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MatriculaService {

  private final MatriculaRepository matriculaRepository;
  private final UsuarioRepository usuarioRepository;
  private final CursoRepository cursoRepository;

  @Transactional
  public DadosDetalhamentoMatricula cadastrarMatricula(DadosCadastroMatricula dados) {
    var usuario = usuarioRepository.findById(dados.alunoId())
        .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"));
    var curso = cursoRepository.findById(dados.cursoId())
        .orElseThrow(() -> new CursoNotFoundException("Curso não encontrado"));
    if (!curso.getAtivo()) {
      throw new CursoInativoException("Curso inativo: " + curso.getId());
    }
    if (!usuario.getAtivo()) {
      throw new CursoInativoException("Usuário inativo: " + usuario.getId());
    }
    if (matriculaRepository.existsByUsuarioAndCurso(usuario, curso)) {
      throw new MatriculaJaExisteException("Matrícula já existe para o usuário e curso informados");
    }

    var matricula = new Matricula(usuario, curso);
    matriculaRepository.save(matricula);
    return new DadosDetalhamentoMatricula(matricula);
  }

  public Page<DadosDetalhamentoMatricula> listarMatriculas(Pageable pageable) {
    return matriculaRepository.findAll(pageable)
        .map(DadosDetalhamentoMatricula::new);
  }

  public DadosDetalhamentoMatricula obterPorId(String id) {
    var matricula = matriculaRepository.findById(id)
        .orElseThrow(() -> new MatriculaNotFoundException("Matrícula não encontrada: " + id));
    if (matricula.getStatus() == StatusMatricula.CANCELADO) {
      throw new MatriculaCanceladaException("Matrícula cancelada: " + id);
    }
    return new DadosDetalhamentoMatricula(matricula);
  }

  @Transactional
  public DadosDetalhamentoMatricula atualizarMatricula(@Valid DadosAtualizacaoMatricula dados, String id) {
    var matricula = matriculaRepository.findById(id)
        .orElseThrow(() -> new MatriculaNotFoundException("Matrícula não encontrada: " + id));
    if (matricula.getStatus() == StatusMatricula.CANCELADO) {
      throw new MatriculaCanceladaException("Matrícula cancelada: " + id);
    }
    matricula.atualizarDados(dados);
    var matriculaAtualizada = matriculaRepository.save(matricula);
    return new DadosDetalhamentoMatricula(matriculaAtualizada);
  }

  @Transactional
  public void cancelarMatricula(String id) {
    var matricula = matriculaRepository.findById(id)
        .orElseThrow(() -> new MatriculaNotFoundException("Matrícula não encontrada: " + id));
    if (matricula.getStatus() == StatusMatricula.CANCELADO) {
      throw new MatriculaCanceladaException("Matrícula já cancelada: " + id);
    }
    matricula.cancelar();
    matriculaRepository.save(matricula);
  }

}
