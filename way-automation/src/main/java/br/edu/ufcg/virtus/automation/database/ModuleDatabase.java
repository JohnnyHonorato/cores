package br.edu.ufcg.virtus.automation.database;

import java.sql.Connection;
import java.sql.SQLException;

import br.edu.ufcg.virtus.automation.dataset.DBHelperData;
import br.edu.ufcg.virtus.automation.dataset.ModulesData;

public class ModuleDatabase {

	// Permission Data
	public static final String PERMISSION_ID_49 = "-49";
	public static final String PERMISSION_ID_50 = "-50";
	public static final String PERMISSION_ID_51 = "-51";
	public static final String PERMISSION_ID_52 = "-52";
	public static final String PERMISSION_ID_53 = "-53";
	public static final String PERMISSION_ID_54 = "-54";
	public static final String PERMISSION_ID_55 = "-55";
	public static final String PERMISSION_ID_56 = "-56";

	// Module ID Data
	public static final String MODULE_ID_4 = "-30";
	public static final String MODULE_ID_5 = "-31";
	public static final String MODULE_ID_6 = "-32";
	public static final String MODULE_ID_7 = "-33";
	public static final String MODULE_ID_8 = "-34";
	public static final String MODULE_ID_9 = "-35";
	public static final String MODULE_ID_10 = "-36";
	public static final String MODULE_ID_11 = "-37";

	// CORE DATABASE URL
	private static final String MODULE_DATABASE_URL = DBHelperData.DATABASE_URL;

	// SQL QUERY TO DELETE MODULE DATASET ON DATABASE
	public static final String DELETE_MODULE = "DELETE FROM core.module WHERE id NOT IN (3,2,1);";
	public static final String DELETE_PERMISSIONS = "DELETE FROM core.permission WHERE module_id NOT IN (3,2,1);";
	public static final String DELETE_ROLE_PERMISSIONS = "DELETE FROM core.role_permission WHERE role_id NOT IN (12,11,10,9,8,7,6,5,4,3,2,1);";

	public static void injectModuleData(String module_id, String module_name, String module_link, String module_path)
			throws SQLException {

		Connection dbConnection = DBConnection.getConnection(ModuleDatabase.MODULE_DATABASE_URL);

		String INSERT_MODULE = "INSERT INTO core.module(id, icon, name, link, open_mode, deleted, path_name, visible, created_on, updated_on, created_by, updated_by) \n"
				+ "VALUES ('" + module_id + "', 'fas fa-cog', '" + module_name + "', '" + module_link
				+ "', 'SPA', false, '" + module_path + "', true, null, null, null, null)";

		DBHelper.runDataModificationQuery(dbConnection, INSERT_MODULE);
	}

	public static void injectPermissionData(String permission_id, String module_id, String permission_name,
			String permission_label, String permission_description) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(ModuleDatabase.MODULE_DATABASE_URL);

		String INSERT_PERMISSION = "INSERT INTO core.permission(id, name, label, description, module_id) \n"
				+ "VALUES ('" + permission_id + "', '" + permission_name + "', '"
				+ permission_label + "', '" + permission_description + "', '"
				+ module_id + "');";

