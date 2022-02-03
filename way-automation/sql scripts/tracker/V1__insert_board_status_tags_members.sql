-- CRIAÇÃO BOARD 

INSERT INTO tracker.tracker_model(id, name, module_id, description, deleted, created_on, created_by) VALUES (1, 'Board_1', 2, 'Nome do board 1', false, '2021-07-14 10:56:58.631', 500);

-- CRIAÇÃO DO STATUS

INSERT INTO tracker.status(id, name, "position", deleted, tracker_model_id) VALUES (1, 'INICIO', 1, 'false', 1);
INSERT INTO tracker.status(id, name, "position", deleted, tracker_model_id) VALUES (2, 'ANDAMENTO', 2, 'false', 1);
INSERT INTO tracker.status(id, name, "position", deleted, tracker_model_id) VALUES (3, 'FIM', 3, 'false', 1);

-- CRIAÇÃO DO CARD 1

INSERT INTO tracker.tracker(title, description, due_date, created_on, created_by, status_id, tracker_model_id)VALUES ('Card 1', 'Descrição Card 1', '2021-07-14 03:00:00', '2021-07-14 14:23:03.285', 500, 1, 1);
INSERT INTO tracker.tracker(title, description, due_date, created_on, created_by, status_id, tracker_model_id)VALUES ('Card 2', 'Descrição Card 2', '2021-07-14 03:00:00', '2021-07-14 14:23:03.285', 500, 1, 1);
INSERT INTO tracker.tracker(title, description, due_date, created_on, created_by, status_id, tracker_model_id)VALUES ('Card 3', 'Descrição Card 2', '2021-07-14 03:00:00', '2021-07-14 14:23:03.285', 500, 2, 1);

-- CRIAÇÃO DA TAGS

INSERT INTO tracker.tag(id, name, color, tracker_model_id) VALUES (1, 'tag1', '#654d40', 1);
INSERT INTO tracker.tag(id, name, color, tracker_model_id) VALUES (2, 'tag2', '#504d20', 1);

-- RELACIONAMENTO ENTRE TRACKER_MODEL e TAGS

INSERT INTO tracker.tracker_tag(id, tracker_id, tag_id)VALUES (1, 1, 1);
INSERT INTO tracker.tracker_tag(id, tracker_id, tag_id)VALUES (2, 1, 2);
INSERT INTO tracker.tracker_tag(id, tracker_id, tag_id)VALUES (3, 2, 1);
INSERT INTO tracker.tracker_tag(id, tracker_id, tag_id)VALUES (4, 2, 2);
INSERT INTO tracker.tracker_tag(id, tracker_id, tag_id)VALUES (5, 3, 1);
INSERT INTO tracker.tracker_tag(id, tracker_id, tag_id)VALUES (6, 3, 2);

-- ASSOCIAÇÃO CARD E MENBROS

INSERT INTO tracker.assignee(id, created_by, created_on,  people_id, tracker_model_id)VALUES (1, 500, '2021-07-14 14:40:32.349', 7, 1);
INSERT INTO tracker.assignee(id, created_by, created_on,  people_id, tracker_model_id)VALUES (2, 500, '2021-07-14 14:40:32.349', 76, 1);

-- RELACIONAMENTO ENTRE TRACKER e ASSIGNEE

INSERT INTO tracker.tracker_assignee(tracker_id, assignee_id) VALUES (1, 1);
INSERT INTO tracker.tracker_assignee(tracker_id, assignee_id) VALUES (1, 2);
INSERT INTO tracker.tracker_assignee(tracker_id, assignee_id) VALUES (2, 1);
INSERT INTO tracker.tracker_assignee(tracker_id, assignee_id) VALUES (2, 2);
INSERT INTO tracker.tracker_assignee(tracker_id, assignee_id) VALUES (3, 1);
INSERT INTO tracker.tracker_assignee(tracker_id, assignee_id) VALUES (3, 2);
