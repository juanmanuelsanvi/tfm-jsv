package amm.unededucaanalisis.servicios;

import java.util.List;

import amm.unededucaanalisis.modelo.Asignatura;

public interface AsignaturaService 
{
	public Asignatura add(Asignatura a);
	public Asignatura edit(Asignatura a);
	public void delete(Integer Id);	
	public Asignatura findById(Integer Id);
	public Asignatura findByDenominacion(String asignatura);
	public List<Asignatura> findAll();

}
