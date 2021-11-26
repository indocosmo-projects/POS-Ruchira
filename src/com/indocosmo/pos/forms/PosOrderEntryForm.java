package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.border.EmptyBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIClassItemListPanelSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIOrderEntrySetting.showSOInfo;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.neworder.BeanUITableSelectionSettings;
import com.indocosmo.pos.common.enums.EnablePrintingOption;
import com.indocosmo.pos.common.enums.PosOrderMedium;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosPaymentOption;
import com.indocosmo.pos.common.utilities.PosAccessPermissionsUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosPaymentUtil;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.common.utilities.PosUtil;
import com.indocosmo.pos.common.utilities.codescanner.PosScanner;
import com.indocosmo.pos.common.utilities.codescanner.PosScanner.IPosDefaultCodeScanListner;
import com.indocosmo.pos.common.utilities.codescanner.bean.BeanWeighingBarcodeDtl;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosEmptyTrayBarcodeScannerHandler.IPosEmptyTrayBarcodeScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosItemTrayBarcodeScannerHandler.IPosItemTrayBarcodeScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosMemberCardScannerHandler.IPosMemberCodeScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosOrderBarcodeScannerHandler.IPosOrderBarcodeScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosUserCardScannerHandler.IPosUserCodeScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosWMBarcodeScannerHandler.IPosWMBarcodeScannerActionLitener;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanCustomer;
import com.indocosmo.pos.data.beans.BeanEmployees;
import com.indocosmo.pos.data.beans.BeanItemClass;
import com.indocosmo.pos.data.beans.BeanItemClassBase;
import com.indocosmo.pos.data.beans.BeanItemPromotion;
import com.indocosmo.pos.data.beans.BeanMenuDepartment;
import com.indocosmo.pos.data.beans.BeanOrderCustomer;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderQHeader;
import com.indocosmo.pos.data.beans.BeanOrderServingTable;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanSaleMenuItem;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.beans.BeanSubClass;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.beans.BeanUser;
import com.indocosmo.pos.data.providers.shopdb.PosCashierShiftProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerProvider;
import com.indocosmo.pos.data.providers.shopdb.PosHotItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosItemClassProvider;
import com.indocosmo.pos.data.providers.shopdb.PosMenuDepartmentProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderDtlProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderPreDiscountProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderQueueProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPromotionItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleMenuProvider;
import com.indocosmo.pos.data.providers.shopdb.PosServiceTableProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.data.providers.shopdb.PosWaiterProvider;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.components.PosScrollMessagePanel;
import com.indocosmo.pos.forms.components.itemcontrols.PosOrderListItemControl;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.orderentry.PosActionPanelBase;
import com.indocosmo.pos.forms.components.orderentry.PosActionPanelBase.OrderEntryMenuActions;
import com.indocosmo.pos.forms.components.orderentry.PosActionPanelCounter;
import com.indocosmo.pos.forms.components.orderentry.PosBottomToolbarPanel;
import com.indocosmo.pos.forms.components.orderentry.PosClassListPanel;
import com.indocosmo.pos.forms.components.orderentry.PosEmptyPanel;
import com.indocosmo.pos.forms.components.orderentry.PosHotItemPanel;
import com.indocosmo.pos.forms.components.orderentry.PosItemListPanel;
import com.indocosmo.pos.forms.components.orderentry.PosMainActionPanel;
import com.indocosmo.pos.forms.components.orderentry.PosMainActionPanel.RestaurantOrderEntryMenuActions;
import com.indocosmo.pos.forms.components.orderentry.PosMessagePanel;
import com.indocosmo.pos.forms.components.orderentry.PosMiscOptionPanel;
import com.indocosmo.pos.forms.components.orderentry.PosOrderEntryBottomMenuPanelBase;
import com.indocosmo.pos.forms.components.orderentry.PosOrderListOptionPanel;
import com.indocosmo.pos.forms.components.orderentry.PosOrderListPanel;
import com.indocosmo.pos.forms.components.orderentry.PosPaymentMethodsPanel;
import com.indocosmo.pos.forms.components.orderentry.PosPrintOptionPanel;
import com.indocosmo.pos.forms.components.orderentry.PosTitlePanel;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosActionPanelListnerBase;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosActionPanelRestaurantListner;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosClassItemListListner;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosHotItemListner;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosItemListPanelListner;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosMiscOperationsListener;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosOrderListPanelListner;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosPaymentPanelListner;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosPrintOperationsListener;
import com.indocosmo.pos.forms.listners.IPosHoldOrderFormFormListner;
import com.indocosmo.pos.forms.listners.IPosMessageListner;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;
import com.indocosmo.pos.forms.listners.IPosOrderInfoFormListner;
import com.indocosmo.pos.forms.listners.IPosOrederRetriveFormListner;
import com.indocosmo.pos.forms.listners.IPosSelectCustomerFormListner;
import com.indocosmo.pos.forms.listners.PosOrderEntryWindowListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter;
import com.indocosmo.pos.forms.restaurant.tablelayout.PosServiceTableSelectionForm;
import com.indocosmo.pos.reports.receipts.PosReceipts;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.poledisplay.PosDevicePoleDisplay;
import com.indocosmo.pos.terminal.devices.printer.printerexceptions.PrinterException;

