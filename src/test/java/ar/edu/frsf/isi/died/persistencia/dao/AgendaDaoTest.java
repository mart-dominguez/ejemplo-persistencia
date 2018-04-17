package ar.edu.frsf.isi.died.persistencia.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ar.edu.frsf.isi.died.persistencia.modelo.Agenda;
import ar.edu.frsf.isi.died.persistencia.modelo.Tarea;

public class AgendaDaoTest {

	ConexionDB conn;
	AgendaDao daoService;

	@Before
	public void init() {
		daoService = new AgendaDaoDefault();
		
		Integer idAgenda = 0;
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn
					.prepareStatement("INSERT INTO AGENDA (NOMBRE) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
				ps.setString(1, "Agenda 1");
				ps.executeUpdate();
				try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		            if (generatedKeys.next()) idAgenda=generatedKeys.getInt(1);		            
		        }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn
					.prepareStatement("INSERT INTO TAREA (DESCRIPCION,FINALIZADA,ID_AGENDA) VALUES(?,?,?)")) {
				ps.setString(1, "TAREA 1");
				ps.setInt(2, 0);
				ps.setInt(3, idAgenda);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@After
	public void limpiar() {
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn
					.prepareStatement("DELETE FROM AGENDA")) {
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn
					.prepareStatement("DELETE FROM TAREA")) {
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCrearAgenda() {
		Integer nroAgendas = daoService.cantidadAgendas();
		assertEquals(Integer.valueOf(1), nroAgendas);
		daoService.crear("AGENDA 99");
		nroAgendas = daoService.cantidadAgendas();
		assertEquals(Integer.valueOf(2), nroAgendas);
	}

	@Test
	public void testBorrarPorId() {
		Integer nroAgendas = daoService.cantidadAgendas();
		assertEquals(Integer.valueOf(1), nroAgendas);
		List<Agenda> agendas = daoService.listar();
		assertEquals(nroAgendas,Integer.valueOf(agendas.size()));
		daoService.borrarPorId(agendas.get(0).getId());
		nroAgendas = daoService.cantidadAgendas();
		assertEquals(Integer.valueOf(0), nroAgendas);	
	}

	@Test
	public void testBuscarPorId() {
		List<Agenda> agendas = daoService.listar();
		Agenda primera = agendas.get(0);
		assertNotNull(primera);
		assertNotNull(primera.getId());
		Agenda buscada = daoService.buscarPorId(agendas.get(0).getId());
		assertFalse(primera == buscada); // verificar que son dos objetos que apuntan a distinta posicion de memoria
		assertEquals(primera.getId(),buscada.getId());	
	}

	@Test
	public void testBuscarPorNombre() {
		List<Agenda> agendas = daoService.listar();
		Agenda primera = agendas.get(0);
		assertNotNull(primera);
		assertNotNull(primera.getNombre());
		Agenda buscada = daoService.buscarPorNombre(agendas.get(0).getNombre());
		assertFalse(primera == buscada); // verificar que son dos objetos que apuntan a distinta posicion de memoria
		assertEquals(primera.getId(),buscada.getId());	
	}

	@Test
	public void testBuscarPorParteDelNombre() {
		List<Agenda> agendas = daoService.listar();
		Agenda primera = agendas.get(0);
		assertNotNull(primera);
		assertNotNull(primera.getNombre());
		Agenda buscada = daoService.buscarPorNombre(agendas.get(0).getNombre().substring(2,4));
		assertFalse(primera == buscada); // verificar que son dos objetos que apuntan a distinta posicion de memoria
		assertEquals(primera.getId(),buscada.getId());	
	}

	@Test
	public void testAgregarTarea() {
		List<Agenda> agendas = daoService.listar();
		Agenda primera = agendas.get(0);
		assertNotNull(primera);
		assertNotNull(primera.getNombre());
		Integer cantidadTareas = daoService.cantidadTareas(primera);
		assertEquals(Integer.valueOf(1), cantidadTareas);
		Tarea t = new Tarea();
		t.setDescripcion("HACER ALGO");
		daoService.agregarTarea(primera,t);
		cantidadTareas = daoService.cantidadTareas(primera);
		assertEquals(Integer.valueOf(2), cantidadTareas);
	}

	@Test
	public void testFinalizarTarea() {
		List<Agenda> agendas = daoService.listar();
		Agenda primera = agendas.get(0);
		assertNotNull(primera);
		assertNotNull(primera.getNombre());
		List<Tarea> finalizadas = daoService.completadas(primera);
		List<Tarea> pendientes = daoService.pendientes(primera);
		Integer cantidadTareas = daoService.cantidadTareas(primera);
		Integer cantidadFinalizadas= finalizadas.size();
		Integer cantidadPendientes = pendientes.size();
		assertEquals(Integer.valueOf(1), cantidadTareas);
		assertEquals(Integer.valueOf(1), cantidadPendientes);
		assertEquals(Integer.valueOf(0), cantidadFinalizadas);
		
		Tarea t = new Tarea();
		t.setDescripcion("HACER ALGO");
		daoService.agregarTarea(primera,t);
		cantidadTareas = daoService.cantidadTareas(primera);
		finalizadas = daoService.completadas(primera);
		pendientes = daoService.pendientes(primera);
		cantidadFinalizadas= finalizadas.size();
		cantidadPendientes = pendientes.size();
		assertEquals(Integer.valueOf(2), cantidadTareas);
		assertEquals(Integer.valueOf(2), cantidadPendientes);
		assertEquals(Integer.valueOf(0), cantidadFinalizadas);
		
		daoService.finalizarTarea(pendientes.get(1));
		cantidadTareas = daoService.cantidadTareas(primera);
		finalizadas = daoService.completadas(primera);
		pendientes = daoService.pendientes(primera);
		cantidadFinalizadas= finalizadas.size();
		cantidadPendientes = pendientes.size();
		assertEquals(Integer.valueOf(2), cantidadTareas);
		assertEquals(Integer.valueOf(1), cantidadPendientes);
		assertEquals(Integer.valueOf(1), cantidadFinalizadas);

		daoService.finalizarTarea(pendientes.get(0));
		cantidadTareas = daoService.cantidadTareas(primera);
		finalizadas = daoService.completadas(primera);
		pendientes = daoService.pendientes(primera);
		cantidadFinalizadas= finalizadas.size();
		cantidadPendientes = pendientes.size();
		assertEquals(Integer.valueOf(2), cantidadTareas);
		assertEquals(Integer.valueOf(0), cantidadPendientes);
		assertEquals(Integer.valueOf(2), cantidadFinalizadas);
	}


}
