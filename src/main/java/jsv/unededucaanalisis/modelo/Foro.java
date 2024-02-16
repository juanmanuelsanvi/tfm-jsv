package jsv.unededucaanalisis.modelo;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FORO")
public class Foro 
{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=false)
	private Integer idMateria;

	@Column
	private Timestamp fechaCreacion;	
	
	@Column(nullable=false)
	private String denominacion;
	
	@Column(nullable=false)
	private String descripcion;	

	@Column
	private Timestamp fechaActualizacion;

	public Foro() {}

	public Foro(Integer idMateria, String denominacion, String descripcion) 
	{
		this.idMateria = idMateria;
		this.denominacion = denominacion;
		this.descripcion = descripcion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(Integer idMateria) {
		this.idMateria = idMateria;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	@Override
	public String toString() {
		return "Foro [id=" + id + ", idMateria=" + idMateria + ", fechaCreacion=" + fechaCreacion + ", denominacion="
				+ denominacion + ", descripcion=" + descripcion + ", fechaActualizacion=" + fechaActualizacion + "]";
	}
	
	
	
	
}


