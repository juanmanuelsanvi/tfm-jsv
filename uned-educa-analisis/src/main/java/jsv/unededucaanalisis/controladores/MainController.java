package jsv.unededucaanalisis.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController 
{
	// GetMapping Se invoca cuandose produce una petición get
	@GetMapping({"/","index"}) // Ruta que está escuchando. Ruta Raíz
	public String index(Model model)
	{
		model.addAttribute("mensaje","Página pendiente de implementar"); //Mensaje enviado al modelo y que es recogido por la vista
		//Interesante el uso de @RequestParam para obtener los parámetros de la url, y pasarlas al modelo.
		//Interesante el uso de @PatVariable
		return "index"; // Se devuelve la ruta de la plantilla sin html en templates
	
	}

}
