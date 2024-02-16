package jsv.unededucaanalisis.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "REVISOR")
public class TareaRevisor {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private Integer numEstudiantes;
	
	@Column 
	private String funcion;
	
	@Column 	
	private Integer activityCompatibility;
	
	@Column 
	private Integer iniciativa;
	
	@Column 
	private Integer actividad;
	
	@Column 
	private Integer popularidad;
	
	@Column 
	private Integer cercania;
	
	@Column 
	private Integer betweenness;
	
	@Column 
	private Integer eigenvector;
	
	@Column
	private Float probLowLow;
	
	@Column
	private Float probLowMedium;
	
	@Column
	private Float probLowHigh;
	
	@Column
	private Float probMediumLow;
	
	@Column
	private Float probMediumMedium;
	
	@Column
	private Float probMediumHigh;
	
	@Column
	private Float probHighLow;
	
	@Column
	private Float probHighMedium;
	
	@Column
	private Float probHighHigh;
	
	// Estos son los parámetros de la tabla ActivityCompatibility
	@Column
	private Float utiLowLow;
	
	@Column
	private Float utiLowMedium;
	
	@Column
	private Float utiLowHigh;
	
	@Column
	private Float utiMediumLow;
	
	@Column
	private Float utiMediumMedium;
	
	@Column
	private Float utiMediumHigh;
	
	@Column
	private Float utiHighLow;
	
	@Column
	private Float utiHighMedium;
	
	@Column
	private Float utiHighHigh;	
	
	
	// private Integer modularidad;
	
	public TareaRevisor() {
	}	
	
	public TareaRevisor(Integer id) 
	{
		this.id = id;
	}
	
	public TareaRevisor(Integer id, Integer numEstudiantes) 
	{
		this.id = id;
		this.numEstudiantes = numEstudiantes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumEstudiantes() {
		return numEstudiantes;
	}

	public void setNumEstudiantes(Integer numEstudiantes) {
		this.numEstudiantes = numEstudiantes;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public Integer getActivityCompatibility() {
		return activityCompatibility;
	}

	public void setActivityCompatibility(Integer activityCompatibility) {
		this.activityCompatibility = activityCompatibility;
	}

	public Integer getIniciativa() {
		return iniciativa;
	}

	public void setIniciativa(Integer iniciativa) {
		this.iniciativa = iniciativa;
	}

	public Integer getActividad() {
		return actividad;
	}

	public void setActividad(Integer actividad) {
		this.actividad = actividad;
	}

	public Integer getPopularidad() {
		return popularidad;
	}

	public void setPopularidad(Integer popularidad) {
		this.popularidad = popularidad;
	}

	public Integer getCercania() {
		return cercania;
	}

	public void setCercania(Integer cercania) {
		this.cercania = cercania;
	}

	public Integer getBetweenness() {
		return betweenness;
	}

	public void setBetweenness(Integer betweenness) {
		this.betweenness = betweenness;
	}

	public Integer getEigenvector() {
		return eigenvector;
	}

	public void setEigenvector(Integer eigenvector) {
		this.eigenvector = eigenvector;
	}

	public Float getProbLowLow() {
		return probLowLow;
	}

	public void setProbLowLow(Float probLowLow) {
		this.probLowLow = probLowLow;
	}

	public Float getProbLowMedium() {
		return probLowMedium;
	}

	public void setProbLowMedium(Float probLowMedium) {
		this.probLowMedium = probLowMedium;
	}

	public Float getProbLowHigh() {
		return probLowHigh;
	}

	public void setProbLowHigh(Float probLowHigh) {
		this.probLowHigh = probLowHigh;
	}

	public Float getProbMediumLow() {
		return probMediumLow;
	}

	public void setProbMediumLow(Float probMediumLow) {
		this.probMediumLow = probMediumLow;
	}

	public Float getProbMediumMedium() {
		return probMediumMedium;
	}

	public void setProbMediumMedium(Float probMediumMedium) {
		this.probMediumMedium = probMediumMedium;
	}

	public Float getProbMediumHigh() {
		return probMediumHigh;
	}

	public void setProbMediumHigh(Float probMediumHigh) {
		this.probMediumHigh = probMediumHigh;
	}

	public Float getProbHighLow() {
		return probHighLow;
	}

	public void setProbHighLow(Float probHighLow) {
		this.probHighLow = probHighLow;
	}

	public Float getProbHighMedium() {
		return probHighMedium;
	}

	public void setProbHighMedium(Float probHighMedium) {
		this.probHighMedium = probHighMedium;
	}

	public Float getProbHighHigh() {
		return probHighHigh;
	}

	public void setProbHighHigh(Float probHighHigh) {
		this.probHighHigh = probHighHigh;
	}

	public Float getUtiLowLow() {
		return utiLowLow;
	}

	public void setUtiLowLow(Float utiLowLow) {
		this.utiLowLow = utiLowLow;
	}

	public Float getUtiLowMedium() {
		return utiLowMedium;
	}

	public void setUtiLowMedium(Float utiLowMedium) {
		this.utiLowMedium = utiLowMedium;
	}

	public Float getUtiLowHigh() {
		return utiLowHigh;
	}

	public void setUtiLowHigh(Float utiLowHigh) {
		this.utiLowHigh = utiLowHigh;
	}

	public Float getUtiMediumLow() {
		return utiMediumLow;
	}

	public void setUtiMediumLow(Float utiMediumLow) {
		this.utiMediumLow = utiMediumLow;
	}

	public Float getUtiMediumMedium() {
		return utiMediumMedium;
	}

	public void setUtiMediumMedium(Float utiMediumMedium) {
		this.utiMediumMedium = utiMediumMedium;
	}

	public Float getUtiMediumHigh() {
		return utiMediumHigh;
	}

	public void setUtiMediumHigh(Float utiMediumHigh) {
		this.utiMediumHigh = utiMediumHigh;
	}

	public Float getUtiHighLow() {
		return utiHighLow;
	}

	public void setUtiHighLow(Float utiHighLow) {
		this.utiHighLow = utiHighLow;
	}

	public Float getUtiHighMedium() {
		return utiHighMedium;
	}

	public void setUtiHighMedium(Float utiHighMedium) {
		this.utiHighMedium = utiHighMedium;
	}

	public Float getUtiHighHigh() {
		return utiHighHigh;
	}

	public void setUtiHighHigh(Float utiHighHigh) {
		this.utiHighHigh = utiHighHigh;
	}

}
