package jsv.unededucaanalisis.servicios;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jsv.unededucaanalisis.modelo.Arista;
import jsv.unededucaanalisis.modelo.Grafo;
import jsv.unededucaanalisis.modelo.Persona;

@Service("GrafoService")
public class GrafoServiceImpl implements GrafoService {

	@Value("${tfmjsv.data.ficherografo}")
	private String ficheroGrafo;
	@Value("${tfmjsv.data.rutaficheros}")
	private String rutaFicheros;		
	
	@Override
	public Grafo generarGrafo(PersonaService servicioPersonas, AristaService servicioAristas) 
	{
		Grafo grafo = new Grafo();
		//Obtener nodos llamando a servicios de personas
		grafo.setNodos(servicioPersonas.findAll());
		//Obtener aristas llamando a servicios de aristas.
		grafo.setAristas(servicioAristas.findAll());
		return grafo;
	}

	@Override
	public int generarFichero(Grafo migrafo, String formato) throws IOException 
	{
		FileWriter fileWriter = new FileWriter(rutaFicheros + ficheroGrafo);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println("Creator \"JUAN MANUEL SÁNCHEZ VILLAREJO\"");
	    printWriter.println("graph");	      
	    printWriter.println("[");
	    
	    //Se escriben los nodos

	    for (Persona nodo : migrafo.getNodos()) 
	    {
		    printWriter.println("node");	    	
		    printWriter.println("[");	    	
		    printWriter.println("id " + nodo.getId().toString());
		    printWriter.println("usuario \"" + nodo.getUsuario() + "\"");
		    printWriter.println("apellidos \"" +  nodo.getApellidos() + "\"");
		    printWriter.println("nombre \"" + nodo.getNombre()+ "\"");
		    printWriter.println("email \"" + nodo.getNombre()+ "\"");
		    printWriter.println("]");
	    }
	    
	    //Se escriben las aristas
	    for (Arista arista : migrafo.getAristas()) 
	    {
		    printWriter.println("edge");	    	
		    printWriter.println("[");
		    printWriter.println("source " + arista.getSource().toString());
		    printWriter.println("target " + arista.getTarget().toString());		    
		    printWriter.println("]");
	    }
	    
	    // Se cierra el grafo
	    printWriter.println("]");
	    printWriter.close();
		return 0;
	}	
}


