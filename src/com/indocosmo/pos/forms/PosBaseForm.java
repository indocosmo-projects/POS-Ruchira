package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanUser;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;

@SuppressWarnings("serial")
public abstract class PosBaseForm extends JDialog implements IPosFormEventsListner {

	private  int TITLE_PANEL_HEIGHT = 60;
	private  int BOTTOM_PANEL_HEIGHT = 76;

	protected static final int PANEL_BORDER_WIDTH = 2;

	protected static final int IMAGE_BUTTON_WIDTH = 150;
	protected static final int IMAGE_BUTTON_HEIGHT = 60;

	protected static final int PANEL_CONTENT_H_GAP = PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	protected static final int PANEL_CONTENT_V_GAP = PosOrderEntryForm.PANEL_CONTENT_V_GAP;

	protected static final String IMAGE_BUTTON_OK = "dlg_ok.png";
	protected static final String IMAGE_BUTTON_OK_TOUCH = "dlg_ok_touch.png";

	protected static final String IMAGE_BUTTON_CANCEL = "dlg_cancel.png";
	protected static final String IMAGE_BUTTON_CANCEL_TOUCH = "dlg_cancel_touch.png";

	protected static final String IMAGE_BUTTON_RESET = "dlg_reset.png";
	protected static final String IMAGE_BUTTON_RESET_TOUCH = "dlg_reset_touch.png";

	protected static final Color PANEL_BG_COLOR = new Color(78, 128, 188);
	protected static final Color LABEL_BG_COLOR = new Color(78, 128, 188);
	protected static final Color LABEL_FG_COLOR = Color.WHITE;

	private int CONTENT_PANEL_HEIGHT;
	private int CONTENT_PANEL_WIDTH;

	private int FORM_HEIGHT = TITLE_PANEL_HEIGHT + BOTTOM_PANEL_HEIGHT
			+ PANEL_CONTENT_V_GAP * 4;
	private int FORM_WIDTH = PANEL_CONTENT_H_GAP * 2;

	private JPanel mContentPane;
	private JPanel mTitlePanel;
	private JPanel mContentPanel;
	private JPanel mBottomPanel;
	private JLabel mlabelTitle;

	protected PosButton mButtonOk;
	protected PosButton mButtonCancel;
	protected PosButton mButtonReset;
	
	private JComponent defaultComponent;

	private String mTitle;

	protected IPosFormEventsListner mListner;

	private Container mParent;
	
	private BeanUser activeUser;

	/* (non-Javadoc)
	 * @see java.awt.Component#getParent()
	 */
	@Override
	public Container getParent() {
		// TODO Auto-generated method stub
		return mParent;
	}

	/**
	 * the mParent
	 */
	public void setParent(Container parent) {
		 mParent = parent;
	}
	
	/**
	 * Use this constructor to build the form
	 * 
	 * @param title
	 *            - title for the form
	 * @param cPanelwidth
	 *            - content panel width
	 * @param cPanelHeight
	 *            - content panel height
	 */
	public PosBaseForm(String title, int cPanelwidth, int cPanelHeight, int titleHeight) {
		
		TITLE_PANEL_HEIGHT=titleHeight;
		FORM_HEIGHT = TITLE_PANEL_HEIGHT + BOTTOM_PANEL_HEIGHT
				+ PANEL_CONTENT_V_GAP * 4;
		
		CONTENT_PANEL_WIDTH = cPanelwidth;
		CONTENT_PANEL_HEIGHT = cPanelHeight;
		FORM_WIDTH += CONTENT_PANEL_WIDTH;
		FORM_HEIGHT += CONTENT_PANEL_HEIGHT;
		mTitle = title;
		initControls();
	}
	
	
	
	/**
	 * Use this constructor to build the form
	 * 
	 * @param title
	 *            - title for the form
	 * @param cPanelwidth
	 *            - content panel width
	 * @param cPanelHeight
	 *            - content panel height
	 */
	public PosBaseForm(String title, int cPanelwidth, int cPanelHeight, boolean showTitle) {
		
		if(!showTitle) {
			TITLE_PANEL_HEIGHT=0;
			FORM_HEIGHT = TITLE_PANEL_HEIGHT + BOTTOM_PANEL_HEIGHT
					+ PANEL_CONTENT_V_GAP * 4;
		}
		
		CONTENT_PANEL_WIDTH = cPanelwidth;
		CONTENT_PANEL_HEIGHT = cPanelHeight;
		FORM_WIDTH += CONTENT_PANEL_WIDTH;
		FORM_HEIGHT += CONTENT_PANEL_HEIGHT;
		mTitle = title;
		initControls();
	}
	
