-- 2021-07-08

insert into user_role(user_account_id, role_id) values(366, 2);

-- 2021-09-17

ALTER TABLE core.filepath
  ADD file_type VARCHAR(255),
  ADD deleted BOOLEAN NOT NULL DEFAULT FALSE;
  
-- 2021-09-21

UPDATE core.module SET link='' WHERE name='Tracker';

UPDATE core.module SET icon='settings' WHERE name='Core';
UPDATE core.module SET icon='business' WHERE name='Business';

-- 2021-09-28

UPDATE core.user_account SET "password" = '4f26aeafdb2367620a393c973eddbe8f8b846ebd', "external" = true WHERE "username" = 'tulio.nobrega';
UPDATE core.user_account SET "password" = '4f26aeafdb2367620a393c973eddbe8f8b846ebd', "external" = true WHERE "username" = 'emilio.farias';
