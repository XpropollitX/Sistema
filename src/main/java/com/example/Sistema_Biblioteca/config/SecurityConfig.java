package com.example.Sistema_Biblioteca.config;

import com.example.Sistema_Biblioteca.security.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            escribirError(
                                    response,
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    "No autorizado. Debes iniciar sesión o enviar un token válido"
                            );
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            escribirError(
                                    response,
                                    HttpServletResponse.SC_FORBIDDEN,
                                    "Acceso denegado. No tienes permisos para realizar esta acción"
                            );
                        })
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/libros/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/libros/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/libros/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/ventas/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/ventas/**").permitAll()

                        .requestMatchers("/api/ventas/**")
                        .hasAnyAuthority("ROLE_CLIENT", "ROLE_EMPLEADO", "ROLE_ADMIN")

                        .requestMatchers("/api/empleado/**")
                        .hasAnyAuthority("ROLE_EMPLEADO", "ROLE_ADMIN")

                        .requestMatchers("/api/admin/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers("/", "/login", "/registro", "/css/**", "/js/**", "/images/**")
                        .permitAll()

                        .anyRequest().authenticated()
                )

                .authenticationProvider(authenticationProvider())

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void escribirError(HttpServletResponse response, int status, String message) throws java.io.IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String json = """
                {
                  "success": false,
                  "message": "%s",
                  "data": null,
                  "timestamp": "%s"
                }
                """.formatted(message, timestamp);

        response.getWriter().write(json);
    }
}