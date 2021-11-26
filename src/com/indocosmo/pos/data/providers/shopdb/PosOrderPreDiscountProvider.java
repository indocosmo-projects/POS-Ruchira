/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanDiscountSummary;
import com.indocosmo.pos.data.beans.BeanOrderDiscount;
import com.indocosmo.pos.data.beans.BeanOrderHeader;

/**
 * @author sandhya
 *
 */
public class PosOrderPreDiscountProvider extends PosShopDBProviderBase{
	/**
	 * 
	 */
	private PreparedStatement psDiscountInsert;
//	private PreparedStatement psDiscountUpdate;

	/**
	 * Default Constructor
	 */
	public PosOrderPreDiscountProvider() {
		super("order_pre_discounts");
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
	}

	/**
	 * Creates the Insert sql
	 * @return
	 */
	private String getInsertStatement(){

		final String insert_sql = "insert into "+"order_pre_discounts "+" (" +
				" id, "+
				" order_id, "+
				" discount_id, "+
				" code, "+
				" name, "+
				" description, "+
				" is_percentage, "+
				" is_overridable, "+
				" price "+
				") "+
				" values (?,?,?,?,?,?,?,?,?)";
		return insert_sql;
	}

 
	public void save(BeanOrderHeader orderHeader) throws SQLException{
		
		PosLog.debug("Satrting to save order discounts...... ");

		if(!orderHeader.isNewOrder()){
			
//			final String sql = "delete from  order_pre_discounts where order_id='" + orderHeader.getOrderId() + "'";
//	
//			try {
//				executeNonQuery(sql);
//			}  catch (SQLException e) {
//				PosLog.write(this,"save order pre discounts", e);
//			}
			deletePreBillDiscount(orderHeader.getOrderId());
		}
		
		BeanDiscount discount=orderHeader.getPreBillDiscount();
		if(discount==null) return ;

		initPreparedStatements();
		int counter=0;

		final String id=PosOrderUtil.appendToId(orderHeader.getOrderId(), counter++);
		final PreparedStatement prep=psDiscountInsert;
			 
		prep.setString(1,id );
		prep.setString(2, orderHeader.getOrderId());
		prep.setInt(3, discount.getId());
		prep.setString(4, discount.getCode());
		prep.setString(5, discount.getName());
		prep.setString(6, discount.getDescription());
		prep.setBoolean(7, discount.isPercentage());
		prep.setBoolean(8, discount.isOverridable());
		prep.setDouble(9, discount.getPrice());
		
		
	   
		prep.execute();
	 
		PosLog.debug("Finished save order pre discount...... ");

	}

	public void deletePreBillDiscount(String orderId) throws SQLException{
		
		final String deleteStmt="delete from  order_pre_discounts where order_id=?";
		
		PreparedStatement psDiscountDelete=getConnection().prepareStatement(deleteStmt);
		psDiscountDelete.setString(1,orderId);
		psDiscountDelete.execute();
	} 
	
	public BeanDiscount getPreBillDiscount(String orderId) throws SQLException{

		
		final String where="order_id='"+ orderId + "'";
		BeanDiscount discount=null;
		CachedRowSet crs=getData(where);
		if(crs!=null){
			if(crs.next()){
				discount=createDiscountFromRecordset(crs);
				
			}
			crs.close();
		}
		return discount;
	}
  

	/**
	 * @param crs
	 * @return
	 * @throws SQLException 
	 */
	private BeanDiscount createDiscountFromRecordset(CachedRowSet crs) throws SQLException {
		
		BeanDiscount discount = new BeanDiscount();

		discount.setId(crs.getInt("discount_id"));
		discount.setCode(crs.getString("code"));
		discount.setName(crs.getString("name"));
		discount.setDescription(crs.getString("description"));
		discount.setPercentage(crs.getBoolean("is_percentage"));
		discount.setOverridable(crs.getBoolean("is_overridable"));
		discount.setPrice(crs.getDouble("price"));
	
		return discount;
	}

}
