package jsv.unededucaanalisis.controladores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import jsv.unededucaanalisis.modelo.Convocatoria;
import jsv.unededucaanalisis.modelo.InfoProcesoForo;
import jsv.unededucaanalisis.modelo.Persona;
import jsv.unededucaanalisis.servicios.ConvocatoriaService;
import jsv.unededucaanalisis.servicios.ForoAlumnosService;
import jsv.unededucaanalisis.servicios.PersonaService;

@Controller
public class ForoAlumnosController 
{
	
	@Autowired
	private ForoAlumnosService servicioForoAlumnos;

	@Autowired
	private PersonaService servicioPersona;
	
	@Autowired
	private ConvocatoriaService servicioConvocatoria;	

	@Value("${tfmjsv.data.rutaficheros}") 
	private String rutaFicheros;	
	
	// Genera la página para la selección del fichero
	@GetMapping({"/importarForo"})
	public String importarForo(Model model)
	{
		
		//Listado de convocatorias

		List<Convocatoria> convocatorias = servicioConvocatoria.findAll();
		model.addAttribute("convocatorias", convocatorias);
		
		List<Persona> personas = servicioPersona.findAll();
		model.addAttribute("personas", personas);						
		
		return "formImportarForo";
	}
	
	@PostMapping("/importar") 
	public String importarFichero(Model model, @RequestParam("fichero") MultipartFile file, @RequestParam("convocatoria") Integer id, @RequestParam("docente") Integer idPersona) throws IOException 
	{
		
		if (!file.isEmpty()) {
	        try {
				//TODO Desarrollar la recogida de convocatoria y profesor 
				StringBuilder fileNames = new StringBuilder();			
		        // Se contruye la ruta y nombre del fichero
		        Path fileNameAndPath = Paths.get(rutaFicheros, file.getOriginalFilename());
		        fileNames.append(file.getOriginalFilename());
		        Files.write(fileNameAndPath, file.getBytes());
		        model.addAttribute("mensaje", "Fichero subido: " + fileNames.toString()); 
		        InfoProcesoForo infoProceso=servicioForoAlumnos.procesaDatos(servicioConvocatoria.findById(id).getConvocatoria(), servicioPersona.findById(idPersona), fileNames.toString());	
			
				model.addAttribute("numAsignaturas",infoProceso.getNumAsignaturas());
				model.addAttribute("numForos",infoProceso.getNumForos());
				model.addAttribute("numMensajes",infoProceso.getNumMensajes());
				model.addAttribute("numPersonas",infoProceso.getNumPersonas());
				model.addAttribute("numRegistrosProcesados",infoProceso.getNumRegistrosProcesados());	
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
        }    
        return "resultadoImportacion";
    }	
	


}
