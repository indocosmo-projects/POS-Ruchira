/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.data.beans.BeanCustomer;
import com.indocosmo.pos.data.beans.BeanGstPartyType;
import com.indocosmo.pos.data.beans.BeanGstRegisterType;
import com.indocosmo.pos.data.beans.BeanOrderCustomer;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderServingTable;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;

/**
 * @author jojesh-13.2
 *
 */
public class PosOrderCustomerProvider extends PosServiceTableProvider {
	
	
	private PreparedStatement mInsertDetailItemPs;
	private PreparedStatement mUpateDetailItemPs;
//	private static Map<String, BeanOrderCustomer> mOrderCustomerMap;
	private ArrayList<BeanOrderCustomer> mCustomerList;
	private PosGstRegisterTypeProvider mGstRegisterTypeProvider;
	private PosGstPartyTypeProvider mGstPartyTypeProvider;
	
	/**
	 * Instantiates a sevice table databse.
	 *
	 * @param p_context the p_context
	 */
	public PosOrderCustomerProvider() {
		super("order_customers");
		mGstRegisterTypeProvider=new PosGstRegisterTypeProvider();
		mGstPartyTypeProvider=new PosGstPartyTypeProvider();
	}
	/**
	 * 
	 * @throws SQLException
	 */
	private void initPreparedStatment() throws SQLException {

		 
		if (mInsertDetailItemPs != null && PosDBUtil.getInstance().isValidConnection(mInsertDetailItemPs.getConnection())) 
			mInsertDetailItemPs.clearParameters();
		 else
			mInsertDetailItemPs = getConnection().prepareStatement(getInsertStatement());

		if (mUpateDetailItemPs != null) 
			mUpateDetailItemPs.clearParameters();
		else
			mUpateDetailItemPs = getConnection().prepareStatement(getUpdateStatement());

	 

	}
 	/**
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	public BeanOrderCustomer getOrderCustomer(String orderID) throws Exception{

		final String sql="select * from order_customers where order_id='"+orderID+"'";

		BeanOrderCustomer orderCustomer=null;
		CachedRowSet crs=executeQuery(sql);

		if(crs!=null){
			if(crs.next()){

				orderCustomer=createOrderCustomerFromCrs(crs);
			}
		}
		return orderCustomer;
	}

	
	/**
	 * @param crs
	 * @param item
	 * @throws SQLException
	 */
	private BeanOrderCustomer createOrderCustomerFromCrs(CachedRowSet crs )
			throws Exception {

		BeanOrderCustomer orderCustomer=new BeanOrderCustomer();
		orderCustomer.setOrderID(crs.getString("order_id"));
		orderCustomer.setCode(crs.getString("code"));
		orderCustomer.setName(crs.getString("name"));
		orderCustomer.setAddress(crs.getString("address"));
		orderCustomer.setCity(crs.getString("city"));
		orderCustomer.setState(crs.getString("state"));
		orderCustomer.setStateCode(crs.getString("state_code"));
		orderCustomer.setCountry(crs.getString("country"));
//		orderCustomer.setPhoneNumber(PosNumberUtil.parseIntegerSafely(crs.getString("phone"))==0?"":crs.getString("phone"));
		orderCustomer.setPhoneNumber(crs.getString("phone"));
		orderCustomer.setTinNo(crs.getString("tin"));
		
		orderCustomer.setAddress2(crs.getString("address2"));
		orderCustomer.setAddress3(crs.getString("address3"));
		orderCustomer.setAddress4(crs.getString("address4"));
		
		orderCustomer.setPhoneNumber2(crs.getString("phone2"));
		
		final BeanGstRegisterType gstRegisterType=mGstRegisterTypeProvider.getRegisterType(crs.getInt("gst_reg_type"));
		orderCustomer.setGstRegisterType(gstRegisterType);
		final BeanGstPartyType gstPartyType=mGstPartyTypeProvider.getPartyType(crs.getInt("gst_party_type"));
		orderCustomer.setGstPartyType(gstPartyType);
		return orderCustomer;
	}
	
	/**
	 * @throws SQLException
	 * Execute the prepared statement.
	 */
	private void executePS() throws SQLException{

		if (mInsertDetailItemPs != null) 
			mInsertDetailItemPs.executeBatch();
		 
		if (mUpateDetailItemPs != null) 
			mUpateDetailItemPs.executeBatch();
		
	}

	/**
	 * @throws SQLException
	 */
	private String getInsertStatement() throws SQLException{

		final String insert_sql = "insert into order_customers "+" (" +
				" order_id, "+
				" code, "+
				" name, "+
				" address, "+
				" city, "+
				" state, "+
				" state_code, "+
				" country, "+
				" phone, " + 
				" tin ,"+
				" gst_reg_type , " + 
				" gst_party_type, "+
				" address2, " +
				" address3, " + 
				" address4, " + 
				" phone2," +
				" updated_at," +
				" customer_type"+ 
				") "+
				" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return insert_sql;
	}