	public PosBaseForm(String title, int cPanelwidth, int cPanelHeight, boolean showTitle, boolean showFooter ) {
		
		if(!showTitle) 
			TITLE_PANEL_HEIGHT=0;
		if(!showFooter) 
			BOTTOM_PANEL_HEIGHT=0;
			
			FORM_HEIGHT = TITLE_PANEL_HEIGHT + BOTTOM_PANEL_HEIGHT
					+ PANEL_CONTENT_V_GAP * 4;
	
		
		CONTENT_PANEL_WIDTH = cPanelwidth;
		CONTENT_PANEL_HEIGHT = cPanelHeight;
		FORM_WIDTH += CONTENT_PANEL_WIDTH;
		FORM_HEIGHT += CONTENT_PANEL_HEIGHT;
		mTitle = title;
		initControls();
	}

	/**
	 * Use this constructor to build the form
	 * 
	 * @param title
	 *            - title for the form
	 * @param cPanelwidth
	 *            - content panel width
	 * @param cPanelHeight
	 *            - content panel height
	 */
	public PosBaseForm(String title, int cPanelwidth, int cPanelHeight) {
		
		CONTENT_PANEL_WIDTH = cPanelwidth;
		CONTENT_PANEL_HEIGHT = cPanelHeight;
		FORM_WIDTH += CONTENT_PANEL_WIDTH;
		FORM_HEIGHT += CONTENT_PANEL_HEIGHT;
		mTitle = title;
		initControls();
	}
	
	/**
	 * @param parent
	 */
	public PosBaseForm(RootPaneContainer parent){
		
		this.mParent=(Container)parent;
	}

	/**
	 * 
	 */
	protected void initControls() {

		setSize(FORM_WIDTH, FORM_HEIGHT);

		mContentPane = new JPanel();
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mContentPane.setLayout(null);
		setContentPane(mContentPane);

		mTitlePanel = new JPanel();
		mTitlePanel.setBounds(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP,
				FORM_WIDTH - PANEL_CONTENT_H_GAP * 2, TITLE_PANEL_HEIGHT);
		mTitlePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mTitlePanel.setBackground(PANEL_BG_COLOR);
		add(mTitlePanel);

		mContentPanel = new JPanel();
		mContentPanel.setBounds(PANEL_CONTENT_H_GAP, mTitlePanel.getY()
				+ mTitlePanel.getHeight() + PANEL_CONTENT_V_GAP, FORM_WIDTH
				- PANEL_CONTENT_H_GAP * 2, CONTENT_PANEL_HEIGHT);
		mContentPanel.setLayout(null);
		// mContentPanel.setBackground(Color.RED);
		mContentPanel.setBorder(new EtchedBorder());
		add(mContentPanel);

		mBottomPanel = new JPanel();
		mBottomPanel.setBounds(PANEL_CONTENT_H_GAP, mContentPanel.getY()
				+ mContentPanel.getHeight() + PANEL_CONTENT_V_GAP, FORM_WIDTH
				- PANEL_CONTENT_H_GAP * 2, BOTTOM_PANEL_HEIGHT);
		mBottomPanel.setLayout(new FlowLayout());
		mBottomPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mBottomPanel);

		setTitlePanelContent();
		setContentPanel(mContentPanel);
		setBottomPanel();
		
		this.addWindowListener(wndLisener);
		
