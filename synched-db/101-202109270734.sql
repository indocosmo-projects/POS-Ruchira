update stations set is_deleted=1 where id =101000101;
DELETE FROM stations WHERE id=101000102;
insert into stations(id,code,name,description,type,created_by,created_at,updated_by,updated_at,is_deleted,is_system,last_sync_at) values ("101000102","101","Terminal 1","","2","0","2021-09-27 07:34:01.0",null,null,"0","0","2021-09-27 07:34:01.0");
