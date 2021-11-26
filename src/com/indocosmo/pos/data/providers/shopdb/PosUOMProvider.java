package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanUOM;

public final class PosUOMProvider extends PosShopDBProviderBase {
	
	private static PosUOMProvider mInstance;
	
	private static Map<Integer, BeanUOM> uomIdMap;
	private static Map<String, BeanUOM> uomCodeMap;
	private BeanUOM maxDecUOM;

	public static PosUOMProvider getInstance(){
		
		if(mInstance==null)
			mInstance=new PosUOMProvider();
		
		return mInstance;
		
	}
	
	private PosUOMProvider() {
		super("uoms");
		
		uomIdMap=new HashMap<Integer, BeanUOM>();
		uomCodeMap=new HashMap<String, BeanUOM>();
			
	}
	
	private void pushToMaps(BeanUOM item){
		
		if(maxDecUOM==null || maxDecUOM.getDecimalPlaces()<item.getDecimalPlaces())
			maxDecUOM=item;
		
		if(!uomIdMap.containsKey(item.getID()))
			uomIdMap.put(item.getID(), item);
		
		if(!uomCodeMap.containsKey(item.getCode()))
			uomCodeMap.put(item.getCode(), item);
	}
	
	private ArrayList<BeanUOM> loadItems(String where){
		
		ArrayList<BeanUOM> uomItemList=null;
		final CachedRowSet res=getData(where);
		try {
			uomItemList=new ArrayList<BeanUOM>();
			while (res.next()) {
				BeanUOM item=new BeanUOM();
				item.setID(res.getInt("id"));
				item.setCode(res.getString("code"));
				item.setName(res.getString("name"));
				item.setSymbol(res.getString("uom_symbol"));
				item.setDecimalPlaces(res.getInt("decimal_places"));
				uomItemList.add(item); 
				pushToMaps(item);
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"loadItems",e);
			uomItemList=null;
		}
		return uomItemList;
	}
	
	public BeanUOM getUom(int id){
		
		BeanUOM uom=null;
		
		if(uomIdMap.containsKey(id)){
			
			uom=uomIdMap.get(id);
			
		}else{

			final String where="id=" +String.valueOf(id);
			ArrayList<BeanUOM> uomList=loadItems(where);
			if(uomList!=null && uomList.size()>=1)
				uom=uomList.get(0);
		}
		return uom;
	}
	
	public BeanUOM getUom(String code){
		
		BeanUOM uom=null;
		
		if(uomCodeMap.containsKey(code)){
			
			uom=uomCodeMap.get(code);
		}else{
		
			String where="code='" +code+"'";
			ArrayList<BeanUOM> uomList=loadItems(where);
			if(uomList!=null && uomList.size()>=1)
				uom=uomList.get(0);
		}
		
		return uom;
	}
	
	/**
	 * The uom which has maximum decimal places.
	 * Can be used to format summary information.
	 * @return
	 */
	public BeanUOM getMaxDecUom(){
		
		if(maxDecUOM==null)
			loadItems(null);
		return maxDecUOM;
	}
}
