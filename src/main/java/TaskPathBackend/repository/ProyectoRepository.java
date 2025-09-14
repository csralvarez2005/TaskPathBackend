package TaskPathBackend.repository;

import TaskPathBackend.entity.Proyecto;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProyectoRepository extends MongoRepository<Proyecto, Long> {
    List<Proyecto> findByUsuarioId(Long usuarioId);
}