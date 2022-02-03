package br.edu.ufcg.virtus.automation.database;

import java.sql.Connection;
import java.sql.SQLException;

import br.edu.ufcg.virtus.automation.dataset.BusinessOpportunityData;
import br.edu.ufcg.virtus.automation.dataset.DBHelperData;
import br.edu.ufcg.virtus.automation.dataset.OrganizationsData;
import br.edu.ufcg.virtus.automation.utils.Utils;

public class BusinessOpportunityDatabase {

	// BUSINESS DATABASE URL
	private static final String BUSINESS_DATABASE_URL = DBHelperData.DATABASE_URL;

	// SQL DATA
	public static final String DELETE_BUSINESS_OPPORTUNITY = "TRUNCATE business.business_opportunity CASCADE;";

	// General Data
	private static final String USER_ID = "312";

	// Organization 1
	private static final String ORGANIZATION_ID_1 = "-101";
	private static final String EMAIL_ID_1 = "1";

	// Organization 23
	private static final String ORGANIZATION_ID_23 = "-23";
	private static final String EMAIL_ID_23 = "-231";
	private static final String PHONE_ID_23 = "-232";
	private static final String ADDRESS_ID_23 = "-233";

	// SQL QUERIES TO INSERT BUSINESS OPPORTUNITY DATASET ON DATABASE
	public static void createBusinessOpportunity(String id, String title, String description, String organization_id)
			throws SQLException {
		Connection dbConnection = DBConnection.getConnection(BusinessOpportunityDatabase.BUSINESS_DATABASE_URL);
		String businessOpportunity = "INSERT INTO business.business_opportunity(id, title, description, organization_id, deleted) \n"
				+ "VALUES (" + id + ", '" + title + "', '" + description + "', " + organization_id + ", 'false');";
		DBHelper.runDataModificationQuery(dbConnection, businessOpportunity);
	}

	public static void injectOrganizationsDataOnDatabase() throws SQLException {

		// Organization 1
		OrganizationsDatabase.injectOrganizationOnDatabase(ORGANIZATION_ID_1, OrganizationsData.VALID_COMPANY_NAME_5,
				OrganizationsData.VALID_FANTASY_NAME_5, OrganizationsData.VALID_CNPJ_7, null, null,
				Utils.getTimeStamp(), USER_ID);

		OrganizationsDatabase.injectContactInfoEmailOnDatabase(EMAIL_ID_1, ORGANIZATION_ID_1,
				OrganizationsData.VALID_EMAIL_5, OrganizationsData.MAIN_EMAIL_TYPE_ENGLISH);

		// Organization 23
		OrganizationsDatabase.injectOrganizationOnDatabase(ORGANIZATION_ID_23, OrganizationsData.VALID_COMPANY_NAME_10,
				OrganizationsData.VALID_FANTASY_NAME_10, OrganizationsData.VALID_CNPJ_10,
				OrganizationsData.VALID_DESCRIPTION_1, null, Utils.getTimeStamp(), USER_ID);

		OrganizationsDatabase.injectContactInfoEmailOnDatabase(EMAIL_ID_23, ORGANIZATION_ID_23,
				OrganizationsData.VALID_EMAIL_10, OrganizationsData.MAIN_EMAIL_TYPE_ENGLISH);

		OrganizationsDatabase.injectContactInfoPhoneOnDatabase(PHONE_ID_23, OrganizationsData.VALID_PHONE_NUMBER_1,
				OrganizationsData.MAIN_PHONE_TYPE_ENGLISH, OrganizationsData.VALID_DDI_CODE_1, ORGANIZATION_ID_23);

		OrganizationsDatabase.injectContactInfoAddressOnDatabase(ADDRESS_ID_23, ORGANIZATION_ID_23,
				OrganizationsData.VALID_COMPLEMENT_1, OrganizationsData.VALID_DISTRICT_1,
				OrganizationsData.VALID_STREET_1, OrganizationsData.NATIONAL_DOMAIN_ENGLISH,
				OrganizationsData.VALID_CEP_1, OrganizationsData.VALID_CITY_1,
				OrganizationsData.VALID_ADDRESS_NUMBER_1, OrganizationsData.VALID_STATE_1,
				OrganizationsData.VALID_COUNTRY_1);

		OrganizationsDatabase.injectCollaboratorOnDatabase(OrganizationsData.PEOPLE_ID_1, OrganizationsData.VALID_DEPARTAMENT_1,
				OrganizationsData.VALID_OFFICE_1, OrganizationsData.PEOPLE_ID_1, ORGANIZATION_ID_23);

	}

	public static void injectBusinessOpportunityDataOnDatabase() throws SQLException {
		BusinessOpportunityDatabase.createBusinessOpportunity(BusinessOpportunityData.ID5,
				BusinessOpportunityData.TITLE5, BusinessOpportunityData.DESCRIPTION5,
				BusinessOpportunityDatabase.ORGANIZATION_ID_23);
		BusinessOpportunityDatabase.createBusinessOpportunity(BusinessOpportunityData.ID6,
				BusinessOpportunityData.TITLE6A, BusinessOpportunityData.DESCRIPTION6,
				BusinessOpportunityDatabase.ORGANIZATION_ID_23);
		BusinessOpportunityDatabase.createBusinessOpportunity(BusinessOpportunityData.ID7,
				BusinessOpportunityData.TITLE7A, BusinessOpportunityData.DESCRIPTION6,
				BusinessOpportunityDatabase.ORGANIZATION_ID_23);
		BusinessOpportunityDatabase.createBusinessOpportunity(BusinessOpportunityData.ID8,
				BusinessOpportunityData.TITLE8A, BusinessOpportunityData.DESCRIPTION6,
				BusinessOpportunityDatabase.ORGANIZATION_ID_23);
		BusinessOpportunityDatabase.createBusinessOpportunity(BusinessOpportunityData.ID9,
				BusinessOpportunityData.TITLE9A, BusinessOpportunityData.DESCRIPTION6,
				BusinessOpportunityDatabase.ORGANIZATION_ID_23);
		BusinessOpportunityDatabase.createBusinessOpportunity(BusinessOpportunityData.ID10,
				BusinessOpportunityData.TITLE10A, BusinessOpportunityData.DESCRIPTION6,
				BusinessOpportunityDatabase.ORGANIZATION_ID_23);
		BusinessOpportunityDatabase.createBusinessOpportunity(BusinessOpportunityData.ID11,
				BusinessOpportunityData.TITLE11, BusinessOpportunityData.DESCRIPTION6,
				BusinessOpportunityDatabase.ORGANIZATION_ID_23);
		BusinessOpportunityDatabase.createBusinessOpportunity(BusinessOpportunityData.ID12,
				BusinessOpportunityData.TITLE12, BusinessOpportunityData.DESCRIPTION6,
				BusinessOpportunityDatabase.ORGANIZATION_ID_23);
	}

	public static void clearBusinessOpportunityDataOnDatabase() throws SQLException {
		Connection dbConnection = DBConnection.getConnection(BusinessOpportunityDatabase.BUSINESS_DATABASE_URL);
		DBHelper.runDataModificationQuery(dbConnection, BusinessOpportunityDatabase.DELETE_BUSINESS_OPPORTUNITY);
	}

	public static void closeBusinessOpportunityDatabaseConnection() throws SQLException {
		Connection dbConnection = DBConnection.getConnection(BusinessOpportunityDatabase.BUSINESS_DATABASE_URL);
		if (!dbConnection.isClosed())
			dbConnection.close();
	}

}
