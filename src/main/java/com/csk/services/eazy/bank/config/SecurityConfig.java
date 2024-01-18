package com.csk.services.eazy.bank.config;

import com.csk.services.eazy.bank.filter.CsrfTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
     By Default Spring Security protects all POST, PUT, DELETE HttpMethods to protect data modification from csrf attacks.
     So we can leverage CSRF Tokens issued by backend and backend send these tokens to UI, UI sends these token in every request
     Backend validates these tokens.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        http.securityContext(Customizer.withDefaults())
                        .sessionManagement(sessionConfigurer -> sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(requests -> requests
                        .requestMatchers("/contact", "/register", "/notices").permitAll()
                        .requestMatchers("/myAccount").hasRole("USER")
                        .requestMatchers("/myCards").hasRole("USER")
                        .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
                .anyRequest().authenticated())
                .addFilterAfter(new CsrfTokenFilter(), BasicAuthenticationFilter.class)
                .oauth2ResourceServer(customizer -> customizer.jwt(cust -> cust.jwtAuthenticationConverter(jwtAuthenticationConverter)));

        return http.cors(cors -> cors.configurationSource(request -> {
                            CorsConfiguration configuration = new CorsConfiguration();
                            configuration.setAllowedOrigins(List.of("http://localhost:4200"));
                            configuration.setAllowedMethods(List.of("*"));
                            configuration.setAllowedHeaders(List.of("*"));
                            configuration.setAllowCredentials(true);
                            configuration.setMaxAge(3600l);

                            return configuration;
                })
                )
                .csrf(csrf -> csrf.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringRequestMatchers("/contact", "/register"))
                .build();
    }
}
