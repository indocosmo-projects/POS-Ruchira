DELETE FROM shop_users WHERE id=9;
insert into shop_users(id,shop_id,user_id,user_group_id,is_deleted) values ("9","101","101000101","1","0");
DELETE FROM users WHERE id=101000101;
insert into users(id,code,name,card_no,user_group_id,employee_id,password,valid_from,valid_to,is_active,is_admin,email,lastlogin_date,token_id,token_expire_time,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","101","Manager","1000","1",null,"a9b7ba70783b617e9998dc4dd82eb3c5","2021-11-12",null,"1","0","",null,null,null,"0","2021-11-12 11:09:32.0",null,null,"0","0");
