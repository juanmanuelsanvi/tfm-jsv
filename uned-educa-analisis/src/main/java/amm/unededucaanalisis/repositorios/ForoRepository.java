package amm.unededucaanalisis.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import amm.unededucaanalisis.modelo.Asignatura;
import amm.unededucaanalisis.modelo.Foro;
import amm.unededucaanalisis.modelo.Materia;

public interface ForoRepository extends JpaRepository<Foro, Integer> 
{
	Foro findByIdMateriaAndDenominacion(Integer idMateria, String denominacion);
}
