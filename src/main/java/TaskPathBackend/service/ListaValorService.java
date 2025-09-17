package TaskPathBackend.service;

import TaskPathBackend.dto.ListaValorDTO;

import java.util.List;

public interface ListaValorService {
    ListaValorDTO crearListaValor(ListaValorDTO dto);
    void eliminarTodos();
    List<ListaValorDTO> crearListaValores(List<ListaValorDTO> dtos);
}
