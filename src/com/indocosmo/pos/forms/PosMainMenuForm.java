package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimerTask;
import javax.swing.JOptionPane;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosEnvSettings.ApplicationType;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.beans.settings.ui.mainmenu.BeanUIMenuListPanelSettings;
import com.indocosmo.pos.common.enums.EnablePrintingOption;
import com.indocosmo.pos.common.enums.PosProcessMessageType;
import com.indocosmo.pos.common.enums.PosPropertyFileType;
import com.indocosmo.pos.common.enums.PosReportsType;
import com.indocosmo.pos.common.enums.PosTerminalOperationalMode;
import com.indocosmo.pos.common.utilities.PosAccessPermissionsUtil;
import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosLicenceUtil;
import com.indocosmo.pos.common.utilities.PosPrintingUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosSyncUtil;
import com.indocosmo.pos.common.utilities.PosUtil;
import com.indocosmo.pos.data.beans.BeanAccessLog;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanDataSync;
import com.indocosmo.pos.data.beans.BeanLoginSessions;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanShopShift;
import com.indocosmo.pos.data.beans.BeanUser;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalInfo;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalSetting;
import com.indocosmo.pos.data.providers.PosLoginSessionsProvider;
import com.indocosmo.pos.data.providers.shopdb.PosAccessLogProvider;
import com.indocosmo.pos.data.providers.shopdb.PosAttendProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCardPaymentRecoveryProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCashierProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCashierShiftProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDayProcessProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDayProcessProvider.PosDayProcess;
import com.indocosmo.pos.data.providers.shopdb.PosFlashMessageProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosServerSyncStatusProvider;
import com.indocosmo.pos.data.providers.shopdb.PosServiceTableProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShopShiftProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevSettingProvider.PosDevices;
import com.indocosmo.pos.data.providers.terminaldb.PosTerminalSettingsProvider;
import com.indocosmo.pos.forms.PosDayProcessForm.PosDayProcessFormListener;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.PosOrderEntryForm.IPosOrderEntryFormListner;
import com.indocosmo.pos.forms.booking.PosOrderBookingForm;
import com.indocosmo.pos.forms.components.PosScrollMessagePanel;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosIconButton;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.components.terminal.PosProcessLogDisplayPanel;
import com.indocosmo.pos.forms.components.terminal.PosShiftDetailsPanel;
import com.indocosmo.pos.forms.components.terminal.PosShiftDetailsPanel.IPosShiftDetailsPanelListner;
import com.indocosmo.pos.forms.components.terminal.PosStockInfoPanel;
import com.indocosmo.pos.forms.components.terminal.PosSyncStatusInfoPanel;
import com.indocosmo.pos.forms.components.terminal.PosSystemInfoDisplayPanel;
import com.indocosmo.pos.forms.listners.IPosMessageListner;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;
import com.indocosmo.pos.forms.listners.IPosTerminalSettingsFormListner;
import com.indocosmo.pos.forms.listners.adapters.PosShiftFormAdapter;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter;
import com.indocosmo.pos.reports.shift.PosShiftReport;
import com.indocosmo.pos.reports.shift.custom.PosShiftReportNihon;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.PosDeviceManager.IPosDeviceManagerListener;
import com.indocosmo.pos.terminal.printserver.PrintServer;

/**
 * @author jojesh-13.2
 *
 */
@SuppressWarnings("serial")
public class PosMainMenuForm extends JDialog implements IPosOrderEntryFormListner, IPosDeviceManagerListener,IPosMessageListner {

	private static final int PANEL_CONTENT_H_GAP = 4;
	private static final int PANEL_CONTENT_V_GAP = 4;

	private static final int TOP_TITLE_LAYOUT_HEIGHT = 100;
	private static final int IMAGE_BUTTON_WIDTH = 130;
	private static final int IMAGE_BUTTON_HEIGHT = 87;

	private static final int NO_MENU_BUTTON_COL = PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getMenuColumnCount();
//	private static final int NO_MENU_BUTTON_ROW = 4;
	private static final int LOG_PANEL_HEIGHT = PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getBottomPanelHeight(); // 300;

	//private static final String TOP_TITLE_PANEL_BG_COLOR="#2B4564";
	private static final String TOP_TITLE_PANEL_BG_COLOR="#000000";
	

	private JPanel mContentPane;
	private JPanel mTopTitlePanel;
	private JPanel mLeftPanel;
	private JPanel mRightPanel;

	private PosScrollMessagePanel mScrollMsgPanel;
	
	private JLabel mLabelLogoImage;
	private static final String IMAGE_LOGO = "main_menu_header.png";

	private static final String IMAGE_BUTTON_NORMAL = "MainMenu.png";
	private static final String IMAGE_BUTTON_TOUCHED = "MainMenu_touch.png";

	private static final String IMAGE_BUTTON_RED_NORMAL = "main_menu_red.png";
	private static final String IMAGE_BUTTON_RED_TOUCHED = "main_menu_red_touch.png";

	private static final String IMAGE_BUTTON_MSG_NORMAL = "main_menu_msg.png";
	private static final String IMAGE_BUTTON_MSG_TOUCHED = "main_menu_msg_touch.png";
	
	private static final String IMAGE_BUTTON_NAGATIVE = "Minimize.png";
	private static final String IMAGE_BUTTON_NAGATIVE_TOUCH = "Minimize_touch.png";

	private static final int INFO_LABEL_WIDTH = 150;
	private static final int INFO_LABEL_HEIGHT = 20;

	private JPanel mMainMenuItemContainer;
	private static PosProcessLogDisplayPanel mLogPanel;
	private PosSystemInfoDisplayPanel mSystemInfoPanel;
	private PosSyncStatusInfoPanel dbStatusInfoDisplayPanel ;
	private PosStockInfoPanel mStockInfoPanel ;
	private PosShiftDetailsPanel mShiftPanel;
	private PosIconButton mButtonDayStart;
	private PosIconButton mButtonShiftStart;
	private PosIconButton mButtonShiftEnd;
	private PosIconButton mButtonOrderEntry;
	private PosIconButton mButtonAttendance;
	private PosIconButton mButtonShiftReports;
	private PosIconButton mButtonOrderDetails;
	private PosIconButton mButtonShiftSummary;
	private PosIconButton mButtonSyncNow;
	private PosIconButton mButtonDayEnd;
	private PosIconButton mButtonTerminal;
	private PosIconButton mButtonLogout;
	private PosIconButton mButtonViewMessage;
	private PosButton mButtonContactUs;
	private PosButton mEditUIConfiguration;
	private PosButton mEditPrintConfiguration;
	private PosButton mButtonLoginSessions;
	private PosButton mButtonBookings;
	private PosButton mButtonCashOut;
	private PosButton mButtonExport;
	private PosButton mButtonReports; 
	private PosButton mButtonAbout; 
	
	private PosButton mButtonMin; 
	
	private ArrayList<PosButton> mMenuOptions;

	private PosDeviceManager mDeviceManager;

	private PosEnvSettings mPosEnvSettings;

	private static PosMainMenuForm mSingleInstance;

	private PosFlashMessageProvider mPosFlashMessageProvider;

	private PosCashierShiftProvider cashierShiftProvider;
	private int activeShifts;

	private PosAccessLogProvider accesslogProvider;

	private static Font labelDateTimeFont = new Font("Serif", Font.PLAIN, 14);

	private enum MenuButtons {
		
		DayStart, 
		ShiftStart, 
		OrderEntry, 
		ShiftSummary, 
		Attendance, 
		ShiftReports, 
		OrderList, 
//		StatusReport,
		ShiftEnd, 
		SyncNow, 
		OrderRefund, 
		DayEnd, 
		Terminal, 
		Messages, 
		Exit, 
		ContactUs,
		EditUIConfig,
		EditPrintingConfig,
		ViewSessions,
		Bookings,
		CashOut,
		Export,
		Reports,
		About,
		Minimize
		
	}

	/***
	 *  
	 */
	private PosOrderEntryForm mPosOrderEntryForm;
	private PosCardPaymentRecoveryProvider mCardPaymentRecoveryProvider;
	private JLabel mLabelCalDate;
	private JLabel mLabelPosDate;
	private JLabel mLabelTerminalInfo;
	private JLabel mLabelShopName;
	
	private boolean mIsMaintancanceModeOn = false;

	private boolean mIsSystemInitilized;

	public static synchronized PosMainMenuForm getInstance() {
		if (mSingleInstance == null)
			mSingleInstance = new PosMainMenuForm();
		return mSingleInstance;
	}

	/**
	 * Create the frame.
	 */
	private PosMainMenuForm() {

		mPosEnvSettings = PosEnvSettings.getInstance();

		mMenuOptions = new ArrayList<PosButton>();

		initControls();

		createTerminalInofPanel();
		createMainMenuItems();
		createLogPanel();
		//PosLog.debug("application type++++++++++++++++<><>"+PosEnvSettings.getInstance().getApplicationType());
		if (PosEnvSettings.getInstance().getApplicationType()==ApplicationType.Standard)
			createSyncStatusPanel();
		else
			createStockInfoPanel();
		
		createCashierShiftPanel();
		
		createLogoImage();
		mPosFlashMessageProvider = new PosFlashMessageProvider();
		cashierShiftProvider = new PosCashierShiftProvider();
		updateMessages();
	
		try {
			/** This is a hack to load all the tables 
			 * Issue found when printing through service and print server
			 */
			final PosServiceTableProvider mServiceTableProvider=new PosServiceTableProvider();
			mServiceTableProvider.getServiceTableList();
		} catch (Exception e) {
			PosLog.write(this, "PosMainMenuForm", e);
		}
		
		PosFormUtil.applyWindowRenderingHack(this);

	}

