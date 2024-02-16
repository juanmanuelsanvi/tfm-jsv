package jsv.unededucaanalisis.servicios;

import java.util.List;

import jsv.unededucaanalisis.modelo.TareaRevisor;

public interface TareaRevisorService 
{
	public TareaRevisor add(TareaRevisor r);
	public TareaRevisor edit(TareaRevisor r);
	public void delete(Integer Id);	
	public TareaRevisor findById(Integer Id);
	public List<TareaRevisor> findAll();

}
