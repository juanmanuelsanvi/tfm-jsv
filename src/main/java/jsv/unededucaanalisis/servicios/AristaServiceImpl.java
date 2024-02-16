package jsv.unededucaanalisis.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jsv.unededucaanalisis.modelo.Arista;
import jsv.unededucaanalisis.repositorios.AristaRepository;

@Service("AristaService")
public class AristaServiceImpl implements AristaService {

	@Autowired
	private AristaRepository repositorio;
	
	
	@Override
	public Arista findByAsignatura(Integer Id) {
		// TODO Auto-generated method stub
		return repositorio.findById(Id).orElse(null);
	}

	@Override
	public List<Arista> findAll() 
	{
		return repositorio.findAll();
	}

}
