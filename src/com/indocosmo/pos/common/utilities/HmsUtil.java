/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosProcessMessageType;
import com.indocosmo.pos.data.beans.BeanOrderCustomer;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanPMSOrderPayment;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.providers.shopdb.PosOrderPaymentsProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderRefundProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderPaymentsProvider.PmsSyncStatus;
import com.indocosmo.pos.forms.PosMainMenuForm;
import com.indocosmo.pos.process.sync.DataToServerSyncThread;
import com.indocosmo.pos.process.sync.SynchronizeToServer;
import com.mysql.cj.xdevapi.JsonArray;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


/**
 * @author sandhya
 *
 */
public class HmsUtil {

	
	/*
	 *  To post unsynched data 
	 */
		public static void postPendingPaymentToPMS() {
			
			PosOrderPaymentsProvider paymentPvdr=new PosOrderPaymentsProvider();
			
			ArrayList<BeanPMSOrderPayment> paymentList;
			try {
				paymentList = paymentPvdr.getPendingPaymentPostToPMS();
				if (paymentList!=null && paymentList.size()>0) {
					
					for(BeanPMSOrderPayment paymentDet:paymentList) {
						
						if(paymentDet.isRepayment()) {
							
							HmsUtil.saveRefundDetais(paymentDet.getPaymentId(),
									paymentDet.getCustomerCode(), paymentDet.getPaidAmount());
						}else {
							
							HmsUtil.saveOrderDetais(paymentDet.getOrderId(),paymentDet.getPaymentId(), 
									paymentDet.getCustomerCode(), paymentDet.getPaidAmount());
						}
					}
				}
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} 
	public static boolean getGuestDetails(BeanOrderCustomer customer) throws Exception{
		boolean flag=false;
		try {
			
			String roomNo=customer.getCode();
			
			
		 	URL url = new URL( PosEnvSettings.getInstance().getWebHMSUrl() +  "/roomStatus/roomDetails");//your url i.e fetch data from .
		 	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
	 
//	        String input = "109";

			OutputStream os = conn.getOutputStream();
			os.write(customer.getName().getBytes());
			os.flush();

//			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//				throw new RuntimeException("Failed : HTTP error code : "
//					+ conn.getResponseCode());
//			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output;
			System.out.println("Output from Server .... \n");
			if ((output = br.readLine()) != null) {
				System.out.println("before Json===================>"+output);
				JSONObject jobj = (JSONObject)JSONSerializer.toJSON(output);
				System.out.println("after json===================>"+jobj);
				JSONObject jRoomDetObj = (JSONObject)JSONSerializer.toJSON(jobj.get("room_details").toString());
				System.out.println( jobj.get("room_details").toString());
				System.out.println( jRoomDetObj.get("guest_name").toString());
				if(jRoomDetObj.getBoolean("occupancy_status")){
				
					flag=true;
					customer.setName("[" + roomNo  + "] " + jRoomDetObj.get("guest_name").toString());
					customer.setAddress(jRoomDetObj.get("address").toString());
//					customer.s(jRoomDetObj.get("email").toString());
					customer.setPhoneNumber(jRoomDetObj.get("phone").toString());
					customer.setCountry(jRoomDetObj.get("nationality").toString());
					customer.setState(jRoomDetObj.get("state").toString());
				}
			}

			conn.disconnect();
	  } catch (Exception e) {
		  e.printStackTrace();
			throw new Exception("Failed to connect to server. Please check your internet connection.");

	  }
		return flag;
	}
	
	/*
	 * 
	 */
 
	public static void saveOrderDetais(BeanOrderHeader order) {	
		 
		if(order.getOrderPaymentItems().get(0).getPaymentMode().equals(PaymentMode.Company)) {
			
			saveOrderDetais(order.getOrderId(),
					order.getOrderPaymentItems().get(0).getId() ,  order.getCustomer().getCode(),
					order.getTotalAmount() + 
					order.getRoundAdjustmentAmount()-
					order.getBillDiscountAmount()); 
		}
	}

	
	public static void saveOrderDetais(String orderId,String paymentId,String roomNo,   double amount) {	
	 
		Thread thread = new Thread(){
		    public void run(){
				PosOrderPaymentsProvider paymentPvdr=new PosOrderPaymentsProvider();
		    	try {
					
				 	URL url = new URL(PosEnvSettings.getInstance().getWebHMSUrl()  + "/roomStatus/orderDetails");//your url i.e fetch data from .
				 	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/json");
			 
			        String input = "";

					JSONObject jsonRequest = SynchronizeToServer.synchronizeTableToHMS( 
							SynchronizeToServer.SyncTable.ORDER_HDRS
							.getCode(), "order_hdrs.order_id='"
									+ orderId + "'");
					
					if(jsonRequest!=null){
						jsonRequest.put("room_no", roomNo);
						jsonRequest.put("transaction_type", "POS");
						jsonRequest.put("transaction_amount", amount);
						input=jsonRequest.toString();
					}
 
					OutputStream os = conn.getOutputStream();
					os.write(input.getBytes());
					os.flush();

					BufferedReader br = new BufferedReader(new InputStreamReader(
							(conn.getInputStream())));
					String output;
					if ((output = br.readLine()) != null) {
						
						JSONObject jobj = (JSONObject)JSONSerializer.toJSON(output);
						
						 if(jobj.get("Status").toString().toLowerCase().equals("success"))
							 paymentPvdr.updatePmsSyncStatus(paymentId, PmsSyncStatus.Success);
						 else {
							 paymentPvdr.updatePmsSyncStatus(paymentId, PmsSyncStatus.Failed);
							 PosLog.write("PmsSync", "saveOrderDetais", output);
						 }
					}
					System.out.println("Output from Server .... \n");
					
					conn.disconnect();
			  } catch (Exception e) {

				e.printStackTrace();

			  }
		    }
		  };

		  thread.start();
		
	}

	
	
	public static void saveRefundDetais(String paymentId,String roomNo,double amount 
			) {	
	  
		Thread thread = new Thread(){
		    public void run(){
		    	PosOrderPaymentsProvider paymentPvdr=new PosOrderPaymentsProvider();
		    	try {
					
				 	URL url = new URL(PosEnvSettings.getInstance().getWebHMSUrl()  + "/roomStatus/orderDetails");//your url i.e fetch data from .
				 	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/json");
			 
			        String input = "";
					
			        JSONObject jsonRequest=createRefundJson(paymentId); 

					
					if(jsonRequest!=null){
						jsonRequest.put("room_no", roomNo);
						jsonRequest.put("transaction_type",  "POS-REFUND");
						jsonRequest.put("transaction_amount", amount);
						input=jsonRequest.toString();
					}
					
					
 
					OutputStream os = conn.getOutputStream();
					os.write(input.getBytes());
					os.flush();


					BufferedReader br = new BufferedReader(new InputStreamReader(
							(conn.getInputStream())));
					String output;
					System.out.println("Output from Server .... \n");
//			 
					paymentPvdr.updatePmsSyncStatus(paymentId, PmsSyncStatus.Success);
					conn.disconnect();
			  } catch (Exception e) {

				e.printStackTrace();

			  }
		    }
		  };

		  thread.start();
		
	}
 
	
	private static JSONObject createRefundJson(String paymentId){

		JSONObject jsonRequest = null;
	 	
		try {

		 
			PosOrderRefundProvider refundProvider=new PosOrderRefundProvider();
			
			CachedRowSet crsHdr=refundProvider.getRefundHeaderForHMS(paymentId);
			if (!crsHdr.next())
				return null;
			
			JSONObject jsonHdrObject = getAsJSON(crsHdr);
			JSONArray jsonHdrArray=new JSONArray();
			JSONArray jsonDtlArray=new JSONArray();
			
			CachedRowSet crsDtl=refundProvider.getRefundDetailsForHMS(paymentId);
			while(crsDtl.next()){
				
				jsonDtlArray.add(getAsJSON(crsDtl));
				
			}	 
			jsonHdrObject.put("order_refund", jsonDtlArray);
			jsonHdrArray.add(jsonHdrObject);
			
			jsonRequest = new JSONObject();
			jsonRequest.put("order_hdrs",jsonHdrArray);
			jsonRequest.put("shop_id", PosEnvSettings.getInstance()
					.getShop().getId());
			jsonRequest.put("station_id", PosEnvSettings.getInstance()
					.getStation().getId());

			PosLog.debug("JSON String:" + jsonRequest.toString());
			PosLog.debug("Request before encoding:" + jsonRequest.toString());
			

		} catch (Exception err) {
			PosLog.write("PostToHms", "RefundToHMS", err);

 		}
		return jsonRequest;
	} 
	private static synchronized JSONObject getAsJSON(CachedRowSet crsTable)
			throws Exception {

		 
		JSONObject jsonObj = new JSONObject();
		ResultSetMetaData metaData = crsTable.getMetaData();
		int numOfCols = metaData.getColumnCount();

		 
			for (int ctr = 1; ctr <= numOfCols; ctr++) {
				// Excluding "_identifier" column, all columns are added to JSON
				if (!metaData.getColumnName(ctr).equalsIgnoreCase("_identifier")) {
					jsonObj.put(metaData.getColumnName(ctr), crsTable
							.getString(crsTable.getMetaData().getColumnName(ctr)));
				}
			}
		 
		return jsonObj;
	}
	
}
