/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanOrderSplitDetail;

/**
 * @author jojesh
 *
 */
public class PosOrderSplitDetailProvider extends PosShopDBProviderBase {

	//	private ArrayList<PreparedStatement> psList=null;;
	private PreparedStatement ps;

	/**
	 * 
	 */
	public PosOrderSplitDetailProvider() {

		super("order_split_dtls");
		//		psList =new ArrayList<PreparedStatement>();
	}

	/**
	 * @throws SQLException
	 */
	private void initPS() throws SQLException{

		String insert_sql="insert into "+ mTablename +" ("	+
				"id"+
				",order_id "+
				",split_id "+					
				",order_dtl_id "+
				",order_dtl_sub_id"+
				",order_dtl_qty "+					
				",order_dtl_price "+
				") "+
				" values (?,?,?,?,?,?,?)";
		ps=mConnection.prepareStatement(insert_sql);
	}

	/**
	 * @param orderSPlitDtls
	 * @param parentID
	 * @throws SQLException
	 */
	public void addSplitDetails(ArrayList<BeanOrderSplitDetail> orderSPlitDtls, final String parentID) throws SQLException{

		if(ps==null)
			initPS();

		int counter=0;
		for (BeanOrderSplitDetail split: orderSPlitDtls){

			final String splitDtlID=PosOrderUtil.appendToId(parentID, counter++);
			PosLog.debug("Split detail id :"+splitDtlID);
			PosLog.debug("Order detail  id "+split.getOrderId()+". Order detail sub id :"+split.getOrderDetailSubID());
			
			ps.setString(1, splitDtlID);
			ps.setString(2, split.getOrderId());
			ps.setString(3, parentID);
			ps.setString(4, split.getOrderDetailItemID());
			ps.setString(5, split.getOrderDetailSubID());
			ps.setDouble(6, split.getQuantity());
			ps.setDouble(7, split.getPrice());
			ps.addBatch();
		}	
	}



	/**
	 * @throws SQLException
	 */
	public void execute() throws SQLException{

		//		for(PreparedStatement ps:psList)
		if(ps!=null)
			ps.executeBatch();
	}

	/**
	 * @throws SQLException 
	 * 
	 */
	public void clear() throws SQLException {

		if(ps!=null)
			ps.clearParameters();
	}

	/**
	 * @param splitID
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<BeanOrderSplitDetail> getSplitDetails(String splitID) throws Exception {
		
		ArrayList<BeanOrderSplitDetail> splitDtls=null;
		final String where="split_id='"+splitID+"'";
		
		try {
			
			CachedRowSet crs=getData(where);
			
			if(crs!=null){

				splitDtls=new ArrayList<BeanOrderSplitDetail>();
				while(crs.next()){
					
					final BeanOrderSplitDetail dtl=createSplitDetailsFromCRS(crs);
					splitDtls.add(dtl);
				}
			}
			
		} catch (Exception e) {
			
			PosLog.write(this,"getSplitDetails", e);
			throw e;
		}
		
		return splitDtls;
	}

	/**
	 * @param crs
	 * @return
	 * @throws SQLException 
	 */
	private BeanOrderSplitDetail createSplitDetailsFromCRS(CachedRowSet crs) throws SQLException {

		BeanOrderSplitDetail dtlItem=new BeanOrderSplitDetail();
		
		dtlItem.setId(crs.getString("id"));
		dtlItem.setOrderId(crs.getString("order_id"));
		dtlItem.setSplitID(crs.getString("split_id"));
		dtlItem.setOrderDetailItemID(crs.getString("order_dtl_id"));
		dtlItem.setOrderDetailSubID(crs.getString("order_dtl_sub_id"));
		dtlItem.setQuantity(crs.getDouble("order_dtl_qty"));
		dtlItem.setPrice(crs.getDouble("order_dtl_price"));
		dtlItem.setPaid(true);
		
		return dtlItem;
	}
	
	/**
	 * @param detailItemID
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderSplitDetail> getSplitDetailsByDetailItem(String detailItemID) throws Exception {
		
		ArrayList<BeanOrderSplitDetail> splitDtls=null;
		final String where="order_dtl_id='"+detailItemID+"'";
		
		try {
			
			CachedRowSet crs=getData(where);
			
			if(crs!=null){

				splitDtls=new ArrayList<BeanOrderSplitDetail>();
				while(crs.next()){
					
					final BeanOrderSplitDetail dtl=createSplitDetailsFromCRS(crs);
					splitDtls.add(dtl);
				}
			}
			
		} catch (Exception e) {
			
			PosLog.write(this,"getSplitDetails", e);
			throw e;
		}
		
		return splitDtls;
	}

}
