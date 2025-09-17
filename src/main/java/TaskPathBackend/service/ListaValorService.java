package TaskPathBackend.service;

import TaskPathBackend.dto.ListaValorDTO;
import TaskPathBackend.entity.ListaValor;

import java.util.List;

public interface ListaValorService {
    ListaValorDTO crearListaValor(ListaValorDTO dto);
    void eliminarTodos();
    List<ListaValorDTO> crearListaValores(List<ListaValorDTO> dtos);
    List<ListaValor> obtenerTiposIdentificacion();
}
