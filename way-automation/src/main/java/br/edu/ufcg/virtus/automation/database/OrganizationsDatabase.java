package br.edu.ufcg.virtus.automation.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;

import br.edu.ufcg.virtus.automation.dataset.ConfigData;
import br.edu.ufcg.virtus.automation.dataset.DBHelperData;
import br.edu.ufcg.virtus.automation.dataset.OrganizationsData;
import br.edu.ufcg.virtus.automation.utils.Utils;

public class OrganizationsDatabase {

	// BUSINESS DATABASE URL
	private static String ORGANIZATIONS_DATABASE_URL = DBHelperData.DATABASE_URL;

	// General Data
	private static final String USER_ID = "312";

	// Organization 1 -> Test Case: addOrganizationsWithTheSameNameAndDifferentCNPJ
	private static final String ORGANIZATION_ID_1 = "-100";
	private static final String EMAIL_ID_1 = "-100";

	// Organization 2 -> Test Case: AddOrganizationWithCNPJThatAlreadyExists
	private static final String ORGANIZATION_ID_2 = "-101";
	private static final String EMAIL_ID_2 = "-101";

	// Organization 3 -> RemoveAnOrganizationSuccessfully
	private static final String ORGANIZATION_ID_3 = "-102";
	private static final String EMAIL_ID_3 = "-102";

	// Organization 4 -> CaseThroughButtonNo / CaseThroughButtonX
	private static final String ORGANIZATION_ID_4 = "-103";
	private static final String EMAIL_ID_4 = "-103";

	// Organization 5 -> Used for all edit tests
	private static final String ORGANIZATION_ID_5 = "-104";
	private static final String EMAIL_ID_5 = "-1041";
	private static final String PHONE_ID_5 = "-1042";
	private static final String ADDRESS_ID_5 = "-1043";
	private static final String COLLABORATOR_ID_5 = "-1044";

	// Organization 6 -> removeImageLogoSuccessfully
	private static final String IMAGE_ID_6 = "-105";
	private static final String ORGANIZATION_ID_6 = "-105";
	private static final String EMAIL_ID_6 = "-1051";
	
	// Organization 7 -> CaseEditFantasyNameWithSuccess
	private static final String ORGANIZATION_ID_7 = "-106";
	private static final String EMAIL_ID_7 = "-1061";
	
	// Organization 30
	private static final String ORGANIZATION_ID_30 = "-130";
	private static final String EMAIL_ID_30 = "-1130";
	private static final String PHONE_ID_30 = "-1120";
	private static final String ADDRESS_ID_30 = "-1130";
	private static final String COLLABORATOR_ID_30 = "-1120";

	// SQL QUERY TO DELETE ORGANIZATIONS DATASET ON DATABASE
	private static final String DELETE_CONTACT_INFO = "TRUNCATE core.contact_info CASCADE;";
	private static final String DELETE_COLLABORATOR = "TRUNCATE core.people_organization CASCADE;";
	private static final String DELETE_IMAGE_FILE_TABLE = "TRUNCATE core.filepath CASCADE;";
	private static final String DELETE_ORGANIZATION = "DELETE FROM core.organization;";

