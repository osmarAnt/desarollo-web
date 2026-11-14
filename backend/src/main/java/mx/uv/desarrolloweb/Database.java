package mx.uv.desarrolloweb;

import java.util.List;

public class Database {
    private List<Usuario> usuarios;
    private List<Tarea> tareas;
    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    public List<Tarea> getTareas() {
        return tareas;
    }
    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    

}
