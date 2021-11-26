package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.CardTypes;
import com.indocosmo.pos.data.beans.BeanCardType;

public final class PosCardTypesProvider extends PosShopDBProviderBase {

	private static Map<CardTypes, BeanCardType> mCardTypes;
	private static PosCardTypesProvider mSingleInstance;
	
	private  PosCardTypesProvider(){
		super("pos_scanner_patterns");
	}
	
	public static PosCardTypesProvider getInstance(){
		if(mSingleInstance==null)
			mSingleInstance=new PosCardTypesProvider();
		return mSingleInstance;
	}

	private void loadCardData() throws Exception{
		CachedRowSet crs=getData();
		if(crs!=null){
			mCardTypes=new HashMap<CardTypes, BeanCardType>();
			try {
				while(crs.next()){
					BeanCardType card=createCardFromCRS(crs);
					mCardTypes.put(card.getCardType(), card);
				}
			} catch (Exception e) {
				PosLog.write(this,"loadCardData",e.getMessage());
				try {
					crs.close();
				} catch (SQLException e1) {
					PosLog.write(this,"loadCardData",e1);
				}
				throw new Exception("Failed load card data");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this,"loadCardData",e);
				}
			}
		} 
	}

	private BeanCardType createCardFromCRS(CachedRowSet crs) throws SQLException{
		BeanCardType card=new BeanCardType();
		card.setCardType(CardTypes.get(crs.getInt("type")));
		card.setCharPrefix(crs.getString("char_prefix"));
		card.setCharSuffix(crs.getString("char_suffix"));
		return card;
	}

	public Map<CardTypes, BeanCardType> getCardTypes() throws Exception{
		if(mCardTypes==null)
			loadCardData();
		return mCardTypes;
	}
}
