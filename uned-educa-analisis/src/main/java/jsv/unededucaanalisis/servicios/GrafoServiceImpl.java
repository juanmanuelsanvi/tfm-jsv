package jsv.unededucaanalisis.servicios;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.mysql.cj.x.protobuf.MysqlxPrepare.Execute;
import jsv.unededucaanalisis.modelo.Arista;
import jsv.unededucaanalisis.modelo.Grafo;
import jsv.unededucaanalisis.modelo.Persona;
//import jsv.unededucaanalisis.servicios.GraphDistance;
import org.gephi.project.api.Project;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.statistics.*;
import org.gephi.statistics.plugin.GraphDistance;
import org.gephi.datalab.*;

import org.gephi.graph.api.*;

@Service("GrafoService")
public class GrafoServiceImpl implements GrafoService {

	@Value("${tfmjsv.data.ficherografo}")
	private String ficheroGrafo;
	@Value("${tfmjsv.data.rutaficheros}")
	private String rutaFicheros;	
	
	private ProjectController pc;
	private GraphModel graphModel;
	private DirectedGraph directedGraph;
	private GraphDistance graphDistance;
	
	private Node n;
	private Edge e;
	
	private List<Double> betweenesscentrality;
	private List<Double> closnesscentrality;
	private List<Double> harmonicclosnesscentrality;
	private List<Double> eccentricity;
	
	public List<Double> getBetweenesscentrality() {
		return betweenesscentrality;
	}

	public void setBetweenesscentrality(Column betweenesscentrality) {
		for (Node n : directedGraph.getNodes()) {
			this.betweenesscentrality.add( (Double)n.getAttribute("betweenesscentrality"));
		}
	}

	public List<Double> getClosnesscentrality() {
		return closnesscentrality;
	}

	public void setClosnesscentrality(List<Double> closnesscentrality) {
		for (Node n : directedGraph.getNodes()) {
			this.closnesscentrality.add( (Double)n.getAttribute("closnesscentrality"));
		}
	}

	public List<Double> getHarmonicclosnesscentrality() {
		return harmonicclosnesscentrality;
	}

	public void setHarmonicclosnesscentrality(List<Double> harmonicclosnesscentrality) {
		for (Node n : directedGraph.getNodes()) {
			this.harmonicclosnesscentrality.add( (Double)n.getAttribute("harmonicclosnesscentrality"));
		}
	}

	public List<Double> getEccentricity() {
		return eccentricity;
	}

	public void setEccentricity(List<Double> eccentricity) {
		for (Node n : directedGraph.getNodes()) {
			this.eccentricity.add( (Double)n.getAttribute("eccentricity"));
		}
	}

	@Override
	public Grafo generarGrafo(PersonaService servicioPersonas, AristaService servicioAristas) 
	{
		Grafo grafo = new Grafo();
		//Obtener nodos llamando a servicios de personas
		grafo.setNodos(servicioPersonas.findAll());
		//Obtener aristas llamando a servicios de aristas.
		grafo.setAristas(servicioAristas.findAll());
		return grafo;
	}

	@Override
	public int generarFichero(Grafo migrafo, String formato) throws IOException 
	{
		FileWriter fileWriter = new FileWriter(rutaFicheros + ficheroGrafo);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println("Creator \"JUAN MANUEL SÁNCHEZ VILLAREJO\"");
	    printWriter.println("graph");	      
	    printWriter.println("[");
	    
	    //Se escriben los nodos

	    for (Persona nodo : migrafo.getNodos()) 
	    {
		    printWriter.println("node");	    	
		    printWriter.println("[");	    	
		    printWriter.println("id " + nodo.getId().toString());
		    printWriter.println("usuario \"" + nodo.getUsuario() + "\"");
		    printWriter.println("apellidos \"" +  nodo.getApellidos() + "\"");
		    printWriter.println("nombre \"" + nodo.getNombre()+ "\"");
		    printWriter.println("email \"" + nodo.getNombre()+ "\"");
		    printWriter.println("]");
	    }
	    
	    //Se escriben las aristas
	    for (Arista arista : migrafo.getAristas()) 
	    {
		    printWriter.println("edge");	    	
		    printWriter.println("[");
		    printWriter.println("source " + arista.getSource().toString());
		    printWriter.println("target " + arista.getTarget().toString());		    
		    printWriter.println("]");
	    }
	    
	    // Se cierra el grafo
	    printWriter.println("]");
	    printWriter.close();
		return 0;
	}

