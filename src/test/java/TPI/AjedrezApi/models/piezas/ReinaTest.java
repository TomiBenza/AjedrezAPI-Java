package TPI.AjedrezApi.models.piezas;
import TPI.AjedrezApi.extra.TableroModelForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import TPI.AjedrezApi.models.*;
import TPI.AjedrezApi.models.Piezas.Reina;

import TPI.AjedrezApi.models.Piezas.Peon;

import static org.junit.jupiter.api.Assertions.*;

public class ReinaTest {
    TableroModelForTests tableroTest;
    Reina reina;
    @BeforeEach
    public void setUp(){
        reina=new Reina(new Posicion(3, 3), Color.BLANCAS, Tipo.REINA);
        tableroTest=new TableroModelForTests();
        tableroTest.agregarPieza(reina,new Posicion(3, 3));
    }
    @Test
    void testGetSymbol_Blancas() {
        Reina reina = new Reina(new Posicion(0, 0), Color.BLANCAS, Tipo.REINA);
        String symbol = reina.getSymbol();
        assertEquals("♕", symbol);
    }

    @Test
    void testGetSymbol_Negras() {
        Reina reina = new Reina(new Posicion(0, 0), Color.NEGRAS, Tipo.REINA);
        String symbol = reina.getSymbol();
        assertEquals("♛", symbol);
    }
    @Test
    void testMovimientoValido_MovimientoValidoHorizontal_PiezaAtacadaNull() {
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(3, 6));
        boolean resultado = reina.movimientoValido(movimiento, tablero);
        assertTrue(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoValidoVertical_PiezaAtacadaNull() {
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(6, 3));
        boolean resultado = reina.movimientoValido(movimiento, tablero);
        assertTrue(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoValidoDiagonal_PiezaAtacadaNull() {
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(6, 6));
        boolean resultado = reina.movimientoValido(movimiento, tablero);
        assertTrue(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoInvalidoHorizontal_PiezaEnCamino() {
        Peon peon = new Peon(new Posicion(3, 5), Color.NEGRAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(3, 6));
        boolean resultado = reina.movimientoValido(movimiento, tablero);
        assertFalse(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoInvalidoVertical_PiezaEnCamino() {
        Peon peon = new Peon(new Posicion(5, 3), Color.NEGRAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(6, 3));
        boolean resultado = reina.movimientoValido(movimiento, tablero);
        assertFalse(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoInvalidoDiagonal_PiezaEnCamino() {
        Peon peon = new Peon(new Posicion(5, 5), Color.NEGRAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(6, 6));
        boolean resultado = reina.movimientoValido(movimiento, tablero);
        assertFalse(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoInvalidoHorizontal_PiezaAtacadaMismoColor() {
        Peon peon = new Peon(new Posicion(3, 6), Color.BLANCAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(3, 6));
        boolean resultado = reina.movimientoValido(movimiento, tablero);
        assertFalse(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoInvalidoVertical_PiezaAtacadaMismoColor() {
        Peon peon = new Peon(new Posicion(6, 3), Color.BLANCAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(6, 3));
        boolean resultado = reina.movimientoValido(movimiento, tablero);
        assertFalse(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoInvalidoDiagonal_PiezaAtacadaMismoColor() {
        Peon peon = new Peon(new Posicion(6, 6), Color.BLANCAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(6, 6));
        boolean resultado = reina.movimientoValido(movimiento, tablero);
        assertFalse(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoInvalidoHorizontal_PiezaAtacadaDistintoColor() {
        Peon peon = new Peon(new Posicion(3, 6), Color.NEGRAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(3, 6));
        boolean resultado = reina.movimientoValido(movimiento, tablero);
        assertTrue(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoInvalidoVertical_PiezaAtacadaDistintoColor() {
        Peon peon = new Peon(new Posicion(6, 3), Color.NEGRAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(6, 3));
        boolean resultado = reina.movimientoValido(movimiento, tablero);
        assertTrue(resultado);
    }

    @Test
    void testMovimientoValido_MovimientoInvalidoDiagonal_PiezaAtacadaDistintoColor() {
        Peon peon = new Peon(new Posicion(6, 6), Color.NEGRAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());

        Movimiento movimiento = new Movimiento(new Posicion(3, 3), new Posicion(6, 6));
        boolean resultado = reina.movimientoValido(movimiento, tablero);
        assertTrue(resultado);
    }
}
