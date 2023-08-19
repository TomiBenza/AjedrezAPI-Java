package TPI.AjedrezApi;

import TPI.AjedrezApi.models.Conversor;
import TPI.AjedrezApi.models.Posicion;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;


public class ConversorTest {
    Conversor conversor = new Conversor();
    @Test
    public void conversion(){
        Posicion posD1 = conversor.convertirStringAPosicion("d1");
        assertEquals(3,posD1.getX());
        assertEquals(0,posD1.getY());

        Posicion posB1 = conversor.convertirStringAPosicion("B1");
        assertEquals(1,posB1.getX());
        assertEquals(0,posB1.getY());

        Posicion posC1 = conversor.convertirStringAPosicion("C1");
        assertEquals(2,posC1.getX());
        assertEquals(0,posC1.getY());

        Posicion posE1 = conversor.convertirStringAPosicion("E1");
        assertEquals(4,posE1.getX());
        assertEquals(0,posE1.getY());

        Posicion posF1 = conversor.convertirStringAPosicion("F1");
        assertEquals(5,posF1.getX());
        assertEquals(0,posF1.getY());

        Posicion posG1 = conversor.convertirStringAPosicion("G1");
        assertEquals(6,posG1.getX());
        assertEquals(0,posG1.getY());

        Posicion posH1 = conversor.convertirStringAPosicion("H1");
        assertEquals(7,posH1.getX());
        assertEquals(0,posH1.getY());
    }

    @Test
    public void conversionBadInput1(){
        Exception excepcion = assertThrows(ResponseStatusException.class, () -> {
            conversor.convertirStringAPosicion("11");
        });

        String errorEsperado = "El formato de la posición debe ser letra-número. Ej: 'A1'";
        String errorActual = excepcion.getMessage();
        assertTrue(errorActual.contains(errorEsperado));
    }

    @Test
    public void conversionBadInput2(){
        Exception excepcion = assertThrows(ResponseStatusException.class, () -> {
            conversor.convertirStringAPosicion("T1");
        });

        String errorEsperado = "T no es una letra válida";
        String errorActual = excepcion.getMessage();
        assertTrue(errorActual.contains(errorEsperado));
    }

    @Test
    public void conversionBadInput3(){
        Exception excepcion = assertThrows(ResponseStatusException.class, () -> {
            conversor.convertirStringAPosicion("A9");
        });

        String errorEsperado = "9 no es un número válido";
        String errorActual = excepcion.getMessage();
        assertTrue(errorActual.contains(errorEsperado));
    }


    @Test
    public void convertirPosicionAString(){
        Posicion pos = new Posicion(4,2);
        String result=conversor.convertirPosicionAString(pos);
        assertEquals("E3",result);
    }


}
