CREATE OR REPLACE FUNCTION core.unaccent(
  text)
    RETURNS text
    LANGUAGE 'sql'

    COST 100
    IMMUTABLE STRICT 
AS $BODY$
  select translate(UPPER($1),'ÁÀÂÃÄÉÈÊËÍÌÏÓÒÔÕÖÚÙÛÜçÇ', 'AAAAAEEEEIIIOOOOOUUUUcC');
$BODY$;

ALTER FUNCTION core.unaccent(text)
    OWNER TO virtus;
