DELETE FROM combo_contents WHERE id=101000101;
insert into combo_contents(id,code,name,description,uom_id,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","TEST","TETS","","43","0","2020-02-04 03:41:19.0",null,null,"0","0");
DELETE FROM combo_content_substitutions WHERE id=101000101;
insert into combo_content_substitutions(id,combo_content_id,substitution_sale_item_id,price_diff,qty,is_default,created_by,created_at,updated_by,updated_at,is_deleted) values ("101000101","101000101","101000102","10.00000","1.00000","0","0","2020-02-04 15:41:19.0",null,null,"0");
DELETE FROM combo_content_substitutions WHERE id=101000102;
insert into combo_content_substitutions(id,combo_content_id,substitution_sale_item_id,price_diff,qty,is_default,created_by,created_at,updated_by,updated_at,is_deleted) values ("101000102","101000101","101000111","5.00000","1.00000","0","0","2020-02-04 15:41:19.0",null,null,"0");
