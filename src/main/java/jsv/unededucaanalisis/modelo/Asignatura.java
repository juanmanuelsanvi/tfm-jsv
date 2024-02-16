package jsv.unededucaanalisis.modelo;

import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASIGNATURA")
public class Asignatura 
{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=false)
	private String denominacion;
	
	@Column
	private Timestamp fechaActualizacion;

	public Asignatura() {}

	public Asignatura(Integer id, String denominacion) 
	{
		this.id = id;
		this.denominacion = denominacion;
	}

	public Asignatura(String denominacion) {
		this.denominacion = denominacion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Asignatura [id=" + id + ", denominacion=" + denominacion + ", fechaActualizacion=" + fechaActualizacion
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(denominacion, fechaActualizacion, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asignatura other = (Asignatura) obj;
		return Objects.equals(denominacion, other.denominacion)
				&& Objects.equals(fechaActualizacion, other.fechaActualizacion) && id == other.id;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}


	
}


