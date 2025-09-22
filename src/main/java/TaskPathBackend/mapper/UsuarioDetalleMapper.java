package TaskPathBackend.mapper;

import TaskPathBackend.dto.UsuarioDetalleDTO;
import TaskPathBackend.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UsuarioDetalleMapper {

    // ====== ENTITY → DTO (GET) ======
    @Mapping(source = "cargo.nombreCargo", target = "cargo")
    @Mapping(source = "estadoCivil.valor", target = "estadoCivil")
    @Mapping(source = "genero.valor", target = "genero")
    @Mapping(source = "tipoIdentificacion.valor", target = "tipoIdentificacion")
    @Mapping(source = "programas", target = "programas")
    UsuarioDetalleDTO toDTO(Usuario usuario);

    List<UsuarioDetalleDTO> toDTOList(List<Usuario> usuarios);

    // Métodos auxiliares para programas
    default List<String> mapProgramas(List<TaskPathBackend.entity.Programa> programas) {
        return programas != null
                ? programas.stream().map(TaskPathBackend.entity.Programa::getNombre).collect(Collectors.toList())
                : null;
    }
}
