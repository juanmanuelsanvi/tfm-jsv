package jsv.unededucaanalisis.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import jsv.unededucaanalisis.modelo.TareaRevisor;

public interface RevisorRepository extends JpaRepository<TareaRevisor, Integer> 
{
	
}
