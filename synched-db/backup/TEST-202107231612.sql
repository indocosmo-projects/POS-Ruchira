DELETE FROM currencies WHERE id=101000101;
insert into currencies(id,code,name,description,symbol,fraction_name,fraction_symbol,decimal_places,rounding_id,is_base_currency,exchange_rate,exchange_rate_at,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","INR","rupees",null,"rs","2","","2","0","0","0.00000","2021-07-23","0","2021-07-23 04:11:22.0",null,null,"0","0");
DELETE FROM stations WHERE id=101000101;
insert into stations(id,code,name,description,type,created_by,created_at,updated_by,updated_at,is_deleted,is_system,last_sync_at) values ("101000101","T01","t01","","2","0","2021-07-23 04:11:47.0",null,null,"0","0","2021-07-23 16:11:47.0");
