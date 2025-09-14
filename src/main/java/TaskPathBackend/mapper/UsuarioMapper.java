package TaskPathBackend.mapper;

import TaskPathBackend.dto.UsuarioDTO;
import TaskPathBackend.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
    UsuarioDTO toDTO(Usuario usuario);
    Usuario toEntity(UsuarioDTO dto);
    List<UsuarioDTO> toDTOList(List<Usuario> usuarios);
}
