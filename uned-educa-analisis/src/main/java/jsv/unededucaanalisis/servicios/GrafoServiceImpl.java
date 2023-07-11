package jsv.unededucaanalisis.servicios;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jsv.unededucaanalisis.modelo.Arista;
import jsv.unededucaanalisis.modelo.Grafo;
import jsv.unededucaanalisis.modelo.Persona;
import org.gephi.statistics.plugin.GraphDistance;
import org.gephi.statistics.plugin.EigenvectorCentrality;
import org.gephi.statistics.plugin.Modularity;
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
	private EigenvectorCentrality eigen;
	private Modularity modular;
	
	private Node n;
	private Edge e;
	
	private List<Integer> iniciativa;
	private List<Integer> actividad;
	private List<Integer> popularidad;
	private List<Double> betweenesscentrality;
	private List<Double> closnesscentrality;
	//private List<Double> harmonicclosnesscentrality;
	//private List<Double> eccentricity;
	private List<Double> eigencentralityList;
	private List<Integer> modularityList;

	public List<Integer> getIniciativa() {
		return iniciativa;
	}

	public void setIniciativa(List<Integer> iniciativa) {
		this.iniciativa = iniciativa;
	}

	public List<Integer> getActividad() {
		return actividad;
	}

	public void setPopularidad(List<Integer> popularidad) {
		this.popularidad = popularidad;
	}
	
	public List<Integer> getPopularidad() {
		return popularidad;
	}

	public void setActividad(List<Integer> actividad) {
		this.actividad = actividad;
	}	

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

	public void setClosnesscentrality(Column closnesscentrality) {
		for (Node n : directedGraph.getNodes()) {
			this.closnesscentrality.add( (Double)n.getAttribute("closnesscentrality"));
		}
	}

	public List<Double> getEigenvector() {
		return eigencentralityList;
	}

	public void setEigenvector(Column eigenvector) {
		for (Node n : directedGraph.getNodes()) {
			this.eigencentralityList.add( (Double)n.getAttribute("eigencentrality"));
		}
	}	
	
	public List<Integer> getModularity() {
		return modularityList;
	}

	public void setModularity(Column modularity) {
		for (Node n : directedGraph.getNodes()) {
			this.modularityList.add( (Integer)n.getAttribute("modularity_class"));			
		}
	}
	
	/*public List<Double> getHarmonicclosnesscentrality() {
		return harmonicclosnesscentrality;
	}

	public void setHarmonicclosnesscentrality(Column harmonicclosnesscentrality) {
		for (Node n : directedGraph.getNodes()) {
			this.harmonicclosnesscentrality.add( (Double)n.getAttribute("harmonicclosnesscentrality"));
		}
	}*/

	/*public List<Double> getEccentricity() {
		return eccentricity;
	}

	public void setEccentricity(Column eccentricity) {
		for (Node n : directedGraph.getNodes()) {
			this.eccentricity.add( (Double)n.getAttribute("eccentricity"));
		}
	}*/
	
	
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
		
		iniciativa = new ArrayList<Integer>();
		actividad = new ArrayList<Integer>();
		popularidad = new ArrayList<Integer>();
		betweenesscentrality = new ArrayList<Double>();
		closnesscentrality = new ArrayList<Double>();
		eigencentralityList = new ArrayList<Double>();
		modularityList = new ArrayList<Integer>();
		
		//harmonicclosnesscentrality = new ArrayList<Double>();
		//eccentricity = new ArrayList<Double>();		
		
		// Primero: transformo grafo de Alfonso a grafo Gephi
		transformaGrafoGephi(migrafo);
	
		graphDistance = new GraphDistance();
		graphDistance.setDirected(true);	
		
		// Calculo los diferentes indicadores:
		// 1. Betweeness y Closeness
		graphDistance.execute(graphModel);
		
		// 2. Eigenvector
		eigen = new EigenvectorCentrality();
		eigen.execute(directedGraph);
		
		// 3. Modularidad
		modular = new Modularity();
		modular.execute(directedGraph);
		
		// Almaceno en listas individuales los 3 indicadores
		setBetweenesscentrality(graphModel.getNodeTable().getColumn(GraphDistance.BETWEENNESS));
		setClosnesscentrality(graphModel.getNodeTable().getColumn(GraphDistance.CLOSENESS));
		setEigenvector(graphModel.getNodeTable().getColumn(EigenvectorCentrality.EIGENVECTOR));
		setModularity(graphModel.getNodeTable().getColumn(Modularity.MODULARITY_CLASS));
		//setHarmonicclosnesscentrality(graphModel.getNodeTable().getColumn(GraphDistance.HARMONIC_CLOSENESS));
		//setEccentricity(graphModel.getNodeTable().getColumn(GraphDistance.ECCENTRICITY));
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
			iniciativa.add(0);
			actividad.add(0);
			popularidad.add(0);
	    }
		
		// Recorre las aristas de migrafo y las añade al nuevo grafo de gephi
		for ( Arista arista : migrafo.getAristas()) 
		{ 
			int contador;
			e = graphModel.factory().newEdge(directedGraph.getNode( arista.getSource().toString()),
											 directedGraph.getNode( arista.getTarget().toString()), 
											 0, 1.0, true);
			directedGraph.addEdge(e);
			e.setAttribute("foro", arista.getForo());
			e.setAttribute("asignatura", arista.getAsignatura());
			e.setAttribute("numcaracteres", arista.getNumcaracteres());
			e.setAttribute("fechaEnvio", arista.getFechaEnvio());
			
			contador = 0;
			// Recorre los nodos de migrafo 
			for ( Persona nodo : migrafo.getNodos()) 
			{	
				// Si el nodo inicia el mensaje lo guarda como iniciativa
				if(arista.getTarget().toString() == "") {
					// Para buscar la posición del nodo que inicia un mensaje
					if (nodo.getId() == arista.getSource()) {
						iniciativa.set(contador, iniciativa.get(contador)+1);
					}
			    }
				// Para buscar la posición del nodo que escribe mensaje e incrementar su actividad
				if (nodo.getId() == arista.getSource()) {
					actividad.set(contador, actividad.get(contador)+1);
				}
				// Para buscar la posición del nodo que escribe mensaje e incrementar su actividad
				if (nodo.getId() == arista.getTarget()) {
					popularidad.set(contador, popularidad.get(contador)+1);
				}
				
				contador = contador + 1;
			}			
		}		
	}
	
}


