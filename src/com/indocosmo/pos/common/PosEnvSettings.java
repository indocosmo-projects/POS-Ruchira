/**
 * Use this class to keep the environment settings which are required through out the application
 */

package com.indocosmo.pos.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.indocosmo.pos.common.beans.settings.printing.BeanPrintDayEndReportSettings;
import com.indocosmo.pos.common.beans.settings.printing.BeanPrintSettings;
import com.indocosmo.pos.common.beans.settings.printing.BeanPrintShiftReportSettings;
import com.indocosmo.pos.common.beans.settings.printing.receipt.BeanPrintKitchenReceiptSettings;
import com.indocosmo.pos.common.beans.settings.printing.receipt.BeanPrintPaymentReceiptSettings;
import com.indocosmo.pos.common.beans.settings.printing.receipt.BeanPrintRefundReceiptSettings;
import com.indocosmo.pos.common.beans.settings.ui.BeanUISetting;
import com.indocosmo.pos.common.beans.settings.ui.mainmenu.BeanUIMainMenuSetting;
import com.indocosmo.pos.common.beans.settings.ui.mainmenu.BeanUIMenuListPanelSettings;
import com.indocosmo.pos.common.beans.settings.ui.mainmenu.BeanUITillFormSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderdetails.BeanUIOrderDetailSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIClassItemListPanelSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIItemListPanelSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIMiscOpionsPanelSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIOrderEntrySetting;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIOrderInfoFormSettings;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIOrderItemListPanelSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIOrderRetrieveFormSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIPaymentPanelSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIPrintPanelSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUISplitSettings;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIOrderEntrySetting.showSOInfo;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.neworder.BeanUINewOrderSettings;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.neworder.BeanUITableSelectionSettings;
import com.indocosmo.pos.common.beans.settings.ui.orderlist.BeanUIOrderListSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderlist.BeanUIOrderRefundSetting;
import com.indocosmo.pos.common.beans.settings.ui.terminal.BeanUITerminalTabsSetting;
import com.indocosmo.pos.common.enums.CardTypes;
import com.indocosmo.pos.common.enums.EnablePrintingOption;
import com.indocosmo.pos.common.enums.ItemEditMode;
import com.indocosmo.pos.common.enums.PosDbType;
import com.indocosmo.pos.common.enums.PosInvoicePrintFormat;
import com.indocosmo.pos.common.enums.PosQueueNoResetPolicy;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosPaymentOption;
import com.indocosmo.pos.common.enums.PosPropertyFileType;
import com.indocosmo.pos.common.enums.PosTerminalOperationalMode;
import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosPropertyFileUtil;
import com.indocosmo.pos.common.utilities.PosUtil;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanCardType;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanCurrency;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderRefund.RefundType;
import com.indocosmo.pos.data.beans.BeanSaleMenuItem;
import com.indocosmo.pos.data.beans.BeanShop;
import com.indocosmo.pos.data.beans.BeanTaxGlobalParam;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalInfo;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalSetting;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevEFTConfig;
import com.indocosmo.pos.data.providers.shopdb.PosAppVersionProvider;
import com.indocosmo.pos.data.providers.shopdb.PosBillParamProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCardTypesProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCashierShiftProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCurrencyProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShopProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSystemParamProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxParamProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevEFTConfigProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosTerminalSettingsProvider;

/**
 * @author jojesh-13.2
 *
 */


public final class PosEnvSettings {

	private static PosEnvSettings mPosEnvSettings=null;
	public final static String PROPERTY_FILE=PosPropertyFileType.APP.getFileName();
	public final static String UI_PROPERTY_FILE=PosPropertyFileType.UI.getFileName();
	public final static String PRINT_PROPERTY_FILE=PosPropertyFileType.PRINT.getFileName();
	public final static String LICENCE_FILE="pos_terminal.lic";

	private BeanUISetting uiSetting;
	private BeanPrintSettings printSettings;

	private PosDbType shopDBType;
	private String dbFolder;
	private String shopDBFile;
	private String terminalDBFile;
	private int maxSyncThreads;
	private int maxThreadRetryCount;
	private String webDataURL;
	private String syncToStart;
	private String syncSuccessful;
	private String syncInProgress;
	private String syncFailed;
	private String webArchiveURL;
	private String publishedDBFolder;
	private int publishDBRetainDays;
	private String downloadFolder;
	private String tmpDownloadedArchive;
	private int mServerDataUpdateCheckInterval;
	private ApplicationType applicationType;

	private Properties mProperties;
	private Properties mUIProperties;
	private Properties mPrintProperties;

	private String mLogPath="./logs";
	private String mExportPath;

	private String mBackupFolder ;
	private int mNumberOfBackupToKeep;
	private String mReportPath;
	private boolean logdebugmode=true;
	private String logfilename;
	private String mTerminalName;
	private BeanShop mShop;
	private BeanTerminalInfo mStation;
	//	private int mDecimalRounding=3;
	private BeanCurrency mCurrency;
	private BeanCashierShift mCashierShiftInfo;
	private BeanCashierShift mTillInfo;
	private BeanTaxGlobalParam mTaxGlobalParam;
	private BeanBillParam mBillParams;
	private Map<CardTypes, BeanCardType> mCardTypes;
	private PosOrderStatus mDefaultOrderStatus=PosOrderStatus.Closed;
	private String mPosDate;
	private BeanSaleMenuItem mMenu;
	private String mSecurityPassword;
	private int mRefundDays;
	private int mTxnDataDays;
	private int primaryScreen;
	private BeanDevEFTConfig mPosPaymentDeviceSettings;
	private int socketCTimeOut;
	private String mCacheFolder;
	private BeanTerminalSetting terminalSettings;
	private PosTerminalOperationalMode terminalOperationalMode;
	private PosQueueNoResetPolicy orderQueueResetPolicy;
	private PosQueueNoResetPolicy kitchenQueueResetPolicy;
	private int defaultMenuID;
	private boolean isDownSyncEnabled;
	private boolean isUpSyncEnabled;
	private boolean isAutoSyncEnabled;
	private boolean isDevelopmentMode;
	
	
	
	// Set to true when the terminal jar is used as support/lib jar for WS etc. 
	private boolean isServiceCall=false;

	/**
	 * To DO : db settings
	 */
	private String applicationVersion ="1.0.0";
	private String jsonVersion="1.0";

	private boolean useEncryptedPropertyFile;
	private boolean isDemoVersion=false;
	private static boolean hasInitialized=false;

	private boolean enableHMSIntegration=false;
	private String webHMSURL;

	private boolean startKotServer=false;
	private int kotServerPort; 
	/**
	 * Singleton model to avoid multiple instance of this class.
	 * use getInstance instead.
	 * DO NOT ALTER THE access modifier. Do the necessary initialization.
	 */
	private PosEnvSettings() {
		//		mProperties = new Properties();
		//		init();
	}

	/**
	 * 
	 * Loads and initializes the system settings
	 */
	public void init(){

		init(false,null);
	}
	/**
	 * 
	 * Loads and initializes the system settings
	 */
	public void init(String[] args){

		init(false,args);
	}
	
	
	/**
	 * @param isServiceCall
	 * @param args
	 */
	public void init(boolean isServiceCall,String[] args){

		this.isServiceCall=isServiceCall;

		if(hasInitialized) return;

		try {

			loadPosSettings();
			loadPosPrintProperties();
			loadPosUIProperites();
			initInvoicePrintFormat();
//			if(!isServiceCall) {
//				
//				
//			}
			loadDbSettings();
			updateAppVersion(args);
			loadEnvSettings_StageOne();
			
			
			hasInitialized=true;
		} catch (Exception e) {
			PosLog.write(this, "init", e);
			PosFormUtil.showErrorMessageBox(null,"Failed to initialize. Please contact Administrator");
			System.exit(1);
		}
	} 

	/**
	 * @param isServiceCall
	 */
	public void init(boolean isServiceCall){
		init(isServiceCall,null);
	} 
	
	/*
	 * 
	 */
	private void updateAppVersion(String[] args){
		
		try {
			
			final PosAppVersionProvider appversionProvider=new PosAppVersionProvider(); 
			appversionProvider.updateAppVersionFromFile();
			if(!PosEnvSettings.getInstance().isDevelopmentMode()){
				File file = new File(PosConstants.VERSION_INFO_FILE);
				file.delete();
			}
		} catch (Exception e) {
			
			PosLog.write(this,"updateAppVersion",e);
		}  
	
	}

	/**
	 * Loads the initial shop and terminal information.
	 * @throws Exception
	 */
	private void loadEnvSettings_StageOne() throws Exception{

		PosShopProvider shopProvider =new PosShopProvider();
		if(shopProvider.hasShopInformation()){
			mShop=shopProvider.getShop();
			PosTerminalSettingsProvider settingProvider=new PosTerminalSettingsProvider();
			terminalSettings=settingProvider.getTerminalSetting();

			if(terminalSettings!=null){

				if (!isServiceCall)	
					setUiPropertiesForTerminal();
				mStation=terminalSettings.getTerminalInfo();
				mStation.setOperationalMode(terminalOperationalMode);

			}
	 
			mCardTypes=PosCardTypesProvider.getInstance().getCardTypes();
		}
	}

	/**
	 * Loads the master data.
	 * @throws Exception
	 */
	public void loadEnvSettings_StageTwo() throws Exception{

		mTaxGlobalParam=PosTaxParamProvider.getInstance().getTaxParam();
		mBillParams=PosBillParamProvider.getInstance().getBillParam();
		mCurrency= (new PosCurrencyProvider()).getBaseCurrency();
		mPosPaymentDeviceSettings = (new PosDevEFTConfigProvider()).getDeviceConfiguration();
		mTillInfo=new PosCashierShiftProvider().getTillOpenCashierShift();

	}

