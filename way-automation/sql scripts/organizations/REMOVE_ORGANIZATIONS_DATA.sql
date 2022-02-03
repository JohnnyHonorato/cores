-- SCRIPT TO DELETE ON DATABASE ALL ORGANIZATIONS DATA USED IN AUTOMATED TESTS

-- THE FOLLOW ORDER MUST BE FOLLOWED TO DELETE AN ORGANIZATION CORRECTLY
	-- 1º: Delete Contact info (Email, Telephone or Address)
	-- 2º: Delete Collaborator
	-- 3º: Delete Organization

--------------------------------- EXAMPLES -----------------------------------------
-- Reset contact info table
TRUNCATE core.contact_info CASCADE;

-- Reset collaborator table
TRUNCATE core.people_organization CASCADE;

-- Remove organization table
DELETE FROM core.organization;