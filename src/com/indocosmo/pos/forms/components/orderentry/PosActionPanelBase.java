package com.indocosmo.pos.forms.components.orderentry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.beans.settings.ui.mainmenu.BeanUIMenuListPanelSettings;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.providers.shopdb.PosFlashMessageProvider;
import com.indocosmo.pos.forms.PosFlashMessageListForm;
import com.indocosmo.pos.forms.PosFlashMessagesForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosIconButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosActionPanelListnerBase;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosActionPanelRestaurantListner;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;

@SuppressWarnings("serial")
public abstract class PosActionPanelBase extends JPanel{

	/***
	 *Default Order Entry Actions
	 * 
	 */
	public enum OrderEntryMenuActions{
		ORDER_NEW,
		ORDER_SAVE,
		ORDER_RETRIEVE,
		ORDER_INFO,
		CUSTOMER,
		ATTENDANCE,
		MORE,
		MENU,
		VIEW_MSG,
		LOCK,
		EXIT, ORDERDETAILS, ORDERREFUND, CASHOUT, SHIFTSUMMARY
	}

	protected static final int PANEL_CONTENT_V_GAP=4;
	protected static final int PANEL_CONTENT_H_GAP=4;

	protected static final int WIDE_BUTTON_HEIGHT=47;
	protected static final int WIDE_BUTTON_WIDTH=90;
	protected static final int NORM_BUTTON_HEIGHT=WIDE_BUTTON_HEIGHT*2+PANEL_CONTENT_V_GAP;
	protected static final int NORM_BUTTON_WIDTH=82;

	protected static final int SEPERATER_WIDTH=1;
	protected static final int SEPERATER_HEIGHT=NORM_BUTTON_HEIGHT*3/4;

	public static final Color  SEPERATER_BG_COLOR=Color.GRAY;

	public static  int LAYOUT_WIDTH=WIDE_BUTTON_WIDTH*2+NORM_BUTTON_WIDTH*4+PANEL_CONTENT_H_GAP*(6+2+3+2);
	public static  int LAYOUT_HEIGHT=NORM_BUTTON_HEIGHT+PANEL_CONTENT_V_GAP*2;

	public static final Color PANEL_BG_COLOR=PosOrderEntryForm.PANEL_BG_COLOR;

	protected static final String IMAGE_BUTTON_NORMAL="action_panel_button.png";
	protected static final String IMAGE_BUTTON_NORMAL_TOUCHED="action_panel_button_touch.png";

	protected static final String IMAGE_BUTTON_WIDE="action_panel_button_wide.png";
	protected static final String IMAGE_BUTTON_WIDE_TOUCHED="action_panel_button_wide_touch.png";

	private static final String IMAGE_BUTTON_EXIT_NORMAL="top_menu_button_exit.png";
	private static final String IMAGE_BUTTON_EXIT_TOUCHED="top_menu_button_exit_touch.png";
	private static final String IMAGE_BUTTON_LOCK_NORMAL="top_menu_button_lock.png";
	private static final String IMAGE_BUTTON_LOCK_TOUCHED="top_menu_button_lock_touch.png";

	private static final String IMAGE_BUTTON_MORE_NORMAL="top_menu_button_more.png";
	private static final String IMAGE_BUTTON_MORE_TOUCHED="top_menu_button_more_touch.png";

	protected ImageIcon mImageButtonNormal;
	protected ImageIcon mImageButtonNormalTouch;
	protected ImageIcon mImageButtonWide;
	protected ImageIcon mImageButtonWideTouch;

	private ImageIcon mExitImageButtonNormal;
	private ImageIcon mExitImageButtonTouch;
	private ImageIcon mLockImageButtonNormal;
	private ImageIcon mLockImageButtonTouch;

	protected IPosActionPanelListnerBase mPosActionPanelListner;
	protected PosOrderServiceTypes mServiceType = PosOrderServiceTypes.TAKE_AWAY;

	protected PosIconButton mButtonNew;
	protected PosIconButton mButtonSave;
	protected PosIconButton mButtonOpen;
	protected PosIconButton mButtonOrderInfo;

	protected PosIconButton mButtonCustomerInfo;
	protected PosIconButton mButtonMore;
	protected PosIconButton mButtonMenu;
	protected PosIconButton mButtonPosMessages;

	protected PosIconButton mButtonLock;
	protected PosIconButton mButtonExit;

	protected ArrayList<PosMoreMenuItem> mMoreActionList;
	protected RootPaneContainer mParent;
	protected BeanOrderHeader mPosOrderItem;

	/***
	 * Default constructor
	 */
	public PosActionPanelBase(RootPaneContainer parent) {
		this.mParent=parent;
		mMoreActionList=new ArrayList<PosMoreMenuItem>();
		prepareMoreActionList();
		initComponent();
	}

