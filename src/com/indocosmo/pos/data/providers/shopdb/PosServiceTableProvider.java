/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosImageUtils;
import com.indocosmo.pos.data.beans.BeanServingSeat;
import com.indocosmo.pos.data.beans.BeanServingTable;

/**
 * @author jojesh
 *
 */
public  class PosServiceTableProvider extends PosShopDBProviderBase {

	public static final String CODE_SYS_TABLE_TAKE_AWAY="TAKEAWAY";
	public static final String CODE_SYS_TABLE_NA="NA";

	private static Map<Integer, String> idCodeMap;

	private static ArrayList<BeanServingTable> serviceTableList=null;
	private static ArrayList<BeanServingTable> sysServiceTableList=null;
	
	private PosServiceTableLocationProvider locationProvider;
	/**
	 * @return the serviceTableList
	 * @throws Exception 
	 */

	public ArrayList<BeanServingTable> getServiceTableList() throws Exception {

		if(serviceTableList==null)
			loadItems();
		return serviceTableList;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private ArrayList<BeanServingTable> getSysServiceTableList() throws Exception {

		if(sysServiceTableList==null)
			loadItems();
		return sysServiceTableList;
	}

	/**
	 * @param servTyp
	 * @return
	 * @throws Exception
	 */
	public BeanServingTable getTableByServiceType(PosOrderServiceTypes servTyp) throws Exception{

		return  getTableByCode(servTyp.getSysCode());
	}

	/**
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public BeanServingTable getTableByCode(String code) throws Exception{

		BeanServingTable tableObject=null;
		tableObject= getTableByCode(getServiceTableList(), code);
		if(tableObject==null)
			tableObject= getTableByCode(getSysServiceTableList(), code);
		return tableObject;
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanServingTable> getTableByLocation(int id) throws Exception{

		ArrayList<BeanServingTable> tableList=null;
		tableList= getTableByLocation(getServiceTableList(), id);
		return tableList;
	}

	/**
	 * @param list
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private ArrayList<BeanServingTable> getTableByLocation(ArrayList<BeanServingTable> list ,int id) throws Exception{

		ArrayList<BeanServingTable> tableList=null;
		if(list!=null){
			tableList=new ArrayList<BeanServingTable>();
			for(BeanServingTable table:list)
				if(table.getLocation().getId() ==id){

					tableList.add(table);
					break;
				}
		}
		return tableList;
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BeanServingTable getTableByID(int id) throws Exception{

		BeanServingTable tableObject=null;
		tableObject= getTableByID(getServiceTableList(), id);
		if(tableObject==null)
			tableObject= getTableByID(getSysServiceTableList(), id);
		return tableObject;
	}


	/**
	 * @param list
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private  BeanServingTable getTableByID(ArrayList<BeanServingTable> list ,int id) throws Exception{

		BeanServingTable tableObject=null;
		if(list!=null)
			for(BeanServingTable table:list)
				if(table.getId()==id){

					tableObject=table;
					break;
				}
		return tableObject;
	}

	/**
	 * @param list
	 * @param code
	 * @return
	 * @throws Exception
	 */
	private BeanServingTable getTableByCode(ArrayList<BeanServingTable> list ,String code) throws Exception{

		BeanServingTable tableObject=null;
		if(list!=null)
			for(BeanServingTable table:list)
				if(table.getCode().equals(code)){

					tableObject=table;
					break;
				}
		return tableObject;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public BeanServingTable getTableTakeAway() throws Exception{

		ArrayList<BeanServingTable> list=getSysServiceTableList();
		return getTableByCode(list,CODE_SYS_TABLE_TAKE_AWAY);
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public BeanServingTable getSysTableNA() throws Exception{

		ArrayList<BeanServingTable> list=getSysServiceTableList();
		return getTableByCode(list,CODE_SYS_TABLE_NA);
	}

	/**
	 * @param table
	 */
	public PosServiceTableProvider(String table) {

		super(table);
		locationProvider=new PosServiceTableLocationProvider();
	}

	/**
	 * 
	 */
	public PosServiceTableProvider() {

		super("v_serving_tables");
		locationProvider=new PosServiceTableLocationProvider();
	}


	/**
	 * @throws Exception
	 */
	private void loadItems() throws Exception{

		ArrayList<BeanServingTable> itemList=null;
		ArrayList<BeanServingTable> sysTableList=null;
		CachedRowSet res=getSortedData("id");
		try {
			itemList=new ArrayList<BeanServingTable>();
			sysTableList=new ArrayList<BeanServingTable>();
			while (res.next()) {

				BeanServingTable item = createItemFromCrs(res);
				if(!res.getBoolean("is_system"))
					itemList.add(item); 
				else
					sysTableList.add(item);
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"loadItems",e.getMessage());
			itemList=null;
			throw e;
		}
		sysServiceTableList=sysTableList;
		serviceTableList= itemList;
	}

	/**
	 * @param crs
	 * @return
	 * @throws SQLException
	 */
	protected BeanServingTable createItemFromCrs(CachedRowSet crs) throws Exception{

		BeanServingTable item= new BeanServingTable();
		setItemFromCrs(crs,item);
		return item;
	}

	/**
	 * @param crs
	 * @param item
	 * @throws SQLException
	 */
	protected void setItemFromCrs(CachedRowSet crs,BeanServingTable item) throws Exception{

		item.setId(crs.getInt("id"));
		item.setName(crs.getString("name"));
		item.setCode(crs.getString("code"));		
		item.isSystem(crs.getBoolean("is_system"));
		item.setSeatCount(crs.getInt("covers"));
		item.setRow(crs.getInt("row_position"));
		item.setColumn(crs.getInt("column_position"));
		item.setLayoutHeight(crs.getInt("layout_height"));
		item.setLayoutWidth(crs.getInt("layout_width"));

		try {
			final String image=crs.getString("layout_image");
			if(image!=null && !image.isEmpty())
				item.setImage(PosImageUtils.decodeFromBase64(image));
		} catch (IOException e) {
			
			PosLog.write(this, "setItemFromCrs", e);
		}
//		item.setServingTableLocationId(crs.getInt("serving_table_location_id"));
		
		item.setLocation(locationProvider.getTableLocationByID(crs.getInt("serving_table_location_id")));
		setIdCodeMap(item);

	}

	/**
	 * @param item
	 */
	private void setIdCodeMap(BeanServingTable item) {

		if(idCodeMap==null)
			idCodeMap=new HashMap<Integer, String>();

		if(!idCodeMap.containsKey(item.getId()))
			idCodeMap.put(item.getId(), item.getCode());

	}

	/**
	 * @param id
	 * @return
	 */
	public String getTableCode(final int id){

		String code=null;

		if(!idCodeMap.containsKey(id))
			code=idCodeMap.get(id);

		return code;
	}

	/**
	 * @param table
	 * @return
	 */
	public ArrayList<BeanServingSeat> getServingSeats(BeanServingTable table){

		ArrayList<BeanServingSeat>seats=new ArrayList<BeanServingSeat>();

		for(int cnt=1;cnt<=table.getSeatCount();cnt++){

			BeanServingSeat seat=new BeanServingSeat();
			seat.setSeatNo(cnt);
			seats.add(seat);
		}

		return seats;

	}

}