	/**
	 * @throws SQLException
	 */
	private String getUpdateStatement() throws SQLException{

		final String update_sql = "update order_customers SET " +
				" order_id=?, "+
				" code=?, "+
				" name=?, "+
				" address=?, "+
				" city=?, "+
				" state=?, "+
				" state_code=?, "+
				" country=?, "+
				" phone=?, " + 
				" tin=?, "+
				" gst_reg_type=?, " + 
				" gst_party_type=?, "+
				" address2=?, " +
				" address3=?, " + 
				" address4=?, " + 
				" phone2=?," +
				" updated_at=?," + 
				" customer_type=? "+ 
				" WHERE order_id=? ";
		return update_sql;
	}
 

	/**
	 * @param orderHdrItem
	 * @throws SQLException
	 */
	public void  save(BeanOrderHeader orderHeader) throws SQLException{
		
		PosLog.debug("Satrting to save order customer...... ");
		
		if( orderHeader.getOrderCustomer()==null) return ;

		initPreparedStatment();
		PreparedStatement prep;
		
		if(orderHeader.isNewOrder())
			prep=mInsertDetailItemPs;
		else
			prep=mUpateDetailItemPs;

		prep.setString(1, orderHeader.getOrderId());
		
		if(orderHeader.getOrderCustomer().getCode()==null)
			prep.setObject(2, null);
		else
			prep.setString(2,orderHeader.getOrderCustomer().getCode());
		
		
		prep.setString(3, orderHeader.getOrderCustomer().getName());
		prep.setString(4, orderHeader.getOrderCustomer().getAddress());
		prep.setString(5, orderHeader.getOrderCustomer().getCity());
		prep.setString(6, orderHeader.getOrderCustomer().getState());
		prep.setString(7, orderHeader.getOrderCustomer().getStateCode());
		prep.setString(8, orderHeader.getOrderCustomer().getCountry());
		prep.setString(9, orderHeader.getOrderCustomer().getPhoneNumber());
		prep.setString(10, orderHeader.getOrderCustomer().getTinNo());
		
		if(orderHeader.getOrderCustomer().getGstRegisterType()==null)
			prep.setObject(11, null);
		else
			prep.setInt(11,orderHeader.getOrderCustomer().getGstRegisterType().getId());
		
		if(orderHeader.getOrderCustomer().getGstPartyType()==null)
			prep.setObject(12, null);
		else
			prep.setInt(12,orderHeader.getOrderCustomer().getGstPartyType().getId());
		
		prep.setString(13, orderHeader.getOrderCustomer().getAddress2());
		prep.setString(14, orderHeader.getOrderCustomer().getAddress3());
		prep.setString(15, orderHeader.getOrderCustomer().getAddress4());
		prep.setString(16, orderHeader.getOrderCustomer().getPhoneNumber2());
		prep.setString(17, PosDateUtil.getDateTime());
		prep.setInt(18,orderHeader.getCustomer().getCustType().getId());
		if(!orderHeader.isNewOrder())
			prep.setString(19, orderHeader.getOrderId());
		prep.addBatch();
		
		executePS();
		
		PosLog.debug("Finished save order customer...... ");
	}
	
