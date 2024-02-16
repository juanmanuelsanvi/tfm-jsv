package jsv.unededucaanalisis.modelo;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "MENSAJE")
public class Mensaje 
{
	@Id
	private String id;
	
	@Column(nullable=false)
	private Integer idForo;	
	
	@Column(nullable=false)
	private Integer idPersona;	
	
	@Column
	private String idMensajePadre;
	
	@Column
	private String titulo;
	
	@Column
	@Type(type="text")
	private String contenidoMensaje;
	
	@Column
	private Integer numCaracteres;	

	@Column
	private String fechaEnvio;
	
	@Column
	private Timestamp fechaActualizacion;

	public Mensaje() {}
	
	public Mensaje(String id, Integer idForo,	Integer idPersona, String idMensajePadre, String titulo,String contenidoMensaje, Integer numCaracteres, String fechaEnvio)
	{
		this.id = id;
		this.idForo = idForo;
		this.idPersona = idPersona;
		this.idMensajePadre = idMensajePadre;
		this.titulo = titulo;
		this.contenidoMensaje = contenidoMensaje;
		this.numCaracteres = numCaracteres;
		this.fechaEnvio = fechaEnvio;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIdForo() {
		return idForo;
	}

	public void setIdForo(Integer idForo) {
		this.idForo = idForo;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public String getIdMensajePadre() {
		return idMensajePadre;
	}

	public void setIdMensajePadre(String idMensajePadre) {
		this.idMensajePadre = idMensajePadre;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenidoMensaje() {
		return contenidoMensaje;
	}

	public void setContenidoMensaje(String contenidoMensaje) {
		this.contenidoMensaje = contenidoMensaje;
	}

	public Integer getNumCaracteres() {
		return numCaracteres;
	}

	public void setNumCaracteres(Integer numCaracteres) {
		this.numCaracteres = numCaracteres;
	}

	public String getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	@Override
	public String toString() {
		return "Mensaje [id=" + id + ", idForo=" + idForo + ", idPersona=" + idPersona + ", idMensajePadre="
				+ idMensajePadre + ", titulo=" + titulo + ", contenidoMensaje=" + contenidoMensaje + ", numCaracteres="
				+ numCaracteres + ", fechaEnvio=" + fechaEnvio + ", fechaActualizacion=" + fechaActualizacion + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mensaje other = (Mensaje) obj;
		return Objects.equals(id, other.id);
	}
	
}


