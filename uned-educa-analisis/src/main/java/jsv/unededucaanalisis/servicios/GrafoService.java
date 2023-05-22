package jsv.unededucaanalisis.servicios;

import java.util.List;

import java.io.IOException;

import jsv.unededucaanalisis.modelo.Grafo;
import jsv.unededucaanalisis.servicios.AristaService;
import jsv.unededucaanalisis.servicios.PersonaService;

public interface GrafoService 
{
	public Grafo generarGrafo(PersonaService servicioPersonas, AristaService servicioAristas);
	public int generarFichero(Grafo migrafo, String formato)  throws IOException;
	public void generarIndicadores(Grafo migrafo) throws IOException;
	public List<Double> getBetweenesscentrality();
	public List<Double> getClosnesscentrality();
	public List<Double> getHarmonicclosnesscentrality();
	public List<Double> getEccentricity();
}