	/**
	 * Singleton method to return the object of this class.
	 *  DO NOT ALTER THE CODE.
	 * @return: returns the same object always.
	 */
	public static synchronized PosEnvSettings getInstance(){

		return getInstance(null);
		
	}
	/**
	 * Singleton method to return the object of this class.
	 *  DO NOT ALTER THE CODE.
	 * @return: returns the same object always.
	 */
	public static synchronized PosEnvSettings getInstance(String[] args){

		 
		return getInstance(false,args);
	}
	
	/**
	 * Singleton method to return the object of this class.
	 *  DO NOT ALTER THE CODE.
	 * @return: returns the same object always.
	 */
	public static synchronized PosEnvSettings getInstance(boolean serviceCall,String[] args){

		
		if(mPosEnvSettings==null){
			mPosEnvSettings=new PosEnvSettings();
			
			if(args!=null){
				for(String argLine:args){
					if (argLine.trim().equals("--dev")){
						mPosEnvSettings.setDevelopmentMode(true);
						break;
					}
				}
			}
			if(!hasInitialized)
				mPosEnvSettings.init(serviceCall,args);
		}
		return mPosEnvSettings;
	}
	
	/* 
	 * This is a singleton class. so we must avoid cloning the objects.
	 *  DO NOT ALTER THE CODE.
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	/**
	 * @throws IOException
	 */
	private void loadPosSystemProperties() throws IOException{


		mProperties = new Properties();
		InputStream is = null;
		is = new FileInputStream(PROPERTY_FILE);
		mProperties.load(is);
		if(is!=null)
			is.close();

	}


	/**
	 * 
	 */
	private void loadPosUIProperties(){

		mUIProperties= new Properties();
		InputStream is = null;
		try{
			if(useEncryptedPropertyFile)
				is =PosPropertyFileUtil.decryptPropertyFile(UI_PROPERTY_FILE);
			else
				is=new FileInputStream(UI_PROPERTY_FILE);
			mUIProperties.load(is);
			if(is!=null)
				is.close();
		}catch(Exception e){
			PosLog.write(this, "loadPosUIProperties",e);
		}
	}

	/**
	 * @throws IOException
	 */
	private void loadPosSettings() throws IOException{

		loadPosSystemProperties();

		mReportPath=mProperties.getProperty("reportfolder");
		mExportPath=mProperties.getProperty("exportFolder");
		mLogPath=mProperties.getProperty("logFolder","./logs");
		socketCTimeOut =Integer.parseInt(mProperties.getProperty("socketCreationTO"));
		mSecurityPassword=mProperties.getProperty("securitypassword");
		mRefundDays=Integer.parseInt(mProperties.getProperty("refundDays"));
		mTxnDataDays=Integer.parseInt(mProperties.getProperty("daysToKeepOrders"));
		publishedDBFolder = mProperties.getProperty("publishDBFolder");
		publishDBRetainDays = Integer.parseInt(mProperties.getProperty("publishDBRetainDays"));
		downloadFolder = mProperties.getProperty("downloadFolder");
		tmpDownloadedArchive = mProperties.getProperty("tmpDownloadedArchive");
		shopDBType = PosDbType.get(mProperties.getProperty("shopDBType"));
		dbFolder = mProperties.getProperty("sqliteDBFolder");
		shopDBFile = mProperties.getProperty("sqliteShopDB");
		terminalDBFile = mProperties.getProperty("sqliteTerminalDB");
		maxSyncThreads = Integer.parseInt(mProperties.getProperty("maxSyncThreads"));
		maxThreadRetryCount = Integer.parseInt(mProperties.getProperty("maxThreadRetryCount"));
		webDataURL = mProperties.getProperty("webDataURL");
		syncToStart = mProperties.getProperty("syncToStart");
		syncSuccessful = mProperties.getProperty("syncSuccessful");
		syncInProgress = mProperties.getProperty("syncInProgress");
		syncFailed = mProperties.getProperty("syncFailed");
		webArchiveURL = mProperties.getProperty("webArchiveURL");
		logdebugmode = (mProperties.getProperty("logDebugMode").trim().equalsIgnoreCase("true")?true:false);
		
		
		isDownSyncEnabled = (mProperties.getProperty("downSyncEnabled","true").trim().equalsIgnoreCase("true")?true:false);
		isUpSyncEnabled = (mProperties.getProperty("upSyncEnabled","true").trim().equalsIgnoreCase("true")?true:false);
		isAutoSyncEnabled = (mProperties.getProperty("autoSyncEnabled","true").trim().equalsIgnoreCase("true")?true:false);
		mServerDataUpdateCheckInterval=Integer.parseInt(mProperties.getProperty("serverDataUpdateCheckInterval","1"))*60000;
		
		logfilename = mProperties.getProperty("logFilename","pos-log.log");
		mCacheFolder = mProperties.getProperty("cacheFolder", "./cache"); //Default value is hard-coded
		useEncryptedPropertyFile = (mProperties.getProperty("useEncryptedPropertyFile","true").trim().equalsIgnoreCase("true")?true:false);
		defaultMenuID=Integer.parseInt(mProperties.getProperty("defaultMenuID","-1"));
		terminalOperationalMode=PosTerminalOperationalMode.get(Integer.parseInt(mProperties.getProperty("operationalMode","0")));
		orderQueueResetPolicy=PosQueueNoResetPolicy.get(Integer.parseInt(mProperties.getProperty("resetOrderQueuePolicy","0")));
		kitchenQueueResetPolicy=PosQueueNoResetPolicy.get(Integer.parseInt(mProperties.getProperty("resetKitchenQueuePolicy","0")));
		primaryScreen=(isServiceCall?0:PosUtil.validatePrimaryScreen(Integer.parseInt(mProperties.getProperty("primaryDisplayScreen","0"))));
	
		applicationType =ApplicationType.get(Integer.parseInt(mProperties.getProperty("applicationType","0")));
		if(applicationType.equals(ApplicationType.StandAlone)){
			isDownSyncEnabled = false;
			isUpSyncEnabled = false;
			isAutoSyncEnabled = false;
		}
		
		
		mBackupFolder=mProperties.getProperty("backupFolder","./backup");
		mNumberOfBackupToKeep = Integer.parseInt(mProperties.getProperty("numberOfBackupToKeep","5"));
		
		webHMSURL = mProperties.getProperty("webHMSURL");
		enableHMSIntegration = (mProperties.getProperty("enableHMSIntegration","false").trim().equalsIgnoreCase("true")?true:false);
	
		startKotServer = (mProperties.getProperty("start_kot_server","false").trim().equalsIgnoreCase("true")?true:false);
		kotServerPort = Integer.parseInt( mProperties.getProperty("kot_server_port","0"));
	}

	/**
	 * 
	 */
	private void loadPosPrintProperties(){

		loadPrintProperties();

		/*** Load receipt printing settings***/
		final BeanPrintPaymentReceiptSettings paymentSetting=loadPaymentReceiptSettings();
		final BeanPrintRefundReceiptSettings refundSetting=loadRefundReceiptSettings();
		final BeanPrintKitchenReceiptSettings kitchenSetting=loadKitchenReceiptSettings();

		printSettings=new BeanPrintSettings();

		printSettings.setPrintingFormat(
				mPrintProperties.getProperty(BeanPrintSettings.PS_PRINTING_FORMAT,"STD"));

	
		printSettings.setReceiptPrintingAtPayment(
				EnablePrintingOption.get(
						Integer.parseInt(mPrintProperties.getProperty(BeanPrintSettings.PS_RECEIPT_PRINTING_AT_PAYMENT,"1"))));

		printSettings.setReceiptPrintingAtRefund(
				EnablePrintingOption.get(
						Integer.parseInt(mPrintProperties.getProperty(BeanPrintSettings.PS_RECEIPT_PRINTING_AT_REFUND,"1"))));

		printSettings.setPrintToKitchenAtPayment(
				EnablePrintingOption.get(
						Integer.parseInt(mPrintProperties.getProperty(BeanPrintSettings.PS_KITCHEN_PRINTING_AT_PAYMENT,"0"))));

		printSettings.setPrintToKitchenAtParking(
				EnablePrintingOption.get(
						Integer.parseInt(mPrintProperties.getProperty(BeanPrintSettings.PS_KITCHEN_PRINTING_AT_PARKING,"1"))));

		printSettings.setKitchenPrintingToCounter(
				mPrintProperties.getProperty(BeanPrintSettings.PS_KITCHEN_PRINTING_TO_COUNTER,"false")
				.trim().equalsIgnoreCase("true")?true:false);
		printSettings.setBillPrintingAtParking(
				 EnablePrintingOption.get(
						 Integer.parseInt(mPrintProperties.getProperty(BeanPrintSettings.PS_BILL_PRINTING_AT_PARKING,"1"))));
		
		printSettings.setBillPrintAtParkingForServices(
				mPrintProperties.getProperty(BeanPrintSettings.PS_BILL_PRINTING_AT_PARKING_FOR_SERVICES,""));
		
		

		printSettings.setPrintSummaryAtShitClosing(
				EnablePrintingOption.get(
						Integer.parseInt(mPrintProperties.getProperty(BeanPrintSettings.PS_SUMMARY_PRINTING_AT_SHIFT_CLOSING,"1"))));

		printSettings.setPrintDayEndReportAtDayEnd(
				EnablePrintingOption.get(
						Integer.parseInt(mPrintProperties.getProperty(BeanPrintSettings.PS_DAYEND_REPORT_PRINTING_AT_DAYEND,"1"))));

		printSettings.setPrintReshitoAtPayment(
				EnablePrintingOption.get(
						Integer.parseInt(mPrintProperties.getProperty(BeanPrintSettings.PS_RESHITO_PRINTING_AT_PAYMENT,"0"))));

		printSettings.setPrintingReshitoEnabled(
				(mPrintProperties.getProperty(BeanPrintSettings.PS_ENABLE_RESHITO_PRINTING,"false").trim().equalsIgnoreCase("true")?true:false));

		printSettings.setLanguageSwitchingAllowed(
				(mPrintProperties.getProperty(BeanPrintSettings.PS_ALLOW_LANGUAGE_SWITCHING,"false").trim().equalsIgnoreCase("true")?true:false));

		printSettings.setBarcodePrintingEnabled(
				(mPrintProperties.getProperty(BeanPrintSettings.PS_BARCODE_PRINTING,"false").trim().equalsIgnoreCase("true")?true:false));

		printSettings.setPaymentReceiptSettings(paymentSetting);
		printSettings.setRefundReceiptSettings(refundSetting);;
		printSettings.setKitchenReceiptSettings(kitchenSetting);
		printSettings.setShiftReportSettings(loadShiftReportSettings());
		printSettings.setDayEndReportSettings(loadDayEndReportSettings());
//		printSettings.setBarcodeSettings(loadBarcodeSettings());

		
		
	
		 
	}
	
