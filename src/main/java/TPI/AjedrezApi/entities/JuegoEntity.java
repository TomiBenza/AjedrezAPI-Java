package TPI.AjedrezApi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "juegos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JuegoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime fecha_creacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador_b")
    private JugadorEntity jugador_blancas;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador_n")
    private JugadorEntity jugador_negras;

    @Column
    private Boolean terminado;





}
