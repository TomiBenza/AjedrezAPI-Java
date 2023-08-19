package TPI.AjedrezApi.repositories.jpa;

import TPI.AjedrezApi.entities.JugadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JugadorJpaRepository extends JpaRepository<JugadorEntity,Long> {
    Optional<JugadorEntity> findByNombre(String nombre);
}
