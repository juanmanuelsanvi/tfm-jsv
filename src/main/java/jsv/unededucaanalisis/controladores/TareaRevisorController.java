package jsv.unededucaanalisis.controladores;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jsv.unededucaanalisis.modelo.TareaRevisor;
import jsv.unededucaanalisis.servicios.PersonaService;
import jsv.unededucaanalisis.servicios.TareaRevisorService;
import org.springframework.beans.BeanUtils;

@Controller
public class TareaRevisorController {

	@Autowired
	private TareaRevisorService servicio;
	@Autowired
	private PersonaService servicioPersona;
	
	// Genera la lista de tareas de revisor
	@GetMapping({"/tareaRevisor","tareaRevisor/list"})
	public String listado(Model model)
	{					
		model.addAttribute("listaTareaRevisor",servicio.findAll());		
		return "listTareaRevisor";
	}
	
	// Prepara el formulario para la gestión de una nueva tareaRevisor
	@GetMapping("/tareaRevisor/new")
	public String nuevoRevisorForm(Model model)
	{
		List<String> listaFunciones = new ArrayList<>();
		
		listaFunciones.add("Lineal");
		listaFunciones.add("Cuadrática");
		listaFunciones.add("Raíz Cuadrada");

		model.addAttribute("funcion", listaFunciones);		
		model.addAttribute("tareaRevisorForm",new TareaRevisor());

		model.addAttribute("numEstud", (servicioPersona.findAll()).size()-1);
		
		return "formTareaRevisor";
	}	
	
	// Envía la información cumplimentada en el formulario
	@PostMapping("/tareaRevisor/new/submit")
	public String nuevoRevisorSubmit(@ModelAttribute("tareaRevisorForm") TareaRevisor nuevaTareaRevisor)
	{	
		if( compruebaOrdenIndicadores(nuevaTareaRevisor)) {
			corrigeTablas(nuevaTareaRevisor);
			servicio.add(nuevaTareaRevisor);
			return "redirect:/tareaRevisor/list";
		}
		else
		{
			servicio.add(nuevaTareaRevisor);			
			return "redirect:/tareaRevisor/modifyError/"+nuevaTareaRevisor.getId();
		}	
	}	
	
	// Antes de almacenar valores de orden de indicadores, comprueba que no haya valores repeditos
	private boolean compruebaOrdenIndicadores(TareaRevisor nuevaTareaRevisor) {
		boolean ordenOk;
		List<Integer> Lista = new ArrayList<>(); 
		
		ordenOk = true;
		Lista = indicadoresLista(nuevaTareaRevisor);
		
		for(int indice = 0;indice<Lista.size()-1;indice++)	{
			for(int indice2 = indice+1;indice2<Lista.size();indice2++)	{
				if(Lista.get(indice) == Lista.get(indice2)) {
					ordenOk = false;
					break;	
				}	
			}
		}
		return ordenOk;
	}
	
	// Antes de almacenar valores recalculará los valores de las tablas autocompletados
	private void corrigeTablas(TareaRevisor nuevaTareaRevisor) {
		//Corrige datos Tabla 1
		nuevaTareaRevisor.setProbLowHigh(1 - nuevaTareaRevisor.getProbLowLow() - nuevaTareaRevisor.getProbLowMedium());
		nuevaTareaRevisor.setProbMediumHigh(1 - nuevaTareaRevisor.getProbMediumLow() - nuevaTareaRevisor.getProbMediumMedium());
		nuevaTareaRevisor.setProbHighHigh(1 - nuevaTareaRevisor.getProbHighLow() - nuevaTareaRevisor.getProbHighMedium());
		//Corrige datos Tabla 2
		nuevaTareaRevisor.setUtiLowHigh(1 - nuevaTareaRevisor.getUtiLowLow() - nuevaTareaRevisor.getUtiLowMedium());
		nuevaTareaRevisor.setUtiMediumHigh(1 - nuevaTareaRevisor.getUtiMediumLow() - nuevaTareaRevisor.getUtiMediumMedium());
		nuevaTareaRevisor.setUtiHighHigh(1 - nuevaTareaRevisor.getUtiHighLow() - nuevaTareaRevisor.getUtiHighMedium());
	}
	
