/**
 * 
 */
package com.indocosmo.pos.forms.components.terminal.shift;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.CardTypes;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanEmployees;
import com.indocosmo.pos.data.beans.BeanStaffAttendance;
import com.indocosmo.pos.data.beans.BeanUser;
import com.indocosmo.pos.data.providers.shopdb.PosAttendProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCashierShiftProvider;
import com.indocosmo.pos.data.providers.shopdb.PosEmployeeProvider;
import com.indocosmo.pos.data.providers.shopdb.PosEmployeeProvider.EmployeeStatus;
import com.indocosmo.pos.data.providers.shopdb.PosUsersProvider;
import com.indocosmo.pos.forms.PosAttendanceForm;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.textfields.PosCardReaderField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.listners.IPosAttendanceFormListener;

/**
 * @author jojesh
 * @author Ramesh S.
 * @since 23th July 2012
 * 
 */
@SuppressWarnings("serial")
public class PosAttendancePanel extends JPanel implements
		IPosAttendanceFormListener {

	public static final int LAYOUT_HEIGHT = 170;
	public static final int LAYOUT_WIDTH = 501;
	private static final int PANEL_CONTENT_H_GAP = PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP = 3;
	private static final int MESSAGE_PANEL_HEIGHT = 30;
	private static final Color LABEL_BG_COLOR = new Color(78, 128, 188);
	private static final int TEXT_FIELD_WIDTH = 330;
	private static final int TEXT_FIELD_HEIGHT = PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
	private static final int LABEL_WIDTH = 163;
	private static final int LABEL_HEIGHT = PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
	
	private static final int H_GAP_BTWN_CMPNTS = 1;
	private static final int V_GAP_BTWN_CMPNTS = 2;
	
	private PosCardReaderField mTxtEmployeeCard;
	private JTextField mTxtEmployeeName;
	private JTextField mTxtShiftStartDate;
	private JTextField mTxtShiftStartTime;
	private PosAttendanceForm mParent;
	private PosEmployeeProvider mEmployeeProv;
	private PosAttendProvider mAttendanceProv;
	private BeanEmployees mEmployee=null; 
	private boolean mIsOrderEntry = false;
	

	public PosAttendancePanel(PosAttendanceForm parent,boolean isOrderEntry) {
		mIsOrderEntry = isOrderEntry;
		mParent = parent;
		mEmployeeProv = new PosEmployeeProvider();
		mAttendanceProv = new PosAttendProvider();
		setLayout(new FlowLayout(FlowLayout.LEFT,H_GAP_BTWN_CMPNTS,V_GAP_BTWN_CMPNTS));
		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		initControls();
	}

	private void initControls() {
		setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
		setShortMessagePanel("Please swipe the card or enter card number.");
		setEmployeeID();
		setEmployeeNameField();
		setStartingDateTime();
	}

	private void setLabelFont(JLabel label) {
//		Font newLabelFont = new Font(PosFormUtil.getLabelFont().getName(), PosFormUtil.getLabelFont().getStyle(), 16);
//		label.setFont(newLabelFont);
		label.setFont(PosFormUtil.getLabelFont());
	}

	private void setShortMessagePanel(String message) {
		JLabel labelMessage = new JLabel();
		labelMessage.setText(message);
		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);
		labelMessage.setPreferredSize(new Dimension(494,MESSAGE_PANEL_HEIGHT));
		labelMessage.setOpaque(true);
		labelMessage.setBackground(LABEL_BG_COLOR);
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(PosFormUtil.getSubHeadingFont());
		add(labelMessage);
	}
	
	//Sets both Employee Id and Employee Code
	private void setEmployeeID() {
		JLabel label = new JLabel();
		label.setText(PosFormUtil.getMnemonicString("Card Number :", 'N'));
		
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(label);
		add(label);

		mTxtEmployeeCard = new PosCardReaderField(mParent, TEXT_FIELD_WIDTH); /*Card number*/
		mTxtEmployeeCard.setMnemonic('N');
		mTxtEmployeeCard.setTitle("Employee Card Number ?");
		mTxtEmployeeCard.setPosCardType(CardTypes.User);
		mTxtEmployeeCard.setListner(mCardListner);
		add(mTxtEmployeeCard);
		mTxtEmployeeCard.setHorizontalTextAlignment(JTextField.LEFT);
	}
	
	public void setCardNumber(String cardNumber){
		mTxtEmployeeCard.setCardNumber(cardNumber);
	}
	
	private IPosTouchableFieldListner mCardListner=new IPosTouchableFieldListner() {
		@Override
		public void onValueSelected(Object value) {
			try {
				String employeeCardNumber=String.valueOf(value);
				 mEmployee = mEmployeeProv.getEmployeeByCard(employeeCardNumber);
				if (mEmployee == null) {
					PosFormUtil.showErrorMessageBox(mParent,"Invalid employee card number.");
					mTxtEmployeeCard.setText("");
					mParent.setInButtonEnabled(false);
					mParent.setOutButtonEnabled(false);
					return;
				}
				if (mEmployee.getStatus().equals(EmployeeStatus.Resigned)) {
					PosFormUtil.showErrorMessageBox(mParent,"Not an active employee.");
					mTxtEmployeeCard.setText("");
					mParent.setInButtonEnabled(false);
					mParent.setOutButtonEnabled(false);
					return;
				}

				mTxtEmployeeName.setText((mEmployee.getFirstName()==null?"":mEmployee.getFirstName().trim())
															+" "+(mEmployee.getMiddleName()==null?"":mEmployee.getMiddleName().trim())
															+" "+(mEmployee.getLastName()==null?"":mEmployee.getLastName().trim()));
				enableDisableButtons(mEmployee.getId());
				
			} catch (Exception err) {
				mEmployee = null;
				PosLog.write(this, "onValueSelected", err);
			}
		}

		@Override
		public void onReset() {
			mEmployee= null;
			mTxtEmployeeName.setText("");
			mTxtShiftStartDate.setText("");
			mTxtShiftStartTime.setText("");
		}
	};
	public void setDefaultFocus(){
		
		mTxtEmployeeCard.requestFocus();
	}

	private void enableDisableButtons(int empID) throws Exception {
		BeanStaffAttendance mAttendance = mAttendanceProv
				.getRecentAttendanceByEmpID(empID);

		if ((mAttendance.getShift_start_date()==null || mAttendance.getShift_start_date().trim().length()<=0) || (mAttendance.getShift_end_date() != null
				&& mAttendance.getShift_end_date().trim().length() > 0)) {
			mParent.setInButtonEnabled(true);
			mParent.setOutButtonEnabled(false);
			mTxtShiftStartDate.setText("");
			mTxtShiftStartTime.setText("");

		} else {
//			Date currSystemDate = Calendar.getInstance().getTime();
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
//					PosDateUtil.DATE_FORMAT);
			String formattedCurrDate = PosDateUtil.getDate();
			mTxtShiftStartDate.setText(mAttendance.getShift_start_date());
			mTxtShiftStartTime.setText(mAttendance.getShift_start_time());

			// Dates between shifts has changed
			if ( (mAttendance.getShift_start_date()!=null && mAttendance.getShift_start_date().compareTo(formattedCurrDate) < 0)) {
				mParent.setInButtonEnabled(true);
				mParent.setOutButtonEnabled(true);

			} else {
				mParent.setInButtonEnabled(false);
				mParent.setOutButtonEnabled(true);
			}
		}
	}

	@Override
	public boolean inButtonClicked() {
		try {
			mAttendanceProv.updateStartTime(mEmployee.getId());

		} catch (Exception err) {
			PosFormUtil.showErrorMessageBox(mParent,"Unexpected System error! Contact Administrator.");
			PosLog.write(this, "inButtonClicked", err);
			return false;
		}
		return true;
	}

	
	@Override
	
	public boolean outButtonClicked() {
		PosCashierShiftProvider mCashierShiftProvider = new PosCashierShiftProvider();
		PosUsersProvider mPosUsersProvider = new PosUsersProvider();
		BeanCashierShift cashierShiftOld = PosEnvSettings.getInstance()
				.getCashierShiftInfo();
		BeanUser user = null;
		BeanCashierShift mCashierShift = null;
		try {
			user = mPosUsersProvider.getUserByCard(mEmployee.getCardNumber());
			if(user!=null){
				mCashierShift = mCashierShiftProvider.getAllOpenCashierShifts().get(user.getCode());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(mCashierShift!=null){
			if(cashierShiftOld.getCashierInfo().getCode().equals(mCashierShift.getCashierInfo().getCode())&&mIsOrderEntry){
				PosFormUtil.showInformationMessageBox(mParent,"Plese exit form Order Entry first.");
				return false;
			}else if(mCashierShift.IsOpenTill()){
				PosFormUtil.showInformationMessageBox(mParent,"Shift is open. Please close shift.");
				return false;
			}else{
				mCashierShiftProvider.closeJoinedShift(mCashierShift);
//				if(cashierShiftOld.equals(mCashierShift)){
//					PosEnvSettings.getInstance().setCashierShiftInfo(null);
//					PosCashierShiftObject cashierShiftNew=mCashierShiftProvider.getShiftDetails();
//					if(cashierShiftNew!=null){
//						PosEnvSettings.getInstance().setCashierShiftInfo(cashierShiftNew);
//					}
//				}
			}
		}
		try {
			BeanStaffAttendance mAttendance = mAttendanceProv
					.getRecentAttendanceByEmpID(mEmployee.getId());
			mAttendanceProv.updateEndTime(mAttendance);

		} catch (Exception err) {
			PosFormUtil.showErrorMessageBox(mParent,"Unexpected System error! Contact Administrator.");
			return false;
		}
		return true;
	}

	@Override
	public void cancelButtonClicked() {
		// TODO Auto-generated method stub

	}

	private void setEmployeeNameField() {
		JLabel label = new JLabel("Name :");
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setOpaque(true);
		label.setBackground(Color.lightGray);
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(label);
		add(label);

		mTxtEmployeeName = new JTextField();
		mTxtEmployeeName.setFont(PosFormUtil.getTextFieldFont());
		mTxtEmployeeName.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtEmployeeName.setEditable(false);
		add(mTxtEmployeeName);
	}

	private void setStartingDateTime() {
		setStartingDateTime(true);
	}

	private void setStartingDateTime(boolean isVisible) {
		JLabel label = new JLabel("In Time :");
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setOpaque(true);
		label.setBackground(Color.lightGray);
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(label);

		mTxtShiftStartDate = new JTextField();
		mTxtShiftStartDate.setFont(PosFormUtil.getTextFieldFont());
		mTxtShiftStartDate.setPreferredSize(new Dimension(
				TEXT_FIELD_WIDTH / 2 - 1, TEXT_FIELD_HEIGHT));
		mTxtShiftStartDate.setEditable(false);

		mTxtShiftStartTime = new JTextField();
		mTxtShiftStartTime.setFont(PosFormUtil.getTextFieldFont());
		mTxtShiftStartTime.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH / 2,
				TEXT_FIELD_HEIGHT));
		mTxtShiftStartTime.setEditable(false);

		if (isVisible) {
			add(label);
			add(mTxtShiftStartDate);
			add(mTxtShiftStartTime);
		}
	}
}