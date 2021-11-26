/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.terminal.device.BeanKitchen;

/**
 * @author anand
 *
 */
public class PosKitchenProvider extends PosShopDBProviderBase {

	private static PosKitchenProvider mInstance;

	/**
	 * @return
	 */
	public static PosKitchenProvider getInstance(){

		if(mInstance==null)
			mInstance=new PosKitchenProvider();

		return mInstance;
	}


	private Map<Integer, BeanKitchen> idMap;
	private ArrayList<BeanKitchen> kitchenList;

	/**
	 * 
	 */
	private PosKitchenProvider() {

		super("v_kitchens");

	}

	/**
	 * @return
	 */
	public ArrayList<BeanKitchen> getKitchens(){

		if(kitchenList==null){

			loadItems();
		}

		return kitchenList;
	}

	/**
	 * @return
	 */
	private ArrayList<BeanKitchen> loadItems(){

		final CachedRowSet res=getData();
		try {

			if(res!=null){

				kitchenList=new ArrayList<BeanKitchen>();
				idMap=new HashMap<Integer, BeanKitchen>();

				while (res.next()) {

					BeanKitchen item=new BeanKitchen();
					item.setId(res.getInt("id"));
					item.setCode(res.getString("code"));
					item.setName(res.getString("name"));
					item.setDescription(res.getString("description"));
					kitchenList.add(item); 
					idMap.put(item.getId(), item);
				}
				res.close();
			}
		} catch (SQLException e) {
			PosLog.write(this,"loadItems",e);
			kitchenList=null;
		}
		return kitchenList;
	}

	/**
	 * @param id
	 * @return
	 */
	public BeanKitchen getKitchenById(int id){

		if(idMap==null)
			loadItems();

		BeanKitchen kitchen=null;

		if(idMap!=null && idMap.containsKey(id)){

			kitchen=idMap.get(id);
		}

		return kitchen;
	}

	/**
	 * @param id
	 * @return
	 */
	public String getKitchenName(int id){

		String name="<unknown>";

		BeanKitchen kitchen=getKitchenById(id);
		if(kitchen!=null)
			name=kitchen.getName();

		return name;
	}
}
