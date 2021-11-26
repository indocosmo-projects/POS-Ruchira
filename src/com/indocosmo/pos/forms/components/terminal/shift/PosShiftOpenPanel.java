/**
 * 
 */
package com.indocosmo.pos.forms.components.terminal.shift;

import javax.swing.JDialog;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.providers.shopdb.PosAttendProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShiftSummaryProvider;
import com.indocosmo.pos.forms.PosShiftOpeningForm;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public final class PosShiftOpenPanel extends PosShiftBasePanel {

	public static final int LAYOUT_HEIGHT=213;
	public static final int LAYOUT_WIDTH=515;
	private boolean isAlreadyOpened;
	private PosShiftOpeningForm mParent;
	private BeanCashierShift shiftDetails;
	public PosShiftOpenPanel(JDialog parent) {
		super(parent, ShiftActions.Shift_Open);
		mParent = (PosShiftOpeningForm) parent;
		
	}

	@Override
	protected void initControls() {
		if(PosEnvSettings.getInstance().getStation()!=null)
			shiftDetails = mCashierShiftProvider.getShiftDetails(PosEnvSettings.getInstance().getStation());
		setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
		setShortMessagePanel("Please swipe the card or enter card number.");
		setCashierCard();
		setCashierNameField();
		setStartingDataTime(false);
		setShift();
		setOpeningFloatAmount();
		resetFields();
		
	}
	
	public void setCardNo(String cardNo){
		
		mTxtCacheirCard.setText(cardNo);
	}
	
	@Override
	protected void resetFields(){
//		mTxtCacheirID.setText("");
		if(shiftDetails!=null){
			mShopShiftInfo = shiftDetails.getShiftItem();
			mIsOpenTill = false;
		}else{
			PosShiftSummaryProvider mShiftSummaryProvider = new PosShiftSummaryProvider();
			mDefaultFloat = PosCurrencyUtil.roundTo(mShiftSummaryProvider.getLatestCashRemainingForDefaultFloat());
			mIsOpenTill = true;
		}
		mTxtCacheirCard.setEditable(true);
		mTxtCashierName.setText("");
		if(PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isSetOpeningFloat())
			mTxtOpeningFloatAmount.setText(PosCurrencyUtil.format((shiftDetails!=null)? shiftDetails.getOpeningFloat():mDefaultFloat));
		else
			mTxtOpeningFloatAmount.setText(PosCurrencyUtil.format(0));
 
		mTxtOpeningFloatAmount.setEditable(false);
		mTxtShift.setText((shiftDetails!=null)?mShopShiftInfo.getName():"");
//		mTxtShift.setText("");
		mTxtShift.setEditable(false);
		mTxtCacheirCard.requestFocus();
		super.resetFields();
	}
	
	@Override
	protected void onCashierCardNumberChanged(String cardNumber){
		mTxtCacheirCard.setText(cardNumber);
		try {
			mCashierInfo= mCashierProvider.getUserByCard(cardNumber);
			if(mCashierInfo!=null && !cardNumber.equalsIgnoreCase("")){
				mTxtCashierName.setText(mCashierInfo.getName());
				loadShiftInfo(mCashierInfo.getCode());
				mParent.setOkEnabled(!isAlreadyOpened);
				if(isAlreadyOpened)
					PosFormUtil.showInformationMessageBox(null,"Shift is already opend");
				super.onCashierCardNumberChanged(cardNumber);
			}else{
				resetFields();
			}
		} catch (Exception e) {
			PosLog.write(this, "onCashierCardNumberChanged", e);
			PosFormUtil.showErrorMessageBox(null,"Failed get cashier information.");
		}
		
	}

	public void setCashierCard(String cardNumber){
		
		onCachierCardSelectedListener.onValueSelected(cardNumber);
	}
	
	private void loadShiftInfo(String code){
		mCashierShiftInfo=mCashierShiftProvider.getAllOpenCashierShifts().get(code);
		if(mCashierShiftInfo!=null){
			mTxtShiftStartDate.setText(mCashierShiftInfo.getOpeningDate());
			mTxtShiftStartTime.setText(mCashierShiftInfo.getOpeningTime());
			mTxtOpeningFloatAmount.setText(PosCurrencyUtil.format(mCashierShiftInfo.getOpeningFloat()));
			mShopShiftInfo=mCashierShiftInfo.getShiftItem();
			mTxtShift.setSelectedItem(mShopShiftInfo);
			isAlreadyOpened=true;
		}
		else{
			mTxtShiftStartDate.setText(PosEnvSettings.getPosEnvSettings().getPosDate());
			mTxtShiftStartTime.setText(PosDateUtil.getDateTime());
			if(PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isSetOpeningFloat())
				mTxtOpeningFloatAmount.setText(PosCurrencyUtil.format((shiftDetails!=null)? shiftDetails.getOpeningFloat():mDefaultFloat));
			else
				mTxtOpeningFloatAmount.setText(PosCurrencyUtil.format(0));
			
			mTxtOpeningFloatAmount.setEditable(shiftDetails==null);
			if(shiftDetails==null)
				mTxtShift.setTextReadOnly(true);
			isAlreadyOpened=false;
		}
	}
	
	@Override
	public boolean onValidate() {
		if(!isAlreadyOpened)
			return super.onValidate();
		return true;
	}
	
	private boolean validateAttendance(int employeeId){
		PosAttendProvider staffAttendProvider = new PosAttendProvider();
		 return staffAttendProvider.isAttendanceOpen(employeeId);	
	}
	
	@Override
	public boolean validateShift() {
		boolean valid= true;
		PosShiftSummaryProvider mShiftSummaryProvider = new PosShiftSummaryProvider();
		if(mTxtCacheirCard.getText().trim().length()<=0){
			PosFormUtil.showErrorMessageBox(mParent, "Please enter card number.");
			valid = false;
		}else if(mCashierInfo.isEmployee() && !validateAttendance(mCashierInfo.getEmployeeId())){
			System.out.println("mCashierInfo.getEmployeeId()===========>"+mCashierInfo.getEmployeeId());
			PosFormUtil.showInformationMessageBox(mParent, "Attendance is not marked. Please mark attendance first.");
			valid = false;
		}else if(mTxtShift.getText().trim().length()<=0){
			PosFormUtil.showInformationMessageBox(mParent, "No shift selected. Please select a shift.");
			valid = false;
		}else if(!mShiftSummaryProvider.isValidShift(mShopShiftInfo.getId())){
			PosFormUtil.showInformationMessageBox(mParent, "This shift has already been used today.");
			valid = false;
//		}else if(shiftDetails==null && !mCashierInfo.IsOpenTill()){
//			PosFormUtil.showInformationMessageBox(mParent, "You are not a valid employee to Open the Till.");
//			valid = false;
		}else if(
				 mTxtOpeningFloatAmount.getText().trim().length()<=0  
				||!PosNumberUtil.isDouble(mTxtOpeningFloatAmount.getText())
				){
			PosFormUtil.showErrorMessageBox(mParent, "The shift details are not valid. Please enter valid details.");
			valid = false;
		}
		return valid;
	}

	@Override
	public void onGotFocus() {
		mTxtCacheirCard.requestFocus();
	}


	@Override
	public boolean onSave() {
		if(isAlreadyOpened) 
			return true;
		if(shiftDetails!=null&&mShopShiftInfo==null){
			mShopShiftInfo = shiftDetails.getShiftItem();
			mTxtOpeningFloatAmount.setText(PosCurrencyUtil.format(0));
		}
		if(mCashierShiftProvider.openShift(getCashierShiftDetails()))
			return super.onSave();
		return false;
	}


	
}
