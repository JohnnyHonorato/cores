package br.edu.ufcg.virtus.automation.database;

import java.sql.Connection;
import java.sql.SQLException;

import br.edu.ufcg.virtus.automation.dataset.DBHelperData;
import br.edu.ufcg.virtus.automation.dataset.ModulesData;
import br.edu.ufcg.virtus.automation.dataset.RolesData;

public class RolesDatabase {

	// ROLE DATABASE URL
	private static final String ROLES_DATABASE_URL = DBHelperData.DATABASE_URL;

	// SQL QUERIES TO INSERT ROLES DATASET ON DATABASE
	public static void createRoles(String id, String name) throws SQLException {
		Connection dbConnection = DBConnection.getConnection(RolesDatabase.ROLES_DATABASE_URL);
		String role = "INSERT INTO core.role(id, name, deleted, created_on, updated_on, created_by, updated_by) \n"
				+ "VALUES (" + id + ", '" + name + "', 'false', null, null, null, null);";
		DBHelper.runDataModificationQuery(dbConnection, role);
	}

	// SQL QUERY TO DELETE ROLES DATASET ON DATABASE
	public static final String DELETE_ROLE = "DELETE FROM core.role WHERE id NOT IN (2,1);";

	public static void clearRolesDataOnDatabase() throws SQLException {
		Connection dbConnection = DBConnection.getConnection(RolesDatabase.ROLES_DATABASE_URL);
		DBHelper.runDataModificationQuery(dbConnection, RolesDatabase.DELETE_ROLE);
	}

	public static void closeRolesDatabaseConnection() throws SQLException {
		Connection dbConnection = DBConnection.getConnection(RolesDatabase.ROLES_DATABASE_URL);
		if (!dbConnection.isClosed())
			dbConnection.close();
	}

	public static void injectRolesDataOnDatabase() throws SQLException {
		RolesDatabase.createRoles(RolesData.ID2, RolesData.VALID_ROLE_NAME_2);
		RolesDatabase.createRoles(RolesData.ID3, RolesData.VALID_ROLE_NAME_3);
		RolesDatabase.createRoles(RolesData.ID4, RolesData.VALID_ROLE_NAME_4);
		RolesDatabase.createRoles(RolesData.ID5, RolesData.VALID_ROLE_NAME_5);
		RolesDatabase.createRoles(RolesData.ID6, RolesData.VALID_ROLE_NAME_6);
		RolesDatabase.createRoles(RolesData.ID7, RolesData.VALID_ROLE_NAME_7);
		RolesDatabase.createRoles(RolesData.ID8, RolesData.VALID_ROLE_NAME_8);
		RolesDatabase.createRoles(RolesData.ID9, RolesData.VALID_ROLE_NAME_9);
	}

	public static void injectModuleDataOnDatabase() throws SQLException {

		ModuleDatabase.injectModuleData(RolesData.MODULE_ID, RolesData.MODULE_NAME, RolesData.LINK,
				ModulesData.VALID_PATH1);
		ModuleDatabase.injectPermissionData(RolesData.PERMISSION_ID, RolesData.MODULE_ID, RolesData.PERMISSION_NAME,
				RolesData.LABEL, RolesData.DESCRIPTION);
	}

}
