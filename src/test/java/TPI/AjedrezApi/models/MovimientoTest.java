package TPI.AjedrezApi.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MovimientoTest {

    @Test
    void testMovimientoConstructorAndGetters() {

        Posicion from = new Posicion(2, 3);
        Posicion to = new Posicion(4, 5);


        Movimiento movimiento = new Movimiento(from, to);


        assertEquals(from, movimiento.getFrom());
        assertEquals(to, movimiento.getTo());
    }

    @Test
    void testMovimientoSetters() {
        Posicion inicialFrom = new Posicion(1, 1);
        Posicion inicialTo = new Posicion(2, 2);
        Movimiento movimiento = new Movimiento(inicialFrom,inicialTo);
        Posicion from = new Posicion(2, 3);
        Posicion to = new Posicion(4, 5);


        movimiento.setFrom(from);
        movimiento.setTo(to);


        assertEquals(from, movimiento.getFrom());
        assertEquals(to, movimiento.getTo());
    }
}

