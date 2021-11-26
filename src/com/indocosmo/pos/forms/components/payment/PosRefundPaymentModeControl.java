package com.indocosmo.pos.forms.components.payment;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanPaymentModes;
import com.indocosmo.pos.data.providers.shopdb.PosCouponItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPaymentModesProvider;
import com.indocosmo.pos.forms.components.buttons.PosToggleButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosToggleButtonListner;
import com.indocosmo.pos.forms.components.payment.listners.IPosRefundPaymentModeControlListner;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumberField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;

@SuppressWarnings("serial")
public final class PosRefundPaymentModeControl extends JPanel{

	private static final String SELECT_BUTTON_NORMAL="payment_coupon_ctrl_select_button.png";
	private static final String SELECT_BUTTON_SELECTED="payment_coupon_ctrl_selected_button.png";
	private static final String SELECT_BUTTON_TOUCHED="payment_coupon_ctrl_select_button_touch.png";
	
	private static final String RESET_BUTTON_NORMAL="ctrl_reset.png";
	private static final String RESET_BUTTON_TOUCHED="ctrl_reset_touch.png";
	
	private static ImageIcon mImageSelectNormal;
	private static ImageIcon mImageSelectTouched;
	private static ImageIcon mImageSelectSelected;
	private static ImageIcon mImageResetNormal;
	private static ImageIcon mImageResetTouched;
	
	private PosToggleButton mButtonCouponSelect;
	
	private PosTouchableNumberField mTxtCouponAmt;	
	private PosTouchableDigitalField mTxtCouponCount;	
	
	private JTextField mTxtTotAmt;	
	
	
	public static final int FIELD_WIDTH=120;
	public static final int FIELD_HEIGHT=40;
	
		
	private static final int PANEL_CONTENT_H_GAP=2;//PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=2;//PosOrderEntryForm.PANEL_CONTENT_V_GAP;

	
	public static final int LAYOUT_WIDTH=	FIELD_WIDTH*5+
											PANEL_CONTENT_V_GAP*6;
	public static final int LAYOUT_HEIGHT=FIELD_HEIGHT+PANEL_CONTENT_V_GAP*2;
	
	private IPosRefundPaymentModeControlListner mRefundPaymentModeControlListner;
	
	private BeanOrderPayment mOrderPayment;
	private int mIndex;
	private JDialog mParent;
	private JLabel mlabelMode;
	private JLabel mlabelAmount;
	private JLabel mlabelRefundable;
	private PosTouchableNumberField mTxtRefundAmount;
	private PosToggleButton mButtonRefundSelf;
	private PosToggleButton mButtonRefundAlternate;
	private double mBalance;
	private BeanOrderPayment mRefundPayment;
	
	public PosRefundPaymentModeControl(BeanOrderPayment payment, JDialog parent, double Balance) {
		mOrderPayment=payment;
		mParent=parent;
		mBalance = Balance;
		initControl();
	}
	
	public PosRefundPaymentModeControl(JDialog parent) {
		mParent=parent;
		initControl() ;
	}
	
	private void initControl() {
		setContentpanel();
		setOrderDetails();	
	}
	
	private void setOrderDetails(){
		
		setModeLabel();
		setAmountLabel();
		setRefundableLabel();
		setRefundMode();
		setRefundAmound();
		setControls();
	}
	
	
	/**
	 * 
	 */
	private void setModeLabel() {
		
		mlabelMode=new JLabel();
		mlabelMode.setText("Mode");
		mlabelMode.setHorizontalAlignment(SwingConstants.CENTER);		
		mlabelMode.setPreferredSize(new Dimension(FIELD_WIDTH-20, FIELD_HEIGHT));		
		mlabelMode.setFont(PosFormUtil.getLabelFont());
		mlabelMode.setFocusable(true);
		mlabelMode.setOpaque(false);
		add(mlabelMode);	
	}
	
	/**
	 * 
	 */
	private void setAmountLabel() {
		int left=mlabelMode.getX()+mlabelMode.getWidth()+PANEL_CONTENT_H_GAP;
		int top=mlabelMode.getY();		
		mlabelAmount=new JLabel();
		mlabelAmount.setText("Amount");
		mlabelAmount.setHorizontalAlignment(SwingConstants.CENTER);		
		mlabelAmount.setPreferredSize(new Dimension(FIELD_WIDTH-20, FIELD_HEIGHT));
		mlabelAmount.setLocation(left, top);
		mlabelAmount.setFont(PosFormUtil.getLabelFont());
		mlabelAmount.setFocusable(true);
		mlabelAmount.setOpaque(false);
		add(mlabelAmount);	
	}
	
