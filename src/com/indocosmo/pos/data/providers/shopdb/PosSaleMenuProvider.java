package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanSaleMenuItem;

public class PosSaleMenuProvider extends PosShopDBProviderBase {
	
	public PosSaleMenuProvider() {
		super("v_menus");
	}
	
	private ArrayList<BeanSaleMenuItem> loadItems(){
		ArrayList<BeanSaleMenuItem> itemList=null;
		try {
			final CachedRowSet res=getData();
			if(res!=null){
				itemList=new ArrayList<BeanSaleMenuItem>();
				while (res.next()) {
					BeanSaleMenuItem item=createMenuItemFromResult(res);
					itemList.add(item); 
				}
				res.close();
			}
		} catch (SQLException e) {
			PosLog.write(this,"loadItems",e);
		}
		return itemList;
	}
	
	private BeanSaleMenuItem createMenuItemFromResult(final CachedRowSet res){
		BeanSaleMenuItem item=new BeanSaleMenuItem();
		try {
			item.setId(res.getInt("id"));
			item.setCode(res.getString("code"));
			item.setName(res.getString("name"));
			item.setColorCode(res.getString("color"));
			item.setDefaultMenu(res.getBoolean("is_default_menu"));
			item.setDescription(res.getString("description"));
			item.setEnableH1Button(res.getBoolean("enable_h1_button"));
			item.setEnableH2Button(res.getBoolean("enable_h2_button"));
			item.setEnableH3Button(res.getBoolean("enable_h3_button"));
		} catch (SQLException e) {
			PosLog.write(this,"createMenuItemFromResult",e);
		}
		return item;
	}
	
	public ArrayList<BeanSaleMenuItem> getList(){
		return loadItems();
	}
	
	public BeanSaleMenuItem getDefaultMenu(){
		BeanSaleMenuItem defaultMenu = null;
		try {
			final CachedRowSet res=getData("is_default_menu");
			if(res!=null){
				while (res.next()) {
					defaultMenu=createMenuItemFromResult(res);
				}
				res.close();
			}
		} catch (SQLException e) {
			PosLog.write(this,"getDefaultMenu",e);
		}
		
		return defaultMenu;
		
	}
	
	public BeanSaleMenuItem getFirstMenu(){
		BeanSaleMenuItem defaultMenu = null;
		try {
			final CachedRowSet res=getData("not is_deleted and is_active", "id asc LIMIT 1");
			if(res!=null){
				while (res.next()) {
					defaultMenu=createMenuItemFromResult(res);
				}
				res.close();
			}
		} catch (SQLException e) {
			PosLog.write(this,"getDefaultMenu",e);
		}
		
		return defaultMenu;
		
	}
	
		/**
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public BeanSaleMenuItem getMenuByID(int id) {

		BeanSaleMenuItem menu = null;
		try {
			final CachedRowSet res=getData("id="+id);
			if(res!=null){

				while (res.next()) {
					menu=createMenuItemFromResult(res);
				}
				res.close();
			}

		} catch (SQLException e) {
			PosLog.write(this,"getDefaultMenu",e);
		}
		return menu;
	}
}
