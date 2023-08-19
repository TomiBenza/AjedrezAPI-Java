package TPI.AjedrezApi.services.impl;

import TPI.AjedrezApi.models.*;
import TPI.AjedrezApi.models.Piezas.Pieza;
import TPI.AjedrezApi.services.JuegoService;
import TPI.AjedrezApi.services.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class JuegoServiceImpl implements JuegoService {

    @Autowired
    private JugadorService jugadorService;
    private Juego juego;
    private EstadoJuego estadoJuego;
    private boolean datosCargados;

    /////////////////////////////////////////////////////////////

    public EstadoJuego getEstadoJuego(){
        return juego.checkEstadoJuego();
    }
    public String verificarEstadoDelJuego(){
        estadoJuego = getEstadoJuego();

        switch (estadoJuego){
            case EMPATE -> {
                if(!datosCargados){
                    datosCargados=true;
                    jugadorService.updateJugadoresJuego(juego.getJugadorBlancas(),false,true);
                    jugadorService.updateJugadoresJuego(juego.getJugadorNegras(),false,true);
                }
                return "¡EMPATE! No han habido ganadores (1 punto para cada jugador)";
            }
            case TERMINADO -> {
                if(!datosCargados) {
                    datosCargados=true;
                    jugadorService.updateJugadoresJuego(juego.obtenerGanador(), true, false);
                    jugadorService.updateJugadoresJuego(juego.obtenerPerdedor(), false, false);
                }
                return "Jaque Mate. El ganador es "+juego.obtenerGanador().getNombre()+" (+3 puntos)";
            }
            case JAQUE -> {
                Jugador jugadorEnJaque = juego.obtenerJugadorEnJaque();
                return "El Rey del jugador "+jugadorEnJaque.getNombre()+" está en Jaque, evita el jaque o perderás la partida";
            }
            default-> {
                return "";
            }
        }
    }
    @Override
    public String verTablero() {
        return juego.verTablero();
    }
    public String verificarMovimiento(String tipoPieza, Posicion desde, Posicion moverHacia){
        if(estadoJuego==EstadoJuego.EMPATE || estadoJuego==EstadoJuego.TERMINADO)
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,"El juego ha terminado");

        piezaValida(tipoPieza,desde);
        piezaPropia(desde);
        moverPieza(desde,moverHacia);
        pasarTurno();
        return tipoPieza+" se movió con éxito";
    }
    @Override
    public boolean moverPieza(Posicion desde,Posicion moverHacia) {
        Pieza piezaTablero = juego.getTablero().getPosPieza(desde);
        Movimiento movimiento = new Movimiento(desde,moverHacia);
        if(!juego.getTablero().moverPieza(piezaTablero, movimiento))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El movimiento solicitado no es válido para la pieza seleccionada");

        return true;
    }
    @Override
    public Jugador getJugadorActual() {
        return juego.getJugadorActual();
    }
    @Override
    public boolean pasarTurno() {
        try{
            return juego.cambiarTurno();
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Ocurrió un error al cambiar el turno...");
        }
    }
    @Override
    public boolean piezaValida(String tipoPieza, Posicion inicio) {
        if(!inputValido(tipoPieza)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La pieza no es válida");
        }

        Pieza piezaTablero = juego.getTablero().getPosPieza(inicio);
        if(piezaTablero.getTipo()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No existe una pieza en esa posición");
        }
        if(!piezaTablero.getTipo().name().equalsIgnoreCase(tipoPieza)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La pieza seleccionada no existe en la posición especificada");
        }
        return true;
    }

    public boolean inputValido(String tipoPieza){
        try {
            Tipo tipo = Tipo.valueOf(tipoPieza);

            // Verificar si el tipoPieza es igual a algún objeto de la enumeración
            return tipo == Tipo.ALFIL || tipo == Tipo.CABALLO || tipo == Tipo.TORRE ||
                    tipo == Tipo.REY || tipo == Tipo.PEON || tipo == Tipo.REINA;
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La pieza no es válida");
        }
    }

    @Override
    public boolean piezaPropia(Posicion posicionActual) {
        Pieza piezaTablero = juego.getTablero().getPosPieza(posicionActual);
        if(piezaTablero.getColor()!=getJugadorActual().getColor()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No está permitido mover piezas de tu rival");
        }
        return true;
    }

    public Juego getJuego(){
        return this.juego;
    }
    public String getJuegoInfo(){
        return this.juego.toString();
    }
    @Override
    public String createJuego(Juego juego) {
        datosCargados=false;
        if(Objects.equals(juego.getId_jugador_blancas(), juego.getId_jugador_negras())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Un mismo jugador no puede competir contra si mismo");
        }
        Jugador jugador1=jugadorService.getJugadorById(juego.getId_jugador_blancas());
        Jugador jugador2=jugadorService.getJugadorById(juego.getId_jugador_negras());
        this.juego=new Juego(jugador1,jugador2, LocalDateTime.now()
        );
        return this.juego.toString();

    }
    public boolean existeJuego(){
        if(juego==null){
            return false;
        }
        return juego.getTablero().getEstadoJuego() != EstadoJuego.TERMINADO && juego.getTablero().getEstadoJuego() != EstadoJuego.EMPATE;
    }


    @Override
    public List<String> getHistorialMovimientos() {
        return this.juego.obtenerHistorialMovimientos();
    }

}
