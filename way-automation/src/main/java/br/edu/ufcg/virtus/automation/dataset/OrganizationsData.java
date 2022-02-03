package br.edu.ufcg.virtus.automation.dataset;

public abstract class OrganizationsData {

	// General data
	public static final String INVALID_CNPJ_MASK = "ABCDEFGH";
	public static final String INVALID_CNPJ = "00.000.000/0000-00";
	public static final String INVALID_CNPJ_MESSAGE = "CNPJ inválido.";
	public static final int NUMBER_OF_ORGANIZATIONS_INSERTED = 2;
	public static final String INVALID_EMAIL_FORMAT = "email123.com";
	public static final String INVALID_EMAIL_FORMAT_MESSAGE = "E-mail inválido";
	public static final String INVALID_FANTASY_NAME = "Invalid fantasy name";
	
	public static final String STRING_255_CHARACTERS = "CHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTE";
	public static final int STRING_255_SIZE = 255;
	public static final String STRING_256_CHARACTERS = "CHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTER";
	public static final int STRING_256_SIZE = 256;
	public static final String STRING_100_CHARACTERS = "CHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERS@HOTMAIL.COM";
	public static final int STRING_100_SIZE = 100;
	public static final String STRING_101_CHARACTERS = "ACHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERSIZECHARACTERS@HOTMAIL.COM";
	public static final int STRING_101_SIZE = 101;
	public static final String STRING_5000_CHARACTERS = "LoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremi";
	public static final int STRING_5000_SIZE = 5000;
	public static final String STRING_5001_CHARACTERS = "ALoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremipsumdolorsitametLoremi";
	
	
	// Domain
	public static final String NATIONAL_DOMAIN = "Nacional";
	public static final String NATIONAL_DOMAIN_ENGLISH = "NATIONAL";
	public static final String INTERNACIONAL_DOMAIN = "Internacional";
	public static final String INTERNACIONAL_DOMAIN_ENGLISH = "INTERNATIONAL";

	// Email Type
	public static final String COMMERCIAL_EMAIL_TYPE = "Comercial";
	public static final String COMMERCIAL_EMAIL_TYPE_ENGLISH = "COMMERCIAL";
	public static final String MAIN_EMAIL_TYPE = "Principal";
	public static final String MAIN_EMAIL_TYPE_ENGLISH = "MAIN";

	// Phone Type
	public static final String MAIN_PHONE_TYPE = "Principal";
	public static final String MAIN_PHONE_TYPE_ENGLISH = "MAIN";
	public static final String CELULAR_PHONE_TYPE = "Celular";
	public static final String CELULAR_PHONE_TYPE_ENGLISH = "MOBILE";

	// CNPJ Data
	public static final String VALID_CNPJ_1 = "31634823000100";
	public static final String VALID_CNPJ_2 = "33708792000102";
	public static final String VALID_CNPJ_3 = "87026151000105";
	public static final String VALID_CNPJ_4 = "64034013000164";
	public static final String VALID_CNPJ_5 = "67694018000101";
	public static final String VALID_CNPJ_6 = "86455497000158";
	public static final String VALID_CNPJ_7 = "28962510000169";
	public static final String VALID_CNPJ_8 = "06722633000190";
	public static final String VALID_CNPJ_9 = "63003979000171";
	public static final String VALID_CNPJ_10 = "43434407000130";
	public static final String VALID_CNPJ_11 = "21579730000169";
	public static final String VALID_CNPJ_12 = "03306352000168";
	public static final String VALID_CNPJ_30 = "57332629000156";

	public static final String NEW_CNPJ = "65.939.480/0001-04";

	public static final String VALID_CNPJ_VALUE = "82665800000196";
	public static final String INVALID_CNPJ_VALUE = "826658000001960";
	public static final int VALID_CNPJ_SIZE = 14;
	public static final int INVALID_CNPJ_SIZE = 15;

	// Company Name Data
	public static final String VALID_COMPANY_NAME_1 = "Valid name 1";
	public static final String VALID_COMPANY_NAME_2 = "Valid name 2";
	public static final String VALID_COMPANY_NAME_3 = "Valid name 3";
	public static final String VALID_COMPANY_NAME_4 = "Valid name 4";
	public static final String VALID_COMPANY_NAME_5 = "Valid name 5";
	public static final String VALID_COMPANY_NAME_6 = "Valid name 6";
	public static final String VALID_COMPANY_NAME_7 = "Valid name 7";
	public static final String VALID_COMPANY_NAME_8 = "Valid name 8";
	public static final String VALID_COMPANY_NAME_9 = "Valid name 9";
	public static final String VALID_COMPANY_NAME_10 = "Valid name 10";
	public static final String VALID_COMPANY_NAME_11 = "Valid name 11";
	public static final String VALID_COMPANY_NAME_30 = "Valid name 30";

