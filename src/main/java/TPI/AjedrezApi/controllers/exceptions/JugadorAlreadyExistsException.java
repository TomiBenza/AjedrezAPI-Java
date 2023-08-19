package TPI.AjedrezApi.controllers.exceptions;

public class JugadorAlreadyExistsException extends RuntimeException {
    public JugadorAlreadyExistsException(String message) {
        super(message);
    }
}
