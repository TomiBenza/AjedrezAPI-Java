package TPI.AjedrezApi.controllers;

import TPI.AjedrezApi.models.Jugador;
import TPI.AjedrezApi.services.JugadorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class JugadorControllerTest {
    @Mock
    private JugadorService jugadorService;

    @InjectMocks
    private JugadorController jugadorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById_ValidId_ReturnsJugador() {
        Long jugadorId = 1L;
        Jugador jugador = new Jugador();
        jugador.setId(jugadorId);
        jugador.setNombre("Juancito");

        when(jugadorService.getJugadorById(jugadorId)).thenReturn(jugador);

        ResponseEntity<Jugador> response = jugadorController.getById(jugadorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(jugador, response.getBody());
        verify(jugadorService, times(1)).getJugadorById(jugadorId);
    }

    @Test
    void testGetAll_ReturnsListOfJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        Jugador jugador1 = new Jugador();
        jugador1.setId(1L);
        jugador1.setNombre("Juancito");
        jugadores.add(jugador1);

        Jugador jugador2 = new Jugador();
        jugador2.setId(2L);
        jugador2.setNombre("Carlitos");
        jugadores.add(jugador2);

        when(jugadorService.getJugadores()).thenReturn(jugadores);

        List<Jugador> result = jugadorController.getAll();

        assertEquals(jugadores, result);
        verify(jugadorService, times(1)).getJugadores();
    }


    @Test
    void testSavePlayer_ValidJugador_ReturnsCreatedJugador() {
        Jugador jugador = new Jugador();
        jugador.setNombre("Juancito");

        Jugador jugadorCreado = new Jugador();
        jugadorCreado.setId(1L);
        jugadorCreado.setNombre("Juancito");

        when(jugadorService.createJugador(jugador)).thenReturn(jugadorCreado);


        ResponseEntity<Jugador> response = jugadorController.savePlayer(jugador);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(jugadorCreado, response.getBody());
        verify(jugadorService, times(1)).createJugador(jugador);
    }

    @Test
    void testSavePlayer_DuplicateName_ThrowsResponseStatusException() {

        Jugador jugador = new Jugador();
        jugador.setNombre("Juancito");

        when(jugadorService.createJugador(jugador)).thenReturn(null);


        ResponseStatusException exception =
                Assertions.assertThrows(
                        ResponseStatusException.class,
                        () -> jugadorController.savePlayer(jugador)
                );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verify(jugadorService, times(1)).createJugador(jugador);
    }
}