@SuppressWarnings("serial")
public class PosOrderEntryForm extends JDialog implements IPosActionPanelListnerBase, 
		IPosActionPanelRestaurantListner,
		IPosPrintOperationsListener, 
		IPosMiscOperationsListener,
		IPosMemberCodeScannerActionLitener,
		IPosUserCodeScannerActionLitener,
		IPosWMBarcodeScannerActionLitener,
		IPosOrderBarcodeScannerActionLitener,
		IPosEmptyTrayBarcodeScannerActionLitener,
		IPosItemTrayBarcodeScannerActionLitener ,
		IPosDefaultCodeScanListner,IPosMessageListner{

	/**
	 * @author jojesh
	 *
	 */
	public enum PosOrderEntryMode {
		New, Retrieve
	}

	/** Constants **/
	public static final int PANEL_CONTENT_H_GAP = 8;
	public static final int PANEL_CONTENT_V_GAP = 8;
	public static final int PANEL_V_GAP = 1;
	public static final int PANEL_H_GAP = 1;
	public static final Color PANEL_BG_COLOR = new Color(184, 207, 229);

	public static final int LAYOUT_HEIGHT = PosUtil.getScreenSize(PosEnvSettings.getInstance().getPrimaryScreen()).height;
	public static final int LAYOUT_WIDTH =  PosUtil.getScreenSize(PosEnvSettings.getInstance().getPrimaryScreen()).width;

	public static final int LEFT_PANEL_WIDTH = PosOrderListPanel.LAYOUT_WIDTH; // PosOrderListOptionPanel.PANEL_WIDTH;//+PANEL_H_GAP*2;
	public static final int RIGHT_PANEL_WIDTH = PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getClassItemListPanelSetting().getButtonWidth()*2+PosClassListPanel.PANEL_CONTENT_H_GAP*3;
	public static final int TOP_PANEL_LAYOUT_HEIGHT = PosActionPanelCounter.LAYOUT_HEIGHT;
	public static final int BOTTOM_PANEL_HEIGHT = PosOrderEntryBottomMenuPanelBase.LAYOUT_HEIGHT;

	private static PosOrderServiceTypes DEF_ORDER_SERVICE = PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getDefaultServiceType();

	/** the main container panel **/
	private JPanel mContentPane;

	/** panel which holds the Title panel and action panel **/
	private JPanel mTopPanel;

	/** the bottom panel which holds the payment operations panels **/
	private JPanel mBottomPanel;

	/** Panel to hold the order item list and order item options **/
	private JPanel mLeftPanel;

	/** The panel to hold the class panel and subclass panels **/
	private JPanel mRightPanel;

	/** Panel where company title and logo is displayed **/
	private PosTitlePanel mTitlePanel;

	/**
	 * The main menu option panel. Base panel is used actual menu depends on the
	 * terminal type type
	 **/
	private PosActionPanelBase mOrderActionPanel;

	/** The message panel where various informations are displayed **/
	private PosMessagePanel mMessagePanel;

	/** Panel where  to show scroll messages  **/
	private PosScrollMessagePanel mScrollMsgPanel;


	/** The panel where all the order list panel options are displayed **/
	private PosOrderListOptionPanel mGridOptionPanel;

	/** The panel which holds the ordered items **/
	private PosOrderListPanel mOrderListPanel;

	/** The panel where all the quick payment buttons are displayed **/
	private PosPaymentMethodsPanel mPaymentMethodsPanel;

	/** panel to hold print operation buttons **/
	private PosPrintOptionPanel mPosPrintOperationPanel;

	/** panel to hold misc operation buttons **/
	private PosMiscOptionPanel mPosMiscOperationPanel;

	/** An panel to add empy items **/
	private PosEmptyPanel mEmptyPanel;

	/** The panel where 3 hot items buttons are displayed **/
	private PosHotItemPanel mHotItemPanel;

	/** The center panel where all the items are displayed **/
	private PosItemListPanel mItemListPanel;

	/** The panel which holds the main class items **/
	private PosClassListPanel mMainClassListPanel;

	/** The panel which displays the sub class items **/
	private PosClassListPanel mSubClassListPanel;

	/** Listener for order entry form **/
	private IPosOrderEntryFormListner mOrderEntryFormListner;

	/** the object to hold the selected promotion **/
	private BeanItemPromotion mCurrentPromotionObject;

	/** variable for checking the saved order status **/
	private Boolean mIsSaved = true;

	/** object to hold the selected customer **/
	private BeanCustomer mSelectedCustomerInfo;
	private BeanOrderCustomer mOrderCustomerInfo;

	/** the default customer object **/
	private BeanCustomer mDefCustomerInfo;

	/** The enum type object which holds the order entry mode **/
	private PosOrderEntryMode mCurOrderEntryMode = PosOrderEntryMode.New;

	/** The single instance object **/
	private static PosOrderEntryForm mSingleToneInstace;

	/** arraylist which keeps all the pos itemlist **/
	private ArrayList<BeanSaleItem> mAllPosSaleItemList;

	/**
	 * Hash map to hold the items and barcodes. can be used to search items
	 * based on barcode
	 **/
	private Map<String, BeanSaleItem> mBarcodeItemMap;

	/**
	 * Hashmap which holes item and its code. Used to search based on code
	 */
	private Map<String, BeanSaleItem> mItemcodeItemMap;

	/** parent of this form **/
	private PosMainMenuForm mParent = null;

	//	private boolean mIsFreshOrder = true;

	//	private boolean mCanPrintToKitchen = false;
	/** Object to hold the selected service type **/
	private PosOrderServiceTypes mSelectedServiceType;

	/** Obect to hold the selected service table **/
	private BeanServingTable mSelectedServiceTable;

	/** all tables added to the order **/
	private Map<String, BeanOrderServingTable> mOrderServiceTables;

	/** Total number of peoples. ic PAX**/
	private int mCovers=0;

	/**
	 * Object to hold the served by user/waiter served by can be waiter or user
	 * based on the serivce selected
	 **/
	private BeanEmployees mSelectedServedBy;

	private PosServiceTableProvider mServiceTableProvider;

	private PosDevicePoleDisplay mPosPoleDisplay;

	private BeanOrderHeader mPosOrderEntryItem;
	private PosOrderHdrProvider mOrderHdrProvider;
	private PosOrderDtlProvider  mOrderDtlProvider;
	private PosPromotionItemProvider mPromotionItemProvider;

	private KeyboardFocusManager mKeyboardManager;
	private PosScanner mCodeScanner;
	// private PosOrderEntryForm Parent;

	private PosCashierShiftProvider mCashierShiftProvider;
	private BeanCashierShift mCashierShiftNew;

	private BeanUIClassItemListPanelSetting classPanelSettings;


	private PosOrderInfoForm mOrderInfoForm; 

	private Object activeWindow;
	/**
	 * Create the frame.
	 */
	private PosOrderEntryForm() {
		//PosLog.debug("PosOrderEntryForm================ PosOrderEntryForm Step 2 ");
		try {
			mSingleToneInstace=this;
			initialize();
		} catch (Exception e) {
			PosFormUtil.showSystemError(this);
			PosLog.write(this, "PosOrderEntryForm", e);
		}
	}

	public static synchronized PosOrderEntryForm getInstance() {
	//	PosLog.debug("PosOrderEntryForm================ PosOrderEntryForm Step 1 ");
		if (mSingleToneInstace == null)
			new PosOrderEntryForm();
		return mSingleToneInstace;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	private void initialize() throws Exception {
		//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
		//PosLog.debug("initialize================ initialize Step  ");

		classPanelSettings=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getClassItemListPanelSetting();
		//PosLog.debug("initialize================ after classPanelSettings Step  ");
		mPosPoleDisplay = PosDevicePoleDisplay.getInstance();
//		switch (PosEnvSettings.getInstance().getStation().getServiceType()){
//		case Restaurant:	
//			DEF_ORDER_SERVICE = PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getDefaultServiceType();
//			break;
//		default:
//			DEF_ORDER_SERVICE = PosOrderServiceTypes.TAKE_AWAY;
//			break;
//		}
		initControls();
		//PosLog.debug("initialize================ after initControls Step  ");
		initVars();
		//PosLog.debug("initialize================ after initVars Step  ");
		setKeyboardListner();
		//PosLog.debug("initialize================ after setKeyboardListner Step  ");
		addWindowListener(new PosOrderEntryWindowListner(this));
		//PosLog.debug("initialize================ after addWindowListener Step  ");
		buildCodeItemMaps();
		//PosLog.debug("initialize================ after buildCodeItemMaps Step  ");
		

		PosFormUtil.applyWindowRenderingHack(this);
		//PosLog.debug("initialize================ after applyWindowRenderingHack Step  ");
	//	PosLog.debug("initialize================ initialize Step end ");


	}

	private void buildCodeItemMaps() {
		if (mAllPosSaleItemList == null)
			return;
		mBarcodeItemMap = new HashMap<String, BeanSaleItem>();
		mItemcodeItemMap = new HashMap<String, BeanSaleItem>();
		for (BeanSaleItem item : mAllPosSaleItemList) {
			if (item.getBarCode() != null
					&& !item.getBarCode().trim().equals(""))
				mBarcodeItemMap.put(item.getBarCode().toUpperCase(), item);
			mItemcodeItemMap.put(item.getCode().toUpperCase(), item);
		}
	}

	public void setEnableKeyboardFocusManager(boolean enable) {
		mCodeScanner.resetScanner();
		if (enable)
			mKeyboardManager.addKeyEventPostProcessor(mCodeScanner);
		else
			mKeyboardManager.removeKeyEventPostProcessor(mCodeScanner);
	}

	private void setKeyboardListner() {

		mKeyboardManager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		mCodeScanner=new PosScanner(this);
	}


	private void onBarcodeScanned(String code) {
		if (mBarcodeItemMap == null)
			return;
		try{
			BeanSaleItem item = mBarcodeItemMap.get(code);
			if (item == null)
				item = mItemcodeItemMap.get(code);
			if (item != null)
				onItemSelectedListner.onItemSelected(item);
		}catch(Exception e){
			PosLog.write(this, "onBarcodeScanned", e);
			PosFormUtil.showErrorMessageBox(this, "Failed to add sale item. Please contact administrator.");
		}
	}

	private void initVars() throws Exception {
		mDefCustomerInfo = new PosCustomerProvider().getDefaultCustomer();
		mPromotionItemProvider = new PosPromotionItemProvider();
		mOrderHdrProvider = new PosOrderHdrProvider();
		mOrderDtlProvider=new PosOrderDtlProvider();
		mServiceTableProvider = new PosServiceTableProvider();
		setDefaults();
	}

	/**
	 * Initializes the UI
	 */
	private void initControls() {
		//PosLog.debug("initControls================ enter initControls  ");
		final Point pt=PosUtil.transformScreenPoints(new Point(0, 0),PosEnvSettings.getInstance().getPrimaryScreen());
		//PosLog.debug("initControls================ after  Point pt  ");
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		//PosLog.debug("initControls================ after setDefaultCloseOperation  ");
		this.setUndecorated(true);
	//	PosLog.debug("initControls================ after setUndecorated  ");
		this.setBounds(pt.x, pt.y, LAYOUT_WIDTH, LAYOUT_HEIGHT);
		//PosLog.debug("initControls================ after setBounds  ");
		this.setPreferredSize(new Dimension(LAYOUT_WIDTH, LAYOUT_HEIGHT));
		//PosLog.debug("initControls================ after setPreferredSize  ");

		mContentPane = new JPanel();
		//PosLog.debug("initControls================ after mContentPane  ");
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//PosLog.debug("initControls================ after setBorder  ");
		mContentPane.setLayout(null);
		//PosLog.debug("initControls================ after setLayout  ");
		this.setContentPane(mContentPane);
		//PosLog.debug("initControls================ after setContentPane  ");

		/** Creates the top title and order menu panels **/
		createTopPanles();
		//PosLog.debug("initControls================ after createTopPanles  ");
		/** Creates message panel **/
		createMessagePanel();
		//PosLog.debug("initControls================ after createMessagePanel  ");
		createScrollMessagePanel();
		//PosLog.debug("initControls================ after createScrollMessagePanel  ");

		/** Creates the panel which holds order list option and order list panel **/
		createLeftPanel();
		//PosLog.debug("initControls================ after createLeftPanel  ");
		/** Creates the hot item, main and sub class panels **/
		createRightPanel();
		//PosLog.debug("initControls================ after createRightPanel  ");
		/** Creates the center panel which holds the sale items **/
		createItemListPanel();
		//PosLog.debug("initControls================ after createItemListPanel  ");
		/**
		 * Creates the the panel at the bottom which holds the payment panel and
		 * other optration panels
		 **/
		createBottomPanels();
		//PosLog.debug("initControls================ after createBottomPanels  ");
		/** Populate the class items **/
		loadClassItems();
		//PosLog.debug("initControls================ after loadClassItems  ");
		//PosLog.debug("initControls================ after end  ");
		// mHotItemPanel.setSelected(true);
	}

	/** Creates the top title panel and menu action panels **/
	private void createTopPanles() {
		//PosLog.debug("createTopPanles================ inside createTopPanles  ");
		mTopPanel = new JPanel();
		//PosLog.debug("createTopPanles================ after mTopPanel  ");
		mTopPanel.setBounds(0, 1, LAYOUT_WIDTH, TOP_PANEL_LAYOUT_HEIGHT);
		//PosLog.debug("createTopPanles================ after setBounds  ");
		mTopPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		//PosLog.debug("createTopPanles================ after setLayout  ");
		// mTopPanel.setBackground(Color.RED);
		mContentPane.add(mTopPanel);
		//PosLog.debug("createTopPanles================ after mContentPane.add  ");

		mOrderActionPanel = createTopActionPanel();
		//PosLog.debug("createTopPanles================ after createTopActionPanel  ");
		mTitlePanel = createTopTitlePanel();
		//PosLog.debug("createTopPanles================ after createTopTitlePanel  ");

		mTopPanel.add(mTitlePanel);
		//PosLog.debug("createTopPanles================ after mTopPanel.add(mTitlePanel  ");
		mTopPanel.add(mOrderActionPanel);
		//PosLog.debug("createTopPanles================ after mTopPanel.add(mOrderActionPanel)  ");

	}

	/**
	 * Creates the payment and additional operation panals
	 */
	private void createBottomPanels() {
		final int height = PosPaymentMethodsPanel.LAYOUT_HEIGHT;
		final int top = getHeight() - height - PANEL_V_GAP;
		final int left = mLeftPanel.getX() + mLeftPanel.getWidth();
		final int width = getWidth() - left;

		mBottomPanel = new JPanel();
		mBottomPanel.setBounds(left, top, width, height);
		mBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 0));
		mContentPane.add(mBottomPanel);

		mPaymentMethodsPanel = createPaymentMethodsPanel();
		if(mPaymentMethodsPanel.getOptionList().size()>0)
			mBottomPanel.add(mPaymentMethodsPanel);

		mPosPrintOperationPanel = createPosPrintOperationPanel();
		if(mPosPrintOperationPanel.getOptionList().size()>0)
			mBottomPanel.add(mPosPrintOperationPanel);

		mPosMiscOperationPanel = createPosMiscOperationPanel();
		if(mPosMiscOperationPanel.getOptionList().size()>0)
			mBottomPanel.add(mPosMiscOperationPanel);

		mEmptyPanel = createEmptyPanel();
		mBottomPanel.add(mEmptyPanel);
	}

	private void createRightPanel() {
		mRightPanel = new JPanel();
		final int left = this.getWidth() - RIGHT_PANEL_WIDTH;// -PANEL_V_GAP;
		final int top = mMessagePanel.getY() + mMessagePanel.getHeight();// +PANEL_V_GAP;
		final int width = RIGHT_PANEL_WIDTH;
		/**Change this height for adjusting item class container Deepak**/
		final int height = this.getHeight() - top
				- PosBottomToolbarPanel.LAYOUT_HEIGHT
				- PosOrderEntryForm.PANEL_V_GAP * 2 ;
		mRightPanel.setBounds(left, top, width, height);
		mRightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, PANEL_V_GAP,
				PANEL_V_GAP));
		mRightPanel.setOpaque(true);
		mRightPanel.setBackground(Color.WHITE);
		mContentPane.add(mRightPanel);

		mHotItemPanel = createHotItemPanel();
		mRightPanel.add(mHotItemPanel);

		mSubClassListPanel = createSubClassListPanel();
		mRightPanel.add(mSubClassListPanel);

		mMainClassListPanel = createMainClassListPanel();
		if (classPanelSettings.showMainClassPanel())
			mRightPanel.add(mMainClassListPanel);
	}

	private void setDefaults() {

	}

	public void setParent(PosMainMenuForm parent) {
		this.mParent = parent;
	}

	private void loadClassItems() {
		//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
		//PosLog.debug("loadClassItems================ inside loadClassItems  ");
		PosItemClassProvider mainClassItemProvider = PosItemClassProvider.getInstance();
		//PosLog.debug("1===================== ");
		ArrayList<BeanItemClassBase> posDefaultMainClassItems = new ArrayList<BeanItemClassBase>();
		//PosLog.debug("2===================== ");
		PosSaleMenuProvider menuProvider = new PosSaleMenuProvider();
		//PosLog.debug("3===================== ");
		PosMenuDepartmentProvider menuDepartmentProvider = new PosMenuDepartmentProvider();
		//PosLog.debug("4===================== ");
		BeanSaleMenuItem defaultMenuItem = (PosEnvSettings.getInstance().getDefaultMenuID()!=-1)?
				menuProvider.getMenuByID(PosEnvSettings.getInstance().getDefaultMenuID())
				:menuProvider.getDefaultMenu();
				//PosLog.debug("5===================== ");
				if (defaultMenuItem == null) {
					
					defaultMenuItem = menuProvider.getFirstMenu();
					//PosLog.debug("6===================== ");
					
				}
				if (defaultMenuItem != null) {
					PosEnvSettings.getInstance().setMenu(defaultMenuItem);
					//PosLog.debug("7===================== ");
					mHotItemPanel.getHotItemButtons().get(0)
					.setEnabled(defaultMenuItem.isEnableH1Button());
					//PosLog.debug("8===================== ");
					mHotItemPanel.getHotItemButtons().get(1)
					.setEnabled(defaultMenuItem.isEnableH2Button());
					//PosLog.debug("9===================== ");
					mHotItemPanel.getHotItemButtons().get(2)
					.setEnabled(defaultMenuItem.isEnableH3Button());
					//PosLog.debug("10===================== ");
					mMessagePanel.setMenu(defaultMenuItem.getName());
				}
				ArrayList<BeanMenuDepartment> MenuDepartments = menuDepartmentProvider
						.getDepartments(defaultMenuItem);
				//PosLog.debug("11===================== MenuDepartments value"+MenuDepartments);
				
				if (MenuDepartments != null) {
					//PosLog.debug("11.1===================== MenuDepartments value"+MenuDepartments);
					for (BeanMenuDepartment department : MenuDepartments) {
						//PosLog.debug("11.2===================== MenuDepartments value"+MenuDepartments);
						posDefaultMainClassItems.addAll(mainClassItemProvider
								.getListByDepartment(department));
						//PosLog.debug("12=====================department"+department);
					}
				}
				if (classPanelSettings.showMainClassPanel()) {

					mMainClassListPanel.setClassList(posDefaultMainClassItems);
					//PosLog.debug("13===================== ");
				} else {
					mSubClassListPanel
					.setClassList(getAllSubClassItems(posDefaultMainClassItems));
					//PosLog.debug("14===================== ");
				}
				mAllPosSaleItemList = getAllSaleItems(posDefaultMainClassItems, true);
				//PosLog.debug("15===================== ");

	}

	private ArrayList<BeanItemClassBase> getAllSubClassItems(
			ArrayList<BeanItemClassBase> posMainClassItems) {
		ArrayList<BeanItemClassBase> posSubClassItems = new ArrayList<BeanItemClassBase>();
		for (BeanItemClassBase item : posMainClassItems)
			posSubClassItems.addAll(((BeanItemClass) item).getSubList());
		Collections.sort(posSubClassItems, new BeanItemClassBase() {
		});
		return posSubClassItems;
	}

	public ArrayList<BeanSaleItem> getAvailablePositems() {
		return mAllPosSaleItemList;
	}

	private PosActionPanelBase createTopActionPanel() {
		PosActionPanelBase panel = null;
		try{	
		
		
		//		if (PosEnvSettings.getInstance().getStation().getServiceType() == PosTerminalServiceType.Counter)
		//			panel = new PosActionPanelCounter(this);
		//		else
		//			panel = new PosActionPanelRestaurant(this);
			
			//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
			//PosLog.debug("createTopPanles================ inside try  ");
		panel = new PosMainActionPanel(this);
//	PosLog.debug("createTopPanles================ after PosMainActionPanel ");
		panel.setPosActionPanelListner(this);
	//	PosLog.debug("createTopPanles================ after setPosActionPanelListner ");
		}catch(Exception e){
			PosLog.write("createTopActionPanel Exception", "createTopActionPanel", e);
		}
		return panel;
	}

	private PosTitlePanel createTopTitlePanel() {
		
		final int width = mTopPanel.getWidth() - mOrderActionPanel.getWidth();
		PosTitlePanel panel = null;
		panel = new PosTitlePanel(this, width);
		return panel;
	}
	
	private void createScrollMessagePanel() {
		
		int top = mTopPanel.getY() + mTopPanel.getHeight() + PANEL_V_GAP;
		int left = PANEL_H_GAP;
		mScrollMsgPanel = new PosScrollMessagePanel(left, top);
		mContentPane.add(mScrollMsgPanel);
		mScrollMsgPanel.setVisible(false);
	}

	private void createMessagePanel() {
		int top = mTopPanel.getY() + mTopPanel.getHeight() + PANEL_V_GAP;
		int left = PANEL_H_GAP;
		mMessagePanel = new PosMessagePanel(left, top);
		mContentPane.add(mMessagePanel);
		mMessagePanel.setVisible(true);
		
	}

	private void setShiftInfo() {
		BeanCashierShift cashierShift = PosEnvSettings.getInstance()
				.getCashierShiftInfo();
		mMessagePanel.setShiftInfo(cashierShift);
	}

	/** Creates the left panel which holds the orderlist and optional panels **/
	private void createLeftPanel() {

		final int top = mMessagePanel.getY() + mMessagePanel.getHeight()
				+ PANEL_V_GAP;
		final int left = 0;

		final int height = LAYOUT_HEIGHT - top;
		final int width = LEFT_PANEL_WIDTH;

		mLeftPanel = new JPanel();
		mLeftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		mLeftPanel.setBounds(left, top, width, height);
		mContentPane.add(mLeftPanel);

		mLeftPanel.setOpaque(true);
		mLeftPanel.setBackground(PANEL_BG_COLOR);

		mGridOptionPanel = createOrderListOptionPanel();
		mLeftPanel.add(mGridOptionPanel);

		mOrderListPanel = createOrderListPanel();
		mLeftPanel.add(mOrderListPanel);

	}

	/**
	 * creates the options for order list panel
	 * 
	 * @return
	 */
	private PosOrderListOptionPanel createOrderListOptionPanel() {
		return new PosOrderListOptionPanel(this);
	}

	/**
	 * creates the list panel which holds the selected items
	 * 
	 * @return
	 */
	private PosOrderListPanel createOrderListPanel() {

		final int height = mLeftPanel.getHeight()
				- mGridOptionPanel.getHeight();
		PosOrderListPanel orderListPanel = new PosOrderListPanel(height,false);
		orderListPanel.setParent(this);
		orderListPanel.setItemGridControlPanel(mGridOptionPanel);
		orderListPanel.setListner(new IPosOrderListPanelListner() {

			@Override
			public void onItemSelected(PosOrderListItemControl item) {
				mGridOptionPanel.setListItem(item);
			}

			@Override
			public void onItemEdited(PosOrderListItemControl item) {
				mIsSaved = false;
			}

			@Override
			public void onItemAdded(PosOrderListItemControl item) {
				mIsSaved = false;
			}

			@Override
			public void onItemDeleted(PosOrderListItemControl item) {
				mIsSaved = false;
			}

		});
		return orderListPanel;
	}

	/**
	 * creates the POS order entry print operations option panel
	 */
	private PosPrintOptionPanel createPosPrintOperationPanel() {

		PosPrintOptionPanel posOperationPanel = new PosPrintOptionPanel(this);
		posOperationPanel.setListener(this);
		return posOperationPanel;
	}

	/**
	 * creates the POS order entry misc operations option panel
	 */
	private PosMiscOptionPanel createPosMiscOperationPanel() {

		PosMiscOptionPanel posOperationPanel = new PosMiscOptionPanel(this);
		posOperationPanel.setListener(this);
		return posOperationPanel;
	}

	/**
	 * Creates the payment method panel
	 */
	private PosPaymentMethodsPanel createPaymentMethodsPanel() {

		PosPaymentMethodsPanel paymentMethodsPanel = new PosPaymentMethodsPanel(
				this);
		paymentMethodsPanel.setListener(new IPosPaymentPanelListner() { 
			@Override
			public void onPaymentDone(PaymentMode paymode) {

				mIsSaved = true;
				try {

					if(mPosOrderEntryItem!=null)
						PosOrderUtil.releaseOrder(mPosOrderEntryItem.getOrderId());
					resetPosOrderEntry();
					//					startNewOrder();
				} catch (Exception e) {
					PosFormUtil.showSystemError(PosOrderEntryForm.this);
				}
			}

			@Override
			public void onPaymentCancelled(Object sender) {

				/**
				 * SPLIT TO DO : check needed?

				if(status.equals(PosOrderStatus.Partial)){
					try {
						BeanOrderHeader Order = mOrderHdrProvider.getOrderData(mPosOrderEntryItem.getOrderId());
						if (Order != null&&(Order.getTotalAmountPaid()!=mPosOrderEntryItem.getTotalAmountPaid()||Order.getBillDiscountAmount()!=mPosOrderEntryItem.getBillDiscountAmount())) {
							resetPosOrderEntry();
							setOrderRetrieved(Order);
							//							mParent.setSectedCashier(mCashierShiftNew.getCashierInfo()
							//									.getCardNumber());
							setShiftInfo();
						} 
						//						resetPosOrderEntry();
						//						startNewOrder();
					} catch (Exception e) {
						PosFormUtil.showSystemError(PosOrderEntryForm.this);
					}
				}
				*/
			}
			@Override
			public void onPreBillDiscountChanged() {
				
				mIsSaved=false;
				
			}
		});
		return paymentMethodsPanel;
	}

	/**
	 * creates the hot item panel
	 * 
	 * @return
	 */
	private PosHotItemPanel createHotItemPanel() {
		PosHotItemPanel hotItemPanel = new PosHotItemPanel(this);
		hotItemPanel.setHotItemList(new PosHotItemProvider().getList());
		hotItemPanel.setOnSelectedListner(new IPosHotItemListner() {
			@Override
			public void onSelected(ArrayList<BeanSaleItem> hotItemList) {
				ArrayList<BeanSaleItem> itemList = hotItemList;
				mItemListPanel.setItemList(itemList);
				mSubClassListPanel.resetSelection();
			}
		});
		return hotItemPanel;
	}

	/**
	 * creates the main class item panel
	 * 
	 * @return
	 */
	private PosClassListPanel createMainClassListPanel() {
		final int height = mRightPanel.getHeight() - mHotItemPanel.getHeight()
				- PANEL_V_GAP;
		PosClassListPanel mainClassListPanel = new PosClassListPanel(true,
				height);
		mainClassListPanel.setClassItemClickListner(mainClassItemListListner);
		return mainClassListPanel;
	}

	/**
	 * creates the sub class item list panel
	 * 
	 * @return
	 */
	private PosClassListPanel createSubClassListPanel() {
		final int height = mRightPanel.getHeight() - mHotItemPanel.getHeight()
				- PANEL_V_GAP;
		PosClassListPanel subClassListPanel = new PosClassListPanel(true,
				height);
		subClassListPanel.setClassItemClickListner(subClassItemListListner);
		return subClassListPanel;
	}

	/***
	 * Creates the Center item list panel
	 */
	private void createItemListPanel() {
		final int top = mMessagePanel.getY() + mMessagePanel.getHeight()
				+ PANEL_V_GAP;
		final int left = LEFT_PANEL_WIDTH + PANEL_V_GAP;

		final int width = this.getWidth()
				- PosOrderEntryForm.RIGHT_PANEL_WIDTH - left;
		final int height = this.getHeight()
				- PosBottomToolbarPanel.LAYOUT_HEIGHT - top
				- PosOrderEntryForm.PANEL_V_GAP * 2;

		mItemListPanel = new PosItemListPanel(this, width, height);
		mItemListPanel.setOnItemSelectedListner(onItemSelectedListner);
		mItemListPanel.setLocation(left, top);
		mContentPane.add(mItemListPanel);
	}

	/**
	 * creates the bottom empty panel.
	 * 
	 * @return
	 */
	private PosEmptyPanel createEmptyPanel() {

		final int width = mBottomPanel.getWidth()
				- mPaymentMethodsPanel.getWidth()
				- mPosPrintOperationPanel.getWidth() 
				- mPosMiscOperationPanel.getWidth()
				- 4;
		PosEmptyPanel emptyPanel = new PosEmptyPanel(width);
		return emptyPanel;
	}

	private IPosItemListPanelListner onItemSelectedListner = new IPosItemListPanelListner() {
		@Override
		public void onItemSelected(BeanSaleItem item) {
			BeanSaleItem saleItem = item.clone();
			try{
				if(saleItem.isGroupItem())
					showGroupItemObjectBrowser(PosOrderEntryForm.this,saleItem);
				else
					addToOrderList(saleItem);
			}catch(Exception e){
				PosLog.write(this, "itemSelected", e);
				PosFormUtil.showErrorMessageBox(this, "Failed to add sale item. Please contact administrator.");
			}
			// mIsSaved = false;
		}
	};

	private void addToOrderList(BeanSaleItem saleItem){
		try{
			mOrderListPanel.addPosItem(saleItem);
		}catch(Exception e){
			PosLog.write(this, "itemSelected", e);
			PosFormUtil.showErrorMessageBox(this, "Failed to add sale item. Please contact administrator.");
		}
	}

	public void showGroupItemObjectBrowser(RootPaneContainer parent,BeanSaleItem groupItem){

		ArrayList<BeanSaleItem> posItemList=groupItem.getSubItemList();
		final int itemListSize=groupItem.getSubItemList().size();
		int row=3;
		int col=itemListSize/3;
		col+=((itemListSize%3)>0)?1:0;
		col=(col>3)?3:col;
		IPosBrowsableItem[] itemList=new IPosBrowsableItem[posItemList.size()];
		posItemList.toArray(itemList);
		PosObjectBrowserForm objBrowserForm=new PosObjectBrowserForm(groupItem.getName(),itemList,ItemSize.SaleItem,col,row);
		objBrowserForm.setTitleColors(groupItem.getForeground(),groupItem.getBackground());
		objBrowserForm.setListner(groupItemBrowserListner);
		PosFormUtil.showLightBoxModal(parent,objBrowserForm);
	}

	private IPosObjectBrowserListner groupItemBrowserListner=new IPosObjectBrowserListner() {
		@Override
		public void onItemSelected(IPosBrowsableItem item) {

			BeanSaleItem saleItem =((BeanSaleItem)item).clone();
			onItemSelectedListner.onItemSelected(saleItem);
		}
		@Override
		public void onCancel() {
			// TODO Auto-generated method stub

		}
	};


	private IPosClassItemListListner mainClassItemListListner = new IPosClassItemListListner() {
		@Override
		public void onClassItemSelected(BeanItemClassBase itemClass) {
			BeanItemClass itemMainClass = (BeanItemClass) itemClass;
			ArrayList<BeanItemClassBase> subClassList = itemMainClass
					.getSubList();
			mSubClassListPanel.setClassList(subClassList);
			mHotItemPanel.setSelected(false);
		}
	};

	private IPosClassItemListListner subClassItemListListner = new IPosClassItemListListner() {
		@Override
		public void onClassItemSelected(BeanItemClassBase itemClass) {
			BeanSubClass itemSubClass = (BeanSubClass) itemClass;
			if(itemSubClass!=null) {
				ArrayList<BeanSaleItem> itemList = itemSubClass.getItemList();
				mItemListPanel.setItemList(itemList);
				mHotItemPanel.setSelected(false);
			}
		}
	};


	private void showHoldOrderForm() {

		if (mPosOrderEntryItem.getOrderDetailItems() == null
				|| mPosOrderEntryItem.getOrderDetailItems().size() == 0) {
			PosFormUtil.showErrorMessageBox(PosOrderEntryForm.this,
					"There are no items to park.");
			return;
		}
		if (PosOrderUtil.check4OnlyVoidOrder(mPosOrderEntryItem)) {
			if (mPosOrderEntryItem.isNewOrder() && PosOrderUtil.isDeliveryService(mPosOrderEntryItem) && mPosOrderEntryItem.getAdvanceAmount()>0){
				PosFormUtil.showErrorMessageBox(PosOrderEntryForm.this,
						"There are no items in the order");
				return;
			} else if(!PosOrderUtil.isDeliveryService(mPosOrderEntryItem) || mPosOrderEntryItem.getAdvanceAmount()<0){
			
				PosFormUtil
				.showQuestionMessageBox(
						this,
						MessageBoxButtonTypes.YesNo,
						"Since there is no item in the order, this order will be cancelled. Do you want to continue?",
						new PosMessageBoxFormListnerAdapter() {
							/*
							 * (non-Javadoc)
							 * 
							 * @see
							 * com.indocosmo.pos.forms.messageboxes.listners
							 * .PosMessageBoxFormListnerAdapter#
							 * onYesButtonPressed()
							 */
							@Override
							public void onYesButtonPressed() {
								/***
								 * sets the order item as void. It is
								 * because there is a chance that user
								 * deleted all the items.
								 */
								mPosOrderEntryItem
								.setStatus(PosOrderStatus.Void); 
								mPosOrderEntryItem.setClosingDate(PosEnvSettings.getInstance().getPosDate());
								mPosOrderEntryItem.setClosingTime(PosDateUtil.getDateTime());
								mPosOrderEntryItem.setClosedBy(PosEnvSettings.getInstance().getCashierShiftInfo()
										.getCashierInfo());
								mPosOrderEntryItem.setVoidBy(PosEnvSettings.getInstance().getCashierShiftInfo()
										.getCashierInfo());
								mPosOrderEntryItem.setVoidAt(PosDateUtil.getDateTime());
								mOnHoldOKButtonListner
								.onOkClicked(mPosOrderEntryItem);
	 
							}
						});
				}
		} else {

			boolean isDirty=true;
			final showSOInfo showSo=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getShowSalesOrderDetail();
			 
		
			if((mSelectedServiceType==PosOrderServiceTypes.SALES_ORDER || 
					mSelectedServiceType==PosOrderServiceTypes.HOME_DELIVERY) &&
					(showSo.equals(showSOInfo.AtParking) || showSo.equals(showSOInfo.Both)) ){
				final PosSalesOrderInfoForm salesOrderForm=new PosSalesOrderInfoForm(PosOrderEntryForm.this, mPosOrderEntryItem);
				
				PosFormUtil.showLightBoxModal(PosOrderEntryForm.this,
					salesOrderForm);
				isDirty=salesOrderForm.isDirty();
			}
			
			if (mPosOrderEntryItem.isNewOrder() && isDirty  ){
				try {

					final String queueNo=new PosOrderQueueProvider().getOrderQueueNo(mPosOrderEntryItem);
					mPosOrderEntryItem.setQueueNo(queueNo);
					
				} catch (Exception e) {

					PosFormUtil
					.showErrorMessageBox(PosOrderEntryForm.this,
							"Failed to save the current order. Please check the log for details.");
					return;

				}
			}

			if(mSelectedServiceType==PosOrderServiceTypes.SALES_ORDER || mSelectedServiceType==PosOrderServiceTypes.HOME_DELIVERY){
				if (isDirty)
					mOnHoldOKButtonListner.onOkClicked(mPosOrderEntryItem);
			}else{
				
				PosOrderHoldForm posHoldOrderForm = new PosOrderHoldForm(
						mPosOrderEntryItem);
				posHoldOrderForm.setonOkClickedListner(mOnHoldOKButtonListner);
				PosFormUtil.showLightBoxModal(PosOrderEntryForm.this,
						posHoldOrderForm);
			}
		}
	}


	private void showSaveOrderMessage(PosMessageBoxFormListnerAdapter listner) {
		PosFormUtil.showQuestionMessageBox(this,
				MessageBoxButtonTypes.YesNoCancel,
				"Order has been modified!!! Do you want to park this order?",
				listner);
	}

	/***
	 * Use this function to reset the service Call this function when there is a
	 * change in the service or an order is retrieved.
	 * 
	 * @throws Exception
	 */
	private void resetServiceSelection() throws Exception {

		resetServiceVars();
		resetServiceControls();
		resetPosOrderEntry();
	}

	/***
	 * Reset the service selection vars
	 */
	private void resetServiceVars() {
		mSelectedServiceType = DEF_ORDER_SERVICE;
	}

	/***
	 * reset all the controls used for displaying the service iformation
	 */
	private void resetServiceControls() {
		mOrderActionPanel.setServiceTypeIfno(mSelectedServiceType, null);
	}

	/***
	 * reset the order entry.
	 * 
	 * @throws Exception
	 */
	private void resetPosOrderEntry() throws Exception {
		resetOrderVars();
		resetOrderControls();
		resetDevices();
	}

	/***
	 * reset the variables used for order entry
	 * 
	 * @throws Exception
	 */
	private void resetOrderVars() throws Exception {

		mIsSaved = true;
		mSelectedServedBy = null;
		mPosOrderEntryItem = null;
		mCurOrderEntryMode = PosOrderEntryMode.New;
		mCurrentPromotionObject = getCurrentPromotion();
		mSelectedCustomerInfo =new PosCustomerProvider().getDefaultCustomer(mSelectedServiceType);
//		setOrderCustomer(mDefCustomerInfo);
		mOrderCustomerInfo=new BeanOrderCustomer(mDefCustomerInfo);
		mSelectedServiceTable = mServiceTableProvider
				.getTableByServiceType(mSelectedServiceType);
		mOrderServiceTables=new HashMap<String, BeanOrderServingTable>();
		mCovers=0;
	}

	/***
	 * Reset all the controls used from displaying the order info.
	 */
	private void resetOrderControls() {
		mOrderListPanel.resetGrid();
		// mOrderListPanel.setPosOrderEntryItem(mPosOrderEntryItem);
		mOrderListPanel.setOrderEntrMode(mCurOrderEntryMode);
		// setCurrentPromotion();
		resetMessagePanel();
		mGridOptionPanel.setListItem(null);
		mOrderActionPanel.reset();
	}

	/***
	 * Set the service selection changes call this function when there is a
	 * service change.
	 * 
	 * @param service
	 * @param data
	 * @throws Exception
	 */
	private void setServiceSelection(PosOrderServiceTypes service) {
		setServiceSelection(service, true);
	}

	private void setServiceSelection(PosOrderServiceTypes service,
			boolean startNewOrder) {
		try {
			resetServiceSelection();
			setServiceVars(service);
			setServiceControls();
			mSelectedCustomerInfo = new PosCustomerProvider().getDefaultCustomer(mSelectedServiceType);
			mOrderCustomerInfo=new BeanOrderCustomer(mDefCustomerInfo);
			setCustomer(mSelectedCustomerInfo,mOrderCustomerInfo);
//			setOrderCustomer(mDefCustomerInfo);
			
			mItemListPanel.refreshItemList();
			if (startNewOrder)
				onActionNewOrderClicked();
		} catch (Exception e) {
			PosLog.write(this, "setServiceSelection", e);
			PosFormUtil.showSystemError(PosOrderEntryForm.this);
		}

	}

	/***
	 * Sets the various vars that are used for service selections
	 * 
	 * @param service
	 * @param data
	 * @throws Exception
	 */
	private void setServiceVars(PosOrderServiceTypes service) throws Exception {
		mSelectedServiceType = service;
	}

	/***
	 * sets the controls used for displaying service info. data part is not need
	 * when setting the service controls.
	 */
	private void setServiceControls() {
		mOrderActionPanel.setServiceTypeIfno(mSelectedServiceType,
				mSelectedServiceTable);
	}

	/***
	 * Call this function when need to start/retrive new order (New/Retrive)
	 * When calling retrieve function call resetServiceSelection() then
	 * setServiceSelection() with service type and table from retrieved order.
	 * After that call this function.
	 * 
	 * @param orderObject
	 * @throws Exception
	 */
	private void setPosOrderEntry(BeanOrderHeader orderObject) throws Exception {
		setOrderVars(orderObject);
		setOrderControls();

	}

	private void setOrderVars(BeanOrderHeader orderObject) throws Exception {

		if (orderObject != null) {
			mPosOrderEntryItem = orderObject;
			mSelectedCustomerInfo = orderObject.getCustomer();
			mOrderCustomerInfo=orderObject.getOrderCustomer();
			mSelectedServedBy = orderObject.getServedBy();
			mSelectedServiceTable = orderObject.getServiceTable();
			mCurOrderEntryMode = PosOrderEntryMode.Retrieve;
			mOrderServiceTables=orderObject.getOrderTableList();
			setCustomer(mSelectedCustomerInfo,mOrderCustomerInfo);
		} else {
			mPosOrderEntryItem = PosOrderUtil.createNewPosOrderEntryItem();
			mPosOrderEntryItem.setServiceTable(mSelectedServiceTable);
			mPosOrderEntryItem.setCovers(mCovers);
			mPosOrderEntryItem.setServedBy(mSelectedServedBy);
			mPosOrderEntryItem.setOrderServiceType(mSelectedServiceType);
			mPosOrderEntryItem.setCustomer(mSelectedCustomerInfo);
			mPosOrderEntryItem.setOrderCustomer(mOrderCustomerInfo);
			mPosOrderEntryItem.setOrderTableList(mOrderServiceTables);
		}
	}

	private void setOrderControls() {

		mOrderListPanel.setReadOnly(mPosOrderEntryItem.isReadOnly());
		mOrderListPanel.setPosOrderEntryItem(mPosOrderEntryItem);
		mOrderListPanel.setOrderEntrMode(mCurOrderEntryMode);
		resetMessagePanel();
		mOrderActionPanel.onNewOrderStarted(mPosOrderEntryItem);
		mOrderActionPanel.setServiceTypeIfno(mSelectedServiceType,
				mSelectedServiceTable);
	}

	/***
	 * set the Top action panel
	 * 
	 */
	private void setActionPanel() {
		Object data = null;
		switch (mSelectedServiceType) {
		case HOME_DELIVERY:
		case TAKE_AWAY:
			break;
		case TABLE_SERVICE:
			data = mSelectedServiceTable;
			break;
		default:
			break;
		}
		mOrderActionPanel.setServiceTypeIfno(mSelectedServiceType, data);
	}

	private void resetDevices() {
		mPosPoleDisplay.clear();
		mPosPoleDisplay.displayNewBillMessage();
	}

	private void resetMessagePanel() {
		mMessagePanel.setCustomer(mOrderCustomerInfo);
		mMessagePanel.setOrderEntryMode(mCurOrderEntryMode);
		mMessagePanel.setOrderNumber(mPosOrderEntryItem);
		mMessagePanel.setPromotion(mCurrentPromotionObject);
	}


	private void setCurrentPromotion() {
		BeanItemPromotion tmpPromo;
		try {
			tmpPromo = mPromotionItemProvider.getCurrentPromotion();
			if (mCurrentPromotionObject == null
					|| !mCurrentPromotionObject.getCode().equals(
							tmpPromo.getCode())) {
				mCurrentPromotionObject = tmpPromo;
				resetMessagePanel();
			}
		} catch (Exception e) {
			PosLog.write(this, "setCurrentPromotion", e);
			PosFormUtil
			.showErrorMessageBox(PosOrderEntryForm.this,
					"Failed to get default promotion. Please check logs for detail");
		}
	}

	public BeanItemPromotion getCurrentPromotion() {
		setCurrentPromotion();
		return mCurrentPromotionObject;
	}


	/**
	 * @param custCardNo
	 */
	private void onCustomerInfoClicked(String custCardNo) {

		if (PosAccessPermissionsUtil.validateAccess(this, PosEnvSettings
				.getInstance().getCashierShiftInfo().getCashierInfo()
				.getUserGroupId(), "change_customer") && !IsPartiallyPaidOrder()) {
			PosOrderCustomerForm custForm = new PosOrderCustomerForm(this,mSelectedCustomerInfo,mOrderCustomerInfo);
			custForm.setListner(onCustomerSelected);
			PosFormUtil.showLightBoxModal(this, custForm);
		}
	}

	/**
	 * 
	 */
	private void onActionOpenOrderClicked() {

		//		if (mIsSaved){

		retrieveOrder();

		//		}else {
		//			
		//			showSaveOrderMessage(new PosMessageBoxFormListnerAdapter() {
		//				@Override
		//				public void onNoButtonPressed() {
		//
		//					retrieveOrder();
		//					
		//				}
		//
		//				@Override
		//				public void onYesButtonPressed() {
		//					
		//					showHoldOrderForm();
		//					retrieveOrder();
		//				}
		//			});
		//		}
	}

	/**
	 * 
	 */
	private void retrieveOrder() {

		showOrderRetriveForm(null);

	}
	/**
	 * @param hdr
	 */
	private void showOrderRetriveForm(BeanOrderHeader hdr) {

		PosOrderRetrieveForm retrieveForm = new PosOrderRetrieveForm();
		retrieveForm.setOrderHeader(hdr);
		retrieveForm.setListner(mRetriveFormListner);
		PosFormUtil.showLightBoxModal(this, retrieveForm);
	}

	private IPosOrederRetriveFormListner mRetriveFormListner = new IPosOrederRetriveFormListner() {

		@Override
		public void onItemSelected( Object sender,int queueNo, BeanOrderQHeader item) {
		}

		@Override
		public void onItemSelected(Object sender,final BeanOrderHeader item) {

			try {

				if (!mIsSaved){

					showSaveOrderMessage(new PosMessageBoxFormListnerAdapter() {
						@Override
						public void onNoButtonPressed() {

							releaseOrder(mPosOrderEntryItem);
							try {

								setOrderRetrieved(item);
							} catch (Exception e) {

								PosFormUtil.showErrorMessageBox(mParent, "Failed to open order. Contact administrator.");
								PosLog.write(PosOrderEntryForm.this, "mRetriveFormListner.onItemSelected", e);
							}

						}

						@Override
						public void onYesButtonPressed() {

							showHoldOrderForm();
							releaseOrder(mPosOrderEntryItem);
							try {

								setOrderRetrieved(item);
							} catch (Exception e) {

								PosFormUtil.showErrorMessageBox(mParent, "Failed to open order. Contact administrator.");
								PosLog.write(PosOrderEntryForm.this, "mRetriveFormListner.onItemSelected", e);
							}
						}
					});

				}else{

					releaseOrder(mPosOrderEntryItem);
					setOrderRetrieved(item);
				}

			} catch (Exception e) {

				PosLog.write(PosOrderEntryForm.this, "mRetriveFormListner.onItemSelected", e);
				PosFormUtil.showSystemError(PosOrderEntryForm.this);
			}
		}
	};
	/**
	 * Order retrieved from order tables
	 * 
	 * @param order
	 * @throws Exception
	 */
	private void setOrderRetrieved(BeanOrderHeader order) throws Exception {
		resetServiceSelection();
		setServiceSelection(order.getOrderServiceType(), false);
		setPosOrderEntry(order);
		mIsSaved = true;
	}

	/**
	 * @param classItems
	 * @param isMainClass
	 * @return
	 */
	private ArrayList<BeanSaleItem> getAllSaleItems(

			ArrayList<BeanItemClassBase> classItems, Boolean isMainClass) {
		ArrayList<BeanSaleItem> posSaleItems = new ArrayList<BeanSaleItem>();
		for (BeanItemClassBase item : classItems)
			if (isMainClass)
				posSaleItems.addAll(getAllSaleItems(
						((BeanItemClass) item).getSubList(), false));
			else
				posSaleItems.addAll(((BeanSubClass) item).getItemList());
		return posSaleItems;
	}

	/**
	 * 
	 */
	private void onAttendanceClicked() {

		mParent.doAttendance(this, true);
	}

	/**
	 * 
	 */
	private void onExitClicked() {

		if (mIsSaved){

			releaseOrder(mPosOrderEntryItem);
			exit();

		}else {
			showSaveOrderMessage(new PosMessageBoxFormListnerAdapter() {
				@Override
				public void onNoButtonPressed() {

					releaseOrder(mPosOrderEntryItem);
					exit();

				}

				@Override
				public void onYesButtonPressed() {

					showHoldOrderForm();
					exit();
				}
			});
		}

	}

	/**
	 * 
	 */
	private void exit() {
		setVisible(false);
		if (mOrderEntryFormListner != null)
			mOrderEntryFormListner.onExit();
		mIsSaved = true;
		mPosPoleDisplay.close();
	}


	private IPosSelectCustomerFormListner onCustomerSelected = new IPosSelectCustomerFormListner() {

		@Override
		public void onOkClicked(BeanCustomer posCustomerItem, BeanOrderCustomer orderCustomer) {
				
			setCustomer(posCustomerItem,orderCustomer);
		}
	};

