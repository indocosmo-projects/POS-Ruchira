/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.Function;

import com.indocosmo.pos.common.PosLog;

/**
 * @author sandhya
 *
 */
public final class PosSQLiteDBUtil {

	/**
	 * @return
	 */
	public static Function now(){

		return new Function(){
			/* (non-Javadoc)
			 * @see org.sqlite.Function#xFunc()
			 */
			@Override
			protected void xFunc() throws SQLException {

				String resultString=PosDateUtil.getDateTime(true);


				result(resultString);
			}
		};
	}

	/**
	 * @return
	 */
	public static Function concat(){

		return new Function(){
			/* (non-Javadoc)
			 * @see org.sqlite.Function#xFunc()
			 */
			@Override
			protected void xFunc() throws SQLException {

				String resultString="";

				if (args() < 2) 
					throw new SQLException("concat(expr1,expr1,...): Invalid argument count." + args());
				for(int index=0; index<args(); index++){
					resultString+=value_text(index);
				}
				result(resultString);
			}
		};
	}

	/**
	 * @param con
	 * @return
	 */
	public static Function getInvoiceNumber(final Connection con ){

		return new Function(){
			/* (non-Javadoc)
			 * @see org.sqlite.Function#xFunc()
			 */
			@Override
			protected void xFunc() throws SQLException {

				if (args() < 2) 
					throw new SQLException("getInvoiceNo(orderid,addInvoice): Invalid argument count." + args());

				final String orderid=value_text(0);
				final Boolean  addNewNo=(value_int(1)==1);
				int id=0;

				try {
					id= getInvoiceNumber(con,orderid);
					if (id==0  && addNewNo)	{
						insertInvoiceNumber(con, orderid);
						id= getInvoiceNumber(con,orderid);
						updateInvoiceNumber(con,orderid,id);
					}
				}catch (SQLException e) {

					PosLog.write(this, "GetInoiveNo", e);
					throw e;
				}



				result(id);

			}
		};
	}
	
	/**
	 * SQLIT function for generating the Invoice number
	 * @param con
	 * @param orderid
	 * @return
	 * @throws SQLException
	 */
	private static int getInvoiceNumber(final Connection con,String orderid ) throws SQLException{

		int id=0;
		final String sql="select id from pos_invoice where order_id='"+ orderid +"'";
		Statement statement=null;

		try {

			statement=con.createStatement();
			final ResultSet rs=statement.executeQuery(sql);
			if (rs!=null){

				if (rs.next()) {
					id= rs.getInt(1);
				}
				rs.close();
			}
		}catch (SQLException e) {

			throw e;
		}finally{

			if (statement !=null)
				statement.close();
		}

		return id;
	}
	
	/**
	 * SQLIT function for generating the Invoice number
	 * @param con
	 * @param orderid
	 * @throws SQLException
	 */
	private static void insertInvoiceNumber(final Connection con,String orderid ) throws SQLException{

		PreparedStatement prepStatment=null;

		try{

			final String sql="insert into pos_invoice (order_id) values(?)";

			prepStatment=con.prepareStatement(sql);
			prepStatment.setString(1, orderid);
			prepStatment.execute();
			
			
			
		}catch (SQLException e) {

			throw e;
		}finally{

			if (prepStatment !=null  )
				prepStatment.close();
		}

	}
	/**
	 * SQLIT function for generating the Invoice number
	 * @param con
	 * @param orderid
	 * @throws SQLException
	 */
	private static void updateInvoiceNumber(final Connection con,String orderid ,int invoiceNo) throws SQLException{

		PreparedStatement prepStatment=null;

		try{

			final String sql="UPDATE order_hdrs SET invoice_no=? WHERE order_id=?";

			prepStatment=con.prepareStatement(sql);
			prepStatment.setInt(1, invoiceNo);
			prepStatment.setString(2, orderid);
			prepStatment.execute();
			
			
			
		}catch (SQLException e) {

			throw e;
		}finally{

			if (prepStatment !=null  )
				prepStatment.close();
		}

	}
	/**
	 * to get queue no  from sqllite db
	 * parameter connection
	 * @param con
	 * @return
	 */
	public static Function getOrderQueueNumber(final Connection con ){

		return new Function(){
			/* (non-Javadoc)
			 * @see org.sqlite.Function#xFunc()
			 */
			@Override
			protected void xFunc() throws SQLException {

				if (args() <1) 
					throw new SQLException("getOrderQueueNumber(orderid,addNew): Invalid argument count." + args());

				final String orderid=value_text(0);
				int id=0;

				try {
					
					id= getOrderQueueNumber(con,orderid);
					if (id==0)	{
						
						insertOrderQueueNumber(con, orderid);
						id= getOrderQueueNumber(con,orderid);
					}
				}catch (SQLException e) {

					PosLog.write(this, "getOrderQueueNumber", e);
					throw e;
				}



				result(id);

			}
		};
	}

	/**
	 * parameter connenect and order id
	 * @param con
	 * @param orderid
	 * @return
	 * @throws SQLException
	 */
	private static int getOrderQueueNumber(final Connection con,String orderid ) throws SQLException{

		int id=0;
		final String sql="select id from order_queue where order_id='"+ orderid +"'";
		Statement statement=null;

		try {

			statement=con.createStatement();
			final ResultSet rs=statement.executeQuery(sql);
			if (rs!=null){

				if (rs.next()) {
					id= rs.getInt(1);
				}
				rs.close();
			}
		}catch (SQLException e) {

			throw e;
		}finally{

			if (statement !=null)
				statement.close();
		}

		return id;
	}