	/**
	 * 
	 */
	private void setRefundableLabel() {
		int left=mlabelAmount.getX()+mlabelAmount.getWidth()+PANEL_CONTENT_H_GAP;
		int top=mlabelAmount.getY();		
		mlabelRefundable=new JLabel();
		mlabelRefundable.setText("?");
		mlabelRefundable.setHorizontalAlignment(SwingConstants.CENTER);		
		mlabelRefundable.setPreferredSize(new Dimension(FIELD_WIDTH-20, FIELD_HEIGHT));
		mlabelRefundable.setLocation(left, top);
		mlabelRefundable.setFont(PosFormUtil.getLabelFont());
		mlabelRefundable.setFocusable(true);
		mlabelRefundable.setOpaque(false);
		add(mlabelRefundable);	
	}

	/**
	 * 
	 */
	private void setRefundMode() {

		mButtonRefundSelf = new PosToggleButton("None");	
		mButtonRefundSelf.setPreferredSize(new Dimension(FIELD_WIDTH/2+20, FIELD_HEIGHT));
		mButtonRefundSelf.setOnToggleListner(new IPosToggleButtonListner() {
			@Override
			public void onToggle(PosToggleButton button, Boolean selected) {
				if(selected){
					mButtonRefundAlternate.setSelected(!selected);
//					if(mOrderPayment.getPaymentMode().equals(PaymentMode.Coupon)){
//						mTxtRefundAmount.setText(PosNumberUtil.formatNumber(mOrderPayment.getPaidAmount()));
//					}
				}
				if(!selected){
					mTxtRefundAmount.setText("");
				}
				mTxtRefundAmount.setEditable(selected);
				if(mRefundPaymentModeControlListner!=null)
					mRefundPaymentModeControlListner.onChange(getRefundItem(),mIndex);
			}
		});
		loadImages();
		mButtonRefundSelf.setImage(mImageSelectNormal);
		mButtonRefundSelf.setTouchedImage(mImageSelectTouched);
		mButtonRefundSelf.setSelectedImage(mImageSelectSelected);
		mButtonRefundSelf.setEnabled(false);
		add(mButtonRefundSelf);
		
		mButtonRefundAlternate = new PosToggleButton("Non");	
		mButtonRefundAlternate.setPreferredSize(new Dimension(FIELD_WIDTH/2+20, FIELD_HEIGHT));
		mButtonRefundAlternate.setOnToggleListner(new IPosToggleButtonListner() {
			@Override
			public void onToggle(PosToggleButton button, Boolean selected) {
				if(selected){
					mButtonRefundSelf.setSelected(!selected);
				}
				if(!selected){
					mTxtRefundAmount.setText("");
				}
				mTxtRefundAmount.setEditable(selected);
				if(mRefundPaymentModeControlListner!=null)
					mRefundPaymentModeControlListner.onChange(getRefundItem(),mIndex);
			}
		});
		loadImages();
		mButtonRefundAlternate.setImage(mImageSelectNormal);
		mButtonRefundAlternate.setTouchedImage(mImageSelectTouched);
		mButtonRefundAlternate.setSelectedImage(mImageSelectSelected);
		mButtonRefundAlternate.setEnabled(false);
		add(mButtonRefundAlternate);
		
	}

	/**
	 * 
	 */
	private void setRefundAmound() {
		
		mTxtRefundAmount=new PosTouchableNumberField(mParent,FIELD_WIDTH+20);	
		mTxtRefundAmount.setFont(PosFormUtil.getTextFieldFont());	
		mTxtRefundAmount.setListner(mFieldChangeListner);
		mTxtRefundAmount.setTitle("Enter the amount");
		mTxtRefundAmount.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtRefundAmount.getTexFieldComponent().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {}
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(mRefundPaymentModeControlListner!=null)
					mRefundPaymentModeControlListner.onChange(getRefundItem(),mIndex);
//				doRefundAmountFieldValidation(mTxtRefundAmount.getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
		mTxtRefundAmount.setEditable(false);
		mTxtRefundAmount.hideResetButton(true);
		add(mTxtRefundAmount);		
		
	}
	
	private IPosTouchableFieldListner mFieldChangeListner=new PosTouchableFieldAdapter() {
		@Override
		public void onValueSelected(Object value) {
			doRefundAmountFieldValidation(String.valueOf(value));
			
		}
	};	

	