	private void setupMenuButtons() {

		activeShifts = cashierShiftProvider.getAllOpenCashierShifts().size();

		String caption = "";

		caption = isRecoveryMode() ? "Recover" : "Order Entry";
		mButtonOrderEntry.setText(caption);

		caption = (activeShifts > 0) ? "Close Till" : "Open Till";
		mButtonShiftStart.setText(caption);

		caption = (activeShifts > 1) ? "Leave Till" : "Close Till";
		mButtonShiftEnd.setText(caption);

	}

	private void initKotServer() {
		
		if (!PosEnvSettings.getInstance().isStartKotServer())
			return;
		
		PrintServer server=PrintServer.getInstance();
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void initSystem() {

		PosDeviceManager.getInstance().setListener(this);

		if (!check4TerminalConfig())
			return;

		setupMenuButtons();
		initKotServer();
		
		check4DayStarted();

		mLabelPosDate.setText((PosEnvSettings.getInstance().getPosDate() != null ? PosEnvSettings
				.getInstance().getPosDate() : ""));

		if (PosEnvSettings.getInstance().getStation() != null)
			mLabelTerminalInfo.setText(PosEnvSettings.getInstance()
					.getStation().getName());
		
		if (PosEnvSettings.getInstance().getShop()!=null)
			mLabelShopName.setText(PosEnvSettings.getInstance().getShop().getName());

		mButtonTerminal.setEnabled(true);
		mButtonViewMessage.setEnabled(true);
		mButtonLogout.setEnabled(true);
		mButtonSyncNow.setEnabled(true);

		mIsSystemInitilized=true;
		
		
			intSyncUpdateCheck();
			initializeBackupUtil();

	}

	/*
	 * This is a singleton class. so we must avoid cloning the objects. DO NOT
	 * ALTER THE CODE.
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Dialog#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean b) {

		super.setVisible(b);
		if (b && !mIsSystemInitilized)
			initSystem();
//		showExport();
//		System.exit(0);
	}

	private boolean initDevices() {
		try {
			mDeviceManager = PosDeviceManager.getInstance();
			mDeviceManager.startupDevices();
		} catch (Exception e) {
			PosLog.write(this, "initDevice", e);
			return false;
		}
		return true;
	}

	private void initControls() {

		final int screen = PosEnvSettings.getInstance().getPrimaryScreen();
		final int width = PosUtil.getScreenSize(screen).width;
		final int height = PosUtil.getScreenSize(screen).height;
		final Point pt=PosUtil.transformScreenPoints(new Point(0,0), screen);
		
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setUndecorated(true);
		this.setBounds(pt.x, pt.y, width, height);

		mContentPane = new JPanel();
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mContentPane.setLayout(null);
		setContentPane(mContentPane);

		mTopTitlePanel = new JPanel();
		mTopTitlePanel.setBounds(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP,
				width - PANEL_CONTENT_H_GAP * 2, TOP_TITLE_LAYOUT_HEIGHT);
		mTopTitlePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mTopTitlePanel.setLayout(null);
		mTopTitlePanel.setOpaque(true);
		mTopTitlePanel.setBackground(Color.decode(TOP_TITLE_PANEL_BG_COLOR));
		add(mTopTitlePanel);
		
		int scrollPanelTop = mTopTitlePanel.getY() + mTopTitlePanel.getHeight()
			+ PANEL_CONTENT_V_GAP;
		mScrollMsgPanel=new PosScrollMessagePanel(PANEL_CONTENT_H_GAP, scrollPanelTop,width - PANEL_CONTENT_H_GAP * 2);
		
//		if(PosEnvSettings.getInstance().getApplicationType()==ApplicationType.Standard) 
			add(mScrollMsgPanel);
	
//		
//		int messagePanelTop = mScrollMsgPanel.getY() +
//				(PosEnvSettings.getInstance().getApplicationType()==ApplicationType.Standard?
//						mScrollMsgPanel.getHeight() + PANEL_CONTENT_V_GAP:0) ; 
			
			
			int messagePanelTop = mScrollMsgPanel.getY() + mScrollMsgPanel.getHeight() + PANEL_CONTENT_V_GAP ; 
			
		int top = messagePanelTop;
		int leftPanelHeight = this.getHeight()- top
				- PANEL_CONTENT_V_GAP;
		int leftPanelWidth = IMAGE_BUTTON_WIDTH * NO_MENU_BUTTON_COL
				+ (PANEL_CONTENT_H_GAP * (NO_MENU_BUTTON_COL + 1));

		mLeftPanel = new JPanel();
		mLeftPanel.setBounds(PANEL_CONTENT_H_GAP, top, leftPanelWidth,
				leftPanelHeight);
		mLeftPanel.setLayout(null);
		mLeftPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mLeftPanel);

		
		int messagePanelleft = mLeftPanel.getX() + mLeftPanel.getWidth()
				+ PANEL_CONTENT_H_GAP;
		int messagePanelHeight = this.getHeight() - top
				- PANEL_CONTENT_V_GAP;

		mRightPanel = new JPanel();
		mRightPanel.setBounds(messagePanelleft, messagePanelTop, width
				- messagePanelleft - PANEL_CONTENT_H_GAP, messagePanelHeight);
		mRightPanel.setLayout(null);
		mRightPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mRightPanel);

		this.addWindowListener(new WindowAdapter() {
			
			boolean isShownWaring=false;
			@Override
			public void windowActivated(WindowEvent e) {

				if (!mIsSystemInitilized)
					return;

				if(!PosEnvSettings.getInstance().isDevelopmentMode() && !PosLicenceUtil.checkLicence()) System.exit(0);

				check4ActieveShifts();
				mShiftPanel.requestFocus();

				if(!isShownWaring){
					
					PosUtil.checkPosDate(PosMainMenuForm.this);
					isShownWaring=true;
					
				}
			}
		});
	}

	/**
	 * @return
	 */
	private boolean check4TerminalConfig() {

		boolean isTerminalConfigured = false;
		final PosTerminalSettingsProvider tsProvider = new PosTerminalSettingsProvider();
		final BeanTerminalSetting terminalSettings = tsProvider
				.getTerminalSetting();
		isTerminalConfigured = !(terminalSettings == null);

		if (!isTerminalConfigured) {
			mButtonDayStart.setEnabled(false);
			mButtonSyncNow.setEnabled(false);
			mButtonContactUs.setEnabled(false);
		}

		return isTerminalConfigured;
	}

	private boolean check4DayStarted() {

		boolean isDayBegine = false;
		try {
			final PosDayProcessProvider dayProvider = new PosDayProcessProvider();
			final String posDate = dayProvider.getOpenPosDate();
			isDayBegine = (posDate != null && !posDate.equals(""));
			if (isDayBegine)
				mDayStartedListner.onOkPressed(posDate);
		} catch (Exception e) {
			PosFormUtil
			.showErrorMessageBox(this,
					"Failed to obtain day start information. Please contact Administrator.");
		}
		return isDayBegine;
	}

	private void check4ActieveShifts() {

		final boolean isEnabled = (PosEnvSettings.getInstance()
				.getCashierShiftInfo() != null);
		mButtonShiftStart.setText((isShiftOpen()) ? "Close Till" : "Open Till");
		mButtonShiftEnd.setEnabled(isEnabled);
		mButtonOrderEntry.setEnabled(isEnabled);
		mButtonLoginSessions.setEnabled(isEnabled);
//		mButtonOrderRefund.setEnabled(isEnabled);
		mButtonOrderDetails.setEnabled(isEnabled);
		mButtonShiftSummary.setEnabled(isEnabled);
		mButtonCashOut.setEnabled(isEnabled);
		mButtonExport.setEnabled(isEnabled);
		if (!isEnabled)
			mShiftPanel.clearSelection();
	}



	private void createSystemPanel() {

		final int top = mShiftPanel.getY() + mShiftPanel.getHeight();
		final int width = mShiftPanel.getWidth();
		final int height = mLeftPanel.getHeight() - mShiftPanel.getHeight();
		mSystemInfoPanel = new PosSystemInfoDisplayPanel(width, height);
		mSystemInfoPanel.setLocation(0, top);
		mSystemInfoPanel.setOpaque(false);
		mRightPanel.setBackground(Color.CYAN);
		mSystemInfoPanel.setFocusable(false);
		mRightPanel.add(mSystemInfoPanel);
	}

	private void createCashierShiftPanel() {
		
		final int top = 0;
		final int width = mRightPanel.getWidth();
		//cashier panel height = right panel height - sync_status_panel height
		
//		final int height =  mRightPanel.getHeight()-   (PosEnvSettings.getInstance().getApplicationType()==ApplicationType.Standard? 
//					dbStatusInfoDisplayPanel.getHeight():PANEL_CONTENT_V_GAP+2); //((mLeftPanel.getHeight()/2)+50);
		
		final int height =  mRightPanel.getHeight()-   (PosEnvSettings.getInstance().getApplicationType()==ApplicationType.Standard? 
				dbStatusInfoDisplayPanel.getHeight():mStockInfoPanel.getHeight()); 
		
		mShiftPanel = new PosShiftDetailsPanel(this, width, height);
		mShiftPanel.setLocation(0, top);
		mRightPanel.add(mShiftPanel);
		mShiftPanel.setListner(new IPosShiftDetailsPanelListner() {

			@Override
			public void onCashierShiftSelected(BeanCashierShift cashierShift) {
				PosEnvSettings.getInstance().setCashierShiftInfo(cashierShift);
				check4ActieveShifts();
			}

			@Override
			public void onNewCashier(String id, boolean byCard) {
				try {
					
					PosCashierProvider CashierProvider = new PosCashierProvider();
					BeanUser cashierInfo = CashierProvider.getUserByCard(id);
					
					if (isShiftOpen()) {
						
						if (cashierInfo != null && !id.equalsIgnoreCase("")) {
							doJoinShift(cashierInfo);
						}
					
					} else {
						doShiftStart(cashierInfo, byCard);
					}

				} catch (Exception e) {
					
					PosLog.write(this, "onNewCashier", e);
					PosFormUtil.showErrorMessageBox(null,
							"Failed get cashier information.");
				}
			}
		});
		mShiftPanel.setEnabled(false);
	}

	private void createLogoImage() {
		int Left = PANEL_CONTENT_H_GAP;
		int Top = PANEL_CONTENT_V_GAP;
		mLabelLogoImage = new JLabel();
		mLabelLogoImage.setVerticalAlignment(SwingConstants.TOP);
		mLabelLogoImage.setBounds(Left, Top, mMainMenuItemContainer.getWidth(), TOP_TITLE_LAYOUT_HEIGHT - 10);
		mLabelLogoImage.setOpaque(false);
		ImageIcon iconLogo = PosResUtil.getLogoFromResource(IMAGE_LOGO);
		mLabelLogoImage.setIcon(iconLogo);
		mLabelLogoImage.setVisible(true);
		mTopTitlePanel.add(mLabelLogoImage);

	}

	private void updateMessages() {

		int count = mPosFlashMessageProvider.getMessageCount();
		mButtonViewMessage.setText("Mails (" + count + ")");
		mButtonViewMessage.setForeground((count > 0) ? Color.RED
				: PosButton.FOREGROUND_COLOR);

	}

	private JLabel createInfoLabel(String caption, JPanel root, Color col) {

		final int titleWidth = 90;
		final JPanel infoPanel = new JPanel();
		infoPanel.setSize(root.getWidth()-PANEL_CONTENT_H_GAP*2, INFO_LABEL_HEIGHT);
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		root.add(infoPanel);

		JLabel lblTitle = new JLabel();
		lblTitle.setPreferredSize(new Dimension(titleWidth, INFO_LABEL_HEIGHT));
		lblTitle.setOpaque(true);
		lblTitle.setText(caption);
		lblTitle.setBackground(Color.WHITE);
		lblTitle.setBorder(new LineBorder(col));
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitle.setFont(PosFormUtil.getLabelFontSmall());
		infoPanel.add(lblTitle);

		JLabel lblInfo = new JLabel();
		lblInfo.setPreferredSize(new Dimension(infoPanel.getWidth()-titleWidth,
				INFO_LABEL_HEIGHT));
		lblInfo.setOpaque(true);
		lblInfo.setBackground(col);
		lblInfo.setForeground(Color.WHITE);
		lblInfo.setFont(PosFormUtil.getLabelFontSmall());
		infoPanel.add(lblInfo);

		return lblInfo;
	}

	private void createTerminalInofPanel() {

		final int width=mRightPanel.getWidth()-PANEL_CONTENT_H_GAP*2;
		final JPanel infoPanel = new JPanel();
		infoPanel.setSize(width, INFO_LABEL_HEIGHT*4+5);
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, PANEL_CONTENT_H_GAP, 1));

