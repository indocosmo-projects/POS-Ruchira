/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanDiscountSummary;
import com.indocosmo.pos.data.beans.BeanOrderDiscount;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanUser;

/**
 * @author deepak
 *
 */
public class PosOrderDiscountProvider extends PosShopDBProviderBase{
	/**
	 * 
	 */
	private PreparedStatement psDiscountInsert;
//	private PreparedStatement psDiscountUpdate;

	/**
	 * Default Constructor
	 */
	public PosOrderDiscountProvider() {
		super("order_discounts");
	}

	/**
	 * initializes the preparedStatement
	 * @throws SQLException
	 */
	private void initPreparedStatements() throws SQLException{

		if(psDiscountInsert!=null && PosDBUtil.getInstance().isValidConnection(psDiscountInsert.getConnection()))
			psDiscountInsert.clearParameters();
		else
			psDiscountInsert=getConnection().prepareStatement(getInsertStatement());

//
//		if(psDiscountUpdate!=null)
//			psDiscountUpdate.clearParameters();
//		else
//			psDiscountUpdate=getConnection().prepareStatement(getUpdateStatement());
	}

	/**
	 * Creates the Insert sql
	 * @return
	 */
	private String getInsertStatement(){

		final String insert_sql = "insert into "+"order_discounts "+" (" +
				" id, "+
				" order_id, "+
				" order_payment_hdr_id, " +
				" discount_id, "+
				" code, "+
				" name, "+
				" description, "+
				" is_percentage, "+
				" is_overridable, "+
				" price, "+
				" amount "+
				") "+
				" values (?,?,?,?,?,?,?,?,?,?,?)";
		return insert_sql;
	}

//	/**
//	 * Creates the update statements
//	 * @return
//	 */
//	private String getUpdateStatement(){
//
//		final String update_sql = "UPDATE order_discounts "+
//				"SET " +
//				"id = ?, " +
//				"order_id = ?, " +
//				"order_payment_hdr_id=?,"  +
//				"discount_id = ?," +
//				"code = ?, " +
//				"name = ?, " +
//				"description = ?, " +
//				"price = ?, " +
//				"is_percentage = ?, " +
//				"is_overridable = ?, " +
//				"amount = ? "+
//				"WHERE id = ?;";
//
//		return update_sql;
//	}

	public void save(BeanOrderHeader orderHeader) throws SQLException{
		
		PosLog.debug("Satrting to save order discounts...... ");

		ArrayList<BeanOrderDiscount> mOrderDiscountObjects = orderHeader.getBillDiscounts(); //= orderHeader.getBillDiscount();
		if(mOrderDiscountObjects==null) return ;

		initPreparedStatements();

		int insCount=0;
//		int updCount=0;

		int counter=0;
		for(BeanOrderDiscount mOrderDiscountObject:mOrderDiscountObjects){

			final PreparedStatement prep;
			final String id=PosOrderUtil.appendToId(orderHeader.getOrderId(), counter++);
//			final boolean isExist=isExist("id='"+id+"'");
//
//			if(isExist){
//
//				prep=psDiscountUpdate;
//				updCount++;
//
//			}else{
//
//				prep=psDiscountInsert;
//				insCount++;
//
//			}
			
			if (!mOrderDiscountObject.isNew()) 
				continue;
			
			prep=psDiscountInsert;
			insCount++;

			prep.setString(1,id );
			prep.setString(2, orderHeader.getOrderId());
			prep.setString(3, mOrderDiscountObject.getOrderPaymentHdrId());
			prep.setInt(4, mOrderDiscountObject.getId());
			prep.setString(5, mOrderDiscountObject.getCode());
			prep.setString(6, mOrderDiscountObject.getName());
			prep.setString(7, mOrderDiscountObject.getDescription());
			prep.setBoolean(8, mOrderDiscountObject.isPercentage());
			prep.setBoolean(9, mOrderDiscountObject.isOverridable());
			prep.setDouble(10, mOrderDiscountObject.getPrice());
			prep.setDouble(11, mOrderDiscountObject.getDiscountedAmount());
			
			prep.addBatch();
		}

		if(insCount>0)
			psDiscountInsert.executeBatch();
		
//		if(updCount>0)
//			psDiscountUpdate.executeBatch();
		
		PosLog.debug("Finished save order discounts...... ");

	}

	 
	public ArrayList<BeanOrderDiscount> getOrderDiscounts(BeanOrderHeader orderHeader) throws SQLException{

		final String where="order_id='"+orderHeader.getOrderId()+"'";
		CachedRowSet crs=getData(where);
		ArrayList<BeanOrderDiscount> discounts = null;
		if(crs!=null){
			discounts=new ArrayList<BeanOrderDiscount>();
			while(crs.next()){
				BeanOrderDiscount discount=createDiscountFromRecordset(crs);
				discounts.add(discount);
			}
			crs.close();
		}
		return discounts;
	}
	/*
	 * For Shift Reports
	 */
	public double getTotalDiscount(String where, Integer shiftId) throws SQLException{
		double totalDiscount = 0;
		final String Sql ="SELECT sum(ods.amount) as total_discount " +
							 " FROM order_discounts  ods " +    
							 " JOIN order_payment_hdr ophdr ON ods.order_id=ophdr.order_id AND  ods.order_payment_hdr_id=ophdr.id " +  
							 " JOIN order_hdrs ohdr ON ophdr.order_id=ohdr.order_id " + 
							 "  where "+ where + (shiftId>0? " AND ophdr.shift_id=" + shiftId : "") ;
		
		CachedRowSet crs = executeQuery(Sql);
		if(crs!=null){
			while(crs.next()){
				totalDiscount = crs.getDouble("total_discount");
			}
		}
		return totalDiscount;
	}
	/*
	 * For Shift Summary Report 
	 */
	
