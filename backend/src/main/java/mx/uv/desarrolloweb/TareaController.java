package mx.uv.desarrolloweb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") 

@RestController
@RequestMapping("/api")

public class TareaController {

    @Autowired
    private Json json;

    @GetMapping("/tareas")
    public List<Tarea> listarTareas() {
        return json.obtenerTareas();
    }

    @PostMapping("/tareas")
    public String crearTarea(@RequestBody Tarea nuevaTarea) {
        json.guardarTarea(nuevaTarea);
        return "Tarea guardada con éxito en el db.json";
    }
}