//	/**
//	 * @param customer
//	 */
//	private void setOrderCustomer(BeanCustomer customer) {
//		
//		final BeanOrderCustomer orderCustomer=new BeanOrderCustomer();
//		orderCustomer.setId(customer.getId());
//		orderCustomer.setName(customer.getName());
//		orderCustomer.setAddress(customer.getAddress());
//		orderCustomer.setCity(customer.getCity());
//		
//		orderCustomer.setState(PosEnvSettings.getInstance().getShop().getState());
//		orderCustomer.setStateCode(PosEnvSettings.getInstance().getShop().getStateCode());
//		orderCustomer.setCountry(PosEnvSettings.getInstance().getShop().getCountry());
//		
////		orderCustomer.setState(customer.getState());
////		orderCustomer.setStateCode(customer.getStateCode());
////		orderCustomer.setCountry(customer.getCountry());
//		orderCustomer.setPhoneNumber(customer.getPhoneNumber());
//		orderCustomer.setTinNo(customer.getTinNo());
//		mOrderCustomerInfo=orderCustomer;
//	}

	
	/**
	 * @param customer
	 */
	private void setCustomer(BeanCustomer customer,BeanOrderCustomer orderCustomer) {
		
		mOrderCustomerInfo=orderCustomer;
		mSelectedCustomerInfo = customer;
		if (mPosOrderEntryItem != null){
			mPosOrderEntryItem.setCustomer(mSelectedCustomerInfo);
			mPosOrderEntryItem.setOrderCustomer(orderCustomer);
		}
		mMessagePanel.setCustomer(mOrderCustomerInfo);
		mOrderListPanel.setCustomerType(mSelectedCustomerInfo.getCustType());
	}

	private IPosHoldOrderFormFormListner mOnHoldOKButtonListner = new IPosHoldOrderFormFormListner() {

		public void onCancelClicked() {
		}

		@Override
		public void onOkClicked(BeanOrderHeader posOrderQueItem) {

			try {

				doSaveOrder();
				releaseOrder(mPosOrderEntryItem);
				resetPosOrderEntry();

			} catch (Exception e) {

				PosLog.write(this, "mOnHoldOKButtonListner.onOkClicked("+posOrderQueItem.getOrderId()+")" , e);
				PosFormUtil.showSystemError(PosOrderEntryForm.this);
			}
		}

	};

	/**
	 * 
	 */
	public BeanOrderHeader doSaveOrder(){

		return doSaveOrder(true);
	}

	/**
	 * @param isInteractive
	 */
	public BeanOrderHeader doSaveOrder(boolean isInteractive) {

		mIsSaved=false;
		BeanOrderHeader orderHdr = PosOrderUtil
				.buildOrderHeaders(false,getBillGrid());

		try {

			StringBuilder printResMsg=new StringBuilder("");
	
			//  delete previous advance payment transactions  if reset advance amount to zero 
			if( PosOrderUtil.isDeliveryService(mPosOrderEntryItem) && 
					mPosOrderEntryItem.getStatus()==PosOrderStatus.Partial && 
					mPosOrderEntryItem.getAdvanceAmount()==0){
				
				mPosOrderEntryItem.setTotalAmountPaid(0);
				mPosOrderEntryItem.setChangeAmount(0);
				mPosOrderEntryItem.setActualBalancePaid(0);
				mPosOrderEntryItem.setCashOut(0);
				mPosOrderEntryItem.setStatus(PosOrderStatus.Open);
				
				mPosOrderEntryItem.setOrderPaymentHeaders(null);
				mPosOrderEntryItem.setOrderPaymentItems(null);
				mPosOrderEntryItem.setOrderSplits(null);
				mOrderHdrProvider.resetAdvancePayment(mPosOrderEntryItem.getOrderId());
				
			}
			if( PosOrderUtil.isDeliveryService(mPosOrderEntryItem))
				setExtraChargeTax();
			
			mOrderHdrProvider.saveOrder(mPosOrderEntryItem);
			
			PosOrderPreDiscountProvider orderPreDiscount=new PosOrderPreDiscountProvider();
			orderPreDiscount.save(mPosOrderEntryItem);
			
			
			
//			if( mPosOrderEntryItem.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER && mPosOrderEntryItem.getTotalAmountPaid()>0)
//				mOrderHdrProvider.resetAdvancePayment(mPosOrderEntryItem.getOrderId());
				
			if(PosOrderUtil.isDeliveryService(mPosOrderEntryItem) && mPosOrderEntryItem.getAdvanceAmount()>0) 
				PosPaymentUtil.doAdvancePaymentForSalesOrder(this);
			
			
			
			if(PosDeviceManager.getInstance().hasKichenPrinter() && isInteractive && PosEnvSettings.getInstance().getPrintSettings().getPrintToKitchenAtParking()!=EnablePrintingOption.NO){

				boolean doPrint=true;

				if( PosEnvSettings.getInstance().getPrintSettings().getPrintToKitchenAtParking()==EnablePrintingOption.ASK)
					doPrint=(PosFormUtil.showQuestionMessageBox(PosOrderEntryForm.this, MessageBoxButtonTypes.YesNo, "Do you want to print Kitchen Receipt?",null)==MessageBoxResults.Yes);

				if(doPrint){

//					if (orderHdr.isNewOrder()){
//
//						final String queueNo=new PosOrderQueueProvider().getOrderQueueNo(orderHdr);
//						orderHdr.setQueueNo(queueNo);
//					}

					if(printToKitchen(orderHdr, false,printResMsg)){

						PosOrderUtil.setAsPrintedToKitchen(mPosOrderEntryItem);
						mOrderDtlProvider.updatePrintStatus(orderHdr.getOrderId());
					}
				}
			}
			mIsSaved = true;
			
			if(PosDeviceManager.getInstance().hasReceiptPrinter() && isInteractive  && 
					PosEnvSettings.getInstance().getPrintSettings().getBillPrintingAtParking()!=EnablePrintingOption.NO){
				
				
				 		if(PosEnvSettings.getInstance().getPrintSettings().isEnabledBillPrintAtParking(mPosOrderEntryItem.getOrderServiceType().getCode())){
							buildOrderHeadersForPrinitng(mPosOrderEntryItem);
							
							if(mPosOrderEntryItem.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER)
								PosFormUtil.showSalesOrderPrintConfirmMessage(PosOrderEntryForm.this, (PosEnvSettings.getInstance().getPrintSettings().getBillPrintingAtParking()==EnablePrintingOption.FORCE),
										mPosOrderEntryItem, false);
							else
								PosFormUtil.showPrintConfirmMessage(PosOrderEntryForm.this,  (PosEnvSettings.getInstance().getPrintSettings().getBillPrintingAtParking()==EnablePrintingOption.FORCE), 
								mPosOrderEntryItem, false, null, true);
							
						}
						
				}
					

			
			mCurOrderEntryMode=PosOrderEntryMode.Retrieve;

			if(isInteractive){

				PosFormUtil.showInformationMessageBox(PosOrderEntryForm.this,printResMsg.toString()+ "<P>Order is parked.");
			}

		}catch(PrinterException prException){
			
			PosFormUtil.closeBusyWindow();
			PosFormUtil.showErrorMessageBox(PosOrderEntryForm.this, prException.getMessage());
			PosLog.write(this, "doSaveOrder", prException);
		} catch (Exception e) {

			mIsSaved=false;
			PosFormUtil.closeBusyWindow();
			PosFormUtil
			.showErrorMessageBox(PosOrderEntryForm.this,
					"Failed to save the current order. Please check the log for details.");
			PosLog.write(this, "doSaveOrder", e);
		}

		return ((mIsSaved)?orderHdr:null);

	}

	/**
	 * @param orderHeader
	 * @param printAll
	 * @param resMessage
	 * @return
	 */
	private boolean printToKitchen(BeanOrderHeader orderHeader, boolean printAll,StringBuilder resMessage) {


		
		boolean isPrinted = false;

		if (orderHeader.getOrderDetailItems() != null){

			 if (!PosOrderUtil.hasPrintableItems(orderHeader,  printAll)){
				
				 resMessage.append("No Items to print.");
//				PosFormUtil.showInformationMessageBox(this, "No Items to print." );
				return   false;
			}
			
			if(!printAll){
				if(!PosOrderUtil.hasNotPrintedToKitchenItems(orderHeader)){
					resMessage.append("Already printed to kitchen(s).");
					return true;
				}
			}
		}
		try {

			PosReceipts.printReceiptToKitchen(orderHeader, printAll);
			isPrinted = true;
			resMessage.append("Print request sent to the Kitchen(s).");

		} catch (Exception e) {
			resMessage.append("Failed to sent print request to kitchen. Please check kitchen printer.");
			PosLog.write(this, "printToKitchen", e);
		}
		return isPrinted;
	}
	/*
	 * 
	 */
	public void saveSalesOrder() throws Exception{
		
		
		setCustomer(mPosOrderEntryItem.getCustomer(),mPosOrderEntryItem.getOrderCustomer());
		mOrderActionPanel.setServiceTypeIfno(mSelectedServiceType,
				mSelectedServiceTable);
		mOrderActionPanel.onNewOrderStarted(mPosOrderEntryItem);
		mOrderListPanel.setPosOrderEntryItem(mPosOrderEntryItem);
		resetMessagePanel();
		mItemListPanel.refreshItemList();
		mIsSaved = false;
		
		
		doSaveOrder(false);
		
		mPosOrderEntryItem.setNewOrder(false);
		mMessagePanel.setOrderNumber(mPosOrderEntryItem);
//		dsffdsf
		mPosOrderEntryItem.setReadOnly(true);
		mOrderListPanel.setReadOnly(mPosOrderEntryItem.isReadOnly());
		setOrderRetrieved(mPosOrderEntryItem);
	}

	/**
	 * @param orderHeader
	 * @return
	 */
	private boolean printPrePaymentReceipt(BeanOrderHeader orderHeader) {

		boolean isPrinted = false;
		try {

			if (!mIsSaved){
				
				if (PosOrderUtil.isDeliveryService(mPosOrderEntryItem) && mPosOrderEntryItem.isNewOrder()){
						
					if (!showsalesOrderForm()) 
						return false;
				}
					
				saveSalesOrder();

			}

			buildOrderHeadersForPrinitng(orderHeader);

			final boolean forceToPrint=(PosEnvSettings.getInstance().getPrintSettings().getReceiptPrintingAtPayment()!=EnablePrintingOption.ASK);
			PosFormUtil.showPrintConfirmMessage(this, forceToPrint, orderHeader, false, null, true);
			isPrinted = true;

		} catch (Exception e) {

			PosFormUtil
			.showErrorMessageBox(this,
					"Failed to print. Please check printer.");
			e.printStackTrace();
		}

		return isPrinted;
	}

	private BeanOrderHeader buildOrderHeadersForPrinitng(BeanOrderHeader mOrderHeader) {

		try {
			double billTax1 = 0;
			double billTax2 = 0;
			double billTax3 = 0;
			double billGST = 0;
			double billServiceTax = 0;
			if (PosEnvSettings.getInstance().getBillParams().getTax() != null) {
				BeanTax billTax = PosEnvSettings.getInstance().getBillParams()
						.getTax();
				double totalBill = getBillGrid().getBillTotal();
				if (billTax.isTaxOneApplicable())
					billTax1 = (totalBill * billTax.getTaxOnePercentage()) / 100;
				if (billTax.isTaxTwoApplicable())
					billTax2 = (totalBill * billTax.getTaxTwoPercentage()) / 100;
				if (billTax.isTaxThreeApplicable())
					billTax3 = (totalBill * billTax.getTaxThreePercentage()) / 100;
				if (billTax.isGSTDefined())
					billGST = (totalBill * billTax.getGSTPercentage()) / 100;
				if (billTax.isServiceTaxApplicable())
					billServiceTax = (totalBill * billTax
							.getServiceTaxPercentage()) / 100;
			}

			mOrderHeader.setDetailTotal(getBillGrid().getBillItemTotal());
			mOrderHeader.setTotalTax1(getBillGrid().getBillTax1() + billTax1);
			mOrderHeader.setTotalTax2(getBillGrid().getBillTax2() + billTax2);
			mOrderHeader.setTotalTax3(getBillGrid().getBillTax3() + billTax3);
			mOrderHeader.setTotalGST(getBillGrid().getBillGST() + billGST);
			mOrderHeader.setTotalServiceTax(getBillGrid().getBillServiceTax()
					+ billServiceTax);
			mOrderHeader
			.setTotalDetailDiscount(getBillGrid().getBillDiscount());
			mOrderHeader.setTotalAmount(getBillGrid().getBillTotal() + billTax1
					+ billTax2 + billTax3 + billGST + billServiceTax);

		} catch (Exception e) {
			PosLog.write(this, "buildOrderHeadersForPrinitng", e);
			PosFormUtil.showErrorMessageBox(this,
					"Failed create payment. Please check log for details");
		}
		return mOrderHeader;
	}

	@Override
	public void onPrintClicked() {
		if(checkForPrinting())
			printPrePaymentReceipt(mPosOrderEntryItem);
	}


	public void setListner(IPosOrderEntryFormListner listner) {
		mOrderEntryFormListner = listner;
	}

	public interface IPosOrderEntryFormListner {
		public void onExit();
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b) {
			try {
				setShiftInfo();
				setServiceSelection(DEF_ORDER_SERVICE, false);
			} catch (Exception e) {
				PosFormUtil.showSystemError(this);
			}
		} else {
			// mKeyboardManager.removeKeyEventPostProcessor(mCodeScanner);
		}

	}

	/*
	 * To Do make it configurable if needed Removed for new way of opening the
	 * order
	 * 
	 * private void openLastOrder(){ PosOrderHeaderObject openOrder =
	 * getOpenOrder(PosEnvSettings.getInstance() .getCashierShiftInfo());
	 * if(openOrder!=null){ setOrderRetrieved(openOrder); } }
	 */

	private void onMenuClicked() {
		
		if (PosAccessPermissionsUtil.validateAccess(this, PosEnvSettings
				.getInstance().getCashierShiftInfo().getCashierInfo()
				.getUserGroupId(), "change_menu")) {
			PosSaleMenuProvider saleMenuProvider = new PosSaleMenuProvider();
			ArrayList<BeanSaleMenuItem> mMenuItemList = saleMenuProvider
					.getList();
			IPosBrowsableItem[] itemList = new IPosBrowsableItem[mMenuItemList
			                                                     .size()];
			mMenuItemList.toArray(itemList);

			PosObjectBrowserForm objectBrowser = new PosObjectBrowserForm(
					"Menu", itemList, ItemSize.Wider);
			objectBrowser.setListner(new IPosObjectBrowserListner() {

				@Override
				public void onItemSelected(IPosBrowsableItem item) {
					BeanSaleMenuItem menu = (BeanSaleMenuItem) item;
					setMenuClassList(menu);
				}

				@Override
				public void onCancel() {
					// TODO Auto-generated method stub

				}
			});
			PosFormUtil.showLightBoxModal(this, objectBrowser);
		}
	}

	/**
	 * @param menu
	 */
	protected void setMenuClassList(BeanSaleMenuItem menu) {
		PosMenuDepartmentProvider menuDepartmentProvider = new PosMenuDepartmentProvider();
		PosItemClassProvider mainClassItemProvider =  PosItemClassProvider.getInstance();
		ArrayList<BeanItemClassBase> MainClassItems = new ArrayList<BeanItemClassBase>();

		BeanSaleMenuItem menuItem = menu;
		if (menuItem != null) {
			PosEnvSettings.getInstance().setMenu(menuItem);
			mHotItemPanel.getHotItemButtons().get(0)
			.setEnabled(menuItem.isEnableH1Button());
			mHotItemPanel.getHotItemButtons().get(1)
			.setEnabled(menuItem.isEnableH2Button());
			mHotItemPanel.getHotItemButtons().get(2)
			.setEnabled(menuItem.isEnableH3Button());
			mMessagePanel.setMenu(menuItem.getName());
		}
		ArrayList<BeanMenuDepartment> MenuDepartments = menuDepartmentProvider
				.getDepartments(menu);
		if (MenuDepartments != null) {
			for (BeanMenuDepartment department : MenuDepartments) {
				MainClassItems.addAll(mainClassItemProvider
						.getListByDepartment(department));
			}
		}

		if (MainClassItems != null) {
			if (classPanelSettings.showMainClassPanel()) {
				mMainClassListPanel.setClassList(MainClassItems);
			} else {
				mSubClassListPanel
				.setClassList(getAllSubClassItems(MainClassItems));
			}
			mAllPosSaleItemList = getAllSaleItems(MainClassItems, true);
		}

	}

	public PosOrderListPanel getBillGrid() {
		return mOrderListPanel;
	}

	public BeanCustomer getCustomerInfo() {
		return mSelectedCustomerInfo;
	}

	/**
	 * 
	 */
	private void onActionSelectTableWaiterClicked() {

		try {
			showRestaurantOrderForm();
		} catch (Exception e) {
			PosLog.write(this, "onActionSelectTableWaiterClicked", e);
			PosFormUtil.showSystemError(this);
		}
	}

	private void onLockClicked() {
		try {
			BeanCashierShift cashierShiftOld = PosEnvSettings.getInstance()
					.getCashierShiftInfo();
			PosUserAuthenticateForm loginForm = new PosUserAuthenticateForm(
					"Authenticate", mParent);
			loginForm.setCancelButtonVisible(false);
			PosFormUtil.showLightBoxModal(this, loginForm);
			loginForm.setVisible(false);
			loginForm.dispose();
			BeanUser user = loginForm.getUser();
			mCashierShiftProvider = new PosCashierShiftProvider();
			if (user != null&&PosAccessPermissionsUtil.validateAccess(this,user.getUserGroupId(), "order_entry")) {
				mCashierShiftNew = mCashierShiftProvider
						.getAllOpenCashierShifts().get(user.getCode());
				if (mCashierShiftNew == null) {
					if (mParent.doJoinShift(user)) {
						mCashierShiftNew = mCashierShiftProvider
								.getAllOpenCashierShifts().get(user.getCode());
						onCashierChanged(mCashierShiftNew);
					} else {
						onLockClicked();
					}
				} else if (mCashierShiftNew != null
						&& !user.getCode().equalsIgnoreCase(
								cashierShiftOld.getCashierInfo().getCode())) {
					onCashierChanged(mCashierShiftNew);
				}
			}
		} catch (Exception e) {
			PosFormUtil.showSystemError(this);
		}

	}

	/**
	 * @param cashierShiftNew
	 * @throws Exception
	 */
	private void onCashierChanged(BeanCashierShift CashierShift)
			throws Exception {
		BeanOrderHeader openOrder =null;// getOpenOrder(CashierShift); //No need for opening the last order when the user changes
		if (mIsSaved) {
			if (openOrder != null) {
				setOrderRetrieved(openOrder);
				mParent.setSectedCashier(mCashierShiftNew.getCashierInfo()
						.getCardNumber());
				setShiftInfo();
			} else {
				setOrderEntryForNewCashier();
			}
		} else {
			doSaveOrder();
			if (openOrder != null) {
				setOrderRetrieved(openOrder);
				mParent.setSectedCashier(mCashierShiftNew.getCashierInfo()
						.getCardNumber());
				setShiftInfo();
			} else {
				setOrderEntryForNewCashier();
			}
		}
	}

	/**
	 * @return
	 * Gets the last saved order.
	 */
	private BeanOrderHeader getOpenOrder(BeanCashierShift CashierShift) {

		BeanOrderHeader openOrder = null;
		try {
			openOrder = (new PosOrderHdrProvider())
					.getLatestOrderHeader("status in ("
							+ (PosOrderStatus.Open).getCode() + "," + (PosOrderStatus.Partial).getCode()+")"
							+ "and shift_id="
							+ CashierShift.getShiftItem().getId()
							+ " and user_id="
							+ CashierShift.getCashierInfo().getId());
		} catch (Exception e) {
			PosLog.write(this, "onCashierChanged", e);
		}

		return openOrder;
	}

	private void setOrderEntryForNewCashier() throws Exception {
		mParent.setSectedCashier(mCashierShiftNew.getCashierInfo()
				.getCardNumber());
		setShiftInfo();
		setServiceSelection(DEF_ORDER_SERVICE);
	}

	public void close() {
		mSingleToneInstace = null;
	}

	private void onActionNewOrderClicked() {

		if (mIsSaved){

			releaseOrder(mPosOrderEntryItem);
			startNewOrder();

		}else {
			showSaveOrderMessage(new PosMessageBoxFormListnerAdapter() {
				@Override
				public void onNoButtonPressed() {

					releaseOrder(mPosOrderEntryItem);
					startNewOrder();

				}

				@Override
				public void onYesButtonPressed() {
					showHoldOrderForm();
					startNewOrder();
				}
			});
		}
	}

	private boolean startNewOrder() {
		return startNewOrder(mSelectedServiceType);
	}

	private boolean startNewOrder(PosOrderServiceTypes service) {

		boolean isCancelled = false;
		try {
			resetPosOrderEntry();
			switch (service) {
			case HOME_DELIVERY:
				mSelectedServiceTable = mServiceTableProvider
				.getTableByServiceType(service);
			case SALES_ORDER:
				setPosOrderEntry(null);
				showsalesOrderFormOnNewOrderClicked();
				break;
			case TABLE_SERVICE:
				
				switch (PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getNewOrderUISetting().getTableSelectionUISettings().getTableWaiterSelectionMode()) {
				
					case BeanUITableSelectionSettings.TABLE_WAITER_SELECTION_MODE_NORMAL:
						isCancelled=showRestaurantOrderForm();
						break;
					case BeanUITableSelectionSettings.TABLE_WAITER_SELECTION_MODE_SILENT:
						isCancelled= showRestaurantOrderFormSimplefied(true);
						break;
					case BeanUITableSelectionSettings.TABLE_WAITER_SELECTION_MODE_WAITER_ONLY:
						isCancelled= showRestaurantOrderFormSimplefied(false);
						
						break;
					default:
						break;
				}
				
				break;
			case TAKE_AWAY:
				mSelectedServiceTable = mServiceTableProvider
				.getTableByServiceType(service);
				break;
			case WHOLE_SALE:
				//				isCancelled = showWholeSaleOrderForm();
				onCustomerInfoClicked(null);
				mSelectedServiceTable = mServiceTableProvider
						.getTableByServiceType(service);
				break;
				
			default:
				break;

			}
			if (!isCancelled) {
				
				if (!service.equals(PosOrderServiceTypes.HOME_DELIVERY) && 
						!service.equals(PosOrderServiceTypes.SALES_ORDER))
				setPosOrderEntry(null);
				
				setActionPanel();
			}
		} catch (Exception e) {
			PosLog.write(this, "startNewOrder", e);
			PosFormUtil.showSystemError(this);
		}

		return !isCancelled;
	}

	/*
	 * 
	 * 
	 */
		public void showsalesOrderFormOnNewOrderClicked() throws Exception{
			
			
//			boolean result=false;
			
			final showSOInfo showSo=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getShowSalesOrderDetail();
			if(showSo.equals(showSOInfo.AtNewOrder) || showSo.equals(showSOInfo.Both)) {
				
				final PosSalesOrderInfoForm salesOrderForm=new PosSalesOrderInfoForm(this, mPosOrderEntryItem);
				PosFormUtil.showLightBoxModal(this,salesOrderForm);
				if(!salesOrderForm.isDirty())
					resetPosOrderEntry();
				else
					setCustomer(mPosOrderEntryItem.getCustomer(),mPosOrderEntryItem.getOrderCustomer());
			}
			 
		}
