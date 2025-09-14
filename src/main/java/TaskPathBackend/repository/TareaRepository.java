package TaskPathBackend.repository;

import TaskPathBackend.entity.Tarea;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TareaRepository extends MongoRepository<Tarea, Long> {

    List<Tarea> findByProyectoId(Long proyectoId);
    List<Tarea> findByUsuarioId(Long usuarioId);
}