	public static void injectOrganizationsDataOnDatabase() throws SQLException {

		// Organization 1 -> Test Case: addOrganizationsWithTheSameNameAndDifferentCNPJ
		injectOrganizationOnDatabase(ORGANIZATION_ID_1, OrganizationsData.VALID_COMPANY_NAME_3,
				OrganizationsData.VALID_FANTASY_NAME_3, OrganizationsData.VALID_CNPJ_3, null, null,
				Utils.getTimeStamp(), USER_ID);

		injectContactInfoEmailOnDatabase(EMAIL_ID_1, ORGANIZATION_ID_1, OrganizationsData.VALID_EMAIL_3,
				OrganizationsData.MAIN_EMAIL_TYPE_ENGLISH);

		// Organization 2 -> Test Case: AddOrganizationWithCNPJThatAlreadyExists
		injectOrganizationOnDatabase(ORGANIZATION_ID_2, OrganizationsData.VALID_COMPANY_NAME_4,
				OrganizationsData.VALID_FANTASY_NAME_4, OrganizationsData.VALID_CNPJ_5, null, null,
				Utils.getTimeStamp(), USER_ID);

		injectContactInfoEmailOnDatabase(EMAIL_ID_2, ORGANIZATION_ID_2, OrganizationsData.VALID_EMAIL_4,
				OrganizationsData.MAIN_EMAIL_TYPE_ENGLISH);

		// Organization 3 -> RemoveAnOrganizationSuccessfully
		injectOrganizationOnDatabase(ORGANIZATION_ID_3, OrganizationsData.VALID_COMPANY_NAME_6,
				OrganizationsData.VALID_FANTASY_NAME_6, OrganizationsData.VALID_CNPJ_7, null, null,
				Utils.getTimeStamp(), USER_ID);

		injectContactInfoEmailOnDatabase(EMAIL_ID_3, ORGANIZATION_ID_3, OrganizationsData.VALID_EMAIL_6,
				OrganizationsData.MAIN_EMAIL_TYPE_ENGLISH);

		// Organization 4 -> CaseThroughButtonNo / CaseThroughButtonX
		injectOrganizationOnDatabase(ORGANIZATION_ID_4, OrganizationsData.VALID_COMPANY_NAME_7,
				OrganizationsData.VALID_FANTASY_NAME_7, OrganizationsData.VALID_CNPJ_8, null, null,
				Utils.getTimeStamp(), USER_ID);

		injectContactInfoEmailOnDatabase(EMAIL_ID_4, ORGANIZATION_ID_4, OrganizationsData.VALID_EMAIL_7,
				OrganizationsData.MAIN_EMAIL_TYPE_ENGLISH);

		// Organization 5 -> Used for edit tests
		injectOrganizationOnDatabase(ORGANIZATION_ID_5, OrganizationsData.VALID_COMPANY_NAME_8,
				OrganizationsData.VALID_FANTASY_NAME_8, OrganizationsData.VALID_CNPJ_9,
				OrganizationsData.VALID_DESCRIPTION_2, null, Utils.getTimeStamp(), USER_ID);

		injectContactInfoEmailOnDatabase(EMAIL_ID_5, ORGANIZATION_ID_5, OrganizationsData.VALID_EMAIL_8,
				OrganizationsData.MAIN_EMAIL_TYPE_ENGLISH);

		injectContactInfoPhoneOnDatabase(PHONE_ID_5, OrganizationsData.VALID_PHONE_NUMBER_2,
				OrganizationsData.MAIN_PHONE_TYPE_ENGLISH, OrganizationsData.VALID_DDI_CODE_2, ORGANIZATION_ID_5);

		injectContactInfoAddressOnDatabase(ADDRESS_ID_5, ORGANIZATION_ID_5, OrganizationsData.VALID_COMPLEMENT_2,
				OrganizationsData.VALID_DISTRICT_2, OrganizationsData.VALID_STREET_2,
				OrganizationsData.NATIONAL_DOMAIN_ENGLISH, OrganizationsData.VALID_CEP_2,
				OrganizationsData.VALID_CITY_2, OrganizationsData.VALID_ADDRESS_NUMBER_2,
				OrganizationsData.VALID_STATE_2, OrganizationsData.VALID_COUNTRY_2);

		injectCollaboratorOnDatabase(COLLABORATOR_ID_5, OrganizationsData.VALID_DEPARTAMENT_2,
				OrganizationsData.VALID_OFFICE_2, OrganizationsData.PEOPLE_ID_1, ORGANIZATION_ID_5);

		// Organization 6 -> removeImageLogoSuccessfully
		Utils.copyFile(Utils.getProjectPath() + OrganizationsData.IMAGE_PATH_3,
				Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY);

		String imagePath = Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY
				+ OrganizationsData.IMAGE_NAME_3;

		injectImgLogoOnDatabase(IMAGE_ID_6, OrganizationsData.IMAGE_NAME_3, imagePath);

		injectOrganizationOnDatabase(ORGANIZATION_ID_6, OrganizationsData.VALID_COMPANY_NAME_10,
				OrganizationsData.VALID_FANTASY_NAME_10, OrganizationsData.VALID_CNPJ_11,
				OrganizationsData.VALID_DESCRIPTION_4, IMAGE_ID_6, Utils.getTimeStamp(), USER_ID);

		injectContactInfoEmailOnDatabase(EMAIL_ID_6, ORGANIZATION_ID_6, OrganizationsData.VALID_EMAIL_10,
				OrganizationsData.MAIN_EMAIL_TYPE_ENGLISH);
		
		// Organization 7 -> CaseEditFantasyNameWithSuccess
		injectOrganizationOnDatabase(ORGANIZATION_ID_7, OrganizationsData.VALID_COMPANY_NAME_11,
				OrganizationsData.VALID_FANTASY_NAME_11, OrganizationsData.VALID_CNPJ_12,
				OrganizationsData.VALID_DESCRIPTION_5, null, Utils.getTimeStamp(), USER_ID);

		injectContactInfoEmailOnDatabase(EMAIL_ID_7, ORGANIZATION_ID_7, OrganizationsData.VALID_EMAIL_11,
				OrganizationsData.MAIN_EMAIL_TYPE_ENGLISH);
		
		// Organization 30
		injectOrganizationOnDatabase(ORGANIZATION_ID_30, OrganizationsData.VALID_COMPANY_NAME_30,
				OrganizationsData.VALID_FANTASY_NAME_30, OrganizationsData.VALID_CNPJ_30,
				OrganizationsData.VALID_DESCRIPTION_2, null, Utils.getTimeStamp(), USER_ID);

		injectContactInfoEmailOnDatabase(EMAIL_ID_30, ORGANIZATION_ID_30, OrganizationsData.VALID_EMAIL_30,
				OrganizationsData.MAIN_EMAIL_TYPE_ENGLISH);

		injectContactInfoPhoneOnDatabase(PHONE_ID_30, OrganizationsData.VALID_PHONE_NUMBER_2,
				OrganizationsData.MAIN_PHONE_TYPE_ENGLISH, OrganizationsData.VALID_DDI_CODE_2, ORGANIZATION_ID_30);

		injectContactInfoAddressOnDatabase(ADDRESS_ID_30, ORGANIZATION_ID_30, OrganizationsData.VALID_COMPLEMENT_2,
				OrganizationsData.VALID_DISTRICT_2, OrganizationsData.VALID_STREET_2,
				OrganizationsData.NATIONAL_DOMAIN_ENGLISH, OrganizationsData.VALID_CEP_2,
				OrganizationsData.VALID_CITY_2, OrganizationsData.VALID_ADDRESS_NUMBER_2,
				OrganizationsData.VALID_STATE_2, OrganizationsData.VALID_COUNTRY_2);

		injectCollaboratorOnDatabase(COLLABORATOR_ID_30, OrganizationsData.VALID_DEPARTAMENT_2,
				OrganizationsData.VALID_OFFICE_2, OrganizationsData.PEOPLE_ID_1, ORGANIZATION_ID_30);
		
	}

