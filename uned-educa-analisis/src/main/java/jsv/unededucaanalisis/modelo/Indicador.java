package jsv.unededucaanalisis.modelo;

public class Indicador {
	private Integer id;
	private Integer iniciativa;
	private Integer actividad;
	private Integer popularidad;
	private Double closeness;
	private Double betweenness;
	private Double centralidad;
	
	@Override
	public String toString() {
		return "Indicador [id=" + id + ", iniciativa=" + iniciativa + ", actividad=" + actividad + ", popularidad="
				+ popularidad + ", closeness=" + closeness + ", betweenness=" + betweenness + ", centralidad="
				+ centralidad + ", modularidad=" + modularidad + "]";
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
	public Double getCentralidad() {
		return centralidad;
	}
	public void setCentralidad(Double centralidad) {
		this.centralidad = centralidad;
	}
	public Double getModularidad() {
		return modularidad;
	}
	public void setModularidad(Double modularidad) {
		this.modularidad = modularidad;
	}
	private Double modularidad;
	
}