		public double getTotalDiscountForShiftSummaryReport(String posDate, Integer shiftId) throws SQLException{

			String where=" ophdr.payment_date ='" + posDate + "'  ";
			return getTotalDiscount(where,shiftId);
			
		}

	public double getBillDiscountsApplied(String where) throws SQLException{
		double totalDiscount = 0;
		final String Sql ="SELECT  sum(amount) as total_discount,name FROM order_discounts where "+where+" group by name";
		CachedRowSet crs = executeQuery(Sql);
		if(crs!=null){
			while(crs.next()){
				totalDiscount = crs.getDouble("total_discount");
			}
		}
		return totalDiscount;
	}

	public ArrayList<BeanDiscountSummary> getDiscountSummary(String where,Integer shiftId) throws SQLException{
		
		final String Sql ="SELECT sum(ods.amount) as total_discount ,count(*) as quantity,name " +
				 " FROM order_discounts  ods " +    
				 " JOIN order_payment_hdr ophdr ON ods.order_id=ophdr.order_id AND  ods.order_payment_hdr_id=ophdr.id " +  
				 " JOIN order_hdrs ohdr ON ophdr.order_id=ohdr.order_id " + 
				 "  where "+ where + (shiftId>0? " AND ophdr.shift_id=" + shiftId : "") + 
				 " group by name"; 
		
		//		final String SQL="SELECT dtl.discount_id, dtl.discount_name, dtl.discount_code, sum(discount_amount) as amount  FROM `order_dtls` dtl where dtl.is_void is not 1 and dtl.discount_id not in (1,2) and "+where+" group by dtl.discount_name  ";
		ArrayList<BeanDiscountSummary> discountSummary=null;
		CachedRowSet crs=executeQuery(Sql);
		try{
			if(crs!=null){
				discountSummary=new ArrayList<BeanDiscountSummary>();
				while(crs.next()){
					BeanDiscountSummary discount=getDiscountSummaryFromRecordSet(crs);
					discountSummary.add(discount);
				}		
				crs.close();
			}
		}catch (Exception ex){
			PosLog.write(this, "getDiscountSummary", ex);
		}
		return discountSummary;	
	}
 
	private BeanDiscountSummary getDiscountSummaryFromRecordSet(
			CachedRowSet crs) throws SQLException {
		BeanDiscountSummary discount = new BeanDiscountSummary();
		//		discount.setId(crs.getInt("discount_id"));
		discount.setName(crs.getString("name"));
		//		discount.setCode(crs.getString("discount_code"));
		discount.setQuantity(crs.getDouble("quantity"));
		discount.setAmount(crs.getDouble("total_discount"));

		return discount;
	}

	/**
	 * @param crs
	 * @return
	 * @throws SQLException 
	 */
	private BeanOrderDiscount createDiscountFromRecordset(CachedRowSet crs) throws SQLException {
		
		BeanOrderDiscount discount = new BeanOrderDiscount();
		discount.setOrderPaymentHdrId(crs.getString("order_payment_hdr_id"));
		discount.setId(crs.getInt("discount_id"));
		discount.setCode(crs.getString("code"));
		discount.setName(crs.getString("name"));
		discount.setDescription(crs.getString("description"));
		discount.setPercentage(crs.getBoolean("is_percentage"));
		discount.setOverridable(crs.getBoolean("is_overridable"));
		discount.setPrice(crs.getDouble("price"));
		discount.setDiscountedAmount(crs.getDouble("amount"));

		return discount;
	}

}
