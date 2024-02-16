package jsv.unededucaanalisis.servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jsv.unededucaanalisis.modelo.Persona;
import jsv.unededucaanalisis.repositorios.PersonaRepository;

@Service("PersonaService")
public class PersonaServiceImpl implements PersonaService {

	@Autowired
	private PersonaRepository repositorio;
	@Override
	public Persona add(Persona a) 
	{
		return repositorio.save(a);		
	}

	@Override
	public Persona edit(Persona a) 
	{
		return repositorio.save(a);
	}

	@Override
	public void delete(Integer Id) {
		// TODO Auto-generated method stub
		repositorio.deleteById(Id);
	}	
	
	
	@Override
	public Persona findById(Integer Id) {
		// TODO Auto-generated method stub
		return repositorio.findById(Id).orElse(null);
	}

	@Override
	public List<Persona> findAll() 
	{
		return repositorio.findAll();
	}

	
	@Override
	public Persona findByUsuario(String usuario) 
	{	
		return repositorio.findByUsuario(usuario);
	}
	
	
}