	public static void clearOrganizationsDataOnDatabase() throws SQLException {

		Connection dbConnection = DBConnection.getConnection(OrganizationsDatabase.ORGANIZATIONS_DATABASE_URL);

		DBHelper.runDataModificationQuery(dbConnection, OrganizationsDatabase.DELETE_CONTACT_INFO);
		DBHelper.runDataModificationQuery(dbConnection, OrganizationsDatabase.DELETE_COLLABORATOR);
		DBHelper.runDataModificationQuery(dbConnection, OrganizationsDatabase.DELETE_IMAGE_FILE_TABLE);
		DBHelper.runDataModificationQuery(dbConnection, OrganizationsDatabase.DELETE_ORGANIZATION);

	}

	public static void closeOrganizationsDatabaseConnection() throws SQLException {

		Connection dbConnection = DBConnection.getConnection(OrganizationsDatabase.ORGANIZATIONS_DATABASE_URL);

		if (!dbConnection.isClosed())
			dbConnection.close();
	}

	public static void clearImageLogoDirectory() throws IOException {

		File f = new File(Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY);
		if (!f.exists())
			f.mkdirs();

		FileUtils.cleanDirectory(new File(Utils.getEnvironmentUserPath() + ConfigData.FILES_DIRECTORY));
	}

	public static void injectImgLogoOnDatabase(String id, String name, String directory) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(OrganizationsDatabase.ORGANIZATIONS_DATABASE_URL);

