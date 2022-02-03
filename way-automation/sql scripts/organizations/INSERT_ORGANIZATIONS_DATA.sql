-- SCRIPT TO INJECT ON DATABASE ALL ORGANIZATIONS DATA TO BE USED IN AUTOMATED TESTS

-- THE FOLLOW ORDER MUST BE FOLLOWED TO INSERT AN ORGANIZATION CORRECTLY
	-- 1º: Insert Organization
	-- 2º: Insert Contact info (Email, Telephone or Address)
	-- 3º: Insert Collaborator

--------------------------------- EXAMPLES -----------------------------------------

-- INSERT ORGANIZATION
INSERT INTO core.organization(id, name, fantasy_name, government_code, description, image, deleted, created_on, created_by) 
VALUES (1, 'Valid Company Name', 'valid fantasy name', '03487929000185', 'valid description', null, false, '2021-07-22 00:00:00', 312);

-- INSERT CONTACT INFO (WORKS FOR EMAIL/TELEPHONE/ADDRESS)
INSERT INTO core.contact_info(id, contact_info_type, phone, phone_tag, phone_country_code, owner_type, owner_id, address_complement, 
address_neighborhood, address_street, contact_domain, address_zip_code, address_city, address_number, address_state, address_country, deleted, email, email_tag) 
VALUES (1, 'EMAIL', null, null, null, 'ORGANIZATION', 1, null, null, null, null, null, null, null, null, null, false, 'valid_email@gmail.com', 'MAIN');

-- INSERT COLLABORATOR
INSERT INTO core.people_organization(id, department, position, people_id, organization_id, technical_manager)
VALUES (1, 'departament test', 'position test', 301, 1, null);