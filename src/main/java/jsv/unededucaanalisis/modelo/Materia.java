package jsv.unededucaanalisis.modelo;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MATERIA")
public class Materia 
{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=false)
	private Integer idConvocatoria;

	@Column(nullable=false)
	private Integer idAsignatura;

	@Column(nullable=false)
	private Integer idProfesor;
	
	@Column
	private Timestamp fechaActualizacion;

	public Materia() {}

	public Materia(Integer idConvocatoria, Integer idAsignatura, Integer idProfesor) 
	{
		this.idConvocatoria=idConvocatoria;
		this.idAsignatura = idAsignatura;
		this.idProfesor = idProfesor;	
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdConvocatoria() {
		return idConvocatoria;
	}

	public void setIdConvocatoria(Integer idConvocatoria) {
		this.idConvocatoria = idConvocatoria;
	}

	public Integer getIdAsignatura() {
		return idAsignatura;
	}

	public void setIdAsignatura(Integer idAsignatura) {
		this.idAsignatura = idAsignatura;
	}

	public Integer getIdProfesor() {
		return idProfesor;
	}

	public void setIdProfesor(Integer idProfesor) {
		this.idProfesor = idProfesor;
	}

	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	@Override
	public String toString() {
		return "Materia [id=" + id + ", idConvocatoria=" + idConvocatoria + ", idAsignatura=" + idAsignatura
				+ ", idProfesor=" + idProfesor + ", fechaActualizacion=" + fechaActualizacion + "]";
	}	

}


