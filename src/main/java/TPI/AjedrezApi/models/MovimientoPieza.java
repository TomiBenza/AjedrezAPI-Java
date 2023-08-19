package TPI.AjedrezApi.models;


import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Clase que actuará como Json para gestionar el movimiento de una pieza
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovimientoPieza {
    @NotNull(message = "Ingresar tipo de pieza")
    private String tipoPieza;
    @NotNull(message = "La posicion a donde se encuentra no puede ser nula")
    private String posicion;
    @NotNull(message = "La posicion a donde se moverá no puede ser nula")
    private String moverHacia;

}
