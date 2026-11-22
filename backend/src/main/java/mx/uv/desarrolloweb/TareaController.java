package mx.uv.desarrolloweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")

public class TareaController {

    @Autowired
    private Json json;

    // GET /api/tareas o GET /api/tareas?idUsuario=user_123
    @GetMapping
    public ResponseEntity<List<Tarea>> listarTareas(Authentication auth) {
        Usuario u = json.buscarUsuarioPorEmail(auth.getName()).orElseThrow();
        return ResponseEntity.ok(json.obtenerTareas(u.getIdUsuario()));
    }

    // POST /api/tareas
    @PostMapping
    public ResponseEntity<?> crearTarea(@RequestBody Tarea nuevaTarea, Authentication auth) {

        if (nuevaTarea.getMateria() == null || nuevaTarea.getMateria().isBlank() ||
                nuevaTarea.getTitulo() == null || nuevaTarea.getTitulo().isBlank() ||
                nuevaTarea.getFecha_limite() == null || nuevaTarea.getFecha_limite().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Materia, título y fecha límite son obligatorios"));
        }

        Usuario u = json.buscarUsuarioPorEmail(auth.getName()).orElseThrow();
        nuevaTarea.setId_usuario(u.getIdUsuario());
        Tarea guardada = json.guardarTarea(nuevaTarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    // PUT /api/tareas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTarea(@PathVariable String id,
            @RequestBody Tarea tareaActualizada,
            Authentication auth) {
        Usuario u = json.buscarUsuarioPorEmail(auth.getName()).orElseThrow();
        Optional<Tarea> existenteOpt = json.buscarTareaPorId(id);

        if (existenteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Tarea no encontrada"));
        }

        if (!u.getIdUsuario().equals(existenteOpt.get().getId_usuario())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "No tienes permiso para modificar esta tarea"));
        }

        return json.actualizarTarea(id, tareaActualizada)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Tarea no encontrada")));
    }

    // DELETE /api/tareas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTarea(@PathVariable String id, Authentication auth) {
        Usuario u = json.buscarUsuarioPorEmail(auth.getName()).orElseThrow();
        Optional<Tarea> existenteOpt = json.buscarTareaPorId(id);

        if (existenteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Tarea no encontrada"));
        }

        if (!u.getIdUsuario().equals(existenteOpt.get().getId_usuario())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "No tienes permiso para eliminar esta tarea"));
        }

        json.eliminarTarea(id);
        return ResponseEntity.ok(Map.of("mensaje", "Tarea eliminada correctamente"));
    }

@PostMapping("/enviar")
    public ResponseEntity<?> enviarTarea(@RequestBody Map<String, String> payload, Authentication auth) {
        String emailDestinatario = payload.get("destinatario_email");
        String materia = payload.get("materia");
        String titulo = payload.get("titulo");
        String fechaLimite = payload.get("fecha_limite");

        if (emailDestinatario == null || emailDestinatario.isBlank() ||
            materia == null || materia.isBlank() ||
            titulo == null || titulo.isBlank() ||
            fechaLimite == null || fechaLimite.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Correo destinatario, materia, título y fecha límite son obligatorios"));
        }

        Usuario remitente = json.buscarUsuarioPorEmail(auth.getName()).orElseThrow();
        Optional<Usuario> destinatarioOpt = json.buscarUsuarioPorEmail(emailDestinatario.trim());

        if (destinatarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No existe ningún usuario registrado con ese correo"));
        }

        Usuario destinatario = destinatarioOpt.get();
        if (remitente.getIdUsuario().equals(destinatario.getIdUsuario())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Deja vacío el destinatario si la tarea es para ti mismo"));
        }

        Tarea tareaEnviada = new Tarea();
        tareaEnviada.setId_usuario(destinatario.getIdUsuario());
        tareaEnviada.setId_remitente(remitente.getIdUsuario());
        tareaEnviada.setNombre_remitente(remitente.getNombre());
        tareaEnviada.setMateria(materia.trim());
        tareaEnviada.setTitulo(titulo.trim());
        tareaEnviada.setFecha_limite(fechaLimite);
        tareaEnviada.setEstado("pendiente");
        tareaEnviada.setEstado_invitacion("pendiente_aceptacion");

        Tarea guardada = json.guardarTarea(tareaEnviada);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @PutMapping("/{id}/responder")
    public ResponseEntity<?> responderTarea(@PathVariable String id,
                                            @RequestBody Map<String, String> body,
                                            Authentication auth) {
        Usuario u = json.buscarUsuarioPorEmail(auth.getName()).orElseThrow();
        Optional<Tarea> existenteOpt = json.buscarTareaPorId(id);

        if (existenteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Solicitud de tarea no encontrada"));
        }

        if (!u.getIdUsuario().equals(existenteOpt.get().getId_usuario())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "No tienes permiso para responder esta solicitud"));
        }

        String accion = body.get("accion");
        if (!"aceptar".equalsIgnoreCase(accion) && !"rechazar".equalsIgnoreCase(accion)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "La acción debe ser 'aceptar' o 'rechazar'"));
        }

        boolean aceptar = "aceptar".equalsIgnoreCase(accion);
        json.responderInvitacion(id, aceptar);

        return ResponseEntity.ok(Map.of(
                "mensaje", aceptar ? "Tarea aceptada y agregada a tu lista" : "Tarea rechazada"
        ));
    }

}