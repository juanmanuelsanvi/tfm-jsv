package jsv.unededucaanalisis.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import jsv.unededucaanalisis.modelo.Convocatoria;

public interface ConvocatoriaRepository extends JpaRepository<Convocatoria, Integer> 
{
	Convocatoria findByConvocatoria(String convocatoria);
}
