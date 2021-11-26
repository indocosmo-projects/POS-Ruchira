package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.CardTypes;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosPasswordUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanUser;
import com.indocosmo.pos.data.providers.shopdb.PosUsersProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.keypads.PosLoginNumKeypad;
import com.indocosmo.pos.forms.components.keypads.PosLoginNumKeypad.IPosNumLoginkeypadListner;
import com.indocosmo.pos.forms.components.textfields.PosCardReaderField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumericPasswordField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;

@SuppressWarnings("serial")
public class PosUserAuthenticateForm extends PosBaseForm {

	private static final int PANEL_CONTENT_H_GAP = PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP = PosOrderEntryForm.PANEL_CONTENT_V_GAP;

	private static final int LABEL_WIDTH = 150;
	private static final int TXT_FIELD_WIDTH = 230;

	private static final int LOGIN_PANEL_HEIGHT = 100;
	private static final int CONTENT_PANEL_HEIGHT = 340;

	private static final int FORM_HEIGHT = CONTENT_PANEL_HEIGHT
			+ LOGIN_PANEL_HEIGHT + PANEL_CONTENT_V_GAP * 2;
	private static final int FORM_WIDTH = PANEL_CONTENT_H_GAP * 5 + LABEL_WIDTH + TXT_FIELD_WIDTH;


	private JPanel mContentPanel;
	private JPanel mUserLoginPanel;
	private JLabel mLabelCardNumber;
	private JLabel mLabelPassword;
	private JPanel mMainContentPanel;

	private PosCardReaderField mtxtCardNumber;
	private JTextField mCurrentCtrl;
	private PosTouchableNumericPasswordField mTxtPassword;

	BeanUser mUsersObject = null;
	String mUserId;

	private PosUserAuthenticateFormListener listener;
	private PosMainMenuForm mainFDormObj;
	private PosButton mButtonAttendance;

	/** Setting listener
	 * @param listener
	 */
	public void setlistener(PosUserAuthenticateFormListener listener) {
		this.listener = listener;
	}

	/**
	 * Constructor with one parameter
	 * @param title
	 */
	public PosUserAuthenticateForm(String title) {
		super(title, FORM_WIDTH, FORM_HEIGHT);
		initControls();
	}


	/**
	 * Constructor with two parameters
	 * @param title
	 * @param userId
	 */
	public PosUserAuthenticateForm(String title, String userId) {
		super(title, FORM_WIDTH, FORM_HEIGHT);
		this.mUserId = userId;
		initControls();
	}

	/**
	 * @param string
	 * @param mParent
	 */
	public PosUserAuthenticateForm(String title, PosMainMenuForm mParent) {
		super(title, FORM_WIDTH, FORM_HEIGHT);
		initControls();
		this.mainFDormObj = mParent;
		createAttandanceButton();
	}

