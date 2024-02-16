package jsv.unededucaanalisis.servicios;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jsv.unededucaanalisis.modelo.Arista;
import jsv.unededucaanalisis.modelo.Grafo;
import jsv.unededucaanalisis.modelo.Mensaje;
import jsv.unededucaanalisis.modelo.Persona;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.exporter.preview.PNGExporter;
import org.gephi.statistics.plugin.GraphDistance;
import org.gephi.statistics.plugin.EigenvectorCentrality;
import org.gephi.statistics.plugin.Modularity;
import org.gephi.graph.api.*;
import org.gephi.preview.api.G2DTarget;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.RenderTarget;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.toolkit.demos.plugins.preview.*;
import org.gephi.utils.progress.Progress;
import org.gephi.utils.progress.ProgressTicket;

@Service("GrafoService")
public class GrafoServiceImpl implements GrafoService {

	@Value("${tfmjsv.data.ficherografo}")
	private String ficheroGrafo;
	// @Value("${tfmjsv.data.rutaficheros}")
	// private String rutaFicheros;

	@Autowired
	private MensajeService servicioMensaje;

	private ProjectController pc;
	private PreviewController previewController;
	private Workspace workspace;
	private GraphModel graphModel;
	private DirectedGraph directedGraph;
	private GraphDistance graphDistance;
	private ExportController ec;
	private Image image;
	private EigenvectorCentrality eigen;
	private Modularity modular;
	private Node n;
	private Edge e;
	private List<Integer> iniciativa;
	private List<Integer> actividad;
	private List<Integer> popularidad;
	private List<Double> betweenesscentrality;
	private List<Double> closnesscentrality;
	// private List<Double> harmonicclosnesscentrality;
	// private List<Double> eccentricity;
	private List<Double> eigencentralityList;
	private List<Integer> modularityList;
	private List<String> nombresImagenes;
	private List<String> imgIndicadores;


	@Override
	public Grafo generarGrafo(PersonaService servicioPersonas, AristaService servicioAristas) {
		Grafo grafo = new Grafo();
		// Obtener nodos llamando a servicios de personas
		grafo.setNodos(servicioPersonas.findAll());
		// Obtener aristas llamando a servicios de aristas.
		grafo.setAristas(servicioAristas.findAll());
		return grafo;
	}

