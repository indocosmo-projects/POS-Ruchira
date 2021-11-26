/**
 * 
 */
package com.indocosmo.pos.forms.components.terminal.shift;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.JTextComponent;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.CardTypes;
import com.indocosmo.pos.common.utilities.PosCardUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosPasswordUtil;
import com.indocosmo.pos.data.beans.BeanAccessLog;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanShopShift;
import com.indocosmo.pos.data.beans.BeanUser;
import com.indocosmo.pos.data.providers.shopdb.PosAccessLogProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCashierProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCashierShiftProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShopShiftProvider;
import com.indocosmo.pos.forms.PosBaseForm;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.PosUserAuthenticateForm;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumericField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.listners.IPosNumKeyPadFormListner;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public abstract class PosShiftBasePanel extends JPanel {

	public enum ShiftActions{
		Shift_Open,
		Shift_Close
	}

	protected static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	protected static final int PANEL_CONTENT_V_GAP=1;
	protected static final int TEXT_FIELD_WIDTH=330;
	protected static final int TEXT_FIELD_HEIGHT=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
	protected static final int LABEL_WIDTH=180;
	protected static final int LABEL_HEIGHT=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
	protected static final Color LABEL_BG_COLOR=new Color(78,128,188);
	protected static final int MESSAGE_PANEL_HEIGHT=30;
	

	protected PosTouchableDigitalField mTxtCacheirCard;
	protected JTextField mTxtCashierName;
	protected JTextField mTxtShiftStartDate;
	protected JTextField mTxtShiftStartTime;
	protected PosItemBrowsableField mTxtShift;
	protected PosTouchableNumericField mTxtOpeningFloatAmount; 
	protected BeanShopShift mShopShiftInfo;
	protected BeanCashierShift mCashierShiftInfo;
	protected BeanUser mCashierInfo;

	protected JDialog mParent;
	protected ShiftActions mShiftAction;
	protected boolean mValidPassword;
	protected boolean mIsOpenTill;
	protected double mDefaultFloat;
	
	protected PosCashierProvider mCashierProvider;
	protected PosCashierShiftProvider mCashierShiftProvider;
	private PosAccessLogProvider accesslogProvider;
	protected boolean mHasPosCard;

	public PosShiftBasePanel(JDialog parent, ShiftActions shiftAction){
		mParent=parent;
		mShiftAction=shiftAction;
		setLayout(new FlowLayout(FlowLayout.LEFT,0,PANEL_CONTENT_V_GAP));
		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mCashierProvider=new PosCashierProvider();
		mCashierShiftProvider=new PosCashierShiftProvider();
		initControls();
	}

	protected void setLabelFont(JLabel label){
//		Font newLabelFont=new Font(PosFormUtil.getLabelFont().getName(),Font.BOLD,16);  
//		label.setFont(newLabelFont);
		
		label.setFont(PosFormUtil.getLabelFont());
	}

	protected void setShortMessagePanel(String message){
		JLabel labelMessage=new JLabel();
		labelMessage.setText(message);
		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);		
		labelMessage.setPreferredSize(new Dimension(getWidth()-(PANEL_CONTENT_H_GAP/2+1), MESSAGE_PANEL_HEIGHT));				
		labelMessage.setOpaque(true);
		labelMessage.setBackground(LABEL_BG_COLOR);
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(PosFormUtil.getLabelFont());
		add(labelMessage);
	}

	
	protected void setCashierCard(){

		JLabel label=new JLabel();
		label.setText(PosFormUtil.getMnemonicString("Card Number :", 'N'));
		
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		label.setBackground(Color.LIGHT_GRAY);
		label.setOpaque(true);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		setLabelFont(label);
		add(label);

		mTxtCacheirCard=new PosTouchableDigitalField(mParent,TEXT_FIELD_WIDTH);
		mTxtCacheirCard.setMnemonic('N');
		
		mTxtCacheirCard.setTitle("Cashier Card Number ?");
		mTxtCacheirCard.setListner(onCachierCardSelectedListener);
		mTxtCacheirCard.setHorizontalTextAlignment(JTextField.LEFT);
		add(mTxtCacheirCard);

	}
	
	protected IPosTouchableFieldListner onCachierCardSelectedListener=new IPosTouchableFieldListner() {
		
		@Override
		public void onValueSelected(Object value) {
			String cardNumber=String.valueOf(value);
			if(cardNumber==null || cardNumber.trim().length()<=0) return;
			
//			if(cardNumber.startsWith("#")&&cardNumber.endsWith("?")){
//				mTxtCacheirCard.setText(cardNumber.substring(1,cardNumber.length()-1));
				mHasPosCard=PosCardUtil.checkForValidPosCard(CardTypes.User, cardNumber);
				if(mHasPosCard)
					cardNumber=PosCardUtil.getPosCardNumber(CardTypes.User, cardNumber);
				resetFields();
				PosShopShiftProvider shiftProvider=new PosShopShiftProvider();
				Map<Integer,BeanShopShift> shopShifts=shiftProvider.getShopShifts();
				shiftProvider=null;
				if(shopShifts.size()==1){
					
					BeanShopShift sShift = shopShifts.entrySet().iterator().next().getValue();
					mTxtShift.setSelectedItem(sShift);
					mShopShiftInfo = sShift;
					mTxtOpeningFloatAmount.requestFocus();
				}else
					mTxtShift.requestFocus();
				
				if(cardNumber!=null){
					mTxtCacheirCard.setText(cardNumber);
					onCashierCardNumberChanged(String.valueOf(cardNumber));
					mTxtCacheirCard.setSelectedAll();
				}
//			}
			
		}

		@Override
		public void onReset() {
			resetFields();
		}
	};

	protected void setCashierNameField(){

		JLabel label=new JLabel("Name :");
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		label.setBackground(Color.LIGHT_GRAY);
		label.setOpaque(true);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		
		setLabelFont(label);
		add(label);

		mTxtCashierName=new JTextField();
		mTxtCashierName.setFont(PosFormUtil.getTextFieldFont());
		mTxtCashierName.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
		mTxtCashierName.setEditable(false);
		add(mTxtCashierName);

	}
	public void setDefaultFocus(){
		mTxtCacheirCard.requestFocus();
	}
	
	
	public void hasPosCard(boolean has){
		mHasPosCard=has;
	}

	protected void setStartingDataTime(){
		setStartingDataTime(true);
	}

	protected void setStartingDataTime(boolean isVisible){

		JLabel label=new JLabel("Shift Open :");
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		label.setBackground(Color.LIGHT_GRAY);
		label.setOpaque(true);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		setLabelFont(label);

		mTxtShiftStartDate=new JTextField();
		mTxtShiftStartDate.setFont(PosFormUtil.getTextFieldFont());
		mTxtShiftStartDate.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH/2-1, TEXT_FIELD_HEIGHT));
		mTxtShiftStartDate.setEditable(false);	

		mTxtShiftStartTime=new JTextField();
		mTxtShiftStartTime.setFont(PosFormUtil.getTextFieldFont());
		mTxtShiftStartTime.setPreferredSize(new Dimension( TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
		mTxtShiftStartTime.setEditable(false);	

		if(isVisible){
			add(label);
//			add(mTxtShiftStartDate);
			add(mTxtShiftStartTime);
		}
	}

	protected void setShift(){

		JLabel label=new JLabel();
		label.setText(PosFormUtil.getMnemonicString("Shift :", 'S'));
		
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		label.setBackground(Color.LIGHT_GRAY);
		label.setOpaque(true);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		
		setLabelFont(label);
		add(label);

		PosShopShiftProvider shiftProvider=new PosShopShiftProvider();
		Map<Integer,BeanShopShift> shopShifts=shiftProvider.getShopShifts();
		shiftProvider=null;

		mTxtShift=new PosItemBrowsableField(mParent,TEXT_FIELD_WIDTH);
		mTxtShift.setMnemonic('S');
		
		mTxtShift.setBrowseItemList(new ArrayList<BeanShopShift>(shopShifts.values()));
		mTxtShift.setBrowseWindowSize(3, 3);
		mTxtShift.setTitle("Shift ?");
		mTxtShift.setEditable(false);
//		mTxtShift.setSelectedValue(shopShifts.entrySet().iterator().next().getValue());
		mTxtShift.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				
				if(value!=null )
				mShopShiftInfo=(BeanShopShift)value;	
			}

			@Override
			public void onReset() {
				mShopShiftInfo=null;
			}
		});
		add(mTxtShift);
	}

	protected void setOpeningFloatAmount(){
		JLabel label=new JLabel();
		label.setText(PosFormUtil.getMnemonicString("Float Amount :", 'A'));
		
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		label.setBackground(Color.LIGHT_GRAY);
		label.setOpaque(true);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		setLabelFont(label);
		add(label);

		mTxtOpeningFloatAmount=new PosTouchableNumericField(mParent,TEXT_FIELD_WIDTH);
		mTxtOpeningFloatAmount.setMnemonic('A');
		mTxtOpeningFloatAmount.setTitle("Float Amount ?");
		mTxtOpeningFloatAmount.setEditable(false);
		mTxtOpeningFloatAmount.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtOpeningFloatAmount.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				
				((PosBaseForm)mParent).doActionAccept();
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		add(mTxtOpeningFloatAmount);
	}

	private boolean validatePassword(){
		if(mHasPosCard)return true;
		mValidPassword=false;
		PosFormUtil.showAcceptPasswordUI(mParent, new IPosNumKeyPadFormListner() {

			@Override
			public void onValueChanged(String value) {
				final String mPassword=value;
				if(mCashierInfo!=null && !PosPasswordUtil.comparePassword(mPassword, mCashierInfo.getPassword())){
					if(mCashierShiftInfo!=null&&mCashierShiftInfo.IsOpenTill()&&mPassword.equalsIgnoreCase(PosEnvSettings.getInstance().getSecurityPassword())){
						PosUserAuthenticateForm loginForm=new PosUserAuthenticateForm("Authenticate");
						PosFormUtil.showLightBoxModal(mParent,loginForm);
						BeanUser user=loginForm.getUser();
						if(user!=null){
							BeanAccessLog accessLog= new BeanAccessLog();
							accessLog.setFunctionName("Close Till");
							accessLog.setUserName(user.getName());
							accessLog.setAccessTime(PosDateUtil.getDateTime());
							accesslogProvider = new PosAccessLogProvider();
							accesslogProvider.updateAccessLog(accessLog);
							mValidPassword =true;
						}
					}else{
						PosFormUtil.showErrorMessageBox(mParent, "Invalid user id or password.");
						mValidPassword =false;
					}
				}else
					mValidPassword=true;
			}
			@Override
			public void onValueChanged(JTextComponent target, String oldValue) {
				// TODO Auto-generated method stub

			}
		});
		return mValidPassword;
	}

	public boolean onSave(){
		return true;
	}
	

	public boolean onValidate(){
		if(!validateShift()) return false;
		return validatePassword();
	}
	 

	public BeanCashierShift getCashierShiftDetails(){
		if(mCashierShiftInfo==null)
			mCashierShiftInfo=new BeanCashierShift();
		mCashierShiftInfo.setCashierInfo(mCashierInfo);
		mCashierShiftInfo.setOpeningDate(mTxtShiftStartDate.getText());
		mCashierShiftInfo.setOpeningTime(mTxtShiftStartTime.getText());
		mCashierShiftInfo.setShiftItem(mShopShiftInfo);
		mCashierShiftInfo.setOpeningFloat(Double.parseDouble(mTxtOpeningFloatAmount.getText()));
		mCashierShiftInfo.setIsOpenTill(mIsOpenTill);
		return mCashierShiftInfo;
	}

	protected void resetFields(){
		mShopShiftInfo=null; 
		mCashierInfo=null;
		
	};

	public void onGotFocus(){};
	public void onCancel(){};
	protected void onCashierCardNumberChanged(String cardNumber){}

	protected abstract void initControls();
	protected abstract boolean validateShift();

}
