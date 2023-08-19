package TPI.AjedrezApi.models;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MovimientoPiezaTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void testMovimientoPiezaConstructorAndGetters() {
        String tipoPieza = "peon";
        String posicion = "A2";
        String moverHacia = "A4";


        MovimientoPieza movimientoPieza = new MovimientoPieza(tipoPieza, posicion, moverHacia);


        assertEquals(tipoPieza, movimientoPieza.getTipoPieza());
        assertEquals(posicion, movimientoPieza.getPosicion());
        assertEquals(moverHacia, movimientoPieza.getMoverHacia());
    }

    @Test
    void testMovimientoPiezaNoArgsConstructor() {
        MovimientoPieza movimientoPieza = new MovimientoPieza();

        assertNull(movimientoPieza.getTipoPieza());
        assertNull(movimientoPieza.getPosicion());
        assertNull(movimientoPieza.getMoverHacia());
    }

    @Test
    void testMovimientoPiezaValidation() {
        MovimientoPieza movimientoPieza = new MovimientoPieza();
        movimientoPieza.setTipoPieza("peon");
        movimientoPieza.setPosicion(null);
        movimientoPieza.setMoverHacia("A4");

        Set<ConstraintViolation<MovimientoPieza>> violations = validator.validate(movimientoPieza);

        assertEquals(1, violations.size());
        ConstraintViolation<MovimientoPieza> violation = violations.iterator().next();
        assertEquals("La posicion a donde se encuentra no puede ser nula", violation.getMessage());
    }
}

