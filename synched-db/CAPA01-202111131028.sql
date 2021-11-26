DELETE FROM discounts WHERE id=101000101;
insert into discounts(id,code,name,description,is_percentage,is_overridable,is_item_specific,permitted_for,is_promotion,price,account_code,grouping_quantity,allow_editing,is_valid,date_from,date_to,time_from,time_to,week_days,created_by,created_at,updated_by,updated_at,is_deleted,is_system,disc_password) values ("101000101","D01","Discount 10%","","1","1","0","2","0","10.00000","","0","0","1","2021-11-13",null,null,null,null,"0","2021-11-13 10:27:22.0",null,null,"0","0",null);
DELETE FROM voucher_class WHERE id=101000101;
insert into voucher_class(id,name,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","Special","0","2021-11-13 10:27:54.0",null,null,"0","0");
DELETE FROM voucher_types WHERE id=101000101;
insert into voucher_types(id,code,name,description,voucher_type,value,is_overridable,is_change_payable,account_code,is_valid,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","V01","Voucher ","","101000101","100.00000","0","0","a","1","0","2021-11-13 10:28:04.0",null,null,"0","0");
