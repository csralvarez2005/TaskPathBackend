package TaskPathBackend.repository;

import TaskPathBackend.entity.Programa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface ProgramaRepository extends MongoRepository<Programa, Long> {

    @Query(
            value = "{ 'idNivelFormacion': ?0, 'idUniversidad': ?1, 'estado': ?2 }",
            fields = "{ 'nombre': 1, '_id': 0 }"
    )
    List<Programa> findNombresByFiltros(Long idNivelFormacion, Long idUniversidad, String estado);
}
