/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanRounding;


/**
 * @author jojesh
 *
 */
public final class PosRoundingProvider extends PosShopDBProviderBase {

	private static Map<Integer, BeanRounding> mList =null;

	/**
	 * Default constructor for rounding.
	 */
	public PosRoundingProvider(){
		super("rounding");
		mList=loadList();
	}

	/**
	 * @return
	 */
	private Map<Integer, BeanRounding> loadList(){
		Map<Integer, BeanRounding> itemList=new HashMap<Integer, BeanRounding>();
		CachedRowSet res=null;
		res=getData();
		try {
			while (res.next()) {
				final int id=res.getInt("id");
				itemList.put(id,createItem(res)); 
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"loadList",e);
		}
		return itemList;
	}

	/**
	 * @param res
	 * @return
	 * @throws SQLException
	 */
	private BeanRounding createItem(CachedRowSet res) throws SQLException{
		BeanRounding item=new BeanRounding();
		item.setId(res.getInt("id"));
		item.setCode(res.getString("code"));
		item.setName(res.getString("name"));
		item.setRoundTo(res.getFloat("round_to"));
		item.setDescription(res.getString("description"));
		return item;
	}

	/**
	 * @param id
	 * @return
	 */
	public BeanRounding getRounding(int id){
		BeanRounding rounding=null;
		if(mList!=null)
			rounding=mList.get(id);
		return rounding;
	}
}
