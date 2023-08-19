package TPI.AjedrezApi.models.piezas;

import TPI.AjedrezApi.models.*;
import TPI.AjedrezApi.models.Piezas.Pieza;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PiezaTest {

    @Test
    void testPiezaConstructorAndGetters() {
        Posicion posicion = new Posicion(2, 3);
        Color color = Color.BLANCAS;
        Tipo tipo = Tipo.PEON;

        Pieza pieza = new MockPieza(posicion, color, tipo);

        assertEquals(posicion, pieza.getPosicion());
        assertEquals(color, pieza.getColor());
        assertEquals(Estado.Activo, pieza.getEstado());
        assertEquals(tipo, pieza.getTipo());
    }

    @Test
    void testToString() {
        Posicion posicion = new Posicion(2, 3);
        Color color = Color.BLANCAS;
        Tipo tipo = Tipo.TORRE;
        Pieza pieza = new MockPieza(posicion, color, tipo);

        String result = pieza.toString();

        assertEquals("TORRE de BLANCAS", result);
    }

    @Test
    void testCambiarEstado() {
        Posicion posicion = new Posicion(2, 3);
        Color color = Color.BLANCAS;
        Tipo tipo = Tipo.CABALLO;
        Pieza pieza = new MockPieza(posicion, color, tipo);

        pieza.cambiarEstado(Estado.Muerto);

        assertEquals(Estado.Muerto, pieza.getEstado());
    }



    // Clase MockPieza para realizar pruebas en la clase abstracta Pieza
    private static class MockPieza extends Pieza {
        public MockPieza(Posicion posicion, Color color, Tipo tipo) {
            super(posicion, color, tipo);
        }

        @Override
        public String getSymbol() {
            return null;
        }

        @Override
        public boolean movimientoValido(Movimiento mov, Tablero tablero) {
            return false;
        }
    }
}