		setDefaultButton(mButtonOk);
	}
	
	/**
	 * 
	 */
	public void doActionAccept(){
		
		okButtonListner.onClicked(mButtonOk);
	}
	
	/**
	 * 
	 */
	public void doActionCancel(){
		
		imgCancelButtonListner.onClicked(mButtonCancel);
		
	}
	
	/**
	 * 
	 */
	public void doActionReset(){
		
		imgResetButtonListner.onClicked(mButtonReset);
	}

	/**
	 * @param enabled
	 */
	public void setOkEnabled(boolean enabled) {
		mButtonOk.setEnabled(enabled);
	}

	/**
	 * @param enabled
	 */
	protected void setCancelEnabled(boolean enabled) {
		mButtonCancel.setEnabled(enabled);
	}

	/**
	 * @param enabled
	 */
	protected void setResetEnable(boolean enabled) {
		mButtonReset.setEnabled(enabled);
	}

	/**
	 * 
	 */
	private void setTitlePanelContent() {
		setTitle();
	}

	/**
	 * 
	 */
	private void setTitle() {
		mlabelTitle = new JLabel();
		mlabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelTitle.setVerticalAlignment(SwingConstants.CENTER);
		mlabelTitle.setText(mTitle);
		mlabelTitle.setPreferredSize(new Dimension(mTitlePanel.getWidth()
				- PANEL_CONTENT_H_GAP * 2, mTitlePanel.getHeight()
				- PANEL_CONTENT_V_GAP * 2));
		mlabelTitle.setFont(PosFormUtil.getHeadingFont());
		mlabelTitle.setOpaque(true);
		mlabelTitle.setBackground(LABEL_BG_COLOR);
		mlabelTitle.setForeground(LABEL_FG_COLOR);
		mTitlePanel.add(mlabelTitle);
	}

	/* (non-Javadoc)
	 * @see java.awt.Dialog#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		mTitle = title;
		mlabelTitle.setText(mTitle);
	}

	/**
	 * 
	 */
	private void setBottomPanel() {
		createOkButton();
	}

	public void setOkButtonFocus(){
		mButtonOk.requestFocus(true);
	}
	/**
	 * 
	 */
	private void createOkButton() {
		int left = mBottomPanel.getX() + PANEL_CONTENT_H_GAP * 10;
		mButtonOk = new PosButton();
		mButtonOk.setDefaultButton(true);
		mButtonOk.setText("OK");
		mButtonOk.setMnemonic('O');
		mButtonOk
				.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonOk.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		mButtonOk.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonOk.setBounds(left, PANEL_CONTENT_V_GAP * 5, IMAGE_BUTTON_WIDTH,
				IMAGE_BUTTON_HEIGHT);
		mButtonOk.setOnClickListner(okButtonListner);
		mButtonOk.setFocusable(true);
		mBottomPanel.add(mButtonOk);

		left = mButtonOk.getX() + mButtonOk.getWidth() + PANEL_CONTENT_V_GAP
				* 2;
		mButtonCancel = new PosButton();
		mButtonCancel.setCancel(true);
		mButtonCancel.setText("Cancel");
		mButtonCancel.setMnemonic('C');
		mButtonCancel.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonCancel.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));
		mButtonCancel.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonCancel.setBounds(left, PANEL_CONTENT_V_GAP, IMAGE_BUTTON_WIDTH,
				IMAGE_BUTTON_HEIGHT);
		mButtonCancel.setOnClickListner(imgCancelButtonListner);
		mBottomPanel.add(mButtonCancel);

		left = mButtonCancel.getX() + mButtonCancel.getWidth()
				+ PANEL_CONTENT_V_GAP * 2;
		mButtonReset = new PosButton();
		mButtonReset.setText("Reset");
		mButtonReset.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_RESET));
		mButtonReset.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_RESET_TOUCH));
		mButtonReset.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonReset.setBounds(left, PANEL_CONTENT_V_GAP, IMAGE_BUTTON_WIDTH,
				IMAGE_BUTTON_HEIGHT);
		mButtonReset.setOnClickListner(imgResetButtonListner);
		mButtonReset.setVisible(false);
		mButtonReset.setMnemonic('R');
		mBottomPanel.add(mButtonReset);
	}

	/**
	 * @param visible
	 */
	public void setAllButtonVisibility(boolean visible) {
		setOkButtonVisible(visible);
		setResetButtonVisible(visible);
		setCancelButtonVisible(visible);
	}

	/**
	 * @param visible
	 */
	public void setOkButtonVisible(boolean visible) {
		mButtonOk.setVisible(visible);
	}
	
	public void setOkButtonEnabled(boolean enabled) {
		mButtonOk.setEnabled(enabled);
	}

	/**
	 * @param visible
	 */
	public void setResetButtonVisible(boolean visible) {
		mButtonReset.setVisible(visible);
	}

	/**
	 * @param visible
	 */
	public void setCancelButtonVisible(boolean visible) {
		mButtonCancel.setVisible(visible);
	}

	protected IPosButtonListner imgResetButtonListner = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			onResetButtonClicked();
		}
	};

	protected IPosButtonListner imgCancelButtonListner = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			if (onCancelButtonClicked())
				closeWindow();
		}
	};

	/**
	 * Override this method to implement Cancel button pressed events. Should
	 * return the value true/false to determine whether the window should be
	 * closed on not.
	 * 
	 * @return true/false
	 */
	@Override
	public boolean onCancelButtonClicked() {
		if (mListner != null)
			mListner.onCancelButtonClicked();
		return true;
	}

	protected IPosButtonListner okButtonListner = new IPosButtonListner() {
		public void onClicked(PosButton button) {
			if (onOkButtonClicked()) {
				closeWindow();
				if (mListner != null)
					mListner.onOkButtonClicked();
			}
		}
	};

	public void setListner(IPosFormEventsListner listner) {
		mListner = listner;
	}

	/**
	 * Override this method to implement OK button pressed events. Should return
	 * the value true/false to determine whether the window should be closed on
	 * not.
	 * 
	 * @return true/false
	 */
	@Override
	public boolean onOkButtonClicked() {
		return true;
	}

	/**
	 * Closes the window.
	 */
	public void closeWindow() {
		setVisible(false);
		dispose();
	}

	/**
	 * Override this method to construct the content panel.
	 */

	protected abstract void setContentPanel(JPanel panel);

	@Override
	public void onResetButtonClicked() {
		// TODO Auto-generated method stub

	}

	/**
	 * @param button
	 */
	public void addButtonsToBottomPanel(PosButton button) {
		mBottomPanel.add(button);
	}

	/**
	 * @param button
	 * @param at
	 */
	public void addButtonsToBottomPanel(PosButton button, int at) {
		mBottomPanel.add(button, at);
	}
	
	/**
	 * @param title
	 * @param listner
	 * @return
	 */
	public PosButton addButtonsToBottomPanel(String title, IPosButtonListner listner) {
		
		return addButtonsToBottomPanel(title,listner,0);
	}
	
	/**
	 * @param title
	 * @param listner
	 * @param at
	 * @return
	 */
	public PosButton addButtonsToBottomPanel(String title, IPosButtonListner listner,int at) {
		
		PosButton button = new PosButton();
//		button.setDefaultButton(true);
		button.setText(title);
		button
				.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK));
		button.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setBounds(0, PANEL_CONTENT_V_GAP * 5, IMAGE_BUTTON_WIDTH,
				IMAGE_BUTTON_HEIGHT);
		button.setOnClickListner(listner);
		
		addButtonsToBottomPanel(button,at);
		
		return button; 
	}


	/**
	 * @param caption
	 */
	public void setOkButtonCaption(String caption) {
//		mButtonOk.setText(caption);
		setOkButtonCaption(caption,caption.charAt(0));
	}
	
	/**
	 * @param caption
	 */
	public void setOkButtonCaption(String caption, char mnemonic) {
		mButtonOk.setText(caption);
		mButtonOk.setMnemonic(mnemonic);
	}

	/**
	 * @param caption
	 */
	public void setCancelButtonCaption(String caption) {
//		mButtonCancel.setText(caption);
		setCancelButtonCaption(caption,caption.charAt(0));
	}
	
	/**
	 * @param caption
	 */
	public void setCancelButtonCaption(String caption, char mnemonic) {
		mButtonCancel.setText(caption);
		mButtonCancel.setMnemonic(mnemonic);
	}

	/**
	 * @param caption
	 */
	public void setResetlButtonCaption(String caption) {
//		mButtonReset.setText(caption);
		setResetlButtonCaption(caption,caption.charAt(0));
	}
	
	/**
	 * @param caption
	 */
	public void setResetlButtonCaption(String caption, char mnemonic) {
		mButtonCancel.setText(caption);
		mButtonReset.setMnemonic(mnemonic);
	}
	
	private WindowListener wndLisener =new WindowListener() {
		
		@Override
		public void windowOpened(WindowEvent e) {

			onWindowOpened(e);
		}
		
		@Override
		public void windowIconified(WindowEvent e) {
			onWindowIconified(e);
			
		}
		
		@Override
		public void windowDeiconified(WindowEvent e) {
			onWindowDeiconified(e);
		}
		
		@Override
		public void windowDeactivated(WindowEvent e) {
			onWindowDeactivated(e);
			
		}
		
		@Override
		public void windowClosing(WindowEvent e) {
			onWindowClosing(e);
		}
		
		@Override
		public void windowClosed(WindowEvent e) {
			onWindowClosed(e);
			
		}
		
		@Override
		public void windowActivated(WindowEvent e) {
			
			onWindowActivated( e);
		}

		
	};
	
	protected void onWindowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	protected void onWindowOpened(WindowEvent e) {
		if(defaultComponent!=null)
			defaultComponent.requestFocus();
		
	}
	
	
	protected void onWindowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	protected void onWindowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	protected void onWindowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	protected void onWindowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	protected void onWindowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param defaultComponent the defaultComponent to set
	 */
	protected void setDefaultComponent(JComponent defaultComponent) {
		this.defaultComponent = defaultComponent;
		
	}
	
	/**
	 * @param btn
	 */
	protected void setDefaultButton(PosButton btn){
		
		btn.setDefaultButton(true);
		btn.registerKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_DOWN_MASK);
	}
	
	WindowListener defComponentListner=new WindowAdapter() {
		
		public void windowOpened(WindowEvent e) {
			

		};
	};

	/**
	 * @return the activeUser
	 */
	public BeanUser getActiveUser() {
		return activeUser;
	}

	/**
	 * @param activeUser the activeUser to set
	 */
	public void setActiveUser(BeanUser activeUser) {
		this.activeUser = activeUser;
	}

}
