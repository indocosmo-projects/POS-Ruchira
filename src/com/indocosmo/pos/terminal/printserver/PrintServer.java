/**
 * 
 */
package com.indocosmo.pos.terminal.printserver;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.reports.receipts.PosReceipts;
import com.indocosmo.pos.server.core.EezDineServer;
import com.indocosmo.pos.server.interfaces.IEezDineServerActionListener;

/**
 * @author jojesh
 *
 */
public class PrintServer implements IEezDineServerActionListener{

	private static final String RETURN_JSON="{error_code:\"{ERROR_CODE}\",error_msg:\"{ERROR_MSG}\"}";
	private static final String REQUEST_PRINT_KITCHENRECEIPT="kitchenprint";
	private static final String REQUEST_PRINT_BILLRECEIPT="billprint";
	private static final String REQUEST_PRINT_TEST="testprint";
	private static PrintServer instance;
	private EezDineServer server;

	/**
	 * 
	 */
	private PrintServer() {
		server=new EezDineServer(this);
	}

	/**
	 * @return
	 */
	public static PrintServer getInstance() {

		if(instance==null)
			instance=new PrintServer();

		return instance;
	}

	/**
	 * @throws Exception
	 */
	public void start() throws Exception {
		
		
		server.start(PosEnvSettings.getInstance().getKotServerPort());
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.server.interfaces.IEezDineServerActionListener#processRequest(java.lang.String)
	 */
	@Override
	public synchronized String processRequest(String message) {

		String resp="NOTHING";
		try {
			resp = processPrintRequest(message);
		} catch (Exception e) {
			resp= handleException(e);
		}


		return resp;

	}

	/**
	 * @param e
	 */
	private String handleException(Exception e){

		String error="";
		if(e.getClass().getSimpleName().equalsIgnoreCase("HasNoItemsToPrintException")){
			error = RETURN_JSON.replace("{ERROR_CODE}", e.getClass().getSimpleName().toUpperCase())
					.replace("{ERROR_MSG}", "There is no items to print.");
		}else{
			
			error =RETURN_JSON.replace("{ERROR_CODE}", "UNKNOWNEXCEPTION")
					.replace("{ERROR_MSG}", e.getMessage());
			PosLog.write(this, "handleException", e);
		}

		return error;

	}

	/*
	 *  {
	 *  "json_ver":"1.0.0","app_ver":"2.0",
	 *  "shop_code":"SH",
	 *  "terminal_id":102000117,
	 *  "os":"ANDROID",
	 *  "request_type":"kitchenprint",
	 *  "request_data":{"orderno":"SH-W14-000019","printall":"false"}}
	 */
	public String processPrintRequest(String request) throws Exception {

		JSONObject objRequest=(JSONObject) new JSONParser().parse(request);
		String requestType=(String)objRequest.get("request_type");
		JSONObject objRequestData=(JSONObject) objRequest.get("request_data");

		switch (requestType) {
		case REQUEST_PRINT_KITCHENRECEIPT:
			printToKitchen(objRequestData);
			break;
		case REQUEST_PRINT_BILLRECEIPT:
			printBillReceipt(objRequestData);
			break;
			
		case REQUEST_PRINT_TEST:
			printTest();
			break;
		}

		return RETURN_JSON.replace("{ERROR_CODE}", "NONE").replace("{ERROR_MSG}", "Print request has been sent to printer.");
	}

	/**
	 * @throws Exception 
	 * 
	 */
	private void printTest() throws Exception {
		PosReceipts.testPrint();
		
	}

	/**
	 * @param objRequestData
	 * @throws Exception 
	 */
	private void printBillReceipt(JSONObject objRequestData) throws Exception {

		String orderNo=(String)objRequestData.get("orderno");
		PosReceipts.printBill(orderNo);

	}

	/**
	 * @param objRequestData
	 * @throws Exception
	 */
	private void printToKitchen(JSONObject objRequestData) throws Exception {

		String orderNo=(String)objRequestData.get("orderno");
		boolean isPrintall=((((String)objRequestData.get("printall")).equals("true"))?true:false);
		PosReceipts.printReceiptToKitchen(orderNo, isPrintall, 1);

	}

	/**
	 * 
	 */
	public void stop() {
		server.stop();

	}

}
