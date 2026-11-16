package mx.uv.desarrolloweb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import tools.jackson.databind.ObjectMapper;

@Repository
public class Json {

    private final String FILE_PATH = "../database/db.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    // metodos DB
    public Database leerDB() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                Database db = new Database();
                db.setUsuarios(new ArrayList<>());
                db.setUsuarios(new ArrayList<>());
            }
            return objectMapper.readValue(file, Database.class);
        } catch (Exception e) {
            e.printStackTrace();
            Database db = new Database();
            db.setUsuarios(new ArrayList<>());
            db.setUsuarios(new ArrayList<>());
            return db;
        }
    }

    public synchronized void guardarDB(Database db) {
        try {
            File file = new File(FILE_PATH);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodos Tareas
    public List<Tarea> obtenerTareas(String idUsuario) {
        Database db = leerDB();
        List<Tarea> tareas = db.getTareas() != null ? db.getTareas() : new ArrayList<>();
        
        if (idUsuario != null && !idUsuario.isBlank()) {
            return tareas.stream()
                    .filter(t -> idUsuario.equals(t.getId_usuario()))
                    .collect(Collectors.toList());
        }
        return tareas;
    }

    public synchronized Tarea guardarTarea(Tarea nuevaTarea) {
        Database db = leerDB();
        if (db.getTareas() == null) {
            db.setTareas(new ArrayList<>());
        }
        if (nuevaTarea.getId_tarea() == null || nuevaTarea.getId_tarea().isBlank()) {
            nuevaTarea.setId_tarea(UUID.randomUUID().toString());
        }
        if (nuevaTarea.getEstado() == null || nuevaTarea.getEstado().isBlank()) {
            nuevaTarea.setEstado("pendiente");
        }
        db.getTareas().add(nuevaTarea);
        guardarDB(db);
        return nuevaTarea;
    }
    public synchronized Optional<Tarea> actualizarTarea(String idTarea, Tarea datosNuevos) {
        Database db = leerDB();
        if (db.getTareas() == null) return Optional.empty();

        for (Tarea t : db.getTareas()) {
            if (idTarea != null && idTarea.equals(t.getId_tarea())) {
                if (datosNuevos.getMateria() != null) t.setMateria(datosNuevos.getMateria());
                if (datosNuevos.getTitulo() != null) t.setTitulo(datosNuevos.getTitulo());
                if (datosNuevos.getFecha_limite() != null) t.setFecha_limite(datosNuevos.getFecha_limite());
                if (datosNuevos.getEstado() != null) t.setEstado(datosNuevos.getEstado());
                guardarDB(db);
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    public synchronized boolean eliminarTarea(String idTarea) {
        Database db = leerDB();
        if (db.getTareas() == null)
            return false;

        boolean eliminada = db.getTareas().removeIf(t -> idTarea != null && idTarea.equals(t.getId_tarea()));
        if (eliminada) {
            guardarDB(db);
        }
        return eliminada;
    }
    // Metodos Usuarios

    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        Database db = leerDB();
        if (db.getUsuarios() == null) return Optional.empty();

        return db.getUsuarios().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public synchronized Usuario guardarUsuario(Usuario nuevoUsuario) {
        Database db = leerDB();
        if (db.getUsuarios() == null) {
            db.setUsuarios(new ArrayList<>());
        }
        if (nuevoUsuario.getIdUsuario() == null || nuevoUsuario.getIdUsuario().isBlank()) {
            nuevoUsuario.setIdUsuario(UUID.randomUUID().toString());
        }
        db.getUsuarios().add(nuevoUsuario);
        guardarDB(db);
        return nuevoUsuario;
    }

}
