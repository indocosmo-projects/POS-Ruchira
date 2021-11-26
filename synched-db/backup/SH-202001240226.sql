DELETE FROM currencies WHERE id=101000101;
insert into currencies(id,code,name,description,symbol,fraction_name,fraction_symbol,decimal_places,rounding_id,is_base_currency,exchange_rate,exchange_rate_at,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","RS","Rupees",null,"RS","","1","2","0","1","1.00000","2020-01-24","0","2020-01-24 12:54:26.0",null,null,"0","0");
DELETE FROM rounding WHERE id=101000101;
insert into rounding(id,code,name,description,round_to,created_by,created_at,updated_by,updated_at,is_deleted,is_system) values ("101000101","R01","Round Off1","","1.00000","0","2020-01-24 12:54:58.0",null,null,"0","0");
