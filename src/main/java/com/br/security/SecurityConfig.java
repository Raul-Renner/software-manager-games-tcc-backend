//package com.br.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.context.SecurityContextRepository;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//import static org.springframework.http.HttpStatus.FORBIDDEN;
//import static org.springframework.http.HttpStatus.UNAUTHORIZED;
//import static reactor.core.publisher.Mono.fromRunnable;
//
//@Configuration
//@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity
//public class SecurityConfig {
//
//    private AuthenticationManager authenticationManager;
//
//    private SecurityContextRepository securityContextRepository;
//
//    public SecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
//        this.authenticationManager = authenticationManager;
//        this.securityContextRepository = securityContextRepository;
//    }
//
//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity https) {
//
//        return https
//                .exceptionHandling()
//                .authenticationEntryPoint((swe, e) -> fromRunnable(() -> swe.getResponse().setStatusCode(UNAUTHORIZED)))
//                .accessDeniedHandler((swe, e) -> fromRunnable(() -> swe.getResponse().setStatusCode(FORBIDDEN))).and()
//                .csrf().disable()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .authenticationManager(authenticationManager)
//                .securityContextRepository(securityContextRepository)
//                .authorizeExchange()
//                .pathMatchers(HttpMethod.OPTIONS).permitAll()
//                .pathMatchers("/actuator/**").permitAll()
//                .anyExchange().authenticated()
//                .and()
//                .build();
//    }
//}
