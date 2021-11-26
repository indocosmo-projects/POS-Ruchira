package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanDiscount.EditTypes;
import com.indocosmo.pos.data.beans.BeanDiscount.PermittedLevel;
import com.indocosmo.pos.data.beans.BeanItemDiscount;

public class PosDiscountItemProvider extends PosShopDBProviderBase {
	
//	public final static String ADJUSTMENT_DISCOUNT_CODE="ADJ";
	public final static String NONE_DISCOUNT_CODE="DEFDSCNON";
	public final static String FREE_TOPPING_DISCOUNT="DEFREETOP";
	public final static String CUSTOMER_DISCOUNT="DEFDSCCUST";
	
	private static final String[] DAYS_OF_WEEK={"SUN","MON","TUE","WED","THU","FRI","SAT"};
	protected ArrayList<BeanDiscount> mPosDiscountItemList;
	
//	private static PosDiscountObject adjDiscount=null;
	private static BeanDiscount nonDiscount=null;
	private static BeanDiscount freeTopDiscount=null;
	private static BeanDiscount customerDiscount=null;
	private static BeanDiscount splitPartAdjDiscount=null;

	public PosDiscountItemProvider(String table){
		mTablename=table;	
	}
	
	public PosDiscountItemProvider(){
		mTablename="v_discounts";	
	}	
	
	/** Loads the discounts from table and stores in mPosDiscountItemList list
	 * @param where the sql where condition
	 */
	protected void loadDiscountItems(String where){
		mPosDiscountItemList=new ArrayList<BeanDiscount>();
		final int weekday=Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		final String day=DAYS_OF_WEEK[weekday-1];
		CachedRowSet res=null;
		res=((where==null)?
			getData():getData(where));
		try {
			while (res.next()) {
				final String week_days=res.getString("week_days");
				if(week_days==null || week_days.equals("") || week_days.contains(day))
					mPosDiscountItemList.add(createDiscountItem(res)); 
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"loadDiscountItems",e);
		}
	}
	
	/** Creates the discount item from record set
	 * @param res the record set
	 * @return the discount item 
	 * @throws SQLException
	 */
	protected BeanDiscount createDiscountItem(CachedRowSet res) throws SQLException{
		
		BeanItemDiscount item=new BeanItemDiscount();	
		item.setId(res.getInt("id"));
		item.setCode(res.getString("code"));
		item.setName(res.getString("name"));
		item.setDescription(res.getString("description"));
		item.setPrice(res.getDouble("price"));
		item.setPermittedLevel(res.getInt("permitted_for"));
		item.setPercentage(res.getBoolean("is_percentage"));
		item.setOverridable(res.getBoolean("is_overridable"));
		item.setItemSpecific(res.getBoolean("is_item_specific"));
		item.setRequiredQuantity(res.getDouble("grouping_quantity"));
		item.setEditType(EditTypes.get(res.getInt("allow_editing")));
		item.setDiscountPassword(res.getString("disc_password"));
		item.setVisibleInUI(!res.getBoolean("is_system"));
		return item;
	}
	
	/**
	 * @returns all the discounts
	 */
	public ArrayList<BeanDiscount> getDiscounts(){
		loadDiscountItems(null);
		return mPosDiscountItemList;
	}
	

	
//	/**This function will returns either bill level or item level
//	 * @param is_item_specific  
//	 * @param PermittedLevels 
//	 * @return discount item list
//	 */
//	public ArrayList<PosDiscountItem> getDiscounts(Boolean is_item_specific, PermittedLevel permittedfor){
//		loadDiscountItems("is_item_specific="+String.valueOf(is_item_specific)+
//				" and permitted_for in ( 0,"+String.valueOf(permittedfor.getCode())+")");
//		return mPosDiscountItemList;
//	}
	
	/**This function will returns either bill level or item level
	 * @param PermittedLevels 
	 * @return discount item list
	 */
	public ArrayList<BeanDiscount> getDiscounts( PermittedLevel permittedfor){
		loadDiscountItems("permitted_for in (0,"+String.valueOf(permittedfor.getCode())+")");
		return mPosDiscountItemList;
	}
	
	/**
	 * @returns the general discounts
	 */
	public  ArrayList<BeanDiscount> getGeneralDiscounts(PermittedLevel permittedfor){
		loadDiscountItems("is_item_specific=0 "+
				" and permitted_for in ( 0,"+String.valueOf(permittedfor.getCode())+")");
		return mPosDiscountItemList;
	}

	/**
	 * @param id
	 * @return
	 */
	public BeanDiscount getDiscountItem(int id){
		BeanDiscount discItem=null;
		CachedRowSet res=getData("id="+String.valueOf(id));
		try {
			res.next();
			discItem=createDiscountItem(res);
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"getDiscountItem",e);
		}
		if(discItem==null)
			discItem=getNoneDiscount();
		return discItem;
	}
	
	/**
	 * @param code
	 * @return
	 */
	public BeanDiscount getDiscountItem(String code){
		BeanDiscount discItem=null;
		CachedRowSet res=getData("code='"+code+"'");
		try {
			res.next();
			discItem=createDiscountItem(res);
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"getDiscountItem",e);
		}
		if(discItem==null)
			discItem=getNoneDiscount();
		return discItem;
	}
	
	/**
	 * @returns adjustment record system defined
	 */
//	public PosDiscountObject getAdjustmentDiscount(){
//		if(adjDiscount==null)
//			adjDiscount=getDiscountItem(ADJUSTMENT_DISCOUNT_CODE);
//		return adjDiscount;
//	}
	
	/**
	 * @returns the None discount system defined.
	 */
	public BeanDiscount getNoneDiscount(){
		if(nonDiscount==null)
			nonDiscount=getDiscountItem(NONE_DISCOUNT_CODE);
		return nonDiscount;
	}
	

	/**
	 * @return the freeTopDiscount
	 */
	public BeanDiscount getFreeToppingDiscount() {
		
		if(freeTopDiscount==null)
			freeTopDiscount=getDiscountItem(FREE_TOPPING_DISCOUNT);
		return freeTopDiscount;
	}

	/**
	 * @return the custDiscount
	 */
	public BeanDiscount getCustomerDiscount() {
		
		if(customerDiscount==null)
			customerDiscount=getDiscountItem(CUSTOMER_DISCOUNT);
		return customerDiscount;
	}
}

