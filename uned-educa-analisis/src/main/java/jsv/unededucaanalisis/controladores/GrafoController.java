package jsv.unededucaanalisis.controladores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

import amm.unededucaanalisis.servicios.GrafoService;
import amm.unededucaanalisis.servicios.PersonaService;
import jsv.unededucaanalisis.modelo.Grafo;
import amm.unededucaanalisis.servicios.AristaService;

@Controller
public class GrafoController 
{
	
	@Autowired
	private GrafoService servicioGrafo;	
	@Autowired
	private PersonaService servicioPersona;
	@Autowired
	private AristaService servicioArista;	
	@Value("${pfgamm.data.ficherografo}")
	private String ficheroGrafo;	
	@Value("${pfgamm.data.rutaficheros}")
	private String rutaFicheros;	

	// Genera la lista de datos
	@GetMapping({"/generargrafo"})
	public String generarGrafo(Model model) throws IOException
	{
	
		Grafo miGrafo=servicioGrafo.generarGrafo(servicioPersona, servicioArista);	
		servicioGrafo.generarFichero(miGrafo,"kk");
		
		Integer nodosGenerados = miGrafo.getNodos().size();
		Integer aristasGeneradas = miGrafo.getAristas().size();
		String rutaFicheroGrafo = "prueba.gml";
		
		model.addAttribute("nodos",nodosGenerados);
		model.addAttribute("aristas",aristasGeneradas);
		model.addAttribute("fichero",rutaFicheroGrafo);		
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

