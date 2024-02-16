package jsv.unededucaanalisis.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jsv.unededucaanalisis.modelo.Materia;
import jsv.unededucaanalisis.repositorios.MateriaRepository;

@Service("MateriaService")
public class MateriaServiceImpl implements MateriaService {

	@Autowired
	private MateriaRepository repositorio;
	@Override
	public Materia add(Materia a) 
	{
		return repositorio.save(a);		
	}

	@Override
	public Materia edit(Materia a) 
	{
		return repositorio.save(a);
	}

	@Override
	public void delete(Integer Id) {
		// TODO Auto-generated method stub
		repositorio.deleteById(Id);
	}	
	
	
	@Override
	public Materia findById(Integer Id) {
		// TODO Auto-generated method stub
		return repositorio.findById(Id).orElse(null);
	}

	@Override
	public List<Materia> findAll() 
	{
		return repositorio.findAll();
	}

	@Override
	public Materia findByidConvocatoriaAndIdAsignaturaAndIdProfesor(Integer idConvocatoria, Integer idAsignatura, Integer idProfesor )
	{
		return repositorio.findByidConvocatoriaAndIdAsignaturaAndIdProfesor(idConvocatoria, idAsignatura, idProfesor);
	}

}
