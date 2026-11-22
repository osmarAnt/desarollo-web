package mx.uv.desarrolloweb;

import java.util.Map;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*") 

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired private UsuarioRepository usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired 
    private AuthenticationManager authenticationManager;

    @Autowired 
    private SecurityContextRepository securityContextRepository;

    // POST /api/auth/register 
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody Usuario nuevoUsuario) {
        if (usuarioRepo.findByEmail(nuevoUsuario.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "El correo ya está registrado"));
        }

        // Encripta la contraseña antes de guardarla en db.json
        nuevoUsuario.setPasswordHash(passwordEncoder.encode(nuevoUsuario.getPasswordHash()));
        Usuario creado = usuarioRepo.save(nuevoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "id_usuario", creado.getIdUsuario(),
                "nombre", creado.getNombre(),
                "email", creado.getEmail()
        ));
    }
    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            String email = credenciales.get("email");
            String password = credenciales.get("password");

            // Spring Security valida el usuario y compara el hash BCrypt
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // Guarda la sesión autenticada en la cookie JSESSIONID
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, request, response);

            Usuario u = usuarioRepo.findByEmail(email).orElseThrow();
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Login exitoso",
                    "id_usuario", u.getIdUsuario(),
                    "nombre", u.getNombre(),
                    "email", u.getEmail()
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Correo o contraseña incorrectos"));
        }
    }
    // GET /api/auth/me
    @GetMapping("/me")
    public ResponseEntity<?> usuarioActual(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Sin sesión activa"));
        }
        return usuarioRepo.findByEmail(auth.getName())
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(Map.of(
                        "id_usuario", u.getIdUsuario(),
                        "nombre", u.getNombre(),
                        "email", u.getEmail()
                )))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}