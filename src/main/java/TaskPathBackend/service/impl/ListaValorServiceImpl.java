package TaskPathBackend.service.impl;

import TaskPathBackend.dto.ListaValorDTO;
import TaskPathBackend.entity.ListaValor;
import TaskPathBackend.mapper.ListaValorMapper;
import TaskPathBackend.repository.ListaValorRepository;
import TaskPathBackend.service.ListaValorService;
import TaskPathBackend.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListaValorServiceImpl implements ListaValorService {
    private final ListaValorRepository listaValorRepository;
    private final ListaValorMapper listaValorMapper;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public ListaValorServiceImpl(ListaValorRepository listaValorRepository,
                                 ListaValorMapper listaValorMapper,
                                 SequenceGeneratorService sequenceGeneratorService) {
        this.listaValorRepository = listaValorRepository;
        this.listaValorMapper = listaValorMapper;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public ListaValorDTO crearListaValor(ListaValorDTO dto) {
        ListaValor entity = listaValorMapper.toEntity(dto);
        entity.setCodigo(sequenceGeneratorService.generateSequence(ListaValor.class.getSimpleName()));
        ListaValor saved = listaValorRepository.save(entity);
        return listaValorMapper.toDTO(saved);
    }

    @Override
    public void eliminarTodos() {
        listaValorRepository.deleteAll();
    }

    @Override
    public List<ListaValorDTO> crearListaValores(List<ListaValorDTO> dtos) {
        List<ListaValor> entidades = dtos.stream()
                .map(listaValorMapper::toEntity)
                .collect(Collectors.toList());

        List<ListaValor> guardados = listaValorRepository.saveAll(entidades);

        return guardados.stream()
                .map(listaValorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListaValor> obtenerTiposIdentificacion() {
        return listaValorRepository.findByCategoria("TIPO_IDENTIFICACION");
    }
}
