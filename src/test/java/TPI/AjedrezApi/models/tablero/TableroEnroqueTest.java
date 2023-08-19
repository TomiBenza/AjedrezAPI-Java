package TPI.AjedrezApi.models.tablero;

import TPI.AjedrezApi.extra.TableroModelForTests;
import TPI.AjedrezApi.models.*;
import TPI.AjedrezApi.models.Piezas.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TableroEnroqueTest {

    TableroModelForTests tablero = new TableroModelForTests();
    Rey reyN;
    Rey reyB;

    @BeforeEach
    void setUp() {
            reyB = new Rey(new Posicion(0,4), Color.BLANCAS, Tipo.REY);
            reyN = new Rey(new Posicion(7,3),Color.NEGRAS,Tipo.REY);
            tablero.agregarPieza(reyB,new Posicion(0,4));
            tablero.agregarPieza(reyN,new Posicion(7,3));

            tablero.agregarPieza(new Torre(new Posicion(0,7),Color.BLANCAS,Tipo.TORRE),new Posicion(0,7));
            tablero.agregarPieza(new Torre(new Posicion(0,7),Color.BLANCAS,Tipo.TORRE),new Posicion(0,0));

            tablero.agregarPieza(new Torre(new Posicion(7,0),Color.NEGRAS,Tipo.TORRE),new Posicion(7,0));
            tablero.agregarPieza(new Torre(new Posicion(7,0),Color.NEGRAS,Tipo.TORRE),new Posicion(7,7));
        }

        @Test
        public void testVerificarEnroqueCorto() {
            Tablero tableroReal = new Tablero(tablero.getTablero());
            // Rey blancas:
            Movimiento movimientoB = new Movimiento(new Posicion(0, 4), new Posicion(0, 7));

            // Rey negras:
            Movimiento movimientoN = new Movimiento(new Posicion(7, 3), new Posicion(7, 0));

            boolean resultadoBlancas = tableroReal.verificarEnroque(reyB,movimientoB);
            boolean resultadoNegras = tableroReal.verificarEnroque(reyN,movimientoN);

            assertTrue(resultadoBlancas && resultadoNegras);
        }

        @Test
        public void testVerificarEnroqueLargo(){
            Tablero tableroReal = new Tablero(tablero.getTablero());
            // Rey blancas:
            Movimiento movimientoB = new Movimiento(new Posicion(0, 4), new Posicion(0, 0));

            // Rey negras:
            Movimiento movimientoN = new Movimiento(new Posicion(7, 3), new Posicion(7, 7));

            boolean resultadoBlancas = tableroReal.verificarEnroque(reyB,movimientoB);
            boolean resultadoNegras = tableroReal.verificarEnroque(reyN,movimientoN);

            assertTrue(resultadoBlancas && resultadoNegras);
        }
        @Test public void enroqueNoValidoBlancas(){
            Tablero tableroReal = new Tablero(tablero.getTablero());
            Movimiento movimiento = new Movimiento(new Posicion(0, 4), new Posicion(1, 0));
            boolean resultado =  tableroReal.verificarEnroque(reyB,movimiento);

            assertFalse(resultado);
        }

    @Test
    public void enroqueCortoNoValido_PiezaEnMedio(){
        Caballo caballo = new Caballo(new Posicion(7,1),Color.NEGRAS,Tipo.CABALLO);
        Caballo caballo2 = new Caballo(new Posicion(0,6),Color.BLANCAS,Tipo.CABALLO);
        tablero.agregarPieza(caballo,new Posicion(7,1));
        tablero.agregarPieza(caballo2,new Posicion(0,6));
        Tablero tableroReal = new Tablero(tablero.getTablero());


        Movimiento movimientoN = new Movimiento(reyN.getPosicion(), new Posicion(7, 0));
        Movimiento movimientoB = new Movimiento(reyB.getPosicion(), new Posicion(0, 7));
        boolean resultadoNegras =  tableroReal.verificarEnroque(reyN,movimientoN);
        boolean resultadoBlancas =  tableroReal.verificarEnroque(reyB,movimientoB);

        assertFalse(resultadoNegras && resultadoBlancas);
    }

    @Test
    public void enroqueLargoNoValido_PiezaEnMedio(){
        Caballo caballo = new Caballo(new Posicion(7,6),Color.NEGRAS,Tipo.CABALLO);
        tablero.agregarPieza(caballo,new Posicion(7,6));
        Tablero tableroReal = new Tablero(tablero.getTablero());


        Movimiento movimiento = new Movimiento(new Posicion(7, 3), new Posicion(7, 7));
        boolean resultado =  tableroReal.verificarEnroque(reyN,movimiento);

        assertFalse(resultado);
    }

    @Test
    public void movimientoEnroqueLargoTest(){
        Tablero tableroReal = new Tablero(tablero.getTablero());
        // Rey blancas:
        Movimiento movimientoB = new Movimiento(new Posicion(0, 4), new Posicion(0, 0));

        // Rey negras:
        Movimiento movimientoN = new Movimiento(new Posicion(7, 3), new Posicion(7, 7));

        boolean resultadoBlancas = tableroReal.moverPieza(reyB,movimientoB);
        boolean resultadoNegras = tableroReal.moverPieza(reyN,movimientoN);

        List<String> historial = tableroReal.getHistorialMovimientos();

        assertTrue(resultadoBlancas && resultadoNegras);
        assertEquals(historial.size(),2);
    }

    @Test
    public void movimientoEnroqueCortoTest(){
        Tablero tableroReal = new Tablero(tablero.getTablero());
        // Rey blancas:
        Movimiento movimientoB = new Movimiento(new Posicion(0, 4), new Posicion(0, 7));

        // Rey negras:
        Movimiento movimientoN = new Movimiento(new Posicion(7, 3), new Posicion(7, 0));

        boolean resultadoBlancas = tableroReal.moverPieza(reyB,movimientoB);
        boolean resultadoNegras = tableroReal.moverPieza(reyN,movimientoN);
        List<String> historial = tableroReal.getHistorialMovimientos();
        assertTrue(resultadoBlancas && resultadoNegras);
        assertEquals(historial.size(),2);
        assertEquals(historial.get(0),"REY de BLANCAS, A5 -> A8");
        assertEquals(historial.get(1),"REY de NEGRAS, H4 -> H1");
    }

    @Test
    public void movimientoEnroqueCortoTest_PiezaEnMedio(){
        tablero.agregarPieza(new Caballo(new Posicion(0,6),Color.BLANCAS,Tipo.CABALLO),new Posicion(0,6));
        tablero.agregarPieza(new Caballo(new Posicion(7,2),Color.NEGRAS,Tipo.CABALLO),new Posicion(7,2));
        Tablero tableroReal = new Tablero(tablero.getTablero());
        // Rey blancas:
        Movimiento movimientoB = new Movimiento(new Posicion(0, 4), new Posicion(0, 7));

        // Rey negras:
        Movimiento movimientoN = new Movimiento(new Posicion(7, 3), new Posicion(7, 0));

        boolean resultadoBlancas = tableroReal.moverPieza(reyB,movimientoB);
        boolean resultadoNegras = tableroReal.moverPieza(reyN,movimientoN);
        assertFalse(resultadoBlancas);
        assertFalse(resultadoNegras);

    }

    @Test
    public void movimientoEnroqueLargoTest_PiezaEnMedio(){
        tablero.agregarPieza(new Caballo(new Posicion(0,3),Color.BLANCAS,Tipo.CABALLO),new Posicion(0,3));
        tablero.agregarPieza(new Caballo(new Posicion(7,4),Color.NEGRAS,Tipo.CABALLO),new Posicion(7,4));

        Tablero tableroReal = new Tablero(tablero.getTablero());
        // Rey blancas:
        Movimiento movimientoB = new Movimiento(new Posicion(0, 4), new Posicion(0, 0));

        // Rey negras:
        Movimiento movimientoN = new Movimiento(new Posicion(7, 3), new Posicion(7, 7));

        boolean resultadoBlancas = tableroReal.moverPieza(reyB,movimientoB);
        boolean resultadoNegras = tableroReal.moverPieza(reyN,movimientoN);
        assertFalse(resultadoNegras);
        assertFalse(resultadoBlancas);
    }


    @Test
    public void movimientoEnroqueNoValido(){
        Tablero tableroReal = new Tablero(tablero.getTablero());
        // Rey blancas:
        Movimiento movimientoB1 = new Movimiento(new Posicion(0, 3), new Posicion(0, 0));
        Movimiento movimientoB2 = new Movimiento(new Posicion(0, 4), new Posicion(0, 1));
        // Rey negras:
        Movimiento movimientoN1 = new Movimiento(new Posicion(7, 4), new Posicion(7, 7));
        Movimiento movimientoN2 = new Movimiento(new Posicion(7, 3), new Posicion(7, 6));

        boolean resultadoBlancas1 = tableroReal.moverPieza(reyB,movimientoB1);
        boolean resultadoNegras1 = tableroReal.moverPieza(reyN,movimientoN1);

        boolean resultadoBlancas2 = tableroReal.moverPieza(reyB,movimientoB2);
        boolean resultadoNegras2 = tableroReal.moverPieza(reyN,movimientoN2);

        assertFalse(resultadoNegras1);
        assertFalse(resultadoBlancas1);
        assertFalse(resultadoNegras2);
        assertFalse(resultadoBlancas2);
    }








}