/*
 * 
 * 
 */
	public boolean showsalesOrderForm(){
		
		boolean result=true;
		final showSOInfo showSo=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getShowSalesOrderDetail();
		if(showSo.equals(showSOInfo.AtParking) || showSo.equals(showSOInfo.Both)) {
			
			final PosSalesOrderInfoForm salesOrderForm=new PosSalesOrderInfoForm(this, mPosOrderEntryItem);
			PosFormUtil.showLightBoxModal(this,salesOrderForm);
			result=salesOrderForm.isDirty();
				result=true;
			
		}
		return result;
	}
//	/**
//	 * 
//	 */
//	private boolean showWholeSaleOrderForm(){
//
//		final PosWholeSaleInfoForm form=new PosWholeSaleInfoForm();
//		PosFormUtil.showLightBoxModal(this,form);
//		if(!form.isCancelled()){
//
//			onCustomerSelected.onOkClicked(form.getCustomer());
//			if (mPosOrderEntryItem != null){
//
//				mPosOrderEntryItem.setVehicleNumber(form.getVehicleNumber());
//				mPosOrderEntryItem.setDriverName(form.getDriverName());
//			}
//		}
//
//		return form.isCancelled();
//	}

	private boolean showRestaurantOrderForm() throws Exception {

		boolean isCancelled = true;
		final PosServiceTableSelectionForm form = new PosServiceTableSelectionForm();
		form.setOrderTables(mOrderServiceTables);
		form.setOrderHeader(mPosOrderEntryItem);
		form.setSelectedWaiter(PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo());
		form.setListener(new PosServiceTableSelectionForm.IPosServiceTableSelectionFormListener() {

			@Override
			public void onOkButtonClicked() {



			}

			@Override
			public void onCancelButtonClicked() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResetClicked() {

				form.setOrderTables(mOrderServiceTables);
			}
		});

		PosFormUtil.showLightBoxModal(this, form);

		if (!form.isCancelled()) {

			mOrderServiceTables=PosOrderUtil.updateOrderTables(mOrderServiceTables,form.getOrderTables());

			final String selTableCode=form.getSelectedServiceTableCode();

			if(selTableCode!=null && mOrderServiceTables.containsKey(selTableCode))
				mSelectedServiceTable = mOrderServiceTables.get(selTableCode);

			mCovers=form.getSelectedCovers();
			mSelectedServedBy = form.getSelectedWaiter();
			mSelectedServiceTable=PosOrderUtil.getSelectedServiceTable(form.getSelectedServiceTableCode(),mOrderServiceTables);
			mCovers=form.getPax();
			if(mCovers<=0){

				if(mOrderServiceTables!=null && mOrderServiceTables.size()>0){

					for(BeanOrderServingTable tbl:mOrderServiceTables.values()){

						mCovers+=tbl.getSeatCount();
					}
				}
			}
			isCancelled = false;
		}

		form.dispose();
		return isCancelled;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	private boolean showRestaurantOrderFormSimplefied(boolean isSilent) throws Exception {
		
		boolean isCancelled = true;

		final BeanServingTable sysTable=(new PosServiceTableProvider().getSysTableNA());  
		mOrderServiceTables=new HashMap<String, BeanOrderServingTable>();
		mOrderServiceTables.put(sysTable.getCode(), new BeanOrderServingTable(sysTable));

//		final String selTableCode=sysTable.getCode();
		mSelectedServiceTable = sysTable;

		mCovers=0;
		ArrayList<BeanEmployees> mWaiterList=(new PosWaiterProvider().getWaiterList());
		if(!isSilent && mWaiterList.size()>1){
			
			PosObjectBrowserForm mBrowseForm=new PosObjectBrowserForm("Select waiter", mWaiterList.toArray(new BeanEmployees[mWaiterList.size()]), ItemSize.Wider,4,3);
			mBrowseForm.setListner(new IPosObjectBrowserListner() {
				@Override
				public void onItemSelected(IPosBrowsableItem item) {
					mSelectedServedBy=(BeanEmployees)item;
				}

				@Override
				public void onCancel() {
			
				}
			});		
			
			PosFormUtil.showLightBoxModal(this,mBrowseForm);
		}else
			mSelectedServedBy = mWaiterList.get(0);
		
		isCancelled = (mSelectedServedBy==null);

		return isCancelled;
	}

	private void onActionSaveOrderClicked() {
		showHoldOrderForm();
		//		startNewOrder();
	}

	public BeanServingTable getSelectedServingTable() {
		return mSelectedServiceTable;
	}

	public BeanEmployees getSelectedWaiter() {
		return mSelectedServedBy;
	}

	/**
	 * @return
	 */
	public PosOrderServiceTypes getSelectedServiceType() {
		return mSelectedServiceType;
	}

	/**
	 * 
	 */
	private void onActionInfoClicked() {

		if (mPosOrderEntryItem != null 
				&& mPosOrderEntryItem.getOrderDetailItems()!=null 
				&& mPosOrderEntryItem.getOrderDetailItems().size()>0) {

			
			if(mSelectedServiceType==PosOrderServiceTypes.SALES_ORDER || mSelectedServiceType==PosOrderServiceTypes.HOME_DELIVERY){
				final PosSalesOrderInfoForm salesOrderForm=new PosSalesOrderInfoForm(PosOrderEntryForm.this, mPosOrderEntryItem);

				PosFormUtil.showLightBoxModal(PosOrderEntryForm.this,
					salesOrderForm);
				if(salesOrderForm.isDirty()){
					try{
						saveSalesOrder();

					} catch (Exception e) {
	
						PosFormUtil
						.showErrorMessageBox(this,
								"Failed to print. Please check printer.");
						e.printStackTrace();
					}
				}
			}else{
			
				PosOrderUtil.updateOrderHdrFromGrid(mPosOrderEntryItem, getBillGrid());
				mOrderInfoForm = new PosOrderInfoForm(this,
						mPosOrderEntryItem);
				mOrderInfoForm.setListner(orderInfoListner);
				PosFormUtil.showLightBoxModal(this, mOrderInfoForm);
			}

		}else{

			PosFormUtil.showErrorMessageBox(PosOrderEntryForm.this,
					"There are no items in the order.");
		}

	}

	private void orderInfoOkClicked(){

		if(mOrderInfoForm.isEdited()){

			mSelectedServiceTable = mPosOrderEntryItem.getServiceTable();
			mCovers=mPosOrderEntryItem.getCovers();
			if(mSelectedServiceType != mPosOrderEntryItem.getOrderServiceType() && 
					mPosOrderEntryItem.getOrderServiceType()==PosOrderServiceTypes.HOME_DELIVERY){
					
					
					mPosOrderEntryItem.setDueDateTime(PosDateUtil.getDateTime());
					mPosOrderEntryItem.setOrderByMedium(PosOrderMedium.DIRECT);
					mPosOrderEntryItem.setDeliveryType(PosOrderServiceTypes.HOME_DELIVERY);
					mPosOrderEntryItem.setAdvancePaymentMode(PaymentMode.Cash);
					mPosOrderEntryItem.setExtraCharges(0);
					mPosOrderEntryItem.setAdvanceAmount(0);
				 
			} 
				
			mSelectedServiceType = mPosOrderEntryItem.getOrderServiceType();
			setCustomer(mPosOrderEntryItem.getCustomer(),mPosOrderEntryItem.getOrderCustomer());
			mOrderActionPanel.setServiceTypeIfno(mSelectedServiceType,
					mSelectedServiceTable);
			mOrderActionPanel.onNewOrderStarted(mPosOrderEntryItem);
			mOrderListPanel.setPosOrderEntryItem(mPosOrderEntryItem);
			resetMessagePanel();
			mItemListPanel.refreshItemList();
			mIsSaved = false;
		}
	}


	/*
	 * 
	 */
	private IPosOrderInfoFormListner orderInfoListner=new IPosOrderInfoFormListner() {

		@Override
		public void onItemSaved(Object sender, BeanOrderHeader item) {

			try{

				orderInfoOkClicked();
				showHoldOrderForm();
			} catch (Exception e) {

				PosLog.write(this, "onInfoSaved", e);
				PosFormUtil
				.showErrorMessageBox(PosOrderEntryForm.this,
						"Failed to save the current order. Please check the log for details.");
			}

		}

		@Override
		public void onOkClicked(Object sender, BeanOrderHeader item) {

			orderInfoOkClicked();
		}

		@Override
		public void onPaymentButtonClicked(Object sender,
				PosPaymentOption paymentOption, BeanOrderHeader item) {

			orderInfoOkClicked();
			mPaymentMethodsPanel.doPayment(paymentOption);

		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.components.orderentry.listners.IPosActionPanelListnerBase
	 * #onPosActionButtonClicked(com.indocosmo.pos.components.orderentry.
	 * PosActionPanelBase.OrderEntryMenuActions, java.lang.Object)
	 */
	@Override
	public void onPosActionButtonClicked(OrderEntryMenuActions menuAction,
			Object data) {

		try {
			switch (menuAction) {
			case ORDER_NEW:
				onActionNewOrderClicked();
				break;
			case ORDER_RETRIEVE:
				onActionOpenOrderClicked();
				break;
			case ORDER_SAVE:
				onActionSaveOrderClicked();
				break;
			case ORDER_INFO:
				onActionInfoClicked();
				break;
			case ATTENDANCE:
				onAttendanceClicked();
				break;
			case CUSTOMER:
				onCustomerInfoClicked((mSelectedCustomerInfo!=null 
				&& !mSelectedCustomerInfo.getCode().equals(PosCustomerProvider.DEF_CUST_CODE))?
						mSelectedCustomerInfo.getCardNumber():null);
				break;
			case EXIT:
				onExitClicked();
				break;
			case LOCK:
				onLockClicked();
				break;
			case MENU:
				onMenuClicked();
				break;
			case ORDERDETAILS:
				PosFormUtil.showOrderListForm(this,true);
				break;
				//			case ORDERREFUND:
				//				PosFormUtil.showOrderRefundForm(this,true);
				//				break;
			case CASHOUT:
				PosFormUtil.showCashOutForm(this,true);
				break;
			case SHIFTSUMMARY:
				PosFormUtil.showShiftSummaryForm(this,true);
				break;
			default:
				break;
			}
		} catch (Exception ex) {

			PosLog.write(this, "onPosActionButtonClicked", ex);
			PosFormUtil.showSystemError(PosOrderEntryForm.this);
		}
 
	}



	/**
	 * @param service
	 */
	private void changeServiceSelection(final PosOrderServiceTypes service) {

		if (mIsSaved){

			releaseOrder(mPosOrderEntryItem);
			setServiceSelection(service);

		}else {
			showSaveOrderMessage(new PosMessageBoxFormListnerAdapter() {
				@Override
				public void onNoButtonPressed() {

					releaseOrder(mPosOrderEntryItem);
					setServiceSelection(service);

				}

				@Override
				public void onYesButtonPressed() {
					showHoldOrderForm();
					setServiceSelection(service);
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.components.orderentry.listners.
	 * IPosActionPanelRestaurantListner
	 * #onPosActionButtonClicked(com.indocosmo.pos
	 * .components.orderentry.PosActionPanelRestaurant
	 * .RestaurantOrderEntryMenuActions, java.lang.Object)
	 */
	@Override
	public void onPosActionButtonClicked(
			RestaurantOrderEntryMenuActions menuAction, Object data) {

		switch (menuAction) {
		case TABLE_SELECTION:
		case WAITER_SELECTION:
			onActionSelectTableWaiterClicked();
			break;
		case SERVICE_SELECTION:
			changeServiceSelection((PosOrderServiceTypes) data);
		default:
			break;
		}
	}

	/***
	 * Validates the current active order before adding items
	 * 
	 * @return
	 */
	public boolean validateNewOrder() {

		boolean isValid = true;
		if (!checkForActiveOrder(false)) {
			//			if(PosEnvSettings.getInstance().getStation().getServiceType() == PosTerminalServiceType.Counter || !PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getNewOrderUISetting().isConfirmServiceRequired()){
			if(!PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getNewOrderUISetting().isConfirmServiceRequired()){
				onActionNewOrderClicked();
				isValid=(mPosOrderEntryItem!=null);
			}else if (PosFormUtil
					.showQuestionMessageBox(
							this,
							MessageBoxButtonTypes.YesNo,
							"No active order!!!. Do you want to start new Order ("+ mSelectedServiceType.getDisplayText() +").",
							null) == MessageBoxResults.Yes) {
				onActionNewOrderClicked();
				isValid = (mPosOrderEntryItem != null);
			} else
				isValid = false;
		}
		return isValid;
	}

	/**
	 * @param showMessage
	 * @return
	 */
	public boolean checkForActiveOrder(boolean showMessage){

		boolean isValid=true;
		if (mPosOrderEntryItem == null) {
			if(showMessage)
				PosFormUtil.showErrorMessageBox(this, "No active order!!! Please start/open order.");
			isValid = false;
		}

		return isValid;

	}

	/**
	 * @return
	 */
	public boolean IsPartiallyPaidOrder(){

		boolean isValid = false;
		if(mPosOrderEntryItem!=null && mPosOrderEntryItem.getStatus()==PosOrderStatus.Partial){
			isValid = true; 
		}
		return isValid;
	}

	/**
	 * @return
	 */
	public boolean IsParked(){

		boolean isValid = false;
		if(mCurOrderEntryMode!=PosOrderEntryMode.New){
			isValid = true;
		}
		return isValid;
	}


	/**
	 * @return
	 */
	private boolean checkForPrinting(){

		boolean result=true;
		if(!checkForActiveOrder(true)){
			result=false;
		}else if(getBillGrid().getItemList().size()<=0) {
			PosFormUtil.showErrorMessageBox(this,"There are no items in the order.");
			result=false;
		}else if(getBillGrid().getBillItemCount()==0) {
			PosFormUtil.showErrorMessageBox(this,"There are no items in the order.");
			result=false;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.components.orderentry.listners.IPosOperationsListener
	 * #onKitchenPrintClicked()
	 */
	@Override
	public void onKitchenPrintClicked() {

		if(!checkForPrinting())
			return;

		BeanOrderHeader orderHdr = PosOrderUtil
				.buildOrderHeaders(false,getBillGrid());
		if(orderHdr.getOrderDetailItems()==null||orderHdr.getOrderDetailItems().size()<1){
			return;
		}
		
		if (PosOrderUtil.isDeliveryService(orderHdr)&& orderHdr.isNewOrder()  ){
			if (!showsalesOrderForm()) 
				return ;
		}


		final MessageBoxResults msgBoxResult=PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNoCancel,"Print new/modified item[s] only?", null);
		if(msgBoxResult!=MessageBoxResults.Cancel){

//			if (orderHdr.isNewOrder()){
//				try {
//
//					final String queueNo=new PosOrderQueueProvider().getOrderQueueNo(orderHdr);
//					orderHdr.setQueueNo(queueNo);
//				} catch (Exception e) {
//
//					PosLog.write(this, "onKitchenPrintClicked", e);
//					PosFormUtil
//					.showErrorMessageBox(PosOrderEntryForm.this,
//							"Failed to save the current order. Please check the log for details.");
//					return;
//
//				}
//			}
			
			doSaveOrder(false);
			StringBuilder printResMsg=new StringBuilder("");
			if(printToKitchen(orderHdr, (msgBoxResult==MessageBoxResults.Yes)?false:true,printResMsg)){


				try {

					PosOrderUtil.setAsPrintedToKitchen(mPosOrderEntryItem);
					mOrderDtlProvider.updatePrintStatus(mPosOrderEntryItem.getOrderId());
//					doSaveOrder(false);
					
					mPosOrderEntryItem.setNewOrder(false);
					PosFormUtil.showInformationMessageBox(this, printResMsg.toString() + "<P>Order is saved." );

					setOrderRetrieved(mPosOrderEntryItem);

				} catch (Exception e) {

					PosLog.write(this, "onKitchenPrintClicked", e);
					PosFormUtil
					.showErrorMessageBox(PosOrderEntryForm.this,
							"Failed to save the current order. Please check the log for details.");
				}
				mIsSaved = true;
			}else
				PosFormUtil.showErrorMessageBox(this, printResMsg.toString()  );
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.components.orderentry.listners.IPosMiscOperationsListener
	 * #onVoidButtonClicked()
	 */
	@Override
	public void onVoidButtonClicked() {

		if(checkForPrinting()) {

			if(mPosOrderEntryItem.isNewOrder()){

				releaseOrder(mPosOrderEntryItem);
				startNewOrder();
			}else{
				if(PosOrderUtil.voidOrder(PosOrderEntryForm.this, mPosOrderEntryItem)){

					releaseOrder(mPosOrderEntryItem);
					startNewOrder();
				}

			}
		}


	}

	/**
	 * @param orderId
	 */
	public void retrieveOrder(String orderId) {

		try {

			BeanOrderHeader oh=mOrderHdrProvider.getOrderData(orderId);
			setOrderRetrieved(oh);
		} catch (Exception e) {

			PosLog.write(this, "retrieveOrder", e);
			PosFormUtil.showSystemError(PosOrderEntryForm.this);
		}

	}

	/**
	 * @return
	 */
	public BeanOrderHeader getOrderObject(){

		return mPosOrderEntryItem;
	}

	/**
	 * @param mPosOrderEntryItem2
	 * @return
	 */
	private boolean releaseOrder(BeanOrderHeader oh){

		boolean released=true;

		if(oh!=null)
			released=PosOrderUtil.releaseOrder(oh.getOrderId());

		return released;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.scanner.PosWMBarcodeScannerHandler.IPosWMBarcodeScannerActionLitener#onWMBarcodeScanned(com.indocosmo.pos.common.utilities.scanner.BeanWMBarcodeDtl)
	 */
	@Override
	public boolean onWMBarcodeScanned(BeanWeighingBarcodeDtl dtl) {

		boolean isProcessed=false;
		BeanSaleItem item=null;

		switch (dtl.getCodeType()) {
		case BarCode:
			item = mBarcodeItemMap.get(dtl.getCode());
			break;
		case ItemCode:
			item = mItemcodeItemMap.get(dtl.getCode());
			break;
		default:
			break;
		}

		if (item != null){

			double value=dtl.getValue();
			BeanSaleItem saleItem=item.clone();
			PosTaxUtil.calculateTax(saleItem);
			saleItem.setRequireWeighing(false);
			saleItem.setQuantity(value);
			onItemSelectedListner.onItemSelected(saleItem);
			isProcessed=true;
		}

		return isProcessed;
	}


	/**
	 * @param saleItem
	 * @param dtl
	 * @return
	 */
	private boolean confirmTrayBarcodeScanning(BeanSaleItem saleItem, BeanWeighingBarcodeDtl dtl ){

		final String message="Tray weight (" 
				+ PosUomUtil.format(dtl.getValue(), PosUOMProvider.getInstance().getMaxDecUom())
				+ saleItem.getUom().getSymbol()
				+ ") will be reduced for item \"" 
				+ saleItem.getName() 
				+ "\" ("
				+ PosUomUtil.format(saleItem.getQuantity(), PosUOMProvider.getInstance().getMaxDecUom())
				+ saleItem.getUom().getSymbol()
				+  "). " 
				+ "Do you want to continue?";

		final MessageBoxResults result=  PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNo,message, null);
		
		return result==MessageBoxResults.Yes;   
	} 

	/**
	 * @param dtl
	 */
	private void updateTrayWeight(BeanWeighingBarcodeDtl dtl){

		final BeanOrderDetail selectedItem=mOrderListPanel.getSelectedItemControl().getOrderDetailItem();
		final String newValueString="{"+selectedItem.getSaleItem().getQuantity()+","+String.valueOf(dtl.getValue())+"}";
		final double qty=selectedItem.getSaleItem().getQuantity()-dtl.getValue();

		mOrderListPanel.onQuantityChanged(qty);

		String trayCode=selectedItem.getTrayCode();
		String trayWeight=selectedItem.getTrayWeight();

		trayCode=(trayCode!=null && trayCode!="" ) ? trayCode+ "," + dtl.getCode() :dtl.getCode() ;
		trayWeight=(trayWeight!=null && trayWeight!="" ) ? trayWeight+ "," + newValueString : newValueString ;

		selectedItem.setTrayCode(trayCode);
		selectedItem.setTrayWeight(trayWeight);
		mOrderListPanel.getSelectedItemControl().refresh();

	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.codescanner.handlers.PosItemTrayBarcodeScannerHandler.IPosItemTrayBarcodeScannerActionLitener#onItemTrayBarcodeScanned(com.indocosmo.pos.common.utilities.codescanner.bean.BeanItemTrayBarcodeDtl)
	 */
	@Override
	public boolean onEmptyTrayBarcodeScanned( BeanWeighingBarcodeDtl dtl) {

		boolean isProcessed=true;
		final BeanOrderDetail selectedItem=mOrderListPanel.getSelectedItemControl().getOrderDetailItem();
		boolean confirmBarcodeScanning=true; 

		if (PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().isConfirmTrayWeightUpdate())
			confirmBarcodeScanning=confirmTrayBarcodeScanning(selectedItem.getSaleItem(),dtl);

		if(confirmBarcodeScanning )
			updateTrayWeight(dtl);

		return isProcessed;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.codescanner.handlers.PosItemTrayBarcodeScannerHandler.IPosItemTrayBarcodeScannerActionLitener#onItemTrayBarcodeScanned(com.indocosmo.pos.common.utilities.codescanner.bean.BeanWeighingBarcodeDtl)
	 */
	@Override
	public boolean onItemTrayBarcodeScanned(BeanWeighingBarcodeDtl dtl) {

		boolean isProcessed=false;
		BeanSaleItem item=null;

		switch (dtl.getCodeType()) {
		case BarCode:
			item = mBarcodeItemMap.get(dtl.getCode());
			break;
		case ItemCode:
			item = mItemcodeItemMap.get(dtl.getCode());
			break;
		case Any:
			item = mItemcodeItemMap.get(dtl.getCode());
			if(item==null)
				item = mBarcodeItemMap.get(dtl.getCode());
			break;
		
		}

		if (item != null){

			BeanSaleItem saleItem=item.clone();

			final double newQty=  (saleItem.isRequireWeighing()? mOrderListPanel.getWeightFromWeighingMachine() : saleItem.getQuantity());
			saleItem.setQuantity(newQty);
			saleItem.setRequireWeighing(false);
			PosTaxUtil.calculateTax(saleItem);

			boolean confirmBarcodeScanning=true; 
			if (PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().isConfirmTrayWeightUpdate())
				confirmBarcodeScanning=confirmTrayBarcodeScanning(saleItem,dtl);

			if(confirmBarcodeScanning ){

				onItemSelectedListner.onItemSelected(saleItem);
				updateTrayWeight(dtl);
			}

			isProcessed=true;
		}

		return isProcessed;

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.scanner.PosUserCardScannerHandler.IPosUserCodeScannerActionLitener#onUserCardScanned(java.lang.String)
	 */
	@Override
	public boolean onUserCardScanned(String cardNumber) {

		mParent.doAttendance(this, cardNumber);	

		return true;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.scanner.PosMemberCardScannerHandler.IPosMemberCodeScannerActionLitener#onMemberCardScanned(java.lang.String)
	 */
	@Override
	public boolean onMemberCardScanned(String cardNumber) {

		onCustomerInfoClicked(cardNumber);

		return true;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.scanner.PosCodeScanner.IPosDefaultCodeScanListner#onCodeScanned(java.lang.String)
	 */
	@Override
	public boolean onCodeScanned(String code) {

		onBarcodeScanned(code.toUpperCase());

		return true;

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.scanner.handlers.PosOrderBarcodeScannerHandler.IPosOrderBarcodeScannerActionLitener#onOrderCodeScanned(java.lang.String)
	 */
	@Override
	public boolean onOrderCodeScanned(String order) {

		boolean orderFound=false;

		if(mPosOrderEntryItem==null || !mPosOrderEntryItem.getOrderId().equals(order)){

			try {
				final BeanOrderHeader oh = mOrderHdrProvider.getOpenOrderById(order);
				if(oh!=null){
					
					orderFound=true;
					if(PosOrderUtil.getLock(this,oh)){

						if(oh!=null){

							showOrderRetriveForm(oh);
						}
					}
				}
			} catch (Exception e) {

				PosLog.write(this, "onOrderCodeScanned", e);
			}
		}
		return orderFound;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.codescanner.handlers.PosScannerHandler.IPosScannerActionLitener#defaultEmptyCodeScanned()
	 */
	@Override
	public boolean defaultEmptyCodeScanned() {

		//		mOrderListPanel.onEditClicked();
		return true;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.orderentry.listners.IPosMiscOperationsListener#onWMButtonClicked()
	 */
	@Override
	public void onWMButtonClicked() {

		if(mPosOrderEntryItem==null) return;

		if(mOrderListPanel.getSelectedItemControl()==null){

			PosFormUtil.showErrorMessageBox(this, "No Item is selected. Please select an item.");
			return;
		}

		try {

			final double weight=PosDeviceManager.getInstance().getWeighingMachine().requestForWeight();
			if(weight>0){

				if(PosFormUtil.showQuestionMessageBox(
						this, 
						MessageBoxButtonTypes.YesNo, 
						"Do you want to update the qunatity of item [" + 
								mOrderListPanel.getSelectedItemControl().getOrderDetailItem().getSaleItemName()+
								"] with "+ weight +"?", 
								null)==MessageBoxResults.Yes){

					mOrderListPanel.onQuantityChanged(weight);
				}

			}else
				PosFormUtil.showErrorMessageBox(this, "Not able to get weight from weighing machine. Please check machine.");

		} catch (Exception e) {

			PosFormUtil.showErrorMessageBox(this, "Not able to get weight from weighing machine. Please check machine or contact administrator.");
			PosLog.write(this, "onWMButtonClicked", e);
		}


	}

	/**
	 * @param mWindowInstance
	 */
	public void setActiveWindow(Object window) {

		activeWindow=window;

	}

	/**
	 * @return
	 */
	public Object getActiveWindow(){

		return activeWindow;

	}

	/*
	 * 
	 */
	public boolean saveOrderBeforePayment() throws Exception{
		
		boolean result=true;
		if (PosOrderUtil.isDeliveryService(mPosOrderEntryItem) && mPosOrderEntryItem.isNewOrder()){
			
			if (! showsalesOrderForm()) 
				 result=false;
			else
			 	saveSalesOrder();
//			PosOrderHdrProvider orderHdrProvider=new PosOrderHdrProvider();
//			order =orderHdrProvider.getOrderData(order.getOrderId());
			
		}else if (PosOrderUtil.isDeliveryService(mPosOrderEntryItem) )
			setExtraChargeTax();
		return result;
	 
	}
	/*
	 * set the highest tax rate from item list 
	 */
	public void setExtraChargeTax(){
		
		
		if(mPosOrderEntryItem.getExtraCharges()==0){
			resetExtraChargeTax(mPosOrderEntryItem);
			return;
		}
		
		BeanTax taxObject=null;
		for (BeanOrderDetail orderDet:mPosOrderEntryItem.getOrderDetailItems()){
			
			if(orderDet.isVoid())
				continue;
			
			if(taxObject==null)
				taxObject=orderDet.getSaleItem().getTax();
			else if(PosTaxUtil.getTotalTaxRate(orderDet.getSaleItem().getTax())>PosTaxUtil.getTotalTaxRate(taxObject))
				taxObject=orderDet.getSaleItem().getTax();
		}
		
		if(taxObject==null ) 
			resetExtraChargeTax(mPosOrderEntryItem);
		else{
			
			mPosOrderEntryItem.setExtraChargeTaxId(taxObject.getId());
			mPosOrderEntryItem.setExtraChargeTaxCode(taxObject.getCode());
			mPosOrderEntryItem.setExtraChargeTaxName(taxObject.getName());
			
			mPosOrderEntryItem.setExtraChargeTaxOneName(taxObject.getTaxOneName());
			mPosOrderEntryItem.setExtraChargeTaxOnePercentage(taxObject.getTaxOnePercentage());
			mPosOrderEntryItem.setExtraChargeTaxOneAmount(mPosOrderEntryItem.getExtraCharges()*taxObject.getTaxOnePercentage()/100);
			
			mPosOrderEntryItem.setExtraChargeTaxTwoName(taxObject.getTaxTwoName());
			mPosOrderEntryItem.setExtraChargeTaxTwoPercentage(taxObject.getTaxTwoPercentage());
			mPosOrderEntryItem.setExtraChargeTaxTwoAmount(mPosOrderEntryItem.getExtraCharges()*taxObject.getTaxTwoPercentage()/100);
			
			mPosOrderEntryItem.setExtraChargeTaxThreeName(taxObject.getTaxThreeName());
			mPosOrderEntryItem.setExtraChargeTaxThreePercentage(taxObject.getTaxThreePercentage());
			mPosOrderEntryItem.setExtraChargeTaxThreeAmount(mPosOrderEntryItem.getExtraCharges()*taxObject.getTaxThreePercentage()/100);
			
			mPosOrderEntryItem.setExtraChargeSCName(taxObject.getServiceTaxName());
			mPosOrderEntryItem.setExtraChargeSCPercentage(taxObject.getServiceTaxPercentage()); 
			mPosOrderEntryItem.setExtraChargeSCAmount(mPosOrderEntryItem.getExtraCharges()*taxObject.getServiceTaxPercentage()/100);
			
			mPosOrderEntryItem.setExtraChargeGSTName(taxObject.getGSTName());
			mPosOrderEntryItem.setExtraChargeGSTPercentage(taxObject.getGSTPercentage()); 
			mPosOrderEntryItem.setExtraChargeGSTAmount(mPosOrderEntryItem.getExtraCharges()*taxObject.getGSTPercentage()/100);
			
		}
			
	}
	private void resetExtraChargeTax(BeanOrderHeader ordHeader){
		
		ordHeader.setExtraChargeTaxId(0);
		ordHeader.setExtraChargeTaxCode("");
		ordHeader.setExtraChargeTaxName("");
		
		ordHeader.setExtraChargeTaxOneName("");
		ordHeader.setExtraChargeTaxOnePercentage(0);
		ordHeader.setExtraChargeTaxOneAmount(0);
		
		ordHeader.setExtraChargeTaxTwoName("");
		ordHeader.setExtraChargeTaxTwoPercentage(0);
		ordHeader.setExtraChargeTaxTwoAmount(0);
		
		ordHeader.setExtraChargeTaxThreeName("");
		ordHeader.setExtraChargeTaxThreePercentage(0);
		ordHeader.setExtraChargeTaxThreeAmount(0);
		
		ordHeader.setExtraChargeSCName("");
		ordHeader.setExtraChargeSCPercentage(0); 
		ordHeader.setExtraChargeSCAmount(0);
		
		ordHeader.setExtraChargeGSTName("");
		ordHeader.setExtraChargeGSTPercentage(0); 
		ordHeader.setExtraChargeGSTAmount(0);
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosMessageListner#showMessage(java.lang.String)
	 */
	@Override
	public void showMessage(String message) {
		 
		mScrollMsgPanel.showMessage(message);
		mMessagePanel.setVisible(false);
 		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosMessageListner#showLogoImage()
	 */
	@Override
	public void hideMessage() {
		
		mScrollMsgPanel.setVisible(false);
		mMessagePanel.setVisible(true);
	}
	
	/**
	 * @param message
	 */
	public static void SET_MESSAGE(String message) {
		
		if(mSingleToneInstace!=null ) {
			if(message!=null && message.trim().length()>0)
				mSingleToneInstace.showMessage(message);
			else
				mSingleToneInstace.hideMessage();
		
		}
 		
	}

 
	
}
