package jsv.unededucaanalisis.servicios;

import java.io.IOException;

import jsv.unededucaanalisis.modelo.Asignatura;
import jsv.unededucaanalisis.modelo.Convocatoria;
import jsv.unededucaanalisis.modelo.Foro;
import jsv.unededucaanalisis.modelo.InfoProcesoForo;
import jsv.unededucaanalisis.modelo.Materia;
import jsv.unededucaanalisis.modelo.Mensaje;
import jsv.unededucaanalisis.modelo.Persona;

public interface ForoAlumnosService 
{
	public InfoProcesoForo procesaDatos(String convocatoria, Persona profesor, String ficheroForo) throws IOException;
	public Convocatoria importarConvocatoria(String convocatoria, ConvocatoriaService servicioConvocatoria);
	public Asignatura importarAsignatura(String asignatura, AsignaturaService servicioAsignatura);	
	public Materia importarMateria(Convocatoria convocatoria, Asignatura asignatura, Persona persona, MateriaService servicioMateria);	
	public Foro importarForo(Materia materia, String denominacion, ForoService servicioForo);	
	public Persona importarPersona(String usuario, PersonaService servicioPersona);
	public Mensaje importarMensaje(Mensaje mensaje, MensajeService servicioMensaje);

}
