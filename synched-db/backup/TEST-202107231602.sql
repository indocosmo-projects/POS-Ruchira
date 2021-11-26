DELETE FROM menus WHERE id=101000101;
insert into menus(id,code,name,description,is_default_menu,enable_h1_button,enable_h2_button,enable_h3_button,color,is_active,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","D01","default menu","","1","0","0","0","#000000","1","0","2021-07-23 04:00:09.0",null,null,"0","0");
DELETE FROM menu_departments WHERE id=101000101;
insert into menu_departments(id,menu_id,department_id,is_deleted) values ("101000101","101000101","101000101","0");
