package TaskPathBackend.service.impl;


import TaskPathBackend.dto.ProgramaDTO;
import TaskPathBackend.entity.Programa;
import TaskPathBackend.mapper.ProgramaMapper;
import TaskPathBackend.repository.ProgramaRepository;
import TaskPathBackend.service.ProgramaService;
import TaskPathBackend.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramaServiceImpl implements ProgramaService {

    private final ProgramaRepository programaRepository;
    private final ProgramaMapper programaMapper;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public ProgramaDTO crearPrograma(ProgramaDTO dto) {
        Programa entity = programaMapper.toEntity(dto);
        if (entity.getId() == null) {
            entity.setId(sequenceGeneratorService.generateSequence(Programa.SEQUENCE_NAME));
        }
        Programa saved = programaRepository.save(entity);
        return programaMapper.toDTO(saved);
    }

    @Override
    public List<ProgramaDTO> listarProgramas() {
        return programaMapper.toDTOList(programaRepository.findAll());
    }

    @Override
    public ProgramaDTO obtenerProgramaPorId(Long id) {
        Programa entity = programaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Programa no encontrado con id " + id));
        return programaMapper.toDTO(entity);
    }

    @Override
    public ProgramaDTO actualizarPrograma(Long id, ProgramaDTO dto) {
        Programa entity = programaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Programa no encontrado con id " + id));

        // Actualizar campos:
        entity.setCodigoPrograma(dto.getCodigoPrograma());
        entity.setEstado(dto.getEstado());
        entity.setIdFacultad(dto.getIdFacultad());
        entity.setIdJornada(dto.getIdJornada());
        entity.setNombre(dto.getNombre());
        entity.setIdNivelFormacion(dto.getIdNivelFormacion());
        entity.setIdUniversidad(dto.getIdUniversidad());
        entity.setTipoPrograma(dto.getTipoPrograma());

        Programa updated = programaRepository.save(entity);
        return programaMapper.toDTO(updated);
    }

    @Override
    public void eliminarPrograma(Long id) {
        programaRepository.deleteById(id);
    }

    @Override
    public List<ProgramaDTO> crearProgramas(List<ProgramaDTO> dtos) {
        List<Programa> programas = dtos.stream()
                .map(programaMapper::toEntity)
                .peek(p -> {
                    if (p.getId() == null) {
                        // 🔹 Aquí corregimos a sequenceGeneratorService
                        p.setId(sequenceGeneratorService.generateSequence(Programa.SEQUENCE_NAME));
                    }
                })
                .collect(Collectors.toList());

        List<Programa> guardados = programaRepository.saveAll(programas);

        return guardados.stream()
                .map(programaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
