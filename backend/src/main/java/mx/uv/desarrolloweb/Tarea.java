package mx.uv.desarrolloweb;

public class Tarea {
    private String id_tarea;
    private String id_usuario;
    private String materia;
    private String titulo ;
    private String fecha_limite;
    private String estado;
    
    public String getId_tarea() {
        return id_tarea;
    }
    public void setId_tarea(String id_tarea) {
        this.id_tarea = id_tarea;
    }
    public String getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
    public String getMateria() {
        return materia;
    }
    public void setMateria(String materia) {
        this.materia = materia;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getFecha_limite() {
        return fecha_limite;
    }
    public void setFecha_limite(String fecha_limite) {
        this.fecha_limite = fecha_limite;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Tarea() {
    }
    public Tarea(String id_tarea, String id_usuario, String materia, String titulo, String fecha_limite,
            String estado) {
        this.id_tarea = id_tarea;
        this.id_usuario = id_usuario;
        this.materia = materia;
        this.titulo = titulo;
        this.fecha_limite = fecha_limite;
        this.estado = estado;
    }

}
