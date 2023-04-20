package jsv.unededucaanalisis.repositorios;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

//import jsv.unededucaanalisis.modelo.Asignatura;
import jsv.unededucaanalisis.modelo.Mensaje;
//import jsv.unededucaanalisis.modelo.Persona;

public interface MensajeRepository extends JpaRepository<Mensaje, String> 
{
	//Mensaje findById(String id);
}
