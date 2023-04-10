package amm.unededucaanalisis.servicios;

import java.util.List;

import amm.unededucaanalisis.modelo.Mensaje;
import amm.unededucaanalisis.modelo.Persona;

public interface MensajeService 
{
	public Mensaje add(Mensaje a);
	public Mensaje edit(Mensaje a);
	public void delete(String Id);	
	public Mensaje findById(String Id);
	public List<Mensaje> findAll();

}
