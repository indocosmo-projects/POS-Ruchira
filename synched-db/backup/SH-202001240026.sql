update shop set id="101", code="SH", name="HOTEL SEAGULL", description="", area_id="101", address="CALVATHI ROAD", city="KOCHI", state="KERALA", state_code="37", country="INDIA", zip_code="68020", company_license_no="", company_tax_no="32AABFH3888A1ZR", cst_no="", email="", email_subscribe="0", phone="04842218128", service_type="0", business_type="0", bank_name="", bank_branch="", bank_address="", bank_ifsc_code="", bank_micr_code="", bank_account_no="", created_by="0", created_at="2020-01-24 09:47:59.0", updated_by="0", updated_at="2020-01-24 10:54:55.0", is_system="0", is_deleted="0" where id=101 and is_deleted = 0 and is_synchable = 1;
DELETE FROM shop_departments WHERE id=101000101;
insert into shop_departments(id,shop_id,department_id,is_system,is_deleted) values ("101000101","101","101000102","0","0");
DELETE FROM shop_departments WHERE id=101000102;
insert into shop_departments(id,shop_id,department_id,is_system,is_deleted) values ("101000102","101","101000101","0","0");
