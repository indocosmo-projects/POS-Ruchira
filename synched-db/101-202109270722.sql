DELETE FROM stock_items WHERE id=101000101;
insert into stock_items(id,code,name,description,supplier_product_code,stock_item_attributes,stock_item_location,stock_item_category_id,prd_department_id,part_no,market_valuation_method,movement_method,uom_id,stock_uom_id,item_weight,qty_on_hand,unit_price,optimum_level,reorder_level,reorder_qty,preferred_supplier_id,last_purchase_unit_price,last_purchase_supplier_id,tax_id,tax_calculation_method,is_service_item,is_valid,is_manufactured,is_sellable,is_semi_finished,is_finished,sales_margin,is_sales_margin_percent,tax_percentage,qty_manufactured,top_consumption_rank,created_by,created_at,updated_by,updated_at,is_system,is_deleted) values ("101000101","101","Chicken 65","",null,null,null,null,null,null,"0",null,"43","27","1.00000",null,"150.00000",null,null,null,null,null,null,"1","1","0","1","0","1","0","0","0","0","0.00",null,"0","0","2021-09-27 07:20:34.0",null,null,"0","0");
DELETE FROM sale_items WHERE id=101000101;
insert into sale_items(id,stock_item_id,stock_item_code,code,hsn_code,is_group_item,group_item_id,is_combo_item,name,description,sub_class_id,is_valid,is_manufactured,alternative_name,is_printable_to_kitchen,name_to_print,alternative_name_to_print,barcode,is_open,qty_on_hand,uom_id,item_weight,profit_category_id,prd_department_id,tax_id,tax_exemption,tax_group_id,tax_id_home_service,tax_id_table_service,tax_id_take_away_service,fixed_price,whls_price,is_whls_price_pc,item_cost,tax_calculation_method,taxation_based_on,is_require_weighing,display_order,best_before,is_hot_item_1,hot_item_1_display_order,is_hot_item_2,hot_item_2_display_order,is_hot_item_3,hot_item_3_display_order,fg_color,bg_color,attrib1_name,attrib1_options,attrib2_name,attrib2_options,attrib3_name,attrib3_options,attrib4_name,attrib4_options,attrib5_name,attrib5_options,tag1,tag2,tag3,tag4,tag5,choice_ids,created_by,created_at,updated_by,updated_at,is_deleted,is_system,item_thumb,sys_sale_flag) values ("101000101","101000101",null,"101","","0",null,"0","Chicken 65","","101000102","1","0","","1","","","","0",null,"43","1.00000",null,null,"1","0","1",null,null,null,"150.00000","0.00000","0","0.00000","1","0","0",null,null,"0",null,"0",null,"0",null,"#FFFFFF","#A9A9A9","",null,null,null,null,null,null,null,null,null,null,null,null,null,null,"","0","2021-09-27 07:20:34.0",null,null,"0","0","","1");
DELETE FROM sale_item_ext WHERE id=101000101;
insert into sale_item_ext(id,kitchen_id,is_deleted,last_sync_at) values ("101000101","101000101","0","2021-09-27 07:20:34.0");
DELETE FROM employees WHERE id=101000101;
insert into employees(id,code,employee_category_id,department_id,status,sex,dob,f_name,m_name,l_name,doj,wage_type,address,country,zip_code,phone,fax,email,loc_address,loc_country,loc_zip_code,loc_phone,card_no,loc_fax,cost_per_hour,over_time_pay_rate,work_permit,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","101","1","1","1","M","2021-09-01","Manager","","",null,"1","","","","","","","","","","","1001","","100.00000","0.00000","","0","2021-09-27 07:21:55.0",null,null,"0","0");