package TPI.AjedrezApi.models.piezas;
import TPI.AjedrezApi.extra.TableroModelForTests;
import TPI.AjedrezApi.models.Color;
import TPI.AjedrezApi.models.Piezas.Peon;
import TPI.AjedrezApi.models.Piezas.Torre;
import TPI.AjedrezApi.models.Posicion;
import TPI.AjedrezApi.models.Tablero;
import TPI.AjedrezApi.models.Tipo;
import TPI.AjedrezApi.models.Movimiento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TorreTest {
    TableroModelForTests tableroTest;
    Torre torre;
    @BeforeEach
    public void setUp(){
        torre=new Torre(new Posicion(0, 0), Color.BLANCAS, Tipo.TORRE);
        tableroTest=new TableroModelForTests();
        tableroTest.agregarPieza(torre,torre.getPosicion());
    }
    @Test
    public void testGetSymbol_Blancas_ReturnsCorrectSymbol() {
        String symbol = torre.getSymbol();
        assertEquals("♖", symbol);
    }

    @Test
    public void testGetSymbol_Negras_ReturnsCorrectSymbol() {
        Torre torre = new Torre(new Posicion(0, 0), Color.NEGRAS, Tipo.TORRE);
        String symbol = torre.getSymbol();

        assertEquals("♜", symbol);
    }

    @Test
    public void testMovimientoValido_Horizontal_ValidMove() {
        Tablero tablero = new Tablero(tableroTest.getTablero());
        Movimiento movimiento = new Movimiento(new Posicion(0, 0), new Posicion(0, 5));
        boolean isValid = torre.movimientoValido(movimiento, tablero);

        // Assert
        assertTrue(isValid);
    }

    @Test
    public void testMovimientoValido_Vertical_ValidMove() {
        Tablero tablero = new Tablero(tableroTest.getTablero());
        Movimiento movimiento = new Movimiento(new Posicion(0, 0), new Posicion(5, 0));
        boolean isValid = torre.movimientoValido(movimiento, tablero);

        assertTrue(isValid);
    }

    @Test
    public void testMovimientoValido_OtherPieceInTheWay_InvalidMove() {
        Peon peon = new Peon(new Posicion(0, 2), Color.NEGRAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());
        Movimiento movimiento = new Movimiento(new Posicion(0, 0), new Posicion(0, 5));

        boolean isValid = torre.movimientoValido(movimiento, tablero);

        assertFalse(isValid);
    }

    @Test
    public void testMovimientoValido_SameColorPieceInTheWay_InvalidMove() {
        Peon peon = new Peon(new Posicion(0, 2), Color.NEGRAS, Tipo.PEON);
        tableroTest.agregarPieza(peon,peon.getPosicion());
        Tablero tablero = new Tablero(tableroTest.getTablero());
        Movimiento movimiento = new Movimiento(new Posicion(0, 0), new Posicion(0, 5));

        boolean isValid = torre.movimientoValido(movimiento, tablero);

        assertFalse(isValid);
    }

    @Test
    public void testMovimientoValido_InvalidMove() {
        Tablero tablero = new Tablero(tableroTest.getTablero());
        Movimiento movimiento = new Movimiento(new Posicion(0, 0), new Posicion(5, 5));

        boolean isValid = torre.movimientoValido(movimiento, tablero);

        assertFalse(isValid);
    }
}
