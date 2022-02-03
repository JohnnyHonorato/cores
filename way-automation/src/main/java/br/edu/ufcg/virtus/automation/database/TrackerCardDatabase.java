package br.edu.ufcg.virtus.automation.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import br.edu.ufcg.virtus.automation.dataset.ConfigData;
import br.edu.ufcg.virtus.automation.dataset.DBHelperData;
import br.edu.ufcg.virtus.automation.dataset.TrackerBoardData;
import br.edu.ufcg.virtus.automation.dataset.TrackerCardData;
import br.edu.ufcg.virtus.automation.dataset.TrackerData;
import br.edu.ufcg.virtus.automation.utils.Utils;

public class TrackerCardDatabase {

	public static final String TRACKER_DB_URL = DBHelperData.DATABASE_URL;

	public static final String MODULE_TRACKER_ID = "2";
	private static final String USER_ID = "312";

	public static final String DELETE_TRACKER_TAG_TABLE_DATA = "TRUNCATE tracker.tracker_tag CASCADE;";
	public static final String DELETE_TRACKER_ASSIGNEE_TABLE_DATA = "TRUNCATE tracker.tracker_assignee CASCADE;";
	public static final String DELETE_TRACKER_TABLE_DATA = "DELETE FROM tracker.tracker;";
	public static final String DELETE_TAG_TABLE_DATA = "DELETE FROM tracker.tag;";
	public static final String DELETE_ASSIGNEE_TABLE_DATA = "DELETE FROM tracker.assignee;";

// CARDS METHODS
	public static void injectCardOnDatabase(String cardId, String cardTitle, String cardDescription, String cardDueDate,
			String cardCreatedBy, String cardStatusId, String cardTrackerModelId, String transitionId)
			throws SQLException {
		Connection dbConnection = DBConnection.getConnection(TrackerCardDatabase.TRACKER_DB_URL);
		String TrackerQuery = "INSERT INTO tracker.tracker(id, title, description, due_date, created_on, updated_on, created_by, updated_by, status_id, tracker_model_id, transition_id) VALUES ("
				+ cardId + ", '" + cardTitle + "', '" + cardDescription + "', " + cardDueDate + ",null ,null, "
				+ cardCreatedBy + ", null, " + cardStatusId + ", " + cardTrackerModelId + ", " + transitionId + ");";
		DBHelper.runDataModificationQuery(dbConnection, TrackerQuery);
	}

// ASSIGNEE METHODS
	public static void injectAssigneeToDataBase(String id, String createdBy, String peopleId, String trackerModelId)
			throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerCardDatabase.TRACKER_DB_URL);
		String query = "INSERT INTO tracker.assignee(id, created_by, created_on,  people_id, tracker_model_id) VALUES ("
				+ id + ", " + createdBy + ", null," + peopleId + "," + trackerModelId + ");";
		DBHelper.runDataModificationQuery(dbConnection, query);
	}

	public static void injectCardAndAssigneeRelationshipToDataBase(String trackerId, String assigneeId)
			throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerCardDatabase.TRACKER_DB_URL);
		String query = "INSERT INTO tracker.tracker_assignee(tracker_id, assignee_id) VALUES (" + trackerId + ","
				+ assigneeId + ");";

		DBHelper.runDataModificationQuery(dbConnection, query);
	}

// TAG METHODS
	public static void injectTagOnDatabase(String tagId, String tagName, String tagColor, String tagTrackerModelId)
			throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerCardDatabase.TRACKER_DB_URL);
		String TagQuery = "INSERT INTO tracker.tag(id, name, color, tracker_model_id) VALUES (" + tagId + ", '"
				+ tagName + "', '" + tagColor + "', " + tagTrackerModelId + ");";
		DBHelper.runDataModificationQuery(dbConnection, TagQuery);
	}

	public static void injectCardAndTagRelationshipToDataBase(String id, String trackerId, String tagId)
			throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerCardDatabase.TRACKER_DB_URL);
		String query = "INSERT INTO tracker.tracker_tag(id, tracker_id, tag_id) VALUES (" + id + "," + trackerId + ","
				+ tagId + ");";

		DBHelper.runDataModificationQuery(dbConnection, query);
	}

