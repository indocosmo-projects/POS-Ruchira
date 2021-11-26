DELETE FROM stock_items WHERE id=101000102;
insert into stock_items(id,code,name,description,supplier_product_code,stock_item_attributes,stock_item_location,stock_item_category_id,prd_department_id,part_no,market_valuation_method,movement_method,uom_id,item_weight,qty_on_hand,unit_price,optimum_level,reorder_level,reorder_qty,preferred_supplier_id,last_purchase_unit_price,last_purchase_supplier_id,tax_id,tax_calculation_method,is_service_item,is_valid,is_manufactured,is_sellable,is_semi_finished,is_finished,sales_margin,is_sales_margin_percent,tax_percentage,qty_manufactured,top_consumption_rank,created_by,created_at,updated_by,updated_at,is_system,is_deleted) values ("101000102","102","cheese cake","",null,null,null,null,null,null,"0",null,"21","1.00000",null,"250.00000",null,null,null,null,null,null,"101000101","0","0","1","1","1","0","0","0","0","0.00",null,"0","0","2021-11-08 11:25:27.0",null,null,"0","0");
DELETE FROM sale_items WHERE id=101000102;
insert into sale_items(id,stock_item_id,stock_item_code,code,hsn_code,is_group_item,group_item_id,is_combo_item,name,description,sub_class_id,is_valid,is_manufactured,alternative_name,is_printable_to_kitchen,name_to_print,alternative_name_to_print,barcode,is_open,qty_on_hand,uom_id,item_weight,profit_category_id,prd_department_id,tax_id,tax_exemption,tax_group_id,tax_id_home_service,tax_id_table_service,tax_id_take_away_service,fixed_price,whls_price,is_whls_price_pc,item_cost,tax_calculation_method,taxation_based_on,is_require_weighing,display_order,best_before,is_hot_item_1,hot_item_1_display_order,is_hot_item_2,hot_item_2_display_order,is_hot_item_3,hot_item_3_display_order,fg_color,bg_color,attrib1_name,attrib1_options,attrib2_name,attrib2_options,attrib3_name,attrib3_options,attrib4_name,attrib4_options,attrib5_name,attrib5_options,tag1,tag2,tag3,tag4,tag5,choice_ids,created_by,created_at,updated_by,updated_at,is_deleted,is_system,item_thumb,sys_sale_flag) values ("101000102","101000102",null,"102","","0",null,"0","cheese cake","","101000102","1","1","","1","","","","0",null,"21","1.00000",null,null,"101000101","0","101000101",null,null,null,"250.00000","0.00000","0","0.00000","0","0","0",null,null,"0",null,"0",null,"0",null,"#FFFFFF","#A9A9A9","",null,null,null,null,null,null,null,null,null,null,null,null,null,null,"","0","2021-11-08 23:25:27.0",null,null,"0","0","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","1");
DELETE FROM sale_item_ext WHERE id=101000102;
insert into sale_item_ext(id,kitchen_id,is_deleted,last_sync_at) values ("101000102","101000101","0","2021-11-08 17:55:14.0");
DELETE FROM customer_types WHERE id=101000102;
insert into customer_types(id,code,name,description,is_ar,default_price_variance_pc,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000102","102","Room","","0","0.00000","0","2021-11-08 11:26:02.0",null,null,"0","0");
DELETE FROM customers WHERE id=101000102;
insert into customers(id,code,name,category,shop_id,customer_type,is_valid,card_no,address,street,city,state,state_code,country,zip_code,phone,fax,email,is_ar,ar_code,joining_date,accumulated_points,redeemed_points,cst_no,license_no,tin,gst_reg_type,gst_party_type,bank_name,bank_branch,bank_address,bank_ifsc_code,bank_micr_code,bank_account_no,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000102","209","209",null,null,"101000102","1","209","address 209","","","","","","","","","","1","209","2021-11-08",null,null,"","","",null,null,"","","","","","","0","2021-11-08 11:26:32.0",null,null,"0","0");
