DELETE FROM stock_items WHERE id=101000356;
insert into stock_items(id,code,name,description,supplier_product_code,stock_item_attributes,stock_item_location,stock_item_category_id,prd_department_id,part_no,market_valuation_method,movement_method,uom_id,stock_uom_id,item_weight,qty_on_hand,unit_price,optimum_level,reorder_level,reorder_qty,preferred_supplier_id,last_purchase_unit_price,last_purchase_supplier_id,tax_id,tax_calculation_method,is_service_item,is_valid,is_manufactured,is_sellable,is_semi_finished,is_finished,sales_margin,is_sales_margin_percent,tax_percentage,qty_manufactured,top_consumption_rank,created_by,created_at,updated_by,updated_at,is_system,is_deleted) values ("101000356","256","Egg Bhurji","",null,null,null,null,null,null,"0",null,"1","1","1.00000",null,"60.00000",null,null,null,null,null,null,"1","0","0","1","0","1","0","0","0","0","0.00",null,"0","0","2020-07-15 01:42:14.0",null,null,"0","0");
DELETE FROM sale_items WHERE id=101000356;
insert into sale_items(id,stock_item_id,stock_item_code,code,hsn_code,is_group_item,group_item_id,is_combo_item,name,description,sub_class_id,is_valid,is_manufactured,alternative_name,is_printable_to_kitchen,name_to_print,alternative_name_to_print,barcode,is_open,qty_on_hand,uom_id,item_weight,profit_category_id,prd_department_id,tax_id,tax_exemption,tax_group_id,tax_id_home_service,tax_id_table_service,tax_id_take_away_service,fixed_price,whls_price,is_whls_price_pc,item_cost,tax_calculation_method,taxation_based_on,is_require_weighing,display_order,best_before,is_hot_item_1,hot_item_1_display_order,is_hot_item_2,hot_item_2_display_order,is_hot_item_3,hot_item_3_display_order,fg_color,bg_color,attrib1_name,attrib1_options,attrib2_name,attrib2_options,attrib3_name,attrib3_options,attrib4_name,attrib4_options,attrib5_name,attrib5_options,tag1,tag2,tag3,tag4,tag5,choice_ids,created_by,created_at,updated_by,updated_at,is_deleted,is_system,item_thumb,sys_sale_flag) values ("101000356","101000356",null,"256","","0",null,"0","Egg Bhurji","","101000134","1","0","","1","","","","0",null,"1","1.00000",null,null,"1","0","1",null,null,null,"60.00000","0.00000","0","0.00000","0","0","0",null,null,"0",null,"0",null,"0",null,"#FFFFFF","#e11717","",null,null,null,null,null,null,null,null,null,null,null,null,null,null,"","0","2020-07-15 13:42:14.0",null,null,"0","0","","1");
DELETE FROM sale_item_ext WHERE id=101000356;
insert into sale_item_ext(id,kitchen_id,is_deleted,last_sync_at) values ("101000356","101000101","0","2020-07-15 13:42:14.0");
DELETE FROM stock_items WHERE id=101000357;
insert into stock_items(id,code,name,description,supplier_product_code,stock_item_attributes,stock_item_location,stock_item_category_id,prd_department_id,part_no,market_valuation_method,movement_method,uom_id,stock_uom_id,item_weight,qty_on_hand,unit_price,optimum_level,reorder_level,reorder_qty,preferred_supplier_id,last_purchase_unit_price,last_purchase_supplier_id,tax_id,tax_calculation_method,is_service_item,is_valid,is_manufactured,is_sellable,is_semi_finished,is_finished,sales_margin,is_sales_margin_percent,tax_percentage,qty_manufactured,top_consumption_rank,created_by,created_at,updated_by,updated_at,is_system,is_deleted) values ("101000357","257","Tomato Omlete","",null,null,null,null,null,null,"0",null,"1","1","1.00000",null,"45.00000",null,null,null,null,null,null,"1","0","0","1","0","1","0","0","0","0","0.00",null,"0","0","2020-07-15 01:42:40.0",null,null,"0","0");
DELETE FROM sale_items WHERE id=101000357;
insert into sale_items(id,stock_item_id,stock_item_code,code,hsn_code,is_group_item,group_item_id,is_combo_item,name,description,sub_class_id,is_valid,is_manufactured,alternative_name,is_printable_to_kitchen,name_to_print,alternative_name_to_print,barcode,is_open,qty_on_hand,uom_id,item_weight,profit_category_id,prd_department_id,tax_id,tax_exemption,tax_group_id,tax_id_home_service,tax_id_table_service,tax_id_take_away_service,fixed_price,whls_price,is_whls_price_pc,item_cost,tax_calculation_method,taxation_based_on,is_require_weighing,display_order,best_before,is_hot_item_1,hot_item_1_display_order,is_hot_item_2,hot_item_2_display_order,is_hot_item_3,hot_item_3_display_order,fg_color,bg_color,attrib1_name,attrib1_options,attrib2_name,attrib2_options,attrib3_name,attrib3_options,attrib4_name,attrib4_options,attrib5_name,attrib5_options,tag1,tag2,tag3,tag4,tag5,choice_ids,created_by,created_at,updated_by,updated_at,is_deleted,is_system,item_thumb,sys_sale_flag) values ("101000357","101000357",null,"257","","0",null,"0","Tomato Omlete","","101000134","1","0","","1","","","","0",null,"1","1.00000",null,null,"1","0","1",null,null,null,"45.00000","0.00000","0","0.00000","0","0","0",null,null,"0",null,"0",null,"0",null,"#FFFFFF","#e11717","",null,null,null,null,null,null,null,null,null,null,null,null,null,null,"","0","2020-07-15 13:42:40.0",null,null,"0","0","","1");
DELETE FROM sale_item_ext WHERE id=101000357;
insert into sale_item_ext(id,kitchen_id,is_deleted,last_sync_at) values ("101000357","101000101","0","2020-07-15 13:42:40.0");
DELETE FROM stock_items WHERE id=101000358;
insert into stock_items(id,code,name,description,supplier_product_code,stock_item_attributes,stock_item_location,stock_item_category_id,prd_department_id,part_no,market_valuation_method,movement_method,uom_id,stock_uom_id,item_weight,qty_on_hand,unit_price,optimum_level,reorder_level,reorder_qty,preferred_supplier_id,last_purchase_unit_price,last_purchase_supplier_id,tax_id,tax_calculation_method,is_service_item,is_valid,is_manufactured,is_sellable,is_semi_finished,is_finished,sales_margin,is_sales_margin_percent,tax_percentage,qty_manufactured,top_consumption_rank,created_by,created_at,updated_by,updated_at,is_system,is_deleted) values ("101000358","258","Masala Omlete","",null,null,null,null,null,null,"0",null,"1","1","1.00000",null,"45.00000",null,null,null,null,null,null,"1","0","0","1","0","1","0","0","0","0","0.00",null,"0","0","2020-07-15 01:43:24.0",null,null,"0","0");
DELETE FROM sale_items WHERE id=101000358;
insert into sale_items(id,stock_item_id,stock_item_code,code,hsn_code,is_group_item,group_item_id,is_combo_item,name,description,sub_class_id,is_valid,is_manufactured,alternative_name,is_printable_to_kitchen,name_to_print,alternative_name_to_print,barcode,is_open,qty_on_hand,uom_id,item_weight,profit_category_id,prd_department_id,tax_id,tax_exemption,tax_group_id,tax_id_home_service,tax_id_table_service,tax_id_take_away_service,fixed_price,whls_price,is_whls_price_pc,item_cost,tax_calculation_method,taxation_based_on,is_require_weighing,display_order,best_before,is_hot_item_1,hot_item_1_display_order,is_hot_item_2,hot_item_2_display_order,is_hot_item_3,hot_item_3_display_order,fg_color,bg_color,attrib1_name,attrib1_options,attrib2_name,attrib2_options,attrib3_name,attrib3_options,attrib4_name,attrib4_options,attrib5_name,attrib5_options,tag1,tag2,tag3,tag4,tag5,choice_ids,created_by,created_at,updated_by,updated_at,is_deleted,is_system,item_thumb,sys_sale_flag) values ("101000358","101000358",null,"258","","0",null,"0","Masala Omlete","","101000134","1","0","","1","","","","0",null,"1","1.00000",null,null,"1","0","1",null,null,null,"45.00000","0.00000","0","0.00000","0","0","0",null,null,"0",null,"0",null,"0",null,"#FFFFFF","#e11717","",null,null,null,null,null,null,null,null,null,null,null,null,null,null,"","0","2020-07-15 13:43:24.0",null,null,"0","0","","1");
DELETE FROM sale_item_ext WHERE id=101000358;
insert into sale_item_ext(id,kitchen_id,is_deleted,last_sync_at) values ("101000358","101000101","0","2020-07-15 13:43:24.0");
DELETE FROM stock_items WHERE id=101000359;
insert into stock_items(id,code,name,description,supplier_product_code,stock_item_attributes,stock_item_location,stock_item_category_id,prd_department_id,part_no,market_valuation_method,movement_method,uom_id,stock_uom_id,item_weight,qty_on_hand,unit_price,optimum_level,reorder_level,reorder_qty,preferred_supplier_id,last_purchase_unit_price,last_purchase_supplier_id,tax_id,tax_calculation_method,is_service_item,is_valid,is_manufactured,is_sellable,is_semi_finished,is_finished,sales_margin,is_sales_margin_percent,tax_percentage,qty_manufactured,top_consumption_rank,created_by,created_at,updated_by,updated_at,is_system,is_deleted) values ("101000359","259","Egg Omlete","",null,null,null,null,null,null,"0",null,"1","1","1.00000",null,"35.00000",null,null,null,null,null,null,"1","0","0","1","0","1","0","0","0","0","0.00",null,"0","0","2020-07-15 01:43:51.0",null,null,"0","0");
DELETE FROM sale_items WHERE id=101000359;
insert into sale_items(id,stock_item_id,stock_item_code,code,hsn_code,is_group_item,group_item_id,is_combo_item,name,description,sub_class_id,is_valid,is_manufactured,alternative_name,is_printable_to_kitchen,name_to_print,alternative_name_to_print,barcode,is_open,qty_on_hand,uom_id,item_weight,profit_category_id,prd_department_id,tax_id,tax_exemption,tax_group_id,tax_id_home_service,tax_id_table_service,tax_id_take_away_service,fixed_price,whls_price,is_whls_price_pc,item_cost,tax_calculation_method,taxation_based_on,is_require_weighing,display_order,best_before,is_hot_item_1,hot_item_1_display_order,is_hot_item_2,hot_item_2_display_order,is_hot_item_3,hot_item_3_display_order,fg_color,bg_color,attrib1_name,attrib1_options,attrib2_name,attrib2_options,attrib3_name,attrib3_options,attrib4_name,attrib4_options,attrib5_name,attrib5_options,tag1,tag2,tag3,tag4,tag5,choice_ids,created_by,created_at,updated_by,updated_at,is_deleted,is_system,item_thumb,sys_sale_flag) values ("101000359","101000359",null,"259","","0",null,"0","Egg Omlete","","101000134","1","0","","1","","","","0",null,"1","1.00000",null,null,"1","0","1",null,null,null,"35.00000","0.00000","0","0.00000","0","0","0",null,null,"0",null,"0",null,"0",null,"#FFFFFF","#e11717","",null,null,null,null,null,null,null,null,null,null,null,null,null,null,"","0","2020-07-15 13:43:51.0",null,null,"0","0","","1");
DELETE FROM sale_item_ext WHERE id=101000359;
insert into sale_item_ext(id,kitchen_id,is_deleted,last_sync_at) values ("101000359","101000101","0","2020-07-15 13:43:51.0");
