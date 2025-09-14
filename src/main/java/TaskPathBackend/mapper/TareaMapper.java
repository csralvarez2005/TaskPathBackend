package TaskPathBackend.mapper;

import TaskPathBackend.dto.TareaDTO;
import TaskPathBackend.entity.Tarea;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TareaMapper {
    TareaMapper INSTANCE = Mappers.getMapper(TareaMapper.class);

    TareaDTO toDto(Tarea tarea);
    Tarea toEntity(TareaDTO dto);

    List<TareaDTO> toDtoList(List<Tarea> tareas);
    List<Tarea> toEntityList(List<TareaDTO> dtos);
}