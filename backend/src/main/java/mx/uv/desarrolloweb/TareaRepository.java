package mx.uv.desarrolloweb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface TareaRepository extends MongoRepository<Tarea, String> {
    @Query("{ 'id_usuario': ?0 }")
    List<Tarea> findByIdUsuario(String idUsuario);
}