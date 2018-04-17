package ar.edu.frsf.isi.died.persistencia.dao;

import java.util.List;

import ar.edu.frsf.isi.died.persistencia.modelo.Agenda;
import ar.edu.frsf.isi.died.persistencia.modelo.Tarea;

public interface AgendaDao {

	public void crear(String nombre);
	public void borrarPorId(Integer id);
	public Agenda buscarPorId(Integer id);
	public Agenda buscarPorNombre(String n);
	public List<Agenda> listar();
	public void agregarTarea(Agenda a, Tarea t);
	public void finalizarTarea(Tarea t);
	public List<Tarea> pendientes(Agenda a);
	public List<Tarea> completadas(Agenda a);
	public List<Tarea> tareas(Agenda a);
	public Integer cantidadAgendas();
	public Integer cantidadTareas(Agenda a);

}