	/*
	 * 
	 */
	private void initInvoicePrintFormat(){
		
		printSettings.setInvoiceFormats(PosOrderServiceTypes.TAKE_AWAY , 
				PosInvoicePrintFormat.get(
						Integer.parseInt(mPrintProperties.getProperty(BeanPrintSettings.PS_INVOICE_PRINTING_FORMAT_TAKEAWAY,"1"))));
		
		printSettings.setInvoiceFormats(PosOrderServiceTypes.HOME_DELIVERY, 
				PosInvoicePrintFormat.get(
						Integer.parseInt(mPrintProperties.getProperty(BeanPrintSettings.PS_INVOICE_PRINTING_FORMAT_HOMEDELIVERY,"1"))));
		
		printSettings.setInvoiceFormats(PosOrderServiceTypes.TABLE_SERVICE, 
				PosInvoicePrintFormat.get(
						Integer.parseInt(mPrintProperties.getProperty(BeanPrintSettings.PS_INVOICE_PRINTING_FORMAT_TABLE,"1"))));
		
		printSettings.setInvoiceFormats(PosOrderServiceTypes.WHOLE_SALE, 
				PosInvoicePrintFormat.get(
						Integer.parseInt(mPrintProperties.getProperty(BeanPrintSettings.PS_INVOICE_PRINTING_FORMAT_WHOLESALE,"1"))));
		
		printSettings.setInvoiceFormats(PosOrderServiceTypes.SALES_ORDER, 
				PosInvoicePrintFormat.get(
						Integer.parseInt(mPrintProperties.getProperty(BeanPrintSettings.PS_INVOICE_PRINTING_FORMAT_SALESORDER,"1"))));
	}
	/*
	 * 
	 */
	private BeanPrintDayEndReportSettings loadDayEndReportSettings(){

		BeanPrintDayEndReportSettings dayEndReportSettings=new BeanPrintDayEndReportSettings();
		dayEndReportSettings.setDayEndReportType(
				Integer.valueOf(mPrintProperties.getProperty(BeanPrintDayEndReportSettings.PS_DAY_END_REPORT_TYPE,"1")));

		return dayEndReportSettings;
	}
	
	
	 
	/*
	 * 
	 */
	private BeanPrintShiftReportSettings loadShiftReportSettings(){

		BeanPrintShiftReportSettings shiftReportSettings=new BeanPrintShiftReportSettings();

		shiftReportSettings.setPrintVoidItemsInPaymentSummary(
				mPrintProperties.getProperty(BeanPrintShiftReportSettings.PS_VOID_ITEMS_IN_PAYMENT_SUMMARY,"false").
				trim().equalsIgnoreCase("true")?true:false);

		return shiftReportSettings;
	}



	/**
	 * @return
	 */
	private BeanPrintKitchenReceiptSettings loadKitchenReceiptSettings() {

		BeanPrintKitchenReceiptSettings printKitchenReceiptSettings=new BeanPrintKitchenReceiptSettings();

		
		printKitchenReceiptSettings.setFontName(
				(mPrintProperties.getProperty(BeanPrintKitchenReceiptSettings.PS_FONT_NAME,"Arial")));

		printKitchenReceiptSettings.setFontSize(
				Integer.valueOf(mPrintProperties.getProperty(BeanPrintKitchenReceiptSettings.PS_FONT_SIZE,"8")));

		printKitchenReceiptSettings.setFontStyle(
				Integer.valueOf(mPrintProperties.getProperty(BeanPrintKitchenReceiptSettings.PS_FONT_STYLE,"0")));

		
		printKitchenReceiptSettings.setItemDetailFontName(
				(mPrintProperties.getProperty(BeanPrintKitchenReceiptSettings.PS_ITEM_DETAIL_FONT_NAME,"Arial")));

		printKitchenReceiptSettings.setItemDetailFontSize(
				Integer.valueOf(mPrintProperties.getProperty(BeanPrintKitchenReceiptSettings.PS_ITEM_DETAIL_FONT_SIZE,"8")));

		printKitchenReceiptSettings.setItemDetailFontStyle(
				Integer.valueOf(mPrintProperties.getProperty(BeanPrintKitchenReceiptSettings.PS_ITEM_DETAIL_FONT_STYLE,"0")));


		printKitchenReceiptSettings.setNoOfBlankLinesHdr(
				Integer.valueOf(mPrintProperties.getProperty(BeanPrintKitchenReceiptSettings.PS_NO_BLANK_LINES_HDR,"1")));

		printKitchenReceiptSettings.setNoOfBlankLinesFooter( 
				Integer.valueOf(mPrintProperties.getProperty(BeanPrintKitchenReceiptSettings.PS_NO_BLANK_LINES_FOOTER,"1")));

		printKitchenReceiptSettings.setShowRelatedKitchen( 
				(mPrintProperties.getProperty(BeanPrintKitchenReceiptSettings.PS_SHOW_RELATED_KITCHEN,"false")
						.trim().equalsIgnoreCase("true")?true:false));

		printKitchenReceiptSettings.setShowBarcode( 
				(mPrintProperties.getProperty(BeanPrintKitchenReceiptSettings.PS_SHOW_BARCODE,"true")
						.trim().equalsIgnoreCase("true")?true:false));

		printKitchenReceiptSettings.setCustomerInfoPrintingServices( 
				mPrintProperties.getProperty(BeanPrintKitchenReceiptSettings.PS_CUSTOMER_PRINTING_FOR_SERVICES,"2"));
		
		printKitchenReceiptSettings.setShowKitchenQueuePrefix(
				mPrintProperties.getProperty(BeanPrintKitchenReceiptSettings.PS_SHOW_KITCHEN_QUEUE_PREFIX,"true")
				.trim().equalsIgnoreCase("true")?true:false);
		
		return printKitchenReceiptSettings;
	}

	/**
	 * @return
	 */
	private BeanPrintRefundReceiptSettings loadRefundReceiptSettings() {

		BeanPrintRefundReceiptSettings printRefundReceiptSettings=new BeanPrintRefundReceiptSettings();

		printRefundReceiptSettings.setNoCopies(
				Integer.valueOf(mPrintProperties.getProperty(BeanPrintRefundReceiptSettings.PS_NO_COPIES,"1")));;

				return printRefundReceiptSettings;
	}


	/**
	 * @return
	 */
	private BeanPrintPaymentReceiptSettings loadPaymentReceiptSettings() {

		BeanPrintPaymentReceiptSettings printPaymentReceiptSettings=new BeanPrintPaymentReceiptSettings();

		printPaymentReceiptSettings.setModifiersVisible(
				(mPrintProperties.getProperty(BeanPrintPaymentReceiptSettings.PS_SHOW_MODIFIERS,"false")
						.trim().equalsIgnoreCase("true")?true:false));

		printPaymentReceiptSettings.setItemDetailFontName(
				(mPrintProperties.getProperty(BeanPrintPaymentReceiptSettings.PS_ITEM_DETAIL_FONT_NAME,"Arial")));

		printPaymentReceiptSettings.setItemDetailFontSize(
				Integer.valueOf(mPrintProperties.getProperty(BeanPrintPaymentReceiptSettings.PS_ITEM_DETAIL_FONT_SIZE,"8")));

		printPaymentReceiptSettings.setItemDetailFontStyle(
				Integer.valueOf(mPrintProperties.getProperty(BeanPrintPaymentReceiptSettings.PS_ITEM_DETAIL_FONT_STYLE,"0")));
 
		
		printPaymentReceiptSettings.setShowItemRemarks(
				(mPrintProperties.getProperty(BeanPrintPaymentReceiptSettings.PS_SHOW_ITEM_REMARKS,"true")
						.trim().equalsIgnoreCase("true")?true:false));
		
		printPaymentReceiptSettings.setCustomerInfoPrintingServices( 
				mPrintProperties.getProperty(BeanPrintPaymentReceiptSettings.PS_CUSTOMER_PRINTING_FOR_SERVICES,"2"));
	
		printPaymentReceiptSettings.setReceiptType(
				Integer.valueOf(mPrintProperties.getProperty(BeanPrintPaymentReceiptSettings.PS_RECEIPT_TYPE,"1")));
		
		printPaymentReceiptSettings.setItemGroupingMethod(
				Integer.valueOf(mPrintProperties.getProperty(BeanPrintPaymentReceiptSettings.PS_RECEIPT_ITEM_GROUPING,"0")));
	
		return printPaymentReceiptSettings;
	}


	/**
	 * 
	 */
	private void loadPrintProperties() {

		mPrintProperties= new Properties();
		InputStream is = null;
		try{
			if(useEncryptedPropertyFile)
				is =PosPropertyFileUtil.decryptPropertyFile(PRINT_PROPERTY_FILE);
			else
				is=new FileInputStream(PRINT_PROPERTY_FILE);
			mPrintProperties.load(is);
			if(is!=null)
				is.close();
		}catch(Exception e){
			PosLog.write(this, "loadPrintProperties",e);
		}

	}


