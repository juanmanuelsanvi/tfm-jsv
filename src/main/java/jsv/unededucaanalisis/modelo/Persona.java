package jsv.unededucaanalisis.modelo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERSONA")
public class Persona 
{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String usuario;
	

	@Column(nullable=true)
	private String apellidos;

	@Column
	private String nombre;

	@Column
	private String email;
	
	@Column
	private Timestamp fechaActualizacion;

	public Persona(String usuario, String apellidos, String nombre, String email) {
		super();
		this.usuario = usuario;
		this.apellidos = apellidos;
		this.nombre = nombre;
		this.email = email;
	}

	public Persona()
	{
		
	}
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	@Override
	public String toString() {
		return "Persona [id=" + id + ", usuario=" + usuario + ", apellidos=" + apellidos + ", nombre=" + nombre
				+ ", email=" + email + ", fechaActualizacion=" + fechaActualizacion + "]";
	}
	
}


