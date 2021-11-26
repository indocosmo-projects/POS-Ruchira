/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.split.SplitBasedOn;
import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderSplit;
import com.indocosmo.pos.data.beans.BeanOrderSplitDetail;

/**
 * @author jojesh-13.2
 *
 */
public class PosOrderSplitProvider extends PosShopDBProviderBase {

	public static int SPLIT_NO_WIDTH=2;
	private PosOrderSplitDetailProvider splitDtlProvider;

	   private PreparedStatement mInsertDetailItemPs;
	   private PreparedStatement mUpateDetailItemPs;

		
		/**
		 * 
		 */
		public PosOrderSplitProvider() {
			super("order_splits");
			splitDtlProvider=new PosOrderSplitDetailProvider();
			
		}

	private void initPreparedStatment() throws SQLException {

		if (mInsertDetailItemPs != null && PosDBUtil.getInstance().isValidConnection(mInsertDetailItemPs.getConnection())) 
			mInsertDetailItemPs.clearParameters();
		 else
			mInsertDetailItemPs = getConnection().prepareStatement(getInsertStatement());

		if (mUpateDetailItemPs != null && PosDBUtil.getInstance().isValidConnection(mUpateDetailItemPs.getConnection())) 
			mUpateDetailItemPs.clearParameters();
		 else
			 mUpateDetailItemPs = getConnection().prepareStatement(getUpdateStatement());

 
	}

/*
 * 
 */
	   private String getInsertStatement() {
		   String insert_sql="insert into "+mTablename+" ("	+
					" id"+
					", split_no"+	
					", order_id"+					
					", based_on"+					
					", value"+					
					", description"+					
					", amount" +
					", adj_amount" +
					", actual_amount_paid" +
					", round_adj"+
					", discount"+
					", part_pay_adj"+
					" ) "+
					" values (?,?,?,?,?,?,?,?,?,?,?,?)";
	      return insert_sql;
	   }
	   
/*
 * 
 */
	   private String getUpdateStatement() {
		      String insert_sql="update "+mTablename+"  SET "    +
		    		  " id =?"+
						", split_no=?"+	
						", order_id=?"+					
						", based_on=?"+					
						", value=?"+					
						", description=?"+					
						", amount=?" +
						", adj_amount=?" +
						", actual_amount_paid=?" +
						", round_adj=?"+
						", discount=?"+
						", part_pay_adj=?"+
						 " WHERE id=? " +
		          
		            " ";
		      return insert_sql;
		   } 
	
	/**
	 * @param orderSplits
	 * @return
	 * @throws SQLException
	 */
	public void setOrderSplits(ArrayList<BeanOrderSplit> orderSplits) throws SQLException{
		
		PreparedStatement ps=null;
		initPreparedStatment();
		
		int counter=0;
		for (BeanOrderSplit split: orderSplits){
			
			final String splitID=PosOrderUtil.appendToId(split.getOrderID(), counter++);
			split.setSplitNo(counter);
		
			final boolean isExist = isExist("order_id='"+split.getOrderID()+"' and id='"+splitID  +"'");
			
			if (isExist) {
			            /*We need to update data if exist*/
			    ps=mUpateDetailItemPs;
			 } else {
			            /*We need to insert data if not exist*/
			    ps=mInsertDetailItemPs;
			 }
			PosLog.debug("Split  id :"+splitID);
			ps.setString(1, splitID);
			ps.setInt(2, split.getSplitNo());
			ps.setString(3, split.getOrderID());
			ps.setInt(4, split.getBasedOn().getCode());
			ps.setDouble(5, split.getValue());
			ps.setString(6, split.getDescription());
			ps.setDouble(7, split.getAmount());
			ps.setDouble(8, split.getAdjustAmount());
			ps.setDouble(9, split.getPayedAmount());
			ps.setDouble(10, split.getRoundingAdjustment());
			ps.setDouble(11, split.getDiscountAmount());
			ps.setDouble(12, split.getPartPayAdjustment());
			ps.addBatch();
			
			final ArrayList<BeanOrderSplitDetail> orderSPlitDtls = split.getSplitDetails();
			
			if(orderSPlitDtls!=null && orderSPlitDtls.size()>0)
				splitDtlProvider.addSplitDetails(orderSPlitDtls, splitID);
		}	
		 
	}
	
