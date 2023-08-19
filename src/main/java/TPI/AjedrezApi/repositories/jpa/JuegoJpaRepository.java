package TPI.AjedrezApi.repositories.jpa;

import TPI.AjedrezApi.entities.JuegoEntity;
import TPI.AjedrezApi.entities.JugadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JuegoJpaRepository extends JpaRepository<JuegoEntity,Long> {
}
