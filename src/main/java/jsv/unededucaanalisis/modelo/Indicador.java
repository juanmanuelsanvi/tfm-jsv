package jsv.unededucaanalisis.modelo;

public class Indicador {
	private Integer id;
	private Integer iniciativa;
	private Integer actividad;
	private Integer popularidad;
	private Double closeness;
	private Double betweenness;
	//private Double harmonic;
	//private Double eccentricity;
	private Double eigenvector;
	private Integer modularidad;
	
	/*public Double getHarmonic() {
		return harmonic;
	}

	public void setHarmonic(Double harmonic) {
		this.harmonic = harmonic;
	}*/

	/*public Double getEccentricity() {
		return eccentricity;
	}

	public void setEccentricity(Double eccentricity) {
		this.eccentricity = eccentricity;
	}*/

	@Override
	public String toString() {
		return "Indicador [id=" + id + ", iniciativa=" + iniciativa + ", actividad=" + actividad + ", popularidad="
				+ popularidad + ", closeness=" + closeness + ", betweenness=" + betweenness + ", eigenvector="
				+ eigenvector + ", modularidad=" + modularidad + "]";
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
	
	public Double getCloseness() {
		return closeness;
	}
	
	public void setCloseness(Double closeness) {
		this.closeness = closeness;
	}
	
	public Double getBetweenness() {
		return betweenness;
	}
	
	public void setBetweenness(Double betweenness) {
		this.betweenness = betweenness;
	}
	
	public Double getEigenvector() {
		return eigenvector;
	}

	public void setEigenvector(Double eigenvector) {
		this.eigenvector = eigenvector;
	}

	public Integer getModularidad() {
		return modularidad;
	}
	
	public void setModularidad(Integer modularidad) {
		this.modularidad = modularidad;
	}
	
	
}