	/**
	 * @throws SQLException
	 */
	public void execute() throws SQLException{
		
		if (mInsertDetailItemPs != null) 
			mInsertDetailItemPs.executeBatch();
		 
		if (mUpateDetailItemPs != null) 
			mUpateDetailItemPs.executeBatch();
		
		if(splitDtlProvider!=null)
			splitDtlProvider.execute();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.data.providers.PosDatabaseProvider#deleteData(java.lang.String)
	 */
	@Override
	public int deleteData(String where) throws SQLException {
		
		if(splitDtlProvider!=null)
			splitDtlProvider.deleteData(where);
		
		return super.deleteData(where);
	}

 
	/**
	 * @param orderObject
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<BeanOrderSplit> getSplits(BeanOrderHeader orderObject) throws Exception {
	
		return getSplits(orderObject.getOrderId());
	}
	
	/**
	 * @param orderObject
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<BeanOrderSplit> getSplits(String id) throws Exception {
	
		ArrayList<BeanOrderSplit> splits=null;
		
		final String where="order_id='"+ id +"'";
		
		try{
			
			CachedRowSet crs=getData(where);
			if(crs!=null){
				splits=new ArrayList<BeanOrderSplit>();
				while(crs.next()){

					BeanOrderSplit split=createSplitFromCRS(crs);
					splits.add(split);
					
				}
			}
		}catch(Exception e){
			
			PosLog.write(this, "", e);
			throw e;

		}
		
		return splits;
	}


	/**
	 * @param crs
	 * @return
	 * @throws Exception 
	 */
	private BeanOrderSplit createSplitFromCRS(CachedRowSet crs) throws Exception {
		
		BeanOrderSplit split=new BeanOrderSplit();
		
		split.setAdjustAmount(crs.getDouble("adj_amount"));
		split.setAmount(crs.getDouble("amount"));
		split.setBasedOn(SplitBasedOn.get(crs.getInt("based_on")));
		split.setId(crs.getString("id"));
		split.setSplitNo(crs.getInt("split_no"));
		split.setDescription(crs.getString("description"));
		split.setOrderID(crs.getString("order_id"));
		split.setPayedAmount(crs.getDouble("actual_amount_paid"));
		split.setPayed(true);
		split.setSplitDetails(getSplitDetails(crs.getString("id")));
		split.setValue(crs.getDouble("value"));
		split.setRoundingAdjustment(crs.getDouble("round_adj"));
		split.setDiscount(crs.getDouble("discount"));
		split.setPartPayAdjustment(crs.getDouble("part_pay_adj"));
		
		return split;
	}


	/**
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<BeanOrderSplitDetail> getSplitDetails(String splitID) throws Exception {

		
		return splitDtlProvider.getSplitDetails(splitID);
	}
	
//	private double getPartPaymentAmount(String orderId){
//		
//		double amount=0;
//		final String where="order_id='"+orderId+"'";
//		
//				
//		return amount;
//	}

	/**
	 * @param orderHdrItem
	 * @throws SQLException 
	 */
	public void save(BeanOrderHeader orderHdrItem) throws SQLException {
		
		PosLog.debug("Satrting to save order split...... ");
		
		final String where= " order_id='"+orderHdrItem.getOrderId()+"'";
		deleteData(where);
		if(orderHdrItem.getOrderSplits()!=null && orderHdrItem.getOrderSplits().size()>0){
			setOrderSplits(orderHdrItem.getOrderSplits());
			execute();
		}
		
		PosLog.debug("Finished save order split...... ");
	}


 
}