	/**
	 * 
	 */
	private void createAttandanceButton() {

		mButtonAttendance=new PosButton();	
		mButtonAttendance.setText("Attendance");
		mButtonAttendance.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonAttendance.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));	
		mButtonAttendance.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonAttendance.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT );	
		mButtonAttendance.setOnClickListner(attendanceButtonListener);
		addButtonsToBottomPanel(mButtonAttendance);

	}

	private IPosButtonListner attendanceButtonListener = new IPosButtonListner() {

		@Override
		public void onClicked(PosButton button) {
			onAttandanceClicked();
			mtxtCardNumber.requestFocus();
		}
	};
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		mMainContentPanel = panel;
		mMainContentPanel.setLayout(null);
	}

	/**
	 * 
	 */
	protected void onAttandanceClicked() {
		mainFDormObj.doAttendance(this, true);
	}

	/**
	 * Method for creating panels and calling the controls.
	 * 
	 */

	protected void initControls() {

		super.initControls();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				if (mUserId != null)
					mTxtPassword.requestFocus();
				else
					mtxtCardNumber.requestFocus();
				super.windowOpened(e);
			}
		});

		mUserLoginPanel = new JPanel();
		mUserLoginPanel.setBounds(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP,
				FORM_WIDTH - PANEL_CONTENT_H_GAP * 2, LOGIN_PANEL_HEIGHT);

		mUserLoginPanel.setLayout(null);

		mMainContentPanel.add(mUserLoginPanel);

		mContentPanel = new JPanel();
		mContentPanel.setBounds(PANEL_CONTENT_H_GAP, mUserLoginPanel.getY()
				+ mUserLoginPanel.getHeight() - 1, FORM_WIDTH
				- PANEL_CONTENT_H_GAP * 2, CONTENT_PANEL_HEIGHT);
		mContentPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		mMainContentPanel.add(mContentPanel);

		createUserLoginControls();
		createLoginKeypadControl();

	}
	/**
	 * Method for the user login controls.
	 */
	private void createUserLoginControls() {

		mLabelCardNumber = new JLabel();
		mLabelCardNumber.setText("Card Number :");
		mLabelCardNumber.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelCardNumber.setVerticalAlignment(SwingConstants.CENTER);
		mLabelCardNumber.setBounds(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP,
				LABEL_WIDTH, 40);
		mLabelCardNumber.setFont(PosFormUtil.getLoginLabelFont());
		mUserLoginPanel.add(mLabelCardNumber);

		int left = mLabelCardNumber.getX()+LABEL_WIDTH + PANEL_CONTENT_H_GAP;
		mtxtCardNumber = new PosCardReaderField(this,TXT_FIELD_WIDTH);
		mtxtCardNumber.hideResetButton(true);
		mtxtCardNumber.hideBrowseButton(true);
		mtxtCardNumber.setLocation(left, PANEL_CONTENT_V_GAP);
		mtxtCardNumber.addFocusListener(focusListener);
		mtxtCardNumber.setPosCardType(CardTypes.User);
		mtxtCardNumber.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				if(mtxtCardNumber.isCardSwiped()){
					mUsersObject =checkLogin(false);
					if(mUsersObject!=null){

						setVisible(false);
						dispose();
					}
				}
				mTxtPassword.requestFocus();
			}

			@Override
			public void onReset() {
				// TODO Auto-generated method stub

			}
		});


		mUserLoginPanel.add(mtxtCardNumber);

		int top = mLabelCardNumber.getY() + mLabelCardNumber.getHeight()
		+ PANEL_CONTENT_V_GAP;
		mLabelPassword = new JLabel("Password :");
		mLabelPassword.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelPassword.setVerticalAlignment(SwingConstants.CENTER);
		mLabelPassword.setBounds(PANEL_CONTENT_H_GAP , top,
				LABEL_WIDTH, 40);
		mLabelPassword.setFont(PosFormUtil.getLoginLabelFont());
		mUserLoginPanel.add(mLabelPassword);

		left = mLabelPassword.getX()+LABEL_WIDTH + PANEL_CONTENT_H_GAP;
		mTxtPassword = new PosTouchableNumericPasswordField(this,TXT_FIELD_WIDTH);
		mTxtPassword.setLocation(left, top);
		mTxtPassword.hideResetButton(true);
		mTxtPassword.hideBrowseButton(true);
		mTxtPassword.setFont(PosFormUtil.getLabelFont());
		mTxtPassword.addFocusListener(focusListener);
		mUserLoginPanel.add(mTxtPassword);
		mTxtPassword.setListner(new PosTouchableFieldAdapter(){

			/* (non-Javadoc)
			 * @see com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter#onValueSelected(java.lang.Object)
			 */
			@Override
			public void onValueSelected(Object value) {

				onOkButtonClicked();

			}
		});

		if (mUserId != null) {
			mtxtCardNumber.setText(mUserId);
			mtxtCardNumber.setEditable(false);
		}


	}

	/**
	 * Method for the login keypad control
	 */
	private void createLoginKeypadControl() {
		PosLoginNumKeypad mPosloginNumKeypad = new PosLoginNumKeypad();
		mPosloginNumKeypad.setOnClicButtonkListner(posLoginNumkeypadListner);
		mPosloginNumKeypad.setOnClearButtonkListner(posLoginNumkeypadListner);
		mContentPanel.add(mPosloginNumKeypad);
	}

	/**
	 * Listener for Text field focusing
	 */
	private FocusListener focusListener = new FocusAdapter() {
		@Override
		public void focusGained(FocusEvent e) {
			JTextField jtf = (JTextField) e.getSource();
			if (mCurrentCtrl != null)
				mCurrentCtrl.setBackground(Color.WHITE);
			mCurrentCtrl = jtf;
			mCurrentCtrl.setBackground(Color.GREEN);
		}
	};

	/**
	 * listener for PosLoginNumKeypad Button. 
	 * 
	 */
	private IPosNumLoginkeypadListner posLoginNumkeypadListner = new IPosNumLoginkeypadListner() {
		@Override
		public void onClickButton(String value) {
			if (mCurrentCtrl != null) {
				mCurrentCtrl.setText(mCurrentCtrl.getText() + value);
			}
		}

		@Override
		public void onClearButton() {
			if (mCurrentCtrl != null) {
				mCurrentCtrl.setText("");
				mCurrentCtrl.requestFocus();
			}

		}
	};

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	public boolean onOkButtonClicked() {
		Boolean valid = false;
		try {
			mUsersObject = checkLogin(true);
			if (mUsersObject != null) {
				valid = true;
				setVisible(false);
				dispose();
				if (valid && listener != null) {
					listener.onUserSelected(mUsersObject);
				}
			} else{
				PosFormUtil.showErrorMessageBox(PosUserAuthenticateForm.this,
						"Sorry, you do not have permission to use this function.");
				mUsersObject=null;
			}
		} catch (Exception e) {
			PosFormUtil.showErrorMessageBox(this,"Failed to get user information.");
			PosLog.write(this, "checkLogin", e);
		}

		return valid;
	}

	public BeanUser getUser(){
		return mUsersObject;
	}

	/**
	 * Method for getting user information. 
	 * @return mUsersObject.
	 * 
	 */
	//	private PosUsersObject getUserInfo() {
	//
	//		try {
	//			mUsersObject = new PosUsersProvider().getUserByCard(mtxtCardNumber
	//					.getText());
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		return mUsersObject;
	//
	//	}

	private BeanUser checkLogin(boolean validatePassword) {
		final String userCardNo = mtxtCardNumber.getText();
		final String password = new String(mTxtPassword.getPassword());
		BeanUser user=null;
		BeanCashierShift shift = null;
		try {
			user = new PosUsersProvider().getUserByCard(userCardNo);
			if(user!=null && user.isActive()==1) {
				if (!validatePassword || (validatePassword && user != null && PosPasswordUtil.comparePassword(password,
						user.getPassword()))){
					//				mPosCashierShiftProvider = new PosCashierShiftProvider();
					//				shift = mPosCashierShiftProvider.getOpenCashierById(user.getId());
					//				if(shift!=null){
					//					PosEnvSettings.getInstance().setCashierShiftInfo(shift);
					//				}
				}else{
					user=null;
				}
			}else {
				user=null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}

	/**
	 * InterFace 
	 * @author deepak
	 *
	 */
	public interface PosUserAuthenticateFormListener {
		public void onUserSelected(BeanUser posUserObject);
	}
}
