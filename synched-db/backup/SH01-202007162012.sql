DELETE FROM shop_users WHERE id=5;
insert into shop_users(id,shop_id,user_id,user_group_id,is_deleted) values ("5","101","101000102","1","0");
DELETE FROM users WHERE id=101000102;
insert into users(id,code,name,description,card_no,user_group_id,employee_id,password,valid_from,valid_to,is_active,is_admin,email,lastlogin_date,token_id,token_expire_time,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000102","MNG","Manager",null,"1000","1",null,"a9b7ba70783b617e9998dc4dd82eb3c5","2020-07-14",null,"1","0","",null,null,null,"0","2020-07-16 08:11:07.0",null,null,"0","0");