	public static final String NEW_COMPANY_NAME = "New company name";

	// Fantasy Name Data
	public static final String VALID_FANTASY_NAME_1 = "Valid fantasy name 1";
	public static final String VALID_FANTASY_NAME_2 = "Valid fantasy name 2";
	public static final String VALID_FANTASY_NAME_3 = "Valid fantasy name 3";
	public static final String VALID_FANTASY_NAME_4 = "Valid fantasy name 4";
	public static final String VALID_FANTASY_NAME_5 = "Valid fantasy name 5";
	public static final String VALID_FANTASY_NAME_6 = "Valid fantasy name 6";
	public static final String VALID_FANTASY_NAME_7 = "Valid fantasy name 7";
	public static final String VALID_FANTASY_NAME_8 = "Valid fantasy name 8";
	public static final String VALID_FANTASY_NAME_9 = "Valid fantasy name 9";
	public static final String VALID_FANTASY_NAME_10 = "Valid fantasy name 10";
	public static final String VALID_FANTASY_NAME_11 = "Valid fantasy name 11";
	public static final String VALID_FANTASY_NAME_30 = "Valid fantasy name 30";

	public static final String NEW_FANTASY_NAME = "New fantasy name";

	// Email Data
	public static final String VALID_EMAIL_1 = "valid.email1@gmail.com";
	public static final String VALID_EMAIL_2 = "valid.email2@gmail.com";
	public static final String VALID_EMAIL_3 = "valid.email3@gmail.com";
	public static final String VALID_EMAIL_4 = "valid.email4@gmail.com";
	public static final String VALID_EMAIL_5 = "valid.email5@gmail.com";
	public static final String VALID_EMAIL_6 = "valid.email6@gmail.com";
	public static final String VALID_EMAIL_7 = "valid.email7@gmail.com";
	public static final String VALID_EMAIL_8 = "valid.email8@gmail.com";
	public static final String VALID_EMAIL_9 = "valid.email9@gmail.com";
	public static final String VALID_EMAIL_10 = "valid.email10@gmail.com";
	public static final String VALID_EMAIL_11 = "valid.email11@gmail.com";
	public static final String VALID_EMAIL_30 = "valid.email30@gmail.com";

	public static final String NEW_EMAIL = "new.email@hotmail.com";

	// Description
	public static final String VALID_DESCRIPTION_1 = "Valid description for an Organization 1";
	public static final String VALID_DESCRIPTION_2 = "Valid description for an Organization 2";
	public static final String VALID_DESCRIPTION_3 = "Valid description for an Organization 3";
	public static final String VALID_DESCRIPTION_4 = "Valid description for an Organization 4";
	public static final String VALID_DESCRIPTION_5 = "Valid description for an Organization 5";

	public static final String NEW_DESCRIPTION = "New description for organization";

	// Collaborator Name
	public static final String VALID_COLLABORATOR_NAME_1 = "Deyvison Melo de Oliveira";
	public static final String VALID_COLLABORATOR_NAME_2 = "Pedro de Oliveira Lira";
	public static final String VALID_COLLABORATOR_NAME_3 = "Johnny Menezes Honorato";
	public static final String VALID_COLLABORATOR_NAME_4 = "Paulo André Braga Souto";

	public static final String NEW_COLLABORATOR_NAME = "Emanuel Dantas Filho";

	// Collaborator ID
	public static final String PEOPLE_ID_1 = "582"; // Paulo André Braga Souto

	// Departament
	public static final String VALID_DEPARTAMENT_1 = "Administrative";
	public static final String VALID_DEPARTAMENT_2 = "Administrative 2";
	public static final String VALID_DEPARTAMENT_3 = "Administrative 3";

	public static final String NEW_DEPARTAMENT = "Human Resources";

	// Office
	public static final String VALID_OFFICE_1 = "Manager";
	public static final String VALID_OFFICE_2 = "Manager 2";
	public static final String VALID_OFFICE_3 = "Manager 3";

	public static final String NEW_OFFICE = "Programmer";

