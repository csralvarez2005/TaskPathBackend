package TaskPathBackend.repository;

import TaskPathBackend.entity.Cargo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CargoRepository extends MongoRepository<Cargo, Long> {
}
