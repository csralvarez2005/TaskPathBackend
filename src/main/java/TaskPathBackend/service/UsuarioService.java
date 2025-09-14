package TaskPathBackend.service;

import TaskPathBackend.dto.UsuarioDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO, MultipartFile file);
    List<UsuarioDTO> listarUsuarios();
    UsuarioDTO buscarPorEmail(String email);
    UsuarioDTO obtenerUsuario(Long id);
    void eliminarUsuario(Long id);
    UsuarioDTO subirFoto(Long id, MultipartFile file);
    UsuarioDTO eliminarFoto(Long id);
}
