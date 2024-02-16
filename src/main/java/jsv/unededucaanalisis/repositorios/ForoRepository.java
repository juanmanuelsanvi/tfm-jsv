package jsv.unededucaanalisis.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import jsv.unededucaanalisis.modelo.Foro;

public interface ForoRepository extends JpaRepository<Foro, Integer> 
{
	Foro findByIdMateriaAndDenominacion(Integer idMateria, String denominacion);
}
