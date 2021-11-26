DELETE FROM kitchens WHERE id=101000101;
insert into kitchens(id,code,name,description,printer_name,printer_port,created_by,created_at,updated_by,updated_at,is_deleted,is_system,last_sync_at) values ("101000101","K1","kitchen one","",null,null,"0","2020-07-13 04:00:28.0",null,null,"0","0","2020-07-13 16:00:28.0");
DELETE FROM kitchens WHERE id=101000102;
insert into kitchens(id,code,name,description,printer_name,printer_port,created_by,created_at,updated_by,updated_at,is_deleted,is_system,last_sync_at) values ("101000102","K2","KICTHENTWO","",null,null,"0","2020-07-13 04:00:50.0",null,null,"0","0","2020-07-13 16:00:50.0");
DELETE FROM item_classes WHERE id=101000101;
insert into item_classes(id,code,hsn_code,name,alternative_name,department_id,description,menu_id,super_class_id,tax_calculation_method,taxation_based_on,tax_id,tax_exemption,tax_group_id,display_order,print_order,account_code,fg_color,bg_color,item_thumb,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","ALL",null,"all","","1","",null,null,null,null,null,"0",null,null,null,"","","","","0","2020-07-13 04:01:31.0",null,null,"0","0");
