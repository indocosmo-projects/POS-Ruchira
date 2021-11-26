/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanShopShift;


/**
 * @author jojesh
 *
 */
public final class PosShopShiftProvider extends PosShopDBProviderBase {

	/**
	 * 
	 */
	private static BeanShopShift shopAllShift;
	private static Map<Integer, BeanShopShift> mShopShifts;
	public static final String ALL_SHIFT_CODE="ALL SHIFTS";
	public static final int ALL_SHIFT_ID=0;
	
	/**
	 * 
	 */
	public PosShopShiftProvider() {
		
		super("shop_shifts");
		loadShopShits();
	}
	
	/**
	 * 
	 */
	private void loadShopShits(){
		
		if(mShopShifts!=null) return;
		
		String where = "is_deleted =  0 and is_active = 1";
		CachedRowSet crs=getData(where);
		
		if(crs!=null)
			mShopShifts=buildShifts(crs);
	}
	
	/**
	 * @param crs
	 * @return
	 */
	private Map<Integer, BeanShopShift> buildShifts(CachedRowSet crs){
		
		Map<Integer, BeanShopShift> shopShifts=new HashMap<Integer, BeanShopShift> ();
		try {
			while(crs.next()){
				BeanShopShift shift=createShopShiftFromRecordSet(crs);
				shopShifts.put(shift.getId(), shift);
			}
		} catch (SQLException e) {
			PosLog.write(this, "buildShifts", e);
			shopShifts=null;
		}finally{
			try {
				crs.close();
			} catch (SQLException e1) {
				PosLog.write(this, "buildShifts", e1);
			}
		}
		
		return shopShifts;
	}
	
	/**
	 * @param crs
	 * @return
	 * @throws SQLException
	 */
	private BeanShopShift createShopShiftFromRecordSet(CachedRowSet crs) throws SQLException{
		
		BeanShopShift shopShift=new BeanShopShift();
		shopShift.setId(crs.getInt("id"));
		shopShift.setCode(crs.getString("code"));
		shopShift.setName(crs.getString("name"));
		
		return shopShift;
	}
	
	public Map<Integer, BeanShopShift> getShopShifts(){
		
		return getShopShifts(true);
	}
	
	/**
	 * @param filterAllShift
	 * @return
	 */
	public Map<Integer, BeanShopShift> getShopShifts(boolean filterAllShift){
		
		if(filterAllShift)
			mShopShifts.remove(ALL_SHIFT_ID);
		else if(!mShopShifts.containsKey(ALL_SHIFT_ID))
			appendAllShiftForSelection();
		
		return mShopShifts;
	}
	
	/**
	 * @return
	 */
	public static BeanShopShift getItemForAllShift(){
		
		if(shopAllShift==null){
			
			shopAllShift=new BeanShopShift();
			shopAllShift.setId(ALL_SHIFT_ID);
			shopAllShift.setCode(ALL_SHIFT_CODE);
			shopAllShift.setName("ALL SHIFT");
		}
		
		return shopAllShift;
	}
	
	/**
	 * 
	 */
	private void appendAllShiftForSelection(){
	
		if(mShopShifts!=null){

			BeanShopShift shopShift= getItemForAllShift();
			mShopShifts.put(shopShift.getId(), shopShift);
		}
		 
	}
	
	/**
	 * @param id
	 * @return
	 */
	public BeanShopShift getShift(int id){
		
		if(mShopShifts!=null)
			return mShopShifts.get(id);
		else
			return null;
		
	}

	
}
