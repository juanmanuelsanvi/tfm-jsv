package jsv.unededucaanalisis.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import jsv.unededucaanalisis.modelo.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer> 
{
	Persona findByUsuario(String usuario);
}
