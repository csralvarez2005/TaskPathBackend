package TaskPathBackend.controller;

import TaskPathBackend.dto.PagedResponseDTO;
import TaskPathBackend.dto.UsuarioDTO;
import TaskPathBackend.mapper.PagedResponseMapper;
import TaskPathBackend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


    @GetMapping
    public ResponseEntity<PagedResponseDTO<UsuarioDTO>> listarUsuarios(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<UsuarioDTO> usuariosPage = usuarioService.listarUsuarios(page, size);
        return ResponseEntity.ok(PagedResponseMapper.toDTO(usuariosPage));
    }


    @GetMapping("/{id}")
    public UsuarioDTO obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuario(id);
    }


    @GetMapping("/email/{email}")
    public UsuarioDTO buscarPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/foto")
    public UsuarioDTO subirFoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return usuarioService.subirFoto(id, file);
    }


    @DeleteMapping("/{id}/foto")
    public UsuarioDTO eliminarFoto(@PathVariable Long id) {
        return usuarioService.eliminarFoto(id);
    }


    @PostMapping(value = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<UsuarioDTO> crearUsuarios(@RequestBody List<UsuarioDTO> usuarios) {
        return usuarioService.crearUsuarios(usuarios);
    }


    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public UsuarioDTO actualizarUsuario(
            @PathVariable Long id,
            @RequestPart("usuario") UsuarioDTO usuarioDTO,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return usuarioService.actualizarUsuario(id, usuarioDTO, file);
    }
    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> obtenerFoto(@PathVariable Long id) {
        byte[] foto = usuarioService.obtenerFoto(id);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // o IMAGE_PNG según el archivo
                .body(foto);
    }
}
