DELETE FROM taxes WHERE id=101000101;
insert into taxes(id,code,name,description,is_item_specific,is_tax1_applicable,tax1_percentage,is_tax2_applicable,tax2_percentage,is_tax3_applicable,tax3_percentage,is_sc_applicable,sc_percentage,is_define_gst,gst_percentage,is_tax1_included_in_gst,is_tax2_included_in_gst,is_tax3_included_in_gst,is_sc_included_in_gst,tax1_refund_rate,tax2_refund_rate,tax3_refund_rate,sc_refund_rate,gst_refund_rate,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","TAX5","Tax 5 %",null,"0","1","2.50","1","2.50","0",null,"0",null,"0",null,"0","0","0","0","2.50000","2.50000","0.00000","0.00000","0.00000","0","2021-09-22 02:12:28.0","0","2021-09-22 02:12:41.0","0","0");
update taxes set id="101000101", code="TAX5", name="Tax 5 %", description=null, is_item_specific="0", is_tax1_applicable="1", tax1_percentage="2.50", is_tax2_applicable="1", tax2_percentage="2.50", is_tax3_applicable="0", tax3_percentage=null, is_sc_applicable="0", sc_percentage=null, is_define_gst="0", gst_percentage=null, is_tax1_included_in_gst="0", is_tax2_included_in_gst="0", is_tax3_included_in_gst="0", is_sc_included_in_gst="0", tax1_refund_rate="2.50000", tax2_refund_rate="2.50000", tax3_refund_rate="0.00000", sc_refund_rate="0.00000", gst_refund_rate="0.00000", created_by="0", created_at="2021-09-22 02:12:28.0", updated_by="0", updated_at="2021-09-22 02:12:41.0", is_deleted="0", is_system="0" where id=101000101 and is_deleted = 0 and is_synchable = 1;
