package amm.unededucaanalisis.servicios;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.csv.*;

import amm.unededucaanalisis.modelo.Asignatura;
import amm.unededucaanalisis.modelo.Convocatoria;
import amm.unededucaanalisis.modelo.Foro;
import amm.unededucaanalisis.modelo.InfoProcesoForo;
import amm.unededucaanalisis.modelo.Materia;
import amm.unededucaanalisis.modelo.Mensaje;
import amm.unededucaanalisis.modelo.Persona;
import amm.unededucaanalisis.repositorios.ConvocatoriaRepository;

@Service("ForoAlumnosService")
public class ForoAlumnosServiceImpl implements ForoAlumnosService 
{
	@Value("${pfgamm.data.ficheroForo}")
	private String ficheroForo;
	@Value("${pfgamm.data.rutaficheros}")
	private String rutaFicheros;
	
	@Autowired
	private ConvocatoriaService servicioConvocatoria;
	@Autowired
	private AsignaturaService servicioAsignatura;
	@Autowired
	private MateriaService servicioMateria;	
	@Autowired
	private ForoService servicioForo;	
	@Autowired
	private PersonaService servicioPersona;
	@Autowired
	private MensajeService servicioMensaje;

	private Integer numRegistrosProcesados;
	private Integer numAsignaturas;
	private Integer numForos;
	private Integer numMensajes;
	private Integer numPersonas;	
	
	
	@Override
	public InfoProcesoForo procesaDatos(String convocatoria, Persona profesor) throws IOException
	{
		InfoProcesoForo infoProceso=new InfoProcesoForo();
		Reader in = new FileReader(rutaFicheros + ficheroForo);

		numRegistrosProcesados=0;
		numAsignaturas=0;
		numForos=0;
		numMensajes=0;
		numPersonas=0;		
		
		String[] HEADERS = {"idAsignatura","Asignatura","idForo","Foro","idHilo","Hilo","idMensaje","Responde a idMensaje","idAutor","Autor","Dia","Fecha","Hora","Titulo mensaje","Texto mensaje","Caracteres mensaje"};
		
	    Iterable<CSVRecord> records = CSVFormat.DEFAULT   
	    		.withHeader(HEADERS)
	    		.withFirstRecordAsHeader()	
	    		.parse(in);
	    for (CSVRecord record : records) 
	    {   
	    	// Calculo el nº de registros procesados
	    	numRegistrosProcesados++;
	    	String asignatura = record.get("Asignatura");
	    	String denominacionForo = record.get("Foro");
	    	String autor = record.get("Autor");
	    	
	    	String idMensaje = record.get("idMensaje");
	    	String idMensajePadre = record.get("Responde a idMensaje");
	    	idMensajePadre = (idMensajePadre == "" ? null : idMensajePadre);
	    	
	    	String titulo = record.get("Titulo mensaje");
	    	String contenidoMensaje = (record.get("Texto mensaje")).replace("\"", "");	    	
	    	String numCaracteres = record.get("Caracteres mensaje");	  
	    	String fecha = record.get("Fecha");
	    	String hora = record.get("Hora"); 
	    	String fechaEnvio = fecha + " " + hora;
	    	
	    	    	
	    	Convocatoria miConvocatoria=importarConvocatoria(convocatoria, servicioConvocatoria);
    		Asignatura miAsignatura=importarAsignatura(asignatura ,servicioAsignatura);
    		Materia materia = importarMateria(miConvocatoria, miAsignatura, profesor , servicioMateria);
    		Foro foro = importarForo(materia, denominacionForo, servicioForo);
    		Persona persona = importarPersona (autor, servicioPersona);
    		
    		Mensaje mensaje = new Mensaje (idMensaje, foro.getId(), persona.getId(), idMensajePadre, titulo, contenidoMensaje, Integer.valueOf(numCaracteres),fechaEnvio);
	        System.out.println(mensaje.toString());
    		Mensaje rmensaje = importarMensaje(mensaje , servicioMensaje);
    		
	        System.out.println(miConvocatoria.toString());
	        System.out.println(miAsignatura.toString());        
	        System.out.println(materia.toString());	        
	        System.out.println(foro.toString());	        
	        System.out.println(persona.toString());
	        System.out.println(rmensaje.toString());
	    }
	    
	    infoProceso.setNumAsignaturas(numAsignaturas.toString());
	    infoProceso.setNumForos(numForos.toString());
	    infoProceso.setNumMensajes(numMensajes.toString());
	    infoProceso.setNumPersonas(numPersonas.toString());
	    infoProceso.setNumRegistrosProcesados(numRegistrosProcesados.toString());
	    
		return infoProceso;
	}
	
	@Override
	public Convocatoria importarConvocatoria(String convocatoria, ConvocatoriaService servicioConvocatoria)
	{
		//Comprobamos existencia. si existe devolvemos, y si no, creamos y devolvemos.
		Convocatoria miConvocatoria = servicioConvocatoria.findByConvocatoria(convocatoria);
		if (miConvocatoria == null)
		{
			miConvocatoria=new Convocatoria(convocatoria);
			miConvocatoria = servicioConvocatoria.add(miConvocatoria);
		}
		
		return miConvocatoria;
	}
	
	@Override
	public Asignatura importarAsignatura(String asignatura, AsignaturaService servicioAsignatura)
	{
		//Comprobamos existencia. si existe devolvemos, y si no, creamos y devolvemos.
		Asignatura miAsignatura = servicioAsignatura.findByDenominacion(asignatura);
		if (miAsignatura == null)
		{
			miAsignatura=new Asignatura(asignatura);
			miAsignatura = servicioAsignatura.add(miAsignatura);		
			numAsignaturas++;
		}
		
		return miAsignatura;		
		
	}

	@Override
	public Materia importarMateria(Convocatoria convocatoria, Asignatura asignatura, Persona persona, MateriaService servicioMateria)
	{
		//Comprobamos existencia. si existe devolvemos, y si no, creamos y devolvemos.
		Materia materia = servicioMateria.findByidConvocatoriaAndIdAsignaturaAndIdProfesor(convocatoria.getId(), asignatura.getId(), persona.getId());
		if (materia == null)
		{
			materia = new Materia(convocatoria.getId(),asignatura.getId(),persona.getId());
			materia = servicioMateria.add(materia);
			
		}

		return materia;		
	}	

	@Override
	public Foro importarForo(Materia materia, String denominacion, ForoService servicioForo)
	{
		Foro foro = servicioForo.findByIdMateriaAndDenominacion(materia.getId(), denominacion);
		if (foro == null)
		{
			foro = new Foro(materia.getId(),denominacion, "");
			foro = servicioForo.add(foro);
			numForos++;
		}
		
		return foro;
		
	}
	
	@Override
	public Persona importarPersona(String usuario, PersonaService servicioPersona)
	{
		Persona persona = servicioPersona.findByUsuario(usuario);
		if (persona == null)
		{
			persona = new Persona(usuario,".",".",".");
			persona = servicioPersona.add(persona);
			numPersonas++;
		}
		
		return persona;
	}
	
	public Mensaje importarMensaje(Mensaje mensaje, MensajeService servicioMensaje)
	{
		//Borro e incorporo
		Mensaje lmensaje = servicioMensaje.findById(mensaje.getId());
		if (lmensaje != null)
		{
			// servicioMensaje.delete(mensaje.getId());
			servicioMensaje.edit(mensaje);
		}

		servicioMensaje.add(mensaje);
		numMensajes++;
		return mensaje;
	}
	
	
}
