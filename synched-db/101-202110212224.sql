update customer_types set id="101000103", code="ROOM", name="ROOM", description="", is_ar="0", default_price_variance_pc="0.00000", created_by="0", created_at="2021-10-21 09:00:44.0", updated_by="0", updated_at="2021-10-21 10:23:09.0", is_deleted="0", is_system="0" where id=101000103 and is_deleted = 0 and is_synchable = 1;
DELETE FROM customers WHERE id=101000117;
insert into customers(id,code,name,category,shop_id,customer_type,is_valid,card_no,address,street,city,state,state_code,country,zip_code,phone,fax,email,is_ar,ar_code,joining_date,accumulated_points,redeemed_points,cst_no,license_no,tin,gst_reg_type,gst_party_type,bank_name,bank_branch,bank_address,bank_ifsc_code,bank_micr_code,bank_account_no,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000117","301","301",null,null,"101000103","1","","jjj","","","","","","","","","","1","a","2021-10-21",null,null,"","","",null,null,"","","","","","","0","2021-10-21 10:24:03.0",null,null,"0","0");