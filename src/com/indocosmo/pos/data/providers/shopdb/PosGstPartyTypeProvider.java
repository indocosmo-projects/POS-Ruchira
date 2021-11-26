/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.data.beans.BeanGstPartyType;

/**
 * @author sandhya
 *
 */
public class PosGstPartyTypeProvider extends PosShopDBProviderBase {

	private static final String TABLENAME="gst_party_type";
	private static final String IDFIELD="id";
	private static ArrayList<BeanGstPartyType> mGSTPartyTypes;
	private static Map<Integer, BeanGstPartyType> mPartyTypeMap;
	
	public PosGstPartyTypeProvider(){
		super(IDFIELD,TABLENAME);
	}
	

	public ArrayList< BeanGstPartyType> getPartyTypeList() throws SQLException{
		
		if(mGSTPartyTypes==null)
			loadPartyTypes();
		return mGSTPartyTypes;
	}
	
	private void loadPartyTypes() throws SQLException{

		ArrayList<BeanGstPartyType> partyTypes=null;
		final CachedRowSet crs = getData();
		if(crs!=null&&crs.size()>0){
			
			partyTypes = new ArrayList<BeanGstPartyType>();
			while(crs.next()){
				BeanGstPartyType partyType=getBeanFromCachedrowset(crs);
				partyTypes.add(partyType);
				addToMap(partyType);
			}
		}
		
		crs.close();
		mGSTPartyTypes= partyTypes;
	}
	private void addToMap(BeanGstPartyType partyType){
		
		if(mPartyTypeMap==null)
			mPartyTypeMap=new HashMap<Integer, BeanGstPartyType>();
		
		if(!mPartyTypeMap.containsKey(partyType.getId()))
			mPartyTypeMap.put(partyType.getId(), partyType);
	}

	public  BeanGstPartyType  getPartyType(Integer id ) throws SQLException{

		if(mGSTPartyTypes==null)
			loadPartyTypes();
		
		BeanGstPartyType partyType=null;
		if(mPartyTypeMap.containsKey(id))
			partyType=mPartyTypeMap.get(id);
		return partyType;
	}

	/**
	 * @param crs
	 * @return
	 * @throws SQLException 
	 */
	private BeanGstPartyType getBeanFromCachedrowset(CachedRowSet crs) throws SQLException {
		
		final BeanGstPartyType partyType = new BeanGstPartyType();
	 
		partyType.setId(crs.getInt("id"));
		partyType.setName(crs.getString("name"));
		 
		return partyType;
	}
}
