package mx.uv.desarrolloweb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*") 

@RestController
@RequestMapping("/api/tareas")

public class TareaController {

    @Autowired
    private Json json;

    // GET /api/tareas o GET /api/tareas?idUsuario=user_123
    @GetMapping
    public ResponseEntity<List<Tarea>> listarTareas(@RequestParam(required = false) String idUsuario) {
        return ResponseEntity.ok(json.obtenerTareas(idUsuario));
    }

    // POST /api/tareas
    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea nuevaTarea) {
        Tarea guardada = json.guardarTarea(nuevaTarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    // PUT /api/tareas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTarea(@PathVariable String id, @RequestBody Tarea tareaActualizada) {
        return json.actualizarTarea(id, tareaActualizada)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Tarea no encontrada")));
    }

    // DELETE /api/tareas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTarea(@PathVariable String id) {
        if (json.eliminarTarea(id)) {
            return ResponseEntity.ok(Map.of("mensaje", "Tarea eliminada correctamente"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Tarea no encontrada"));
    }
}