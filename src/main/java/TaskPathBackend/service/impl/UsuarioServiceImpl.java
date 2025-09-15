package TaskPathBackend.service.impl;

import TaskPathBackend.dto.UsuarioDTO;
import TaskPathBackend.entity.Usuario;
import TaskPathBackend.exception.UsuarioAlreadyExistsException;
import TaskPathBackend.mapper.UsuarioMapper;
import TaskPathBackend.repository.UsuarioRepository;
import TaskPathBackend.service.CloudinaryService;
import TaskPathBackend.service.UsuarioService;
import TaskPathBackend.service.SequenceGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SequenceGeneratorService sequenceGenerator;
    private final UsuarioMapper usuarioMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            SequenceGeneratorService sequenceGenerator,
            UsuarioMapper usuarioMapper,
            CloudinaryService cloudinaryService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.sequenceGenerator = sequenceGenerator;
        this.usuarioMapper = usuarioMapper;
        this.cloudinaryService = cloudinaryService;
    }

    /**
     * Crear usuario con posibilidad de subir imagen opcional
     */
    @Override
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO, MultipartFile file) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);

        // Validar identificación única
        usuarioRepository.findByIdentificacion(usuario.getIdentificacion())
                .ifPresent(u -> {
                    throw new UsuarioAlreadyExistsException(usuario.getIdentificacion());
                });

        // Generar secuencia si no viene ID
        if (usuario.getId() == null) {
            usuario.setId(sequenceGenerator.generateSequence("usuarios_sequence"));
        }

        // Manejo de foto (opcional)
        if (file != null && !file.isEmpty()) {
            try {
                validarImagen(file);
                Map<String, Object> result = cloudinaryService.uploadFile(file);
                usuario.setIdFoto((String) result.get("public_id"));
                usuario.setNombreArchivoFoto((String) result.get("secure_url"));
                log.info("✅ Imagen subida correctamente a Cloudinary. Public ID: {}", usuario.getIdFoto());
            } catch (Exception e) {
                log.error("⚠️ Error al subir imagen a Cloudinary: {}", e.getMessage(), e);
                // Se guarda el usuario sin imagen
                usuario.setIdFoto(null);
                usuario.setNombreArchivoFoto(null);
            }
        }

        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(saved);
    }

    @Override
    public Page<UsuarioDTO> listarUsuarios(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return usuarioRepository.findAll(pageable)
                .map(usuarioMapper::toDTO);
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
            log.error("⚠️ Error al subir nueva imagen para usuario {}: {}", id, e.getMessage(), e);
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
                log.error("⚠️ Error al eliminar imagen del usuario {}: {}", id, e.getMessage(), e);
                throw new RuntimeException("Error al eliminar imagen", e);
            }
        } else {
            throw new RuntimeException("El usuario no tiene una foto asociada");
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