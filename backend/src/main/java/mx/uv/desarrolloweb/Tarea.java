package mx.uv.desarrolloweb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "tareas")
public class Tarea {
    @Id 
    private String id_tarea;
    private String id_usuario;
    private String materia;
    private String titulo ;
    private String fecha_limite;
    private String estado;

    private String id_remitente;
    private String nombre_remitente;
    private String estado_invitacion;
    
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



    public String getId_remitente() {
        return id_remitente;
    }
    public void setId_remitente(String id_remitente) {
        this.id_remitente = id_remitente;
    }
    public String getNombre_remitente() {
        return nombre_remitente;
    }
    public void setNombre_remitente(String nombre_remitente) {
        this.nombre_remitente = nombre_remitente;
    }
    public String getEstado_invitacion() {
        return estado_invitacion;
    }
    public void setEstado_invitacion(String estado_invitacion) {
        this.estado_invitacion = estado_invitacion;
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
