package TPI.AjedrezApi.controllers.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class JugadorNotFoundException extends EntityNotFoundException {

    private Class<?> entityClass;
    private Long id;

    public JugadorNotFoundException(Class<?> entityClass, Long id) {
        this.entityClass = entityClass;
        this.id = id;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public Long getId() {
        return id;
    }
}
