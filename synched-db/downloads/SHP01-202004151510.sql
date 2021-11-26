DELETE FROM employees WHERE id=101000112;
insert into employees(id,code,employee_category_id,department_id,status,sex,dob,f_name,m_name,l_name,doj,wage_type,address,country,zip_code,phone,fax,email,loc_address,loc_country,loc_zip_code,loc_phone,card_no,loc_fax,cost_per_hour,over_time_pay_rate,work_permit,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000112","EMP","1","1","1","M","2020-01-07","emp","","",null,"2","","","","","","","","","","","3000","","100.00000","0.00000","","0","2020-04-15 03:08:36.0",null,null,"0","0");
DELETE FROM shop_users WHERE id=4;
insert into shop_users(id,shop_id,user_id,user_group_id,is_deleted) values ("4","101","101000111","102","0");
DELETE FROM users WHERE id=101000111;
insert into users(id,code,name,card_no,user_group_id,employee_id,password,valid_from,valid_to,is_active,is_admin,email,lastlogin_date,token_id,token_expire_time,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000111","EMP1","emp","3000","102","101000112","e93028bdc1aacdfb3687181f2031765d",null,null,"1","0","",null,null,null,"0","2020-04-15 03:09:08.0",null,null,"0","0");
