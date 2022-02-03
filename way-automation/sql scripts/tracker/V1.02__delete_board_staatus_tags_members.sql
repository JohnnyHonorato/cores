--!!!Os scripts devem rodar na sequência estabelecida para que todos os dados sejam deletados com êxito!!!


-- DELETAR RELACIONAMENTO ENTRE TRACKER_MODEL e TAGS

TRUNCATE tracker.tracker_tag CASCADE;

-- DELETAR RELACIONAMENTO ENTRE TRACKER e ASSIGNEE

TRUNCATE tracker.tracker_assignee CASCADE;

-- DELETAR CRIAÇÃO BOARD 

TRUNCATE tracker.tracker_model CASCADE;

-- DELETAR CRIAÇÃO DO STATUS

DELETE FROM tracker.status;

-- DELETAR CRIAÇÃO DO CARD 1

DELETE FROM tracker.tracker;

-- DELETAR CRIAÇÃO DA TAGS

DELETE FROM tracker.tag;

-- DELETAR ASSOCIAÇÃO CARD E MEMBROS

DELETE FROM  tracker.assignee;

