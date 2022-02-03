CREATE TABLE core.contact_info(
    id serial NOT NULL PRIMARY KEY,
    contact_info_type character varying(100),
    phone character varying(20),
    phone_tag character varying(20),
    phone_country_code character varying(4),
    owner_type character varying(20),
    owner_id integer,
    address_complement character varying(100),
    address_neighborhood character varying(100) ,
    address_street character varying(255),
    contact_domain character varying(20),
    address_zip_code character varying(20),
    address_city character varying(100),
    address_number character varying(255),
    address_state character varying(255),
    address_country character varying(255),
    deleted boolean DEFAULT false,
    email  character varying(254),
    email_tag character varying(100)
);

