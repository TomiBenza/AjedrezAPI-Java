package TPI.AjedrezApi.services;

import TPI.AjedrezApi.models.Jugador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JugadorService {
    Jugador getJugadorById(Long id);
    List<Jugador> getJugadores();
    Jugador createJugador(Jugador jugador);
    Jugador updateJugadoresJuego(Jugador jugador,boolean esGanador,boolean huboEmpate);
}
