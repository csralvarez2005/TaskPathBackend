package TaskPathBackend.service.impl;

import TaskPathBackend.dto.UsuarioDTO;
import TaskPathBackend.entity.Cargo;
import TaskPathBackend.entity.ListaValor;
import TaskPathBackend.entity.Programa;
import TaskPathBackend.entity.Usuario;
import TaskPathBackend.exception.UsuarioAlreadyExistsException;
import TaskPathBackend.mapper.UsuarioMapper;
import TaskPathBackend.repository.CargoRepository;
import TaskPathBackend.repository.ListaValorRepository;

import TaskPathBackend.repository.ProgramaRepository;
import TaskPathBackend.repository.UsuarioRepository;
import TaskPathBackend.service.CloudinaryService;
import TaskPathBackend.service.UsuarioService;
import TaskPathBackend.service.SequenceGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SequenceGeneratorService sequenceGenerator;
    private final UsuarioMapper usuarioMapper;
    private final CloudinaryService cloudinaryService;
    private final CargoRepository cargoRepository;
    private final ListaValorRepository listaValorRepository;
    private final ProgramaRepository programaRepository;


    @Autowired
    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            SequenceGeneratorService sequenceGenerator,
            UsuarioMapper usuarioMapper,
            CloudinaryService cloudinaryService,
            CargoRepository cargoRepository,
            ListaValorRepository listaValorRepository,
            ProgramaRepository programaRepository 
    ) {
        this.usuarioRepository = usuarioRepository;
        this.sequenceGenerator = sequenceGenerator;
        this.usuarioMapper = usuarioMapper;
        this.cloudinaryService = cloudinaryService;
        this.cargoRepository = cargoRepository;
        this.listaValorRepository = listaValorRepository;
        this.programaRepository = programaRepository;
      
    }

    @Override
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO, MultipartFile file) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);

        // 🔹 Validar identificación única
        usuarioRepository.findByIdentificacion(usuario.getIdentificacion())
                .ifPresent(u -> {
                    throw new UsuarioAlreadyExistsException(usuario.getIdentificacion());
                });

        // 🔹 Generar secuencia si no viene ID
        if (usuario.getId() == null) {
            usuario.setId(sequenceGenerator.generateSequence("usuarios_sequence"));
        }

        // 🔹 Estado por defecto
        usuario.setEstado("ACTIVO");

        // 🔹 Fecha de creación automática
        if (usuario.getFechaCreacion() == null) {
            usuario.setFechaCreacion(new Date());
        }

        // 🔹 Cargo (si no viene, asignar por defecto 10007L)
        Cargo cargo = cargoRepository.findById(
                usuarioDTO.getIdCargo() != null ? usuarioDTO.getIdCargo() : 10007L
        ).orElseThrow(() -> new RuntimeException("Cargo no encontrado"));
        usuario.setCargo(cargo);

        // 🔹 ListaValor: Tipo Identificación
        if (usuarioDTO.getIdTipoIdentificacion() != null) {
            ListaValor tipoIdentificacion = listaValorRepository.findById(usuarioDTO.getIdTipoIdentificacion())
                    .orElseThrow(() -> new RuntimeException(
                            "Tipo de identificación no encontrado con id: " + usuarioDTO.getIdTipoIdentificacion()
                    ));
            usuario.setTipoIdentificacion(tipoIdentificacion);
        }

        // 🔹 ListaValor: Género
        if (usuarioDTO.getIdGenero() != null) {
            ListaValor genero = listaValorRepository.findById(usuarioDTO.getIdGenero())
                    .orElseThrow(() -> new RuntimeException(
                            "Género no encontrado con id: " + usuarioDTO.getIdGenero()
                    ));
            usuario.setGenero(genero);
        }

        // 🔹 ListaValor: Estado Civil
        if (usuarioDTO.getIdEstadoCivil() != null) {
            ListaValor estadoCivil = listaValorRepository.findById(usuarioDTO.getIdEstadoCivil())
                    .orElseThrow(() -> new RuntimeException(
                            "Estado civil no encontrado con id: " + usuarioDTO.getIdEstadoCivil()
                    ));
            usuario.setEstadoCivil(estadoCivil);
        }

        // 🔹 Username y password = identificación
        usuario.setUsername(usuario.getIdentificacion());
        usuario.setPassword(usuario.getIdentificacion());

        // 🔹 Manejo de foto (opcional con Cloudinary)
        if (file != null && !file.isEmpty()) {
            try {
                validarImagen(file);
                Map<String, Object> result = cloudinaryService.uploadFile(file);
                usuario.setIdFoto((String) result.get("public_id"));
                usuario.setNombreArchivoFoto((String) result.get("secure_url"));
                log.info("Imagen subida correctamente a Cloudinary. Public ID: {}", usuario.getIdFoto());
            } catch (Exception e) {
                log.error("Error al subir imagen a Cloudinary: {}", e.getMessage(), e);
                usuario.setIdFoto(null);
                usuario.setNombreArchivoFoto(null);
            }
        }
        if (usuarioDTO.getProgramasIds() != null && !usuarioDTO.getProgramasIds().isEmpty()) {
            List<Programa> programas = programaRepository.findAllById(usuarioDTO.getProgramasIds());
            usuario.setProgramas(programas);
        }

        // 🔹 Guardar usuario
        Usuario saved = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(saved);
    }

    @Override
    public Page<UsuarioDTO> listarUsuarios(int page, int size) {
        Pageable pageable = PageRequest.of(
                page < 0 ? 0 : page,
                size <= 0 ? 10 : size,
                Sort.by("id").descending()
        );
        Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);
        return usuariosPage.map(usuarioMapper::toDTO);
    }

    @Override
    public UsuarioDTO buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuarioMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }

    @Override
    public UsuarioDTO obtenerUsuario(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioDTO subirFoto(Long id, MultipartFile file) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        validarImagen(file);

        try {
            // Si ya tiene foto, eliminar la anterior
            if (usuario.getIdFoto() != null) {
                cloudinaryService.deleteFile(usuario.getIdFoto());
            }

            Map<String, Object> result = cloudinaryService.uploadFile(file);
            usuario.setIdFoto((String) result.get("public_id"));
            usuario.setNombreArchivoFoto((String) result.get("secure_url"));

            Usuario actualizado = usuarioRepository.save(usuario);
            return usuarioMapper.toDTO(actualizado);

        } catch (Exception e) {
            log.error("Error al subir nueva imagen para usuario {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al subir imagen", e);
        }
    }

    @Override
    public UsuarioDTO eliminarFoto(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        if (usuario.getIdFoto() != null) {
            try {
                cloudinaryService.deleteFile(usuario.getIdFoto());
                usuario.setIdFoto(null);
                usuario.setNombreArchivoFoto(null);

                Usuario actualizado = usuarioRepository.save(usuario);
                return usuarioMapper.toDTO(actualizado);
            } catch (Exception e) {
                log.error("Error al eliminar imagen del usuario {}: {}", id, e.getMessage(), e);
                throw new RuntimeException("Error al eliminar imagen", e);
            }
        } else {
            throw new RuntimeException("El usuario no tiene una foto asociada");
        }
    }

    @Override
    public List<UsuarioDTO> crearUsuarios(List<UsuarioDTO> usuarios) {
        return usuarios.stream()
                .map(usuario -> crearUsuario(usuario, null)) // reusamos tu método individual
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO, MultipartFile file) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuarioMapper.updateEntityFromDto(usuarioDTO, usuario);

        if (file != null && !file.isEmpty()) {
            try {
                validarImagen(file);

                // Si ya tenía foto, la eliminamos
                if (usuario.getIdFoto() != null) {
                    cloudinaryService.deleteFile(usuario.getIdFoto());
                }

                Map<String, Object> result = cloudinaryService.uploadFile(file);
                usuario.setIdFoto((String) result.get("public_id"));
                usuario.setNombreArchivoFoto((String) result.get("secure_url"));

            } catch (Exception e) {
                log.error("Error al actualizar la foto del usuario {}: {}", id, e.getMessage(), e);
            }
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuarioActualizado);
    }

    @Override
    public byte[] obtenerFoto(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        if (usuario.getNombreArchivoFoto() == null) {
            throw new RuntimeException("El usuario no tiene foto asociada");
        }

        try (InputStream inputStream = new URL(usuario.getNombreArchivoFoto()).openStream()) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error al obtener la foto desde Cloudinary", e);
        }
    }

    /**
     * Validar tipo y tamaño de archivo
     */
    private void validarImagen(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("El archivo está vacío");
        }

        String contentType = file.getContentType();
        if (contentType == null ||
                !(contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("image/gif"))) {
            throw new RuntimeException("Formato de archivo no válido. Solo se permiten JPG, PNG o GIF");
        }

        long maxSize = 5 * 1024 * 1024; // 5 MB
        if (file.getSize() > maxSize) {
            throw new RuntimeException("El archivo excede el tamaño máximo permitido (5 MB)");
        }
    }
}