	/**
	 * parameter connenect and order id 
	 * @param con
	 * @param orderid
	 * @throws SQLException
	 */
	private static void insertOrderQueueNumber(final Connection con,String orderid ) throws SQLException{

		PreparedStatement prepStatment=null;

		try{

			final String sql="insert into order_queue (order_id) values(?)";
			prepStatment=con.prepareStatement(sql);
			prepStatment.setString(1, orderid);
			prepStatment.execute();
			
		}catch (SQLException e) {

			throw e;
		}finally{

			if (prepStatment !=null  )
				prepStatment.close();
		}

	}
	
	/**
	 * @param con
	 * @return
	 */
	public static Function resetOrderQueueNumber(final Connection con ){

		return new Function(){
			/* (non-Javadoc)
			 * @see org.sqlite.Function#xFunc()
			 */
			@Override
			protected void xFunc() throws SQLException {
				
				Statement statement=null;

				try {

					statement=con.createStatement();
					/*
					 *  Delete all que numbers other than open/partial
					 */
					statement.executeUpdate("DELETE from order_queue where id NOT in (SELECT queue_no from v_order_hdrs where status in (1,6));");
					/*
					 * update the squence number for order queue table
					 */
					statement.executeUpdate("UPDATE sqlite_sequence SET seq= ifnull((SELECT max(id)  from order_queue),0) where name='order_queue';");
				
				}catch (SQLException e) {

					PosLog.write(this, "resetOrderQueueNumber", e);
					throw e;
				}

			}
		};
	}	

	
	
	/**
	 * @param con
	 * @return
	 */
	public static Function resetKitchenQueueNumber(final Connection con ){

		return new Function(){
			/* (non-Javadoc)
			 * @see org.sqlite.Function#xFunc()
			 */
			@Override
			protected void xFunc() throws SQLException {
				
				Statement statement=null;

				try {

					statement=con.createStatement();
					/*
					 *  Delete all que numbers other than open/partial
					 */
					statement.executeUpdate("DELETE from order_kitchen_queue where order_id NOT in (SELECT order_id from order_hdrs where status in (1,6));");
					 
				
				}catch (SQLException e) {

					PosLog.write(this, "resetOrderQueueNumber", e);
					throw e;
				}

			}
		};
	}	

	
	
	
	/**
	 * to get queue no  from sqllite db
	 * parameter connection
	 * @param con
	 * @return
	 */
	public static Function getKitchenQueueNo(final Connection con ){

		return new Function(){
			/* (non-Javadoc)
			 * @see org.sqlite.Function#xFunc()
			 */
			@Override
			protected void xFunc() throws SQLException {

				if (args() <2) 
					throw new SQLException("getKitchenQueueNo(orderid,kitchenId ): Invalid argument count." + args());

				final String orderid=value_text(0);
				final int kitchenId=PosNumberUtil.parseIntegerSafely(value_text(1));
				int id=0;

				try {
					
					id= getKitchenQueueNo(con,orderid,kitchenId);
					 
				}catch (SQLException e) {

					PosLog.write(this, "getOrderQueueNumber", e);
					throw e;
				}



				result(id);

			}
		};
	}

	/**
	 * parameter connenect and order id
	 * @param con
	 * @param orderid
	 * @return
	 * @throws SQLException
	 */
	private static int getKitchenQueueNo(final Connection con,String orderid,int kitchenId) throws SQLException{

		int id=0;
		Statement statement=null;
		boolean checkExists=false;

		
		final String checkSql=" SELECT 1 as total_count  FROM order_dtls dtl " +
					" JOIN sale_items si on dtl.sale_item_id=si.id " +  
					" where   dtl.is_void=0 and dtl.is_printed_to_kitchen=0 " +  
					" and si.is_printable_to_kitchen=1 and dtl.kitchen_id=" + kitchenId + 
					" and dtl.order_id='"+ orderid +"' LIMIT 1";

		 
		
		try {
 
			statement=con.createStatement();

			final ResultSet rsCheck=statement.executeQuery(checkSql);
			if (rsCheck!=null){

				if (rsCheck.next()) {
					checkExists=rsCheck.getBoolean(1);
				}
				rsCheck.close();
			}
			
			if(checkExists){
				final String sql="	SELECT IFNULL(max(kitchen_queue_no),0) as queueNo FROM order_kitchen_queue where kitchen_id="+ kitchenId ;
				
				final ResultSet rs=statement.executeQuery(sql);
				if (rs!=null){
	
					if (rs.next()) {
						id= rs.getInt(1);
						id+=1;
						insertKitchenQueueNumber(con,orderid,kitchenId,id);
					}
					rs.close();
				}
			}
		}catch (SQLException e) {

			throw e;
		}finally{

			if (statement !=null)
				statement.close();
		}

		return id;
	}

	/**
	 * parameter connenect and order id 
	 * @param con
	 * @param orderid
	 * @throws SQLException
	 */
	private static void insertKitchenQueueNumber(final Connection con,String orderid ,int kitchenId, int queueNo) throws SQLException{

		PreparedStatement prepStatment=null;

		try{

			final String sql="insert INTO order_kitchen_queue(order_id,kitchen_id,kitchen_queue_no,printed_time) values(?,?,?,?)";
			prepStatment=con.prepareStatement(sql);
			prepStatment.setString(1, orderid);
			prepStatment.setInt(2, kitchenId);
			prepStatment.setInt(3, queueNo);
			prepStatment.setString(4, PosDateUtil.getDateTime());
			prepStatment.execute();
			
		}catch (SQLException e) {

			throw e;
		}finally{

			if (prepStatment !=null  )
				prepStatment.close();
		}

	}
}
