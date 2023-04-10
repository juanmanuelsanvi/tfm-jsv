package amm.unededucaanalisis.servicios;

import java.util.List;
import java.io.File;
import java.io.IOException;

import amm.unededucaanalisis.modelo.Grafo;
import amm.unededucaanalisis.servicios.PersonaService;
import amm.unededucaanalisis.servicios.AristaService;

public interface GrafoService 
{
	public Grafo generarGrafo(PersonaService servicioPersonas, AristaService servicioAristas);
	public int generarFichero(Grafo migrafo, String formato)  throws IOException;

}
