package ar.edu.frsf.isi.died.persistencia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionDB {
	private static final String url = "jdbc:mysql://localhost:3306/datasets";
	private static final String user = "root";
	private static final String pass = "root";

	private ConexionDB() {
		// no se pueden crear instancias de esta clase
	}

	public static Connection get() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(url, user, pass);
		return conn;
	}
}