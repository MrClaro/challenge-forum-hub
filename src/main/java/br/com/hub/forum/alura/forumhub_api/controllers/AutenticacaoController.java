package br.com.hub.forum.alura.forumhub_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hub.forum.alura.forumhub_api.domain.dto.autenticacao.DadosAutenticacao;
import br.com.hub.forum.alura.forumhub_api.domain.dto.autenticacao.DadosRegistroComToken;
import br.com.hub.forum.alura.forumhub_api.domain.dto.autenticacao.DadosRegistroPublico;
import br.com.hub.forum.alura.forumhub_api.domain.dto.autenticacao.DadosTokenJWT;
import br.com.hub.forum.alura.forumhub_api.services.AutenticacaoService;
import br.com.hub.forum.alura.forumhub_api.services.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AutenticacaoController {

  @Autowired
  private AutenticacaoService autenticacaoService;

  @Autowired
  private UsuarioService usuarioService;

  @PostMapping("/login")
  public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutenticacao dados) {
    var token = autenticacaoService.autenticar(dados);
    return ResponseEntity.ok(new DadosTokenJWT(token));
  }

  @PostMapping("/registrar")
  public ResponseEntity<DadosRegistroComToken> registrarPublico(@RequestBody @Valid DadosRegistroPublico dados) {
    var dadosComCargo = dados.toDadosCadastroUsuario();

    var usuarioCadastrado = usuarioService.cadastrarUsuario(dadosComCargo);

    var dadosAuth = new DadosAutenticacao(dados.email(), dados.senha());
    var token = autenticacaoService.autenticar(dadosAuth);

    var resposta = new DadosRegistroComToken(usuarioCadastrado, token);
    return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
  }

}
