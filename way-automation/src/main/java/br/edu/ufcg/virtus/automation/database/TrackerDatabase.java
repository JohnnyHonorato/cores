package br.edu.ufcg.virtus.automation.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import br.edu.ufcg.virtus.automation.dataset.ConfigData;
import br.edu.ufcg.virtus.automation.dataset.DBHelperData;
import br.edu.ufcg.virtus.automation.dataset.TrackerData;
import br.edu.ufcg.virtus.automation.utils.Utils;

public class TrackerDatabase {

	// THIS CLASS IS OBSOLETE, PLEASE MOVE ALL NEEDS TO TRACKERBOARDDATABASE OR TO TRACKERCARDDATABASE
	
	public static final String TRACKER_DB_URL = DBHelperData.DATABASE_URL;
	
	public static final String MODULE_ID = "2";
	
	// Board 1 -> Filter Tests
	public static final String TRACKER_MODEL1_ID = "1";

	// Status 1 -> Filter Tests
	public static final String STATUS1_ID = "1";

	// Status 2 -> Filter Tests
	public static final String STATUS2_ID = "2";

	// Status 3 -> Filter Tests
	public static final String STATUS3_ID = "3";

	// Tag 1 -> Filter Tests
	public static final String TAG1_ID = "1";

	// Tag 2 -> Filter Tests
	public static final String TAG2_ID = "2";

	// Card 1 -> Filter Tests
	public static final String TRACKER1_ID = "1";

	// Card 2 -> Filter Tests
	public static final String TRACKER2_ID = "2";

	// Card 3 -> Filter Tests
	public static final String TRACKER3_ID = "3";

	// TRACKER TAG -> Filter Tests
	public static final String TRACKER_TAG1_ID = "1";
	public static final String TRACKER_TAG2_ID = "2";
	public static final String TRACKER_TAG3_ID = "3";

	// ASSIGNEE -> Filter Tests
	public static final String ASSIGNEE1_ID = "1";
	public static final String ASSIGNEE2_ID = "2";
	public static final String ASSIGNEE3_ID = "3";
	public static final String ASSIGNEE4_ID = "4";
	public static final String ASSIGNEE5_ID = "5";
	public static final String ASSIGNEE6_ID = "6";

	// PEOPLE -> Filter Tests
	public static final String PEOPLE_ID = "7";
	public static final String PEOPLE_ID_2 = "555";
	public static final String PEOPLE_ID_3 = "76";
	public static final String PEOPLE_ID_4 = "6";
	public static final String PEOPLE_ID_5 = "7";
	public static final String PEOPLE_ID_6 = "9";
	
	public static final String FILE1_DATA_ID = "1";
	public static final String FILE2_DATA_ID = "2";
	public static final String FILE3_DATA_ID = "3";
	public static final String FILE4_DATA_ID = "4";
	public static final String FILE5_DATA_ID = "5";

	// FILE 1 -> Test Case: cardAttachmentUploadSuccessfully
	public static final String BOARD2_ID = "2";
	
	public static final String STATUS4_ID = "4";
	public static final String STATUS5_ID = "5";
	public static final String STATUS6_ID = "6";

	public static final String TRACKER4_ID = "4";
	
	// FILE 2 -> Test Case: cardAttachmentDownloadSuccessfully
	public static final String TRACKER_MODEL3_ID = "3";
		
	public static final String STATUS7_ID = "7";
	public static final String STATUS8_ID = "8";
	public static final String STATUS9_ID = "9";
	
	public static final String TRACKER5_ID = "5";

	public static final String DELETE_BOARD_TABLE_DATA = "TRUNCATE tracker.tracker_model CASCADE;";
	public static final String DELETE_TRACKER_TAG_TABLE_DATA = "TRUNCATE tracker.tracker_tag CASCADE;";
	public static final String DELETE_TRACKER_ASSIGNEE_TABLE_DATA = "TRUNCATE tracker.tracker_assignee CASCADE;";
	public static final String DELETE_STATUS_TABLE_DATA = "DELETE FROM tracker.status;";
	public static final String DELETE_TRACKER_TABLE_DATA = "DELETE FROM tracker.tracker;";
	public static final String DELETE_TAG_TABLE_DATA = "DELETE FROM tracker.tag;";
	public static final String DELETE_ASSIGNEE_TABLE_DATA = "DELETE FROM tracker.assignee;";

	// FILE - CARD VIEW
	private static final String TRACKER_MODEL5_ID = "5";

	public static final String STATUS13_ID = "13";
	private static final String STATUS14_ID = "14";
	private static final String STATUS15_ID = "15";

	private static final String TRACKER7_ID = "7";
	
