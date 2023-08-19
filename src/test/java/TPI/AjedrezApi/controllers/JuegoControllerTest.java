package TPI.AjedrezApi.controllers;

import TPI.AjedrezApi.controllers.JuegoController;
import TPI.AjedrezApi.models.*;
import TPI.AjedrezApi.services.impl.JuegoServiceImpl;
import TPI.AjedrezApi.services.impl.JugadorServiceImpl;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class JuegoControllerTest {

    @MockBean
    private JuegoServiceImpl juegoService;
    @MockBean
    private JugadorServiceImpl jugadorService;

    @Autowired
    private JuegoController juegoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearJuego_ReturnsResponseWithOKStatus() {
        Juego juego = new Juego();

        when(juegoService.createJuego(juego)).thenReturn("Juego creado exitosamente");

        ResponseEntity<Map<String, Object>> response = juegoController.crearJuego(juego);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("mensajeJuego"));
        assertEquals("Juego creado exitosamente", response.getBody().get("mensajeJuego"));

        HttpHeaders headers = response.getHeaders();
        assertNotNull(headers);
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType());
    }

    @Test
    void testVerTableroHTML_ReturnsResponseWithOKStatus() {
        String tableroHTML = "<html><body>Tablero HTML</body></html>";
        EstadoJuego estadoJuego = EstadoJuego.EN_CURSO;
        String mensajeEstado = "Mensaje de estado";

        when(juegoService.verTablero()).thenReturn(tableroHTML);
        when(juegoService.getEstadoJuego()).thenReturn(estadoJuego);
        when(juegoService.verificarEstadoDelJuego()).thenReturn(mensajeEstado);

        ResponseEntity<Map<String, Object>> response = juegoController.verTableroHTML();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("tableroHTML"));
        assertTrue(response.getBody().containsKey("estadoJuego"));
        assertTrue(response.getBody().containsKey("mensajeEstado"));
        assertEquals(tableroHTML, response.getBody().get("tableroHTML"));
        assertEquals(estadoJuego.toString(), response.getBody().get("estadoJuego"));
        assertEquals(mensajeEstado, response.getBody().get("mensajeEstado"));

        HttpHeaders headers = response.getHeaders();
        assertNotNull(headers);
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType());
    }


    @Test
    void testGetTurno_ReturnsResponseWithOKStatus() {
        Jugador jugador = new Jugador();
        jugador.setNombre("John Doe");
        jugador.setColor(Color.BLANCAS);

        when(juegoService.getJugadorActual()).thenReturn(jugador);

        ResponseEntity<String> response = juegoController.getTurno();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Turno del jugador John Doe, BLANCAS", response.getBody());

        HttpHeaders headers = response.getHeaders();
        assertNotNull(headers);
        assertEquals(MediaType.TEXT_HTML, headers.getContentType());
    }

    @Test
    void testMoverPiezaHTML_ThrowsMethodNotAllowedException() {
        MovimientoPieza movimientoPieza = new MovimientoPieza();
        movimientoPieza.setPosicion("A1");
        movimientoPieza.setMoverHacia("A2");
        movimientoPieza.setTipoPieza("Peón");

        when(juegoService.getEstadoJuego()).thenReturn(EstadoJuego.TERMINADO);

        assertThrows(ResponseStatusException.class, () -> juegoController.moverPiezaHTML(movimientoPieza));
        verify(juegoService, times(1)).getEstadoJuego();
        verifyNoMoreInteractions(juegoService);
    }

    @Test
    void testGetJuego_ReturnsResponseWithOKStatus() {
        Juego juego = new Juego();

        when(juegoService.getJuego()).thenReturn(juego);

        ResponseEntity<Juego> response = juegoController.getJuego();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(juego, response.getBody());
    }

    @Test
    void testGetHistorialMovimientos_ReturnsResponseWithOKStatus() {
        List<String> historialMovimientos = Arrays.asList("A1-A2", "B1-B2", "C1-C2");

        when(juegoService.getHistorialMovimientos()).thenReturn(historialMovimientos);

        ResponseEntity<List<String>> response = juegoController.getHistorialMovimientos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(historialMovimientos, response.getBody());
    }
}

