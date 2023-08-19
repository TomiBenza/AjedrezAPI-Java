package TPI.AjedrezApi.services;

import TPI.AjedrezApi.models.Juego;

//import TPI.AjedrezApi.proyectLogic.Jugador;
import TPI.AjedrezApi.models.Jugador;
import TPI.AjedrezApi.models.Posicion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JuegoService {
    String verTablero();

    boolean moverPieza(Posicion desde,Posicion moverHacia);

    Jugador getJugadorActual();
    boolean pasarTurno();

    boolean piezaValida(String tipoPieza, Posicion inicio);

    boolean piezaPropia(Posicion posicion);
    String createJuego(Juego juego);

    List<String> getHistorialMovimientos();


}
