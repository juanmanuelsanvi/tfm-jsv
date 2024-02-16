package jsv.unededucaanalisis;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UnedEducaAnalisisApplication {

	public static void main(String[] args) 
	{
		//gephiController migephyController=new gephiController();
		//migephyController.testGephi();
		SpringApplication.run(UnedEducaAnalisisApplication.class, args);
		System.setProperty("java.awt.headless", "false");
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
