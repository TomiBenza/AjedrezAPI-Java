package TPI.AjedrezApi.entities;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JuegoEntityTest {

    @Mock
    private JugadorEntity jugadorBlancasMock;

    @Mock
    private JugadorEntity jugadorNegrasMock;

    @Test
    void testJuegoEntityConstructorAndGetters() {
        Long id = 1L;
        LocalDateTime fechaCreacion = LocalDateTime.now();
        Boolean terminado = false;

        JuegoEntity juegoEntity = new JuegoEntity(id, fechaCreacion, jugadorBlancasMock, jugadorNegrasMock, terminado);

        assertEquals(id, juegoEntity.getId());
        assertEquals(fechaCreacion, juegoEntity.getFecha_creacion());
        assertEquals(jugadorBlancasMock, juegoEntity.getJugador_blancas());
        assertEquals(jugadorNegrasMock, juegoEntity.getJugador_negras());
        assertEquals(terminado, juegoEntity.getTerminado());
    }

    @Test
    void testJuegoEntityNoArgsConstructor() {
        JuegoEntity juegoEntity = new JuegoEntity();
        assertNotNull(juegoEntity);
    }

    @Test
    void testJuegoEntitySetters() {
        JuegoEntity juegoEntity = new JuegoEntity();

        Long id = 1L;
        LocalDateTime fechaCreacion = LocalDateTime.now();
        Boolean terminado = false;


        juegoEntity.setId(id);
        juegoEntity.setFecha_creacion(fechaCreacion);
        juegoEntity.setJugador_blancas(jugadorBlancasMock);
        juegoEntity.setJugador_negras(jugadorNegrasMock);
        juegoEntity.setTerminado(terminado);


        assertEquals(id, juegoEntity.getId());
        assertEquals(fechaCreacion, juegoEntity.getFecha_creacion());
        assertEquals(jugadorBlancasMock, juegoEntity.getJugador_blancas());
        assertEquals(jugadorNegrasMock, juegoEntity.getJugador_negras());
        assertEquals(terminado, juegoEntity.getTerminado());
    }


}


