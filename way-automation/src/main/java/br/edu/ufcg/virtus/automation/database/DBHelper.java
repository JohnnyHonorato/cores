package br.edu.ufcg.virtus.automation.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {
	
	public static void runDataModificationQuery(Connection connection, String sqlQuery) throws SQLException {
		
		PreparedStatement statement = connection.prepareStatement(sqlQuery);
		
		try {
			connection.setAutoCommit(false);
			statement.execute();
			
			connection.commit();
		} catch (SQLException exception) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		} finally {
			if(statement != null) {
				statement.close();
			}
			connection.setAutoCommit(true);
		}
	}
}
