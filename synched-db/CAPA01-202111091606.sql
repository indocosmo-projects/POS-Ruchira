update system_params set id="1", date_format="0", date_separator="/", time_format="0", time_zone="1", decimal_places="2", default_customer_type="", default_taxation_method=null, is_direct_stock_entry="0", financial_month=null, week_start="0", smtp_server="", smtp_port="", smtp_mailid="", smtp_password="", sms_web_service="", sms_userid="", sms_password="", created_by="0", created_at="2021-11-09 04:04:53.0", updated_by="0", updated_at="2021-11-09 04:04:53.0", is_deleted="0" where id=1 and is_deleted = 0 and is_synchable = 1;
update bill_params set id="1", show_discount_summary="0", show_tax_summary="0", show_item_discount="0", show_item_tax="0", bill_tax_id=null, bill_hdr1="Cafe Papaya", bill_hdr2="Ground floor Pulikkal Bay", bill_hdr3="Near st Joseph church ", bill_hdr4=" Kadavanthara ", bill_hdr5="682020", bill_hdr6="PHONE  : 04844032583", bill_hdr7="GST : 32AAFCC0383A2ZD", bill_hdr8="", bill_hdr9="", bill_hdr10="", bill_footer1="...THANK YOU...", bill_footer2="", bill_footer3="", bill_footer4="", bill_footer5="", bill_footer6="", bill_footer7="", bill_footer8="", bill_footer9="", bill_footer10="", rounding_id=null, created_by="0", created_at="2021-11-09 04:04:53.0", updated_by="0", updated_at="2021-11-09 04:04:53.0", is_deleted="0" where id=1 and is_deleted = 0 and is_synchable = 1;
update payment_modes set id="1", can_pay_by_cash="1", title_cash="Cash", can_pay_by_company="1", title_company="Credit ", can_pay_by_vouchers="1", title_voucher="Voucher", max_voucher_type="0", can_pay_by_card="1", title_card="Card", can_pay_online="1", title_online="Online", can_cash_refundable="1", can_card_refundable="1", can_company_refundable="1", can_voucher_refundable="1", can_online_refund="1", cash_account_no="", cash_refund_account_no="", alternative_refund_method="1", can_cash_round="1", can_company_round="0", can_voucher_round="1", can_card_round="0", can_online_round="0", created_by="0", created_at="2021-11-09 04:04:53.0", updated_by="0", updated_at="2021-11-09 04:04:53.0", is_deleted="0" where id=1 and is_deleted = 0 and is_synchable = 1;
update tax_param set id="1", tax1_name="CGST", tax2_name="SGST", tax3_name="CESS", gst_name=null, sc_name=null, default_taxation_method="0", default_purchase_taxation_method="0", claculate_tax_before_discount="0", is_deleted="0" where id=1 and is_deleted = 0 and is_synchable = 1;
DELETE FROM taxes WHERE id=101000101;
insert into taxes(id,code,name,is_item_specific,is_tax1_applicable,tax1_percentage,is_tax2_applicable,tax2_percentage,is_tax3_applicable,tax3_percentage,is_sc_applicable,sc_percentage,is_define_gst,gst_percentage,is_tax1_included_in_gst,is_tax2_included_in_gst,is_tax3_included_in_gst,is_sc_included_in_gst,tax1_refund_rate,tax2_refund_rate,tax3_refund_rate,sc_refund_rate,gst_refund_rate,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","T05","T05","0","1","2.50","1","2.50","0",null,"0",null,"0",null,"0","0","0","0","2.50000","2.50000","0.00000","0.00000","0.00000","0","2021-11-09 04:05:58.0",null,null,"0","0");
