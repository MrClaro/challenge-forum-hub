package br.com.hub.forum.alura.forumhub_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hub.forum.alura.forumhub_api.domain.dto.topico.DadosAtualizacaoTopico;
import br.com.hub.forum.alura.forumhub_api.domain.dto.topico.DadosCadastroTopico;
import br.com.hub.forum.alura.forumhub_api.domain.dto.topico.DadosDetalhamentoTopico;
import br.com.hub.forum.alura.forumhub_api.domain.entity.Topico;
import br.com.hub.forum.alura.forumhub_api.infra.exception.curso.CursoInativoException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.curso.CursoNotFoundException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.topico.TopicoInativoException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.topico.TopicoNotFoundException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.usuario.UsuarioNotFoundException;
import br.com.hub.forum.alura.forumhub_api.repositories.CursoRepository;
import br.com.hub.forum.alura.forumhub_api.repositories.TopicoRepository;
import br.com.hub.forum.alura.forumhub_api.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TopicoService {

  private final TopicoRepository topicoRepository;
  private final UsuarioRepository usuarioRepository;
  private final CursoRepository cursoRepository;

  @Transactional
  public DadosDetalhamentoTopico cadastrarTopico(DadosCadastroTopico dados) {
    var usuario = usuarioRepository.findById(dados.autorId())
        .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"));
    var curso = cursoRepository.findById(dados.cursoId())
        .orElseThrow(() -> new CursoNotFoundException("Curso não encontrado"));
    if (!curso.getAtivo()) {
      throw new CursoInativoException("Curso inativo: " + curso.getId());
    }
    if (!usuario.getAtivo()) {
      throw new CursoInativoException("Usuário inativo: " + usuario.getId());
    }
    var topico = new Topico(dados, usuario, curso);
    topicoRepository.save(topico);
    return new DadosDetalhamentoTopico(topico);
  }

  public ResponseEntity<Page<DadosDetalhamentoTopico>> listarTopicos(Pageable paginacao) {
    var topicos = topicoRepository.findAllByAtivoTrue(paginacao)
        .map(DadosDetalhamentoTopico::new);
    return ResponseEntity.ok(topicos);
  }

  public DadosDetalhamentoTopico obterPorId(String id) {
    var topico = topicoRepository.findById(id)
        .orElseThrow(() -> new TopicoNotFoundException("Tópico não encontrado com o ID: " + id));
    if (!topico.getAtivo()) {
      throw new TopicoInativoException("Tópico está inativo: " + id);
    }
    if (!topico.getCurso().getAtivo()) {
      throw new CursoInativoException("Curso do tópico está inativo: " + topico.getCurso().getId());
    }
    return new DadosDetalhamentoTopico(topico);
  }

  @Transactional
  public DadosDetalhamentoTopico atualizarTopico(String id, DadosAtualizacaoTopico dados) {
    var topico = topicoRepository.findById(id)
        .orElseThrow(() -> new TopicoNotFoundException("Tópico não encontrado com o ID: " + id));
    if (!topico.getAtivo()) {
      throw new TopicoInativoException("Tópico está inativo: " + id);
    }
    if (!topico.getCurso().getAtivo()) {
      throw new CursoInativoException("Curso do tópico está inativo: " + topico.getCurso().getId());
    }
    topico.atualizar(dados);
    return new DadosDetalhamentoTopico(topico);
  }

  @Transactional
  public void excluirTopico(String id) {
    var topico = topicoRepository.findById(id)
        .orElseThrow(() -> new TopicoNotFoundException("Tópico não encontrado com o ID: " + id));
    if (!topico.getAtivo()) {
      throw new TopicoInativoException("Tópico já está inativo: " + id);
    }
    if (!topico.getCurso().getAtivo()) {
      throw new CursoInativoException("Curso do tópico está inativo: " + topico.getCurso().getId());
    }
    topico.desativar();
    topicoRepository.save(topico);
  }

}
