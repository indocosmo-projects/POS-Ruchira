update currencies set id="101000101", code="101", name="Indian Rupee", description=null, symbol="₹", fraction_name="ps", fraction_symbol="", decimal_places="2", rounding_id="0", is_base_currency="1", exchange_rate="1.00000", exchange_rate_at="2021-09-27", created_by="0", created_at="2021-09-27 07:16:58.0", updated_by="0", updated_at="2021-09-27 11:01:50.0", is_deleted="1", is_system="0" where id=101000101 and is_deleted = 0 and is_synchable = 1;
update currencies set is_deleted=1 where id =101000101;
update rounding set is_deleted=1 where id =101000101;