	/**
	 * @param value
	 */
	protected void doRefundAmountFieldValidation(String value) {
		if(Double.parseDouble(value)>Double.parseDouble(mlabelAmount.getText())){
			PosFormUtil.showInformationMessageBox(mParent, "You can not enter an amount greater than paid amount");
			mTxtRefundAmount.setText("");
		}
		if((mButtonRefundSelf.isSelected()&&mOrderPayment.getPaymentMode().equals(PaymentMode.Coupon))&&Double.parseDouble(value)%mOrderPayment.getVoucherValue()!=0){
			PosFormUtil.showInformationMessageBox(mParent, "Please Enter Multiple of Voucher value, "+mOrderPayment.getVoucherValue());
			mTxtRefundAmount.setText("");
		}else if(mRefundPaymentModeControlListner!=null)
			mRefundPaymentModeControlListner.onChange(getRefundItem(),mIndex);
		
	}
	
	private void setControls(){
		
		PosPaymentModesProvider mPosPaymentModesProvider = PosPaymentModesProvider.getInstance();
//		PosBankCardTypesProvider mPosBankCardTypesProvider = new PosBankCardTypesProvider();
		BeanPaymentModes mPaymentModesObject = mPosPaymentModesProvider.getPaymentModes();
		PosCouponItemProvider mCouponItemProvider = new PosCouponItemProvider();
		if(mOrderPayment!=null){
			resetFields();
			mButtonRefundSelf.setText("None");
			mlabelMode.setText(String.valueOf(mOrderPayment.getPaymentMode()));
			mlabelAmount.setText(PosCurrencyUtil.format(mOrderPayment.getPaidAmount()-mBalance));
			switch (mOrderPayment.getPaymentMode()) {
			case Cash:
				if (mOrderPayment.getPaidAmount() > 0){
					if(mPaymentModesObject.isCanCashRefundable()){
						mlabelRefundable.setText("Yes");
						mButtonRefundSelf.setEnabled(true);
						mButtonRefundAlternate.setEnabled(false);
						mButtonRefundSelf.setText(String.valueOf(mOrderPayment.getPaymentMode()));
					}else{
						mlabelRefundable.setText("No");
					}
				}
				break;
			case Card:
				if (mOrderPayment.getPaidAmount() > 0){
					mlabelRefundable.setText("Yes");
					mButtonRefundSelf.setEnabled(false);
					if(mOrderPayment.getAccount()!=null&&!mOrderPayment.getAccount().trim().equalsIgnoreCase("CRD")){
						mButtonRefundAlternate.setEnabled(true);
						mButtonRefundAlternate.setText("Cash");
					}
					mButtonRefundSelf.setText(String.valueOf(mOrderPayment.getPaymentMode()));
				}
				break;
			case Company:
				if (mOrderPayment.getPaidAmount() > 0){
					if(mPaymentModesObject.isCanCompanyRefundable()){
						mlabelRefundable.setText("Yes");
						mButtonRefundSelf.setEnabled(true);
						mButtonRefundAlternate.setEnabled(false);
						mButtonRefundSelf.setText(String.valueOf(mOrderPayment.getPaymentMode()));
					}else{
						mlabelRefundable.setText("No");
					}
				}
				break;
			case Coupon:
				if (mOrderPayment.getVoucherCount()>0){
					mlabelMode.setText(mCouponItemProvider.getCouponById(mOrderPayment.getVoucherId()).getName());
					if(mPaymentModesObject.isCanVoucherRefundable()){
						mlabelRefundable.setText("Yes");
						if(mBalance>0&&mCouponItemProvider.getCouponById(mOrderPayment.getVoucherId()).isChangePayable()){
							mButtonRefundSelf.setEnabled(false);
							mButtonRefundAlternate.setEnabled(true);
							mButtonRefundAlternate.setText("Cash");
						}else{
							mButtonRefundSelf.setEnabled(true);
						}
						if(mPaymentModesObject.isAlternativeRefundMethodForVoucher()){
							mButtonRefundAlternate.setEnabled(true);
							mButtonRefundAlternate.setText("Cash");
						}
						mButtonRefundSelf.setText(mCouponItemProvider.getCouponById(mOrderPayment.getVoucherId()).getName());
					}else{
						mlabelRefundable.setText("No");
					}
					
				}
				break;
			default:
				break;
			}
		}
	}
	



