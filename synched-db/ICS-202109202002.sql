DELETE FROM departments WHERE id=101000101;
insert into departments(id,code,name,description,sales_account_code,purchase_account_code,stock_account_code,cogs_account_code,wages_account_code,gst_collected_account_code,gst_paid_account_code,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","S1","Sales dept","","","","","","","","","0","2021-09-20 08:01:15.0",null,null,"0","0");
update currencies set id="101000101", code="INR", name="rupees", description=null, symbol="₹", fraction_name="", fraction_symbol="ps", decimal_places="2", rounding_id="101000101", is_base_currency="1", exchange_rate="1.00000", exchange_rate_at="2021-09-20", created_by="0", created_at="2021-09-20 07:55:27.0", updated_by="0", updated_at="2021-09-20 08:01:59.0", is_deleted="0", is_system="0" where id=101000101 and is_deleted = 0 and is_synchable = 1;
