DELETE FROM stations WHERE id=101000101;
insert into stations(id,code,name,description,type,created_by,created_at,updated_by,updated_at,is_deleted,is_system,last_sync_at) values ("101000101","M01","main","","2","0","2021-09-22 02:26:59.0",null,null,"0","0","2021-09-22 14:26:59.0");
DELETE FROM kitchens WHERE id=101000101;
insert into kitchens(id,code,name,description,printer_name,printer_port,created_by,created_at,updated_by,updated_at,is_deleted,is_system,last_sync_at) values ("101000101","K1","Kitchen1","",null,null,"0","2021-09-22 02:27:30.0",null,null,"0","0","2021-09-22 14:27:30.0");