	private void setContentpanel(){
		setPreferredSize(new Dimension(LAYOUT_WIDTH, LAYOUT_HEIGHT));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));		
	}
		
	private void loadImages(){
		if(mImageSelectNormal==null)
			mImageSelectNormal=PosResUtil.getImageIconFromResource(SELECT_BUTTON_NORMAL);
		if(mImageSelectTouched==null)
			mImageSelectTouched=PosResUtil.getImageIconFromResource(SELECT_BUTTON_TOUCHED);
		if(mImageSelectSelected==null)
			mImageSelectSelected = PosResUtil.getImageIconFromResource(SELECT_BUTTON_SELECTED);
		if(mImageResetNormal==null)
			mImageResetNormal=PosResUtil.getImageIconFromResource(RESET_BUTTON_NORMAL);
		if(mImageResetTouched==null)
			mImageResetTouched=PosResUtil.getImageIconFromResource(RESET_BUTTON_TOUCHED);
	}
	
	
	private void resetFields(){
		mlabelMode.setText("Mode");
		mlabelAmount.setText("Amount");
		mlabelRefundable.setText("?");
		mTxtRefundAmount.setEditable(false);
		mTxtRefundAmount.setText("");
//		mButtonRefundAlternate.setSelected(false);
//		mButtonRefundSelf.setSelected(false);
		mButtonRefundAlternate.setEnabled(false);
		mButtonRefundSelf.setEnabled(false);
	}
	
	public double getPaidAmount() {
		String value=mTxtRefundAmount.getText();
		return (value.equals(""))?0:Double.parseDouble(value);
	}
	
	public BeanOrderPayment setRefundItem(){
		PosCouponItemProvider mCouponItemProvider;
		mRefundPayment=null;
		if(mOrderPayment!=null){
			mRefundPayment=mOrderPayment.clone();
			mRefundPayment.setPaymentMode(getPaymentMode());
			mRefundPayment.setPaidAmount(getPaidAmount());
			mRefundPayment.setRepayment(true);
			mRefundPayment.setCashierID(PosEnvSettings.getInstance().getCashierShiftInfo()
					.getCashierInfo().getId());
			mRefundPayment.setPaymentTime(PosDateUtil.getDateTime());
			mRefundPayment.setPaymentDate(PosEnvSettings.getPosEnvSettings().getPosDate());
			if(mRefundPayment.getPaymentMode().equals(PaymentMode.Coupon)){
				mCouponItemProvider = new PosCouponItemProvider();
				mRefundPayment.setVoucherName(mCouponItemProvider.getCouponById(mRefundPayment.getVoucherId()).getName());
			}
		}
		return mRefundPayment;
	}
	public BeanOrderPayment getRefundItem(){
		setRefundItem();
		return mRefundPayment;
	}
	
	
	/**
	 * @return
	 */
	private PaymentMode getPaymentMode() {
		return (mButtonRefundAlternate.isSelected()?PaymentMode.Cash:mOrderPayment.getPaymentMode());
	}

	public void actionPerformed(ActionEvent evt) {
		 JOptionPane.showMessageDialog(null, "Pressed");	 
	}
	
	
	/**
	 * @return the mIndex
	 */
	public final int getIndex() {
		return mIndex;
	}
	
	/**
	 * @param mIndex the mIndex to set
	 */
	public final void setIndex(int index) {
		this.mIndex = index;
	}
	
	public void setListner(IPosRefundPaymentModeControlListner listner){
		mRefundPaymentModeControlListner=listner;
	}
	
	public boolean validateRefundAmount(){
		boolean valid = true;
		if(mlabelAmount.getText().trim().length()==0||mTxtRefundAmount.getText().trim().length()==0){
			return valid;
		}
		if(Double.parseDouble(mTxtRefundAmount.getText())>Double.parseDouble(mlabelAmount.getText())){
			PosFormUtil.showInformationMessageBox(mParent, "You can not enter an amount greater than paid amount");
			mTxtRefundAmount.setText("");
//			reset();
			valid = false;
		}
		if((mButtonRefundSelf.isSelected()&&mOrderPayment.getPaymentMode().equals(PaymentMode.Coupon))&&Double.parseDouble(mTxtRefundAmount.getText())%mOrderPayment.getVoucherValue()!=0){
			PosFormUtil.showInformationMessageBox(mParent, "Please Enter Multiple of Voucher value, "+mOrderPayment.getVoucherValue());
			mTxtRefundAmount.setText("");
//			reset();
			valid = false;
		}
		
		return valid;
		
	}
	/**
	 * @param posCouponObject
	 */
	public void reset() {
		resetFields();
	}

}