// ATTACHMENTS METHOD
	public static void injectCardAttachmentDataToDatabase(String id, String name, String orignalName, String fileSize,
			String path, String trackerId) throws SQLException {
		Connection dbConnection = DBConnection.getConnection(TrackerCardDatabase.TRACKER_DB_URL);
		String query = "INSERT INTO tracker.filepath(id, name, original_name, file_size, directory, tracker_id) VALUES ("
				+ id + ", '" + name + "', '" + orignalName + "', '" + fileSize + "', '" + path + "', " + trackerId
				+ ");";

		DBHelper.runDataModificationQuery(dbConnection, query);
	}

// CHECKLIST METHOD
	public static void injectCheckListDataToDatabase(String id, String name, String trackerId) throws SQLException {
		Connection dbConnection = DBConnection.getConnection(TrackerCardDatabase.TRACKER_DB_URL);
		String query = "INSERT INTO tracker.checklist(id, name, tracker_id) VALUES (" + id + ", '" + name + "', "
				+ trackerId + ");";

		DBHelper.runDataModificationQuery(dbConnection, query);
	}

// CHECKLIST ITEM METHOD
	public static void injectCheckListItemDataToDatabase(String id, String name, String checklistId, String done)
			throws SQLException {
		Connection dbConnection = DBConnection.getConnection(TrackerCardDatabase.TRACKER_DB_URL);
		String query = "INSERT INTO tracker.checklist_item(id, name, checklist_id, done) VALUES (" + id + ", '" + name
				+ "', " + checklistId + ", " + done + ");";

		DBHelper.runDataModificationQuery(dbConnection, query);
	}

