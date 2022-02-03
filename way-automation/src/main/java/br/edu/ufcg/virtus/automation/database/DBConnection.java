package br.edu.ufcg.virtus.automation.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.edu.ufcg.virtus.automation.dataset.DBHelperData;

public class DBConnection {
	
	private static Connection connection;
	private DBConnection(String url) {
		try {
			Class.forName(DBHelperData.DB_DRIVER);
			connection = DriverManager.getConnection(url, DBHelperData.DB_USER, DBHelperData.DB_PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection(String dataBaseUrl) throws SQLException {
		if (connection == null || (connection.isClosed())){
			new DBConnection(dataBaseUrl);
		}
		return connection;
	}
}
