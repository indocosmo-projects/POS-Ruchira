package com.indocosmo.pos.forms.components.terminal.settings;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.PosOrderEntryForm;


@SuppressWarnings("serial")
public final class DatabaseSettingsLayout extends PosTermnalSettingsBase {				
	

	private JLabel mLabelServerSettingCaption;
	
	private JLabel mlabelServerDetails;
	private JTextField mTxtServerName;
	
	private JLabel mLabelDatabaseName;
	private JTextField mTxtDatabaseName;
	
	private JLabel mLabelUserId;
	private JTextField mTxtUserId;
	
	private JLabel mLabelPassword;
	private JTextField mTxtPassword;
		
	private static final int TEXT_FIELD_WIDTH=180;
	private static final int TEXT_FIELD_HEIGHT=30;
	
	private static final int LABEL_WIDTH=100;
	private static final int LABEL_HEIGHT=30;	
	
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;	
	
	private String mServername;
	private String mDatabaseName;
	private String mUserId;
	private String mPassword;


	public DatabaseSettingsLayout(RootPaneContainer parent) {	
		super(parent,"Data Base");
//		setBounds(PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP,width-PANEL_CONTENT_H_GAP,
//				height-PosTabControl.BUTTON_HEIGHT-PANEL_CONTENT_V_GAP);
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
		int left=getWidth()/2-160;
		int top=getY()+PANEL_CONTENT_V_GAP*2;
		mLabelServerSettingCaption=new JLabel();
		mLabelServerSettingCaption.setText("Database Settings");
		mLabelServerSettingCaption.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelServerSettingCaption.setBounds(left, top, 320, LABEL_HEIGHT);		
//		mLabelServerSettingCaption.setFont(PosFormUtil.getTextFieldFont());	
		mLabelServerSettingCaption.setFont(PosFormUtil.getLabelHeaderFont());	
		mLabelServerSettingCaption.setOpaque(true);
		add(mLabelServerSettingCaption);	
		setServer();
		setDatabase();
		setUserid();
		setpassword();
	}
	
	private void setServer(){
//		int left=mLabelServerSettingCaption.getX();		
		int left=getWidth()/2-LABEL_WIDTH-PANEL_CONTENT_H_GAP*5;
		int top=mLabelServerSettingCaption.getY()+mLabelServerSettingCaption.getHeight()+PANEL_CONTENT_V_GAP*3;
		mlabelServerDetails=new JLabel();
		mlabelServerDetails.setText("Server");
		mlabelServerDetails.setHorizontalAlignment(SwingConstants.LEFT);		
		mlabelServerDetails.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mlabelServerDetails.setFont(PosFormUtil.getLabelFont());
		mlabelServerDetails.setFocusable(true);
		add(mlabelServerDetails);
		
		left=mlabelServerDetails.getX()+mlabelServerDetails.getWidth()+PANEL_CONTENT_H_GAP*2;
		mTxtServerName=new JTextField();	
		mTxtServerName.setHorizontalAlignment(JTextField.LEFT);
		mTxtServerName.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtServerName.setFont(PosFormUtil.getTextFieldFont());		
		mTxtServerName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				super.mouseClicked(e);				
				PosFormUtil.showSoftKeyPad((RootPaneContainer )mParent,mTxtServerName,null);
			}
		});		
		add(mTxtServerName);
	}
	
	private void setDatabase(){
		int left=mlabelServerDetails.getX();		
		int top=mlabelServerDetails.getY()+mlabelServerDetails.getHeight()+PANEL_CONTENT_V_GAP;
		mLabelDatabaseName=new JLabel();
		mLabelDatabaseName.setText("Database");
		mLabelDatabaseName.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelDatabaseName.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelDatabaseName.setFont(PosFormUtil.getLabelFont());
		mLabelDatabaseName.setFocusable(true);
		add(mLabelDatabaseName);
		
		left=mLabelDatabaseName.getX()+mLabelDatabaseName.getWidth()+PANEL_CONTENT_H_GAP*2;
		mTxtDatabaseName=new JTextField();	
		mTxtDatabaseName.setHorizontalAlignment(JTextField.LEFT);
		mTxtDatabaseName.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtDatabaseName.setFont(PosFormUtil.getTextFieldFont());		 
		mTxtDatabaseName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				super.mouseClicked(e);				
				PosFormUtil.showSoftKeyPad((RootPaneContainer )mParent,mTxtDatabaseName,null);
			}
		});		
		add(mTxtDatabaseName);
	}
	private void setUserid(){
		int left=mLabelDatabaseName.getX();		
		int top=mLabelDatabaseName.getY()+mLabelDatabaseName.getHeight()+PANEL_CONTENT_V_GAP;
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
				PosFormUtil.showSoftKeyPad((RootPaneContainer )mParent,mTxtUserId,null);
			}
		});		
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
		mTxtPassword=new JTextField();	
		mTxtPassword.setHorizontalAlignment(JTextField.LEFT);
		mTxtPassword.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtPassword.setFont(PosFormUtil.getTextFieldFont());		
		mTxtPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				super.mouseClicked(e);				
				PosFormUtil.showSoftKeyPad((RootPaneContainer )mParent,mTxtPassword,null);
			}
		});		
		add(mTxtPassword);
	}
	
	 public void actionPerformed(ActionEvent evt) {
		 JOptionPane.showMessageDialog(null, "Pressed");	 
		}
	 
	 private void setDatabaseSettings(){
		 mServername=mTxtServerName.getText();
		 mDatabaseName=mTxtDatabaseName.getText();
		 mUserId=mTxtUserId.getText();
		 mPassword=mTxtPassword.getText();
	 }

	/**
	 * @return the mServername
	 */
	public final String getServername() {
		return mTxtServerName.getText();
	}

	/**
	 * @return the mDatabase
	 */
	public final String getDatabase() {
		return mTxtDatabaseName.getText();
	}
	
	/**
	 * @return the mUserId
	 */
	public final String getUserId() {
		return mTxtUserId.getText();
	}

	/**
	 * @return the mPassword
	 */
	public final String getPassword() {
		return mTxtPassword.getText();
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
