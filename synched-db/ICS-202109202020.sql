DELETE FROM serving_table_location WHERE id=101000102;
insert into serving_table_location(id,code,name,description,num_rows,num_columns,apply_service_charge,sc_based_on,sc_amount,is_sc_percentage,apply_service_tax,service_tax_id,que_no_prefix,is_auto_layout,bg_image,created_by,created_at,updated_by,updated_at,is_deleted,is_system,last_sync_at) values ("101000102","TABLE2","table 2","","0","0","0",null,null,"0","0",null,"","0",null,"0","2021-09-20 20:18:38.0",null,null,"0","0","2021-09-20 20:18:38.0");
DELETE FROM serving_tables WHERE id=101000102;
insert into serving_tables(id,code,name,description,is_valid,covers,row_position,column_position,serving_table_location_id,layout_image,status,created_by,created_at,updated_by,updated_at,is_system,is_deleted,last_sync_at) values ("101000102","T2","table 2",null,"1","2",null,null,"101000102","1","1","0","2021-09-20 20:19:09.0",null,null,"0","0","2021-09-20 20:19:09.0");
