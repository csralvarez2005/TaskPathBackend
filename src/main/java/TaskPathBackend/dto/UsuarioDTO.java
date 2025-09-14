package TaskPathBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;

    private String nombre;
    private String apellidoFuncionario;
    private String nombreFuncionario;
    private String email;
    private String rol;

    private String celular;
    private String telefono;
    private String direccion;
    private String estado;
    private LocalDateTime fechaNacimiento;

    private Long idCargo;
    private Long idEstadoCivil;
    private Long idGenero;
    private Long idTipoIdentificacion;

    private String identificacion;
    private String idFoto;
    private String nombreArchivoFoto;
}