	@Override
	public int generarFichero(Grafo migrafo, String formato) throws IOException {
		String home = System.getProperty("user.home");
		FileWriter fileWriter = new FileWriter(home + "/Downloads/" + ficheroGrafo);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println("Creator \"JUAN MANUEL SÁNCHEZ VILLAREJO\"");
		printWriter.println("graph");
		printWriter.println("[");

		// Se escriben los nodos
		for (Persona nodo : migrafo.getNodos()) {
			printWriter.println("node");
			printWriter.println("[");
			printWriter.println("id " + nodo.getId().toString());
			printWriter.println("usuario \"" + nodo.getUsuario() + "\"");
			// printWriter.println("apellidos \"" + nodo.getApellidos() + "\"");
			// printWriter.println("nombre \"" + nodo.getNombre() + "\"");
			// printWriter.println("email \"" + nodo.getNombre() + "\"");
			printWriter.println("]");
		}

		// Se escriben las aristas
		for (Arista arista : migrafo.getAristas()) {
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
		String imgPath;

		nombresImagenes = new ArrayList<String>();
		nombresImagenes.add("./target/classes/static/images/Closeness Centrality Distribution.png");
		nombresImagenes.add("./target/classes/static/images/Betweenness Centrality Distribution.png");
		nombresImagenes.add("./target/classes/static/images/eigenvector-centralities.png");
		nombresImagenes.add("./target/classes/static/images/communities-size-distribution.png");

		iniciativa = new ArrayList<Integer>();
		actividad = new ArrayList<Integer>();
		popularidad = new ArrayList<Integer>();

		betweenesscentrality = new ArrayList<Double>();
		closnesscentrality = new ArrayList<Double>();
		eigencentralityList = new ArrayList<Double>();
		modularityList = new ArrayList<Integer>();
		
		// harmonicclosnesscentrality = new ArrayList<Double>();
		// eccentricity = new ArrayList<Double>();

		// Primero: transformo grafo de Alfonso a grafo Gephi
		transformaGrafoGephi(migrafo);

		imgIndicadores = new ArrayList<String>();
		       
		Graph graph = graphModel.getGraphVisible();
		ProgressTicket progress = null;
		Progress.start(progress, graph.getNodeCount());
        		
		graphDistance = new GraphDistance();
		graphDistance.setDirected(true);

		// Calculo los diferentes indicadores y guardo imágenes que crea GEPHI:
		// 1. Betweeness y Closeness
		graphDistance.setNormalized(false);
		graphDistance.execute(graphModel);
		imgPath = graphDistance.getReport();
		imgIndicadores.add(imgPath.substring(imgPath.indexOf("file") + 5, imgPath.indexOf(".png") + 4));
		imgPath = imgPath.substring(imgPath.indexOf("</IMG>"));
		imgIndicadores.add(imgPath.substring(imgPath.indexOf("file") + 5, imgPath.indexOf(".png") + 4));
		
		// 2. Eigenvector
		eigen = new EigenvectorCentrality();
		eigen.setNumRuns(100);
		eigen.execute(graphModel);
		imgPath = eigen.getReport();
		imgIndicadores.add(imgPath.substring(imgPath.indexOf("file") + 5, imgPath.indexOf(".png") + 4));
		
		// 3. Modularidad
		modular = new Modularity();
		modular.setResolution(1.0);
		modular.setRandom(true);
		modular.setInitialModularityClassIndex(0);
		modular.setUseWeight(true);
		modular.execute(graphModel);
		imgPath = modular.getReport();
		imgIndicadores.add(imgPath.substring(imgPath.indexOf("file") + 5, imgPath.indexOf(".png") + 4));

		for (int i = 0; i < imgIndicadores.size(); i++) {
			try {
				Path source = Path.of((imgIndicadores.get(i)));
				Path dest = Path.of("./target/classes/static/images");
				Files.move(source, dest.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				Exceptions.printStackTrace(e);
			}
		} 
		
		/* Recorrer un grafo
		 * graph = graphModel.getGraphVisible();
        NodeIterable nodesIterableU = graph.getNodes();
        for (Node s : nodesIterableU) {
        	System.out.println("nodo: " + s.getId());
        }
        
        EdgeIterable edgesIterable = graph.getEdges();
        for (Edge e : edgesIterable) {
        	System.out.println("arista: " + e.getId() + " origen: " + e.getSource().getId() + " destino: " + e.getTarget().getId() + " peso: " + e.getWeight());
        }
        */
		
		// Almaceno en listas individuales los 4 indicadores
        setBetweenesscentrality(graphModel.getNodeTable().getColumn(GraphDistance.BETWEENNESS));
		setClosnesscentrality(graphModel.getNodeTable().getColumn(GraphDistance.CLOSENESS));
		setEigenvector(graphModel.getNodeTable().getColumn(EigenvectorCentrality.EIGENVECTOR));
		setModularity(graphModel.getNodeTable().getColumn(Modularity.MODULARITY_CLASS));     
        
		// setHarmonicclosnesscentrality(graphModel.getNodeTable().getColumn(GraphDistance.HARMONIC_CLOSENESS));
		// setEccentricity(graphModel.getNodeTable().getColumn(GraphDistance.ECCENTRICITY));
		
	}

	
	@Override
	public List<Integer> getIniciativa() {
		return iniciativa;
	}
	public void setIniciativa(List<Integer> iniciativa) {
		this.iniciativa = iniciativa;
	}
	
	@Override
	public List<Integer> getActividad() {
		return actividad;
	}
	public void setActividad(List<Integer> actividad) {
		this.actividad = actividad;
	}
	
	@Override
	public List<Integer> getPopularidad() {
		return popularidad;
	}
	public void setPopularidad(List<Integer> popularidad) {
		this.popularidad = popularidad;
	}
	
	@Override
	public List<Double> getBetweenesscentrality() {
		return betweenesscentrality;
	}
	public void setBetweenesscentrality(Column betweenesscentrality) {
		for (Node n : directedGraph.getNodes()) {
			this.betweenesscentrality.add((Double) n.getAttribute("betweenesscentrality"));
		}
	}
	
	@Override
	public List<Double> getClosnesscentrality() {
		return closnesscentrality;
	}
	public void setClosnesscentrality(Column closnesscentrality) {
		for (Node n : directedGraph.getNodes()) {
			this.closnesscentrality.add((Double) n.getAttribute("closnesscentrality"));
		}
	}
	
	@Override
	public List<Double> getEigenvector() {
		return eigencentralityList;
	}
	public void setEigenvector(Column eigenvector) {
		for (Node n : directedGraph.getNodes()) {
			this.eigencentralityList.add((Double) n.getAttribute("eigencentrality"));
		}
	}
	
	@Override
	public List<Integer> getModularity() {
		return modularityList;
	}	
	public void setModularity(Column modularity) {
		for (Node n : directedGraph.getNodes()) {
			this.modularityList.add((Integer) n.getAttribute("modularity_class"));
		}
	}
	
	@Override
	public List<String> getImagenes() {
		// TODO Auto-generated method stub
		return nombresImagenes;
	}
	public void setImagenes() {
		for (Node n : directedGraph.getNodes()) {
			this.nombresImagenes.add((String) n.getAttribute("nombresImagenes"));
		}
	}
	
	@Override
	public GraphDistance getGraphDistance() {
		return graphDistance;
	}
	public void setGraphDistance(GraphDistance graphDistance) {
		this.graphDistance = graphDistance;
	}
	
	@Override
	public GraphModel getGraphModel() {
		return graphModel;
	}
	public void setGraphModel(GraphModel graphModel) {
		this.graphModel = graphModel;
	}

	@Override
	public Workspace getWorkspace() {
		return workspace;
	}
	public void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}	

	@Override
	public ProjectController getPc() {
		return pc;
	}
	public void setPc(ProjectController pc) {
		this.pc = pc;
	}

	@Override
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}



