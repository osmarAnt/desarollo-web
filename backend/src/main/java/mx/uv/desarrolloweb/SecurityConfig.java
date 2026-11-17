package mx.uv.desarrolloweb;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .securityContext(context -> context
                .securityContextRepository(securityContextRepository())
            )
            .authorizeHttpRequests(auth -> auth
                // Frontend estático y endpoints de autenticación son públicos
                .requestMatchers("/", "/index.html", "/styles.css", "/app.js", "/api/auth/**").permitAll()
                // Cualquier operación con tareas exige haber iniciado sesión
                .requestMatchers("/api/tareas/**").authenticated()
                .anyRequest().permitAll()
            )
            // Evita que Spring redirija al HTML de login por defecto cuando falla un fetch
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((req, res, authEx) -> {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.setContentType("application/json;charset=UTF-8");
                    res.getWriter().write("{\"error\":\"Debes iniciar sesión primero\"}");
                })
            )
            // Endpoint nativo de Spring Security para cerrar sesión y destruir la cookie
            .logout(logout -> logout
                .logoutUrl("/api/auth/logout")
                .logoutSuccessHandler((req, res, auth) -> {
                    res.setStatus(HttpServletResponse.SC_OK);
                    res.setContentType("application/json;charset=UTF-8");
                    res.getWriter().write("{\"mensaje\":\"Sesión cerrada\"}");
                })
            );

        return http.build();
    }
}