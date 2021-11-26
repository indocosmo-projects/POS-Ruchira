/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.data.beans.BeanCurrency;

/**
 * @author anand
 *
 */
public class PosCurrencyProvider extends PosShopDBProviderBase {

	private static final String TABLENAME="v_currencies";
	private static final String IDFIELD="code";

	public PosCurrencyProvider(){
		super(IDFIELD,TABLENAME);
	}
	
	public BeanCurrency getBaseCurrency() throws SQLException{

		BeanCurrency currency=null;
		final CachedRowSet crs = getData("is_base_currency='1'");

		if(crs!=null&&crs.size()>0){

			if(crs.next())
				currency= getBeanFromCachedrowset(crs);
		}
		
		crs.close();
		return currency;

	}

	public ArrayList<BeanCurrency> getCurrencies() throws SQLException{

		ArrayList<BeanCurrency> currencies=null;
		final CachedRowSet crs = getData();
		if(crs!=null&&crs.size()>0){
			
			currencies = new ArrayList<BeanCurrency>();
			while(crs.next()){

				currencies.add(getBeanFromCachedrowset(crs));
			}
		}
		
		crs.close();
		return currencies;
	}

	/**
	 * @param crs
	 * @return
	 * @throws SQLException 
	 */
	private BeanCurrency getBeanFromCachedrowset(CachedRowSet crs) throws SQLException {
		
		final BeanCurrency currency = new BeanCurrency();
		final PosRoundingProvider roundingPvdr=new PosRoundingProvider();

		currency.setId(crs.getInt("id"));
		currency.setCode(crs.getString("code"));
		currency.setName(crs.getString("name"));
		currency.setCurrencySymbol(crs.getString("symbol"));
		currency.setFractionName(crs.getString("fraction_name"));
		currency.setFractionSymbol(crs.getString("fraction_symbol"));
		currency.setDecimalPlaces(crs.getInt("decimal_places"));
		currency.setExchangeRate(crs.getFloat("exchange_rate"));
		currency.setBaseCurrency(crs.getBoolean("is_base_currency"));
		final int roundingID=crs.getInt("rounding_id");
		currency.setRounding(roundingPvdr.getRounding(roundingID));
		
		return currency;
	}
}