		String imgQuery = "INSERT INTO core.filepath(id, name, directory) VALUES (" + id + ", '" + name + "', '"
				+ directory + "');";

		DBHelper.runDataModificationQuery(dbConnection, imgQuery);
	}

	public static void injectOrganizationOnDatabase(String id, String name, String fantasyName, String cnpj,
			String description, String imageID, String timeStamp, String createdBy) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(OrganizationsDatabase.ORGANIZATIONS_DATABASE_URL);

		String organizationQuery = "INSERT INTO core.organization(id, name, fantasy_name, government_code, description, img_id, deleted, created_on, created_by) \n"
				+ "VALUES (" + id + ", '" + name + "', '" + fantasyName + "', '" + cnpj + "', '" + description + "', "
				+ imageID + ", false, '" + timeStamp + "', '" + createdBy + "');";

		DBHelper.runDataModificationQuery(dbConnection, organizationQuery);
	}

	public static void injectContactInfoEmailOnDatabase(String id, String organizationId, String email,
			String emailType) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(OrganizationsDatabase.ORGANIZATIONS_DATABASE_URL);

		String emailQuery = "INSERT INTO core.contact_info(id, contact_info_type, phone, phone_tag, phone_country_code, owner_type, owner_id, address_complement, \n"
				+ "address_neighborhood, address_street, contact_domain, address_zip_code, address_city, address_number, address_state, address_country, deleted, email, email_tag) \n"
				+ "VALUES (" + id + ", 'EMAIL', null, null, null, 'ORGANIZATION', " + organizationId
				+ ", null, null, null, null, null, null, null, null, null, false, '" + email + "', '" + emailType
				+ "');";

		DBHelper.runDataModificationQuery(dbConnection, emailQuery);
	}

	public static void injectContactInfoPhoneOnDatabase(String id, String phoneNumber, String phoneType, String ddiCode,
			String organizationID) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(OrganizationsDatabase.ORGANIZATIONS_DATABASE_URL);

		String phoneQuery = "INSERT INTO core.contact_info(id, contact_info_type, phone, phone_tag, phone_country_code, owner_type, owner_id, address_complement, \n"
				+ "address_neighborhood, address_street, contact_domain, address_zip_code, address_city, address_number, address_state, address_country, deleted, email, email_tag) \n"
				+ "VALUES (" + id + ", 'PHONE', '" + phoneNumber + "', '" + phoneType + "', '" + ddiCode
				+ "', 'ORGANIZATION', " + organizationID
				+ ", null, null, null, null, null, null, null, null, null, false, null, null);";

		DBHelper.runDataModificationQuery(dbConnection, phoneQuery);
	}

	public static void injectContactInfoAddressOnDatabase(String id, String organizationID, String complement,
			String district, String street, String domain, String cep, String city, String addressNumber, String state,
			String country) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(OrganizationsDatabase.ORGANIZATIONS_DATABASE_URL);

		String addressQuery = "INSERT INTO core.contact_info(id, contact_info_type, phone, phone_tag, phone_country_code, owner_type, owner_id, address_complement, \n"
				+ "address_neighborhood, address_street, contact_domain, address_zip_code, address_city, address_number, address_state, address_country, deleted, email, email_tag) \n"
				+ "VALUES (" + id + ", 'ADDRESS', null, null, null, 'ORGANIZATION', " + organizationID + ", '"
				+ complement + "', '" + district + "', '" + street + "', '" + domain + "', '" + cep + "', '" + city
				+ "', '" + addressNumber + "', '" + state + "', '" + country + "', false, null, null);";

		DBHelper.runDataModificationQuery(dbConnection, addressQuery);
	}

	public static void injectCollaboratorOnDatabase(String id, String departament, String office, String peopleID,
			String organizationID) throws SQLException {

		Connection dbConnection = DBConnection.getConnection(OrganizationsDatabase.ORGANIZATIONS_DATABASE_URL);

		String collaboratorQuery = "INSERT INTO core.people_organization(id, department, position, people_id, organization_id, technical_manager)\n"
				+ "VALUES (" + id + ", '" + departament + "', '" + office + "', " + peopleID + ", " + organizationID
				+ ", null);";

		DBHelper.runDataModificationQuery(dbConnection, collaboratorQuery);
	}
}