		final int infoPanelTop = 7;
		final int infoPanelLeft = mTopTitlePanel.getWidth()
				- infoPanel.getWidth() - PANEL_CONTENT_H_GAP;
		infoPanel.setLocation(infoPanelLeft, infoPanelTop);
		infoPanel.setOpaque(false);
		mTopTitlePanel.add(infoPanel);

		mLabelShopName = createInfoLabel("Shop :", infoPanel, new Color(74, 112,
				139));
		mLabelTerminalInfo = createInfoLabel("Terminal :", infoPanel, new Color(
				139, 71, 93));
		mLabelPosDate = createInfoLabel("POS Date :", infoPanel, new Color(128,
				0, 128));
		mLabelCalDate = createInfoLabel("CAL. Date :", infoPanel, new Color(74,
				112, 139));

		setTimer();
	}

	private void setTimer() {

		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				mLabelCalDate.setText(PosDateUtil.getDate("yyyy-MM-dd (E) HH:mm:ss")
						.toString());
			}
		};
		Timer timer = new Timer(500, actionListener);
		timer.start();
	}

	private void createMainMenuItems() {

		int width = mLeftPanel.getWidth();
//		int height = IMAGE_BUTTON_HEIGHT * NO_MENU_BUTTON_ROW
//				+ PANEL_CONTENT_V_GAP * (NO_MENU_BUTTON_ROW + 1);
		final int height = mLeftPanel.getHeight()
		- LOG_PANEL_HEIGHT;
		mMainMenuItemContainer = new JPanel();
		
		//Added by Udhay on 07-07-2021 for Menu Item height change
		//mMainMenuItemContainer.setBounds(0, 0, width, height);
		mMainMenuItemContainer.setBounds(0, 0, width, height+25);
		
		mMainMenuItemContainer.setLayout(createLayout());
		mMainMenuItemContainer.setOpaque(false);
		mLeftPanel.add(mMainMenuItemContainer);
		createMenuButtons();

	}

	private void createMenuButtons() {

		final BeanUIMenuListPanelSettings menuPanleSettings= PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getMenuListPanelSettings();

		mButtonDayStart = createMenuButton(MenuButtons.DayStart,
				menuButtonListener);
		mButtonDayStart.setEnabled(true);
		if(menuPanleSettings.isDayStartButtonVisible())
			mMainMenuItemContainer.add(mButtonDayStart);
		
		mButtonDayEnd = createMenuButton(MenuButtons.DayEnd, menuButtonListener);
		mButtonDayEnd.setVisible(false);
		if(menuPanleSettings.isDayEndButtonVisible())
			mMainMenuItemContainer.add(mButtonDayEnd);

		mButtonShiftStart = createMenuButton(MenuButtons.ShiftStart,
				menuButtonListener);
		if(menuPanleSettings.isTillButtonVisible())
			mMainMenuItemContainer.add(mButtonShiftStart);

		mButtonShiftEnd = createMenuButton(MenuButtons.ShiftEnd,
				menuButtonListener);
		
		mButtonAttendance = createMenuButton(MenuButtons.Attendance,
				menuButtonListener);
		if(menuPanleSettings.isAttendanceButtonVisible())
			mMainMenuItemContainer.add(mButtonAttendance);

		mButtonShiftSummary = createMenuButton(MenuButtons.ShiftSummary,
				menuButtonListener);
		if(menuPanleSettings.isSummaryButtonVisible())
			mMainMenuItemContainer.add(mButtonShiftSummary);

		mButtonOrderEntry = createMenuButton(MenuButtons.OrderEntry,
				menuButtonListener);
		if(menuPanleSettings.isOrderEntryButtonVisible())
			mMainMenuItemContainer.add(mButtonOrderEntry);

		mButtonShiftReports = createMenuButton(MenuButtons.ShiftReports,
				menuButtonListener);
		if(menuPanleSettings.isShiftReportsButtonVisible())
			mMainMenuItemContainer.add(mButtonShiftReports);

		mButtonOrderDetails = createMenuButton(MenuButtons.OrderList,
				menuButtonListener);
		if(menuPanleSettings.isOrderDetailsButtonVisible())
			mMainMenuItemContainer.add(mButtonOrderDetails);
		
		mButtonCashOut = createMenuButton(MenuButtons.CashOut, menuButtonListener);
		if(PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getMenuListPanelSettings().isCashoutButtonVisible()){
			mMainMenuItemContainer.add(mButtonCashOut);
		}
		
		mButtonReports = createMenuButton(MenuButtons.Reports, menuButtonListener);
		if(menuPanleSettings.isSalesOrderReportButtonVisible())
			mMainMenuItemContainer.add(mButtonReports);
		
		mButtonSyncNow = createMenuButton(MenuButtons.SyncNow,
				menuButtonListener);
		if(menuPanleSettings.isSyncButtonVisible() && PosEnvSettings.getInstance().getApplicationType()==ApplicationType.Standard)
			mMainMenuItemContainer.add(mButtonSyncNow);


		mButtonTerminal = createMenuButton(MenuButtons.Terminal,
				menuButtonListener);
		mButtonTerminal.setEnabled(true);
		if(menuPanleSettings.isTerminalButtonVisible())
			mMainMenuItemContainer.add(mButtonTerminal);

		mButtonExport = createMenuButton(MenuButtons.Export, menuButtonListener);
		if(menuPanleSettings.isTallyExportButtonVisible())
		mMainMenuItemContainer.add(mButtonExport);
	
		mButtonViewMessage = createMenuButton(MenuButtons.Messages,
				menuButtonListener);
		mButtonViewMessage.setTextIcon(PosResUtil
				.getImageIconFromResource("pos-msg.png"));
		if(menuPanleSettings.isMailsButtonVisible())
			mMainMenuItemContainer.add(mButtonViewMessage);

		mButtonContactUs = createMenuButton(MenuButtons.ContactUs, menuButtonListener);
		mButtonContactUs.setTag(MenuButtons.ContactUs);
		if(PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getMenuListPanelSettings().isContactUsButtonVisible())
			mMainMenuItemContainer.add(mButtonContactUs);
		mButtonContactUs.setEnabled(true);

		mButtonLoginSessions = createMenuButton(MenuButtons.ViewSessions,menuButtonListener);
		if(PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getMenuListPanelSettings().isViewOpenSessionsButtonVisible()){
			mMainMenuItemContainer.add(mButtonLoginSessions);
			mButtonLoginSessions.registerKeyStroke(KeyEvent.VK_L, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK);
		}
		mButtonAbout = createMenuButton(MenuButtons.About, menuButtonListener);
		if(menuPanleSettings.isAboutButtonVisible())
			mMainMenuItemContainer.add(mButtonAbout);
		
		mButtonBookings = createMenuButton(MenuButtons.Bookings, menuButtonListener);
		mButtonBookings.setEnabled(true);
		
		//Added by Udhay on 06-07-2021 for Minimize and Maximize
		mButtonMin = createMenuButton(MenuButtons.Minimize, menuButtonListener);
		mMainMenuItemContainer.add(mButtonMin);
		mButtonMin.setEnabled(true);
				
		mButtonLogout = createMenuButton(MenuButtons.Exit, menuButtonListener);
		mMainMenuItemContainer.add(mButtonLogout);
		mButtonLogout.setEnabled(true);
		
		
		mEditUIConfiguration = createMenuButton(MenuButtons.EditUIConfig,menuButtonListener);
		mEditUIConfiguration.setSize(0, 0);
		mMainMenuItemContainer.add(mEditUIConfiguration);
		mEditUIConfiguration.registerKeyStroke(KeyEvent.VK_U, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK);
		mEditUIConfiguration.setEnabled(true);
		
		mEditPrintConfiguration = createMenuButton(MenuButtons.EditPrintingConfig,menuButtonListener);
		mEditPrintConfiguration.setSize(0, 0);
		mMainMenuItemContainer.add(mEditPrintConfiguration);
		mEditPrintConfiguration.registerKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK);
		mEditPrintConfiguration.setEnabled(true);

	}

	/**
	 * @return
	 */
	private boolean isRecoveryMode() {
		mCardPaymentRecoveryProvider = new PosCardPaymentRecoveryProvider();
		return (mCardPaymentRecoveryProvider.getEftPurchaseMessage() != null);
	}

	private PosIconButton createMenuButton(MenuButtons buttonType,
			IPosButtonListner listner) {
		String normalImageName = IMAGE_BUTTON_NORMAL;
		String touchImageName = IMAGE_BUTTON_TOUCHED;
		String caption = buttonType.toString();
		Character mnemonicChar=null;
		
		switch (buttonType) {
		case ContactUs:
			caption = "Support";
			mnemonicChar='u';
			break;
		case Attendance:
			caption = "Attendance";
			mnemonicChar='c';
			break;
		case DayEnd:
			caption = "Day End";
			mnemonicChar='D';
			break;
		case DayStart:
			caption = "Day Start";
			mnemonicChar='D';
			break;
		case Exit:
			caption = "Exit";
			normalImageName = IMAGE_BUTTON_RED_NORMAL;
			touchImageName = IMAGE_BUTTON_RED_TOUCHED;
			mnemonicChar='x';
			break;
		case Messages:
			caption = "Message";
			mnemonicChar='M';
			normalImageName = IMAGE_BUTTON_MSG_NORMAL;
			touchImageName = IMAGE_BUTTON_MSG_TOUCHED;
			break;
		case OrderList:
			caption = "Order List";
			mnemonicChar='L';
			break;
		case OrderEntry:
			caption = "Order Entry";
			mnemonicChar='O';
			break;
		case OrderRefund:
			caption = "Refund";
			break;
		case ShiftEnd:
			// caption = (activeShifts > 1) ? "Leave Till" : "Close Till";
			caption = "Close Till";
			mnemonicChar='T';
			break;
		case ShiftReports:
			caption = "Shift Reports";
			mnemonicChar='R';
			break;
		case ShiftStart:
			// caption = (activeShifts > 0) ? "Close Till" : "Open Till";
			caption = "Open Till";
			mnemonicChar='T';
			break;
		case ShiftSummary:
			caption = "Summary";
			mnemonicChar='y';
			break;
		case SyncNow:
			caption = "Sync. Now";
			mnemonicChar='N';
			break;
		case Terminal:
			caption = "Terminal";
			mnemonicChar='i';
			break;
		case EditUIConfig:
			caption = "Edit Config.";
			break;
		case ViewSessions:
			caption = "Open Sessions";
			break;
		case CashOut:
			caption = "Daily Expenses";
			mnemonicChar='E';
			break;
		case Export:
			caption = "Tally Export";
			mnemonicChar='a';
			break;
		case Reports:
			caption = "Reports";
			mnemonicChar='s';
			break;
		case About:
			caption = "About";
			mnemonicChar='b';
			break;
		case Minimize:
			caption = "Minimize";
			normalImageName = IMAGE_BUTTON_NAGATIVE;
			touchImageName = IMAGE_BUTTON_NAGATIVE_TOUCH;
			mnemonicChar='n';
			break;
		default:
			break;
		}
		final PosIconButton mainMenuButton = new PosIconButton(caption);
		mainMenuButton.setBounds(0, 0, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		if(mnemonicChar!=null)
			mainMenuButton.setMnemonic(mnemonicChar);
		mainMenuButton.setHorizontalAlignment(SwingConstants.CENTER);
		mainMenuButton.setVerticalTextPosition(SwingConstants.CENTER);
		mainMenuButton.setHorizontalTextPosition(SwingConstants.CENTER);

		mainMenuButton.setImage(PosResUtil
				.getImageIconFromResource(normalImageName));
		mainMenuButton.setTouchedImage(PosResUtil
				.getImageIconFromResource(touchImageName));
		mainMenuButton.setFont(PosFormUtil.getMainmenuItemFont());
		mainMenuButton.setOnClickListner(listner);
		mainMenuButton.setEnabled(false);
		mainMenuButton.setTag(buttonType);
		mainMenuButton.setFocusable(false);
		mMenuOptions.add(mainMenuButton);
		return mainMenuButton;
	}

	private FlowLayout createLayout() {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(PANEL_CONTENT_V_GAP);
		flowLayout.setHgap(PANEL_CONTENT_H_GAP);
		flowLayout.setAlignment(FlowLayout.LEFT);
		return flowLayout;
	}

	private IPosButtonListner menuButtonListener = new PosButtonListnerAdapter() {
		public void onClicked(PosButton button) {
			MenuButtons menuItem = (MenuButtons) button.getTag();
			switch (menuItem) {
			case DayStart:
				doDayBegin();
				break;
			case DayEnd:
				doDayEnd();
				break;
			case ShiftStart:
				doShiftStart();
				break;
			case ShiftSummary:
				doShiftSummary();
				break;
//			case ShiftEnd:
//				doShiftEnd();
//				break;
			case ShiftReports:
				doShiftReports();
				break;
			case OrderList:
				doOrderList();
				break;
			case Attendance:
				doAttendance();
				break;
			case SyncNow:
				doSyncNow();
				break;
//			case OrderRefund:
//				doOrderRefund();
//				break;
			case OrderEntry:
				// mPosOrderEntryForm.setVisible(true);
				// setVisible(false);
				doOrderEntry();
				break;

			case Exit:
				
				if(PosEnvSettings.getInstance().getStation()!=null 
					&& PosEnvSettings.getInstance().getStation().getOperationalMode()!=PosTerminalOperationalMode.Master){
					
					doExit();
					return;
				}
				if (!mIsMaintancanceModeOn ) {
					if (PosEnvSettings.getInstance().getCashierShiftInfo() == null
							&& PosEnvSettings.getInstance().getPosDate() != null) {
						PosFormUtil
						.showQuestionMessageBox(
								PosMainMenuForm.this,
								MessageBoxButtonTypes.YesNo,
								"Till is closed and Day End is not done. Do you want to do Day-End now?",
								new PosMessageBoxFormListnerAdapter() {

									@Override
									public void onYesButtonPressed() {
										doDayEnd();
									}

									@Override
									public void onNoButtonPressed() {
										doExit();
									}
								});
					} else {
						doExit();
					}
				} else
					doExit();

				break;
			case Terminal:
				doTerminalSettings();
				break;
			case Messages:
				showMessages();
				break;
			case ContactUs:
				doSendContactMessage();
				break;
			case EditUIConfig:
				doEditPrprtyFile(MenuButtons.EditUIConfig);
				break;
			case EditPrintingConfig:
				doEditPrprtyFile(MenuButtons.EditPrintingConfig);
				break;
			case ViewSessions:
				showLoginSession();
				break;
			case Bookings:
				showOrderBookingForm();
				break;
			case CashOut:
				showCashOutForm();
				break;
			case Export:
				showExport();
				break;
			case Reports:
				showReportForm();
				break;
			case About:
				showAboutForm();
				break;
			//Added by Udhay on 06-07-2021 for Minimize and Maximize
			case Minimize:				
				setVisible(false);
				JFrame f = new JFrame("frame");
				JDialog d = new JDialog(f, "Init Dine");
				// create a label
	           // JLabel l = new JLabel("this is a dialog box");
	            PosButton maximize=new PosButton("Maximize");
	            maximize.setForeground(Color.RED);
	            maximize.setButtonStyle(ButtonStyle.COLORED);
	            maximize.setBackgroundColor(Color.RED, Color.PINK);
	            d.add(maximize);	 
	            // setsize of dialog
	            d.setSize(150, 100);
	            // set visibility of dialog
	            d.setVisible(true);
	            d.addWindowListener(new java.awt.event.WindowAdapter() {
	                @Override
	                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	                	doExit();
	                	/*int selectedOption = JOptionPane.showConfirmDialog(null, 
                                "Do you wanna close the window?", 
                                "Close Window?", 
                                JOptionPane.YES_NO_OPTION); 
						if (selectedOption == JOptionPane.YES_OPTION) {
							doExit();
						}else{
							PosButton maximize=new PosButton("Maximize");
					            maximize.setForeground(Color.RED);
					            maximize.setButtonStyle(ButtonStyle.COLORED);
					            maximize.setBackgroundColor(Color.RED, Color.PINK);
					            d.add(maximize);	 
					            // setsize of dialog
					            d.setSize(150, 100);
					            // set visibility of dialog
					            d.setVisible(true);
							
						}*/
	                	
	                  /*  if (JOptionPane.showConfirmDialog(d, 
	                        "Are you sure you want to close this window?", "Close Window?", 
	                        JOptionPane.YES_NO_OPTION,
	                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){	                    	
	                    		                       	                    }
	                    else{
	                    	d.setVisible(true);
	                    	
	                    }*/
	                    
	                }
	            });
	            maximize.setOnClickListner(new IPosButtonListner() {
	    			
	    			@Override
	    			public void onClicked(PosButton button) {
	    			
	    				setVisible(true);
	    				d.setVisible(false);
	    				
	    			}
	    		});
	           break;
				
			
			}
		}

	};
	
	/*
	 * 
	 */
	private void showReportForm(){
		
		PosObjectBrowserForm form=new PosObjectBrowserForm("Reports",  PosReportsType.values(),ItemSize.Wider,2,2);
		form.setListner(new IPosObjectBrowserListner() {
			
			@Override
			public void onItemSelected(IPosBrowsableItem item) {
				
				if (((PosReportsType)item).equals(PosReportsType.VOID_REPORT) ||
						((PosReportsType)item).equals(PosReportsType.REFUND_REPORT)){
					
					final PosVoidRefundItemReportForm form=new PosVoidRefundItemReportForm((PosReportsType)item);
					PosFormUtil.showLightBoxModal(PosMainMenuForm.this,form);
					
				}else{
					final PosSalesOrderReportForm form=new PosSalesOrderReportForm((PosReportsType)item);
					PosFormUtil.showLightBoxModal(PosMainMenuForm.this,form);
				}
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		});
		PosFormUtil.showLightBoxModal(this,form);
	}
//	/**
//	 * Sales Order  form
//	 */
//	private void showSalesOrderReportForm() {
//
//		final PosSalesOrderReportForm form=new PosSalesOrderReportForm();
////		form.setReportType(PosSalesOrderReportType.DAILYADVANCE);
//		PosFormUtil.showLightBoxModal(this,form);
//	}

	/**
	 * Sales Order  form
	 */
	private void showAboutForm() {

		final PosAboutForm form=new PosAboutForm();
		PosFormUtil.showLightBoxModal(this,form);
	}
	/**
	 * Sales Order  form
	 */
 

	/**
	 * Cashout form
	 */
	private void showCashOutForm() {

		PosFormUtil.showCashOutForm(this, false);
	}


	/**
	 * Order Booking
	 */
	private void showOrderBookingForm(){

		final PosOrderBookingForm form=new PosOrderBookingForm();

		PosFormUtil.showLightBoxModal(this,form);

	}

	/**
	 * tab logins
	 */
	private void showLoginSession() {
			 
		PosFormUtil.showOpenSessiosForm(this,false);
	}

	/**
	 * 
	 */
	private void showMessages() {

		PosFlashMessageListForm mFlashMessagesForm = new PosFlashMessageListForm();
		PosFormUtil.showLightBoxModal(this, mFlashMessagesForm);
	}
	/**
	 * 
	 */
	private void showExport() {

		
		PosTallyInvoiceExportForm form=new PosTallyInvoiceExportForm();
		PosFormUtil.showLightBoxModal(this, form);
	}
	/**
	 * @param edituiconfig 
	 * 
	 */
	protected void doEditPrprtyFile(MenuButtons config) {

		final PosUserAuthenticateForm userAuthenticate = new PosUserAuthenticateForm("Authenticate");
		PosFormUtil.showLightBoxModal(this,userAuthenticate);
		BeanUser user = userAuthenticate.getUser();
		if(user!=null&&PosAccessPermissionsUtil.validateAccess(this, user.getUserGroupId(), "ui_configure")){

			PosPropertyEditForm propertyEditForm = new PosPropertyEditForm((config==MenuButtons.EditUIConfig)? PosPropertyFileType.UI:PosPropertyFileType.PRINT);
			propertyEditForm.setParent(this);
			PosFormUtil.showLightBoxModal(this,propertyEditForm);

		}
	}

	/**
	 * 
	 */
	protected void doSendContactMessage() {
		
		PosContactUsForm form=new PosContactUsForm();
		form.setParent(this);
		PosFormUtil.showLightBoxModal(this,form);
	}

	/**
	 * 
	 */
	private void doShiftSummary() {
		
		PosFormUtil.showShiftSummaryForm(this,false);
	}

	private void doShiftReports() {

		PosUserAuthenticateForm loginForm=new PosUserAuthenticateForm("Authenticate");
		PosFormUtil.showLightBoxModal(this,loginForm);
		BeanUser user=loginForm.getUser();
		if(user!=null&&PosAccessPermissionsUtil.validateAccess(this,user.getUserGroupId(), "shift_report")){
			BeanAccessLog accessLog= new BeanAccessLog();
			accessLog.setFunctionName("ShiftReports");
			accessLog.setUserName(user.getName());
			accessLog.setAccessTime(PosDateUtil.getDateTime());
			accesslogProvider = new PosAccessLogProvider();
			accesslogProvider.updateAccessLog(accessLog);
			PosShiftReportForm shiftReportForm = new PosShiftReportForm();
			PosFormUtil.showLightBoxModal(this, shiftReportForm);
		}
	}

	/**
	 * 
	 */
	private void doOrderList() {

//		PosFormUtil.showOrderDetailsForm(this,false);
		PosFormUtil.showOrderListForm(this,false);
	}


//	private void doOrderStatusReport() {
//
//		PosFormUtil.showOrderStatusReportForm(this,false);
//	}
	private void doOrderEntry() {
		
		if(PosUtil.checkPosDate(this)){

			if (isRecoveryMode()) {
				doRecovery();
				mButtonOrderEntry.setText(isRecoveryMode() ? "Recover"
						: "Order Entry");
			} else {
				OrderEntry();
				mButtonOrderEntry.setText(isRecoveryMode() ? "Recover"
						: "Order Entry");
			}
		}
	}

	/**
	 * 
	 */
	private void doRecovery() {
		PosUserAuthenticateForm loginForm = new PosUserAuthenticateForm(
				"Authenticate");
		PosFormUtil.showLightBoxModal(this, loginForm);
		BeanUser user = loginForm.getUser();
		if (user != null) {
			BeanAccessLog accessLog = new BeanAccessLog();
			accessLog.setFunctionName("doRecovery");
			accessLog.setUserName(user.getName());
			accessLog.setAccessTime(PosDateUtil.getDateTime());
			accesslogProvider = new PosAccessLogProvider();
			accesslogProvider.updateAccessLog(accessLog);
			PosCardPaymentRecoveryForm recoveryForm = new PosCardPaymentRecoveryForm();
			PosFormUtil.showLightBoxModal(this, recoveryForm);
		}
	}

	private void OrderEntry() {

		PosUserAuthenticateForm loginForm=new PosUserAuthenticateForm("Authenticate");
		PosFormUtil.showLightBoxModal(this,loginForm);
		BeanUser user=loginForm.getUser();
		if(user!=null&&PosAccessPermissionsUtil.validateAccess(this,user.getUserGroupId(),"order_entry")){
			BeanCashierShift cashierShift=cashierShiftProvider.getAllOpenCashierShifts().get(user.getCode());
			if(cashierShift!=null){
				mShiftPanel
				.setSelectedCashier(cashierShift.getCashierInfo().getCardNumber());
				mPosOrderEntryForm.setParent(this);
				mPosOrderEntryForm.setVisible(true);
				setVisible(false);	
			}else{
				if(doJoinShift(user)){
					mPosOrderEntryForm.setParent(this);
					mPosOrderEntryForm.setVisible(true);
					setVisible(false);	
				}
			}
		}
	}

	public void setSectedCashier(String id) {
		mShiftPanel.setSelectedCashier(id);
	}

	public void resetCashierDetails() {
		mShiftPanel.resetCashierDetails();
	}

	/**
	 * @param user
	 */
	protected boolean doJoinShift(BeanUser user) {
		boolean valid = false;
		if (user.isEmployee() && !validateAttendance(user.getEmployeeId())) {
			
			System.out.println("main warning===========>");
			PosFormUtil.showInformationMessageBox(this,
					"Attendance is not marked. Please mark attendance first.");
			valid = false;
		} else {

			BeanCashierShift cashierShift = new BeanCashierShift();
			cashierShift.setCashierInfo(user);
			cashierShift.setOpeningDate(PosEnvSettings.getPosEnvSettings()
					.getPosDate());
			cashierShift.setOpeningTime(PosDateUtil.getDateTime());
			/***
			 * To support KOT slave stations
			 */
			//			cashierShift.setShiftItem(cashierShiftProvider.getShiftDetails(PosEnvSettings.getInstance().getStation())
			//					.getShiftItem());
			cashierShift.setShiftItem(cashierShiftProvider.getShiftDetails(null)
					.getShiftItem());

			cashierShift.setOpeningFloat(0.00);
			cashierShift.setIsOpenTill(false);

			if (cashierShiftProvider.openShift(cashierShift)) {
				BeanCashierShift activeCashierShift = cashierShiftProvider
						.getAllOpenCashierShifts().get(user.getCode());
				if (activeCashierShift != null) {
					PosEnvSettings.getInstance().setCashierShiftInfo(
							activeCashierShift);
					mShiftPanel.resetCashierDetails();
					mShiftPanel.setSelectedCashier(activeCashierShift
							.getCashierInfo().getCardNumber());
					valid = true;
				}
			}
		}
		return valid;
	}

	private boolean validateAttendance(int employeeId) {
		PosAttendProvider staffAttendProvider = new PosAttendProvider();
		return staffAttendProvider.isAttendanceOpen(employeeId);
	}

	private void doExit() {
		
		if (mDeviceManager != null)
			try {

				mDeviceManager.shutdownDevices();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		setVisible(false);
		endSession();
		dispose();
		System.exit(0);
	}



	private void doTerminalSettings() {
		BeanUser user = null;
		BeanTerminalInfo station = PosEnvSettings.getPosEnvSettings()
				.getStation();
		if (station != null) {
			PosUserAuthenticateForm loginForm = new PosUserAuthenticateForm(
					"Authenticate");
			PosFormUtil.showLightBoxModal(this, loginForm);
			user = loginForm.getUser();
			if (user != null) {
				BeanAccessLog accessLog = new BeanAccessLog();
				accessLog.setFunctionName("Terminal");
				accessLog.setUserName(user.getName());
				accessLog.setAccessTime(PosDateUtil.getDateTime());
				accesslogProvider = new PosAccessLogProvider();
				accesslogProvider.updateAccessLog(accessLog);
			}
		}
		if ((user!=null&&PosAccessPermissionsUtil.validateAccess(this,user.getUserGroupId(), "terminal"))|| station == null) {
			PosTerminalSettingsForm posSettingForm = new PosTerminalSettingsForm();
			posSettingForm.setListner(new IPosTerminalSettingsFormListner() {
				@Override
				public void onExit() {
					//					setVisible(true);
				}
			});
			// PosFormUtil.showModal(posSettingForm);
			PosFormUtil.showLightBoxModal(this, posSettingForm);
		}
	}

	private void doAttendance() {
		doAttendance(this, false);
		// doPurgeData();

	}

	private void doPurgeData() {
		final PosOrderHdrProvider mOrderHdrProvider = new PosOrderHdrProvider();
		SwingWorker<Boolean, String> swt = new SwingWorker<Boolean, String>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				try {
					mOrderHdrProvider.clearTransactionData(PosEnvSettings
							.getInstance().getPosDate());
				} catch (Exception e) {
					PosFormUtil.closeBusyWindow();
					throw new Exception(
							"Problem in purging old data. Please check the log for details.");
				}
				return true;
			}

			@Override
			protected void done() {
				PosFormUtil.closeBusyWindow();
				PosFormUtil.showInformationMessageBox(null,
						"Purging process completed.");
			}
		};
		swt.execute();
		PosFormUtil
		.showBusyWindow(
				this,
				"Purging the old data. This process may take a few minutes to complete. Please wait...");

	}

	public void doAttendance(JDialog dialog, boolean isOrderEntry) {
		PosAttendanceForm attendanceForm = new PosAttendanceForm(this,
				isOrderEntry);
		PosFormUtil.showLightBoxModal(dialog, attendanceForm);
		BeanCashierShift cashierShift = PosEnvSettings.getInstance()
				.getCashierShiftInfo();
		mShiftPanel.resetCashierDetails();
		check4ActieveShifts();
		if (cashierShift != null) {
			mShiftPanel.setSelectedCashier(PosEnvSettings.getInstance()
					.getCashierShiftInfo().getCashierInfo().getCardNumber());
		}
	}

	/**
	 * @param posOrderEntryForm
	 * @param cardNumber
	 */
	public void doAttendance(JDialog dialog, String cardNumber) {
		
		PosAttendanceForm attendanceForm = new PosAttendanceForm(this, false);
		attendanceForm.setCardNumber(cardNumber);
		PosFormUtil.showLightBoxModal(dialog, attendanceForm);

	}

	public boolean isShiftOpen(String code) {
		return mShiftPanel.isShiftOpen(code);
	}

	public boolean isShiftOpen() {
		return mShiftPanel.isShiftOpen();
	}

	private void doShiftStart() {

		PosUserAuthenticateForm loginForm=new PosUserAuthenticateForm("Authenticate");
		PosFormUtil.showLightBoxModal(this,loginForm);
		BeanUser user=loginForm.getUser();
		if(user!=null&&PosAccessPermissionsUtil.validateAccess(this,user.getUserGroupId(), "open_till")){
			doShiftStart(user);
		}
	}

	private void doShiftStart(final BeanUser user) {

		if (isShiftOpen()) {
			doShiftEnd(  user);
			mButtonShiftStart.setText((isShiftOpen()) ? "Close Till"
					: "Open Till");
		} else {
			String posDate = PosEnvSettings.getInstance().getPosDate();
			if (!posDate.trim().equalsIgnoreCase(PosDateUtil.getDate().trim())) {
				PosFormUtil
				.showQuestionMessageBox(
						this,
						MessageBoxButtonTypes.YesNo,
						"POS date is not the same as system date.Any sales after this till will be treated as "
								+ posDate + "'s sales. Is it ok?",
								new PosMessageBoxFormListnerAdapter() {
							/*
							 * (non-Javadoc)
							 * 
							 * @see
							 * com.indocosmo.pos.forms.messageboxes.
							 * listners.PosMessageBoxFormListnerAdapter#
							 * onYesButtonPressed()
							 */
							@Override
							public void onYesButtonPressed() {
								doShiftStart(user, false);
								mButtonShiftStart
								.setText((isShiftOpen()) ? "Close Till"
										: "Open Till");
							}
						});
			} else {
				doShiftStart(user, false);
				mButtonShiftStart.setText((isShiftOpen()) ? "Close Till"
						: "Open Till");
			}
		}
	}

	private void doShiftStart(BeanUser user , boolean hasPosCard) {

		PosShiftOpeningForm shiftForm = new PosShiftOpeningForm("Open Till");
		if (user.getCardNumber() != null)
			shiftForm.setCashierCard(user.getCardNumber());
		shiftForm.setActiveUser(user);
		shiftForm.hasPosCard(hasPosCard);
		shiftForm.setListner(new PosShiftFormAdapter() {
			@Override
			public void onOkClicked(BeanCashierShift shift) {
				PosEnvSettings.getInstance().setCashierShiftInfo(shift);
				PosEnvSettings.getInstance().setTillOpenCashierShiftInfo(shift);
				mShiftPanel.resetCashierDetails();
				mShiftPanel.setSelectedCashier(shift.getCashierInfo().getCardNumber());
			}
		});

		PosFormUtil.showLightBoxModal(this, shiftForm);
	}

	private void doShiftEnd(final BeanUser user) {

		PosFormUtil
		.showQuestionMessageBox(
				this,
				MessageBoxButtonTypes.YesNo,
				"You will not be able to open till for this shift after you have closed the till. Are you sure to proceed? ",
				new PosMessageBoxFormListnerAdapter() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * com.indocosmo.pos.forms.messageboxes.listners
					 * .PosMessageBoxFormListnerAdapter
					 * #onYesButtonPressed()
					 */
					@Override
					public void onYesButtonPressed() {
					
						if(PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().openCashBox())
							PosDeviceManager.getInstance().openCashBox();
					
					doShiftEndProcess(user);
					}
				});

	}

	/**
	 * 
	 */
	protected void doShiftEndProcess(BeanUser user) {

		BeanCashierShift shift = PosEnvSettings.getInstance().getTillOpenCashierShiftInfo();
		ArrayList<BeanOrderHeader> openOrderList = null;
		try {

			final int activeSessionCount = (new PosLoginSessionsProvider().getActiveSessionCount(shift.getID()));
			if(activeSessionCount>1){

				PosFormUtil.showErrorMessageBox(null, "One or more stations are using this till. Please logout from all stations before proceeding.");
				return ;
			}
//			openOrderList = (new PosOrderHdrProvider()).getOrderHeaders("status="+(PosOrderStatus.Partial).getCode()+" and shift_id="+shift.getShiftItem().getId()+" and order_date = '"+PosEnvSettings.getInstance().getPosDate()+"'");
//			if(!openOrderList.isEmpty()){
//
//				PosFormUtil.showErrorMessageBox(null, "There is partially paid orders , Please complete the payments first.");
//				return ;
//			}
		} catch (Exception e) {
			
			PosFormUtil.showSystemError(this);
			PosLog.write(this, "onOkButtonClicked", e);
			return;
		}

//		mShiftPanel.setSelectedCashier(cashierShiftProvider.getShiftDetails(PosEnvSettings.getInstance().getStation())
//				.getCashierInfo().getCardNumber());
		PosShiftClosingForm shiftForm = new PosShiftClosingForm("Close Till");
		shiftForm.setActiveUser(user);
		shiftForm.setListner(new PosShiftFormAdapter() {
			@Override
			public void onOkClicked(BeanCashierShift shift) {
				PosEnvSettings.getInstance().setCashierShiftInfo(null);
				mShiftPanel.resetCashierDetails();
				check4ActieveShifts();
				super.onOkClicked(shift);
			}
		});
		PosFormUtil.showLightBoxModal(this, shiftForm);
	}

	private void doSyncNow() {
		// Synchronize all data
		// SynchronizeToServer.synchAll();
		PosUserAuthenticateForm loginForm = new PosUserAuthenticateForm(
				"Authenticate");
		PosFormUtil.showLightBoxModal(this, loginForm);
		BeanUser user = loginForm.getUser();
		if (user!=null&&PosAccessPermissionsUtil.validateAccess(this,user.getUserGroupId(), "sync_now")) {
			BeanAccessLog accessLog = new BeanAccessLog();
			accessLog.setFunctionName("SyncNow");
			accessLog.setUserName(user.getName());
			accessLog.setAccessTime(PosDateUtil.getDateTime());
			accesslogProvider = new PosAccessLogProvider();
			accesslogProvider.updateAccessLog(accessLog);
			PosDataSyncForm dataSyncForm = new PosDataSyncForm();
			PosFormUtil.showLightBoxModal(this, dataSyncForm);
		}
	}