	/**
	 * 
	 */
	private void loadPosUIProperites(){

		loadPosUIProperties();

		

		/**Set Main Menu Settings**/
		final BeanUIMainMenuSetting mainMenuSettings = loadMainMenuSettings();

		/** Load the order entry print panel settings **/
		final BeanUIPrintPanelSetting printPanelSetting=loadPrintPanelUISettings();

		/** Load order entry payment panel settings **/
		final BeanUIPaymentPanelSetting paymentSetting=loadPaymentPanelUISettings();

		/** Load order entry misc panel settings **/
		final BeanUIMiscOpionsPanelSetting miscPanelSetting=loadMiscPanelSettings();

		/** Load order entry item list panel settings**/
		final BeanUIItemListPanelSetting itemListPanelSettings=loadItemListPanelUISettings();

		/** Load order entry class item list panel settings **/
		final BeanUIClassItemListPanelSetting classItemListPanelSetting=loadClassItemListPanelSettings();

		/** Load Order item list panel settings**/
		final BeanUIOrderItemListPanelSetting orderItemListPanelSetting=loadOrderItemListPanelSettings();

		/** Load the new order ui settings **/
		final BeanUINewOrderSettings newOrderSettings=loadNewOrderUISettings();

		/** Load the split ui settings **/
		final BeanUISplitSettings splitSettings=loadSplitUISettings();

		/** Load the order retrieve ui settings **/
		final BeanUIOrderRetrieveFormSetting orderRetrieveFormSettings=loadOrderRetrieveFormSettings();

		/** Load the order info ui settings **/
		final BeanUIOrderInfoFormSettings orderInfoFormSettings=loadOrderInfoFormSettings();

		/** Set order entry ui settings **/
		final BeanUIOrderEntrySetting orderEntrySettings=new BeanUIOrderEntrySetting();

		orderEntrySettings.setPaymentPanelSetting(paymentSetting);
		orderEntrySettings.setMiscOptionsPanelSetting(miscPanelSetting);
		orderEntrySettings.setPrintePanelSettings(printPanelSetting);
		orderEntrySettings.setItemListPanelSetting(itemListPanelSettings);
		orderEntrySettings.setClassItemListPanelSetting(classItemListPanelSetting);
		orderEntrySettings.setOrderItemListPanelSetting(orderItemListPanelSetting);
		orderEntrySettings.setNewOrderUISetting(newOrderSettings);
		orderEntrySettings.setSplitUISettings(splitSettings);
		orderEntrySettings.setOrderRetrieveFormSetting(orderRetrieveFormSettings);
		orderEntrySettings.setOrderInfoFormSettings(orderInfoFormSettings);

		orderEntrySettings.setPaymentOption(
				PosPaymentOption.get(Integer.parseInt(mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_PAYMENT_METHOD,"1"))));

