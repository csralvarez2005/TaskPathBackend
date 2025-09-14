package TaskPathBackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private String password;
    private String celular;
    private String telefono;
    private String direccion;
    private String estado;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    private Long idCargo;
    private Long idEstadoCivil;
    private Long idGenero;
    private Long idTipoIdentificacion;
    private String identificacion;
    private String idFoto;
    private String nombreArchivoFoto;
}
