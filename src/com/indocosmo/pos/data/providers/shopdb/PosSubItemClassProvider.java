package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosItemDisplayStyle;
import com.indocosmo.pos.common.utilities.PosImageUtils;
import com.indocosmo.pos.data.beans.BeanItemClassBase;
import com.indocosmo.pos.data.beans.BeanSubClass;

public class PosSubItemClassProvider extends PosShopDBProviderBase{

	private PosSaleItemProvider mPosSalesItemProvider;
	
	private static PosSubItemClassProvider mInstance;
	
	private static Map<String, ArrayList<BeanItemClassBase>>  itemClassBaseCodeMapList;
	private static Map<Integer, ArrayList<BeanItemClassBase>>  itemClassBaseIdMapList;
	private static Map<Integer, BeanSubClass > subclssIdMap;
	
	public static PosSubItemClassProvider getInstance(){
		
		if(mInstance==null)
			mInstance=new PosSubItemClassProvider();
		
		return mInstance;
	}
	
	private PosSubItemClassProvider(){
		super();
		mTablename="v_item_classes";
		mPosSalesItemProvider=new PosSaleItemProvider();
		itemClassBaseCodeMapList=new HashMap<String, ArrayList<BeanItemClassBase>>();
		itemClassBaseIdMapList=new HashMap<Integer, ArrayList<BeanItemClassBase>>();
		subclssIdMap=new HashMap<Integer, BeanSubClass>();
	}
	
	public ArrayList<BeanItemClassBase> getList(String classCode){
		
		ArrayList<BeanItemClassBase> list=null;
		if(itemClassBaseCodeMapList.containsKey(classCode))
			list=itemClassBaseCodeMapList.get(classCode);
		else{
			list=loadSubClassItems(classCode);
			itemClassBaseCodeMapList.put(classCode,list);
		}
			
		return list;
	}
	
	public ArrayList<BeanItemClassBase> getListById(int id){
		
		ArrayList<BeanItemClassBase> list=null;
		if(itemClassBaseIdMapList.containsKey(id)){
		//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
		//	PosLog.debug("BeanItemClassBase if=====================");
			list=itemClassBaseIdMapList.get(id);}
		else{
			//PosLog.debug("BeanItemClassBase b else=====================");
			list=loadSubClassItemsbyId(id);
		//	PosLog.debug("BeanItemClassBase else=====================");
			itemClassBaseIdMapList.put(id,list);
		}
			
		return list;
	}
	
	private ArrayList<BeanItemClassBase> loadSubClassItemsbyId(int id){
		ArrayList<BeanItemClassBase> itemList=new ArrayList<BeanItemClassBase>();
		int menuId =PosEnvSettings.getInstance().getMenu().getId();
		//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
		//PosLog.debug("loadSubClassItemsbyId menuId 1=========="+menuId+"======id====="+id);
		String SQL="SELECT cls.*,icd.`display_order` as item_display_order "+
				"FROM `v_item_classes` as cls left join   item_class_display_order as icd"+
				" on cls.id= icd.sub_class_id  and icd.menu_id='"+menuId+"' where  cls.super_class_id='"+id+"'" ;
		try {
		//	PosLog.debug("loadSubClassItemsbyId menuId 2==========try");
			CachedRowSet res=executeQuery(SQL);
		//	PosLog.debug("loadSubClassItemsbyId 3=========="+res);
			if(res!=null){
				while(res.next()){
					//PosLog.debug("loadSubClassItemsbyId 4==========");
					BeanSubClass posClassItem=createSubClassItemFromResultById(res);
					//PosLog.debug("loadSubClassItemsbyId 5==========");
					itemList.add(posClassItem);
				//	PosLog.debug("loadSubClassItemsbyId 6==========");
				}
				res.close();
			}
		} catch (SQLException e) {
			PosLog.write(this,"getList",e);
		}
		return itemList;
	}
	
	private BeanSubClass createSubClassItemFromResultById(final CachedRowSet res){
		return createSubClassItemFromResultById(res, true);
	}
	
