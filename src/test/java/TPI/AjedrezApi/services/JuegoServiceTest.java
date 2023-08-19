package TPI.AjedrezApi.services;

import TPI.AjedrezApi.services.impl.JuegoServiceImpl;
import TPI.AjedrezApi.services.impl.JugadorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import TPI.AjedrezApi.models.*;
import TPI.AjedrezApi.models.Piezas.Pieza;
import TPI.AjedrezApi.services.JuegoService;
import TPI.AjedrezApi.services.JugadorService;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class JuegoServiceTest {

    @Mock
    private JugadorService jugadorService;

    @InjectMocks
    private JuegoServiceImpl juegoService;

    private Juego juego;

    @Mock
    private Juego juegoMock;
    private Jugador jugadorBlancas;
    private Jugador jugadorNegras;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        jugadorBlancas = new Jugador(1L, "Jugador1", 0, 0, 0, Color.BLANCAS);
        jugadorNegras = new Jugador(2L, "Jugador2", 0, 0, 0, Color.NEGRAS);

        juego = new Juego(jugadorBlancas, jugadorNegras, LocalDateTime.now());
        when(jugadorService.getJugadorById(1L))
                .thenReturn(new Jugador(1L, "Jugador1", 0, 0, 0, Color.BLANCAS));
        when(jugadorService.getJugadorById(2L))
                .thenReturn(new Jugador(2L, "Jugador2", 0, 0, 0, Color.NEGRAS));

        juegoService.createJuego(juego);
    }

    @Test
    public void testGetEstadoJuego() {
        EstadoJuego estadoJuego = juegoService.getEstadoJuego();
        assertNotNull(estadoJuego);
    }

    @Test
    public void testVerificarEstadoDelJuego() {
        String resultado = juegoService.verificarEstadoDelJuego();
        assertNotNull(resultado);
    }

    @Test
    public void testVerTablero() {
        String tablero = juegoService.verTablero();
        assertNotNull(tablero);
    }

    @Test
    public void testVerificarMovimiento_Valido() {
        String tipoPieza = "PEON";
        Posicion desde = new Posicion(1, 2);
        Posicion moverHacia = new Posicion(2, 2);

        assertDoesNotThrow(() -> juegoService.verificarMovimiento(tipoPieza, desde, moverHacia));
    }

    @Test
    public void testVerificarMovimiento_PiezaNoValida() {
        String tipoPieza = "PIEZA_INVALIDA";
        Posicion desde = new Posicion(1, 1);
        Posicion moverHacia = new Posicion(3, 3);

        assertThrows(ResponseStatusException.class, () -> juegoService.verificarMovimiento(tipoPieza, desde, moverHacia));
    }

    @Test
    public void testMoverPieza_MovimientoValido() {
        Posicion desde = new Posicion(1, 1);
        Posicion moverHacia = new Posicion(2, 1);

        boolean resultado = juegoService.moverPieza(desde, moverHacia);
        assertTrue(resultado);
    }

    @Test
    public void testMoverPieza_MovimientoInvalido() {
        Posicion desde = new Posicion(1, 1);
        Posicion moverHacia = new Posicion(4, 4);

        assertThrows(ResponseStatusException.class, () -> juegoService.moverPieza(desde, moverHacia));
    }

    @Test
    public void testGetJugadorActual() {
        Jugador jugadorActual = juegoService.getJugadorActual();
        assertNotNull(jugadorActual);
    }

    @Test
    public void testPasarTurno() {
        boolean resultado = juegoService.pasarTurno();
        assertTrue(resultado);
    }

    @Test
    public void testPiezaValida_PiezaValida1() {
        String tipoPieza = "PEON";
        Posicion inicio = new Posicion(1, 1);

        boolean resultado = juegoService.piezaValida(tipoPieza, inicio);
        assertTrue(resultado);
    }
    @Test
    public void testPiezaValida_PiezaValida2() {
        String tipoPieza = "TORRE";
        Posicion inicio = new Posicion(0, 0);

        boolean resultado = juegoService.piezaValida(tipoPieza, inicio);
        assertTrue(resultado);
    }
    @Test
    public void testPiezaValida_PiezaValida3() {
        String tipoPieza = "ALFIL";
        Posicion inicio = new Posicion(0, 2);

        boolean resultado = juegoService.piezaValida(tipoPieza, inicio);
        assertTrue(resultado);
    }
    @Test
    public void testPiezaValida_PiezaValida4() {
        String tipoPieza = "REY";
        Posicion inicio = new Posicion(0, 4);

        boolean resultado = juegoService.piezaValida(tipoPieza, inicio);
        assertTrue(resultado);
    }
    @Test
    public void testPiezaValida_PiezaValida5() {
        String tipoPieza = "REINA";
        Posicion inicio = new Posicion(0, 3);

        boolean resultado = juegoService.piezaValida(tipoPieza, inicio);
        assertTrue(resultado);
    }
    @Test
    public void testPiezaValida_PiezaValida6() {
        String tipoPieza = "CABALLO";
        Posicion inicio = new Posicion(0, 1);

        boolean resultado = juegoService.piezaValida(tipoPieza, inicio);
        assertTrue(resultado);
    }

    @Test
    public void testPiezaValida_PiezaNoValida() {
        String tipoPieza = "PIEZA_INVALIDA";
        Posicion inicio = new Posicion(1, 1);

        assertThrows(ResponseStatusException.class, () -> juegoService.piezaValida(tipoPieza, inicio));
    }

    @Test
    public void testPiezaPropia_PiezaPropia() {
        Posicion posicionActual = new Posicion(1, 1);

        boolean resultado = juegoService.piezaPropia(posicionActual);
        assertTrue(resultado);
    }


    @Test
    public void testCreateJuego_Valido() {
        Jugador jugador1 = new Jugador(1L, "Jugador1", 0, 0, 0, Color.BLANCAS);
        Jugador jugador2 = new Jugador(2L, "Jugador2", 0, 0, 0, Color.NEGRAS);

        Juego juego = new Juego(jugador1, jugador2, LocalDateTime.now());

        String resultado = juegoService.createJuego(juego);
        assertNotNull(resultado);
    }

    @Test
    public void testCreateJuego_MismoJugador() {
        Jugador jugador1 = new Jugador(1L, "Jugador1", 0, 0, 0, Color.BLANCAS);

        Juego juego = new Juego(jugador1, jugador1, LocalDateTime.now());

        assertThrows(ResponseStatusException.class, () -> juegoService.createJuego(juego));
    }

    @Test
    public void testGetHistorialMovimientos() {
        List<String> historialMovimientos = juegoService.getHistorialMovimientos();
        assertNotNull(historialMovimientos);
    }
    @Test
    public void testInputValido_PiezaValida() {
        String tipoPieza = "ALFIL";
        boolean resultadoEsperado = true;

        boolean resultado = juegoService.inputValido(tipoPieza);

        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void testExisteJuegoFalso(){
        boolean res = juegoService.existeJuego();
        assertTrue(res);
    }
}
