package jsv.unededucaanalisis.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jsv.unededucaanalisis.modelo.Persona;
import jsv.unededucaanalisis.servicios.PersonaService;

@Controller
public class PersonaController {
	
	@Autowired
	private PersonaService servicio;

	// Genera la lista de personas
	@GetMapping({"/persona","persona/list"})
	public String listado(Model model)
	{
		
		model.addAttribute("listaPersonas",servicio.findAll());
		return "listPersona";
	}
	
	// Prepara el formulario para la gestión de una nueva persona
	@GetMapping("/persona/new")
	public String nuevaPersonaForm(Model model)
	{
		model.addAttribute("personaForm",new Persona());
		return "formPersona";
	}	
	
	// Envía la información cumplimentada en el formulario
	@PostMapping("/persona/new/submit")
	public String nuevaPersonaSubmit(@ModelAttribute("personaForm") Persona nuevaPersona)
	{
		
		servicio.add(nuevaPersona);
		return "redirect:/persona/list";
	}	
	
	// Prepara el formulario para la gestión de una nueva persona
	@GetMapping("/persona/edit/{id}")
	public String editarPersonaForm(@PathVariable Integer id, Model model)
	{
		//Busco en base a id
		Persona persona = servicio.findById(id);
		// Si encuentro agrego al modelo y redirigo al formulario
		if (persona != null)
		{
			model.addAttribute("personaForm", persona);
			return "formPersona";
		}
		// si no lo encuentro, redirigo a nueva persona
		else
		{
			return "redirect:/persona/new";
		}
	}	
	
	// Envía la información cumplimentada en el formulario
	@PostMapping("/persona/edit/submit")
	public String editarPersonaSubmit(@ModelAttribute("personaForm") Persona nuevaPersona)
	{
		
		servicio.edit(nuevaPersona);
		return "redirect:/persona/list";
	}	
	
	// Elimina una persona
	@GetMapping("/persona/delete/{id}")
	public String eliminarPersona(@PathVariable Integer id, Model model)
	{
		servicio.delete(id);
		return "redirect:/persona/list";
	}	

}
