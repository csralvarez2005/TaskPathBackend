package TaskPathBackend.repository;

import TaskPathBackend.entity.Programa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProgramaRepository extends MongoRepository<Programa, Long> {
}