		DBHelper.runDataModificationQuery(dbConnection, INSERT_PERMISSION);
	}

	public static void clearModuleDataOnDatabase() throws SQLException {

		Connection dbConnection = DBConnection.getConnection(ModuleDatabase.MODULE_DATABASE_URL);

		DBHelper.runDataModificationQuery(dbConnection, ModuleDatabase.DELETE_ROLE_PERMISSIONS);
		DBHelper.runDataModificationQuery(dbConnection, ModuleDatabase.DELETE_PERMISSIONS);
		DBHelper.runDataModificationQuery(dbConnection, ModuleDatabase.DELETE_MODULE);
	}

	public static void closeModuleDatabaseConnection() throws SQLException {

		Connection dbConnection = DBConnection.getConnection(ModuleDatabase.MODULE_DATABASE_URL);

		if (!dbConnection.isClosed())
			dbConnection.close();
	}

	public static void injectModuleDataOnDatabase() throws SQLException {

		ModuleDatabase.injectModuleData(MODULE_ID_4, ModulesData.VALID_MODULE_NAME7, ModulesData.VALID_LINK7,
				ModulesData.VALID_PATH7);
		ModuleDatabase.injectPermissionData(PERMISSION_ID_49, MODULE_ID_4, ModulesData.VALID_PERMISSION_NAME,
				ModulesData.VALID_PERMISSION_LABEL, ModulesData.VALID_PERMISSION_DESCRIPTION);
		ModuleDatabase.injectModuleData(MODULE_ID_5, ModulesData.VALID_MODULE_NAME8, ModulesData.VALID_LINK8,
				ModulesData.VALID_PATH8);
		ModuleDatabase.injectPermissionData(PERMISSION_ID_50, MODULE_ID_5, ModulesData.VALID_PERMISSION_NAME,
				ModulesData.VALID_PERMISSION_LABEL, ModulesData.VALID_PERMISSION_DESCRIPTION);
		ModuleDatabase.injectModuleData(MODULE_ID_6, ModulesData.VALID_MODULE_NAME9, ModulesData.VALID_LINK9,
				ModulesData.VALID_PATH9);
		ModuleDatabase.injectPermissionData(PERMISSION_ID_51, MODULE_ID_6, ModulesData.VALID_PERMISSION_NAME,
				ModulesData.VALID_PERMISSION_LABEL, ModulesData.VALID_PERMISSION_DESCRIPTION);
		ModuleDatabase.injectModuleData(MODULE_ID_7, ModulesData.VALID_MODULE_NAME10, ModulesData.VALID_LINK10,
				ModulesData.VALID_PATH10);
		ModuleDatabase.injectPermissionData(PERMISSION_ID_52, MODULE_ID_7, ModulesData.VALID_PERMISSION_NAME,
				ModulesData.VALID_PERMISSION_LABEL, ModulesData.VALID_PERMISSION_DESCRIPTION);
		ModuleDatabase.injectModuleData(MODULE_ID_8, ModulesData.VALID_MODULE_NAME12, ModulesData.VALID_LINK12,
				ModulesData.VALID_PATH12);
		ModuleDatabase.injectPermissionData(PERMISSION_ID_53, MODULE_ID_8, ModulesData.VALID_PERMISSION_NAME,
				ModulesData.VALID_PERMISSION_LABEL, ModulesData.VALID_PERMISSION_DESCRIPTION);
		ModuleDatabase.injectModuleData(MODULE_ID_9, ModulesData.VALID_MODULE_NAME14, ModulesData.VALID_LINK14,
				ModulesData.VALID_PATH14);
		ModuleDatabase.injectPermissionData(PERMISSION_ID_54, MODULE_ID_9, ModulesData.VALID_PERMISSION_NAME,
				ModulesData.VALID_PERMISSION_LABEL, ModulesData.VALID_PERMISSION_DESCRIPTION);
		ModuleDatabase.injectModuleData(MODULE_ID_10, ModulesData.VALID_MODULE_NAME15, ModulesData.VALID_LINK15,
				ModulesData.VALID_PATH15);
		ModuleDatabase.injectPermissionData(PERMISSION_ID_55, MODULE_ID_10, ModulesData.VALID_PERMISSION_NAME,
				ModulesData.VALID_PERMISSION_LABEL, ModulesData.VALID_PERMISSION_DESCRIPTION);
		ModuleDatabase.injectModuleData(MODULE_ID_11, ModulesData.VALID_MODULE_NAME16, ModulesData.VALID_LINK16,
				ModulesData.VALID_PATH16);
		ModuleDatabase.injectPermissionData(PERMISSION_ID_56, MODULE_ID_11, ModulesData.VALID_PERMISSION_NAME,
				ModulesData.VALID_PERMISSION_LABEL, ModulesData.VALID_PERMISSION_DESCRIPTION);
	}
}