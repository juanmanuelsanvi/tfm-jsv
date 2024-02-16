package jsv.unededucaanalisis.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jsv.unededucaanalisis.modelo.Convocatoria;
import jsv.unededucaanalisis.servicios.ConvocatoriaService;

@Controller
public class ConvocatoriaController 
{
	
	@Autowired
	private ConvocatoriaService servicio;

	// Genera la lista de datos
	@GetMapping({"/convocatoria","convocatoria/list"})
	public String listado(Model model)
	{
		
		model.addAttribute("listaConvocatorias",servicio.findAll());
		return "listConvocatoria";
	}
	
	// Prepara el formulario para la gestión de una nueva convocatoria
	@GetMapping("/convocatoria/new")
	public String nuevaConvocatoriaForm(Model model)
	{
		
		model.addAttribute("convocatoriaForm",new Convocatoria());
		return "formConvocatoria";
	}	
	
	// Se añade la información cumplimentada en el formulario
	@PostMapping("/convocatoria/new/sunbmit")
	public String nuevaConvocatoriaSubmit(@ModelAttribute("convocatoriaForm") Convocatoria nuevaConvocatoria)
	{
		
		servicio.add(nuevaConvocatoria);
		return "redirect:/convocatoria/list";
	}	
	
	// Prepara el formulario para la gestión de una nueva convocatoria
	@GetMapping("/convocatoria/edit/{id}")
	public String editarConvocatoriaForm(@PathVariable Integer id, Model model)
	{
		//Busco en base a id
		Convocatoria convocatoria = servicio.findById(id);
		// Si encuentro agrego al modelo y redirigo al formulario
		if (convocatoria != null)
		{
			model.addAttribute("convocatoriaForm", convocatoria);
			return "formConvocatoria";
		}
		// si no lo encuentro, redirigo
		else
		{
			return "redirect:/convocatoria/new";
		}
	}	
	
	// Envía la información cumplimentada en el formulario
	@PostMapping("/convocatoria/edit/submit")
	public String editarConvocatoriaSubmit(@ModelAttribute("convocatoriaForm") Convocatoria nuevaConvocatoria)
	{
		
		servicio.edit(nuevaConvocatoria);
		return "redirect:/convocatoria/list";
	}	
	
	// Eliminar
	@GetMapping("/convocatoria/delete/{id}")
	public String eliminarConvocatoria(@PathVariable Integer id, Model model)
	{
		servicio.delete(id);
		return "redirect:/convocatoria/list";
	}	

}
