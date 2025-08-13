package br.com.hub.forum.alura.forumhub_api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfigurations {

  @Autowired
  private SecurityFilter securityFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    // Desabilita CSRF
    http.csrf(csrf -> csrf.disable());

    // Sessão stateless
    http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // Configuração de autorização
    http.authorizeHttpRequests(req -> {
      // Endpoints públicos
      req.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
      req.requestMatchers(HttpMethod.POST, "/auth/registrar").permitAll();

      // Swagger/OpenAPI
      req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();

      // Endpoints administrativos
      req.requestMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.GET, "/usuarios/**").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.PUT, "/usuarios/**").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("ADMIN");

      req.requestMatchers(HttpMethod.POST, "/cursos").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.GET, "/cursos/**").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.PUT, "/cursos/**").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.DELETE, "/cursos/**").hasRole("ADMIN");

      req.requestMatchers(HttpMethod.POST, "/matriculas").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.GET, "/matriculas/**").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.PUT, "/matriculas/**").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.DELETE, "/matriculas/**").hasRole("ADMIN");

      req.requestMatchers(HttpMethod.POST, "/topicos").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.GET, "/topicos/**").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.PUT, "/topicos/**").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.DELETE, "/topicos/**").hasRole("ADMIN");

      req.requestMatchers(HttpMethod.POST, "/respostas").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.GET, "/respostas/**").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.PUT, "/respostas/**").hasRole("ADMIN");
      req.requestMatchers(HttpMethod.DELETE, "/respostas/**").hasRole("ADMIN");

      // Demais endpoints requerem autenticação
      req.anyRequest().authenticated();
    });

    http.addFilterBefore(securityFilter,
        UsernamePasswordAuthenticationFilter.class);

    return http.build();

  }

  @Bean
  public AuthenticationManager AuthenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
