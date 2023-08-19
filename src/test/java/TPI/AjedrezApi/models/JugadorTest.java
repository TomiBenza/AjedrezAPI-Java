package TPI.AjedrezApi.models;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JugadorTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void testJugadorConstructorAndGetters() {
        Long id = 1L;
        String nombre = "Juancito";
        Integer puntos = 100;
        Integer partidasGanadas = 5;
        Integer partidasPerdidas = 3;
        Color color = Color.BLANCAS;

        Jugador jugador = new Jugador(id, nombre, puntos, partidasGanadas, partidasPerdidas, color);

        assertEquals(id, jugador.getId());
        assertEquals(nombre, jugador.getNombre());
        assertEquals(puntos, jugador.getPuntos());
        assertEquals(partidasGanadas, jugador.getPartidas_ganadas());
        assertEquals(partidasPerdidas, jugador.getPartidas_perdidas());
        assertEquals(color, jugador.getColor());
    }

    @Test
    void testJugadorNoArgsConstructor() {
        Jugador jugador = new Jugador();

        assertNull(jugador.getId());
        assertNull(jugador.getNombre());
        assertNull(jugador.getPuntos());
        assertNull(jugador.getPartidas_ganadas());
        assertNull(jugador.getPartidas_perdidas());
        assertNull(jugador.getColor());
    }

    @Test
    void testJugadorToString() {
        String nombre = "John Doe";
        Integer puntos = 100;
        Integer partidasGanadas = 5;
        Integer partidasPerdidas = 3;
        Jugador jugador = new Jugador(nombre);
        jugador.setPuntos(puntos);
        jugador.setPartidas_ganadas(partidasGanadas);
        jugador.setPartidas_perdidas(partidasPerdidas);

        String expectedToString = "Nombre: " + nombre +
                ", Puntaje: " + puntos +
                ", Partidas Ganadas: " + partidasGanadas +
                ", Partidas Perdidas: " + partidasPerdidas;

        String actualToString = jugador.toString();

        assertEquals(expectedToString, actualToString);
    }

    @Test
    void testJugadorNombreValidation() {
        Jugador jugador = new Jugador();
        jugador.setNombre("J");

        Set<ConstraintViolation<Jugador>> violations = validator.validate(jugador);

        assertEquals(1, violations.size());
        ConstraintViolation<Jugador> violation = violations.iterator().next();
        assertEquals("El nombre debe tener entre 2 y 50 caracteres", violation.getMessage());
    }
}

