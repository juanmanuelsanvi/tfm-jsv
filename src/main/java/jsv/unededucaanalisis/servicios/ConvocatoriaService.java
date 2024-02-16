package jsv.unededucaanalisis.servicios;

import java.util.List;

import jsv.unededucaanalisis.modelo.Convocatoria;

public interface ConvocatoriaService 
{
	public Convocatoria add(Convocatoria a);
	public Convocatoria edit(Convocatoria a);
	public void delete(Integer Id);	
	public Convocatoria findById(Integer Id);
	public Convocatoria findByConvocatoria(String convocatoria);
	public List<Convocatoria> findAll();

}
