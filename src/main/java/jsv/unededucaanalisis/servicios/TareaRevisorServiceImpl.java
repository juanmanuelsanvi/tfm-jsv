package jsv.unededucaanalisis.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jsv.unededucaanalisis.modelo.TareaRevisor;
import jsv.unededucaanalisis.repositorios.RevisorRepository;

@Service("TareaRevisorService")
public class TareaRevisorServiceImpl implements TareaRevisorService {

	@Autowired
	private RevisorRepository repositorio;
	
	@Override
	public TareaRevisor add(TareaRevisor r)
	{
		return repositorio.save(r);		
	}

	@Override
	public TareaRevisor edit(TareaRevisor r) 
	{
		return repositorio.save(r);
	}

	@Override
	public void delete(Integer Id) {
		// TODO Auto-generated method stub
		repositorio.deleteById(Id);
	}	
	
	@Override
	public TareaRevisor findById(Integer Id) {
		// TODO Auto-generated method stub
		return repositorio.findById(Id).orElse(null);
	}

	@Override
	public List<TareaRevisor> findAll() 
	{
		return repositorio.findAll();
	}
	

}
