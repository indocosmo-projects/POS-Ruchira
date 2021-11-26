/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.data.beans.BeanGstRegisterType;

/**
 * @author sandhya
 *
 */
public class PosGstRegisterTypeProvider extends PosShopDBProviderBase {

	private static final String TABLENAME="gst_reg_type";
	private static final String IDFIELD="id";
	private static ArrayList<BeanGstRegisterType> mGSTRegisterTypes;
	private static Map<Integer, BeanGstRegisterType> mRegisterTypeMap;
	
	public PosGstRegisterTypeProvider(){
		super(IDFIELD,TABLENAME);
	}
	

	public ArrayList< BeanGstRegisterType> getRegisterTypeList() throws SQLException{
		if(mGSTRegisterTypes==null)
			loadRegisterTypes();
		return mGSTRegisterTypes;
	}
	
	private void loadRegisterTypes() throws SQLException{

		ArrayList<BeanGstRegisterType> registerTypes=null;
		final CachedRowSet crs = getData();
		if(crs!=null&&crs.size()>0){
			
			registerTypes = new ArrayList<BeanGstRegisterType>();
			while(crs.next()){
				BeanGstRegisterType registerType=getBeanFromCachedrowset(crs);
				registerTypes.add(registerType);
				addToMap(registerType);
			}
		}
		
		crs.close();
		mGSTRegisterTypes= registerTypes;
	}
	private void addToMap(BeanGstRegisterType registerType){
		
		if(mRegisterTypeMap==null)
			mRegisterTypeMap=new HashMap<Integer, BeanGstRegisterType>();
		
		if(!mRegisterTypeMap.containsKey(registerType.getId()))
			mRegisterTypeMap.put(registerType.getId(), registerType);
	}

	public  BeanGstRegisterType  getRegisterType(Integer id ) throws SQLException{

		if(mGSTRegisterTypes==null)
			loadRegisterTypes();
		
		BeanGstRegisterType registerType=null;
		if(mRegisterTypeMap.containsKey(id))
			registerType=mRegisterTypeMap.get(id);
		return registerType;
	}

	/**
	 * @param crs
	 * @return
	 * @throws SQLException 
	 */
	private BeanGstRegisterType getBeanFromCachedrowset(CachedRowSet crs) throws SQLException {
		
		final BeanGstRegisterType registerType = new BeanGstRegisterType();
	 
		registerType.setId(crs.getInt("id"));
		registerType.setName(crs.getString("name"));
		 
		return registerType;
	}


}