	public static final String FILE6_DATA_ID = "6";
	public static final String FILE7_DATA_ID = "7";
	public static final String FILE8_DATA_ID = "8";
	public static final String FILE9_DATA_ID = "9";

	public static void injectTrackersDataOnDatabase() throws SQLException {

		// Filters Test
		injectTrackerModelOnDatabase(TRACKER_MODEL1_ID, TrackerData.BOARD_1, TRACKER_MODEL1_ID,
				TrackerData.BOARD1_DESCRIPTION, TrackerData.DELETE_FALSE, TrackerData.CREATED_ON, PEOPLE_ID);

		injectStatusOnDatabase(STATUS1_ID, TrackerData.TODO, TrackerData.POSITION1, TrackerData.DELETE_FALSE, TRACKER_MODEL1_ID);
		injectStatusOnDatabase(STATUS2_ID, TrackerData.ON_GOING, TrackerData.POSITION2, TrackerData.DELETE_FALSE, TRACKER_MODEL1_ID);
		injectStatusOnDatabase(STATUS3_ID, TrackerData.DONE, TrackerData.POSITION3, TrackerData.DELETE_FALSE, TRACKER_MODEL1_ID);

		injectTrackerOnDatabase(TRACKER1_ID, TrackerData.CARD1_TITLE, TrackerData.CARD1_DESCRIPTION, TrackerData.CARD1_DUE_DATE, TrackerData.CREATED_ON, PEOPLE_ID, STATUS1_ID, TRACKER_MODEL1_ID);
		injectTrackerOnDatabase(TRACKER2_ID, TrackerData.CARD2_TITLE, TrackerData.CARD2_DESCRIPTION, TrackerData.CURRENT_DATE, TrackerData.CREATED_ON, PEOPLE_ID, STATUS1_ID, TRACKER_MODEL1_ID);
		injectTrackerOnDatabase(TRACKER3_ID, TrackerData.CARD3_TITLE, TrackerData.CARD3_DESCRIPTION, TrackerData.CURRENT_DATE, TrackerData.CREATED_ON, PEOPLE_ID, STATUS1_ID, TRACKER_MODEL1_ID);


		injectTagOnDatabase(TAG1_ID, TrackerData.TAG_NAME_1, TrackerData.TAG1_COLOR, TRACKER_MODEL1_ID);
		injectTagOnDatabase(TAG2_ID, TrackerData.TAG_NAME_2, TrackerData.TAG2_COLOR, TRACKER_MODEL1_ID);
				
		injectTrackerAndTagRelationshipToDataBase(TRACKER_TAG1_ID, TRACKER1_ID, TAG1_ID);
		injectTrackerAndTagRelationshipToDataBase(TRACKER_TAG2_ID, TRACKER1_ID, TAG2_ID);
		injectTrackerAndTagRelationshipToDataBase(TRACKER_TAG3_ID, TRACKER2_ID, TAG2_ID);
		
		injectAssigneeToDataBase(ASSIGNEE1_ID, PEOPLE_ID, TrackerData.CURRENT_DATE, PEOPLE_ID, TRACKER_MODEL1_ID);

		injectAssigneeToDataBase(ASSIGNEE2_ID, PEOPLE_ID_2, TrackerData.CURRENT_DATE, PEOPLE_ID_2, TRACKER_MODEL1_ID);

		injectAssigneeToDataBase(ASSIGNEE3_ID, PEOPLE_ID_3, TrackerData.CURRENT_DATE, PEOPLE_ID_3, TRACKER_MODEL1_ID);

		injectAssigneeToDataBase(ASSIGNEE4_ID, PEOPLE_ID_4, TrackerData.CURRENT_DATE, PEOPLE_ID_4, TRACKER_MODEL1_ID);

		injectAssigneeToDataBase(ASSIGNEE5_ID, PEOPLE_ID_5, TrackerData.CURRENT_DATE, PEOPLE_ID_5, TRACKER_MODEL1_ID);

		injectAssigneeToDataBase(ASSIGNEE6_ID, PEOPLE_ID_6, TrackerData.CURRENT_DATE, PEOPLE_ID_6, TRACKER_MODEL1_ID);

		injectTrackerAndAssigneeRelationshipToDataBase(TRACKER1_ID, ASSIGNEE1_ID);

		injectTrackerAndAssigneeRelationshipToDataBase(TRACKER1_ID, ASSIGNEE2_ID);

		injectTrackerAndAssigneeRelationshipToDataBase(TRACKER1_ID, ASSIGNEE3_ID);

		injectTrackerAndAssigneeRelationshipToDataBase(TRACKER1_ID, ASSIGNEE5_ID);

		injectTrackerAndAssigneeRelationshipToDataBase(TRACKER2_ID, ASSIGNEE6_ID);

		injectTrackerAndAssigneeRelationshipToDataBase(TRACKER2_ID, ASSIGNEE2_ID);
		
		Utils.copyFile(Utils.getProjectPath() + TrackerData.FILE_PATH1,
				Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY);
		injectTrackerAttachmentDataToDatabase(FILE2_DATA_ID, TrackerData.FILE_NAME1, TrackerData.FILE_NAME1, TrackerData.FILE_SIZE1,
				Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY + TrackerData.FILE_NAME1, TRACKER1_ID);
		
		Utils.copyFile(Utils.getProjectPath() + TrackerData.FILE_PATH2,
				Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY);
		injectTrackerAttachmentDataToDatabase(FILE3_DATA_ID, TrackerData.FILE_NAME2, TrackerData.FILE_NAME2, TrackerData.FILE_SIZE2,
				Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY + TrackerData.FILE_NAME2, TRACKER2_ID);
		
		Utils.copyFile(Utils.getProjectPath() + TrackerData.FILE_PATH3,
				Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY);
		injectTrackerAttachmentDataToDatabase(FILE4_DATA_ID, TrackerData.FILE_NAME3, TrackerData.FILE_NAME3, TrackerData.FILE_SIZE3,
				Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY + TrackerData.FILE_NAME3, TRACKER2_ID);
		
		// FILE 1 -> Test Case: cardAttachmentUploadSuccessfully
		injectTrackerModelOnDatabase(BOARD2_ID, TrackerData.BOARD2_NAME, BOARD2_ID,
				TrackerData.BOARD2_DESCRIPTION, TrackerData.DELETE_FALSE, TrackerData.CREATED_ON, PEOPLE_ID);

		injectStatusOnDatabase(STATUS4_ID, TrackerData.TODO, TrackerData.POSITION1, TrackerData.DELETE_FALSE, BOARD2_ID);
		injectStatusOnDatabase(STATUS5_ID, TrackerData.ON_GOING, TrackerData.POSITION2, TrackerData.DELETE_FALSE, BOARD2_ID);
		injectStatusOnDatabase(STATUS6_ID, TrackerData.DONE, TrackerData.POSITION3, TrackerData.DELETE_FALSE, BOARD2_ID);

		injectTrackerOnDatabase(TRACKER4_ID, TrackerData.CARD4_TITLE, TrackerData.CARD4_DESCRIPTION, TrackerData.CURRENT_DATE, TrackerData.CREATED_ON, PEOPLE_ID, STATUS4_ID, BOARD2_ID);
				
		Utils.copyFile(Utils.getProjectPath() + TrackerData.FILE_PATH1,	Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY);

		// FILE 2 -> Test Case: cardAttachmentDownloadSuccessfully
		injectTrackerModelOnDatabase(TRACKER_MODEL3_ID, TrackerData.BOARD3_NAME, TRACKER_MODEL3_ID,
				TrackerData.BOARD3_DESCRIPTION, TrackerData.DELETE_FALSE, TrackerData.CREATED_ON, PEOPLE_ID);

		injectStatusOnDatabase(STATUS7_ID, TrackerData.TODO, TrackerData.POSITION1, TrackerData.DELETE_FALSE, TRACKER_MODEL3_ID);
		injectStatusOnDatabase(STATUS8_ID, TrackerData.ON_GOING, TrackerData.POSITION2, TrackerData.DELETE_FALSE, TRACKER_MODEL3_ID);
		injectStatusOnDatabase(STATUS9_ID, TrackerData.DONE, TrackerData.POSITION3, TrackerData.DELETE_FALSE, TRACKER_MODEL3_ID);

		injectTrackerOnDatabase(TRACKER5_ID, TrackerData.CARD5_TITLE, TrackerData.CARD5_DESCRIPTION, TrackerData.CURRENT_DATE, TrackerData.CREATED_ON, PEOPLE_ID, STATUS8_ID, TRACKER_MODEL3_ID);

		Utils.copyFile(Utils.getProjectPath() + TrackerData.FILE_PATH4,	Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY);
		injectTrackerAttachmentDataToDatabase(FILE5_DATA_ID, TrackerData.FILE_NAME4, TrackerData.FILE_NAME4, TrackerData.FILE_SIZE4,
				Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY + TrackerData.FILE_NAME4, TRACKER5_ID);
		
		// FILE - CARD VIEW
		injectTrackerModelOnDatabase(TRACKER_MODEL5_ID, TrackerData.BOARD5_NAME, TRACKER_MODEL5_ID,
				TrackerData.BOARD5_DESCRIPTION, TrackerData.DELETE_FALSE, TrackerData.CREATED_ON, PEOPLE_ID);
		injectStatusOnDatabase(STATUS13_ID, TrackerData.TODO, TrackerData.POSITION1, TrackerData.DELETE_FALSE, TRACKER_MODEL5_ID);
		injectStatusOnDatabase(STATUS14_ID, TrackerData.ON_GOING, TrackerData.POSITION2, TrackerData.DELETE_FALSE, TRACKER_MODEL5_ID);
		injectStatusOnDatabase(STATUS15_ID, TrackerData.DONE, TrackerData.POSITION3, TrackerData.DELETE_FALSE, TRACKER_MODEL5_ID);
		injectTrackerOnDatabase(TRACKER7_ID, TrackerData.CARD7_TITLE, TrackerData.CARD7_DESCRIPTION, TrackerData.CURRENT_DATE, TrackerData.CREATED_ON, PEOPLE_ID, STATUS14_ID, TRACKER_MODEL5_ID);
		
		Utils.copyFile(Utils.getProjectPath() + TrackerData.FILE_PATH1,	Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY);
		injectTrackerAttachmentDataToDatabase(FILE6_DATA_ID, TrackerData.FILE_NAME1, TrackerData.FILE_NAME1, TrackerData.FILE_SIZE1,
				Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY + TrackerData.FILE_NAME1, TRACKER7_ID);
		
		Utils.copyFile(Utils.getProjectPath() + TrackerData.FILE_PATH2,	Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY);
		injectTrackerAttachmentDataToDatabase(FILE7_DATA_ID, TrackerData.FILE_NAME2, TrackerData.FILE_NAME2, TrackerData.FILE_SIZE2,
				Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY + TrackerData.FILE_NAME2, TRACKER7_ID);
		
		Utils.copyFile(Utils.getProjectPath() + TrackerData.FILE_PATH3,	Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY);
		injectTrackerAttachmentDataToDatabase(FILE8_DATA_ID, TrackerData.FILE_NAME3, TrackerData.FILE_NAME3, TrackerData.FILE_SIZE3,
				Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY + TrackerData.FILE_NAME3, TRACKER7_ID);
		
		Utils.copyFile(Utils.getProjectPath() + TrackerData.FILE_PATH4,	Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY);
		injectTrackerAttachmentDataToDatabase(FILE9_DATA_ID, TrackerData.FILE_NAME4, TrackerData.FILE_NAME4, TrackerData.FILE_SIZE4,
				Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY + TrackerData.FILE_NAME4, TRACKER7_ID);
	}

