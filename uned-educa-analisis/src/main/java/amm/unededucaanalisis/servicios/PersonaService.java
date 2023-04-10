package amm.unededucaanalisis.servicios;

import java.util.List;

import amm.unededucaanalisis.modelo.Persona;

public interface PersonaService 
{
	public Persona add(Persona a);
	public Persona edit(Persona a);
	public void delete(Integer Id);	
	public Persona findById(Integer Id);
	public Persona findByUsuario(String usuario);	
	public List<Persona> findAll();

}
