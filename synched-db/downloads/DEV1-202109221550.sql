update menus set id="101", code="MAIN", name="main", description="", is_default_menu="1", enable_h1_button="1", enable_h2_button="1", enable_h3_button="1", color="#000000", is_active="1", created_by="0", created_at="2021-09-22 01:14:52.0", updated_by="0", updated_at="2021-09-22 03:48:31.0", is_deleted="0", is_system="0" where id=101 and is_deleted = 0 and is_synchable = 1;
update item_classes set id="102", code="B1", hsn_code="", name="BIRIYANI", alternative_name="", department_id="101000101", description="", menu_id=null, super_class_id="101", tax_calculation_method="0", taxation_based_on="0", tax_id=null, tax_exemption="0", tax_group_id=null, display_order=null, print_order=null, account_code="", fg_color="#FFFFFF", bg_color="#ff5454", item_thumb="", created_by="0", created_at="2021-09-22 01:13:08.0", updated_by="0", updated_at="2021-09-22 03:48:52.0", is_deleted="0", is_system="0" where id=102 and is_deleted = 0 and is_synchable = 1;
update item_classes set id="101", code="M1", hsn_code=null, name="main", alternative_name="", department_id="101000101", description="", menu_id=null, super_class_id=null, tax_calculation_method=null, taxation_based_on="0", tax_id=null, tax_exemption="0", tax_group_id=null, display_order=null, print_order=null, account_code="", fg_color="", bg_color="", item_thumb="", created_by="0", created_at="2021-09-22 01:11:27.0", updated_by="0", updated_at="2021-09-22 03:48:52.0", is_deleted="0", is_system="0" where id=101 and is_deleted = 0 and is_synchable = 1;
DELETE FROM customers WHERE id=101000101;
insert into customers(id,code,name,description,category,shop_id,customer_type,is_valid,card_no,address,street,city,state,state_code,country,zip_code,phone,fax,email,is_ar,ar_code,joining_date,accumulated_points,redeemed_points,cst_no,license_no,tin,gst_reg_type,gst_party_type,bank_name,bank_branch,bank_address,bank_ifsc_code,bank_micr_code,bank_account_no,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","ONLINE","online",null,null,null,"101000101","1","","w","","","","","","","","","","1","w","2021-09-22",null,null,"","","",null,null,"","","","","","","0","2021-09-22 03:49:44.0",null,null,"0","0");
