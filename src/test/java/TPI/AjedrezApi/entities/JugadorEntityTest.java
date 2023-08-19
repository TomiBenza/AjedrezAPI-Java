package TPI.AjedrezApi.entities;

import TPI.AjedrezApi.entities.JugadorEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JugadorEntityTest {

    @Test
    void testJugadorEntity() {

        Long id = 1L;
        String nombre = "Roberto";
        Integer partidasGanadas = 5;
        Integer partidasPerdidas = 3;
        Integer puntos = 10;


        JugadorEntity jugadorEntity = new JugadorEntity(id, nombre, partidasGanadas, partidasPerdidas, puntos);


        assertEquals(id, jugadorEntity.getId_jugador());
        assertEquals(nombre, jugadorEntity.getNombre());
        assertEquals(partidasGanadas, jugadorEntity.getPartidas_ganadas());
        assertEquals(partidasPerdidas, jugadorEntity.getPartidas_perdidas());
        assertEquals(puntos, jugadorEntity.getPuntos());
    }
}