	/**
	 * @param code
	 * @param caption
	 * @param action
	 */
	protected void AddToMoreActionList(String code, String caption, OrderEntryMenuActions action){

		AddToMoreActionList(new PosMoreMenuItem(code,caption, action));
	}
	/**
	 * @param posMoreMenuItem
	 */
	protected void AddToMoreActionList(PosMoreMenuItem menuItem) {

		mMoreActionList.add(menuItem);
	}

	/**
	 * 
	 */
	protected void prepareMoreActionList(){

		final BeanUIMenuListPanelSettings mainMenListSettings= PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getMenuListPanelSettings();
		if(mainMenListSettings.isAttendanceButtonVisible())
			AddToMoreActionList("ATTENDANCE", "Attendance", OrderEntryMenuActions.ATTENDANCE);
		if(mainMenListSettings.isOrderDetailsButtonVisible())
			AddToMoreActionList("ORDDTLS", "Order Details", OrderEntryMenuActions.ORDERDETAILS);
//		if(mainMenListSettings.isOrderRefundButtonVisible())
//			AddToMoreActionList("ORDRFND", "Order Refund", OrderEntryMenuActions.ORDERREFUND);
		if(mainMenListSettings.isCashoutButtonVisible())
			AddToMoreActionList("CASHOUT", "Daily Expenses", OrderEntryMenuActions.CASHOUT);
		if(mainMenListSettings.isSummaryButtonVisible())
			AddToMoreActionList("SHIFTSUMMARY", "Shift Summary", OrderEntryMenuActions.SHIFTSUMMARY);
	}

	/***
	 * Initialize the Panel component
	 */
	private void initComponent() {
		//		mActionButtonList=PosEnvSettings.getInstance().getOrderEntryActions();
		LAYOUT_WIDTH=getLayoutWidth();
		setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
		setBackground(PANEL_BG_COLOR);
		setLayout(new FlowLayout(FlowLayout.LEFT ,0,0));
		loadButtonImages();
		createActionControls();
	}
	/***
	 * get the Layoutwidth
	 * @return
	 */
	protected int getLayoutWidth(){

		return LAYOUT_WIDTH;
	}
	/***
	 * Creates the various controls
	 */
	private void createActionControls()
	{
		createActionButtons();
		addSeperater();
		createSystemButtons();
	}
	/***
	 * Virtual function that has to be implemented to create the actual buttons
	 */
	abstract protected void createActionButtons();
	abstract public void setServiceTypeIfno(PosOrderServiceTypes type, Object data);
	/***
	 * Reset the menu buttons
	 */
	public void reset(){
		mPosOrderItem=null;
		mButtonOrderInfo.setEnabled(false);
		mButtonSave.setEnabled(false);
		mButtonCustomerInfo.setEnabled(false);
	}
	/***
	 * Enable the buttons when order starts
	 * @param order
	 */
	public void onNewOrderStarted(BeanOrderHeader order){
		mPosOrderItem=order;
		mButtonOrderInfo.setEnabled(true);
		mButtonSave.setEnabled(true);
		mButtonCustomerInfo.setEnabled(!PosOrderUtil.isDeliveryService(order));
	}
	/***
	 * adds the separator
	 */
	protected void addSeperater(){
		addSeperater(this);
	}
	/***
	 * Adds the separator
	 * @param panel
	 */
	protected void addSeperater(JPanel panel){
		JPanel seperater=new JPanel();
		seperater.setPreferredSize(new Dimension(SEPERATER_WIDTH,SEPERATER_HEIGHT));
		seperater.setLayout(null);
		seperater.setOpaque(true);
		seperater.setBackground(SEPERATER_BG_COLOR);
		panel.add(seperater);
	}
	/***
	 * Loads the various images 
	 */
	private void loadButtonImages(){
		mImageButtonNormal=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_NORMAL);
		mImageButtonNormalTouch=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_NORMAL_TOUCHED);
		mImageButtonWide=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_WIDE);
		mImageButtonWideTouch=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_WIDE_TOUCHED);

		mExitImageButtonNormal=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_EXIT_NORMAL);
		mExitImageButtonTouch=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_EXIT_TOUCHED);
		mLockImageButtonNormal=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_LOCK_NORMAL);
		mLockImageButtonTouch=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_LOCK_TOUCHED);
	}
	/***
	 * creates the system buttons
	 */
	private void createSystemButtons(){
		JPanel pnlSystem=new JPanel();
		pnlSystem.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		pnlSystem.setPreferredSize(new Dimension(WIDE_BUTTON_WIDTH+PANEL_CONTENT_H_GAP, WIDE_BUTTON_HEIGHT*2+PANEL_CONTENT_V_GAP*3));
		pnlSystem.setOpaque(false);
		add(pnlSystem);
		createLockButton(pnlSystem);
		createExitButton(pnlSystem);
	}
	/***
	 * Creates the exit button
	 * @param pnlSystem
	 */
	private void createExitButton(JPanel pnlSystem)
	{

		mButtonExit=createActionPanelButton("Exit",true,KeyEvent.VK_X,'x');
		mButtonExit.setImage(mExitImageButtonNormal);
		mButtonExit.setTouchedImage(mExitImageButtonTouch);
		mButtonExit.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if(mPosActionPanelListner!=null)
					mPosActionPanelListner.onPosActionButtonClicked(OrderEntryMenuActions.EXIT, null);		
			};
		});
		pnlSystem.add(mButtonExit);
	}
	/***
	 * Creates the system Lock button
	 * @param pnlSystem
	 */
	private void createLockButton(JPanel pnlSystem)
	{
		mButtonLock=createActionPanelButton("Lock",true,KeyEvent.VK_L,'L');
		mButtonLock.setImage(mLockImageButtonNormal);
		mButtonLock.setTouchedImage(mLockImageButtonTouch);
		mButtonLock.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if(mPosActionPanelListner!=null)
					mPosActionPanelListner.onPosActionButtonClicked(OrderEntryMenuActions.LOCK, null);;		
			};
		});
		pnlSystem.add(mButtonLock);
	}
	/***
	 * Sets the listener
	 * @param posActionPanelListner
	 */
	public void setPosActionPanelListner(
			IPosActionPanelListnerBase posActionPanelListner) {
		this.mPosActionPanelListner = posActionPanelListner;
	}
	/***
	 * Creates the Action Panel buttons
	 * @param caption
	 * @return
	 */
