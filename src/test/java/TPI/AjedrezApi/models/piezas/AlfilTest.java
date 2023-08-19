package TPI.AjedrezApi.models.piezas;

import TPI.AjedrezApi.extra.TableroModelForTests;
import TPI.AjedrezApi.models.*;
import TPI.AjedrezApi.models.Piezas.Alfil;
import TPI.AjedrezApi.models.Piezas.Peon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlfilTest {
    TableroModelForTests tableroTest;
    Alfil alfil;
    @BeforeEach
    public void setUp(){
        alfil=new Alfil(new Posicion(0, 0), Color.BLANCAS, Tipo.ALFIL);
        tableroTest=new TableroModelForTests();
        tableroTest.agregarPieza(alfil,new Posicion(0, 0));
    }
    @Test
    void testGetSymbol_Blanco() {
        Alfil alfilBlanco = new Alfil(new Posicion(0, 0), Color.BLANCAS, Tipo.ALFIL);
        String symbol = alfilBlanco.getSymbol();
        assertEquals("♗", symbol);
    }

    @Test
    void testGetSymbol_Negro() {
        Alfil alfilNegro = new Alfil(new Posicion(0, 0), Color.NEGRAS, Tipo.ALFIL);
        String symbol = alfilNegro.getSymbol();
        assertEquals("♝", symbol);
    }

    @Test
    void testMovimientoValido_MovimientoDiagonalValido() {
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimientoValido = new Movimiento(new Posicion(0, 0), new Posicion(3, 3));
        boolean resultado = alfil.movimientoValido(movimientoValido, tablero);
        assertTrue(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoNoDiagonal() {
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimientoNoDiagonal = new Movimiento(new Posicion(0, 0), new Posicion(1, 2));
        boolean resultado = alfil.movimientoValido(movimientoNoDiagonal, tablero);
        assertFalse(resultado);
    }

    @Test
    void testMovimientoValido_PiezaEnCamino() {
        // Colocar una pieza en el camino del movimiento diagonal
        Peon peon = new Peon(new Posicion(1, 1), Color.BLANCAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());

        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimientoConPiezaEnCamino = new Movimiento(new Posicion(0, 0), new Posicion(3, 3));
        boolean resultado = alfil.movimientoValido(movimientoConPiezaEnCamino, tablero);
        assertFalse(resultado);
    }

    @Test
    void testMovimientoValido_PiezaEnPosicionFinalDelMovimiento() {
        // Colocar una pieza del mismo color en la posición final del movimiento
        Peon peon = new Peon(new Posicion(3, 3), Color.BLANCAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());

        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimientoConPiezaEnPosicionFinal = new Movimiento(new Posicion(0, 0), new Posicion(3, 3));
        boolean resultado = alfil.movimientoValido(movimientoConPiezaEnPosicionFinal, tablero);
        assertFalse(resultado);
    }


    @Test
    void testMovimientoValido_MovimientoDiagonalInversoValido() {
        Alfil alfil = new Alfil(new Posicion(4, 4), Color.BLANCAS, Tipo.ALFIL);
        tableroTest.agregarPieza(alfil, alfil.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());
        Movimiento movimientoValido = new Movimiento(new Posicion(4, 4), new Posicion(1, 1));
        boolean resultado = alfil.movimientoValido(movimientoValido, tablero);
        assertTrue(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoNoDiagonalInvalido() {
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimientoNoDiagonal = new Movimiento(new Posicion(0, 0), new Posicion(1, 3));
        boolean resultado = alfil.movimientoValido(movimientoNoDiagonal, tablero);
        assertFalse(resultado);
    }

    @Test
    void testMovimientoValido_PiezaEnCaminoInvalido() {
        // Colocar una pieza en el camino del movimiento diagonal
        Peon peon = new Peon(new Posicion(1, 1), Color.BLANCAS, Tipo.PEON);
        tableroTest.agregarPieza(peon, peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimientoConPiezaEnCamino = new Movimiento(new Posicion(0, 0), new Posicion(2, 2));
        boolean resultado = alfil.movimientoValido(movimientoConPiezaEnCamino, tablero);
        assertFalse(resultado);
    }

    @Test
    void testMovimientoValido_PiezaEnPosicionFinalDelMovimientoInvalido() {
        // Colocar una pieza del mismo color en la posición final del movimiento
        Peon peon = new Peon(new Posicion(2, 2), Color.BLANCAS, Tipo.PEON);
        tableroTest.agregarPieza(peon, peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimientoConPiezaEnPosicionFinal = new Movimiento(new Posicion(0, 0), new Posicion(2, 2));
        boolean resultado = alfil.movimientoValido(movimientoConPiezaEnPosicionFinal, tablero);
        assertFalse(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoDiagonalInvalido() {
        Tablero tablero = new Tablero(tableroTest.getTablero());
        Movimiento movimientoDiagonalInvalido = new Movimiento(new Posicion(0, 0), new Posicion(2, 1));
        boolean resultado = alfil.movimientoValido(movimientoDiagonalInvalido, tablero);
        assertFalse(resultado);
    }

    @Test
    void testMovimientoValido_PosicionInicialIgualAPosicionFinalInvalido() {
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimientoMismaPosicion = new Movimiento(new Posicion(0, 0), new Posicion(0, 0));
        boolean resultado = alfil.movimientoValido(movimientoMismaPosicion, tablero);
        assertFalse(resultado);
    }
}

