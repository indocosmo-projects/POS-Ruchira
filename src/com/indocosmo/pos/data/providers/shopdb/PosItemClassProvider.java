package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanItemClass;
import com.indocosmo.pos.data.beans.BeanItemClassBase;
import com.indocosmo.pos.data.beans.BeanMenuDepartment;

public final class PosItemClassProvider extends PosShopDBProviderBase{
	
	private PosSubItemClassProvider mPosSubItemClassProvider;
	
	private static Map<Integer,ArrayList<BeanItemClassBase>> itemClassMenuDepartmentMap;
	
	private static PosItemClassProvider mInstance;
	
	public static PosItemClassProvider getInstance(){
		
		if(mInstance==null)
			mInstance=new PosItemClassProvider();
		
		return mInstance;
	}
	
	
	private PosItemClassProvider(){
		super();
		mTablename="v_item_classes";
		mPosSubItemClassProvider=PosSubItemClassProvider.getInstance();
		itemClassMenuDepartmentMap=new HashMap<Integer, ArrayList<BeanItemClassBase>>();
	}

//	public ArrayList<BeanItemClassBase> getList(){
//		return  loadItemClasses(null);
//	}
	
	public ArrayList<BeanItemClassBase> getListByDepartment(BeanMenuDepartment deparment){
		
		ArrayList<BeanItemClassBase> list=null;
		//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
		//PosLog.debug("getListByDepartment 1====================="+deparment.getDepartmentId());
		
		if(itemClassMenuDepartmentMap.containsKey(deparment.getDepartmentId())){
			list=itemClassMenuDepartmentMap.get(deparment.getDepartmentId());
		//PosLog.debug("getListByDepartment 2=====================if==========>");
		}else{
			String where = "department_id ="+deparment.getDepartmentId();
		//	PosLog.debug("getListByDepartment 2=====================else==========>");
			list= loadItemClasses(where);
		//	PosLog.debug("getListByDepartment 3=====================after loadItemClasses==========>");
			itemClassMenuDepartmentMap.put(deparment.getDepartmentId(), list);
		//	PosLog.debug("getListByDepartment 4=====================after itemClassMenuDepartmentMap.put==========>");

		}
		
		return list;
	}
	
	private ArrayList<BeanItemClassBase> loadItemClasses(String where){
		//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
		//PosLog.debug("loadItemClasses 1===================== inside");
		ArrayList<BeanItemClassBase> itemList=new ArrayList<BeanItemClassBase>();
		try {
			//PosLog.debug("loadItemClasses 2=====================");
			final String orderBy="ifnull(display_order,100000),"+"display_order"; 
		//	PosLog.debug("loadItemClasses 3=====================");
			
			final String whereSql = (where == null)?"super_class_id is null":where+" and super_class_id is null";
			//PosLog.debug("loadItemClasses 4=====================");
			CachedRowSet res=getData(whereSql,orderBy);
			//PosLog.debug("loadItemClasses 5====================="+res);
			if(res!=null){
				while(res.next()){
					//PosLog.debug("loadItemClasses 6=====================");
					BeanItemClass posClassItem=createClassItemFromResult(res);
					//PosLog.debug("loadItemClasses 6=====================");
					if(posClassItem!=null)
						itemList.add(posClassItem);
					//PosLog.debug("loadItemClasses 7=====================");
				}
				res.close();
				//PosLog.debug("loadItemClasses 7=====================");
			}
		} catch (SQLException e) {
			PosLog.write(this,"loadItemClasses",e);
		}
		return itemList;
	}
	
	private BeanItemClass createClassItemFromResult(final CachedRowSet res ){
		BeanItemClass posClassItem=new BeanItemClass();
		//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
		//PosLog.debug("createClassItemFromResult 1=====================");
		try {
			posClassItem.setCode(res.getString("code"));
			//PosLog.debug("createClassItemFromResult 2=====================");
			posClassItem.setName(res.getString("name"));
		//	PosLog.debug("createClassItemFromResult 3=====================");
			posClassItem.setID(res.getInt("id"));
		//	PosLog.debug("createClassItemFromResult 4=====================");
			posClassItem.setAlternativeName(res.getString("alternative_name")!=null&&res.getString("alternative_name").length()>0?res.getString("alternative_name"):res.getString("name"));
		//	PosLog.debug("createClassItemFromResult 5=====================");
			//			posClassItem.setSubClassList(mPosSubItemClassProvider.getList(posClassItem.getCode()));
			if(mPosSubItemClassProvider.getListById(posClassItem.getID()).size()>0){
				posClassItem.setSubClassList(mPosSubItemClassProvider.getListById(posClassItem.getID()));
				//PosLog.debug("createClassItemFromResult 6=========if===========");
			}else{
				posClassItem = null;
				//PosLog.debug("createClassItemFromResult 6=======else=============");
			}
		} catch (SQLException e) {
			PosLog.write(this,"createClassItemFromResult",e);
		}
		return posClassItem;
	}
	
	
}
