INSERT INTO core.role (id, name) VALUES (1, 'Administrador Geral'), (2, 'Colaborador');

INSERT INTO core.permission (id, name, label, description, module_id) VALUES
    (1, 'CORE/insert-*', 'Inserir Tudo', 'Permissão para inserir todas as entidades no módulo core', 1),
    (2, 'CORE/read-*', 'Visualizar Tudo', 'Permissão para visualizar todas as entidades no módulo core', 1),
    (3, 'CORE/update-*', 'Atualizar Tudo', 'Permissão para atualizar todas as entidades no módulo core', 1),
    (4, 'CORE/delete-*', 'Remover Tudo', 'Permissão para remover todas as entidades no módulo core', 1),
    
    (5, 'CORE/insert-organization', 'Inserir Organização', 'Permissão para inserir organizações no módulo core', 1),
    (6, 'CORE/read-organization', 'Visualizar Organização', 'Permissão para visualizar organizações no módulo core', 1),
    (7, 'CORE/update-organization', 'Atualizar Organização', 'Permissão para atualizar organizações no módulo core', 1),
    (8, 'CORE/delete-organization', 'Remover Organização', 'Permissão para remover organizações no módulo core', 1),
    
    (9, 'CORE/insert-module', 'Inserir Módulo', 'Permissão para inserir módulos no módulo core', 1),
    (10, 'CORE/read-module', 'Visualizar Módulo', 'Permissão para visualizar módulos no módulo core', 1),
    (11, 'CORE/update-module', 'Atualizar Módulo', 'Permissão para atualizar módulos no módulo core', 1),
    (12, 'CORE/delete-module', 'Remover Módulo', 'Permissão para remover módulos no módulo core', 1),
    
    (13, 'CORE/insert-user', 'Inserir Usuário', 'Permissão para inserir usuários no módulo core', 1),
    (14, 'CORE/read-user', 'Visualizar Usuário', 'Permissão para visualizar usuários no módulo core', 1),
    (15, 'CORE/update-user', 'Atualizar Usuário', 'Permissão para atualizar usuários no módulo core', 1),
    (16, 'CORE/delete-user', 'Remover Usuário', 'Permissão para remover usuários no módulo core', 1),
    
    (17, 'CORE/insert-role', 'Inserir Papel', 'Permissão para inserir papéis no módulo core', 1),
    (18, 'CORE/read-role', 'Visualizar Papel', 'Permissão para visualizar papéis no módulo core', 1),
    (19, 'CORE/update-role', 'Atualizar Papel', 'Permissão para atualizar papéis no módulo core', 1),
    (20, 'CORE/delete-role', 'Remover Papel', 'Permissão para remover papéis no módulo core', 1),
    (21, 'CORE/insert-member', 'Inserir Membro', 'Permissão para inserir membros no módulo core', 1),
    (22, 'CORE/read-member', 'Visualizar Membro', 'Permissão para visualizar membros no módulo core', 1),
    (23, 'CORE/update-member', 'Atualizar Membro', 'Permissão para atualizar membros no módulo core', 1),
    (24, 'CORE/delete-member', 'Remover Membro', 'Permissão para remover membros no módulo core', 1),
    
    (25, 'CORE/insert-people', 'Inserir Pessoas', 'Permissão para inserir pessoas no módulo core', 1),
    (26, 'CORE/read-people', 'Visualizar Pessoas', 'Permissão para visualizar pessoas no módulo core', 1),
    (27, 'CORE/update-people', 'Atualizar Pessoas', 'Permissão para atualizar pessoas no módulo core', 1),
    (28, 'CORE/delete-people', 'Remover Pessoas', 'Permissão para remover pessoas no módulo core', 1),
    
    (29, 'BUSINESS/insert-*', 'Inserir Tudo', 'Permissão para inserir todas as entidades no módulo business', 2),
    (30, 'BUSINESS/read-*', 'Visualizar Tudo', 'Permissão para visualizar todas as entidades no módulo business', 2),
    (31, 'BUSINESS/update-*', 'Atualizar Tudo', 'Permissão para atualizar todas as entidades no módulo business', 2),
    (32, 'BUSINESS/delete-*', 'Remover Tudo', 'Permissão para remover todas as entidades no módulo business', 2),
    
    (33, 'BUSINESS/insert-lead', 'Inserir Lead', 'Permissão para inserir leads no módulo business', 2),
    (34, 'BUSINESS/read-lead', 'Visualizar Lead', 'Permissão para visualizar leads no módulo business', 2),
    (35, 'BUSINESS/update-lead', 'Atualizar Lead', 'Permissão para atualizar leads no módulo business', 2),
    (36, 'BUSINESS/delete-lead', 'Remover Lead', 'Permissão para remover leads no módulo business', 2),
    
    (37, 'BUSINESS/insert-organization', 'Inserir Organização', 'Permissão para inserir organizações no módulo business', 2),
    (38, 'BUSINESS/read-organization', 'Visualizar Organização', 'Permissão para visualizar organizações no módulo business', 2),
    (39, 'BUSINESS/update-organization', 'Atualizar Organização', 'Permissão para atualizar organizações no módulo business', 2),
    (40, 'BUSINESS/delete-organization', 'Remover Organização', 'Permissão para remover organizações no módulo business', 2),
    
    (41, 'TRACKER/insert-*', 'Inserir Tudo', 'Permissão para inserir todas as entidades no módulo tracker', 3),
    (42, 'TRACKER/read-*', 'Visualizar Tudo', 'Permissão para visualizar todas as entidades no módulo tracker', 3),
    (43, 'TRACKER/update-*', 'Atualizar Tudo', 'Permissão para atualizar todas as entidades no módulo tracker', 3),
    (44, 'TRACKER/delete-*', 'Remover Tudo', 'Permissão para remover todas as entidades no módulo tracker', 3),
    
    (45, 'TRACKER/insert-tracker-model', 'Inserir Board', 'Permissão para inserir boards, assim como adicionar, editar e excluir status, atributos customizados e transições na criação do tracker model', 3),
    (46, 'TRACKER/read-tracker-model', 'Visualizar Board', 'Permissão para visualizar boards existentes no módulo tracker. Dependência(s): [Tracker: Visualizar Card, Visualizar Pessoas, Visualizar Organização. Core: Visualizar Pessoas, Visualizar Organização]', 3),
    (47, 'TRACKER/update-tracker-model', 'Atualizar Board', 'Permissão para atualizar boards,  assim como editar status, atributos customizados e transições no módulo tracker', 3),
    (48, 'TRACKER/delete-tracker-model', 'Remover Board', 'Permissão para remover boards no módulo tracker', 3),
    
    (49, 'TRACKER/insert-tracker', 'Inserir Card', 'Permissão para inserir card no módulo tracker', 3),
    (50, 'TRACKER/read-tracker', 'Visualizar Card', 'Permissão para visualizar cards no módulo tracker', 3),
    (51, 'TRACKER/update-tracker', 'Atualizar Card', 'Permissão para atualizar cards no módulo tracker', 3),
    (52, 'TRACKER/delete-tracker', 'Remover Card', 'Permissão para remover cards no módulo tracker', 3),
    
    (53, 'TRACKER/insert-tag', 'Inserir Tags', 'Permissão para inserir tags no módulo tracker', 3),
    (54, 'TRACKER/read-tag', 'Visualizar Tags', 'Permissão para visualizar tags no módulo tracker', 3),
    (55, 'TRACKER/update-tag', 'Atualizar Tags', 'Permissão para atualizar tags no módulo tracker', 3),
    (56, 'TRACKER/delete-tag', 'Remover Tags', 'Permissão para remover tags no módulo tracker', 3),
    
    (57, 'TRACKER/insert-checklist', 'Inserir Checklist', 'Permissão para inserir checklists no módulo tracker', 3),
    (58, 'TRACKER/read-checklist', 'Visualizar Checklist','Permissão para visualizar checklists no módulo tracker', 3),
    (59, 'TRACKER/update-checklist', 'Atualizar Checklist','Permissão para atualizar checklists no módulo tracker', 3),
    (60, 'TRACKER/delete-checklist', 'Remover Checklist', 'Permissão para remover checklists no módulo tracker', 3),
    
    (61, 'TRACKER/insert-checklist-item', 'Inserir Itens de Checklist', 'Permissão para inserir itens de checklist no módulo tracker', 3),
    (62, 'TRACKER/read-checklist-item', 'Visualizar Itens de Checklist', 'Permissão para visualizar itens de checklist no módulo tracker', 3),
    (63, 'TRACKER/update-checklist-item', 'Atualizar Itens de Checklist', 'Permissão para atualizar itens de checklists no módulo tracker', 3),
    (64, 'TRACKER/delete-checklist-item', 'Remover Itens de Checklist', 'Permissão para remover itens de checklists no módulo tracker', 3),
    
    (65, 'TRACKER/insert-comment', 'Inserir Comentários', 'Permissão para inserir comentários no módulo tracker', 3),
    (66, 'TRACKER/read-comment', 'Visualizar Comentários', 'Permissão para visualizar comentários no módulo tracker', 3),
    (67, 'TRACKER/update-comment', 'Atualizar Comentários', 'Permissão para atualizar comentários no módulo tracker', 3),
    (68, 'TRACKER/delete-comment', 'Remover Comentários', 'Permissão para remover comentários no módulo tracker', 3),
    
    (69, 'TRACKER/insert-status', 'Inserir Status', 'Permissão para inserir status no módulo tracker', 3),
    (70, 'TRACKER/read-status', 'Visualizar Status', 'Permissão para visualizar status no módulo tracker', 3),
    (71, 'TRACKER/update-status', 'Atualizar Status', 'Permissão para atualizar status no módulo tracker', 3),
    (72, 'TRACKER/delete-status', 'Remover Status', 'Permissão para remover status no módulo tracker', 3),
    
    (73, 'TRACKER/insert-attribute', 'Inserir Atributos', 'Permissão para inserir atributos no módulo tracker', 3),
    (74, 'TRACKER/read-attribute', 'Visualizar Atributos', 'Permissão para visualizar atributos no módulo tracker', 3),
    (75, 'TRACKER/update-attribute', 'Atualizar Atributos', 'Permissão para atualizar atributos no módulo tracker', 3),
    (76, 'TRACKER/delete-attribute', 'Remover Atributos', 'Permissão para remover atributos no módulo tracker', 3),
    
    (77, 'TRACKER/insert-transition', 'Inserir Transição', 'Permissão para inserir transições no módulo tracker', 3),
    (78, 'TRACKER/read-transition', 'Visualizar Transição', 'Permissão para visualizar transições no módulo tracker', 3),
    (79, 'TRACKER/update-transition', 'Atualizar Transição', 'Permissões para atualizar transições no módulo tracker', 3),
    (80, 'TRACKER/delete-transition', 'Remover Transição', 'Permissão para remover transições no módulo tracker', 3),
       
    (81, 'TRACKER/read-people', 'Visualizar Pessoas', 'Permissão para visualizar pessoas no módulo tracker', 3),
    
    (82, 'TRACKER/read-organization', 'Visualizar Organização', 'Permissão para visualizar organizações no módulo tracker', 3);

    
INSERT INTO core.role_permission (role_id, permission_id)
    VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 29), (1, 30), (1, 31), (1, 32), (1, 41), (1, 42), (1, 43), (1, 44);


ALTER SEQUENCE role_id_seq RESTART WITH 3;

ALTER SEQUENCE permission_id_seq RESTART WITH 83;
