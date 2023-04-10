package jsv.unededucaanalisis;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import amm.unededucaanalisis.servicios.ConvocatoriaService;
import amm.unededucaanalisis.servicios.AristaService;
import amm.unededucaanalisis.servicios.AsignaturaService;
import amm.unededucaanalisis.servicios.PersonaService;
import jsv.unededucaanalisis.modelo.Arista;
import jsv.unededucaanalisis.modelo.Asignatura;
import jsv.unededucaanalisis.modelo.Convocatoria;
import jsv.unededucaanalisis.modelo.Grafo;
import jsv.unededucaanalisis.modelo.InfoProcesoForo;
import jsv.unededucaanalisis.modelo.Persona;
import jsv.unededucaanalisis.repositorios.AsignaturaRepository;
import amm.unededucaanalisis.servicios.GrafoService;
import amm.unededucaanalisis.servicios.ForoAlumnosService;

@SpringBootApplication
public class UnedEducaAnalisisApplication {

	public static void main(String[] args) 
	{
		//gephiController migephyController=new gephiController();
		//migephyController.testGephi();
		SpringApplication.run(UnedEducaAnalisisApplication.class, args);
	}


	
//	@Bean
//	CommandLineRunner testimportarForo(ForoAlumnosService servicioForoAlumnos, PersonaService servicioPersona)
//	{
//		return(args) ->
//		{
//			System.out.println("Tests básicos de importación");
//			InfoProcesoForo miInfoProcesoForo = servicioForoAlumnos.procesaDatos("2012-2013",servicioPersona.findById(1));		
//		};
//	
//	}
//	
	


//	@Bean
//	CommandLineRunner testAristas(AristaService servicio)
//	{
//		return(args) ->
//		{
//		System.out.println("Tests básicos Aristas");			
//		servicio.findAll().forEach(System.out::println);
//		};
//		
//	}	



//	@Bean
//	CommandLineRunner testgrafo(GrafoService servicioGrafo, PersonaService servicioPersona, AristaService servicioArista)
//	{
//		return(args) ->
//		{
//		System.out.println("Tests básicos de grafos");	
//		Grafo miGrafo=servicioGrafo.generarGrafo(servicioPersona, servicioArista);
//		System.out.println("Listado de nodos");
//		miGrafo.getNodos().forEach(System.out::println);
//		System.out.println("Listado de aristas");
//		miGrafo.getAristas().forEach(System.out::println);		
//		servicioGrafo.generarFichero(miGrafo,"kk");
//
//		};
//		
//	}	
	

	
}
