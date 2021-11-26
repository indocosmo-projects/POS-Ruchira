package com.indocosmo.pos.forms.components.terminal.settings;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.listners.IPosNumKeyPadFormListner;
import com.indocosmo.pos.forms.messageboxes.listners.PosNumKeyPadFormAdapter;


@SuppressWarnings("serial")
public final class PosUserSettingsLayout extends PosTermnalSettingsBase {		
	
	private JLabel mLabelUserSettingCaption;	
	
	private JLabel mLabelUserId;
	private JTextField mTxtUserId;
	
	private JLabel mLabelPassword;
	private JPasswordField mTxtPassword;
		
	private static final int TEXT_FIELD_WIDTH=180;
	private static final int TEXT_FIELD_HEIGHT=30;
	
	private static final int LABEL_WIDTH=100;
	private static final int LABEL_HEIGHT=30;	
	
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;	
	
	private int mUserId;
	private int mPassword;


	public PosUserSettingsLayout(RootPaneContainer parent) {
		super(parent,"User");
//		setBounds(PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP,width-PANEL_CONTENT_H_GAP,
//				height-PosTabControl.BUTTON_HEIGHT-PANEL_CONTENT_V_GAP);
//		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
//		setBackground(Color.RED);
//		int width=PosDeviceSettingsLayout.LAYOUT_WIDTH;
//		int height=PosDeviceSettingsLayout.LAYOUT_HEIGHT-PosTabControl.BUTTON_HEIGHT-PANEL_CONTENT_V_GAP;
//		setSize(width,height);
//		setLayout(null);
//		setOpaque(true);
//		setServerDetails();
	}	
	
	private void setServerDetails(){			
		setCaption();
	}			
	
	private void setCaption(){
		
//		int left=getX()+PANEL_CONTENT_H_GAP;
		int top=getY()+PANEL_CONTENT_V_GAP*2;
		int left=getWidth()/2-LABEL_WIDTH;	
		mLabelUserSettingCaption=new JLabel();
		mLabelUserSettingCaption.setText("User Settings");
		mLabelUserSettingCaption.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelUserSettingCaption.setBounds(left, top, 250, LABEL_HEIGHT);		
//		mLabelUserSettingCaption.setFont(PosFormUtil.getTextFieldFont());	
		mLabelUserSettingCaption.setFont(PosFormUtil.getLabelHeaderFont());	
		mLabelUserSettingCaption.setOpaque(true);
		add(mLabelUserSettingCaption);	
		setUserid();		
		setpassword();
	}	
	
	private void setUserid(){
//		int left=getX()+PANEL_CONTENT_H_GAP;
		int left=getWidth()/2-LABEL_WIDTH-PANEL_CONTENT_H_GAP*5;
		int top=mLabelUserSettingCaption.getY()+mLabelUserSettingCaption.getHeight()+PANEL_CONTENT_V_GAP*3;
		mLabelUserId=new JLabel();
		mLabelUserId.setText("UserId");
		mLabelUserId.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelUserId.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelUserId.setFont(PosFormUtil.getLabelFont());
		mLabelUserId.setFocusable(true);
		add(mLabelUserId);
		
		left=mLabelUserId.getX()+mLabelUserId.getWidth()+PANEL_CONTENT_H_GAP*2;
		mTxtUserId=new JTextField();	
		mTxtUserId.setHorizontalAlignment(JTextField.LEFT);
		mTxtUserId.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtUserId.setFont(PosFormUtil.getTextFieldFont());	
		mTxtUserId.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				super.mouseClicked(e);				
				PosFormUtil.showNumKeyPad(mTxtUserId, numKeyPadFormListner);
			}
		});		
//		mTxtUserId.setEditable(false);
		add(mTxtUserId);
	}
	
	private void setpassword(){
		int left=mLabelUserId.getX();		
		int top=mLabelUserId.getY()+mLabelUserId.getHeight()+PANEL_CONTENT_V_GAP;
		mLabelPassword=new JLabel();
		mLabelPassword.setText("Password");
		mLabelPassword.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelPassword.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelPassword.setFont(PosFormUtil.getLabelFont());
		mLabelPassword.setFocusable(true);
		add(mLabelPassword);
		
		left=mLabelPassword.getX()+mLabelPassword.getWidth()+PANEL_CONTENT_H_GAP*2;
		mTxtPassword=new JPasswordField();	
		mTxtPassword.setHorizontalAlignment(JTextField.LEFT);
		mTxtPassword.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtPassword.setFont(PosFormUtil.getTextFieldFont());		
		mTxtPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				super.mouseClicked(e);				
				PosFormUtil.showNumKeyPad(mTxtPassword, numKeyPadFormListner);
			}
		});		
		add(mTxtPassword);
	}
	
	public void setUserData(){
//		mUserId=Integer.parseInt(mTxtUserId.getText());
//		mPassword=Integer.parseInt(mTxtPassword.getText());
		
		mUserId=1;
		mPassword=123456;
		
	}
	
	private IPosNumKeyPadFormListner numKeyPadFormListner=new PosNumKeyPadFormAdapter() {
		public void onValueChanged(String value) {
//			mTxtUserId.setText(String.valueOf(value));
		}
	};
	
	 public void actionPerformed(ActionEvent evt) {
		 JOptionPane.showMessageDialog(null, "Pressed");	 
		}

	/**
	 * @return the userId
	 */
	public final int getUserId() {		
		int userId=mTxtUserId.getText().toString().equals("")?0:Integer.parseInt(mTxtUserId.getText().toString());
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public final void setUserId(int userId) {
		this.mUserId = userId;
	}

	/**
	 * @return the password
	 */
	public final String getPassword() {
		String password=mTxtPassword.getPassword().toString();	
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public final void setPassword(int password) {
		this.mPassword = password;
	}

	@Override
	public String getTabCaption() {
		return "User";
	}
	
	@Override
	public boolean onValidating() {
		// TODO Auto-generated method stub
		return true;
	}

	

	@Override
	public void onGotFocus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSave() {
		// TODO Auto-generated method stub
		return false;
	}

}
