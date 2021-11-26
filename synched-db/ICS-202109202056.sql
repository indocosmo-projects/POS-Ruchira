DELETE FROM stock_items WHERE id=101000104;
insert into stock_items(id,code,name,description,supplier_product_code,stock_item_attributes,stock_item_location,stock_item_category_id,prd_department_id,part_no,market_valuation_method,movement_method,uom_id,stock_uom_id,item_weight,qty_on_hand,unit_price,optimum_level,reorder_level,reorder_qty,preferred_supplier_id,last_purchase_unit_price,last_purchase_supplier_id,tax_id,tax_calculation_method,is_service_item,is_valid,is_manufactured,is_sellable,is_semi_finished,is_finished,sales_margin,is_sales_margin_percent,tax_percentage,qty_manufactured,top_consumption_rank,created_by,created_at,updated_by,updated_at,is_system,is_deleted) values ("101000104","102","test item","",null,null,null,null,null,null,"0",null,"7","7","1.00000",null,"50.00000",null,null,null,null,null,null,"101000101","0","0","1","0","1","0","0","0","0","0.00",null,"0","0","2021-09-20 08:55:56.0",null,null,"0","0");
DELETE FROM sale_item_ext WHERE id=101000104;
insert into sale_item_ext(id,kitchen_id,is_deleted,last_sync_at) values ("101000104","101000102","0","2021-09-20 20:55:56.0");
