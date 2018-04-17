package ar.edu.frsf.isi.died.persistencia.modelo;

public class Tarea {

	private Integer id;
	private String descripcion;
	private Boolean completada;
	private Agenda agenda;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Agenda getAgenda() {
		return agenda;
	}
	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Boolean getCompletada() {
		return completada;
	}
	public void setCompletada(Boolean completada) {
		this.completada = completada;
	}
	
	
	
}
