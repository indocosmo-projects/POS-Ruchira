update shop set id="101", code="SH01", name="New Malabar  Restaurant", description="", area_id="101", address="Mass Building 35/3103
AIMS main Gate
682041", city="edapally", state="kerala", state_code="kerala", country="", zip_code="682041", company_license_no="", company_tax_no="", cst_no="", email="", email_subscribe="0", phone="9847031546", service_type="0", business_type="0", bank_name="", bank_branch="", bank_address="", bank_ifsc_code="", bank_micr_code="", bank_account_no="", created_by="0", created_at="2020-07-13 03:49:43.0", updated_by="0", updated_at="2020-07-13 05:20:02.0", is_system="0", is_deleted="0" where id=101 and is_deleted = 0 and is_synchable = 1;
DELETE FROM shop_departments WHERE id=101000101;
insert into shop_departments(id,shop_id,department_id,is_system,is_deleted) values ("101000101","101","101000101","0","0");
