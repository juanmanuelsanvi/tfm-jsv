package jsv.unededucaanalisis.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import jsv.unededucaanalisis.modelo.Asignatura;
//import jsv.unededucaanalisis.modelo.Materia;
import jsv.unededucaanalisis.modelo.Mensaje;
//import jsv.unededucaanalisis.modelo.Persona;
import jsv.unededucaanalisis.repositorios.MensajeRepository;
//import jsv.unededucaanalisis.repositorios.PersonaRepository;

@Service("MensajeService")
public class MensajeServiceImpl implements MensajeService {

	@Autowired
	private MensajeRepository repositorio;
	@Override
	public Mensaje add(Mensaje a) 
	{
		return repositorio.save(a);		
	}

	@Override
	public Mensaje edit(Mensaje a) 
	{
		return repositorio.save(a);
	}

	@Override
	public void delete(String Id) {
		// TODO Auto-generated method stub
		repositorio.deleteById(Id);
	}	
	
	@Override
	public Mensaje findById(String Id) {
		// TODO Auto-generated method stub
		return repositorio.findById(Id).orElse(null);
	}

	@Override
	public List<Mensaje> findAll() 
	{
		return repositorio.findAll();
	}
}
