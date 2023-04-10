package amm.unededucaanalisis.servicios;

import java.util.List;

import amm.unededucaanalisis.modelo.Asignatura;
import amm.unededucaanalisis.modelo.Foro;

public interface ForoService 
{
	public Foro add(Foro a);
	public Foro edit(Foro a);
	public void delete(Integer Id);	
	public Foro findById(Integer Id);
	public Foro findByIdMateriaAndDenominacion(Integer idMateria, String denominacion);
	public List<Foro> findAll();

}
