/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanOrderSplitPayment;

/**
 * @author jojesh
 * his may not be needed as the multiple split can be combined later and payed together.
 */
@Deprecated
public class PosOrderSplitPaymentProvider extends PosShopDBProviderBase {

	private ArrayList<PreparedStatement> psList=null;;

	/**
	 * 
	 */
	@Deprecated
	private PosOrderSplitPaymentProvider() {
		
		super("order_split_payments");
		psList =new ArrayList<PreparedStatement>();
	}

	/**
	 * @param orderSPlitDtls
	 * @param parentID
	 * @throws SQLException
	 */
	public void addPaymentDetails(ArrayList<BeanOrderSplitPayment> orderSPlitDtls, final String parentID) throws SQLException{

		//		if(ps==null){
		//			ps.clearParameters();
		//		else{

		String insert_sql="insert into "+ mTablename +" ("	+
				"id"+
				",split_hdr_id "+					
				",payment_id "+					
				") "+
				" values (?,?,?)";
		PreparedStatement ps=mConnection.prepareStatement(insert_sql);
		//		}

		int counter=0;
		for (BeanOrderSplitPayment split: orderSPlitDtls){

			final String splitDtlID=PosOrderUtil.appendToId(parentID, counter++);
			split.setId(splitDtlID);
			ps.setString(1, splitDtlID);
			ps.setString(2, split.getHeaderID());
			ps.setString(3, split.getTransactionID());
			ps.addBatch();
		}	

		psList.add(ps);
	}
	
	

	public void execute() throws SQLException{

		for(PreparedStatement ps:psList)
			ps.executeBatch();
	}


}
