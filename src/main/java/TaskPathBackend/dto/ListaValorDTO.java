package TaskPathBackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaValorDTO {
    private long codigo;
    private String categoria;
    private String estado;
    private String referencia;
    private String valor;
}
