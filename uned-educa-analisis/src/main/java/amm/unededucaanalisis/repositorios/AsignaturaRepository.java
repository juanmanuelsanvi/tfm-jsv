package amm.unededucaanalisis.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import amm.unededucaanalisis.modelo.Asignatura;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Integer> 
{
	Asignatura findByDenominacion(String asignatura);
	

}