	public static void clearTrackersDataOnDatabase() throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerDatabase.TRACKER_DB_URL);
		DBHelper.runDataModificationQuery(dbConnection, TrackerDatabase.DELETE_TRACKER_TAG_TABLE_DATA);
		DBHelper.runDataModificationQuery(dbConnection, TrackerDatabase.DELETE_TRACKER_ASSIGNEE_TABLE_DATA);
		DBHelper.runDataModificationQuery(dbConnection, TrackerDatabase.DELETE_BOARD_TABLE_DATA);
		DBHelper.runDataModificationQuery(dbConnection, TrackerDatabase.DELETE_STATUS_TABLE_DATA);
		DBHelper.runDataModificationQuery(dbConnection, TrackerDatabase.DELETE_TRACKER_TABLE_DATA);
		DBHelper.runDataModificationQuery(dbConnection, TrackerDatabase.DELETE_TAG_TABLE_DATA);
		DBHelper.runDataModificationQuery(dbConnection, TrackerDatabase.DELETE_ASSIGNEE_TABLE_DATA);
	}

	public static void closeTrackersDatabaseConnection() throws SQLException {
		Connection dbConnection = DBConnection.getConnection(TrackerDatabase.TRACKER_DB_URL);

		if (!dbConnection.isClosed())
			dbConnection.close();
	}
	
	public static void injectTrackerAndTagRelationshipToDataBase(String id, String trackerId, String tagId) throws SQLException {
		
		Connection dbConnection = DBConnection.getConnection(TrackerDatabase.TRACKER_DB_URL);	
		String query = "INSERT INTO tracker.tracker_tag(id, tracker_id, tag_id) VALUES (" + id + "," + trackerId + "," + tagId + ");";
		
		DBHelper.runDataModificationQuery(dbConnection, query);	
	}
	
	public static void injectAssigneeToDataBase(String id, String createdBy, String createdOn, String peopleId, String trackerModelId) throws SQLException {
		
		Connection dbConnection = DBConnection.getConnection(TrackerDatabase.TRACKER_DB_URL);
		String query = "INSERT INTO tracker.assignee(id, created_by, created_on,  people_id, tracker_model_id) VALUES (" + id + "," + createdBy + "," + createdOn + "," + peopleId + "," + trackerModelId + ");";
		DBHelper.runDataModificationQuery(dbConnection, query);		
	}
	
	public static void injectTrackerAndAssigneeRelationshipToDataBase(String trackerId, String assigneeId) throws SQLException {
		
		Connection dbConnection = DBConnection.getConnection(TrackerDatabase.TRACKER_DB_URL);
		String query = "INSERT INTO tracker.tracker_assignee(tracker_id, assignee_id) VALUES (" + trackerId + "," + assigneeId + ");";

		DBHelper.runDataModificationQuery(dbConnection, query);
	}

	public static void injectTrackerModelOnDatabase(String id, String name, String module_id, String description,
			String deleted, String created_on, String created_by) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerDatabase.TRACKER_DB_URL);
		String TrackerModelQuery = "INSERT INTO tracker.tracker_model(id, name, module_id, description, deleted, created_on, created_by) VALUES ("
				+ id + ", '" + name + "', " + module_id + ", '" + description + "', "+deleted+", '"+created_on+"', "+created_by+");";
		DBHelper.runDataModificationQuery(dbConnection, TrackerModelQuery);
	}

	public static void injectStatusOnDatabase(String statusId, String statusName, String statusPosition,
			String statusDelete, String statusTrackerModelId) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerDatabase.TRACKER_DB_URL);
		String StatusQuery = "INSERT INTO tracker.status(id, name, position, deleted, tracker_model_id) VALUES ("
				+ statusId + ", '" + statusName + "', " + statusPosition + ", '" + statusDelete + "', "
				+ statusTrackerModelId + ");";
		DBHelper.runDataModificationQuery(dbConnection, StatusQuery);
	}

	public static void injectTagOnDatabase(String tagId, String tagName, String tagColor, String tagTrackerModelId)
			throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerDatabase.TRACKER_DB_URL);
		String TagQuery = "INSERT INTO tracker.tag(id, name, color, tracker_model_id) VALUES (" + tagId + ", '"
				+ tagName + "', '" + tagColor + "', " + tagTrackerModelId + ");";
		DBHelper.runDataModificationQuery(dbConnection, TagQuery);
	}

	public static void injectTrackerOnDatabase(String cardId, String cardTitle, String cardDescription,
			String cardDueDate, String cardCreatedOn, String cardCreatedBy, String cardStatusId,
			String cardTrackerModelId) throws SQLException {
		Connection dbConnection = DBConnection.getConnection(TrackerDatabase.TRACKER_DB_URL);
		String TrackerQuery = "INSERT INTO tracker.tracker(id, title, description, due_date, created_on, created_by, status_id, tracker_model_id) VALUES ("
				+ cardId + ", '" + cardTitle + "', '" + cardDescription + "', " + cardDueDate + ", '" + cardCreatedOn
				+ "', " + cardCreatedBy + ", " + cardStatusId + ", " + cardTrackerModelId + ");";
		DBHelper.runDataModificationQuery(dbConnection, TrackerQuery);
	}
	
	public static void injectTrackerAttachmentDataToDatabase(String id, String name, String orignalName, String fileSize, String path, String trackerId) throws SQLException {
		Connection dbConnection = DBConnection.getConnection(TrackerDatabase.TRACKER_DB_URL);
		String query = "INSERT INTO tracker.filepath(id, name, original_name, file_size, directory, tracker_id) VALUES (" + id + ", '"
				+ name + "', '" + orignalName + "', '" + fileSize + "', '"
				+ path + "', " + trackerId + ");";
		
		DBHelper.runDataModificationQuery(dbConnection, query);
	}

	public static void clearFilesFromDownloadsDirectory() throws IOException {
		String filePath1 = Utils.getEnvironmentUserPath() + ConfigData.DOWNLOADS_DIRECTORY + TrackerData.FILE_NAME1;
		String filePath2 = Utils.getEnvironmentUserPath() + ConfigData.DOWNLOADS_DIRECTORY + TrackerData.FILE_NAME2;
		String filePath4 = Utils.getEnvironmentUserPath() + ConfigData.DOWNLOADS_DIRECTORY + TrackerData.FILE_PATH4;
		Utils.deleteFile(filePath1);
		Utils.deleteFile(filePath2);
		Utils.deleteFile(filePath4);
	}

	public static void clearFilesFromWayFilesDirectory() throws IOException {
		Utils.clearFilesDirectory();
	}
}
