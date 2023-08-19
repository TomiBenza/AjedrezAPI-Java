package TPI.AjedrezApi.models.piezas;

import TPI.AjedrezApi.extra.TableroModelForTests;
import TPI.AjedrezApi.models.*;
import TPI.AjedrezApi.models.Piezas.Peon;
import TPI.AjedrezApi.models.Piezas.Rey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PeonTest {

    Tablero tablero;
    @BeforeEach
    void setUp(){
        tablero = new Tablero();
    }

    @Test
    public void primerMovimientoTest(){
        Movimiento movimiento = new Movimiento(new Posicion(1,0),new Posicion(3,0));
        boolean pudo = tablero.moverPieza(tablero.getPosPieza(new Posicion(1,0)),movimiento);
        assertTrue(pudo);
    }

    @Test
    public void normalMovimientoTest(){
        Movimiento movimiento = new Movimiento(new Posicion(1,0),new Posicion(2,0));
        boolean pudo = tablero.moverPieza(tablero.getPosPieza(new Posicion(1,0)),movimiento);
        assertTrue(pudo);
    }

    @Test
    public void movimientoErroneoTest(){
        Movimiento movimiento = new Movimiento(new Posicion(1,0),new Posicion(5,0));
        boolean pudo = tablero.moverPieza(tablero.getPosPieza(new Posicion(1,0)),movimiento);
        assertFalse(pudo);
    }

    @Test
    public void movimientoAtrasTest(){
        // PEON BLANCO
        Movimiento movimiento = new Movimiento(new Posicion(1,0),new Posicion(0,0));
        boolean pudo = tablero.moverPieza(tablero.getPosPieza(new Posicion(1,0)),movimiento);
        assertFalse(pudo);

        // PEON NEGRO
        Movimiento movimiento2 = new Movimiento(new Posicion(6,0),new Posicion(7,0));
        boolean pudo2 = tablero.moverPieza(tablero.getPosPieza(new Posicion(6,0)),movimiento2);
        assertFalse(pudo2);
    }

    @Test
    public void comerPiezaTest(){
        // Mover peon blancas B1 -> D1
        Movimiento movimiento1 = new Movimiento(new Posicion(1,0),new Posicion(3,0));
        tablero.moverPieza(tablero.getPosPieza(new Posicion(1,0)), movimiento1);

        // Mover peon negras G2 -> E2
        Movimiento movimiento2 = new Movimiento(new Posicion(6,1),new Posicion(4,1));
        tablero.moverPieza(tablero.getPosPieza(new Posicion(6,1)), movimiento2);

        // Comer peon
        Movimiento comer = new Movimiento(new Posicion(3,0),new Posicion(4,1));
        boolean pudo = tablero.moverPieza(tablero.getPosPieza(new Posicion(3,0)),comer);
        assertTrue(pudo);
    }

    @Test
    public void comerPiezaTest_Error(){
        // Comer peon
        Movimiento comer = new Movimiento(new Posicion(3,0),new Posicion(4,1));
        boolean pudo = tablero.moverPieza(tablero.getPosPieza(new Posicion(3,0)),comer);
        assertFalse(pudo);
    }

    @Test
    public void promocionTest(){
        TableroModelForTests tablero = new TableroModelForTests();
        Peon peon = new Peon(new Posicion(6,0), Color.BLANCAS, Tipo.PEON);

        // Se agrega rey para cuando se verifique el estado:
        Rey rey = new Rey(new Posicion(0,0), Color.NEGRAS, Tipo.REY);
        tablero.agregarPieza(peon,new Posicion(6,0));
        tablero.agregarPieza(rey,new Posicion(0,0));
        Tablero tableroReal = new Tablero(tablero.getTablero());
        boolean resultado = tableroReal.moverPieza(peon,new Movimiento(peon.getPosicion(),new Posicion(7,0)));

        assertTrue(resultado);
    }


}
