package TPI.AjedrezApi.models.tablero;

import TPI.AjedrezApi.extra.TableroModelForTests;
import TPI.AjedrezApi.models.*;
import TPI.AjedrezApi.models.Piezas.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TablasTest {
    TableroModelForTests tablero = new TableroModelForTests();
    Rey reyN;
    Rey reyB;

    @BeforeEach
    void setUp() {
        reyB = new Rey(new Posicion(0,4), Color.BLANCAS, Tipo.REY);
        reyN = new Rey(new Posicion(7,3),Color.NEGRAS,Tipo.REY);
        tablero.agregarPieza(reyB,new Posicion(0,4));
        tablero.agregarPieza(reyN,new Posicion(7,3));

    }
    // Solo reyes vivos:
    @Test
    public void tablasSoloReyesVivos(){
        Tablero tableroReal = new Tablero(tablero.getTablero());
        tableroReal.setEstadoJuego(reyB);
        EstadoJuego estado=tableroReal.getEstadoJuego();
        assertEquals(EstadoJuego.EMPATE,estado);
    }

    @Test
    public void tablasSoloReyesVivos_Falso_BlancaExtra(){
        tablero.agregarPieza(new Reina(new Posicion(1,1),Color.BLANCAS,Tipo.REINA),new Posicion(1,1));
        Tablero tableroReal = new Tablero(tablero.getTablero());
        tableroReal.setEstadoJuego(reyB);
        EstadoJuego estado=tableroReal.getEstadoJuego();
        assertEquals(EstadoJuego.EN_CURSO,estado);
    }

    @Test
    public void tablasSoloReyesVivos_Falso_NegraExtra(){
        tablero.agregarPieza(new Reina(new Posicion(1,1),Color.NEGRAS,Tipo.REINA),new Posicion(1,1));
        Tablero tableroReal = new Tablero(tablero.getTablero());
        tableroReal.setEstadoJuego(reyB);
        EstadoJuego estado=tableroReal.getEstadoJuego();
        assertEquals(EstadoJuego.EN_CURSO,estado);
    }

    // REY Y ALFIL vs REY:
    @Test
    public void tablasReyAlfilContraReyNegro(){
        tablero.agregarPieza(new Alfil(new Posicion(1,1),Color.BLANCAS,Tipo.ALFIL),new Posicion(1,1));
        Tablero tableroReal = new Tablero(tablero.getTablero());
        tableroReal.setEstadoJuego(reyB);
        EstadoJuego estado=tableroReal.getEstadoJuego();
        assertEquals(EstadoJuego.EMPATE,estado);
    }
    @Test
    public void tablasReyAlfilContraReyBlanco(){
        tablero.agregarPieza(new Alfil(new Posicion(1,1),Color.NEGRAS,Tipo.ALFIL),new Posicion(1,1));
        Tablero tableroReal = new Tablero(tablero.getTablero());
        tableroReal.setEstadoJuego(reyB);
        EstadoJuego estado=tableroReal.getEstadoJuego();
        assertEquals(EstadoJuego.EMPATE,estado);
    }
    @Test
    public void tablasReyAlfilContraReyBlanco_Falso1(){
        tablero.agregarPieza(new Alfil(new Posicion(1,1),Color.NEGRAS,Tipo.ALFIL),new Posicion(1,1));
        tablero.agregarPieza(new Alfil(new Posicion(2,1),Color.NEGRAS,Tipo.ALFIL),new Posicion(2,1));
        Tablero tableroReal = new Tablero(tablero.getTablero());
        tableroReal.setEstadoJuego(reyB);
        EstadoJuego estado=tableroReal.getEstadoJuego();
        assertEquals(EstadoJuego.EN_CURSO,estado);
    }
    @Test
    public void tablasReyAlfilContraReyBlanco_Falso2(){
        tablero.agregarPieza(new Alfil(new Posicion(1,1),Color.NEGRAS,Tipo.ALFIL),new Posicion(1,1));
        tablero.agregarPieza(new Peon(new Posicion(2,1),Color.NEGRAS,Tipo.PEON),new Posicion(2,1));
        Tablero tableroReal = new Tablero(tablero.getTablero());
        tableroReal.setEstadoJuego(reyB);
        EstadoJuego estado=tableroReal.getEstadoJuego();
        assertEquals(EstadoJuego.EN_CURSO,estado);
    }
    @Test
    public void tablasReyAlfilContraReyBlanco_Falso3(){
        tablero.agregarPieza(new Alfil(new Posicion(1,1),Color.NEGRAS,Tipo.ALFIL),new Posicion(1,1));
        tablero.agregarPieza(new Peon(new Posicion(2,1),Color.BLANCAS,Tipo.PEON),new Posicion(2,1));
        Tablero tableroReal = new Tablero(tablero.getTablero());
        tableroReal.setEstadoJuego(reyB);
        EstadoJuego estado=tableroReal.getEstadoJuego();
        assertEquals(EstadoJuego.EN_CURSO,estado);
    }
    @Test
    public void tablasReyAlfilContraReyBlanco_Falso4(){
        tablero.agregarPieza(new Alfil(new Posicion(1,1),Color.NEGRAS,Tipo.ALFIL),new Posicion(1,1));
        tablero.agregarPieza(new Alfil(new Posicion(2,1),Color.BLANCAS,Tipo.ALFIL),new Posicion(2,1));
        Tablero tableroReal = new Tablero(tablero.getTablero());
        tableroReal.setEstadoJuego(reyB);
        EstadoJuego estado=tableroReal.getEstadoJuego();
        assertEquals(EstadoJuego.EN_CURSO,estado);
    }

    // REY Y CABALLO vs REY:
    @Test
    public void tablasReyCaballoContraReyNegro(){
        tablero.agregarPieza(new Caballo(new Posicion(1,1),Color.BLANCAS,Tipo.CABALLO),new Posicion(1,1));
        Tablero tableroReal = new Tablero(tablero.getTablero());
        tableroReal.setEstadoJuego(reyB);
        EstadoJuego estado=tableroReal.getEstadoJuego();
        assertEquals(EstadoJuego.EMPATE,estado);
    }

    @Test
    public void tablasReyCaballoContraReyBlanco(){
        tablero.agregarPieza(new Caballo(new Posicion(1,1),Color.NEGRAS,Tipo.CABALLO),new Posicion(1,1));
        Tablero tableroReal = new Tablero(tablero.getTablero());
        tableroReal.setEstadoJuego(reyB);
        EstadoJuego estado=tableroReal.getEstadoJuego();
        assertEquals(EstadoJuego.EMPATE,estado);
    }
}
