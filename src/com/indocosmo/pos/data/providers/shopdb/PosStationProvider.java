/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosTerminalServiceType;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalInfo;

/**
 * @author jojesh
 *
 */
public class PosStationProvider extends PosShopDBProviderBase {

	private static ArrayList<BeanTerminalInfo> mPosPosStationItemList=null;
	/**
	 * 
	 */
	public PosStationProvider() {
		super("v_stations");
	}
	
	private ArrayList<BeanTerminalInfo> loadItems(String where){
		ArrayList<BeanTerminalInfo> stationItemList=null;
		final CachedRowSet res=((where==null)?getData("type<>3"):getData(where));
		try {
			stationItemList=new ArrayList<BeanTerminalInfo>();
			while (res.next()) {
				BeanTerminalInfo item=new BeanTerminalInfo();
				item.setId(res.getInt("id"));
				item.setCode(res.getString("code"));
				item.setName(res.getString("name"));
				item.setDescription(res.getString("description"));
				item.setServiceType(PosTerminalServiceType.get(res.getInt("type")));
				stationItemList.add(item); 
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"loadItems",e);
			stationItemList=null;
		}
		return stationItemList;
	}
	
	public ArrayList<BeanTerminalInfo> getStations(){
		if(mPosPosStationItemList==null)
			mPosPosStationItemList=loadItems(null);
		return mPosPosStationItemList;
	}
	
	public BeanTerminalInfo getStation(int id){
		String where="id=" +String.valueOf(id);
		BeanTerminalInfo station=null;
		ArrayList<BeanTerminalInfo> stationlist=loadItems(where);
		if(stationlist!=null && stationlist.size()>=1)
			station=stationlist.get(0);
		return station;
	}
	
	public BeanTerminalInfo getStation(String code){
		String where="code='" +code+"'";
		BeanTerminalInfo station=null;
		ArrayList<BeanTerminalInfo> stationlist=loadItems(where);
		if(stationlist!=null && stationlist.size()>=1)
			station=stationlist.get(0);
		return station;
	}

}
