update shop set id="101", code="DEV1", name="Dev Snacks Factory", description="", area_id="101", address="C-7,C-8, Industrial Estate, Umayanalloor, Kollam, Kerala", city="", state="kerala", state_code="21", country="", zip_code="", company_license_no="", company_tax_no="", cst_no="", email="", email_subscribe="0", phone="9847800119", service_type="0", business_type="0", bank_name="", bank_branch="", bank_address="", bank_ifsc_code="", bank_micr_code="", bank_account_no="", created_by="0", created_at="2021-09-22 01:08:08.0", updated_by="0", updated_at="2021-09-22 03:21:14.0", is_system="0", is_deleted="0" where id=101 and is_deleted = 0 and is_synchable = 1;
DELETE FROM departments WHERE id=101000101;
insert into departments(id,code,name,description,sales_account_code,purchase_account_code,stock_account_code,cogs_account_code,wages_account_code,gst_collected_account_code,gst_paid_account_code,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","D1","sales","","","","","","","","","0","2021-09-22 03:20:54.0",null,null,"0","0");
update shop set id="101", code="DEV1", name="Dev Snacks Factory", description="", area_id="101", address="C-7,C-8, Industrial Estate, Umayanalloor, Kollam, Kerala", city="", state="kerala", state_code="21", country="", zip_code="", company_license_no="", company_tax_no="", cst_no="", email="", email_subscribe="0", phone="9847800119", service_type="0", business_type="0", bank_name="", bank_branch="", bank_address="", bank_ifsc_code="", bank_micr_code="", bank_account_no="", created_by="0", created_at="2021-09-22 01:08:08.0", updated_by="0", updated_at="2021-09-22 03:21:14.0", is_system="0", is_deleted="0" where id=101 and is_deleted = 0 and is_synchable = 1;
update shop set id="101", code="DEV1", name="Dev Snacks Factory", description="", area_id="101", address="C-7,C-8, Industrial Estate, Umayanalloor, Kollam, Kerala", city="", state="kerala", state_code="21", country="", zip_code="", company_license_no="", company_tax_no="", cst_no="", email="", email_subscribe="0", phone="9847800119", service_type="0", business_type="0", bank_name="", bank_branch="", bank_address="", bank_ifsc_code="", bank_micr_code="", bank_account_no="", created_by="0", created_at="2021-09-22 01:08:08.0", updated_by="0", updated_at="2021-09-22 03:21:14.0", is_system="0", is_deleted="0" where id=101 and is_deleted = 0 and is_synchable = 1;
DELETE FROM shop_departments WHERE id=101000101;
insert into shop_departments(id,shop_id,department_id,is_system,is_deleted) values ("101000101","101","101000101","0","0");
update item_classes set id="102", code="B1", hsn_code="", name="BIRIYANI", alternative_name="", department_id="101000101", description="", menu_id=null, super_class_id="101", tax_calculation_method="0", taxation_based_on="0", tax_id=null, tax_exemption="0", tax_group_id=null, display_order=null, print_order=null, account_code="", fg_color="#FFFFFF", bg_color="#ff5454", item_thumb="", created_by="0", created_at="2021-09-22 01:13:08.0", updated_by="0", updated_at="2021-09-22 03:21:47.0", is_deleted="0", is_system="0" where id=102 and is_deleted = 0 and is_synchable = 1;
update item_classes set id="101", code="M1", hsn_code=null, name="main", alternative_name="", department_id="101000101", description="", menu_id=null, super_class_id=null, tax_calculation_method=null, taxation_based_on="0", tax_id=null, tax_exemption="0", tax_group_id=null, display_order=null, print_order=null, account_code="", fg_color="", bg_color="", item_thumb="", created_by="0", created_at="2021-09-22 01:11:27.0", updated_by="0", updated_at="2021-09-22 03:21:47.0", is_deleted="0", is_system="0" where id=101 and is_deleted = 0 and is_synchable = 1;
update menus set id="101", code="MAIN", name="main", description="", is_default_menu="1", enable_h1_button="1", enable_h2_button="1", enable_h3_button="1", color="#000000", is_active="1", created_by="0", created_at="2021-09-22 01:14:52.0", updated_by="0", updated_at="2021-09-22 03:21:57.0", is_deleted="0", is_system="0" where id=101 and is_deleted = 0 and is_synchable = 1;
DELETE FROM menu_departments WHERE id=101000101;
insert into menu_departments(id,menu_id,department_id,is_deleted) values ("101000101","101","101000101","0");