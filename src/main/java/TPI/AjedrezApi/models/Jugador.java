package TPI.AjedrezApi.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Jugador {

    private Long id;
    @NotNull(message = "Ingresar un nombre")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;
    private Integer puntos;
    private Integer partidas_ganadas;
    private Integer partidas_perdidas;
    private Color color;
    
    public Jugador(String nombre){
        this.nombre=nombre;
        this.puntos=0;
        this.partidas_ganadas=0;
        this.partidas_perdidas=0;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre +
                ", Puntaje: " + puntos +
                ", Partidas Ganadas: " + partidas_ganadas +
                ", Partidas Perdidas: " + partidas_perdidas;
    }
}

