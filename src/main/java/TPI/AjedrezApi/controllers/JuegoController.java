package TPI.AjedrezApi.controllers;

import TPI.AjedrezApi.models.EstadoJuego;
import TPI.AjedrezApi.models.Juego;
import TPI.AjedrezApi.models.Jugador;
import TPI.AjedrezApi.models.MovimientoPieza;
import TPI.AjedrezApi.models.Conversor;
import TPI.AjedrezApi.models.Posicion;
import TPI.AjedrezApi.services.impl.JuegoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/juego")
@CrossOrigin(origins = {"*"})
public class JuegoController {

    // http://localhost:8080/doc/swagger-ui/index.html#/
    Conversor conversor = new Conversor();
    @Autowired
    JuegoServiceImpl juegoService;

    @PostMapping("/crearJuego")
    @Validated
    public ResponseEntity<Map<String, Object>> crearJuego(@RequestBody @Valid Juego juego){
        String mensajeCreacion = juegoService.createJuego(juego);
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensajeJuego", mensajeCreacion);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(respuesta, headers, HttpStatus.OK);
    }

    @GetMapping("/existeJuegoEnCurso")
    public ResponseEntity<Map<Object, Object>> existeJuegoEnCurso(){
        Map<Object, Object> respuesta = new HashMap<>();
        respuesta.put("existeJuego", juegoService.existeJuego());
        if(juegoService.existeJuego()){
            respuesta.put("infoJuego", juegoService.getJuegoInfo());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(respuesta, headers, HttpStatus.OK);
    }

    @GetMapping("/ver-tablero/html")
    public ResponseEntity<Map<String, Object>> verTableroHTML(){
        String tableroHTML = juegoService.verTablero();
        EstadoJuego estadoJuego = juegoService.getEstadoJuego();


        String mensajeEstado = juegoService.verificarEstadoDelJuego();

        // HashMap que se convertirá en Json para enviar las respuestas
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("tableroHTML", tableroHTML);
        respuesta.put("estadoJuego", estadoJuego.toString());
        respuesta.put("mensajeEstado", mensajeEstado);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(respuesta, headers, HttpStatus.OK);
    }
    @GetMapping("/getTurno")
    public ResponseEntity<String> getTurno(){
        Jugador jugador = juegoService.getJugadorActual();
        String respuesta = "Turno del jugador "+jugador.getNombre()+", "+jugador.getColor();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(respuesta, headers, HttpStatus.OK);
    }


    @PostMapping("/movimiento")
    @Validated
    public ResponseEntity<Map<String, Object>> moverPiezaHTML(@RequestBody @Valid MovimientoPieza movimientoPieza){
        Posicion desde = conversor.convertirStringAPosicion(movimientoPieza.getPosicion());
        Posicion hacia = conversor.convertirStringAPosicion(movimientoPieza.getMoverHacia());
        String tipo = movimientoPieza.getTipoPieza().toUpperCase();

        if(juegoService.getEstadoJuego()==EstadoJuego.TERMINADO || juegoService.getEstadoJuego()==EstadoJuego.EMPATE)
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,"El juego ya ha finalizado");
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("piezaMovida", juegoService.verificarMovimiento(tipo, desde, hacia));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(respuesta, headers, HttpStatus.OK);


    }

    @GetMapping("/getJugadoresJuego")
    public ResponseEntity<Juego> getJuego(){
        return ResponseEntity.ok(juegoService.getJuego());
    }

    @GetMapping("/getHistorialMovimientos")
    @CrossOrigin(origins ={"*"})
    public ResponseEntity<List<String>> getHistorialMovimientos(){
        return ResponseEntity.ok(juegoService.getHistorialMovimientos());
    }



}