//	protected PosIconButton createActionPanelButton(String caption)	{
//		
//		return createActionPanelButton( caption, false,null,null);
//	}
	
	/**
	 * @param caption
	 * @return
	 */
	protected PosIconButton createActionPanelButton(String caption,Integer ctrlKey, Character mnemonic)	{
	
	return createActionPanelButton( caption, false,ctrlKey,mnemonic);
}
	/***
	 * Creates the Action Panel buttons
	 * @param caption
	 * @param isWide
	 * @return
	 */
	protected PosIconButton createActionPanelButton(String caption, boolean isWide,Integer ctrlKey, Character mnemonic)
	{
		PosIconButton mButton = new PosIconButton(caption);
		if(isWide){
			mButton.setSize(WIDE_BUTTON_WIDTH, WIDE_BUTTON_HEIGHT);
			mButton.setImage(mImageButtonWide);
			mButton.setTouchedImage(mImageButtonWideTouch);
		}
		else{
			mButton.setSize(NORM_BUTTON_WIDTH, NORM_BUTTON_HEIGHT);
			mButton.setImage(mImageButtonNormal);
			mButton.setTouchedImage(mImageButtonNormalTouch);
		}
		mButton.setHorizontalAlignment(SwingConstants.CENTER);			
		mButton.setVerticalTextPosition(SwingConstants.CENTER);
		mButton.setHorizontalTextPosition(SwingConstants.CENTER);
		if(ctrlKey!=null)
			mButton.registerKeyStroke(ctrlKey,KeyEvent.CTRL_DOWN_MASK);
		if(mnemonic!=null)
			mButton.setMnemonic(mnemonic);
		return mButton;
	}
	/***
	 * This will create an empty button. used to fill the gap if needed
	 * @param isWide
	 * @return
	 */
	protected PosIconButton createActionPanelEmptyButton(boolean isWide)
	{
		PosIconButton buttonInfo=createActionPanelButton("",isWide,null,null);
		buttonInfo.setEnabled(false);
		return buttonInfo;

	}
	/***
	 * Message Buttons
	 * @param panel
	 * @param isWide
	 */
	protected void createPosMessageButton(JPanel panel,boolean isWide) {

		PosFlashMessageProvider mPosFlashMessageProvider = new PosFlashMessageProvider();						
		int count =mPosFlashMessageProvider.getMessageCount();
		mButtonPosMessages = new PosIconButton("("+count+")");
		mButtonPosMessages.setSize(WIDE_BUTTON_WIDTH, WIDE_BUTTON_HEIGHT);
		mButtonPosMessages.setImage(PosResUtil.getImageIconFromResource("action_panel_msg_button_wide.png"));
		mButtonPosMessages.setTouchedImage(PosResUtil.getImageIconFromResource("action_panel_msg_button_wide_touch.png"));
		mButtonPosMessages.setHorizontalAlignment(SwingConstants.CENTER);			
		mButtonPosMessages.setVerticalTextPosition(SwingConstants.CENTER);
		mButtonPosMessages.setHorizontalTextPosition(SwingConstants.CENTER);
		mButtonPosMessages.setTextIcon(PosResUtil.getImageIconFromResource("pos-msg.png"));
		mButtonPosMessages.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				PosFlashMessageListForm mFlashMessagesForm = new PosFlashMessageListForm();
				PosFormUtil.showLightBoxModal(mParent,mFlashMessagesForm);

			}
		});
		panel.add(mButtonPosMessages);
	}

	/***
	 * Create the customer button
	 * 
	 * @param panel
	 */
	protected void createCustomerInfoButton(JPanel panel,boolean isWide) {
		mButtonCustomerInfo = createActionPanelButton("Customer", isWide,KeyEvent.VK_C,'C');
		mButtonCustomerInfo.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if (mPosActionPanelListner != null)
					mPosActionPanelListner.onPosActionButtonClicked(
							OrderEntryMenuActions.CUSTOMER, null);
			};
		});
		panel.add(mButtonCustomerInfo);
	}
	/***
	 * Create the Attendance button
	 * 
	 * @param panel
	 */
	protected void createMoreButton(JPanel panel,boolean isWide) {
		
		mButtonMore = createActionPanelButton("More", isWide,KeyEvent.VK_M,'M');
		mButtonMore.setImage(IMAGE_BUTTON_MORE_NORMAL, IMAGE_BUTTON_MORE_TOUCHED);
		mButtonMore.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				
				final PosObjectBrowserForm form=new PosObjectBrowserForm("More Menu",mMoreActionList,ItemSize.Wider,true);
				form.setListner(new IPosObjectBrowserListner() {
					
					@Override
					public void onItemSelected(IPosBrowsableItem item) {

						if (mPosActionPanelListner != null)
						mPosActionPanelListner.onPosActionButtonClicked(
								((PosMoreMenuItem)item).getAction(), null);
					}
					
					@Override
					public void onCancel() {
						// TODO Auto-generated method stub
						
					}
				});
				PosFormUtil.showLightBoxModal(mParent,form);
			};
		});
		panel.add(mButtonMore);
	}

	/***
	 * Create the Menu button
	 * 
	 * @param panel
	 */
	protected void createMenuButton(JPanel panel, boolean isWide) {
		mButtonMenu = createActionPanelButton("Menu", isWide,KeyEvent.VK_M,'u');
		mButtonMenu.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if (mPosActionPanelListner != null)
					mPosActionPanelListner.onPosActionButtonClicked(
							OrderEntryMenuActions.MENU, null);
			};
		});
		panel.add(mButtonMenu);
	}
	/***
	 * creates the save button.
	 * @param panel
	 */
	protected void createSaveButton(JPanel panel, boolean isWide) {
		mButtonSave = createActionPanelButton("Park", isWide,KeyEvent.VK_S,'P');
		mButtonSave.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if (mPosActionPanelListner != null)
					((IPosActionPanelRestaurantListner) mPosActionPanelListner)
					.onPosActionButtonClicked(
							OrderEntryMenuActions.ORDER_SAVE, null);
			};
		});
		panel.add(mButtonSave);
	}
	/***
	 * Creates the retrieve button
	 * @param panel
	 */
	protected void createRetrieveButton(JPanel panel, boolean isWide) {
		
		mButtonOpen = createActionPanelButton("Open", true,KeyEvent.VK_O,'O');
		mButtonOpen.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if (mPosActionPanelListner != null)
					((IPosActionPanelRestaurantListner) mPosActionPanelListner)
					.onPosActionButtonClicked(
							OrderEntryMenuActions.ORDER_RETRIEVE, null);
			};
		});
		panel.add(mButtonOpen);
	}
	/***
	 * create info button
	 * @param panel
	 */
	protected void createInfoButton(JPanel panel, boolean isWide) {
		
		mButtonOrderInfo = createActionPanelButton("Info", isWide,KeyEvent.VK_I,'I');
		mButtonOrderInfo.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if (mPosActionPanelListner != null)
					((IPosActionPanelRestaurantListner) mPosActionPanelListner)
					.onPosActionButtonClicked(
							OrderEntryMenuActions.ORDER_INFO, null);
			};
		});
		panel.add(mButtonOrderInfo);
	}


	protected class PosMoreMenuItem implements IPosBrowsableItem{


		private String itemCode;
		private String itemText;
		private boolean isVisible=true;
		private OrderEntryMenuActions action;

		public PosMoreMenuItem(String code, String caption, OrderEntryMenuActions action) {

			this.itemCode=code;
			this.itemText=caption;
			this.action=action;

		}

		public OrderEntryMenuActions getAction(){

			return action;
		}


		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getItemCode()
		 */
		@Override
		public Object getItemCode() {

			return itemCode;
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getDisplayText()
		 */
		@Override
		public String getDisplayText() {

			return itemText;
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
		 */
		@Override
		public boolean isVisibleInUI() {

			return isVisible;
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
		 */
		@Override
		public KeyStroke getKeyStroke() {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
