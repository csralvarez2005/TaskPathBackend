package TaskPathBackend.service.impl;

import TaskPathBackend.dto.ListaValorDTO;
import TaskPathBackend.entity.ListaValor;
import TaskPathBackend.mapper.ListaValorMapper;
import TaskPathBackend.repository.ListaValorRepository;
import TaskPathBackend.service.ListaValorService;
import TaskPathBackend.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        // Convertir DTO a Entity
        ListaValor entity = listaValorMapper.toEntity(dto);

        // Generar ID secuencial
        entity.setCodigo(sequenceGeneratorService.generateSequence(ListaValor.class.getSimpleName()));

        // Guardar en Mongo
        ListaValor saved = listaValorRepository.save(entity);

        // Devolver DTO
        return listaValorMapper.toDTO(saved);
    }
}
