package amm.unededucaanalisis.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import jsv.unededucaanalisis.modelo.Asignatura;
import jsv.unededucaanalisis.modelo.Foro;
import jsv.unededucaanalisis.modelo.Materia;

public interface ForoRepository extends JpaRepository<Foro, Integer> 
{
	Foro findByIdMateriaAndDenominacion(Integer idMateria, String denominacion);
}
