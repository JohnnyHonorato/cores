package br.edu.ufcg.virtus.automation.database;

import java.sql.Connection;
import java.sql.SQLException;
import br.edu.ufcg.virtus.automation.dataset.CommonData;
import br.edu.ufcg.virtus.automation.dataset.DBHelperData;
import br.edu.ufcg.virtus.automation.dataset.TrackerBoardData;

public class TrackerBoardDatabase {

	public static final String TRACKER_DB_URL = DBHelperData.DATABASE_URL;

	public static final String MODULE_TRACKER_ID = "2";
	private static final String USER_ID = "312";

	public static final String DELETE_BOARD_TABLE_DATA = "TRUNCATE tracker.tracker_model CASCADE;";
	public static final String DELETE_STATUS_TABLE_DATA = "DELETE FROM tracker.status;";

// BOARD METHOD
	public static void injectBoardOnDatabase(String id, String name, String module_id, String description,
			String created_by) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerBoardDatabase.TRACKER_DB_URL);
		String TrackerModelQuery = "INSERT INTO tracker.tracker_model(id, name, module_id, description, deleted, created_on, updated_on, created_by, updated_by) VALUES ("
				+ id + ", '" + name + "', " + module_id + ", '" + description + "', 'false', null, null," + created_by
				+ ", null);";

		DBHelper.runDataModificationQuery(dbConnection, TrackerModelQuery);
	}

// STATUS METHOD
	public static void injectStatusOnDatabase(String statusId, String statusName, String statusPosition,
			String statusTrackerModelId) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerBoardDatabase.TRACKER_DB_URL);
		String StatusQuery = "INSERT INTO tracker.status(id, name, position, deleted, tracker_model_id) VALUES ("
				+ statusId + ", '" + statusName + "', " + statusPosition + ", 'false', " + statusTrackerModelId + ");";
		DBHelper.runDataModificationQuery(dbConnection, StatusQuery);
	}

// TRANSITIONS METHOD
	public static void injectTransitionOnDatabase(String transitionId, String attributes, String sourceId,
			String targetId, String boardId) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerBoardDatabase.TRACKER_DB_URL);
		String StatusQuery = "INSERT INTO tracker.transition(id, attributes, source_id, target_id, deleted, tracker_model_id) VALUES ("
				+ transitionId + ", '" + attributes + "', " + sourceId + ", " + targetId + ", 'false', " + boardId
				+ ");";
		DBHelper.runDataModificationQuery(dbConnection, StatusQuery);
	}

// ATTRIBUTES METHOD
	public static void injectAttributeValueOnDatabase(String id, String value, String value_complement,
			String tracker_id, String attribute_id) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerBoardDatabase.TRACKER_DB_URL);
		String attributeValue = "INSERT INTO tracker.attribute_value(id, value, value_complement, tracker_id, attribute_id) VALUES ("
				+ id + ", '" + value + "', '" + value_complement + "', " + tracker_id + ", " + attribute_id + ");";
		DBHelper.runDataModificationQuery(dbConnection, attributeValue);
	}

	public static void injectAttributeIntegerOnDatabase(String id, String title, String required, String position,
			String x_axis, String y_axis, String min_value, String max_value, String show_on_card,
			String tracker_model_id) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerBoardDatabase.TRACKER_DB_URL);
		String AttInjectQuery = "INSERT INTO tracker.attribute(id, title, type, related_attribute_id, required, position, x_axis, y_axis, number_of_columns, min_value, max_value, max_length, list_values, show_on_card, needs_value_complement, currency, min_date, max_date, deleted, tracker_model_id) VALUES ("
				+ id + ", '" + title + "', 'INTEGER', null, '" + required + "', " + position + ", " + x_axis + ", "
				+ y_axis + ", 3, " + min_value + ", " + max_value + ", null, null, '" + show_on_card
				+ "', 'false', null, null, null, 'false', " + tracker_model_id + ");";

		DBHelper.runDataModificationQuery(dbConnection, AttInjectQuery);
	}