// CLEAR METHODS
	public static void clearFilesFromDownloadsDirectory() throws IOException {
		String filePath = Utils.getEnvironmentUserPath() + ConfigData.DOWNLOADS_DIRECTORY + TrackerData.FILE_NAME2;
		Utils.deleteFile(filePath);
	}

	public static void clearFilesFromWayFilesDirectory() throws IOException {
		Utils.clearFilesDirectory();
	}

	public static void clearCardsDataOnDatabase() throws SQLException {

		Connection dbConnection = DBConnection.getConnection(TrackerCardDatabase.TRACKER_DB_URL);

		DBHelper.runDataModificationQuery(dbConnection, TrackerCardDatabase.DELETE_TRACKER_TAG_TABLE_DATA);
		DBHelper.runDataModificationQuery(dbConnection, TrackerCardDatabase.DELETE_TRACKER_ASSIGNEE_TABLE_DATA);
		DBHelper.runDataModificationQuery(dbConnection, TrackerCardDatabase.DELETE_TRACKER_TABLE_DATA);
		DBHelper.runDataModificationQuery(dbConnection, TrackerCardDatabase.DELETE_TAG_TABLE_DATA);
		DBHelper.runDataModificationQuery(dbConnection, TrackerCardDatabase.DELETE_ASSIGNEE_TABLE_DATA);
	}

	public static void closeCardsDatabaseConnection() throws SQLException {
		Connection dbConnection = DBConnection.getConnection(TrackerCardDatabase.TRACKER_DB_URL);

		if (!dbConnection.isClosed())
			dbConnection.close();
	}

	public static void injectCardsDataOnDatabase() throws SQLException {

		injectAssigneeToDataBase(TrackerBoardData.ASSIGNEE_ID_1, USER_ID, TrackerBoardData.PEOPLE_ID_1,
				TrackerBoardData.BOARD_ID_1);
		injectAssigneeToDataBase(TrackerBoardData.ASSIGNEE_ID_2, USER_ID, TrackerBoardData.PEOPLE_ID_2,
				TrackerBoardData.BOARD_ID_1);
		injectAssigneeToDataBase(TrackerBoardData.ASSIGNEE_ID_3, USER_ID, TrackerBoardData.PEOPLE_ID_3,
				TrackerBoardData.BOARD_ID_1);
// CARD 1
		injectCardOnDatabase(TrackerCardData.CARD_ID_1, TrackerCardData.CARD_TITLE_1,
				TrackerCardData.CARD_DESCRIPTION_1, TrackerCardData.CARD_DUE_DATE_1, USER_ID,
				TrackerBoardData.STATUS_ID_1, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_1);
		injectCardAndAssigneeRelationshipToDataBase(TrackerCardData.CARD_ID_1, TrackerBoardData.ASSIGNEE_ID_1);
		injectCardAndAssigneeRelationshipToDataBase(TrackerCardData.CARD_ID_1, TrackerBoardData.ASSIGNEE_ID_2);

		injectTagOnDatabase(TrackerCardData.TAG_ID_1, TrackerCardData.TAG_NAME_1, TrackerCardData.TAG_COLOR_1,
				TrackerBoardData.BOARD_ID_1);
		injectCardAndTagRelationshipToDataBase(TrackerCardData.TAG_ID_1, TrackerCardData.CARD_ID_1,
				TrackerCardData.TAG_ID_1);

		TrackerBoardDatabase.injectAttributeValueOnDatabase(TrackerCardData.ATTRIBUTE_VALUE_ID_1,
				TrackerCardData.ATTRIBUTE_VALUE_1, null, TrackerCardData.CARD_ID_1, TrackerBoardData.ATT_INTEGER_ID_1);

// CARD 2
		injectCardOnDatabase(TrackerCardData.CARD_ID_2, TrackerCardData.CARD_TITLE_2,
				TrackerCardData.CARD_DESCRIPTION_2, TrackerCardData.CARD_DUE_DATE_1, USER_ID,
				TrackerBoardData.STATUS_ID_1, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_1);

// CARD 5
		injectCardOnDatabase(TrackerCardData.CARD_ID_5, TrackerCardData.CARD_TITLE_5,
				TrackerCardData.CARD_DESCRIPTION_5, TrackerCardData.CARD_DUE_DATE_5, USER_ID,
				TrackerBoardData.STATUS_ID_1, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_1);

// CARD 6
		injectCardOnDatabase(TrackerCardData.CARD_ID_6, TrackerCardData.CARD_TITLE_6,
				TrackerCardData.CARD_DESCRIPTION_6, TrackerCardData.CARD_DUE_DATE_6, USER_ID,
				TrackerBoardData.STATUS_ID_1, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_1);

// CARD 7
		injectCardOnDatabase(TrackerCardData.CARD_ID_7, TrackerCardData.CARD_TITLE_7,
				TrackerCardData.CARD_DESCRIPTION_7, TrackerCardData.CARD_DUE_DATE_7, USER_ID,
				TrackerBoardData.STATUS_ID_1, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_1);

// CARD 12
		injectCardOnDatabase(TrackerCardData.CARD_ID_12, TrackerCardData.CARD_TITLE_12,
				TrackerCardData.CARD_DESCRIPTION_12, TrackerCardData.CARD_DUE_DATE_12, USER_ID,
				TrackerBoardData.STATUS_ID_3, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_3);

// CARD 13
		injectCardOnDatabase(TrackerCardData.CARD_ID_13, TrackerCardData.CARD_TITLE_13,
				TrackerCardData.CARD_DESCRIPTION_13, TrackerCardData.CARD_DUE_DATE_13, USER_ID,
				TrackerBoardData.STATUS_ID_3, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_3);

// CARD 14
		injectCardOnDatabase(TrackerCardData.CARD_ID_14, TrackerCardData.CARD_TITLE_14,
				TrackerCardData.CARD_DESCRIPTION_14, TrackerCardData.CARD_DUE_DATE_14, USER_ID,
				TrackerBoardData.STATUS_ID_2, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_2);
		injectCheckListDataToDatabase(TrackerCardData.CHECKLIST_ID_1, TrackerCardData.CHECKLIST_1,
				TrackerCardData.CARD_ID_14);

// CARD 15
		injectCardOnDatabase(TrackerCardData.CARD_ID_15, TrackerCardData.CARD_TITLE_15,
				TrackerCardData.CARD_DESCRIPTION_15, TrackerCardData.CARD_DUE_DATE_15, USER_ID,
				TrackerBoardData.STATUS_ID_2, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_2);
		injectCheckListDataToDatabase(TrackerCardData.CHECKLIST_ID_3, TrackerCardData.CHECKLIST_3,
				TrackerCardData.CARD_ID_15);

// CARD 16
		injectCardOnDatabase(TrackerCardData.CARD_ID_16, TrackerCardData.CARD_TITLE_16,
				TrackerCardData.CARD_DESCRIPTION_16, TrackerCardData.CARD_DUE_DATE_16, USER_ID,
				TrackerBoardData.STATUS_ID_2, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_2);
		injectCheckListDataToDatabase(TrackerCardData.CHECKLIST_ID_4, TrackerCardData.CHECKLIST_4,
				TrackerCardData.CARD_ID_16);
		injectCheckListItemDataToDatabase(TrackerCardData.CHECKLIST_ITEM_ID_2, TrackerCardData.CHECKLIST_ITEM_2,
				TrackerCardData.CHECKLIST_ID_4, TrackerCardData.CHECKLIST_DONE_2);

// CARD 17
		injectCardOnDatabase(TrackerCardData.CARD_ID_17, TrackerCardData.CARD_TITLE_17,
				TrackerCardData.CARD_DESCRIPTION_17, TrackerCardData.CARD_DUE_DATE_17, USER_ID,
				TrackerBoardData.STATUS_ID_2, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_2);
		injectCheckListDataToDatabase(TrackerCardData.CHECKLIST_ID_5, TrackerCardData.CHECKLIST_5,
				TrackerCardData.CARD_ID_17);
		injectCheckListItemDataToDatabase(TrackerCardData.CHECKLIST_ITEM_ID_3, TrackerCardData.CHECKLIST_ITEM_3,
				TrackerCardData.CHECKLIST_ID_5, TrackerCardData.CHECKLIST_DONE_3);

// CARD 18
		injectCardOnDatabase(TrackerCardData.CARD_ID_18, TrackerCardData.CARD_TITLE_18,
				TrackerCardData.CARD_DESCRIPTION_18, TrackerCardData.CARD_DUE_DATE_18, USER_ID,
				TrackerBoardData.STATUS_ID_3, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_3);

// CARD 19
		injectCardOnDatabase(TrackerCardData.CARD_ID_19, TrackerCardData.CARD_TITLE_19,
				TrackerCardData.CARD_DESCRIPTION_19, TrackerCardData.CARD_DUE_DATE_19, USER_ID,
				TrackerBoardData.STATUS_ID_3, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_3);

// CARD 20
		injectCardOnDatabase(TrackerCardData.CARD_ID_20, TrackerCardData.CARD_TITLE_20,
				TrackerCardData.CARD_DESCRIPTION_20, TrackerCardData.CARD_DUE_DATE_20, USER_ID,
				TrackerBoardData.STATUS_ID_3, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_3);
		injectCheckListDataToDatabase(TrackerCardData.CHECKLIST_ID_6, TrackerCardData.CHECKLIST_6,
				TrackerCardData.CARD_ID_20);

// CARD 21
		injectCardOnDatabase(TrackerCardData.CARD_ID_21, TrackerCardData.CARD_TITLE_21,
				TrackerCardData.CARD_DESCRIPTION_21, TrackerCardData.CARD_DUE_DATE_21, USER_ID,
				TrackerBoardData.STATUS_ID_3, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_3);
		injectCheckListDataToDatabase(TrackerCardData.CHECKLIST_ID_7, TrackerCardData.CHECKLIST_7,
				TrackerCardData.CARD_ID_21);

// CARD 22
		injectCardOnDatabase(TrackerCardData.CARD_ID_22, TrackerCardData.CARD_TITLE_22,
				TrackerCardData.CARD_DESCRIPTION_22, TrackerCardData.CARD_DUE_DATE_22, USER_ID,
				TrackerBoardData.STATUS_ID_3, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_3);
		injectCheckListDataToDatabase(TrackerCardData.CHECKLIST_ID_8, TrackerCardData.CHECKLIST_8,
				TrackerCardData.CARD_ID_22);
		injectCheckListItemDataToDatabase(TrackerCardData.CHECKLIST_ITEM_ID_4, TrackerCardData.CHECKLIST_ITEM_4,
				TrackerCardData.CHECKLIST_ID_8, TrackerCardData.CHECKLIST_DONE_4);

// CARD 23
		injectCardOnDatabase(TrackerCardData.CARD_ID_23, TrackerCardData.CARD_TITLE_23,
				TrackerCardData.CARD_DESCRIPTION_23, TrackerCardData.CARD_DUE_DATE_23, USER_ID,
				TrackerBoardData.STATUS_ID_3, TrackerBoardData.BOARD_ID_1, TrackerBoardData.TRANSITION_ID_3);
		injectCheckListDataToDatabase(TrackerCardData.CHECKLIST_ID_9, TrackerCardData.CHECKLIST_9,
				TrackerCardData.CARD_ID_23);
		injectCheckListItemDataToDatabase(TrackerCardData.CHECKLIST_ITEM_ID_5, TrackerCardData.CHECKLIST_ITEM_5,
				TrackerCardData.CHECKLIST_ID_9, TrackerCardData.CHECKLIST_DONE_5);
	}
}
