package br.edu.ufcg.virtus.automation.database;

import java.sql.Connection;
import java.sql.SQLException;

import br.edu.ufcg.virtus.automation.dataset.PeopleData;
import br.edu.ufcg.virtus.automation.dataset.DBHelperData;

public class PeopleDatabase {

	// People ID Data
	public static final String PEOPLE_ID1 = "1";
	public static final String PEOPLE_ID2 = "2";
	public static final String PEOPLE_ID3 = "3";
	public static final String PEOPLE_ID4 = "4";
	public static final String PEOPLE_ID5 = "5";
	public static final String PEOPLE_ID6 = "6";
	public static final String PEOPLE_ID7 = "7";
	public static final String PEOPLE_ID8 = "8";
	public static final String PEOPLE_ID9 = "9";
	public static final String PEOPLE_ID10 = "10";
	public static final String PEOPLE_ID11 = "11";

	// Contact Info IDs
	public static final String CONTACT_ID1 = "1";
	public static final String CONTACT_ID2 = "2";
	public static final String CONTACT_ID3 = "3";
	public static final String CONTACT_ID4 = "4";
	public static final String CONTACT_ID5 = "5";
	public static final String CONTACT_ID6 = "6";
	public static final String CONTACT_ID7 = "7";
	public static final String CONTACT_ID8 = "8";
	public static final String CONTACT_ID9 = "9";
	public static final String CONTACT_ID10 = "10";
	public static final String CONTACT_ID11 = "11";

	// CORE DATABASE URL
	private static final String PEOPLE_DATABASE_URL = DBHelperData.DATABASE_URL;

	// SQL QUERY TO DELETE PEOPLE DATASET ON DATABASE
	public static final String DELETE_CONTACT_INFO = "DELETE FROM core.contact_info WHERE id < 0 AND id >= 713;";
	public static final String DELETE_PEOPLE = "DELETE FROM core.people WHERE id < 0 AND id >= 713;";

	public static void injectPeopleData(String people_id, String name) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(PeopleDatabase.PEOPLE_DATABASE_URL);

		String INSERT_PEOPLE = "INSERT INTO core.people(id, name, government_code, nickname, people_type, deleted, user_id, updated_on, created_by, updated_by) \n"
				+ "VALUES ('" + people_id + "', '" + name
				+ "', null, null, null, false, null, '2021-07-22 00:00:00', 353, 353);";

		DBHelper.runDataModificationQuery(dbConnection, INSERT_PEOPLE);
	}

	public static void injectContactInfoData(String contact_id, String people_id) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(PeopleDatabase.PEOPLE_DATABASE_URL);

		String INSERT_CONTACT_INFO = "INSERT INTO core.contact_info(id, contact_info_type, phone, phone_tag, phone_country_code, owner_type, owner_id, address_complement, \n"
				+ "address_neighborhood, address_street, contact_domain, address_zip_code, address_city, address_number, address_state, address_country, deleted, email, email_tag) \n"
				+ "VALUES ('" + contact_id + "', 'EMAIL', null, null, null, 'PEOPLE', '" + people_id
				+ "', null, null, null, null, null, null, null, null, null, false, 'valid.email5@gmail.com', 'MAIN');";

		DBHelper.runDataModificationQuery(dbConnection, INSERT_CONTACT_INFO);
	}

	public static void clearPeopleDataOnDatabase() throws SQLException {

		Connection dbConnection = DBConnection.getConnection(PeopleDatabase.PEOPLE_DATABASE_URL);

		DBHelper.runDataModificationQuery(dbConnection, PeopleDatabase.DELETE_CONTACT_INFO);
		DBHelper.runDataModificationQuery(dbConnection, PeopleDatabase.DELETE_PEOPLE);

	}

	public static void closePeopleDatabaseConnection() throws SQLException {

		Connection dbConnection = DBConnection.getConnection(PeopleDatabase.PEOPLE_DATABASE_URL);

		if (!dbConnection.isClosed())
			dbConnection.close();
	}

	public static void injectPeopleDataOnDatabase() throws SQLException {

		PeopleDatabase.injectPeopleData(PEOPLE_ID1, PeopleData.VALID_PEOPLE_NAME6);
		PeopleDatabase.injectContactInfoData(CONTACT_ID1, PEOPLE_ID1);
		PeopleDatabase.injectPeopleData(PEOPLE_ID2, PeopleData.VALID_PEOPLE_NAME7);
		PeopleDatabase.injectContactInfoData(CONTACT_ID2, PEOPLE_ID2);
		PeopleDatabase.injectPeopleData(PEOPLE_ID3, PeopleData.VALID_PEOPLE_NAME8);
		PeopleDatabase.injectContactInfoData(CONTACT_ID3, PEOPLE_ID3);
		PeopleDatabase.injectPeopleData(PEOPLE_ID4, PeopleData.VALID_PEOPLE_NAME9);
		PeopleDatabase.injectContactInfoData(CONTACT_ID4, PEOPLE_ID4);
		PeopleDatabase.injectPeopleData(PEOPLE_ID5, PeopleData.VALID_PEOPLE_NAME11);
		PeopleDatabase.injectContactInfoData(CONTACT_ID5, PEOPLE_ID5);
		PeopleDatabase.injectPeopleData(PEOPLE_ID6, PeopleData.VALID_PEOPLE_NAME12);
		PeopleDatabase.injectContactInfoData(CONTACT_ID6, PEOPLE_ID6);
		PeopleDatabase.injectPeopleData(PEOPLE_ID7, PeopleData.VALID_PEOPLE_NAME13);
		PeopleDatabase.injectContactInfoData(CONTACT_ID7, PEOPLE_ID7);
		PeopleDatabase.injectPeopleData(PEOPLE_ID8, PeopleData.VALID_PEOPLE_NAME14);
		PeopleDatabase.injectContactInfoData(CONTACT_ID8, PEOPLE_ID8);
		PeopleDatabase.injectPeopleData(PEOPLE_ID9, PeopleData.VALID_PEOPLE_NAME15);
		PeopleDatabase.injectContactInfoData(CONTACT_ID9, PEOPLE_ID9);
		PeopleDatabase.injectPeopleData(PEOPLE_ID10, PeopleData.VALID_PEOPLE_NAME16);
		PeopleDatabase.injectContactInfoData(CONTACT_ID10, PEOPLE_ID10);
		PeopleDatabase.injectPeopleData(PEOPLE_ID11, PeopleData.VALID_PEOPLE_NAME17);
		PeopleDatabase.injectContactInfoData(CONTACT_ID11, PEOPLE_ID11);
	}
}
