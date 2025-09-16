package TaskPathBackend.repository;


import TaskPathBackend.entity.ListaValor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ListaValorRepository extends MongoRepository<ListaValor, Long> {

    List<ListaValor> findByCategoria(String categoria);

    List<ListaValor> findByEstado(String estado);

    List<ListaValor> findByReferencia(String referencia);
}
