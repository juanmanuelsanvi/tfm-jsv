package jsv.unededucaanalisis.servicios;

import java.util.List;

import org.gephi.graph.api.GraphModel;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.statistics.plugin.GraphDistance;

import java.awt.Image;
import java.io.IOException;
import jsv.unededucaanalisis.modelo.Grafo;

public interface GrafoService 
{
	public Grafo generarGrafo(PersonaService servicioPersonas, AristaService servicioAristas);
	public int generarFichero(Grafo migrafo, String formato)  throws IOException;
	public void generarIndicadores(Grafo migrafo) throws IOException;
	public List<Integer> getIniciativa();
	public List<Integer> getActividad();
	public List<Integer> getPopularidad();
	public List<Double> getBetweenesscentrality();
	public List<Double> getClosnesscentrality();
	public List<Double> getEigenvector();
	public List<Integer> getModularity();
	public List<String> getImagenes();
	public GraphDistance getGraphDistance();
	public GraphModel getGraphModel();
	public Workspace getWorkspace();
	public ProjectController getPc();
	public Image getImage();
	//public List<Double> getHarmonicclosnesscentrality();
	//public List<Double> getEccentricity();
	
}
