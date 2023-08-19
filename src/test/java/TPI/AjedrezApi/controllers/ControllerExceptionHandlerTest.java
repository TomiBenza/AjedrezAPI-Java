package TPI.AjedrezApi.controllers;

import TPI.AjedrezApi.controllers.ControllerExceptionHandler;
import TPI.AjedrezApi.controllers.exceptions.JugadorNotFoundException;
import TPI.AjedrezApi.dtos.common.ErrorApi;
import TPI.AjedrezApi.entities.JugadorEntity;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllerExceptionHandlerTest {
    @Test
    void handleError_ShouldReturnInternalServerError() {

        Exception exception = new Exception("Internal Server Error");
        ControllerExceptionHandler controllerExceptionHandler = new ControllerExceptionHandler();


        ResponseEntity<ErrorApi> responseEntity = controllerExceptionHandler.handleError(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Internal Server Error", responseEntity.getBody().getMessage());
    }

    @Test
    void handleError_ShouldReturnBadRequest() {

        ResponseStatusException exception = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        ControllerExceptionHandler controllerExceptionHandler = new ControllerExceptionHandler();

        ResponseEntity<ErrorApi> responseEntity = controllerExceptionHandler.handleError(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Bad Request", responseEntity.getBody().getMessage());
    }

    @Test
    void handleError_ShouldReturnNotFound() {

        EntityNotFoundException exception = new EntityNotFoundException("Entity Not Found");
        ControllerExceptionHandler controllerExceptionHandler = new ControllerExceptionHandler();


        ResponseEntity<ErrorApi> responseEntity = controllerExceptionHandler.handleError(exception);


        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Entity Not Found", responseEntity.getBody().getMessage());
    }

    @Test
    void handleJugadorNotFoundException_ShouldReturnNotFound() {

        Class<?> entityClass = JugadorEntity.class; // Reemplaza JugadorEntity con la clase correspondiente
        Long id = 123L;
        JugadorNotFoundException exception = new JugadorNotFoundException(entityClass, id);
        ControllerExceptionHandler controllerExceptionHandler = new ControllerExceptionHandler();


        ResponseEntity<ErrorApi> responseEntity = controllerExceptionHandler.handleJugadorNotFoundException(exception);


        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("No existe el jugador con id 123. Si lo desea, puede crear un nuevo jugador.", responseEntity.getBody().getMessage());
    }

    @Test
    void handleError_ShouldReturnBadRequestForMethodArgumentNotValidException() {

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("object", "field1", "Error 1");
        FieldError fieldError2 = new FieldError("object", "field2", "Error 2");
        List<FieldError> fieldErrors = Arrays.asList(fieldError1, fieldError2);
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        ControllerExceptionHandler controllerExceptionHandler = new ControllerExceptionHandler();


        ResponseEntity<ErrorApi> responseEntity = controllerExceptionHandler.handleError(exception);


        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Error 1; Error 2", responseEntity.getBody().getMessage());
    }
}

