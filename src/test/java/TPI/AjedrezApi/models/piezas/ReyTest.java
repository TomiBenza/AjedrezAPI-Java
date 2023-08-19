package TPI.AjedrezApi.models.piezas;
import TPI.AjedrezApi.extra.TableroModelForTests;
import TPI.AjedrezApi.models.*;
import TPI.AjedrezApi.models.Piezas.Rey;
import TPI.AjedrezApi.models.Piezas.Peon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReyTest {
    TableroModelForTests tableroTest;
    Rey rey;
    @BeforeEach
    public void setUp(){
        rey=new Rey(new Posicion(3, 3), Color.BLANCAS, Tipo.REY);
        tableroTest=new TableroModelForTests();
        tableroTest.agregarPieza(rey,new Posicion(3, 3));
    }
    @Test
    void testGetSymbol_PiezaBlancaDevuelveSimboloCorrecto() {
        Rey reyBlanco = new Rey(new Posicion(0, 0), Color.BLANCAS, Tipo.REY);
        String symbol = reyBlanco.getSymbol();
        assertEquals("♔", symbol);
    }

    @Test
    void testGetSymbol_PiezaNegraDevuelveSimboloCorrecto() {
        Rey reyNegro = new Rey(new Posicion(0, 0), Color.NEGRAS, Tipo.REY);
        String symbol = reyNegro.getSymbol();
        assertEquals("♚", symbol);
    }

    @Test
    void testMovimientoValido_MovimientoDiagonalValido() {
        Tablero tablero = new Tablero(tableroTest.getTablero());
        Movimiento movimientoDiagonal = new Movimiento(new Posicion(3, 3), new Posicion(4, 4));
        boolean resultado = rey.movimientoValido(movimientoDiagonal, tablero);
        assertTrue(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoHorizontalValido() {
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimientoHorizontal = new Movimiento(new Posicion(3, 3), new Posicion(3, 4));
        boolean resultado = rey.movimientoValido(movimientoHorizontal, tablero);
        assertTrue(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoVerticalValido() {
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimientoVertical = new Movimiento(new Posicion(3, 3), new Posicion(4, 3));
        boolean resultado = rey.movimientoValido(movimientoVertical, tablero);
        assertTrue(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoInvalido_PiezaEnPosicionFinal() {
        // Colocar una pieza del mismo color en la posición final del movimiento
        Peon peon = new Peon(new Posicion(4, 4), Color.BLANCAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());

        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimientoInvalido = new Movimiento(new Posicion(3, 3), new Posicion(4, 4));
        boolean resultado = rey.movimientoValido(movimientoInvalido, tablero);
        assertFalse(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoInvalido_PosicionInicialIgualAPosicionFinal() {
        Tablero tablero = new Tablero(tableroTest.getTablero());
        Movimiento movimientoMismaPosicion = new Movimiento(new Posicion(3, 3), new Posicion(3, 3));
        boolean resultado = rey.movimientoValido(movimientoMismaPosicion, tablero);
        assertFalse(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoValido_PiezaAtacadaNull_ColorIgual() {
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(3, 4));
        boolean resultado = rey.movimientoValido(movimiento, tablero);
        assertTrue(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoValido_PiezaAtacadaNull_ColorDiferente() {
        Peon peon = new Peon(new Posicion(3, 4), Color.NEGRAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(3, 4));
        boolean resultado = rey.movimientoValido(movimiento, tablero);
        assertTrue(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoInvalido_PiezaAtacadaNoNull_ColorIgual() {
        Peon peon = new Peon(new Posicion(3, 4), Color.BLANCAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(3, 4));
        boolean resultado = rey.movimientoValido(movimiento, tablero);
        assertFalse(resultado);
    }


}
