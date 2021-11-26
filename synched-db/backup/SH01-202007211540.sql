update stock_items set id="101000123", code="23", name="Kadai Veg", description="", supplier_product_code=null, stock_item_attributes=null, stock_item_location=null, stock_item_category_id=null, prd_department_id=null, part_no=null, market_valuation_method="0", movement_method=null, uom_id="1", stock_uom_id="1", item_weight="1.00000", qty_on_hand=null, unit_price="80.00000", optimum_level=null, reorder_level=null, reorder_qty=null, preferred_supplier_id=null, last_purchase_unit_price=null, last_purchase_supplier_id=null, tax_id="1", tax_calculation_method="0", is_service_item="0", is_valid="1", is_manufactured="0", is_sellable="1", is_semi_finished="0", is_finished="0", sales_margin="0", is_sales_margin_percent="0", tax_percentage="0.00", qty_manufactured=null, top_consumption_rank="0", created_by="0", created_at="2020-07-14 12:56:22.0", updated_by="0", updated_at="2020-07-21 03:38:54.0", is_system="0", is_deleted="0" where id=101000123 and is_deleted = 0 and is_synchable = 1;
update sale_items set id="101000123", stock_item_id="101000123", stock_item_code=null, code="23", hsn_code="", is_group_item="0", group_item_id=null, is_combo_item="0", name="Kadai Veg", description="", sub_class_id="101000107", is_valid="1", is_manufactured="0", alternative_name="", is_printable_to_kitchen="1", name_to_print="", alternative_name_to_print="", barcode="", is_open="0", qty_on_hand=null, uom_id="1", item_weight="1.00000", profit_category_id=null, prd_department_id=null, tax_id="1", tax_exemption="0", tax_group_id="1", tax_id_home_service=null, tax_id_table_service=null, tax_id_take_away_service=null, fixed_price="80.00000", whls_price="0.00000", is_whls_price_pc="0", item_cost="0.00000", tax_calculation_method="0", taxation_based_on="0", is_require_weighing="0", display_order=null, best_before=null, is_hot_item_1="0", hot_item_1_display_order=null, is_hot_item_2="0", hot_item_2_display_order=null, is_hot_item_3="0", hot_item_3_display_order=null, fg_color="#FFFFFF", bg_color="#ed1414", attrib1_name="", attrib1_options=null, attrib2_name=null, attrib2_options=null, attrib3_name=null, attrib3_options=null, attrib4_name=null, attrib4_options=null, attrib5_name=null, attrib5_options=null, tag1=null, tag2=null, tag3=null, tag4=null, tag5=null, choice_ids="", created_by="0", created_at="2020-07-14 12:56:22.0", updated_by="0", updated_at="2020-07-21 15:38:54.0", is_deleted="0", is_system="0", item_thumb="", sys_sale_flag="1" where id=101000123 and is_deleted = 0 and is_synchable = 1;
update sale_item_ext set id="101000123", kitchen_id="101000101", is_deleted="0", last_sync_at="2020-07-14 12:56:22.0" where id=101000123 and is_deleted = 0 and is_synchable = 1;
DELETE FROM stock_items WHERE id=101000495;
insert into stock_items(id,code,name,description,supplier_product_code,stock_item_attributes,stock_item_location,stock_item_category_id,prd_department_id,part_no,market_valuation_method,movement_method,uom_id,stock_uom_id,item_weight,qty_on_hand,unit_price,optimum_level,reorder_level,reorder_qty,preferred_supplier_id,last_purchase_unit_price,last_purchase_supplier_id,tax_id,tax_calculation_method,is_service_item,is_valid,is_manufactured,is_sellable,is_semi_finished,is_finished,sales_margin,is_sales_margin_percent,tax_percentage,qty_manufactured,top_consumption_rank,created_by,created_at,updated_by,updated_at,is_system,is_deleted) values ("101000495","A12","green peas curry","",null,null,null,null,null,null,"0",null,"1","1","1.00000",null,"30.00000",null,null,null,null,null,null,"1","0","0","1","0","1","0","0","0","0","0.00",null,"0","0","2020-07-21 03:40:02.0",null,null,"0","0");
DELETE FROM sale_items WHERE id=101000495;
insert into sale_items(id,stock_item_id,stock_item_code,code,hsn_code,is_group_item,group_item_id,is_combo_item,name,description,sub_class_id,is_valid,is_manufactured,alternative_name,is_printable_to_kitchen,name_to_print,alternative_name_to_print,barcode,is_open,qty_on_hand,uom_id,item_weight,profit_category_id,prd_department_id,tax_id,tax_exemption,tax_group_id,tax_id_home_service,tax_id_table_service,tax_id_take_away_service,fixed_price,whls_price,is_whls_price_pc,item_cost,tax_calculation_method,taxation_based_on,is_require_weighing,display_order,best_before,is_hot_item_1,hot_item_1_display_order,is_hot_item_2,hot_item_2_display_order,is_hot_item_3,hot_item_3_display_order,fg_color,bg_color,attrib1_name,attrib1_options,attrib2_name,attrib2_options,attrib3_name,attrib3_options,attrib4_name,attrib4_options,attrib5_name,attrib5_options,tag1,tag2,tag3,tag4,tag5,choice_ids,created_by,created_at,updated_by,updated_at,is_deleted,is_system,item_thumb,sys_sale_flag) values ("101000495","101000495",null,"A12","","0",null,"0","green peas curry","","101000148","1","0","","1","","","","0",null,"1","1.00000",null,null,"1","0","1",null,null,null,"30.00000","0.00000","0","0.00000","0","0","0",null,null,"0",null,"0",null,"0",null,"#FFFFFF","#d81673","",null,null,null,null,null,null,null,null,null,null,null,null,null,null,"","0","2020-07-21 15:40:02.0",null,null,"0","0","","1");
