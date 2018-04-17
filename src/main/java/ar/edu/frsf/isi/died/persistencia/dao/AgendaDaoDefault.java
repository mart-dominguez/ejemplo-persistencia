package ar.edu.frsf.isi.died.persistencia.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.edu.frsf.isi.died.persistencia.modelo.Agenda;
import ar.edu.frsf.isi.died.persistencia.modelo.Tarea;

public class AgendaDaoDefault implements AgendaDao {

	public void crear(String nombre) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConexionDB.get();
			ps = conn.prepareStatement("INSERT INTO AGENDA (NOMBRE) VALUES (?)");
			ps.setString(1, nombre);
			if (ps.executeUpdate() > 0)
				System.out.println("Correctamente creada agenda " + nombre);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public void borrarPorId(Integer id) {
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn.prepareStatement("DELETE FROM AGENDA WHERE ID = ?")) {
				ps.setInt(1, id);
				if (ps.executeUpdate() > 0)
					System.out.println("Correctamente borrada la agenda de id " + id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Agenda buscarPorId(Integer id) {
		Agenda agendaEncontrada = null;
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn.prepareStatement("SELECT ID,NOMBRE FROM AGENDA WHERE ID = ?")) {
				ps.setInt(1, id);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						agendaEncontrada = new Agenda();
						agendaEncontrada.setId(rs.getInt("ID"));
						agendaEncontrada.setNombre(rs.getString(2));
						agendaEncontrada.setTareas(this.tareas(agendaEncontrada));						
					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return agendaEncontrada;
	}

	public Agenda buscarPorNombre(String q) {
		Agenda agendaEncontrada = null;
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn.prepareStatement("SELECT ID,NOMBRE FROM AGENDA WHERE NOMBRE LIKE ?")) {
				ps.setString(1, "%" + q + "%");
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						agendaEncontrada = new Agenda();
						agendaEncontrada.setId(rs.getInt("ID"));
						agendaEncontrada.setNombre(rs.getString(2));
						agendaEncontrada.setTareas(this.tareas(agendaEncontrada));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return agendaEncontrada;
	}

	public void agregarTarea(Agenda a, Tarea t) {
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn
					.prepareStatement("INSERT INTO TAREA (DESCRIPCION,FINALIZADA,ID_AGENDA) VALUES(?,?,?)")) {
				ps.setString(1, t.getDescripcion());
				ps.setInt(2, 0);
				ps.setInt(3, a.getId());
				if (ps.executeUpdate() > 0)
					System.out.println("Correctamente insertada la tarea " + t.getDescripcion() + " en la agenda "
							+ a.getNombre());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void finalizarTarea(Tarea t) {
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn.prepareStatement("UPDATE TAREA SET FINALIZADA = ? WHERE ID = ?")) {
				ps.setInt(1, 1);
				ps.setInt(2, t.getId());
				if (ps.executeUpdate() > 0)
					System.out.println("Correctamente fianalizada la tarea ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Tarea> pendientes(Agenda a) {
		List<Tarea> listaTareas = new ArrayList<>();
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn.prepareStatement(
					"SELECT ID,DESCRIPCION,FINALIZADA FROM TAREA WHERE ID_AGENDA = ? AND FINALIZADA = 0")) {
				ps.setInt(1, a.getId());
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Tarea tareaAux = new Tarea();
						tareaAux.setId(rs.getInt("ID"));
						tareaAux.setDescripcion(rs.getString(2));
						tareaAux.setCompletada(rs.getInt(3) > 0);
						tareaAux.setAgenda(a);
						listaTareas.add(tareaAux);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaTareas;
	}

	public List<Tarea> completadas(Agenda a) {
		List<Tarea> listaTareas = new ArrayList<>();
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn.prepareStatement(
					"SELECT ID,DESCRIPCION,FINALIZADA FROM TAREA WHERE ID_AGENDA = ? AND FINALIZADA = 1")) {
				ps.setInt(1, a.getId());
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Tarea tareaAux = new Tarea();
						tareaAux.setId(rs.getInt("ID"));
						tareaAux.setDescripcion(rs.getString(2));
						tareaAux.setCompletada(rs.getInt(3) > 0);
						tareaAux.setAgenda(a);
						listaTareas.add(tareaAux);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaTareas;
	}

	public List<Tarea> tareas(Agenda a) {
		List<Tarea> listaTareas = new ArrayList<>();
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn
					.prepareStatement("SELECT ID,DESCRIPCION,FINALIZADA FROM TAREA WHERE ID_AGENDA = ?")) {
				ps.setInt(1, a.getId());
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Tarea tareaAux = new Tarea();
						tareaAux.setId(rs.getInt("ID"));
						tareaAux.setDescripcion(rs.getString(2));
						tareaAux.setCompletada(rs.getInt(3) > 0);
						tareaAux.setAgenda(a);
						listaTareas.add(tareaAux);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaTareas;
	}

	@Override
	public Integer cantidadAgendas() {
		Integer cantidad = 0;
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM AGENDA ")) {
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next())
						cantidad = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cantidad;
	}

	@Override
	public Integer cantidadTareas(Agenda a) {
		Integer cantidad = 0;
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM TAREA ")) {
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next())
						cantidad = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cantidad;
	}

	@Override
	public List<Agenda> listar() {
		List<Agenda>  agendasEncontrada = new ArrayList<>();
		try (Connection conn = ConexionDB.get()) {
			try (PreparedStatement ps = conn.prepareStatement("SELECT ID,NOMBRE FROM AGENDA ")) {
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Agenda auxAgenda = new Agenda();
						auxAgenda.setId(rs.getInt("ID"));
						auxAgenda.setNombre(rs.getString(2));
						auxAgenda.setTareas(this.tareas(auxAgenda));
						agendasEncontrada.add(auxAgenda);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return agendasEncontrada;
	}
}