	private BeanSubClass createSubClassItemFromResultById(final CachedRowSet res, boolean loadItems){
		BeanSubClass posSubClassItem=new BeanSubClass();
		try {
			posSubClassItem.setID(res.getInt("id"));
			//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
			//PosLog.debug("posSubClassItem.setID 1=========="+posSubClassItem.getID());
			posSubClassItem.setCode(res.getString("code"));
			//PosLog.debug("posSubClassItem.setCode 2=========="+posSubClassItem.getCode());
			posSubClassItem.setName(res.getString("name"));
			//PosLog.debug("posSubClassItem.setName 2=========="+posSubClassItem.getName());
			posSubClassItem.setAlternativeName(res.getString("alternative_name")!=null&&res.getString("alternative_name").length()>0?res.getString("alternative_name"):res.getString("name"));
			//PosLog.debug("posSubClassItem.setAlternativeName 2=========="+posSubClassItem.getAlternativeName());
			posSubClassItem.setFgColor(res.getString("fg_color")!=null&&res.getString("fg_color").length()>0?res.getString("fg_color"):"#FFFFFF");
			//PosLog.debug("posSubClassItem.setFgColor 2=========="+posSubClassItem.getFgColor());
			posSubClassItem.setBgColor(res.getString("bg_color")!=null&&res.getString("bg_color").length()>0?res.getString("bg_color"):"#A9A9A9");
			//PosLog.debug("posSubClassItem.setBgColor 2=========="+posSubClassItem.getBgColor());
			posSubClassItem.setClassDisplayOrder(res.getInt("item_display_order"));
			//PosLog.debug("posSubClassItem.setClassDisplayOrder 2=========="+posSubClassItem.getClassDisplayOrder());
			posSubClassItem.setPrintingOrder(res.getInt("print_order"));
			//PosLog.debug("posSubClassItem.setPrintingOrder 2=========="+posSubClassItem.getPrintingOrder());
			posSubClassItem.setHSNCode(res.getString("hsn_code"));
			//PosLog.debug("posSubClassItem.setHSNCode 2=========="+posSubClassItem.getHSNCode());
			
			posSubClassItem.setSuperClassID(res.getInt("super_class_id"));
			//PosLog.debug("posSubClassItem.setSuperClassID 2=========="+posSubClassItem.getSuperClassID());
			posSubClassItem.setSuperClassCode(res.getString("super_class_code"));
			//PosLog.debug("posSubClassItem.setSuperClassCode 2=========="+posSubClassItem.getSuperClassCode());
			
			if(loadItems){
				//PosLog.debug("loadItems if ==========");
//				posSubClassItem.setPosItemList(mPosSalesItemProvider.getListByCode(posSubClassItem.getCode()));
				posSubClassItem.setPosItemList(mPosSalesItemProvider.getListByClassId(posSubClassItem.getID()));
				//PosLog.debug("loadItems else ==========");
			}
			final boolean isTextOnlyType = (PosEnvSettings.getInstance().
					getUISetting().getOrderEntryUISettings().getClassItemListPanelSetting().
					getButtonType()==PosItemDisplayStyle.TEXT_ONLY);
			//PosLog.debug("isTextOnlyType  =========="+isTextOnlyType);
			if(res.getString("item_thumb")!=null&&res.getString("item_thumb")!=""&&!isTextOnlyType)
				posSubClassItem.setImage(PosImageUtils.decodeFromBase64(res.getString("item_thumb")));
			//PosLog.debug("loadItems  =========="+posSubClassItem.getImage());
		} catch (Exception e) {
			PosLog.write(this,"createSubClassItemFromResult",e);
		}
		return posSubClassItem;
	}
	
	private ArrayList<BeanItemClassBase> loadSubClassItems(String classCode){
		ArrayList<BeanItemClassBase> itemList=new ArrayList<BeanItemClassBase>();
		try {
			final String orderBy="ifnull(display_order,100000),"+"display_order"; 
			CachedRowSet res=getData("super_class_code ='"+classCode+"'",orderBy);
			if(res!=null){
				while(res.next()){
					BeanSubClass posClassItem=createSubClassItemFromResult(res);
					itemList.add(posClassItem);
				}
				res.close();
			}
		} catch (SQLException e) {
			PosLog.write(this,"getList",e);
		}
		return itemList;
	}
	
	private BeanSubClass createSubClassItemFromResult(final CachedRowSet res){
		return createSubClassItemFromResult(res, true);
	}
	
	private BeanSubClass createSubClassItemFromResult(final CachedRowSet res, boolean loadItems){
		BeanSubClass posSubClassItem=new BeanSubClass();
		try {
			posSubClassItem.setID(res.getInt("id"));
			posSubClassItem.setCode(res.getString("code"));
			posSubClassItem.setName(res.getString("name"));
			posSubClassItem.setAlternativeName(res.getString("alternative_name"));
			posSubClassItem.setFgColor(res.getString("fg_color")!=null&&res.getString("fg_color").length()>0?res.getString("fg_color"):"#FFFFFF");
			posSubClassItem.setBgColor(res.getString("bg_color")!=null&&res.getString("bg_color").length()>0?res.getString("bg_color"):"#A9A9A9");
			posSubClassItem.setPrintingOrder(res.getInt("print_order"));
			posSubClassItem.setHSNCode(res.getString("hsn_code"));
			
			posSubClassItem.setSuperClassID(res.getInt("super_class_id"));
			posSubClassItem.setSuperClassCode(res.getString("super_class_code"));
			
			if(loadItems)
//				posSubClassItem.setPosItemList(mPosSalesItemProvider.getListByCode(posSubClassItem.getCode()));
				posSubClassItem.setPosItemList(mPosSalesItemProvider.getListByClassId(posSubClassItem.getID()));
		} catch (SQLException e) {
			PosLog.write(this,"createSubClassItemFromResult",e);
		}
		return posSubClassItem;
	}
	
	public BeanSubClass getClassItem(int classID){
		
		BeanSubClass posClassItem=null;
		
		if(subclssIdMap.containsKey(classID))
			posClassItem=subclssIdMap.get(classID);
		else{
			try {
				CachedRowSet res=getData("id ="+classID);
				if(res!=null){
					if(res.next()){
						posClassItem=createSubClassItemFromResult(res, false);
					}
					res.close();
				}
				subclssIdMap.put(classID,posClassItem);
			} catch (SQLException e) {
				PosLog.write(this,"getList",e);
			}
		}
		return posClassItem;
	}
}
