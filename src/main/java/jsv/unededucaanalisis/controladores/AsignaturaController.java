package jsv.unededucaanalisis.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jsv.unededucaanalisis.modelo.Asignatura;
import jsv.unededucaanalisis.servicios.AsignaturaService;

@Controller
public class AsignaturaController 
{
	
	@Autowired
	private AsignaturaService servicio;

	// Genera la lista de asignaturas
	@GetMapping({"/asignatura","asignatura/list"})
	public String listado(Model model)
	{		
		model.addAttribute("listaAsignatura",servicio.findAll());
		return "listAsignatura";
	}
	
	// Prepara el formulario para la gestión de una nueva asignatura
	@GetMapping("/asignatura/new")
	public String nuevaAsignaturaForm(Model model)
	{
		
		model.addAttribute("asignaturaForm",new Asignatura());
		return "formAsignatura";
	}	
	
	// Envía la información cumplimentada en el formulario
	@PostMapping("/asignatura/new/submit")
	public String nuevaAsignaturaSubmit(@ModelAttribute("asignaturaForm") Asignatura nuevaAsignatura)
	{		
		servicio.add(nuevaAsignatura);
		return "redirect:/asignatura/list";
	}	
	
	// Prepara el formulario para la gestión de una nueva asignatura
	@GetMapping("/asignatura/edit/{id}")
	public String editarAsignaturaForm(@PathVariable Integer id, Model model)
	{
		//Busco en base a id
		Asignatura asignatura = servicio.findById(id);
		// Si encuentro agrego al modelo y redirigo al formulario
		if (asignatura != null)
		{
			model.addAttribute("asignaturaForm", asignatura);
			return "formAsignatura";
		}
		// si no lo encuentro, redirigo a nueva asignatura
		else
		{
			return "redirect:/asignatura/new";
		}
	}	
	
	// Envía la información cumplimentada en el formulario
	@PostMapping("/asignatura/edit/submit")
	public String editarEmpleadoSubmit(@ModelAttribute("asignaturaForm") Asignatura nuevaAsignatura)
	{
		
		servicio.edit(nuevaAsignatura);
		return "redirect:/asignatura/list";
	}	
	
	// Elimina una asignatura
	@GetMapping("/asignatura/delete/{id}")
	public String eliminarAsignatura(@PathVariable Integer id, Model model)
	{
		servicio.delete(id);
		return "redirect:/asignatura/list";
	}	

}
