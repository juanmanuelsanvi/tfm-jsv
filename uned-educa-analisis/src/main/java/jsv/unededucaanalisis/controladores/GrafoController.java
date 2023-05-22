package jsv.unededucaanalisis.controladores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jsv.unededucaanalisis.modelo.Arista;
import jsv.unededucaanalisis.modelo.Grafo;
import jsv.unededucaanalisis.modelo.Persona;
import jsv.unededucaanalisis.servicios.AristaService;
import jsv.unededucaanalisis.servicios.GrafoService;
import jsv.unededucaanalisis.servicios.PersonaService;

@Controller
public class GrafoController 
{
	
	@Autowired
	private GrafoService servicioGrafo;	
	@Autowired
	private PersonaService servicioPersona;
	@Autowired
	private AristaService servicioArista;	
	@Value("${tfmjsv.data.ficherografo}")
	private String ficheroGrafo;	
	@Value("${tfmjsv.data.rutaficheros}")
	private String rutaFicheros;	

	// Genera la lista de datos
	@GetMapping({"/generargrafo"})
	public String generarGrafo(Model model) throws IOException
	{
		List<Persona> listaNodos; 
		List<Arista> listaAristas;
		List<Double> listaBetweenessCentrality; 
		List<Double> listaClosnessCentrality;
		List<Double> listaHarmonicClosnessCentrality;
		List<Double> listaEccentricity;
		
		Grafo miGrafo=servicioGrafo.generarGrafo(servicioPersona, servicioArista);	
		servicioGrafo.generarFichero(miGrafo,"kk");
		
		// Llamada a la creación de indicadores jsv: 16-06-2023
		servicioGrafo.generarIndicadores(miGrafo);
		
		Integer nodosGenerados = miGrafo.getNodos().size();
		Integer aristasGeneradas = miGrafo.getAristas().size();
		String rutaFicheroGrafo = "prueba.gml";
		
		listaNodos = miGrafo.getNodos();
		listaAristas = miGrafo.getAristas();
		listaBetweenessCentrality = servicioGrafo.getBetweenesscentrality();
		listaClosnessCentrality = servicioGrafo.getClosnesscentrality();
		listaHarmonicClosnessCentrality = servicioGrafo.getHarmonicclosnesscentrality();
		listaEccentricity = servicioGrafo.getEccentricity();
		
		model.addAttribute("nodos",nodosGenerados);
		model.addAttribute("aristas",aristasGeneradas);
		model.addAttribute("fichero",rutaFicheroGrafo);	
		// Crea un nuevo atributo con matriz de adyascencia jsv: 17-05-2023
		model.addAttribute("listaNodos", listaNodos);
		model.addAttribute("betweenness", listaBetweenessCentrality);
		model.addAttribute("closeness", listaClosnessCentrality);
		model.addAttribute("harmonic", listaHarmonicClosnessCentrality);
		model.addAttribute("eccentricity", listaEccentricity);
		
		return "grafoGenerado";
	}
	
	@GetMapping({"/descargagrafo"})
	  public void descargaGrafo( HttpServletRequest request, 
	                   HttpServletResponse response
	                   
	                   ) 
	  {
	    Path file = Paths.get(rutaFicheros + ficheroGrafo);
	    if (Files.exists(file)) 
	    {
	      response.setContentType("application/octet-stream");
	      response.addHeader("Content-Disposition", "attachment; filename="+ficheroGrafo);
	      try
	      {
	        Files.copy(file, response.getOutputStream());
	        response.getOutputStream().flush();
	      } 
	      catch (IOException ex) {
	        ex.printStackTrace();
	      }
	    }
	  }	


}

