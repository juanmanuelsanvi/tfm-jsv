package amm.unededucaanalisis.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import amm.unededucaanalisis.modelo.Asignatura;
import amm.unededucaanalisis.modelo.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Integer> 
{
	Materia findByidConvocatoriaAndIdAsignaturaAndIdProfesor(Integer idConvocatoria, Integer idAsignatura, Integer idProfesor );
}
