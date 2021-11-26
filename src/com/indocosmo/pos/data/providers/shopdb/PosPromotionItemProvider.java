package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.data.beans.BeanDiscount.EditTypes;
import com.indocosmo.pos.data.beans.BeanItemPromotion;

public class PosPromotionItemProvider extends PosShopDBProviderBase {

	private static final String[] DAYS_OF_WEEK = { "SUN", "MON", "TUE", "WED",
			"THU", "FRI", "SAT" };
	protected static ArrayList<BeanItemPromotion> mPosItemPromotionList;
	public static final String DEF_PROMO_CODE = "DEFPRONON";
	private static BeanItemPromotion mDefaultPromotion;
	
	private static ArrayList<String> nonItemSpecificPromotions;
	private static Map<String, BeanItemPromotion> itemPromotionCodeMap;
	private static Map<Integer, BeanItemPromotion> itemPromotionIdMap;

	public PosPromotionItemProvider(String table) {
		mTablename = table;
	}

	public PosPromotionItemProvider() {
		mTablename = "v_promotions";
	}

	/**
	 * Loads the promotions from table and stores in mPosPromotionItemList list
	 * 
	 * @param where
	 *            the sql where condition
	 * @throws Exception 
	 */
	protected ArrayList<BeanItemPromotion> loadPromotionItems(String where) throws Exception {
		
		ArrayList<BeanItemPromotion> posItemPromotionList = new ArrayList<BeanItemPromotion>();
		final int weekday = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		final String day = DAYS_OF_WEEK[weekday - 1];
		final String orderBy = "date_from desc, date_to asc, time_from  desc,time_to asc";
		CachedRowSet res = null;
		res = ((where == null) ? getData(null, orderBy) : getData(where,
				orderBy));
		try {
			while (res.next()) {
				
				if(res.getString("week_days")!=null){
					final String week_days = res.getString("week_days").toUpperCase();
					if (week_days == null || week_days.trim().equals("") || week_days.contains(day.toUpperCase()))
						posItemPromotionList.add(createPromotionItem(res));
				}
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this, "loadPromotionItems", e);
			throw new Exception("Failed to load promotions");
		}
		return posItemPromotionList;
	}

	/**
	 * Creates the promotion item from record set
	 * 
	 * @param res
	 *            the record set
	 * @return the promotion item
	 * @throws SQLException
	 */
	protected BeanItemPromotion createPromotionItem(CachedRowSet res)
			throws SQLException {

		BeanItemPromotion item = new BeanItemPromotion();
		item.setId(res.getInt("id"));
		item.setCode(res.getString("code"));
		item.setName(res.getString("name"));
		item.setDescription(res.getString("description"));
		item.setPrice(res.getDouble("price"));
		item.setPercentage(res.getBoolean("is_percentage"));
		item.setOverridable(res.getBoolean("is_overridable"));
		item.setItemSpecific(res.getBoolean("is_item_specific"));
		item.setRequiredQuantity(res.getDouble("grouping_quantity"));
		item.setEditType(EditTypes.get(res.getInt("allow_editing")));
		item.setTimeFrom(res.getString("time_from"));
		item.setTimeTo(res.getString("time_to"));
		
		return item;
	}

	/**
	 * @throws Exception 
	 * @returns all the promotions applicable for today
	 */
	public ArrayList<BeanItemPromotion> getPromotions() throws Exception {
		if (mPosItemPromotionList == null)
			mPosItemPromotionList = loadPromotionItems(null);
		return mPosItemPromotionList;
	}

	/**
	 * @param id
	 * @return
	 */
	public BeanItemPromotion getPromotionItem(int id) {
		
		if(itemPromotionIdMap==null)
			itemPromotionIdMap=new HashMap<Integer, BeanItemPromotion>();
		
		BeanItemPromotion discItem = null;

		if(itemPromotionIdMap.containsKey(id))
			discItem=itemPromotionIdMap.get(id).clone();
		else{
			
			CachedRowSet res = getData("id=" + String.valueOf(id));
			try {
				res.next();
				discItem = createPromotionItem(res);
				res.close();
				itemPromotionIdMap.put(id,discItem);
				
			} catch (SQLException e) {
				PosLog.write(this, "getPromotionItem", e);
			}
		}
		return discItem;
	}

	/**
	 * @param code
	 * @return
	 */
	public BeanItemPromotion getPromotionItem(String code) {
		
		if(itemPromotionCodeMap==null)
			itemPromotionCodeMap=new HashMap<String, BeanItemPromotion>();
		
		BeanItemPromotion discItem = null;
		
		if(itemPromotionCodeMap.containsKey(code))
			discItem=itemPromotionCodeMap.get(code).clone();
		else{

			CachedRowSet res = getData("code='" + code + "'");
			try {
				res.next();
				discItem = createPromotionItem(res);
				res.close();
				itemPromotionCodeMap.put(code, discItem);
			} catch (SQLException e) {
				PosLog.write(this, "getPromotionItem", e);
			}
			
		}
		return discItem;
	}

	/***
	 * 
	 * @return the current active promotion based on time;
	 * @throws Exception 
	 */
	public BeanItemPromotion getCurrentPromotion() throws Exception {
		BeanItemPromotion discItem = null;
		if (getPromotions() != null) {
			for (BeanItemPromotion item : getPromotions()) {
				final long timeFrom = PosDateUtil.getTimeInMills(
						PosDateUtil.getDate(), item.getTimeFrom());
				final long timeTo = PosDateUtil.getTimeInMills(
						PosDateUtil.getDate(), item.getTimeTo());
				final long now = PosDateUtil.getTimeInMills(PosDateUtil
						.getDateTime(true));
				if (timeFrom > 0) {
					if (timeTo > 0) {
						if (now >= timeFrom && now <= timeTo) {
							discItem = item;
							break;
						}
					} else {
						discItem = item;
						break;
					}
				} else {
					discItem = item;
					break;
				}
			}
		}
		if (discItem==null) // load default if no promotions exist
			discItem=getDefaultPromotion();
		return discItem;
	}

	public ArrayList<String> getNonItemSpecificPromotionCodes() {
		
		if(nonItemSpecificPromotions==null){
			nonItemSpecificPromotions = new ArrayList<String>();
			CachedRowSet res = getData("is_item_specific=0");
			try {
				while (res.next()) {
					nonItemSpecificPromotions.add(String.valueOf(res.getString("code")));
				}
				res.close();
			} catch (SQLException e) {
				PosLog.write(this, "getSaleItemDiscountCodes", e);
			}
		}
		return nonItemSpecificPromotions;
	}

	/**
	 * @return the mDefaultPromotion
	 */
	public BeanItemPromotion getDefaultPromotion() {
		if (mDefaultPromotion == null)
			mDefaultPromotion = getPromotionItem(DEF_PROMO_CODE);
		return mDefaultPromotion;
	}

}
