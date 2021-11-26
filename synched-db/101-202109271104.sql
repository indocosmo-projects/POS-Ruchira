DELETE FROM rounding WHERE id=101000102;
insert into rounding(id,code,name,description,round_to,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000102","102","roundto50ps","","0.50000","0","2021-09-27 11:02:31.0",null,null,"0","0");
DELETE FROM currencies WHERE id=101000102;
insert into currencies(id,code,name,description,symbol,fraction_name,fraction_symbol,decimal_places,rounding_id,is_base_currency,exchange_rate,exchange_rate_at,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000102","102","INR",null,"₹","ps","","2","101000102","1","1.00000","2021-09-27","0","2021-09-27 11:02:58.0",null,null,"0","0");
