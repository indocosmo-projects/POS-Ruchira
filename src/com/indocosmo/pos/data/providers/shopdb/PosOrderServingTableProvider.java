/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderServingTable;
import com.indocosmo.pos.data.beans.BeanServingTable;

/**
 * @author jojesh-13.2
 *
 */
public class PosOrderServingTableProvider extends PosServiceTableProvider {
	
	

	/**
	 * Sets the table name.
	 */
	//	private PosOrderServingSeatProvider mPosOrderServingSeatProvider;
	private PreparedStatement ps;


	/**
	 * Instantiates a sevice table databse.
	 *
	 * @param p_context the p_context
	 */
	public PosOrderServingTableProvider() {
		super("v_order_serving_tables");
		
	}

	/**
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	public Map<String, BeanOrderServingTable> getOrderTables(String orderID) throws Exception{

		Map<String, BeanOrderServingTable> tableList=null;

		final String sql="select * from v_order_serving_tables where order_id='"+orderID+"'";


		CachedRowSet crs=executeQuery(sql);

		if(crs!=null){

			tableList=new HashMap<String, BeanOrderServingTable>();

			while(crs.next()){

				final BeanOrderServingTable table=new BeanOrderServingTable();
				setItemFromCrs(crs, table);
				tableList.put(table.getCode(), table);
			}
		}
		return tableList;
	}

	/**
	 * @param crs
	 * @param item
	 * @throws SQLException
	 */
	public void setItemFromCrs(CachedRowSet crs, BeanOrderServingTable item)
			throws Exception {

		super.setItemFromCrs(crs, (BeanServingTable)item);
		item.setSelected(crs.getBoolean("is_selected"));
		item.setSelectedSeatNo(crs.getInt("selected_seat_no"));
	}

	/**
	 * @throws SQLException
	 */
	private void initPS() throws SQLException{

		String insert_sql="insert into "+ "order_serving_tables" +" ("	+
				"order_id "+
				",table_id "+					
				",selected_seat_no "+
				",is_selected "+
				",is_void "+					
				",covers "+
				") "+
				" values (?,?,?,?,?,?)";


		ps=mConnection.prepareStatement(insert_sql);
	}

	/**
	 * @param orderTables
	 * @param orderID
	 * @throws SQLException
	 */
	private void setOrderTableDetails(Map<String, BeanOrderServingTable> orderTables, final String orderID) throws SQLException{

		if(ps==null)
			initPS();

		for (BeanOrderServingTable table: orderTables.values()){

			if(!table.isVoid()){
				ps.setString(1, orderID);
				ps.setInt(2, table.getId());
				ps.setInt(3, table.getSelectedSeatNo());
				ps.setBoolean(4, table.IsSelected());
				ps.setBoolean(5, table.isVoid());
				ps.setDouble(6, table.getSeatCount());
				ps.addBatch();
			}
		}	
	}

	/**
	 * @throws SQLException
	 */
	private void execute() throws SQLException{

		if(ps!=null)
			ps.executeBatch();
	}

	/**
	 * @param order
	 * @throws SQLException
	 */
	public void save(BeanOrderHeader order) throws SQLException{

		final String where="order_id='"+ order.getOrderId() +"'";
		deleteData(where);

		if(order.getOrderTableList()!=null && order.getOrderTableList().size()>0){

			setOrderTableDetails(order.getOrderTableList(),order.getOrderId());
			execute();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.data.providers.PosDatabaseProvider#deleteData(java.lang.String)
	 */
	@Override
	public int deleteData(String where) throws SQLException {
	
		final String sql= "delete from order_serving_tables where "+where;
		
		return executeNonQuery(sql);
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.data.providers.shopdb.PosServiceTableProvider#createItemFromCrs(javax.sql.rowset.CachedRowSet)
	 */
	@Override
	protected BeanServingTable createItemFromCrs(CachedRowSet crs)
			throws Exception {
		
		BeanOrderServingTable item= new BeanOrderServingTable();
		setItemFromCrs(crs,item);
		return item;
		
	}
 

}
