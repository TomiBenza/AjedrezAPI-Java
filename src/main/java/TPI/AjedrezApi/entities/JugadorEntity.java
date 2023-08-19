package TPI.AjedrezApi.entities;

import TPI.AjedrezApi.models.Juego;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="jugadores")
public class JugadorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_jugador;
    @Column
    private String nombre;
    @Column
    private Integer partidas_ganadas;
    @Column
    private Integer partidas_perdidas;
    @Column
    private Integer puntos;


}
