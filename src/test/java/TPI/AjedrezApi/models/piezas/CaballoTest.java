package TPI.AjedrezApi.models.piezas;
import TPI.AjedrezApi.extra.TableroModelForTests;
import TPI.AjedrezApi.models.Color;
import TPI.AjedrezApi.models.Piezas.Caballo;
import TPI.AjedrezApi.models.Posicion;
import TPI.AjedrezApi.models.Tipo;
import TPI.AjedrezApi.models.Movimiento;
import TPI.AjedrezApi.models.Tablero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CaballoTest {

    TableroModelForTests tableroTest;
    Caballo caballo;
    @BeforeEach
    public void setUp(){
        caballo=new Caballo(new Posicion(0, 0), Color.BLANCAS, Tipo.CABALLO);
        tableroTest=new TableroModelForTests();
        tableroTest.agregarPieza(caballo,new Posicion(0, 0));
    }
    @Test
    void testMovimientoValido_Valido1() {
        Tablero tablero = new Tablero(tableroTest.getTablero());
        Movimiento movimiento = new Movimiento(new Posicion(0, 0), new Posicion(2, 1));
        boolean esValido = caballo.movimientoValido(movimiento, tablero);
        assertTrue(esValido);
    }

    @Test
    void testMovimientoValido_Valido2() {
        Tablero tablero = new Tablero(tableroTest.getTablero());
        Movimiento movimiento = new Movimiento(new Posicion(0, 0), new Posicion(1, 2));
        boolean esValido = caballo.movimientoValido(movimiento, tablero);
        assertTrue(esValido);
    }

    @Test
    void testMovimientoValido_Invalido1() {
        Tablero tablero = new Tablero(tableroTest.getTablero());
        Movimiento movimiento = new Movimiento(new Posicion(0, 0), new Posicion(1, 1));
        boolean esValido = caballo.movimientoValido(movimiento, tablero);
        assertFalse(esValido);
    }

    @Test
    void testMovimientoValido_Invalido2() {
        Tablero tablero = new Tablero(tableroTest.getTablero());
        Movimiento movimiento = new Movimiento(new Posicion(0, 0), new Posicion(3, 2));
        boolean esValido = caballo.movimientoValido(movimiento, tablero);
        assertFalse(esValido);
    }
}
