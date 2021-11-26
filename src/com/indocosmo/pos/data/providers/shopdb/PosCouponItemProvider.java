package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanCoupon;

public final class PosCouponItemProvider extends PosShopDBProviderBase {

	private ArrayList<BeanCoupon> mPosCouponBrowseItemList;
	/**
	 * Enum for voucher types.
	 * @author deepak
	 *
	 */
	public enum PosVoucherTypes{
	
		NONE("NONE",0),
		LUNCH("LUNCH",1),
		MALL("MALL",2),
		PREPAID("PREPAID",3);
		
		private static final Map<Integer,PosVoucherTypes> mLookup 
		= new HashMap<Integer,PosVoucherTypes>();

		static {
			for(PosVoucherTypes posvouchertypes : EnumSet.allOf(PosVoucherTypes.class))
				mLookup.put(posvouchertypes.getValue(), posvouchertypes);
		}

		private int mValue;
		private String mName;
		private PosVoucherTypes(String name , int value ){
			this.mName = name;
			this.mValue = value;
		}
		public String getName(){
			return mName;
			
		}
		public int getValue() { return mValue; }
		
		public static PosVoucherTypes get(int value) { 
			return mLookup.get(value); 
		}
	}
	
	public PosCouponItemProvider(){
		mTablename="v_voucher_types";		
		loadCouponItems();
	}
	
	private void loadCouponItems(){
		ArrayList<BeanCoupon> couponItemList=null;
		CachedRowSet res=getData();
		couponItemList=new ArrayList<BeanCoupon>();
		try {
			while (res.next()) {
				BeanCoupon coupon = createCoupnFromCrs(res);
				couponItemList.add(coupon); 
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"loadCouponItems",e.getMessage());
		}
		mPosCouponBrowseItemList= couponItemList;
	}
	
	private BeanCoupon createCoupnFromCrs(CachedRowSet crs) throws SQLException{
		
		BeanCoupon coupon= new BeanCoupon();
		coupon.setId(crs.getInt("id"));
		coupon.setName(crs.getString("name"));
		coupon.setCode(crs.getString("code"));		
		coupon.setValue(crs.getDouble("value"));
		coupon.setOverridable(crs.getBoolean("is_overridable"));
//		CouponItemList.add(new PosCoupon(code,name)); 
		coupon.setChangePayable(crs.getBoolean("is_change_payable"));
		
		return coupon;
		
	}
	
	public ArrayList<BeanCoupon> getCouponBrowseItemList(){
		return mPosCouponBrowseItemList;
	}
	
	public BeanCoupon getCouponById(int id){
		BeanCoupon coupon = null;
		String where = "id ="+String.valueOf(id);
		CachedRowSet res=getData(where);
		try{
			while(res.next()){
				coupon = createCoupnFromCrs(res);
			}
		}catch (Exception e) {
			PosLog.write(this,"getCouponById",e.getMessage());
		}
		return coupon;
		
	}
}

