package jsv.unededucaanalisis.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jsv.unededucaanalisis.modelo.Foro;
import jsv.unededucaanalisis.repositorios.ForoRepository;

@Service("ForoService")
public class ForoServiceImpl implements ForoService {

	@Autowired
	private ForoRepository repositorio;

	@Override
	public Foro add(Foro a) 
	{
		return repositorio.save(a);		
	}

	@Override
	public Foro edit(Foro a) 
	{
		return repositorio.save(a);
	}

	@Override
	public void delete(Integer Id) {
		// TODO Auto-generated method stub
		repositorio.deleteById(Id);
	}		
	
	@Override
	public Foro findById(Integer Id) {
		// TODO Auto-generated method stub
		return repositorio.findById(Id).orElse(null);
	}

	@Override
	public List<Foro> findAll() 
	{
		return repositorio.findAll();
	}
	
	@Override
	public Foro findByIdMateriaAndDenominacion(Integer idMateria, String denominacion) 
	{	
		return repositorio.findByIdMateriaAndDenominacion(idMateria, denominacion);
	}

}
