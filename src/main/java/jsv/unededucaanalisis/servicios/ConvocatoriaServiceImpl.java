package jsv.unededucaanalisis.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jsv.unededucaanalisis.modelo.Convocatoria;
import jsv.unededucaanalisis.repositorios.ConvocatoriaRepository;

@Service("ConvocatoriaService")
public class ConvocatoriaServiceImpl implements ConvocatoriaService {

	@Autowired
	private ConvocatoriaRepository repositorio;
	@Override
	public Convocatoria add(Convocatoria  a) 
	{
		return repositorio.save(a);		
	}

	@Override
	public Convocatoria edit(Convocatoria a) 
	{
		return repositorio.save(a);
	}

	@Override
	public void delete(Integer Id) {
		// TODO Auto-generated method stub
		repositorio.deleteById(Id);
	}	
	
	
	@Override
	public Convocatoria findById(Integer Id) {
		// TODO Auto-generated method stub
		return repositorio.findById(Id).orElse(null);
	}

	@Override
	public List<Convocatoria> findAll() 
	{
		return repositorio.findAll();
	}
	
	@Override
	public Convocatoria findByConvocatoria(String convocatoria)
	{
		return repositorio.findByConvocatoria(convocatoria);
	}

}