	public ArrayList< BeanOrderCustomer> getItemList(){
		 
			loadItems();
		return mCustomerList;
	}
	private void loadItems(){
		
		CachedRowSet res=executeQuery("SELECT  * FROM v_customer_list order by name");
		ArrayList<BeanOrderCustomer> customerList=new ArrayList<BeanOrderCustomer>();
	 	try {
			if(res.next())
				do{
					BeanOrderCustomer item=createCustomerFromCrs(res);
					customerList.add(item); 
				}while (res.next()) ;
			res.close();
		} catch (Exception e) {
			PosLog.write(this,"loadCustomers",e);
		}
		
		mCustomerList= customerList;
	}
	/**
	 * @param crs
	 * @param item
	 * @throws SQLException
	 */
	private BeanOrderCustomer createCustomerFromCrs(CachedRowSet crs )
			throws Exception {

		BeanOrderCustomer customer=new BeanOrderCustomer();
		customer.setId(crs.getInt("id"));
		customer.setCode(crs.getString("code"));
		customer.setName(crs.getString("name"));
		customer.setAddress(crs.getString("address"));
		customer.setCity(crs.getString("city"));
		customer.setState(crs.getString("state"));
		customer.setStateCode(crs.getString("state_code"));
		customer.setCountry(crs.getString("country"));
		customer.setPhoneNumber(crs.getString("phone"));
		customer.setTinNo(crs.getString("tin"));
		
		final BeanGstRegisterType gstRegisterType=mGstRegisterTypeProvider.getRegisterType(crs.getInt("gst_reg_type"));
		customer.setGstRegisterType(gstRegisterType);
		final BeanGstPartyType gstPartyType=mGstPartyTypeProvider.getPartyType(crs.getInt("gst_party_type"));
		customer.setGstPartyType(gstPartyType);
		
		customer.setCutomerTypeId(crs.getInt("customer_type"));
		customer.setAddress2(crs.getString("address2"));
		customer.setAddress3(crs.getString("address3"));
		customer.setAddress4(crs.getString("address4"));
		customer.setPhoneNumber2(crs.getString("phone2"));
		
		return customer;
	}
	
	
	/**
	 * 
	 */
	public BeanOrderCustomer loadCustomerByPhoneNo(String phoneNo1,String phoneNo2) throws Exception{
		
		BeanOrderCustomer customer=null;
		String where = "";
		
		
		  String sql="SELECT ocust.* from order_customers  ocust  " +
				  	" JOIN customer_types custtype on ocust.customer_type=custtype.id "+ 
				  	" WHERE custtype.code<>'" + PosCustomerTypeProvider.ROOM_TYPE_CODE + "'";
		  
		  where += (phoneNo1==null || phoneNo1.trim().equals(""))?"":
			  "  (phone like '%" + phoneNo1 + "%' or phone2 like '%" + phoneNo1 + "%')" ; 
		  where += (phoneNo2==null || phoneNo2.trim().equals(""))?"":
			  (phoneNo1.trim().equals("")?"":  " or ")+ " ( phone like '%" + phoneNo2 + "%' or phone2 like '%" + phoneNo2 + "%')"; 
		  
		  
		  sql= sql + (where.trim().equals("")?"":" AND  (" + where + ")") + " ORDER BY  updated_at desc ";
		  
		  if(!where.trim().equals("")) {
//			CachedRowSet res=getData(where, " updated_at desc ");
			
			  CachedRowSet res=executeQuery(sql);
		 	try {
				if(res.next()) {
					 
					customer=createOrderCustomerFromCrs(res);
						 
				}
				res.close();
			} catch (Exception e) {
				PosLog.write(this,"loadCustomers",e);
				throw e;
			}
		  }
		return customer; 
	}
//	/* (non-Javadoc)
//	 * @see com.indocosmo.pos.data.providers.PosDatabaseProvider#deleteData(java.lang.String)
//	 */
//	@Override
//	public int deleteData(String where) throws SQLException {
//	
//		int result=0;
//		
//		 String innerSql= "SELECT 1 as flag FROM order_customers cust1, order_customers cust2 " + 
//		 		" WHERE (cust1.phone=cust2.phone or (cust1.phone IS NULL AND cust2.phone IS NULL) ) and " + 
//		 		" (cust1.phone2=cust2.phone2 or (cust1.phone2 IS NULL AND cust2.phone2 IS NULL) ) " + 
//		 		" AND cust1.updated_at <cust2.updated_at AND  " + where.replace("order_id", "cust1.order_id");
//		 
//		 
//		 CachedRowSet crs=executeQuery(innerSql);
//		 if(crs.next()) {
//			 
//			final int flag= crs.getInt(1);
//			if (flag>0) {
//			 
//				final String sql= "delete from order_customers where "+ where ;
//				result =executeNonQuery(sql);
//			}
//		 }
//				 
//		crs.close();	 
//		return result;
//		
//	}
	
	
	
	public int deleteDuplicatedCustomers() throws SQLException {
		
		int result=0;
		
		String orderIds="";
		
		String sql= " SELECT DISTINCT cust1.order_id  as order_id FROM order_customers cust1, order_customers cust2  " + 
		 		"WHERE (cust1.phone=cust2.phone or (cust1.phone IS NULL AND cust2.phone IS NULL) ) and  " + 
		 		"(cust1.phone2=cust2.phone2 or (cust1.phone2 IS NULL AND cust2.phone2 IS NULL) )  AND  " + 
		 		"cust1.updated_at <cust2.updated_at AND   cust1.order_id not in   (select order_id from order_hdrs )";
		 
		CachedRowSet crs=executeQuery(sql);
		while(crs.next()) {
			
			orderIds=orderIds + (orderIds.trim().equals("")?"":",");
			orderIds=orderIds + "'" + crs.getString("order_id") + "'";
			 
		}
				 
		crs.close();
		
		if(!orderIds.trim().equals("")) {
		
			sql= "delete from order_customers where order_id in ("+ orderIds + ")" ;
			result =executeNonQuery(sql);
		}
		return result;
		
	}
	
}
