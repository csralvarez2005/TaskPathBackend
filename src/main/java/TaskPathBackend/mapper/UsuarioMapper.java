package TaskPathBackend.mapper;

import TaskPathBackend.dto.UsuarioDTO;
import TaskPathBackend.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    // ====== ENTITY → DTO ======
    @Mapping(source = "cargo.id", target = "idCargo")
    @Mapping(source = "cargo.nombreCargo", target = "nombreCargo")
    @Mapping(source = "estadoCivil.codigo", target = "idEstadoCivil")
    @Mapping(source = "estadoCivil.valor", target = "estadoCivil")
    @Mapping(source = "genero.codigo", target = "idGenero")
    @Mapping(source = "genero.valor", target = "genero")
    @Mapping(source = "tipoIdentificacion.codigo", target = "idTipoIdentificacion")
    @Mapping(source = "tipoIdentificacion.valor", target = "tipoIdentificacion")
    @Mapping(
            target = "programasIds",
            expression = "java(usuario.getProgramas() != null ? usuario.getProgramas().stream().map(TaskPathBackend.entity.Programa::getId).collect(java.util.stream.Collectors.toList()) : null)"
    )
    @Mapping(
            target = "programasNombres",
            expression = "java(usuario.getProgramas() != null ? usuario.getProgramas().stream().map(TaskPathBackend.entity.Programa::getNombre).collect(java.util.stream.Collectors.toList()) : null)"
    )
    UsuarioDTO toDTO(Usuario usuario);

    // ====== DTO → ENTITY ======
    @Mapping(source = "idCargo", target = "cargo.id")
    @Mapping(source = "idEstadoCivil", target = "estadoCivil.codigo")
    @Mapping(source = "idGenero", target = "genero.codigo")
    @Mapping(source = "idTipoIdentificacion", target = "tipoIdentificacion.codigo")
    @Mapping(target = "programas", ignore = true) // se resuelve en UsuarioServiceImpl
    Usuario toEntity(UsuarioDTO dto);

    // ====== LISTAS ======
    List<UsuarioDTO> toDTOList(List<Usuario> usuarios);

    void updateEntityFromDto(UsuarioDTO dto, @MappingTarget Usuario entity);

    // ========= Métodos auxiliares =========
    default ListaValor map(Long id) {
        if (id == null) return null;
        ListaValor lv = new ListaValor();
        lv.setCodigo(id);
        return lv;
    }

    default Long map(ListaValor lv) {
        return lv != null ? lv.getCodigo() : null;
    }

    default Long map(Programa programa) {
        return programa != null ? programa.getId() : null;
    }

    default Programa mapPrograma(Long id) {
        if (id == null) return null;
        Programa programa = new Programa();
        programa.setId(id);
        return programa;
    }

    default List<Long> mapProgramas(List<Programa> programas) {
        return programas != null
                ? programas.stream().map(Programa::getId).collect(Collectors.toList())
                : null;
    }

    default List<Programa> mapIds(List<Long> ids) {
        return ids != null
                ? ids.stream().map(this::mapPrograma).collect(Collectors.toList())
                : null;
    }
}