	@Override
	public void generarIndicadores(Grafo migrafo) throws IOException {
		// TODO Auto-generated method stub

		betweenesscentrality = new ArrayList<Double>();
		closnesscentrality = new ArrayList<Double>();
		harmonicclosnesscentrality = new ArrayList<Double>();
		eccentricity = new ArrayList<Double>();		
		
		// Primero: transformo grafo de Alfonso a grafo Gephi
		transformaGrafoGephi(migrafo);

		// Añadimos a nuestra tabla de valores 4 columnas de indicadores
		// betweenesscentrality
		// closnesscentrality
		// harmonicclosnesscentrality
		// eccentricity
		
		graphDistance = new GraphDistance();
		graphDistance.setDirected(true);		
		graphDistance.execute(graphModel);
		
		// Obtiene los 4 indicadores
		setBetweenesscentrality(graphModel.getNodeTable().getColumn(GraphDistance.BETWEENNESS));
		setBetweenesscentrality(graphModel.getNodeTable().getColumn(GraphDistance.CLOSENESS));
		setBetweenesscentrality(graphModel.getNodeTable().getColumn(GraphDistance.HARMONIC_CLOSENESS));
		setBetweenesscentrality(graphModel.getNodeTable().getColumn(GraphDistance.ECCENTRICITY));
			
	}	
	
	
	private void transformaGrafoGephi(Grafo migrafo) {
		// Inicia un proyecto y un espacio de trabajo
		pc = Lookup.getDefault().lookup(ProjectController.class);
		pc.newProject();

		Workspace workspace = pc.getCurrentWorkspace();		
		
		// Crea un modelo grafo - existe porque tenemos el workspace
		graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel(workspace);
		
		// Crea un grafo dirigido
		directedGraph = graphModel.getDirectedGraph();
		
		// Añade columnas para atributos de una persona (NODO)
		graphModel.getNodeTable().addColumn("usuario", String.class);
		graphModel.getNodeTable().addColumn("apellido", String.class);
		graphModel.getNodeTable().addColumn("nombre", String.class);
		graphModel.getNodeTable().addColumn("email", String.class);
		graphModel.getNodeTable().addColumn("fecha", String.class);
		
		// Añade columnas para atributos de un mensaje (ARISTA)
		graphModel.getEdgeTable().addColumn("foro", Integer.class);
		graphModel.getEdgeTable().addColumn("asignatura", Integer.class);
		graphModel.getEdgeTable().addColumn("numcaracteres", Integer.class);
		graphModel.getEdgeTable().addColumn("fechaEnvio", String.class);
		
		// Recorre los nodos de migrafo y los añade al nuevo grafo de gephi
		for ( Persona nodo : migrafo.getNodos()) 
	    {	
			n = graphModel.factory().newNode(nodo.getId().toString());
			directedGraph.addNode(n);
			n.setAttribute("usuario", nodo.getUsuario());
			n.setAttribute("apellido", nodo.getApellidos());
			n.setAttribute("nombre", nodo.getNombre());
			n.setAttribute("email", nodo.getEmail());
			n.setAttribute("fecha", nodo.getFechaActualizacion().toString());
	    }
		
		// Recorre las aristas de migrafo y las añade al nuevo grafo de gephi
		for ( Arista arista : migrafo.getAristas()) 
		{ 
			e = graphModel.factory().newEdge(directedGraph.getNode( arista.getSource().toString()),
											 directedGraph.getNode( arista.getTarget().toString()), 
											 0, 1.0, true);
			directedGraph.addEdge(e);
			e.setAttribute("foro", arista.getForo());
			e.setAttribute("asignatura", arista.getAsignatura());
			e.setAttribute("numcaracteres", arista.getNumcaracteres());
			e.setAttribute("fechaEnvio", arista.getFechaEnvio());
		}		
	}
	
}


