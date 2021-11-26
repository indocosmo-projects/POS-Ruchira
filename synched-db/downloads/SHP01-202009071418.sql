DELETE FROM shop_users WHERE id=5;
insert into shop_users(id,shop_id,user_id,user_group_id,is_deleted) values ("5","101","101000112","1","0");
DELETE FROM users WHERE id=101000112;
insert into users(id,code,name,card_no,user_group_id,employee_id,password,valid_from,valid_to,is_active,is_admin,email,lastlogin_date,token_id,token_expire_time,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000112","MNGR","Manager","1010","1",null,"1e48c4420b7073bc11916c6c1de226bb",null,null,"1","0","",null,null,null,"0","2020-09-07 02:17:53.0",null,null,"0","0");