	// DDI Code
	public static final String VALID_DDI_CODE_1 = "+55";
	public static final String VALID_DDI_CODE_2 = "+61";
	public static final String VALID_DDI_CODE_3 = "+243";

	public static final String NEW_DDI_CODE = "+44";
	
	public static final String DDI_CODE_4_CHARACTERS = "+123";
	public static final int DDI_CODE_4_SIZE = 4;
	public static final String DDI_CODE_5_CHARACTERS = "+1234";
	public static final int DDI_CODE_5_SIZE = 5;
	
	// Phone Number
	public static final String VALID_PHONE_NUMBER_1 = "23 41310-5317";
	public static final String VALID_PHONE_NUMBER_2 = "42 42079-0137";
	public static final String VALID_PHONE_NUMBER_3 = "83 66278-4561";

	public static final String NEW_PHONE_NUMBER = "83 3121-0102";
	
	public static final String PHONE_NUMBER_20_CHARACTERS = "12345678912345678912";
	public static final int PHONE_NUMBER_20_SIZE = 20;
	public static final String PHONE_NUMBER_21_CHARACTERS = "123456789123456789123";
	public static final int PHONE_NUMBER_21_SIZE = 21;
	
	// Cep
	public static final String VALID_CEP_1 = "58428830";
	public static final String VALID_CEP_2 = "60872486";
	public static final String VALID_CEP_3 = "85862730";

	public static final String NEW_CEP = "58429-900";
	public static final String CEP_9_CHARACTERS = "12345-123";
	public static final int CEP_9_SIZE = 9;
	
	public static final String CEP_10_CHARACTERS = "12345-1234";
	public static final int CEP_10_SIZE = 10;

	// Street
	public static final String VALID_STREET_1 = "R. Aprígio Veloso";
	public static final String VALID_STREET_2 = "R. Aprígio Veloso 2";
	public static final String VALID_STREET_3 = "R. Aprígio Veloso 3";

	public static final String NEW_STREET = "AV. Juvêncio Arruda";

	// Address Number
	public static final String VALID_ADDRESS_NUMBER_1 = "1500";
	public static final String VALID_ADDRESS_NUMBER_2 = "1502";
	public static final String VALID_ADDRESS_NUMBER_3 = "1503";

	public static final String NEW_ADDRESS_NUMBER = "2000";

	// Complement
	public static final String VALID_COMPLEMENT_1 = "Building";
	public static final String VALID_COMPLEMENT_2 = "Building 2";
	public static final String VALID_COMPLEMENT_3 = "Building 3";

	public static final String NEW_COMPLEMENT_VALUE = "Apartament";

	// District
	public static final String VALID_DISTRICT_1 = "Bodocongó";
	public static final String VALID_DISTRICT_2 = "Bodocongó 2";
	public static final String VALID_DISTRICT_3 = "Bodocongó 3";

	public static final String NEW_DISTRICT_VALUE = "Prata";

	// City
	public static final String VALID_CITY_1 = "Campina Grande";
	public static final String VALID_CITY_2 = "Campina Grande 2";
	public static final String VALID_CITY_3 = "Campina Grande 3";

	public static final String NEW_ADDRESS_CITY = "João Pessoa";

	// State
	public static final String VALID_STATE_1 = "Paraíba";
	public static final String VALID_STATE_2 = "Paraíba 2";
	public static final String VALID_STATE_3 = "Paraíba 3";

	public static final String NEW_ADDRESS_STATE = "Bahia";

	// Country
	public static final String VALID_COUNTRY_1 = "Brasil";
	public static final String VALID_COUNTRY_2 = "Brasil 2";
	public static final String VALID_COUNTRY_3 = "Brasil 3";

	public static final String NEW_ADDRESS_COUNTRY = "Argentina";

	// Image logo
	public static final String IMAGE_PATH_1 = "/images/imageLogo1.png";
	public static final String IMAGE_NAME_1 = "imageLogo1.png";

	public static final String IMAGE_PATH_2 = "/images/imageLogo2.jpeg";
	public static final String IMAGE_NAME_2 = "imageLogo2.jpeg";

	public static final String IMAGE_PATH_3 = "/images/imageLogo3.jpeg";
	public static final String IMAGE_NAME_3 = "imageLogo3.jpeg";
	
	public static final String INVALID_IMAGE_SIZE_11_MB = "/images/ImageWith11MB.png";
	public static final String INVALID_IMAGE_SIZE_10_MB = "/images/ImageWith10MB.jpg";
	
}
