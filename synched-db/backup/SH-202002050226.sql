DELETE FROM stock_items WHERE id=101000327;
insert into stock_items(id,code,name,description,supplier_product_code,stock_item_attributes,stock_item_location,stock_item_category_id,prd_department_id,part_no,market_valuation_method,movement_method,uom_id,item_weight,qty_on_hand,unit_price,optimum_level,reorder_level,reorder_qty,preferred_supplier_id,last_purchase_unit_price,last_purchase_supplier_id,tax_id,tax_calculation_method,is_service_item,is_valid,is_manufactured,is_sellable,is_semi_finished,is_finished,sales_margin,is_sales_margin_percent,tax_percentage,qty_manufactured,top_consumption_rank,created_by,created_at,updated_by,updated_at,is_system,is_deleted) values ("101000327","LIMEBTL","lime bottle","",null,null,null,"1",null,null,"0",null,"101000108","1.00000",null,"50.00000",null,null,null,null,null,null,"1","0","0","1","0","1","0","0","0","0","0.00",null,"0","0","2020-02-05 12:55:26.0",null,null,"0","0");
DELETE FROM sale_items WHERE id=101000327;
insert into sale_items(id,stock_item_id,stock_item_code,code,hsn_code,is_group_item,group_item_id,is_combo_item,name,description,sub_class_id,is_valid,is_manufactured,alternative_name,is_printable_to_kitchen,name_to_print,alternative_name_to_print,barcode,is_open,qty_on_hand,uom_id,item_weight,profit_category_id,prd_department_id,tax_id,tax_exemption,tax_group_id,tax_id_home_service,tax_id_table_service,tax_id_take_away_service,fixed_price,whls_price,is_whls_price_pc,item_cost,tax_calculation_method,taxation_based_on,is_require_weighing,display_order,best_before,is_hot_item_1,hot_item_1_display_order,is_hot_item_2,hot_item_2_display_order,is_hot_item_3,hot_item_3_display_order,fg_color,bg_color,attrib1_name,attrib1_options,attrib2_name,attrib2_options,attrib3_name,attrib3_options,attrib4_name,attrib4_options,attrib5_name,attrib5_options,tag1,tag2,tag3,tag4,tag5,choice_ids,created_by,created_at,updated_by,updated_at,is_deleted,is_system,item_thumb,sys_sale_flag) values ("101000327","101000327",null,"LIMEBTL","","0",null,"0","lime bottle","","101000104","1","0","","1","","","","0",null,"101000108","750.00000",null,null,"1","0","1",null,null,null,"50.00000","0.00000","0","0.00000","0","0","0",null,null,"0",null,"0",null,"0",null,"#FFFFFF","#ff005f","",null,null,null,null,null,null,null,null,null,null,null,null,null,null,"","0","2020-02-05 12:55:26.0",null,null,"0","0","","1");
