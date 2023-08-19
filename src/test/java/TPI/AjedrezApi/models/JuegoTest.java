package TPI.AjedrezApi.models;

import TPI.AjedrezApi.extra.TableroModelForTests;
import TPI.AjedrezApi.models.Piezas.Peon;
import TPI.AjedrezApi.models.Piezas.Pieza;
import TPI.AjedrezApi.models.Piezas.Rey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JuegoTest {

    @Mock
    private Jugador jugadorBlancasMock;

    @Mock
    private Jugador jugadorNegrasMock;


    private Juego juego;

    @BeforeEach
    void setUp() {
        jugadorBlancasMock = mock(Jugador.class);
        jugadorNegrasMock = mock(Jugador.class);
        LocalDateTime horaCreacion = LocalDateTime.now();
        juego = new Juego(jugadorBlancasMock, jugadorNegrasMock, horaCreacion);
    }

    @Test
    void testJuegoConstructorAndGetters() {

        Long id = 1L;
        Long idJugadorBlancas = 2L;
        Long idJugadorNegras = 3L;


        juego.setId(id);
        juego.setId_jugador_blancas(idJugadorBlancas);
        juego.setId_jugador_negras(idJugadorNegras);


        assertEquals(id, juego.getId());
        assertEquals(idJugadorBlancas, juego.getId_jugador_blancas());
        assertEquals(idJugadorNegras, juego.getId_jugador_negras());
    }

    @Test
    void testObtenerHistorialMovimientos() {
        List<String> historialMovimientos = Arrays.asList("e2-e4", "e7-e5");

        juego.getTablero().setHistorialMovimientos(historialMovimientos);
        List<String> result = juego.obtenerHistorialMovimientos();

        assertEquals(historialMovimientos, result);
    }

    @Test
    void testToString() {
        LocalDateTime horaCreacion = LocalDateTime.of(2023, 6, 16, 10, 30);

        when(jugadorBlancasMock.getNombre()).thenReturn("JugadorBlancas");
        when(jugadorNegrasMock.getNombre()).thenReturn("JugadorNegras");

        juego.setHoraCreacion(horaCreacion);

        String result = juego.toString();

        assertEquals("16 de junio | 10:30 |\nJugadorBlancas (Blancas) vs JugadorNegras (Negras)", result);
    }


    @Test
    void testVerTablero() {
        String expectedTablero = "＃ １ ２ ３ ４ ５ ６ ７ ８ "
                + "\n<br>Ａ ♖ ♘ ♗ ♕ ♔ ♗ ♘ ♖ "
                +"\n<br>Ｂ ♙ ♙ ♙ ♙ ♙ ♙ ♙ ♙ "
                +"\n<br>Ｃ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ "
                +"\n<br>Ｄ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ "
                +"\n<br>Ｅ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ "
                +"\n<br>Ｆ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ "
                +"\n<br>Ｇ ♟ ♟ ♟ ♟ ♟ ♟ ♟ ♟ "
                +"\n<br>Ｈ ♜ ♞ ♝ ♚ ♛ ♝ ♞ ♜ \n<br>";


        String result = juego.verTablero();

        assertEquals(expectedTablero, result);
    }

    @Test
    void testCambiarTurno() {
        juego.setJugadorActual(jugadorBlancasMock);


        boolean result = juego.cambiarTurno();

        assertTrue(result);
        assertEquals(jugadorNegrasMock, juego.getJugadorActual());
    }
    @Test
    void testCambiarTurno2() {
        juego.setJugadorActual(jugadorNegrasMock);


        boolean result = juego.cambiarTurno();

        assertTrue(result);
        assertEquals(jugadorBlancasMock, juego.getJugadorActual());
    }

    @Test
    void testCambiarTurno_Falso() {
        TableroModelForTests tablero = new TableroModelForTests();
        Posicion pos1 =new Posicion(0,4);
        Posicion pos2 =new Posicion(7,3);
        Rey reyB = new Rey(pos1, Color.BLANCAS, Tipo.REY);
        tablero.agregarPieza(reyB,pos1);
        tablero.agregarPieza(new Rey(pos2,Color.NEGRAS,Tipo.REY),pos2);
        Tablero tablero1 = new Tablero(tablero.getTablero());
        juego.setTablero(tablero1);
        juego.getTablero().setEstadoJuego(reyB);
        juego.setJugadorActual(jugadorNegrasMock);


        boolean result = juego.cambiarTurno();

        assertFalse(result);
    }



    @Test
    void testCheckEstadoJuego() {
        Peon pieza = new Peon(null,Color.BLANCAS,Tipo.PEON);
        
        juego.getTablero().setEstadoJuego(pieza);

        EstadoJuego result = juego.getTablero().getEstadoJuego();
        assertEquals(EstadoJuego.EN_CURSO, result);
    }


    @Test
    void testObtenerGanador() {
        juego.getTablero().setColorGanador(Color.BLANCAS);
        when(jugadorBlancasMock.getColor()).thenReturn(Color.BLANCAS);

        Jugador result = juego.obtenerGanador();

        assertEquals(jugadorBlancasMock, result);
    }

    @Test
    void testObtenerPerdedor() {
        juego.getTablero().setColorGanador(Color.NEGRAS);
        jugadorBlancasMock.setColor(Color.BLANCAS);

        Jugador result = juego.obtenerPerdedor();

        assertEquals(jugadorBlancasMock, result);
    }
}

