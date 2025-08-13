package br.com.hub.forum.alura.forumhub_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hub.forum.alura.forumhub_api.domain.dto.resposta.DadosAtualizacaoResposta;
import br.com.hub.forum.alura.forumhub_api.domain.dto.resposta.DadosCadastroResposta;
import br.com.hub.forum.alura.forumhub_api.domain.dto.resposta.DadosDetalhamentoResposta;
import br.com.hub.forum.alura.forumhub_api.domain.entity.Resposta;
import br.com.hub.forum.alura.forumhub_api.infra.exception.resposta.RespostaInativaException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.resposta.RespostaNotFoundException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.topico.TopicoNotFoundException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.usuario.UsuarioNotFoundException;
import br.com.hub.forum.alura.forumhub_api.repositories.RespostaRepository;
import br.com.hub.forum.alura.forumhub_api.repositories.TopicoRepository;
import br.com.hub.forum.alura.forumhub_api.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RespostaService {

  private final RespostaRepository respostaRepository;
  private final TopicoRepository topicoRepository;
  private final UsuarioRepository usuarioRepository;

  @Transactional
  public DadosDetalhamentoResposta cadastrarResposta(
      DadosCadastroResposta dados) {
    var topico = topicoRepository.findById(dados.topicoId())
        .orElseThrow(() -> new TopicoNotFoundException("Tópico não encontrado"));
    var usuario = usuarioRepository.findById(dados.autorId())
        .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"));
    var resposta = new Resposta(dados.mensagem(), topico, usuario);
    var respostaSalva = respostaRepository.save(resposta);
    return new DadosDetalhamentoResposta(respostaSalva);

  }

  public Page<DadosDetalhamentoResposta> listarRespostas(Pageable paginacao) {
    var respostas = respostaRepository.findAllByAtivoTrue(paginacao)
        .map(DadosDetalhamentoResposta::new);
    return respostas;
  }

  public DadosDetalhamentoResposta listarRespostaPorId(String id) {
    var resposta = respostaRepository.findById(id)
        .orElseThrow(() -> new RespostaNotFoundException("Resposta não encontrada"));
    if (!resposta.getAtivo()) {
      throw new RespostaInativaException("Resposta está inativa: " + id);
    }
    return new DadosDetalhamentoResposta(resposta);
  }

  @Transactional
  public DadosDetalhamentoResposta atualizarResposta(
      String id, DadosAtualizacaoResposta dados) {
    var resposta = respostaRepository.findById(id)
        .orElseThrow(() -> new RespostaNotFoundException("Resposta não encontrada"));
    if (!resposta.getAtivo()) {
      throw new RespostaInativaException("Resposta está inativa: " + id);
    }
    resposta.atualizar(dados);
    respostaRepository.save(resposta);
    return new DadosDetalhamentoResposta(resposta);
  }

  @Transactional
  public void excluirResposta(@Valid String id) {
    var resposta = respostaRepository.findById(id)
        .orElseThrow(() -> new RespostaNotFoundException("Resposta não encontrada"));
    if (!resposta.getAtivo()) {
      throw new RespostaInativaException("Resposta está inativa: " + id);
    }
    resposta.desativar();
    respostaRepository.save(resposta);
  }

}