//	/**
//	 * 
//	 */
//	private void doOrderRefund() {
//
//		PosFormUtil.showOrderRefundForm(this,false);
//	}

	private void doDayBegin() {

		//		if(!PosLicenceUtil.checkLicence()) System.exit(0);
		
		//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
		//PosLog.debug("doDayBegin================Step 1 ");
		PosDayProcessForm dayStartForm = new PosDayProcessForm(PosDayProcess.DAY_START);
		dayStartForm.setParent(this);
		dayStartForm.setlistener(mDayStartedListner);
		PosFormUtil.showLightBoxModal(this, dayStartForm);
	}

	private void doInitializePosTerminal() throws Exception {

		SwingWorker<Boolean, String> swt = new SwingWorker<Boolean, String>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				try {
					//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
					//PosLog.debug("doDayProcCommonTask================ doInitializePosTerminal Step 1 ");

					mPosEnvSettings.loadEnvSettings_StageTwo();
					//PosLog.debug("doDayProcCommonTask================ doInitializePosTerminal Step 2 ");
					initDevices();
				//	PosLog.debug("doDayProcCommonTask================ doInitializePosTerminal Step 3 ");
					mPosOrderEntryForm = PosOrderEntryForm.getInstance();
				//	PosLog.debug("doDayProcCommonTask================ doInitializePosTerminal Step 4 ");
					mPosOrderEntryForm.pack();
					//PosLog.debug("doDayProcCommonTask================ doInitializePosTerminal Step 5 ");
					mPosOrderEntryForm
					.setListner(new IPosOrderEntryFormListner() {
						@Override
						public void onExit() {
							PosEnvSettings.getInstance()
							.setCashierShiftInfo(null);
							check4ActieveShifts();
							setVisible(true);
						}
					});
					//PosLog.debug("doDayProcCommonTask================ doInitializePosTerminal Step 6 ");
					mPosOrderEntryForm
					.setDefaultCloseOperation(PosOrderEntryForm.DO_NOTHING_ON_CLOSE);
					//PosLog.debug("doDayProcCommonTask================ doInitializePosTerminal Step 7 ");
					mPosOrderEntryForm.setAlwaysOnTop(true);
					//PosLog.debug("doDayProcCommonTask================ doInitializePosTerminal Step 8 ");
					// mPosOrderEntryForm.setParent(this);
					mPosOrderEntryForm.setListner(PosMainMenuForm.this);
					//PosLog.debug("doDayProcCommonTask================ doInitializePosTerminal Step 9 ");
					//					BeanCashierShift cashierShift = cashierShiftProvider
					//							.getShiftDetails(PosEnvSettings.getInstance().getStation());

					/**
					 * Load all shifts. do not check for station. 
					 * To support  KOT slave stations
					 * 
					 */
					BeanCashierShift cashierShift = cashierShiftProvider
							.getShiftDetails(null);
					//PosLog.debug("doDayProcCommonTask================Step 2.1.1 ");
					check4ActieveShifts();
					mShiftPanel.loadCashierList();
					//PosLog.debug("doDayProcCommonTask================Step 2.1.2 ");
					doDayProcCommonTask(MenuButtons.DayStart);
					//PosLog.debug("doDayProcCommonTask================Step 2.1.3 ");
					if (cashierShift != null) {
						PosEnvSettings.getInstance().setCashierShiftInfo(
								cashierShift);
						mShiftPanel.resetCashierDetails();
						mShiftPanel.setSelectedCashier(cashierShift
								.getCashierInfo().getCardNumber());
					}
				} catch (Exception e) {
					PosLog.write(this, "doInitializePosTerminal", e);
					PosFormUtil.closeBusyWindow();
				}
				return true;
			}

			@Override
			protected void done() {
				PosFormUtil.closeBusyWindow();
			}
		};
		swt.execute();
		PosFormUtil.showBusyWindow(this,
				"Please wait while it configures the terminal...");

	}

	private PosDayProcessFormListener mDayStartedListner = new PosDayProcessFormListener() {
		@Override
		public void onOkPressed(String date) {
			try {
				//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
				//PosLog.debug("mDayStartedListner================Step 2 ");
				doInitializePosTerminal();
			//	PosLog.debug("mDayStartedListner================Step 2.1 ");
				PosEnvSettings.getInstance().setPosDate(date);
				//PosLog.debug("mDayStartedListner================Step 2.2 ");
				mLabelPosDate.setText(PosEnvSettings.getInstance().getPosDate());
				//PosLog.debug("mDayStartedListner================Step 2.3 ");
				openNewSession();
			//	PosLog.debug("mDayStartedListner================Step 2.4 ");
				
			} catch (Exception e) {
				PosFormUtil.showErrorMessageBox(null, e.getMessage());
			}
		}
	};
	
	/**
	 * @throws SQLException
	 */
	private void openNewSession() throws SQLException{
		
		if(PosEnvSettings.getInstance().getCashierShiftInfo()!=null){
			
			BeanLoginSessions loginSession=new BeanLoginSessions();
			loginSession.setPosId(PosEnvSettings.getInstance().getStation().getId());
			loginSession.setCashierShiftId(PosEnvSettings.getInstance().getCashierShiftInfo().getID());
			loginSession.setLoginUserId(PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getId());
			loginSession.setStartAt(PosDateUtil.getDateTime());
			
			final PosLoginSessionsProvider loginSessionProvider=new PosLoginSessionsProvider();
			loginSessionProvider.openNewSession(loginSession);
		}		
	}
	
	/**
	 * 
	 */
	/**
	 * @throws SQLException 
	 * 
	 */
	private void endSession()  {
		
		if(PosEnvSettings.getInstance().getCashierShiftInfo()!=null){
		
			
			try {
				final PosLoginSessionsProvider loginSessProvider=new PosLoginSessionsProvider();
				loginSessProvider.closeOpenSession(PosEnvSettings.getInstance().getStation().getId());
			} catch (SQLException e) {
				
				PosLog.write(this, "endSession", e);
			}
			
		}
	}

	private void doDayEnd() {
		
		PosDayProcessForm dayEndForm = new PosDayProcessForm(
				PosDayProcess.DAY_END);
		dayEndForm.setParent(this);
		dayEndForm.setlistener(mDayEndFormListner);
		PosFormUtil.showLightBoxModal(this, dayEndForm);

	}

	private PosDayProcessFormListener mDayEndFormListner = new PosDayProcessFormListener() {

		@Override
		public void onOkPressed(String date) {
						
			/* to clear all active tab sessions at dayend  
			 * Author - Aslam  
			 * Date - 01-02-2020 */
			PosDayProcessProvider pdp = new PosDayProcessProvider();
			pdp.removeTabSessions();
			/* to clear all active tab sessions at dayend ends */
			
			doPrintingAtDayEnd(date);
			doDayProcCommonTask(MenuButtons.DayEnd);
			PosFormUtil.showInformationMessageBox(PosMainMenuForm.this, "Day End has been completed.");
			mPosOrderEntryForm.close();
		}

	};
	
	private void doPrintingAtDayEnd(String posDate){
		
		
		if(!PosDeviceManager.getInstance().hasReceiptPrinter() 
				|| PosDeviceManager.getInstance().getReceiptPrinter()==null
				|| !PosDeviceManager.getInstance().getReceiptPrinter().isDeviceInitialized())
			return;
			

		if (PosEnvSettings.getInstance().getPrintSettings().getPrintDayEndReportAtDayEnd()==EnablePrintingOption.NO) 
			return;

		boolean doPrint=true;

		if (PosEnvSettings.getInstance().getPrintSettings().getPrintDayEndReportAtDayEnd()==EnablePrintingOption.ASK) 
			doPrint=PosFormUtil.showQuestionMessageBox(this  ,
					MessageBoxButtonTypes.YesNo,
					"  Do you want to print the Day End report?",
					null)==MessageBoxResults.Yes;

		if(doPrint){

			IPosSearchableItem mShiftAll;
			try{
				mShiftAll=PosShopShiftProvider.getItemForAllShift();
	
				final PosShiftReport report;
				
				switch(PosEnvSettings.getInstance().getPrintSettings().getPrintingFormat()){
					
					case "NIHON":
						report=new PosShiftReportNihon();
						break;
				 	default:
				 		report =new PosShiftReport();
						break;
						
					}
				
				report.setCriteria(" closing_date ='" + posDate + "'");
				report.setPosDate(posDate);
	
				report.setSummaryOnly(false);
				report.setShift((BeanShopShift) mShiftAll);
				report.setPrintPaymentDtls(false);
				report.setSalesReportOnly(false);
				report.setIsDayEndReport(true);
				PosShiftReport.doPrintDayEndReport(this, report);
			} catch (Exception e) {
				
				PosFormUtil.showErrorMessageBox(this, e.getMessage());
			}

		}
	}

	private void doDayProcCommonTask(MenuButtons dayProc) {
		//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
	//	PosLog.debug("doDayProcCommonTask================Step 3 ");
		final boolean status = (dayProc == MenuButtons.DayStart);
		setMenuOptions(dayProc);
		mShiftPanel.setEnabled(status);
		mLabelPosDate.setText("");
	}



	private void setMenuOptions(MenuButtons dayProc) {
		//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
		//PosLog.debug("setMenuOptions================Step 4 ");

		if (dayProc == MenuButtons.DayStart){
			//PosLog.debug(" MenuButtons.DayStart================Step 5=========satrt process ");
			mButtonDayStart.setEnabled(false);
			mButtonDayStart.setVisible(false);
			mButtonDayEnd.setEnabled(true);
			mButtonDayEnd.setVisible(true);
			
			mButtonShiftStart.setEnabled(true);
			mButtonLoginSessions.setEnabled(false);
			mButtonAttendance.setEnabled(true);
			mButtonShiftSummary.setEnabled(false);
			mButtonOrderEntry.setEnabled(false);
			mButtonShiftReports.setEnabled(true);
			mButtonOrderDetails.setEnabled(false);
//			mButtonOrderStatusReport.setEnabled(false);
			mButtonSyncNow.setEnabled(true);
//			mButtonOrderRefund.setEnabled(false);
			mButtonContactUs.setEnabled(true);
			
			mButtonTerminal.setEnabled(true);
			mButtonViewMessage.setEnabled(true);
			mButtonContactUs.setEnabled(true);
			mButtonCashOut.setEnabled(true);
			mButtonExport.setEnabled(true);
			mButtonReports.setEnabled(true);
			mButtonAbout.setEnabled(true);
		//	PosLog.debug(" MenuButtons.DayStart================Step 5=========satrt completed ");
		}else if (dayProc == MenuButtons.DayEnd) {

			mButtonDayStart.setEnabled(true);
			mButtonDayStart.setVisible(true);
			mButtonDayEnd.setEnabled(false);
			mButtonDayEnd.setVisible(false);
			
			mButtonShiftStart.setEnabled(false);
			mButtonLoginSessions.setEnabled(false);
			mButtonAttendance.setEnabled(false);
			mButtonShiftSummary.setEnabled(false);
			mButtonOrderEntry.setEnabled(false);
			mButtonShiftReports.setEnabled(false);
			mButtonOrderDetails.setEnabled(false);
//			mButtonOrderStatusReport.setEnabled(false);
			mButtonSyncNow.setEnabled(true);
//			mButtonOrderRefund.setEnabled(false);
			mButtonContactUs.setEnabled(true);
			
			mButtonTerminal.setEnabled(true);
			mButtonViewMessage.setEnabled(true);
			mButtonContactUs.setEnabled(true);
			mButtonCashOut.setEnabled(false);
			mButtonExport.setEnabled(false);
			mButtonReports.setEnabled(false);
			mButtonAbout.setEnabled(true);

		}

	}

	@Override
	public void onExit() {
		setVisible(true);
	}

	public static synchronized void writeProcessLog(String message,
			PosProcessMessageType type) {
		if (message != null) {
			message = "<font color=" + type.getTextColor() +">" + message + "</font>";
			mLogPanel.write(message);
		}
	}

	public static synchronized void writeProcessLog(String message) {
		if (message != null) {
			mLogPanel.write(message);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.terminal.devices.PosDeviceManager.IPosDeviceManagerListener
	 * #onInitStarted(com.indocosmo.pos.dataproviders.terminaldb.
	 * PosDevSettingProvider.PosDevices, java.lang.String)
	 */
	@Override
	public void onInitStarted(PosDevices device, String message) {
		writeProcessLog(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.terminal.devices.PosDeviceManager.IPosDeviceManagerListener
	 * #
	 * onInitFailed(com.indocosmo.pos.dataproviders.terminaldb.PosDevSettingProvider
	 * .PosDevices, java.lang.String)
	 */
	@Override
	public void onInitFailed(PosDevices device, String message) {
		writeProcessLog(message, PosProcessMessageType.ERROR);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.terminal.devices.PosDeviceManager.IPosDeviceManagerListener
	 * #onInitSuccess(com.indocosmo.pos.dataproviders.terminaldb.
	 * PosDevSettingProvider.PosDevices, java.lang.String)
	 */
	@Override
	public void onInitSuccess(PosDevices device, String message) {
		writeProcessLog(message, PosProcessMessageType.SUCCEESS);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.terminal.devices.PosDeviceManager.IPosDeviceManagerListener
	 * #onMessage(java.lang.String)
	 */
	@Override
	public void onMessage(String message) {
		writeProcessLog(message);
	}
	
//	private void createLogPanel() {
//
//		final int top = mMainMenuItemContainer.getY()
//				+ mMainMenuItemContainer.getHeight();
//		final int width = mMainMenuItemContainer.getWidth();
////		final int height = mLeftPanel.getHeight()
////				- mMainMenuItemContainer.getHeight();
//		mLogPanel = new PosProcessLogDisplayPanel(this, width, LOG_PANEL_HEIGHT);
//		mLogPanel.setLocation(0, top);
//		mLogPanel.setOpaque(false);
//		mLeftPanel.setFocusable(false);
//		mLeftPanel.add(mLogPanel);
//	}
	
	private void createLogPanel() {

		final int top = mMainMenuItemContainer.getY() + mMainMenuItemContainer.getHeight();
		
//		final int height = mLeftPanel.getHeight()
//				- mMainMenuItemContainer.getHeight();
		mLogPanel = new PosProcessLogDisplayPanel(this, mRightPanel.getWidth(), LOG_PANEL_HEIGHT);
		mLogPanel.setLocation(2, top);
		mLogPanel.setOpaque(false);
		mLeftPanel.setFocusable(false);
		mRightPanel.add(mLogPanel);
		
	}
	public void createSyncStatusPanel()
	{
		final int top = mMainMenuItemContainer.getY() + mMainMenuItemContainer.getHeight();
		dbStatusInfoDisplayPanel = new PosSyncStatusInfoPanel(LOG_PANEL_HEIGHT,mMainMenuItemContainer.getWidth());
		dbStatusInfoDisplayPanel.setLocation(2,top);
		 

		mLeftPanel.add(dbStatusInfoDisplayPanel);
	}
	
	
	public void createStockInfoPanel()
	{ 
		final int top = mMainMenuItemContainer.getY() + mMainMenuItemContainer.getHeight();
		mStockInfoPanel = new PosStockInfoPanel(LOG_PANEL_HEIGHT, mMainMenuItemContainer.getWidth());
		mStockInfoPanel.setLocation(2, top);
		mLeftPanel.add(mStockInfoPanel);  
	}
	
	
	private void intSyncUpdateCheck(){
		
		java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(timerTask, 1000, PosEnvSettings.getInstance().getServerDataUpdateCheckInterval());
	}
	
	TimerTask timerTask=new TimerTask() {

        @Override
        public void run() {
            try {
            	if(PosEnvSettings.getInstance().getApplicationType()==ApplicationType.Standard)
            		PosSyncUtil.checkForSynUpdates();
            	PosFormUtil.checkForScrollMessges();
				
			} catch (Exception e) {
				PosLog.write(this, "checkSysnUpdates", e);
			} 
        }
    };
	
 

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosMessageListner#showMessage(java.lang.String)
	 */
	@Override
	public void showMessage(String message) {
		
		mScrollMsgPanel.showMessage(message);
 		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosMessageListner#showLogoImage()
	 */
	@Override
	public void hideMessage() {
		
		mScrollMsgPanel.showMessage("");
	}
	
	
	/**
	 * @param message
	 */
	public static void SET_MESSAGE(String message) {
		
		if(mSingleInstance!=null ) {
			if(message!=null && message.trim().length()>0)
				mSingleInstance.showMessage(message);
			else
				mSingleInstance.hideMessage();
		
		}
 		
	}

	private void initializeBackupUtil(){
		
		com.indocosmo.backuputil.common.PosEnvSettings envSettings=com.indocosmo.backuputil.common.PosEnvSettings.getInstance();
		
		envSettings.setShopCode(PosEnvSettings.getInstance().getShop().getCode());
		envSettings.setTerminalCode(PosEnvSettings.getInstance().getStation().getCode());
		
		envSettings.setSqlServerName(PosDBUtil.getInstance().getServerPath());
		envSettings.setSqlServerPort(PosDBUtil.getInstance().getServerPort());
		envSettings.setSqlUserName(PosDBUtil.getInstance().getServerUser());
		envSettings.setSqlPassword(PosDBUtil.getInstance().getServerPassword());
		envSettings.setDbName(PosDBUtil.getInstance().getShopDBName());
		
		envSettings.setBackupFolder(PosEnvSettings.getInstance().getBackupFolder());
		envSettings.setNumOfBackups(PosEnvSettings.getInstance().getNumberOfBackupToKeep());
		envSettings.setLogPath(PosEnvSettings.getInstance().getLogPath());
	} 
}
