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
		
		Grafo miGrafo=servicioGrafo.generarGrafo(servicioPersona, servicioArista);	
		servicioGrafo.generarFichero(miGrafo,"kk");
		
		// Llamada a la creación de indicadores jsv: 16-06-2023
		servicioGrafo.generarIndicadores(miGrafo);
		
		Integer nodosGenerados = miGrafo.getNodos().size();
		Integer aristasGeneradas = miGrafo.getAristas().size();
		String rutaFicheroGrafo = "prueba.gml";
		
		listaNodos = miGrafo.getNodos();
		listaAristas = miGrafo.getAristas();
		
		//String [][] matrizAdyascencia = new String[miGrafo.getNodos().size()+1][miGrafo.getNodos().size()+1];
		/*
        // Rellena cabecera horizontal
        for(int i=1; i<  miGrafo.getAristas().size(); i++)
        {
        	matrizAdyascencia[0][i] = miGrafo.getNodos().get(i).toString();
        }	
        // Rellena cabecera vertical
        for(int i=1; i<  miGrafo.getAristas().size(); i++)
        {
        	matrizAdyascencia[i][0] = miGrafo.getNodos().get(i).toString();
        }        
        
		// Inicializa matriz en 0
        for(int i=1; i<  miGrafo.getNodos().size()+1; i++)
        {
            for(int j=1; j<  miGrafo.getNodos().size()+1; j++)
            {
            	matrizAdyascencia[i][j] = "0";
            }            
        }
		*/
		
		model.addAttribute("nodos",nodosGenerados);
		model.addAttribute("aristas",aristasGeneradas);
		model.addAttribute("fichero",rutaFicheroGrafo);	
		// Crea un nuevo atributo con matriz de adyascencia jsv: 17-05-2023
		model.addAttribute("listaNodos",listaNodos);
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

