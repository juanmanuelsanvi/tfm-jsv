package jsv.unededucaanalisis.controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jsv.unededucaanalisis.modelo.Arista;
import jsv.unededucaanalisis.modelo.Grafo;
import jsv.unededucaanalisis.modelo.Persona;
import jsv.unededucaanalisis.servicios.AristaService;
import jsv.unededucaanalisis.servicios.GrafoService;
import jsv.unededucaanalisis.servicios.PersonaService;
import jsv.unededucaanalisis.modelo.Indicador;

@Controller
public class GrafoController {

	@Autowired
	private PersonaService servicioPersona;
	@Autowired
	private AristaService servicioArista;
	@Autowired
	private GrafoService servicioGrafo;
 
	private Grafo miGrafo;
	
	@Value("${tfmjsv.data.ficherografo}")
	private String ficheroGrafo;
	// @Value("${tfmjsv.data.rutaficheros}")
	// private String rutaFicheros;

	// Genera la lista de datos
	@GetMapping({ "/generargrafo" })
	public String generaGrafo(Model model) throws IOException {
		List<Persona> listaNodos;
		List<Arista> listaAristas;
		List<Integer> listaIniciativa;
		List<Integer> listaActividad;
		List<Integer> listaPopularidad;
		List<Double> listaBetweenessCentrality;
		List<Double> listaClosnessCentrality;
		// List<Double> listaHarmonicClosnessCentrality;
		// List<Double> listaEccentricity;
		List<Double> listaEigenvectorCentrality;
		List<Integer> listaModularidad;
		Integer[][] matrizAdyacencia = null;
		List<Indicador> lista;
		List<Integer> usuarios;

		// Contiene las rutas y nombre de todas la imágenes generadas por Gephi
		List<String> listaImg;

		Indicador indicador;
		Integer fila, col, source, target;

		lista = new ArrayList<Indicador>();
		usuarios = new ArrayList<Integer>();
		listaImg = new ArrayList<String>();

		miGrafo = servicioGrafo.generarGrafo(servicioPersona, servicioArista);

		// Llamada a la creación de indicadores jsv: 16-06-2023
		servicioGrafo.generarIndicadores(miGrafo);

		Integer nodosGenerados = miGrafo.getNodos().size();
		Integer aristasGeneradas = miGrafo.getAristas().size();

		listaNodos = miGrafo.getNodos();
		listaAristas = miGrafo.getAristas();

		listaIniciativa = servicioGrafo.getIniciativa();
		listaActividad = servicioGrafo.getActividad();
		listaPopularidad = servicioGrafo.getPopularidad();
		listaBetweenessCentrality = servicioGrafo.getBetweenesscentrality();
		listaClosnessCentrality = servicioGrafo.getClosnesscentrality();
		listaEigenvectorCentrality = servicioGrafo.getEigenvector();
		listaModularidad = servicioGrafo.getModularity();
		
		// listaHarmonicClosnessCentrality =
		// servicioGrafo.getHarmonicclosnesscentrality();
		// listaEccentricity = servicioGrafo.getEccentricity();
		fila = col = 0;
		matrizAdyacencia = new Integer[miGrafo.getNodos().size() + 1][miGrafo.getNodos().size() + 1];
		for (int i = 0; i < listaNodos.size() + 1; i++) {
			for (int j = 0; j < listaNodos.size() + 1; j++) {
				if (i == 0) {
					if (j == 0)
						matrizAdyacencia[i][j] = null;
					// Para rellenar la primera fila con los id de cada nodo
					else
						matrizAdyacencia[i][j] = listaNodos.get(j - 1).getId();
				} else
				// Para rellenar la primera columna con los id de cada nodo
				if (j == 0)
					matrizAdyacencia[i][j] = listaNodos.get(i - 1).getId();
				else
					matrizAdyacencia[i][j] = 0;
			}
		}
		for (int i = 0; i < listaAristas.size(); i++) {
			source = listaAristas.get(i).getSource();
			target = listaAristas.get(i).getTarget();
			fila = 0;
			col = 0;
			for (int j = 1; j < listaNodos.size() + 1; j++) {
				// Defino esta variable intermedia "value" porque en una ocasión no me tomó por
				// igual un
				// source y un valor de matriz que sí que eran iguales, de esta forma lo subsané
				int value = matrizAdyacencia[j][0];
				if (source == value) {
					fila = j;
				}
			}
			for (int j = 1; j < listaNodos.size() + 1; j++) {
				int value = matrizAdyacencia[j][0];
				if (target == value) {
					col = j;
				}
			}
			matrizAdyacencia[fila][col] = matrizAdyacencia[fila][col] + 1;
		}
		for (int i = 0; i < listaNodos.size(); i++) {
			indicador = new Indicador();
			indicador.setIniciativa(listaIniciativa.get(i));
			indicador.setActividad(listaActividad.get(i));
			indicador.setPopularidad(listaPopularidad.get(i));
			indicador.setId(listaNodos.get(i).getId());
			indicador.setBetweenness(listaBetweenessCentrality.get(i));
			indicador.setCloseness(listaClosnessCentrality.get(i));
			indicador.setEigenvector(listaEigenvectorCentrality.get(i));
			indicador.setModularidad(listaModularidad.get(i));
		
			// indicador.setHarmonic(listaHarmonicClosnessCentrality.get(i));
			// indicador.setEccentricity(listaEccentricity.get(i));
			lista.add(indicador);
			usuarios.add(listaNodos.get(i).getId());
		}
		listaImg = servicioGrafo.getImagenes();

		model.addAttribute("nodos", nodosGenerados);
		model.addAttribute("aristas", aristasGeneradas);
		model.addAttribute("fichero", ficheroGrafo);

		// Crea un nuevo atributo con matriz de adyascencia jsv: 17-05-2023
		model.addAttribute("matriz", matrizAdyacencia);
		// Crea atributo para los indicadores
		model.addAttribute("listaIndicadores", lista);
		// Crea nuevo atributo para las imágenes generadas de indicadores: jsv
		// 12-07-2023
		model.addAttribute("listaImagenes", listaImg);
		// Crea nuevo atributo para enviar lista de id de usuario a la página elegir
		model.addAttribute("listaUsuarios", usuarios);

		model.addAttribute("image", servicioGrafo.getImage());

		return "grafoGenerado";
	}

	@GetMapping({ "/descargagrafo" })
	public void descargaGrafo(HttpServletRequest request, HttpServletResponse response

	) throws IOException {
		servicioGrafo.generarFichero(miGrafo, "gml");
		/*String home = System.getProperty("user.home");
		// Se comenta la parte de ruta de fichero ya que no hace falta para descargar el fichero
		Path file = Paths.get(home + "/Downloads/" + ficheroGrafo);
		if (Files.exists(file)) {
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment; filename=" + ficheroGrafo);
			try {
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}*/
	}

}
