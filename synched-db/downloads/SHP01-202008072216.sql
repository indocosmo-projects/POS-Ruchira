update sale_item_display_order set id="101000474", menu_id="101000101", sub_class_id="125", sale_item_id="231", display_order="1", is_deleted="0" where id=101000474 and is_deleted = 0 and is_synchable = 1;
update sale_item_display_order set id="101000441", menu_id="101000101", sub_class_id="125", sale_item_id="242", display_order="2", is_deleted="0" where id=101000441 and is_deleted = 0 and is_synchable = 1;
DELETE FROM sale_item_display_order WHERE id=101000556;
insert into sale_item_display_order(id,menu_id,sub_class_id,sale_item_id,display_order,is_deleted) values ("101000556","101000101","125","214","3","0");
DELETE FROM sale_item_display_order WHERE id=101000557;
insert into sale_item_display_order(id,menu_id,sub_class_id,sale_item_id,display_order,is_deleted) values ("101000557","101000101","125","177","7","0");
DELETE FROM sale_item_display_order WHERE id=101000558;
insert into sale_item_display_order(id,menu_id,sub_class_id,sale_item_id,display_order,is_deleted) values ("101000558","101000101","125","228","14","0");
