package jsv.unededucaanalisis.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import jsv.unededucaanalisis.modelo.Asignatura;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Integer> 
{
	Asignatura findByDenominacion(String asignatura);
}
