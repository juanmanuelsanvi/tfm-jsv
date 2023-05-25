package jsv.unededucaanalisis.controladores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		//List<Double> listaHarmonicClosnessCentrality;
		//List<Double> listaEccentricity;

		Integer[][] matrizAdyacencia = null;
		List<Indicador> lista;
		Indicador indicador;		
		Integer fila, col, source, target;
		
		lista = new ArrayList<Indicador>();		
		
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
		//listaHarmonicClosnessCentrality = servicioGrafo.getHarmonicclosnesscentrality();
		//listaEccentricity = servicioGrafo.getEccentricity();
		fila = col = 0;
	    matrizAdyacencia = new Integer[miGrafo.getNodos().size()+1][miGrafo.getNodos().size()+1];
	    
	    for(int i = 0; i < listaNodos.size(); i++) 
	    	System.out.println("nodo " + i + ": " + listaNodos.get(i).getId());
	    
		for(int i = 0; i < listaNodos.size()+1; i++) 
		{			
			for(int j = 0; j < listaNodos.size()+1; j++) 
			{
				if(i==0) {
					if(j==0)
						matrizAdyacencia[i][j] = null;
					// Para rellenar la primera fila con los id de cada nodo
					else matrizAdyacencia[i][j] = listaNodos.get(j-1).getId();
				}
				else
					// Para rellenar la primera columna con los id de cada nodo
					if(j==0)
						matrizAdyacencia[i][j] = listaNodos.get(i-1).getId();
					else matrizAdyacencia[i][j] = 0;
					
			}
		}
		
		for(int i = 0; i < listaAristas.size(); i++) 
		{			
			source = listaAristas.get(i).getSource();
			System.out.println("source: " + source);
			target = listaAristas.get(i).getTarget();
			System.out.println("target: " + target);
			fila = 0;
			col = 0;
			for(int j = 1; j < listaNodos.size()+1; j++)
				if(source == matrizAdyacencia[j][0]) {
					fila = j ;
					System.out.println("Fila: " + fila);
				}

			for(int j = 1; j < listaNodos.size()+1; j++)
				if(target == matrizAdyacencia[0][j]) {
					col = j;
					System.out.println("Columna: " + col);
				}
			matrizAdyacencia[fila][col] = matrizAdyacencia[fila][col] + 1;
		}
			
		for(int i=0; i < listaNodos.size(); i++) {
			indicador = new Indicador();
			indicador.setId(listaNodos.get(i).getId());
			indicador.setBetweenness(listaBetweenessCentrality.get(i));
			indicador.setCloseness(listaClosnessCentrality.get(i));
			lista.add(indicador);
		}
		
		
		model.addAttribute("nodos", nodosGenerados);
		model.addAttribute("aristas", aristasGeneradas);
		model.addAttribute("fichero", rutaFicheroGrafo);	
		
		// Crea un nuevo atributo con matriz de adyascencia jsv: 17-05-2023
		model.addAttribute("matriz", matrizAdyacencia);
		// Crea atributo para los indicadores
		model.addAttribute("listaIndicadores", lista);
	
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