		orderEntrySettings.setDefaultCustomerTakeAway(
				mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_TAKE_AWAY_DEFAULT_CUSTOMER,PosCustomerProvider.DEF_CUST_CODE).trim().toUpperCase());
		orderEntrySettings.setDefaultCustomerHomeDelivery(
				mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_HOME_DELIVERY_DEFAULT_CUSTOMER,PosCustomerProvider.DEF_CUST_CODE).trim().toUpperCase());
		orderEntrySettings.setDefaultCustomerTableService(
				mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_TABLE_DEFAULT_CUSTOMER,PosCustomerProvider.DEF_CUST_CODE).trim().toUpperCase());
		orderEntrySettings.setDefaultCustomerWholesale(
				mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_WHOLESALE_DEFAULT_CUSTOMER,PosCustomerProvider.DEF_CUST_CODE).trim().toUpperCase());
		orderEntrySettings.setDefaultCustomerSO(
				mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_SO_DEFAULT_CUSTOMER,PosCustomerProvider.DEF_CUST_CODE).trim().toUpperCase());
		
		orderEntrySettings.setShowEditWindowForOpenItemsOnAdd(
				mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_SHOW_EDIT_UI_FOR_OPEN_ITEMS_ON_ADD,"true").trim().equalsIgnoreCase("true")?true:false);
		orderEntrySettings.setShowEditWindowForItemsWithChoiceOnAdd(
				mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_SHOW_EDIT_UI_FOR_ITEMS_WITH_CHOICE_ON_ADD,"true").trim().equalsIgnoreCase("true")?true:false);
		orderEntrySettings.setShowEditWindowForItemsWithComboOnAdd(
				mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_SHOW_EDIT_UI_FOR_ITEMS_WITH_COMBO_ON_ADD,"true").trim().equalsIgnoreCase("true")?true:false);
		orderEntrySettings.setShowEditWindowForItemsWithModifiersOnAdd(
				mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_SHOW_EDIT_UI_FOR_ITEMS_WITH_MODIFIERS_ON_ADD,"true").trim().equalsIgnoreCase("true")?true:false);
		

		orderEntrySettings.setConfirmVoid(
				mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_CONFIRM_VOID,"true").trim().equalsIgnoreCase("true")?true:false);

		orderEntrySettings.setConfirmTrayWeightUpdate(
				mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_CONFIRM_TRAY_WEIGHT_UPDATE,"true").trim().equalsIgnoreCase("true")?true:false);

		orderEntrySettings.setEnableItemDelete(
				mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_ENABLE_ITEM_DELETE,"false").trim().equalsIgnoreCase("true")?true:false);

		
		orderEntrySettings.setQuickEditMode(ItemEditMode.get(Integer.parseInt(
				(mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_QUICK_EDIT_MODE,"1")))));
		
		orderEntrySettings.setShowQuickEdit(
				(mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_QUICK_EDIT,"false").trim().equalsIgnoreCase("true")?true:false));

		orderEntrySettings.setAllowCustomerEdit(
				(mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_ALLOW_CUSTOMER_EDIT,"false").trim().equalsIgnoreCase("true")?true:false));
		
		orderEntrySettings.setShowCustomerGSTDetails(
				(mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_SHOW_GST_DET_IN_CUSTOMER_INFO,"true").trim().equalsIgnoreCase("true")?true:false));
	
		orderEntrySettings.setShowSalesOrderDetail(
				showSOInfo.get(Integer.parseInt(mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_SHOW_SO_DETAILS,"2"))));

		
		/** Set terminal tabs settings **/
		final BeanUITerminalTabsSetting terminaltabsSettings = loadTerminalTabsSettings();

		/**Set Order Detail Setting**/
		final BeanUIOrderDetailSetting orderDetailSetting =loadOrderDetailUISetting();

		/**Set Order List Setting**/
		final BeanUIOrderListSetting orderListSetting =loadOrderListUISetting() ;

		/** set pos ui settings **/
		uiSetting=new BeanUISetting();
		uiSetting.setOrderEntryUISettings(orderEntrySettings);
		uiSetting.setMainMenuUISettings(mainMenuSettings);
		uiSetting.setOrderDetailSettings(orderDetailSetting);
		uiSetting.setOrderListSettings(orderListSetting);

		uiSetting.setLightBox(
				(mUIProperties.getProperty(BeanUISetting.PS_SHOW_LIGHT_BOX,"true").trim().equalsIgnoreCase("true")?true:false));
		uiSetting.setShowKeyStrokeCharOnButtons(
				(mUIProperties.getProperty(BeanUISetting.PS_SHOW_KEY_STROKE_CHAR_ON_BUTTONS,"false").trim().equalsIgnoreCase("true")?true:false));
		uiSetting.setApplyWindowRenderingHack(
				mUIProperties.getProperty(BeanUISetting.PS_APPLY_WINDOW_RENDERING_HACK,"false").trim().equalsIgnoreCase("true")?true:false);
		uiSetting.setTerminalUISettings(terminaltabsSettings);

		uiSetting.setServiceHomeDelTitle(
				mUIProperties.getProperty(BeanUISetting.PS_ORDER_SERVICE_SELECTION_CAPTION_HOMEDELIVERY,"Home Delivery"));
		uiSetting.setServiceTableTitle(
				mUIProperties.getProperty(BeanUISetting.PS_ORDER_SERVICE_SELECTION_CAPTION_TABLE,"Dining"));
		uiSetting.setServiceTakeAwayTitle(
				mUIProperties.getProperty(BeanUISetting.PS_ORDER_SERVICE_SELECTION_CAPTION_TAKEAWAY,"Take Away"));
		uiSetting.setServiceWholeSaleTitle(
				mUIProperties.getProperty(BeanUISetting.PS_ORDER_SERVICE_SELECTION_CAPTION_WHOLESALE,"Whole Sale"));

		uiSetting.setServiceSalesOrderTitle(
				mUIProperties.getProperty(BeanUISetting.PS_ORDER_SERVICE_SELECTION_CAPTION_SALESORDER,"Sales Order"));

		uiSetting.setEnabledOrderServices(
				mUIProperties.getProperty(BeanUISetting.PS_ORDER_ENABLED_SERVICES,"1,2,3"));

		uiSetting.setBilledOrderEditAuthenticationRequired(
				mUIProperties.getProperty(BeanUISetting.PS_AUTHENTICATE_BILLED_ORDER_EDIT,"false")
				.trim().equalsIgnoreCase("true")?true:false);

		uiSetting.setParkedOrderEditAuthenticationRequired(
				mUIProperties.getProperty(BeanUISetting.PS_AUTHENTICATE_PARKED_ORDER_EDIT,"false")
				.trim().equalsIgnoreCase("true")?true:false);
		
		uiSetting.setQuickPayMode(
				mUIProperties.getProperty(BeanUISetting.PS_QUICK_PAY_MODES,""));

		uiSetting.setUseOrderQueueNo(
				mUIProperties.getProperty(BeanUISetting.PS_USE_QUEUE_NO,"false")
				.trim().equalsIgnoreCase("true")?true:false);

		uiSetting.setLabelFont(
				mUIProperties.getProperty(BeanUISetting.PS_LABEL_FONT,"Arial").trim() );

		uiSetting.setWindowTitleFont(
				mUIProperties.getProperty(BeanUISetting.PS_WINDOW_TITLE_FONT,"Arial").trim() );

		uiSetting.setTextAreaFont(
				mUIProperties.getProperty(BeanUISetting.PS_TEXT_AREA_FONT,"Arial").trim() );

		uiSetting.setQueueNoPrefix(mUIProperties.getProperty(BeanUISetting.PS_QUEUE_NO_PREFIX,""));
		uiSetting.setInvoiceNoPrefix(mUIProperties.getProperty(BeanUISetting.PS_INVOICE_NO_PREFIX,""));
	
	}

	private BeanUIMainMenuSetting loadMainMenuSettings(){
		
		/** Load the main  menu list panel settings **/
		final BeanUIMenuListPanelSettings menuListPanelSetting= loadMenuListPanelUISettings();

		/** Load  till form settings **/
		final BeanUITillFormSetting tillFormSetting= loadTillFormUISettings();

		
		final BeanUIMainMenuSetting mainMenuSettings = new BeanUIMainMenuSetting();
		
		mainMenuSettings.setMenuColumnCount(Integer.parseInt(
				mUIProperties.getProperty(BeanUIMainMenuSetting.MENU_BUTTON_COLUMN_HEIGHT,"5").trim()));
		
		mainMenuSettings.setBottomPanelHeight(Integer.parseInt(
				mUIProperties.getProperty(BeanUIMainMenuSetting.BOTTOM_PANEL_HEIGHT,"288").trim()));

		mainMenuSettings.setMenuListPanelSettings(menuListPanelSetting);
		mainMenuSettings.setTillFormSettings(tillFormSetting);
		return mainMenuSettings;
	
	}
	/**
	 * @return
	 */
	private BeanUIOrderInfoFormSettings loadOrderInfoFormSettings() {

		final BeanUIOrderInfoFormSettings settings = new BeanUIOrderInfoFormSettings();
		settings.setPaymentButtonVisible(
				mUIProperties.getProperty(BeanUIOrderInfoFormSettings.PS_SHOW_PAYMENT_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		return settings;
	}

	/**
	 * @return
	 */
	private PosOrderServiceTypes getDefaultOrderService() {

		PosOrderServiceTypes defServiceType=PosOrderServiceTypes.TAKE_AWAY;
//		if(getTerminalSettings().getType()==PosTerminalServiceType.Restaurant){

			defServiceType=PosOrderServiceTypes.get(
					Integer.valueOf(mUIProperties.getProperty(BeanUIOrderEntrySetting.PS_DEFAULT_ORDER_SERVICE_TYPE,"2")));
//		}

		return defServiceType;
	}

	/**
	 * @return
	 */
	private BeanUIOrderRetrieveFormSetting loadOrderRetrieveFormSettings() {

		final BeanUIOrderRetrieveFormSetting settings = new BeanUIOrderRetrieveFormSetting();

		settings.setCloseBillButtonVisible(
				mUIProperties.getProperty(BeanUIOrderRetrieveFormSetting.PS_SHOW_CLOSEBILL_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);
		settings.setPaymentButtonVisible(
				mUIProperties.getProperty(BeanUIOrderRetrieveFormSetting.PS_SHOW_PAYMENT_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);
		
		settings.setOrderSearchGridColumns(
				mUIProperties.getProperty(BeanUIOrderRetrieveFormSetting.PS_ORDER_SEARCH_GRID_COLUMNS,BeanUIOrderRetrieveFormSetting.PS_ORDER_SEARCH_GRID_DEF_COLUMNS)
				.trim());
		return settings;
	}


	/**
	 * Split UI settings
	 * @return
	 */
	private BeanUISplitSettings loadSplitUISettings(){

		final BeanUISplitSettings settings=new BeanUISplitSettings();

		settings.setItemChoicesVisible(
				mUIProperties.getProperty(BeanUISplitSettings.PS_SHOW_ITEM_CHOICES,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		settings.setItemMoidifiersVisible(
				mUIProperties.getProperty(BeanUISplitSettings.PS_SHOW_ITEM_MODIFIERS,"true")
				.trim().equalsIgnoreCase("true")?true:false);


		return settings;
	}

	/**
	 * Set up the terminal ui settings to match with the terminal type.
	 * user should not override it.
	 */
	private void setUiPropertiesForTerminal() {

		uiSetting.getOrderEntryUISettings().setDefaultServiceType(getDefaultOrderService());

		switch (terminalOperationalMode) {
		case Master:

			break;
		case KOTSlave :

			uiSetting.getMainMenuUISettings().getMenuListPanelSettings().setCashoutButtonVisible(false);
			uiSetting.getMainMenuUISettings().getMenuListPanelSettings().setOrderRefundButtonVisible(false);
			uiSetting.getMainMenuUISettings().getMenuListPanelSettings().setShiftReportsButtonVisible(false);
			uiSetting.getMainMenuUISettings().getMenuListPanelSettings().setSummaryButtonVisible(false);
			uiSetting.getMainMenuUISettings().getMenuListPanelSettings().setOrderDetailsButtonVisible(false);
			uiSetting.getMainMenuUISettings().getMenuListPanelSettings().setViewOpenSessionsButtonVisible(false);

			uiSetting.getOrderEntryUISettings().getPaymentPanelSetting().setCardPayButtonVisibile(false);
			uiSetting.getOrderEntryUISettings().getPaymentPanelSetting().setCashPayButtonVisibile(false);
			uiSetting.getOrderEntryUISettings().getPaymentPanelSetting().setCompanyPayButtonVisibile(false);
			uiSetting.getOrderEntryUISettings().getPaymentPanelSetting().setPartialPayButtonVisibile(false);
			uiSetting.getOrderEntryUISettings().getPaymentPanelSetting().setSplitPayButtonVisibile(false);
			uiSetting.getOrderEntryUISettings().getPaymentPanelSetting().setVoucherPayButtonVisibile(false);
			uiSetting.getOrderEntryUISettings().getPaymentPanelSetting().setOnlinePayButtonVisible(false);
			uiSetting.getOrderEntryUISettings().getOrderRetrieveFormSetting().setCloseBillButtonVisible(false);
			uiSetting.getOrderEntryUISettings().getOrderRetrieveFormSetting().setPaymentButtonVisible(false);

			uiSetting.getOrderEntryUISettings().getOrderInfoFormSetting() .setPaymentButtonVisible(false);

			uiSetting.setQuickPayMode(null);
		case Slave:

			uiSetting.getMainMenuUISettings().getMenuListPanelSettings().setDayEndButtonVisible(false);
			uiSetting.getMainMenuUISettings().getMenuListPanelSettings().setDayStartButtonVisible(false);
			uiSetting.getMainMenuUISettings().getMenuListPanelSettings().setSyncButtonVisible(false);
			uiSetting.getMainMenuUISettings().getMenuListPanelSettings().setTillButtonVisible(false);

			break;
		}

		//Ui Settings based on the terminal type
		switch (getTerminalSettings().getType()) {
		case RWS:

//			setEnabledServices(new String[]{
//					String.valueOf(PosOrderServiceTypes.HOME_DELIVERY.getCode()),
//					String.valueOf(PosOrderServiceTypes.TAKE_AWAY.getCode()),
//					String.valueOf(PosOrderServiceTypes.WHOLE_SALE.getCode()),
//					String.valueOf(PosOrderServiceTypes.SALES_ORDER.getCode())
//			} );

//			uiSetting.getOrderEntryUISettings().getPaymentPanelSetting().setSplitPayButtonVisibile(false);

			break;
		case Restaurant:

//			setEnabledServices(new String[]{
//					String.valueOf(PosOrderServiceTypes.HOME_DELIVERY.getCode()),
//					String.valueOf(PosOrderServiceTypes.TAKE_AWAY.getCode()),
//					String.valueOf(PosOrderServiceTypes.TABLE_SERVICE.getCode()),
//					String.valueOf(PosOrderServiceTypes.SALES_ORDER.getCode())
//			});

			uiSetting.getOrderEntryUISettings().getOrderItemListPanelSetting().setGroupSameItems(false);

			break;
		default:
			break;
		}

		// disable services if not enabled in the ui property.
		final ArrayList<String> codes=new ArrayList<String>(Arrays.asList(getUISetting().getEnabledOrderServices()));
		for(PosOrderServiceTypes es: PosOrderServiceTypes.values()){

			if(codes.contains(String.valueOf(es.getCode()))){
				es.setVisible(true);
			}
			
			
		}
		
		 PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getMenuListPanelSettings().setSalesOrderReportButtonVisible(
				 PosOrderServiceTypes.SALES_ORDER.isVisibleInUI() || PosOrderServiceTypes.HOME_DELIVERY.isVisibleInUI() );

	}

	private ArrayList<PosOrderServiceTypes> enabledServices;
	private Date validTillDate;
	/**
	 * 
	 */
	private void setEnabledServices(String[] serviceCodes){

		this.enabledServices=new ArrayList<PosOrderServiceTypes>();
		int index=0;

		for(String service :serviceCodes){

			this.enabledServices.add(PosOrderServiceTypes.get(Integer.parseInt(service)));
			this.enabledServices.get(index).setVisible(true);
			index++;
		}
	}

	/**
	 * @return
	 */
	public ArrayList<PosOrderServiceTypes> getEnabledServices(){

		return this.enabledServices;
	}


	/**
	 * @return
	 */
	private BeanUIOrderDetailSetting loadOrderDetailUISetting() {

		final BeanUIOrderDetailSetting orderDetailSetting = new BeanUIOrderDetailSetting();
		if(getPrintSettings().isPrintingReshitoEnabled()){
			orderDetailSetting.setPrintReshitoButtonVisible(
					mUIProperties.getProperty(BeanUIOrderDetailSetting.SHOW_RESHITO_BUTTON, "false").trim().equalsIgnoreCase("true")?true:false);
		}

		return orderDetailSetting;
	}

	/**
	 * @return
	 */
	private BeanUIOrderListSetting loadOrderListUISetting() {

		final BeanUIOrderListSetting orderListSetting = new BeanUIOrderListSetting();
		if(getPrintSettings().isPrintingReshitoEnabled()){
			orderListSetting.setPrintReshitoButtonVisible(
					mUIProperties.getProperty(BeanUIOrderListSetting.SHOW_RESHITO_BUTTON, "false").trim().equalsIgnoreCase("true")?true:false);
		}

		orderListSetting.setDefaultOrderStatus(
				PosOrderStatus.get(Integer.parseInt(mUIProperties.getProperty(BeanUIOrderListSetting.DEFAULT_ORDER_STATUS, "1"))));

		
		orderListSetting.setRefundSetting(loadOrderRefundUISetting());
		
		orderListSetting.setOrderListGridColumns(
				mUIProperties.getProperty(BeanUIOrderListSetting.PS_ORDER_SEARCH_GRID_COLUMNS,BeanUIOrderRetrieveFormSetting.PS_ORDER_SEARCH_GRID_DEF_COLUMNS)
				.trim());
	
		return orderListSetting;
	}
	
	
	/**
	 * @return
	 */
	private BeanUIOrderRefundSetting loadOrderRefundUISetting() {

		final BeanUIOrderRefundSetting orderRefundSetting = new BeanUIOrderRefundSetting();
		 
		orderRefundSetting.setRefundAdjustmentMethods(
				mUIProperties.getProperty(BeanUIOrderRefundSetting.REFUND_ADJUSTMENT_METHOD, "0"));

		orderRefundSetting.setRefundType(RefundType.get(Integer.parseInt(
				mUIProperties.getProperty(BeanUIOrderRefundSetting.REFUND_TYPE,"1").trim())));
	
		return orderRefundSetting;
	}

	/**
	 * @return
	 */
	private BeanUIMenuListPanelSettings loadMenuListPanelUISettings() {

		final BeanUIMenuListPanelSettings menulistpanelsetting = new BeanUIMenuListPanelSettings();


		menulistpanelsetting.setContactUsButtonVisibile(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_CONTACTUS_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		menulistpanelsetting.setViewOpenSessionsButtonVisible(
				(mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_VIEW_OPENSESSIONS_BUTTON,"true")
						.trim().equalsIgnoreCase("true") && shopDBType == PosDbType.MYSQL) ?true:false);

		menulistpanelsetting.setAttendanceButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_ATTENDANCE_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		menulistpanelsetting.setDayEndButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_DAYEND_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		menulistpanelsetting.setDayStartButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_DAYSTART_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		menulistpanelsetting.setMailsButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_MAILS_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		menulistpanelsetting.setOrderDetailsButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_ORDERDETAILS_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		menulistpanelsetting.setOrderEntryButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_ORDERENTRY_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		menulistpanelsetting.setOrderRefundButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_ORDERREFUND_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		menulistpanelsetting.setShiftReportsButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_SHIFTREPORTS_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		menulistpanelsetting.setSummaryButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_SUMMARY_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		menulistpanelsetting.setSyncButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_SYNC_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		menulistpanelsetting.setTerminalButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_TERMINAL_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		menulistpanelsetting.setTerminalButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_TERMINAL_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		menulistpanelsetting.setTillButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_TILL_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		menulistpanelsetting.setCashoutButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_CASHOUT_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);


		menulistpanelsetting.setTallyExportButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_TALLYEXPORT_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		
		menulistpanelsetting.setSalesOrderReportButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_SOREPORTS_BUTTON,"true")
				.trim().equalsIgnoreCase("true")?true:false);
		
		menulistpanelsetting.setAboutButtonVisible(
				mUIProperties.getProperty(BeanUIMenuListPanelSettings.SHOW_ABOUT_BUTTON,"false")
				.trim().equalsIgnoreCase("true")?true:false);

		return menulistpanelsetting;
	}

	/**
	 * @return
	 */
	private BeanUITillFormSetting loadTillFormUISettings() {

		final BeanUITillFormSetting tillFormSetting = new BeanUITillFormSetting();

		tillFormSetting.setOpenCashBox(
				mUIProperties.getProperty(BeanUITillFormSetting.OPEN_CASH_BOX,"true")
				.trim().equalsIgnoreCase("true")?true:false);
		
		tillFormSetting.setShowSummaryOnly(
				mUIProperties.getProperty(BeanUITillFormSetting.SHOW_SUMMARY_ONLY,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		tillFormSetting.setConfirmBankDetails(
				mUIProperties.getProperty(BeanUITillFormSetting.CONFIRM_BANK_DETAILS,"true")
				.trim().equalsIgnoreCase("true")?true:false);

		tillFormSetting.setOpeningFloat(mUIProperties.getProperty(BeanUITillFormSetting.SET_OPENINGFLOAT,"true")
				.trim().equalsIgnoreCase("true")?true:false);
		return tillFormSetting;
	}

	/**
	 * @return
	 */
	private BeanUITerminalTabsSetting loadTerminalTabsSettings() {

		final BeanUITerminalTabsSetting settings = new BeanUITerminalTabsSetting();
		settings.setShowCashboxSettingsTab(mUIProperties.getProperty(BeanUITerminalTabsSetting.TS_SHOW_CASHBOX_SETTINGS_TAB,"false").trim().equalsIgnoreCase("true")?true:false);
		settings.setShowEFTSettingsTab(mUIProperties.getProperty(BeanUITerminalTabsSetting.TS_SHOW_EFT_SETTINGS_TAB,"false").trim().equalsIgnoreCase("true")?true:false);
		settings.setShowPoleSettingsTab(mUIProperties.getProperty(BeanUITerminalTabsSetting.TS_SHOW_POLE_SETTINGS_TAB,"false").trim().equalsIgnoreCase("true")?true:false);
		settings.setShowWeighingSettingsTab(mUIProperties.getProperty(BeanUITerminalTabsSetting.TS_SHOW_WEIGHING_SETTINGS_TAB,"false").trim().equalsIgnoreCase("true")?true:false);

		return settings;
	}


	/**
	 * @return
	 */
	private BeanUIOrderItemListPanelSetting loadOrderItemListPanelSettings() {

		final BeanUIOrderItemListPanelSetting settings=new BeanUIOrderItemListPanelSetting();
		settings.setAlternativeTitle(
				(mUIProperties.getProperty(BeanUIOrderItemListPanelSetting.PS_USE_ALTERNATIVE_TITLE,"false").trim().equalsIgnoreCase("true")?true:false));
		settings.setSingleLineUI(
				(mUIProperties.getProperty(BeanUIOrderItemListPanelSetting.PS_USE_SINGLE_LINE_UI,"false").trim().equalsIgnoreCase("true")?true:false));
		settings.setItemDetailsFontSize(Integer.parseInt(
				mUIProperties.getProperty(BeanUIOrderItemListPanelSetting.PS_ITEM_DTLS_FONT_SIZE,"13").trim()));
		settings.setItemNameFontSize(Integer.parseInt(
				mUIProperties.getProperty(BeanUIOrderItemListPanelSetting.PS_ITEM_NAME_FONT_SIZE,"15").trim()));
		settings.setShowDiscount(
				(mUIProperties.getProperty(BeanUIOrderItemListPanelSetting.PS_ITEM_DTLS_SHOW_DISC,"true").trim().equalsIgnoreCase("true")?true:false));
		settings.setShowPrice(
				(mUIProperties.getProperty(BeanUIOrderItemListPanelSetting.PS_ITEM_DTLS_SHOW_PRICE,"true").trim().equalsIgnoreCase("true")?true:false));
		settings.setShowQuantity(
				(mUIProperties.getProperty(BeanUIOrderItemListPanelSetting.PS_ITEM_DTLS_SHOW_QTY,"true").trim().equalsIgnoreCase("true")?true:false));
		settings.setShowTax(
				(mUIProperties.getProperty(BeanUIOrderItemListPanelSetting.PS_ITEM_DTLS_SHOW_TAX,"true").trim().equalsIgnoreCase("true")?true:false));
		settings.setConfirmItemDeletionRequired(
				(mUIProperties.getProperty(BeanUIOrderItemListPanelSetting.PS_CONFIRM_ITEM_DELETION,"true").trim().equalsIgnoreCase("true")?true:false));
		settings.setGroupSameItems(
				(mUIProperties.getProperty(BeanUIOrderItemListPanelSetting.PS_GROUP_SAME_ITEMS,"false").trim().equalsIgnoreCase("true")?true:false));
		settings.setConfirmDuplicateItems(
				(mUIProperties.getProperty(BeanUIOrderItemListPanelSetting.PS_CONFIRM_DUPLICATE_ITEMS,"false").trim().equalsIgnoreCase("true")?true:false));

		return settings;
	}

	/**
	 * @return
	 */
	private BeanUIClassItemListPanelSetting loadClassItemListPanelSettings() {

		final BeanUIClassItemListPanelSetting settings=new BeanUIClassItemListPanelSetting();
		settings.setAlternativeTitle(
				(mUIProperties.getProperty(BeanUIClassItemListPanelSetting.PS_USE_ALTERNATIVE_TITLE,"false").trim().equalsIgnoreCase("true")?true:false));
		settings.setMainClassPanel(
				(mUIProperties.getProperty(BeanUIClassItemListPanelSetting.PS_SHOW_MAIN_CLASS_PANEL,"false").trim().equalsIgnoreCase("true")?true:false));
		settings.setButtonHeight(Integer.parseInt(
				mUIProperties.getProperty(BeanUIClassItemListPanelSetting.PS_CLASS_ITEM_BUTTON_HEIGHT,"95")));
		settings.setButtonWidth(Integer.parseInt(
				mUIProperties.getProperty(BeanUIClassItemListPanelSetting.PS_CLASS_ITEM_BUTTON_WIDTH,"85")));
		settings.setButtonType(
				mUIProperties.getProperty(BeanUIClassItemListPanelSetting.PS_CLASS_ITEM_BUTTON_STYLE,"text_only").trim().toLowerCase());
		settings.setClassItemButtonTextPosition(
				mUIProperties.getProperty(BeanUIClassItemListPanelSetting.PS_CLASS_ITEM_BUTTON_TEXT_POSITION,"under_image").trim().toLowerCase());
		settings.setClassButtonTextFontSize(Integer.parseInt(
				mUIProperties.getProperty(BeanUIClassItemListPanelSetting.PS_CLASS_ITEM_BUTTON_FONT_SIZE,"17").trim()));
		return settings;
	}


	/**
	 * @return
	 */
	private BeanUIItemListPanelSetting loadItemListPanelUISettings() {

		final BeanUIItemListPanelSetting itemListPanelSettings=new BeanUIItemListPanelSetting();
		itemListPanelSettings.setColoredButton(
				(mUIProperties.getProperty(BeanUIItemListPanelSetting.PS_USE_COLORED_ITEM_BUTTON,"true").trim().equalsIgnoreCase("true")?true:false));
		itemListPanelSettings.setAlternativeTitle(
				(mUIProperties.getProperty(BeanUIItemListPanelSetting.PS_USE_ALTERNATIVE_TITLE,"false").trim().equalsIgnoreCase("true")?true:false));
		
		itemListPanelSettings.setShowItemCode(
				(mUIProperties.getProperty(BeanUIItemListPanelSetting.PS_SHOW_ITEM_CODE,"true").trim().equalsIgnoreCase("true")?true:false));
		itemListPanelSettings.setShowItemPrice(
				(mUIProperties.getProperty(BeanUIItemListPanelSetting.PS_SHOW_ITEM_PRICE,"false").trim().equalsIgnoreCase("true")?true:false));
		
		itemListPanelSettings.setButtonType(
				mUIProperties.getProperty(BeanUIItemListPanelSetting.PS_SALE_ITEM_BUTTON_STYLE,"text_only").trim().toLowerCase());
		itemListPanelSettings.setItemControlWidth(Integer.parseInt(
				mUIProperties.getProperty(BeanUIItemListPanelSetting.PS_SALE_ITEM_BUTTON_WIDTH,"170").trim()));
		itemListPanelSettings.setItemControlHeight(Integer.parseInt(
				mUIProperties.getProperty(BeanUIItemListPanelSetting.PS_SALE_ITEM_BUTTON_HEIGHT,"90").trim()));
		itemListPanelSettings.setItemNameFontSize(Integer.parseInt(
				mUIProperties.getProperty(BeanUIItemListPanelSetting.PS_SALE_ITEM_NAME_FONT_SIZE, "15").trim()));
		itemListPanelSettings.setItemDetailFontSize(Integer.parseInt(
				mUIProperties.getProperty(BeanUIItemListPanelSetting.PS_SALE_ITEM_DETAIL_FONT_SIZE, "13").trim()));
		return itemListPanelSettings;
	}



	/**
	 * @return
	 */
	private BeanUIPaymentPanelSetting loadPaymentPanelUISettings() {

		final BeanUIPaymentPanelSetting paymentSetting=new BeanUIPaymentPanelSetting();

		paymentSetting.setPreBillDiscountButtonVisible(
				(mUIProperties.getProperty(BeanUIPaymentPanelSetting.PS_SHOW_PRE_BILL_DISCOUNT_BUTTON,"false").trim().equalsIgnoreCase("true")?true:false));

		paymentSetting.setSplitPayButtonVisibile(
				(mUIProperties.getProperty(BeanUIPaymentPanelSetting.PS_SHOW_SPLIT_PAY_BUTTON,"true").trim().equalsIgnoreCase("true")?true:false));
		paymentSetting.setCardPayButtonVisibile(
				(mUIProperties.getProperty(BeanUIPaymentPanelSetting.PS_SHOW_CARD_PAY_BUTTON,"true").trim().equalsIgnoreCase("true")?true:false));
		paymentSetting.setCashPayButtonVisibile(
				(mUIProperties.getProperty(BeanUIPaymentPanelSetting.PS_SHOW_CASH_PAY_BUTTON,"true").trim().equalsIgnoreCase("true")?true:false));
		paymentSetting.setVoucherPayButtonVisibile(
				(mUIProperties.getProperty(BeanUIPaymentPanelSetting.PS_SHOW_VOUCHER_PAY_BUTTON,"true").trim().equalsIgnoreCase("true")?true:false));
		paymentSetting.setCompanyPayButtonVisibile(
				(mUIProperties.getProperty(BeanUIPaymentPanelSetting.PS_SHOW_COMPANY_PAY_BUTTON,"true").trim().equalsIgnoreCase("true")?true:false));
		paymentSetting.setOnlinePayButtonVisible(
				(mUIProperties.getProperty(BeanUIPaymentPanelSetting.PS_SHOW_ONLINE_PAY_BUTTON,"true").trim().equalsIgnoreCase("true")?true:false));
		paymentSetting.setPartialPayButtonVisibile(
				(mUIProperties.getProperty(BeanUIPaymentPanelSetting.PS_SHOW_PARTIAL_PAY_BUTTON,"true").trim().equalsIgnoreCase("true")?true:false));
		paymentSetting.setQuickPaymentOptions(
				mUIProperties.getProperty(BeanUIPaymentPanelSetting.PS_QUICK_PAY_OPTIONS,
						"50,100,500,1000,5000,10000").trim().split(",",-1));
		
		paymentSetting.setCreditCardValidationRequired(
				(mUIProperties.getProperty(BeanUIPaymentPanelSetting.PS_CREDIT_CARD_VALIDATION,"true").trim().equalsIgnoreCase("true")?true:false));
		

		return paymentSetting;
	}

	/**
	 * @return
	 */
	private BeanUIMiscOpionsPanelSetting loadMiscPanelSettings() {

		final BeanUIMiscOpionsPanelSetting miscPanelSetting=new BeanUIMiscOpionsPanelSetting();

		miscPanelSetting.setWeighingButtonVisible(
				(mUIProperties.getProperty(BeanUIMiscOpionsPanelSetting.PS_SHOW_WEIGHING_BUTTON,"false").trim().equalsIgnoreCase("true")?true:false));

		miscPanelSetting.setShowVoidButton(
				mUIProperties.getProperty(BeanUIMiscOpionsPanelSetting.PS_SHOW_VOID_BUTTON,"true").trim().equalsIgnoreCase("true")?true:false);


		return miscPanelSetting;
	}

	/**
	 * @param mUIProperties2
	 * @return
	 */
	private BeanUIPrintPanelSetting loadPrintPanelUISettings() {

		final BeanUIPrintPanelSetting printPanelSetting=new BeanUIPrintPanelSetting();
		printPanelSetting.setShowCashBoxButton(
				(mUIProperties.getProperty(BeanUIPrintPanelSetting.PS_SHOW_CASH_BOX_BUTTON,"true").trim().equalsIgnoreCase("true")?true:false));
		printPanelSetting.setShowKitchenPrintButton(
				(mUIProperties.getProperty(BeanUIPrintPanelSetting.PS_SHOW_KITCHEN_PRINT_BUTTON,"true").trim().equalsIgnoreCase("true")?true:false));
		printPanelSetting.setShowReceiptPrintButton(
				(mUIProperties.getProperty(BeanUIPrintPanelSetting.PS_SHOW_RECEIPT_PRINT_BUTTON,"true").trim().equalsIgnoreCase("true")?true:false));

		return printPanelSetting;
	}

	/**
	 * @return
	 */
	private BeanUINewOrderSettings loadNewOrderUISettings(){

		final BeanUINewOrderSettings newOrderSettings=new BeanUINewOrderSettings();
		newOrderSettings.setTableSelectionUISettings(loadTableSelectionUISettings());
			newOrderSettings.setConfirmServiceRequired(
				(mUIProperties.getProperty(BeanUINewOrderSettings.PS_CONFIRM_SERVICE_FOR_NEW_ORDER,"true").trim().equalsIgnoreCase("true")?true:false));
		
		
		return newOrderSettings;
	}

	/**
	 * @return
	 */
	private BeanUITableSelectionSettings loadTableSelectionUISettings(){

		final BeanUITableSelectionSettings tableSelectionUiSettings=new BeanUITableSelectionSettings();
		
		tableSelectionUiSettings.setTableWaiterSelectionMode(Integer.valueOf(
				(mUIProperties.getProperty(BeanUITableSelectionSettings.PS_TABLE_WAITER_SELECTION_MODE,"0").trim())));
		
		tableSelectionUiSettings.setAllowMultipleOrders(
				(mUIProperties.getProperty(BeanUITableSelectionSettings.PS_ALLOW_MULTIPLE_ORDERS,"true").trim().equalsIgnoreCase("true")?true:false));
		tableSelectionUiSettings.setSingleTouchSelectionEnabled(
				(mUIProperties.getProperty(BeanUITableSelectionSettings.PS_ENABLE_SINGLE_TOUCH_SELECTION,"false").trim().equalsIgnoreCase("true")?true:false));
		tableSelectionUiSettings.setOrderCountVisible(
				(mUIProperties.getProperty(BeanUITableSelectionSettings.PS_SHOW_ORDER_COUNT,"true").trim().equalsIgnoreCase("true")?true:false));

		tableSelectionUiSettings.setSeaCountVisible(
				(mUIProperties.getProperty(BeanUITableSelectionSettings.PS_SHOW_SEAT_COUNT,"true").trim().equalsIgnoreCase("true")?true:false));
		tableSelectionUiSettings.setTableCodeVisible(
				(mUIProperties.getProperty(BeanUITableSelectionSettings.PS_SHOW_TABLE_CODE,"true").trim().equalsIgnoreCase("true")?true:false));

		tableSelectionUiSettings.setLayoutHeight(Integer.valueOf(
				(mUIProperties.getProperty(BeanUITableSelectionSettings.PS_LAYOUT_HEIGHT,"732").trim())));
		tableSelectionUiSettings.setLayoutWidth(Integer.valueOf(
				(mUIProperties.getProperty(BeanUITableSelectionSettings.PS_LAYOUT_WIDTH,"732").trim())));

		tableSelectionUiSettings.setInfoFontColor(
				(mUIProperties.getProperty(BeanUITableSelectionSettings.PS_INFO_COLOR,"#FFFFFF").trim()));

		return tableSelectionUiSettings;
	}


	public String getDownloadFolder() {
		return downloadFolder;
	}

	public String getTmpDownloadedArchive() {
		return tmpDownloadedArchive;
	}

	private void loadDbSettings(){
		PosDBUtil.init(mProperties);
	}

	public String getLogPath(){
		return mLogPath;
	}

	public String getTerminalName() {
		return mTerminalName;
	}

	public BeanShop getShop() {
		return mShop;
	}

	/**
	 * @param shop
	 */
	public void setShop(BeanShop shop) {
		mShop=shop;
	}

	/**
	 * @return
	 */
	public BeanTerminalInfo getStation() {
		return mStation;
	}

	/**
	 * @return the orderResetPolicy
	 */
	public PosQueueNoResetPolicy getOrderQueueResetPolicy() {
		return orderQueueResetPolicy;
	}
	
	/**
	 * @return the orderResetPolicy
	 */
	public PosQueueNoResetPolicy getKitchenQueueResetPolicy() {
		return kitchenQueueResetPolicy;
	}
	/**
	 * @return the defaultMenuID
	 */
	public int getDefaultMenuID() {

		return defaultMenuID;
	}


	public boolean isLogDebugMode() {
		return logdebugmode;
	}

	public String getLogfilename() {
		return logfilename;
	}

	public BeanCashierShift getCashierShiftInfo() {

		return mCashierShiftInfo;
	}

	public void setCashierShiftInfo(BeanCashierShift cashierInfo) {
		this.mCashierShiftInfo = cashierInfo;
	}

	public BeanCashierShift getTillOpenCashierShiftInfo() {

		return mTillInfo;
	}

	public void setTillOpenCashierShiftInfo(BeanCashierShift cashierInfo) {
		this.mTillInfo = cashierInfo;
	}

	/**
	 * @return the mMenu
	 */
	public BeanSaleMenuItem getMenu() {
		return mMenu;
	}


	/**
	 * @param mMenu the mMenu to set
	 */
	public void setMenu(BeanSaleMenuItem menu) {
		this.mMenu = menu;
	}

	/**
	 * @return the mProperties
	 */
	public final Properties getProperties() {
		return mProperties;
	}

	/**
	 * @return
	 */
	public String getCurrencySymbol(){
		return mCurrency.getCurrencySymbol();
	}

	/**
	 * @return
	 */
	public BeanCurrency getCurrency(){
		return mCurrency;
	}

	public double getRoundingRule(){
		return mBillParams.getRounding().getRoundTo();
	}

	public int getDecimalRounding(){

		int decimalPlaces=2;
		try {

			decimalPlaces=PosSystemParamProvider.getInstance().getSystemParam().getDecimalPlaces();
		} catch (Exception e) {

			PosLog.write(this,"getDecimalRounding",e);
		}
		return decimalPlaces; 
	}

	/**
	 * Returns the global settings for tax
	 * @return
	 * @throws Exception 
	 */
	public BeanTaxGlobalParam  getTaxParam() {
		return mTaxGlobalParam;
	}


	/**
	 * @return the mBillParams
	 */
	public BeanBillParam getBillParams() {
		return mBillParams;
	}


	/**
	 * @param mBillParams the mBillParams to set
	 */
	public void setBillParams(BeanBillParam billParams) {
		this.mBillParams = billParams;
	}

	public String getPublishedDBFolder() {
		return publishedDBFolder;
	}

	public int getPublishDBRetainDays() {
		return publishDBRetainDays;
	}

	public void setPosCardTypes(Map<CardTypes, BeanCardType> cardTypes){
		mCardTypes=cardTypes;
	}

	public Map<CardTypes, BeanCardType>  getCardTypes(){
		return mCardTypes;
	}


	/**
	 * @return Default Order Status
	 */
	public PosOrderStatus getDefaultOrderStatus() {
		return mDefaultOrderStatus;
	}


	/**
	 * @param Default Order Status 
	 */
	public void setDefaultOrderStatus(PosOrderStatus defaultOrderStatus) {
		this.mDefaultOrderStatus = defaultOrderStatus;
	}

	public static PosEnvSettings getPosEnvSettings() {
		return mPosEnvSettings;
	}

	public static String getPROPERTY_FILE() {
		return PROPERTY_FILE;
	}

	public PosDbType getShopDBType() {
		return shopDBType;
	}

	public String getDbFolder() {
		return dbFolder;
	}

	public String getShopDBFile() {
		return shopDBFile;
	}

	public String getTerminalDBFile() {
		return terminalDBFile;
	}

	public int getMaxSyncThreads() {
		return maxSyncThreads;
	}

	public int getMaxThreadRetryCount() {
		return maxThreadRetryCount;
	}

	public String getWebDataURL() {
		return webDataURL;
	}

	public String getSyncToStart() {
		return syncToStart;
	}

	public String getSyncSuccessful() {
		return syncSuccessful;
	}

	public String getSyncInProgress() {
		return syncInProgress;
	}

	public String getSyncFailed() {
		return syncFailed;
	}

	public String getWebArchiveURL() {
		return webArchiveURL;
	}


	/**
	 * @return the mPosOrderDate
	 */
	public String getPosDate() {
		return mPosDate;
	}


	/**
	 * @param mPosOrderDate the mPosOrderDate to set
	 */
	public void setPosDate(String date) {
		this.mPosDate = date;
	}


	/**
	 * @return the ExportPath
	 */
	public String getExportPath() {
		return mExportPath;
	}

	public String getReportPath() {
		return mReportPath;
	}

	//	/**
	//	 * @param mExportPath the mExportPath to set
	//	 */
	//	public void setmExportPath(String mExportPath) {
	//		this.mExportPath = mExportPath;
	//	}

	public String getSecurityPassword() {
		return mSecurityPassword;
	}

	public int getRefundDays(){
		return mRefundDays;
	}

	public int getNumberOfDaysToKeepTXNData(){
		return mTxnDataDays;
	}

	public int getPrimaryScreen(){
		return primaryScreen;
	}

	public BeanDevEFTConfig getEFTConfiguration(){
		return mPosPaymentDeviceSettings;

	}
	public int getSocketCreationTimeOut(){
		return socketCTimeOut;

	}

	/**
	 * @return the uiSetting
	 */
	public BeanUISetting getUISetting() {
		return uiSetting;
	}


	/**
	 * @return
	 */
	public Object getApplicationVersion() {
		return applicationVersion;
	}


	/**
	 * @return
	 */
	public Object getJsonVersion() {
		return jsonVersion;
	}


	/**
	 * @return
	 */
	public String getCacheFolder() {
		return mCacheFolder;
	}


	/**
	 * @return
	 */
	public boolean showSyncButton() {

		return false;
	}

	public boolean isUIPropertyFileEncrypted(){
		return useEncryptedPropertyFile;
	}

	/**
	 * @return the isDemoVersion
	 */
	public boolean isDemoVersion() {
		return isDemoVersion;
	}


	/**
	 * @param isDemoVersion the isDemoVersion to set
	 */
	public void setDemoVersion(boolean isDemoVersion) {
		this.isDemoVersion = isDemoVersion;
	}


	/**
	 * @return
	 */
	public String getDateTimeFormat() {
		// TODO Auto-generated method stub
		return PosDateUtil.DATE_FORMAT_NOW_24;
	}
 
	/**
	 * @return the terminalSettings
	 */
	public BeanTerminalSetting getTerminalSettings() {
		return terminalSettings;
	}

	/**
	 * @return the printSettings
	 */
	public BeanPrintSettings getPrintSettings() {
		return printSettings;
	}


	/**
	 * @return the terminalType
	 */
	public PosTerminalOperationalMode getTerminalOperationalMode() {
		return terminalOperationalMode;
	}


	/**
	 * @return
	 */
	public boolean isDownSyncEnabled() {
		// TODO Auto-generated method stub
		return isDownSyncEnabled;
	}
	
	/**
	 * @return
	 */
	public boolean isUpSyncEnabled() {
		// TODO Auto-generated method stub
		return isUpSyncEnabled;
	}

	/**
	 * @return
	 */
	public boolean isAutoSyncEnabled() {
		// TODO Auto-generated method stub
		return isAutoSyncEnabled;
	}


	/**
	 * @param valueOf
	 */
	public void setValidTillDate(Date validTillDate) {
		
		this.validTillDate=validTillDate;
		
	}


	/**
	 * @return the validTillDate
	 */
	public Date getValidTillDate() {
		return validTillDate;
	}

	/**
	 * @return the isDevelopmentMode
	 */
	public boolean isDevelopmentMode() {
		return isDevelopmentMode;
	}

	/**
	 * @param isDevelopmentMode the isDevelopmentMode to set
	 */
	public void setDevelopmentMode(boolean isDevelopmentMode) {
		this.isDevelopmentMode = isDevelopmentMode;
	}

	/**
	 * @return the syncScrollMsgTimerPeriod
	 */
	public int getServerDataUpdateCheckInterval() {
		return mServerDataUpdateCheckInterval;
	}

	
	public String getBackupFolder(){
		return mBackupFolder;
	}
	
	public int getNumberOfBackupToKeep(){
		return mNumberOfBackupToKeep;
	}
	
	
	public String getWebHMSUrl(){	
		return webHMSURL;
	}
	
	public boolean isEnabledHMSIntegration(){
		return enableHMSIntegration;
	}
/**
	 * @return the applicationType
	 */
	public ApplicationType getApplicationType() {
		return applicationType;
	}

	/**
 * @return the startKotServer
 */
public boolean isStartKotServer() {
	return startKotServer;
}

 
/**
 * @return the kotServerPort
 */
public int getKotServerPort() {
	return kotServerPort;
}

 
	/**
	 * @param applicationType the applicationType to set
	 */
	public void setApplicationType(ApplicationType applicationType) {
		this.applicationType = applicationType;
	}



public enum ApplicationType{
	Standard (0),
	StandAlone(1) ;
	
	private static final Map<Integer,ApplicationType> mLookup 
	= new HashMap<Integer,ApplicationType>();

	static {
		for(ApplicationType rc : EnumSet.allOf(ApplicationType.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	
	private ApplicationType(int value) {
		this.mValue = value;
	}

	public int getValue() { return mValue; }
	
	public static ApplicationType get(int value) { 
		return mLookup.get(value); 
	}

	
}
	
}
