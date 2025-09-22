package TaskPathBackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDetalleDTO {
    private Long id;
    private String apellidoFuncionario;
    private String nombreFuncionario;
    private String email;
    private String rol;
    private String celular;
    private String direccion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fechaCreacion;
    private String cargo;
    private String estadoCivil;
    private String genero;
    private String tipoIdentificacion;
    private String identificacion;
    private String idFoto;
    private String nombreArchivoFoto;
    private List<String> programas;
}
