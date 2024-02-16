package jsv.unededucaanalisis.servicios;

import java.util.List;
import jsv.unededucaanalisis.modelo.Mensaje;

public interface MensajeService 
{
	public Mensaje add(Mensaje a);
	public Mensaje edit(Mensaje a);
	public void delete(String Id);	
	public Mensaje findById(String Id);
	public List<Mensaje> findAll();
}
