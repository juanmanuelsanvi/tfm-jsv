package amm.unededucaanalisis.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import amm.unededucaanalisis.modelo.Asignatura;
import amm.unededucaanalisis.modelo.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer> 
{
	Persona findByUsuario(String usuario);
	

}