// CLEAR METHODS
	public static void clearBoardsDataOnDatabase() throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerBoardDatabase.TRACKER_DB_URL);

		DBHelper.runDataModificationQuery(dbConnection, TrackerBoardDatabase.DELETE_BOARD_TABLE_DATA);
		DBHelper.runDataModificationQuery(dbConnection, TrackerBoardDatabase.DELETE_STATUS_TABLE_DATA);

	}

	public static void closeBoardsDatabaseConnection() throws SQLException {
		Connection dbConnection = DBConnection.getConnection(TrackerBoardDatabase.TRACKER_DB_URL);

		if (!dbConnection.isClosed())
			dbConnection.close();
	}

	public static void injectBoardsDataOnDatabase() throws SQLException {
		// BOARD 1
		injectBoardOnDatabase(TrackerBoardData.BOARD_ID_1, TrackerBoardData.BOARD_NAME_1, MODULE_TRACKER_ID,
				TrackerBoardData.BOARD_DESCRIPTION_1, USER_ID);

		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_1, TrackerBoardData.TODO, TrackerBoardData.POSITION_0,
				TrackerBoardData.BOARD_ID_1);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_2, TrackerBoardData.ON_GOING, TrackerBoardData.POSITION_1,
				TrackerBoardData.BOARD_ID_1);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_3, TrackerBoardData.DONE, TrackerBoardData.POSITION_2,
				TrackerBoardData.BOARD_ID_1);

		injectTransitionOnDatabase(TrackerBoardData.TRANSITION_ID_1, CommonData.EMPTY_VALUE,
				TrackerBoardData.TRANSITION_ID_1, TrackerBoardData.TRANSITION_ID_1, TrackerBoardData.BOARD_ID_1);
		injectTransitionOnDatabase(TrackerBoardData.TRANSITION_ID_2, CommonData.EMPTY_VALUE,
				TrackerBoardData.TRANSITION_ID_2, TrackerBoardData.TRANSITION_ID_2, TrackerBoardData.BOARD_ID_1);
		injectTransitionOnDatabase(TrackerBoardData.TRANSITION_ID_3, CommonData.EMPTY_VALUE,
				TrackerBoardData.TRANSITION_ID_3, TrackerBoardData.TRANSITION_ID_3, TrackerBoardData.BOARD_ID_1);

		injectAttributeIntegerOnDatabase(TrackerBoardData.ATT_INTEGER_ID_1, TrackerBoardData.ATT_INTEGER_TITLE_1,
				CommonData.FALSE, TrackerBoardData.ATT_INTEGER_POSITION_1, TrackerBoardData.ATT_INTEGER_X_1,
				TrackerBoardData.ATT_INTEGER_Y_1, TrackerBoardData.ATT_INTEGER_MIN_VALUE_1,
				TrackerBoardData.ATT_INTEGER_MAX_VALUE_1, CommonData.TRUE, TrackerBoardData.BOARD_ID_1);

		// BOARD 2
		injectBoardOnDatabase(TrackerBoardData.BOARD_ID_2, TrackerBoardData.BOARD_NAME_3, MODULE_TRACKER_ID,
				TrackerBoardData.BOARD_DESCRIPTION_3, USER_ID);

		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_4, TrackerBoardData.TODO, TrackerBoardData.POSITION_0,
				TrackerBoardData.BOARD_ID_2);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_5, TrackerBoardData.ON_GOING, TrackerBoardData.POSITION_1,
				TrackerBoardData.BOARD_ID_2);

		injectAttributeIntegerOnDatabase(TrackerBoardData.ATT_INTEGER_ID_2, TrackerBoardData.ATT_INTEGER_TITLE_2,
				CommonData.FALSE, TrackerBoardData.ATT_INTEGER_POSITION_1, TrackerBoardData.ATT_INTEGER_X_1,
				TrackerBoardData.ATT_INTEGER_Y_1, TrackerBoardData.ATT_INTEGER_MIN_VALUE_1,
				TrackerBoardData.ATT_INTEGER_MAX_VALUE_1, CommonData.TRUE, TrackerBoardData.BOARD_ID_2);

		// BOARD 3
		injectBoardOnDatabase(TrackerBoardData.BOARD_ID_3, TrackerBoardData.BOARD_NAME_11, MODULE_TRACKER_ID,
				TrackerBoardData.BOARD_DESCRIPTION_1, USER_ID);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_5, TrackerBoardData.TODO, TrackerBoardData.POSITION_0,
				TrackerBoardData.BOARD_ID_3);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_6, TrackerBoardData.ON_GOING, TrackerBoardData.POSITION_1,
				TrackerBoardData.BOARD_ID_3);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_7, TrackerBoardData.DONE, TrackerBoardData.POSITION_2,
				TrackerBoardData.BOARD_ID_3);

		// BOARD 4
		injectBoardOnDatabase(TrackerBoardData.BOARD_ID_5, TrackerBoardData.BOARD_NAME_5, MODULE_TRACKER_ID,
				TrackerBoardData.BOARD_DESCRIPTION_1, USER_ID);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_8, TrackerBoardData.TODO, TrackerBoardData.POSITION_0,
				TrackerBoardData.BOARD_ID_5);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_9, TrackerBoardData.ON_GOING, TrackerBoardData.POSITION_1,
				TrackerBoardData.BOARD_ID_5);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_10, TrackerBoardData.DONE, TrackerBoardData.POSITION_2,
				TrackerBoardData.BOARD_ID_5);

		// BOARD 5
		injectBoardOnDatabase(TrackerBoardData.BOARD_ID_6, TrackerBoardData.BOARD_NAME_6, MODULE_TRACKER_ID,
				TrackerBoardData.BOARD_DESCRIPTION_1, USER_ID);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_11, TrackerBoardData.TODO, TrackerBoardData.POSITION_0,
				TrackerBoardData.BOARD_ID_6);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_12, TrackerBoardData.ON_GOING, TrackerBoardData.POSITION_1,
				TrackerBoardData.BOARD_ID_6);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_13, TrackerBoardData.DONE, TrackerBoardData.POSITION_2,
				TrackerBoardData.BOARD_ID_6);

		// BOARD 6
		injectBoardOnDatabase(TrackerBoardData.BOARD_ID_7, TrackerBoardData.BOARD_NAME_7, MODULE_TRACKER_ID,
				TrackerBoardData.BOARD_DESCRIPTION_1, USER_ID);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_14, TrackerBoardData.TODO, TrackerBoardData.POSITION_0,
				TrackerBoardData.BOARD_ID_7);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_15, TrackerBoardData.ON_GOING, TrackerBoardData.POSITION_1,
				TrackerBoardData.BOARD_ID_7);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_16, TrackerBoardData.DONE, TrackerBoardData.POSITION_2,
				TrackerBoardData.BOARD_ID_7);

		// BOARD 7
		injectBoardOnDatabase(TrackerBoardData.BOARD_ID_8, TrackerBoardData.BOARD_NAME_8, MODULE_TRACKER_ID,
				TrackerBoardData.BOARD_DESCRIPTION_1, USER_ID);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_17, TrackerBoardData.TODO, TrackerBoardData.POSITION_0,
				TrackerBoardData.BOARD_ID_8);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_18, TrackerBoardData.ON_GOING, TrackerBoardData.POSITION_1,
				TrackerBoardData.BOARD_ID_8);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_19, TrackerBoardData.DONE, TrackerBoardData.POSITION_2,
				TrackerBoardData.BOARD_ID_8);

		// BOARD 8
		injectBoardOnDatabase(TrackerBoardData.BOARD_ID_9, TrackerBoardData.BOARD_NAME_9, MODULE_TRACKER_ID,
				TrackerBoardData.BOARD_DESCRIPTION_1, USER_ID);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_20, TrackerBoardData.TODO, TrackerBoardData.POSITION_0,
				TrackerBoardData.BOARD_ID_9);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_21, TrackerBoardData.ON_GOING, TrackerBoardData.POSITION_1,
				TrackerBoardData.BOARD_ID_9);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_22, TrackerBoardData.DONE, TrackerBoardData.POSITION_2,
				TrackerBoardData.BOARD_ID_9);

		// BOARD 9
		injectBoardOnDatabase(TrackerBoardData.BOARD_ID_10, TrackerBoardData.BOARD_NAME_10, MODULE_TRACKER_ID,
				TrackerBoardData.BOARD_DESCRIPTION_1, USER_ID);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_23, TrackerBoardData.TODO, TrackerBoardData.POSITION_0,
				TrackerBoardData.BOARD_ID_10);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_24, TrackerBoardData.ON_GOING, TrackerBoardData.POSITION_1,
				TrackerBoardData.BOARD_ID_10);
		
		// BOARD 12
		injectBoardOnDatabase(TrackerBoardData.BOARD_ID_12, TrackerBoardData.BOARD_NAME_12, MODULE_TRACKER_ID,
				TrackerBoardData.BOARD_DESCRIPTION_12, USER_ID);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_25, TrackerBoardData.TODO, TrackerBoardData.POSITION_0,
				TrackerBoardData.BOARD_ID_12);
		injectStatusOnDatabase(TrackerBoardData.STATUS_ID_26, TrackerBoardData.ON_GOING, TrackerBoardData.POSITION_1,
				TrackerBoardData.BOARD_ID_12);
	}
	
}