	// Añade los valores del orden seleccionados por el usuario a la lista de indicadores
	private List<Integer> indicadoresLista(TareaRevisor nuevaTareaRevisor){
		List<Integer> indicadoresLista = new ArrayList<>();
		indicadoresLista.add(nuevaTareaRevisor.getActivityCompatibility() );
		indicadoresLista.add(nuevaTareaRevisor.getIniciativa());
		indicadoresLista.add(nuevaTareaRevisor.getActividad());
		indicadoresLista.add(nuevaTareaRevisor.getPopularidad());
		indicadoresLista.add(nuevaTareaRevisor.getCercania());
		indicadoresLista.add(nuevaTareaRevisor.getBetweenness());
		indicadoresLista.add(nuevaTareaRevisor.getEigenvector());
		return indicadoresLista;		
	}

	// Prepara el formulario para la gestión de una nueva tareaRevisor
	@GetMapping("/tareaRevisor/edit/{id}")
	public String editarRevisorForm(@PathVariable Integer id, Model model)
	{
		//Busco en base a id
		TareaRevisor tareaRevisor = servicio.findById(id);
		// Si encuentro agrego al modelo y redirigo al formulario
		if (tareaRevisor != null)
		{
			List<String> listaFunciones = new ArrayList<>();
			
			listaFunciones.add("Lineal");
			listaFunciones.add("Cuadrática");
			listaFunciones.add("Raíz Cuadrada");
			
			model.addAttribute("funcion", listaFunciones);
			model.addAttribute("tareaRevisorForm", tareaRevisor);
			
			model.addAttribute("numEstud", (servicioPersona.findAll()).size()-1);
			
			return "formEditTareaRevisor";
		}
		// si no lo encuentro, redirijo a nueva tareaRevisor
		else
			return "redirect:/tareaRevisor/new";
	}	
	
	// Prepara el formulario para la gestión de una nueva tareaRevisor
	@GetMapping("/tareaRevisor/duplicar/{id}")
	public String duplicarRevisorForm(@PathVariable Integer id, Model model)
	{		
		//Busco en base a id
		TareaRevisor tareaRevisor = servicio.findById(id);
		// Si encuentro agrego al modelo y redirigo al formulario
		if (tareaRevisor != null)
		{
			TareaRevisor nuevaTareaRevisor = new TareaRevisor();
			BeanUtils.copyProperties(tareaRevisor, nuevaTareaRevisor, "id");
			servicio.add(nuevaTareaRevisor);
		}
		return "redirect:/tareaRevisor/list";		
	}	
	
	// Envía la información cumplimentada en el formulario
	@PostMapping("/tareaRevisor/edit/submit")
	public String editarTareaRevisorSubmit(@ModelAttribute("tareaRevisorForm") TareaRevisor nuevaTareaRevisor)
	{
		if( compruebaOrdenIndicadores(nuevaTareaRevisor)) {
			corrigeTablas(nuevaTareaRevisor);
			servicio.edit(nuevaTareaRevisor);
			return "redirect:/tareaRevisor/list";
		}
		else
		{	
			servicio.add(nuevaTareaRevisor);
			return "redirect:/tareaRevisor/modifyError/"+nuevaTareaRevisor.getId();
		}	
	}	
	
	// Elimina una tareaRevisor
	@GetMapping("/tareaRevisor/delete/{id}")
	public String eliminarTareaRevisor(@PathVariable Integer id, Model model)
	{
		servicio.delete(id);
		return "redirect:/tareaRevisor/list";
	}	
	
	// Prepara el formulario para la modificación de errores en el orden de indicadores (repetido algún valor)
	@GetMapping("/tareaRevisor/modifyError/{id}")
	public String modificaErrorTareaRevisor(@PathVariable Integer id, Model model)
	{
		//Busco en base a id
		TareaRevisor tareaRevisor = servicio.findById(id);
		// Si encuentro agrego al modelo y redirijo al formulario
		
		if (tareaRevisor != null)
		{
			model.addAttribute("tareaRevisorForm", tareaRevisor);
			model.addAttribute("numEstud", (servicioPersona.findAll()).size()-1);
			
			return "formModifyErrorTareaRevisor";
		}
		// si no lo encuentro, redirijo a nueva tareaRevisor
		else
			return "redirect:/tareaRevisor/list";
		
	}
	
	// Envía la información cumplimentada en el formulario
	@PostMapping("/tareaRevisor/modifyError/submit")
	public String modificaErrorTareaRevisorSubmit(@ModelAttribute("tareaRevisorForm") TareaRevisor nuevaTareaRevisor)
	{
		if( compruebaOrdenIndicadores(nuevaTareaRevisor)) {
			servicio.edit(nuevaTareaRevisor);
			return "redirect:/tareaRevisor/list";
		}
		else
			return "redirect:/tareaRevisor/modifyError/"+nuevaTareaRevisor.getId().toString();
	}
	
}
