/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanBankCard;

/**
 * @author deepak
 *
 */
public class PosBankCardTypesProvider extends PosShopDBProviderBase{
	/**
	 * 
	 */
	private static Map<Integer, BeanBankCard> mBankCards;
	public PosBankCardTypesProvider() {
		super("bank_card_types");
		loadItems();
	}
	/**
	 * Load the data from table to the cached-row-set.
	 */
	private void loadItems() {
		if(mBankCards!=null) return;
		CachedRowSet crs = getData();
		if(crs!=null){
			mBankCards = buildCards(crs);
	}

}
	/**
	 * @param crs
	 * @return
	 * Get each card from the result set.
	 */
	private Map<Integer, BeanBankCard> buildCards(CachedRowSet crs) {
		Map<Integer, BeanBankCard> bankCards = new HashMap<Integer, BeanBankCard>();
		try {
			while(crs.next()){
				BeanBankCard card = getCardFromCrs(crs);
				bankCards.put(card.getId(), card);
			}
		} catch (SQLException e) {
			PosLog.write(this, "buildCards", e);
			bankCards = null;
		} finally{
			try {
				crs.close();
			} catch (SQLException e) {
				PosLog.write(this, "buildCards", e);
			}
			
		}
		
		return bankCards;
	}
	/**
	 * @param crs
	 * @return
	 * @throws SQLException 
	 * Setting the object with values from the result set.
	 */
	private BeanBankCard getCardFromCrs(CachedRowSet crs) throws SQLException {
		BeanBankCard bankCard = new BeanBankCard();
		bankCard.setId(crs.getInt("id"));
		bankCard.setCode(crs.getString("code"));
		bankCard.setName(crs.getString("name"));
		bankCard.setDescription(crs.getString("description"));
		bankCard.setIsValid(crs.getBoolean("is_valid"));
		bankCard.setIsRefundable(crs.getBoolean("is_refundable"));
		bankCard.setIsDeleted(crs.getBoolean("is_deleted"));
		bankCard.setAlternativeRefundMethod(crs.getBoolean("alternative_refund_method"));
		bankCard.setIinPrefixRangeFrom(crs.getInt("iin_prefix_range_from"));
		bankCard.setIinPrefixRangeTo(crs.getInt("iin_prefix_range_to"));
		return bankCard;
	}
	
	public Map<Integer, BeanBankCard> getBankCards(){
		return mBankCards;
		
	}
	
	public BeanBankCard getBankCard(int id){
		if(mBankCards!=null){
			return mBankCards.get(id);
		}
		return null;
		
	}
}