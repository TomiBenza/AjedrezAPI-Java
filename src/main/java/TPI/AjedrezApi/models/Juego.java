package TPI.AjedrezApi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Juego {
    @JsonIgnore
    private Long id;
    @NotNull(message = "Ingresar el id del jugador para el equipo blancas")
    private Long id_jugador_blancas;
    @NotNull(message = "Ingresar el id del jugador para el equipo negras")
    private Long id_jugador_negras;

    @JsonIgnore
    private LocalDateTime horaCreacion;
    @JsonIgnore
    private Jugador jugadorBlancas;
    @JsonIgnore
    private Jugador jugadorNegras;
    @JsonIgnore
    private Jugador jugadorActual;
    @JsonIgnore
    private Jugador jugadorGanador;
    @JsonIgnore
    private Tablero tablero;

    public Juego(Jugador jBlancas, Jugador jNegras, LocalDateTime horaCreacion){
        tablero=new Tablero();

        this.horaCreacion =horaCreacion;

        jNegras.setColor(Color.NEGRAS);
        jugadorNegras = jNegras;
        id_jugador_negras=jNegras.getId();
        jBlancas.setColor(Color.BLANCAS);
        jugadorBlancas = jBlancas;
        id_jugador_blancas=jBlancas.getId();
        jugadorActual = jugadorBlancas;

    }

    public List<String> obtenerHistorialMovimientos() {
        return tablero.getHistorialMovimientos();
    }
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM '|' HH:mm");
        String formattedHoraCreacion = horaCreacion.format(formatter);
        return formattedHoraCreacion +" |\n"+ jugadorBlancas.getNombre()+" (Blancas) vs " + jugadorNegras.getNombre()+" (Negras)";
    }


    public String verTablero() {
        return tablero.verTablero();
    }

    /**
     * Gestiona los turnos de los jugadores, siempre que el juego no haya finalizado
     *
     */
    public boolean cambiarTurno(){
        boolean cambio = false;
        if(tablero.getEstadoJuego()  == EstadoJuego.EN_CURSO || tablero.getEstadoJuego() == EstadoJuego.JAQUE){
            cambio = true;
            if(jugadorActual == jugadorBlancas) jugadorActual= jugadorNegras;
            else jugadorActual= jugadorBlancas;
        }
        return cambio;
    }

    public EstadoJuego checkEstadoJuego(){
        return tablero.getEstadoJuego();
    }
    public Jugador obtenerGanador(){
        Color colorGanador = tablero.getColorGanador();
        if(colorGanador==null){
            return null;
        }
        if(colorGanador==jugadorBlancas.getColor()){
            return jugadorBlancas;
        }
        else return jugadorNegras;
    }
    public Jugador obtenerPerdedor(){
        if(tablero.getColorGanador()!=jugadorBlancas.getColor()){
            return jugadorBlancas;
        }
        else return jugadorNegras;
    }

    public Jugador obtenerJugadorEnJaque() {
        if(tablero.getColorEnJaque()==jugadorBlancas.getColor()) return jugadorBlancas;
        else return jugadorNegras;
    }


}
