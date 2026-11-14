package mx.uv.desarrolloweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva protección CSRF para permitir peticiones POST/PUT
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite el acceso libre a cualquier endpoint
            );
        return http.build();
    }
}