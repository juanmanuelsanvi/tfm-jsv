package jsv.unededucaanalisis.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import jsv.unededucaanalisis.modelo.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, String> 
{
	//Mensaje findById(String id);
}
