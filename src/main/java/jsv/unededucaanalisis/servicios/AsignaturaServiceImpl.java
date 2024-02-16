package jsv.unededucaanalisis.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jsv.unededucaanalisis.modelo.Asignatura;
import jsv.unededucaanalisis.repositorios.AsignaturaRepository;

@Service("AsignaturaService")
public class AsignaturaServiceImpl implements AsignaturaService {

	@Autowired
	private AsignaturaRepository repositorio;
	
	@Override
	public Asignatura add(Asignatura a) 
	{
		return repositorio.save(a);		
	}

	@Override
	public Asignatura edit(Asignatura a) 
	{
		return repositorio.save(a);
	}

	@Override
	public void delete(Integer Id) {
		// TODO Auto-generated method stub
		repositorio.deleteById(Id);
	}	
		
	@Override
	public Asignatura findById(Integer Id) {
		// TODO Auto-generated method stub
		return repositorio.findById(Id).orElse(null);
	}

	@Override
	public List<Asignatura> findAll() 
	{
		return repositorio.findAll();
	}
	
	@Override
	public Asignatura findByDenominacion(String asignatura) 
	{	
		return repositorio.findByDenominacion(asignatura);
	}

}
