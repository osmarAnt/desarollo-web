Para incorporar **Spring Security** en tu proyecto y conectarlo con tu persistencia en `db.json`, necesitas agregar la dependencia en Maven, crear un servicio que lea tus usuarios desde el archivo JSON, configurar las reglas de acceso/sesiones y encriptar las contraseñas con `BCrypt`.

### 1. Agregar la dependencia en `pom.xml`

Incluye el starter oficial dentro de la etiqueta `<dependencies>` de tu `pom.xml` y recarga Maven:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

```

---

### 2. Conectar Spring Security con `db.json` (`CustomUserDetailsService.java`)

Crea esta clase en `src/main/java/mx/uv/desarrolloweb/CustomUserDetailsService.java` para indicarle a Spring Security cómo buscar usuarios registrados en tu repositorio JSON:

```java
package mx.uv.desarrolloweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private Json jsonRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = jsonRepo.buscarUsuarioPorEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword_hash())
                .roles("USER")
                .build();
    }
}

```

---

### 3. Configurar filtros, sesiones y encriptación (`SecurityConfig.java`)

Crea `src/main/java/mx/uv/desarrolloweb/SecurityConfig.java`. Esta configuración habilita `BCrypt`, permite el acceso público al frontend y a `/api/auth/**`, protege `/api/tareas/**` exigiendo sesión activa (`JSESSIONID`) y devuelve respuestas JSON `401` en lugar de la pantalla HTML de login por defecto:

```java
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
                .requestMatchers("/", "/index.html", "/styles.css", "/app.js", "/api/auth/**").permitAll()
                .requestMatchers("/api/tareas/**").authenticated()
                .anyRequest().permitAll()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((req, res, authEx) -> {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.setContentType("application/json;charset=UTF-8");
                    res.getWriter().write("{\"error\":\"Debes iniciar sesión primero\"}");
                })
            )
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

```

---

### 4. Autenticación y registro con `BCrypt` (`AuthController.java`)

Implementa `/api/auth/register` para encriptar contraseñas antes de guardarlas en `db.json`, `/api/auth/login` para validar credenciales mediante `AuthenticationManager` y guardar la sesión en la cookie, y `/api/auth/me` para consultar la sesión activa:

```java
package mx.uv.desarrolloweb;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private Json jsonRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody Usuario nuevoUsuario) {
        if (jsonRepo.buscarUsuarioPorEmail(nuevoUsuario.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "El correo ya está registrado"));
        }

        nuevoUsuario.setPassword_hash(passwordEncoder.encode(nuevoUsuario.getPassword_hash()));
        Usuario creado = jsonRepo.guardarUsuario(nuevoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "id_usuario", creado.getId_usuario(),
                "nombre", creado.getNombre(),
                "email", creado.getEmail()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            String email = credenciales.get("email");
            String password = credenciales.get("password");

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, request, response);

            Usuario u = jsonRepo.buscarUsuarioPorEmail(email).orElseThrow();
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Login exitoso",
                    "id_usuario", u.getId_usuario(),
                    "nombre", u.getNombre(),
                    "email", u.getEmail()
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Correo o contraseña incorrectos"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> usuarioActual(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Sin sesión activa"));
        }
        return jsonRepo.buscarUsuarioPorEmail(auth.getName())
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(Map.of(
                        "id_usuario", u.getId_usuario(),
                        "nombre", u.getNombre(),
                        "email", u.getEmail()
                )))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}

```

---

### 5. Identificar al usuario desde el servidor en `TareaController.java`

Inyectando `Authentication auth` en tus endpoints protegidos, Spring Security entrega automáticamente el usuario autenticado de la sesión actual sin depender de parámetros enviados desde el cliente:

```java
@GetMapping
public ResponseEntity<List<Tarea>> listarTareas(Authentication auth) {
    Usuario u = jsonRepo.buscarUsuarioPorEmail(auth.getName()).orElseThrow();
    return ResponseEntity.ok(jsonRepo.obtenerTareas(u.getId_usuario()));
}

@PostMapping
public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea nuevaTarea, Authentication auth) {
    Usuario u = jsonRepo.buscarUsuarioPorEmail(auth.getName()).orElseThrow();
    nuevaTarea.setId_usuario(u.getId_usuario());
    Tarea guardada = jsonRepo.guardarTarea(nuevaTarea);
    return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
}

```

Para probarlo, limpia cualquier usuario antiguo sin encriptar en `database/db.json`, reinicia el servidor con `./mvnw spring-boot:run` y registra una cuenta nueva: el campo `password_hash` se almacenará cifrado con el prefijo `$2a$10$...` y las rutas `/api/tareas/**` rechazarán con `401 Unauthorized` cualquier petición sin sesión válida.