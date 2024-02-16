package jsv.unededucaanalisis.modelo;

import java.util.List;
import java.util.Objects;

public class Grafo 
{
	
	private List<Arista> aristas; 
	private List<Persona> nodos;
	
	public Grafo()
	{

	}
	
	public void setAristas(List<Arista> aristas) {
		this.aristas = aristas;
	}
	
	public void setNodos(List<Persona> nodos) {
		this.nodos = nodos;
	}
	
	public List<Arista> getAristas() {
		return aristas;
	}

	public List<Persona> getNodos() {
		return nodos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(aristas, nodos);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grafo other = (Grafo) obj;
		return Objects.equals(aristas, other.aristas) && Objects.equals(nodos, other.nodos);
	}
	
	@Override
	public String toString() {
		return "Grafo [aristas=" + aristas + ", nodos=" + nodos + "]";
	}	
	
}
