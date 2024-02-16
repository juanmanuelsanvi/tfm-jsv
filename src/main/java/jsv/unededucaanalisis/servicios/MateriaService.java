package jsv.unededucaanalisis.servicios;

import java.util.List;
import jsv.unededucaanalisis.modelo.Materia;

public interface MateriaService 
{
	public Materia add(Materia a);
	public Materia edit(Materia a);
	public void delete(Integer Id);	
	public Materia findById(Integer Id);
	public List<Materia> findAll();
	public Materia findByidConvocatoriaAndIdAsignaturaAndIdProfesor(Integer idConvocatoria, Integer idAsignatura, Integer idProfesor );
}
