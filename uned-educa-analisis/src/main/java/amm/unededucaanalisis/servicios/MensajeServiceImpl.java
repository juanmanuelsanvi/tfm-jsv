package amm.unededucaanalisis.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import amm.unededucaanalisis.modelo.Asignatura;
import amm.unededucaanalisis.modelo.Mensaje;
import amm.unededucaanalisis.modelo.Persona;
import amm.unededucaanalisis.repositorios.MensajeRepository;
import amm.unededucaanalisis.repositorios.PersonaRepository;

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
