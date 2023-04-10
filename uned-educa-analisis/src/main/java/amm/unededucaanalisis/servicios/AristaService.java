package amm.unededucaanalisis.servicios;

import java.util.List;

import amm.unededucaanalisis.modelo.Arista;

public interface AristaService 
{
	public Arista findByAsignatura(Integer Id);
	public List<Arista> findAll();

}
