package TaskPathBackend.controller;

import TaskPathBackend.dto.UsuarioDTO;
import TaskPathBackend.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping(value = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public UsuarioDTO crearUsuario(
            @RequestPart("usuario") UsuarioDTO usuarioDTO,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return usuarioService.crearUsuario(usuarioDTO, file);
    }

    // Listar todos los usuarios
    @GetMapping
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    // Buscar usuario por ID
    @GetMapping("/{id}")
    public UsuarioDTO obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuario(id);
    }

    // Buscar usuario por email
    @GetMapping("/email/{email}")
    public UsuarioDTO buscarPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email);
    }

    // Eliminar usuario por ID
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }

    // Subir foto de usuario
    @PostMapping("/{id}/foto")
    public UsuarioDTO subirFoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return usuarioService.subirFoto(id, file);
    }

    // Eliminar foto de usuario
    @DeleteMapping("/{id}/foto")
    public UsuarioDTO eliminarFoto(@PathVariable Long id) {
        return usuarioService.eliminarFoto(id);
    }
}
