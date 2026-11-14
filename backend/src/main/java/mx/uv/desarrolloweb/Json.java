package mx.uv.desarrolloweb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import tools.jackson.databind.ObjectMapper;

@Repository 
public class Json {
    
    private final String FILE_PATH = "../database/db.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Database leerDB(){
        try{
            File file = new File(FILE_PATH);
            if (!file.exists()){
                Database db = new Database();
                db.setUsuarios(new ArrayList<>());
                db.setUsuarios(new ArrayList<>());
            }
            return objectMapper.readValue(file, Database.class);
        }   catch (Exception e){
            e.printStackTrace();
            Database db = new Database();
            db.setUsuarios(new ArrayList<>());
            db.setUsuarios(new ArrayList<>());
            return db;
        }
    }

    public synchronized void guardarDB(Database db){
        try{
            File file = new File(FILE_PATH);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, db);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Tarea> obtenerTareas(){
        Database db = leerDB();
        return db.getTareas() != null ? db.getTareas() : new ArrayList<>();
    }

    public synchronized void guardarTarea(Tarea nuevaTarea){
        Database db = leerDB();
        if (db.getTareas() == null){
            db.setTareas(new ArrayList<>());
        }
        db.getTareas().add(nuevaTarea);
        guardarDB(db);
    }
}
