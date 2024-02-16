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
@Table(name = "CONVOCATORIA")
public class Convocatoria 
{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=false)
	private String convocatoria;
	
	@Column
	private Timestamp fechaActualizacion;

	public Convocatoria() {}

	public Convocatoria(Integer id, String convocatoria) 
	{
		this.id = id;
		this.convocatoria = convocatoria;
	}

	public Convocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Convocatoria [id=" + id + ", convocatoria=" + convocatoria + ", fechaActualizacion=" + fechaActualizacion
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(convocatoria, fechaActualizacion, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Convocatoria other = (Convocatoria) obj;
		return Objects.equals(convocatoria, other.convocatoria)
				&& Objects.equals(fechaActualizacion, other.fechaActualizacion) && id == other.id;
	}

	public String getConvocatoria() {
		return convocatoria;
	}

	public void setconvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}

	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}


	
}


