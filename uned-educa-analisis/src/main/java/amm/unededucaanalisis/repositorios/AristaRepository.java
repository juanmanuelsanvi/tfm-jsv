package amm.unededucaanalisis.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import amm.unededucaanalisis.modelo.Arista;

public interface AristaRepository 
		extends JpaRepository<Arista, Integer> {
	

}
