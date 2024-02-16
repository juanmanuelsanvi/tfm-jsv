package jsv.unededucaanalisis.servicios;

import java.util.List;

import jsv.unededucaanalisis.modelo.Arista;

public interface AristaService 
{
	public Arista findByAsignatura(Integer Id);
	public List<Arista> findAll();

}
