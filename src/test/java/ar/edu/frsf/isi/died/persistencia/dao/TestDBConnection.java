package ar.edu.frsf.isi.died.persistencia.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ar.edu.frsf.isi.died.persistencia.modelo.Agenda;

public class TestDBConnection {
	private static final String url = "jdbc:mysql://localhost:3306/datasets";
	private static final String user = "root";
	private static final String pass = "root";

	@Test
	@Ignore
	public void test1() {
		String nombre = "agenda 1";
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pass);

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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void test2() {

		String nombre = "agenda 1";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pass);
			pstm = conn.prepareStatement("SELECT ID,NOMBRE FROM AGENDA ");
			rs = pstm.executeQuery();
			List<Agenda> agendasEncontrada = new ArrayList<>();
			while (rs.next()) {
				Agenda auxAgenda = new Agenda();
				auxAgenda.setId(rs.getInt("ID"));
				auxAgenda.setNombre(rs.getString(2));
				agendasEncontrada.add(auxAgenda);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(pstm!=null)
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

}