	/*
	 * public List<Double> getHarmonicclosnesscentrality() { return
	 * harmonicclosnesscentrality; }
	 * 
	 * public void setHarmonicclosnesscentrality(Column harmonicclosnesscentrality)
	 * { for (Node n : directedGraph.getNodes()) {
	 * this.harmonicclosnesscentrality.add(
	 * (Double)n.getAttribute("harmonicclosnesscentrality")); } }
	 */

	/*
	 * public List<Double> getEccentricity() { return eccentricity; }
	 * 
	 * public void setEccentricity(Column eccentricity) { for (Node n :
	 * directedGraph.getNodes()) { this.eccentricity.add(
	 * (Double)n.getAttribute("eccentricity")); } }
	 */



	private void transformaGrafoGephi(Grafo migrafo) throws IOException {
		// Inicia un proyecto y un espacio de trabajo
		pc = Lookup.getDefault().lookup(ProjectController.class);
		pc.newProject();
		workspace = pc.getCurrentWorkspace();

		// Crea un modelo grafo - existe porque tenemos el workspace
		graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel(/* workspace */);
		
		// Crea un grafo dirigido
		directedGraph = graphModel.getDirectedGraph();
        
		// Añade columnas para atributos de una persona (NODO)
		//// graphModel.getNodeTable().addColumn("idUsuario", Integer.class);
		// graphModel.getNodeTable().addColumn("apellido", String.class);
		// graphModel.getNodeTable().addColumn("nombre", String.class);
		// graphModel.getNodeTable().addColumn("email", String.class);
		//// graphModel.getNodeTable().addColumn("fecha", String.class);

		// Añade columnas para atributos de un mensaje (ARISTA)
		//// graphModel.getEdgeTable().addColumn("foro", Integer.class);
		//// graphModel.getEdgeTable().addColumn("asignatura", Integer.class);
		//// graphModel.getEdgeTable().addColumn("numcaracteres", Integer.class);
		//// graphModel.getEdgeTable().addColumn("fechaEnvio", String.class);

		// Recorre los nodos de migrafo y los añade al nuevo grafo de gephi
		for (Persona nodo : migrafo.getNodos()) {
			n = graphModel.factory().newNode(nodo.getId().toString());
			//n.setColor(Color.BLUE);
			n.setSize(15);
			n.setLabel(nodo.getId().toString());
			// VERY IMPORTANT: initialize with random position. Otherwise, if all nodes get
			// x=0,y=0 coordinates, layouts won't work (known bug)
			float x = (float) (((0.01 + Math.random()) * 1000) - 500);
			float y = (float) (((0.01 + Math.random()) * 1000) - 500);
			n.setPosition(x, y);
			directedGraph.addNode(n);

			//// n.setAttribute("idUsuario", nodo.getId());
			// n.setAttribute("apellido", nodo.getApellidos());
			// n.setAttribute("nombre", nodo.getNombre());
			// n.setAttribute("email", nodo.getEmail());
			//// n.setAttribute("fecha", nodo.getFechaActualizacion().toString());
			iniciativa.add(0);
			actividad.add(0);
			popularidad.add(0);
		}

		// Recorro todos los mensajes de la BBDD, porque los mensajes iniciativa no
		// están en el grafo. Si hay uno sin idMensajePadre, incremento en 1 el valor
		// del usuario
		for (Mensaje mensaje : servicioMensaje.findAll()) {
			// Esta variable la uso para posicionarme en el List de iniciativa
			int contador;
			if (mensaje.getIdMensajePadre() == null) {
				contador = 0;
				// Busco la posición en el grafo por el id (usuario) que inicia
				for (Persona nodo : migrafo.getNodos()) {
					if (mensaje.getIdPersona().equals(nodo.getId()))
						iniciativa.set(contador, iniciativa.get(contador) + 1);
					contador = contador + 1;
				}
			}
		}

		// Recorre las aristas de migrafo y las añade al nuevo grafo de gephi
		for (Arista arista : migrafo.getAristas()) {
			int contador;
			e = graphModel.factory().newEdge(directedGraph.getNode(arista.getSource().toString()),
					directedGraph.getNode(arista.getTarget().toString()));
			directedGraph.addEdge(e);
			
			//// e.setAttribute("foro", arista.getForo());
			//// e.setAttribute("asignatura", arista.getAsignatura());
			//// e.setAttribute("numcaracteres", arista.getNumcaracteres());
			//// e.setAttribute("fechaEnvio", arista.getFechaEnvio());
			contador = 0;
			// Recorre los nodos de migrafo
			for (Persona nodo : migrafo.getNodos()) {
				// Para buscar la posición del nodo que escribe mensaje e incrementar su
				// actividad
				if (nodo.getId().equals(arista.getSource())) {
					actividad.set(contador, actividad.get(contador) + 1);
				}
				// Para buscar la posición del nodo que escribe mensaje e incrementar su
				// actividad
				if (nodo.getId().equals(arista.getTarget())) {
					popularidad.set(contador, popularidad.get(contador) + 1);
				}
				contador = contador + 1;
			}
		}   
        
		
		// Preview
		previewController = Lookup.getDefault().lookup(PreviewController.class);
		// PreviewModel model =
		// Lookup.getDefault().lookup(PreviewController.class).getModel();
		PreviewModel model = previewController.getModel();
		
		model.getProperties().putValue(PreviewProperty.NODE_LABEL_PROPORTIONAL_SIZE, Boolean.TRUE);
		model.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
		model.getProperties().putValue(PreviewProperty.SHOW_EDGE_LABELS, Boolean.TRUE);
		model.getProperties().putValue(PreviewProperty.EDGE_RESCALE_WEIGHT, Boolean.TRUE);
		model.getProperties().putValue(PreviewProperty.ARROW_SIZE, 5.0f);
		model.getProperties().putValue(PreviewProperty.NODE_OPACITY, 20.0f);
		
		previewController.refreshPreview();

		// New Processing target, get the PApplet
		G2DTarget target = (G2DTarget) previewController.getRenderTarget(RenderTarget.G2D_TARGET);
		final PreviewSketch previewSketch = new PreviewSketch(target);

		// Add the applet to a JFrame and display
		JFrame frame = new JFrame("Test Preview");
		frame.setLayout(new BorderLayout());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(previewSketch, BorderLayout.CENTER);
		frame.setSize(1024, 1024);

		// Export full graph, save it to test.png and show it in an image frame
		ec = Lookup.getDefault().lookup(ExportController.class);
		ec.exportFile(new File("./target/classes/static/images/grafo.png"));
		PNGExporter exporter = (PNGExporter) ec.getExporter("png");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ec.exportStream(baos, exporter);
		byte[] png = baos.toByteArray();
		image = ImageIO.read(new ByteArrayInputStream(png));

		// Espera a que el frame sea visible antes de dibujar o se verá extraño
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				previewSketch.resetZoom();
			}
		});
		// frame.setVisible(true);
	}


}
