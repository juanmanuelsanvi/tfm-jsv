package amm.unededucaanalisis.servicios;

import java.io.IOException;
import java.util.List;

import amm.unededucaanalisis.modelo.Asignatura;
import amm.unededucaanalisis.modelo.Convocatoria;
import amm.unededucaanalisis.modelo.Foro;
import amm.unededucaanalisis.modelo.InfoProcesoForo;
import amm.unededucaanalisis.modelo.Materia;
import amm.unededucaanalisis.modelo.Mensaje;
import amm.unededucaanalisis.modelo.Persona;

public interface ForoAlumnosService 
{
	public InfoProcesoForo procesaDatos(String convocatoria, Persona profesor) throws IOException;
	public Convocatoria importarConvocatoria(String convocatoria, ConvocatoriaService servicioConvocatoria);
	public Asignatura importarAsignatura(String asignatura, AsignaturaService servicioAsignatura);	
	public Materia importarMateria(Convocatoria convocatoria, Asignatura asignatura, Persona persona, MateriaService servicioMateria);	
	public Foro importarForo(Materia materia, String denominacion, ForoService servicioForo);	
	public Persona importarPersona(String usuario, PersonaService servicioPersona);
	public Mensaje importarMensaje(Mensaje mensaje, MensajeService servicioMensaje);

}
