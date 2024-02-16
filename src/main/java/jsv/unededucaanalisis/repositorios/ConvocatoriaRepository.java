package jsv.unededucaanalisis.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import jsv.unededucaanalisis.modelo.Convocatoria;

public interface ConvocatoriaRepository extends JpaRepository<Convocatoria, Integer> 
{
	Convocatoria findByConvocatoria(String convocatoria);
}
