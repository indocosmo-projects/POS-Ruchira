update menus set id="101000101", code="AC", name="AC", description="", is_default_menu="0", enable_h1_button="1", enable_h2_button="1", enable_h3_button="1", color="#000000", is_active="1", created_by="0", created_at="2020-07-13 04:11:11.0", updated_by="0", updated_at="2020-07-18 03:35:35.0", is_deleted="0", is_system="0" where id=101000101 and is_deleted = 0 and is_synchable = 1;
DELETE FROM menu_departments WHERE id=101000105;
insert into menu_departments(id,menu_id,department_id,is_deleted) values ("101000105","101000101","1","0");
