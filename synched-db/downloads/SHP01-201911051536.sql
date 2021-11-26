DELETE FROM voucher_class WHERE id=101000101;
insert into voucher_class(id,name,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","CASH","0","2019-11-05 03:34:33.0",null,null,"0","0");
DELETE FROM voucher_types WHERE id=101000101;
insert into voucher_types(id,code,name,description,voucher_type,value,is_overridable,is_change_payable,account_code,is_valid,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","V0001","Gift Voucher","","101000101","1000.00000","0","0","0","1","0","2019-11-05 03:35:20.0",null,null,"0","0");
