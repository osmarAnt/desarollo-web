package mx.uv.desarrolloweb;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*") 

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired 
    private Json json;

    // POST /api/auth/register 
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody Usuario nuevoUsuario) {
        if (json.buscarUsuarioPorEmail(nuevoUsuario.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "El correo ya está registrado"));
        }
        Usuario creado = json.guardarUsuario(nuevoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "idUsuario", creado.getIdUsuario(),
                "nombre", creado.getNombre(),
                "email", creado.getEmail()
        ));
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Faltan credenciales"));
        }

        Optional<Usuario> usuarioOpt = json.buscarUsuarioPorEmail(email);

        if (usuarioOpt.isPresent() && password.equals(usuarioOpt.get().getPasswordHash())) {
            Usuario u = usuarioOpt.get();
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Login exitoso",
                    "idUsuario", u.getIdUsuario(),
                    "nombre", u.getNombre(),
                    "email", u.getEmail()
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Correo o contraseña incorrectos"));
    }
}