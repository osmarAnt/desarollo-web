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
}