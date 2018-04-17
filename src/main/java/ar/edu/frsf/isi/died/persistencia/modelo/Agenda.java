package ar.edu.frsf.isi.died.persistencia.modelo;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
	private Integer id;
	private String nombre;
	private List<Tarea> tareas;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void addTarea(Tarea t) {
		if(this.tareas==null) this.tareas = new ArrayList<>();
		this.tareas.add(t);
	}
	public List<Tarea> getTareas() {
		return tareas;
	}
	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}
	
	
}
