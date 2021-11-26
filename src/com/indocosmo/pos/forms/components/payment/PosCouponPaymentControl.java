package com.indocosmo.pos.forms.components.payment;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanCoupon;
import com.indocosmo.pos.forms.components.buttons.PosToggleButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosToggleButtonListner;
import com.indocosmo.pos.forms.components.payment.listners.IPosCouponPaymentControlListner;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumericField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;

@SuppressWarnings("serial")
public final class PosCouponPaymentControl extends JPanel{

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
	
	private PosTouchableNumericField mTxtCouponAmt;	
	private PosTouchableDigitalField mTxtCouponCount;	
	
	private JTextField mTxtTotAmt;	
	
	public static final int COUPON_SELECT_FIELD_WIDTH=225;
	public static final int COUPON_SELECT_FIELD_HEIGHT=40;
	
	public static final int COUPON_VALUE_FIELD_WIDTH=180;
	public static final int COUPON_VALUE_FIELD_HEIGHT=40;
	
	public static final int COUPON_COUNT_FIELD_WIDTH=120;
	public static final int COUPON_COUNT_FIELD_HEIGHT=40;
	
	public static final int COUPON_TOTAL_AMT_FIELD_WIDTH=150;
	public static final int COUPON_TOTAL_AMT_FIELD_HEIGHT=40;
		
	private static final int PANEL_CONTENT_H_GAP=2;//PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=2;//PosOrderEntryForm.PANEL_CONTENT_V_GAP;

	
	public static final int LAYOUT_WIDTH=	COUPON_SELECT_FIELD_WIDTH+
											COUPON_VALUE_FIELD_WIDTH+
											COUPON_COUNT_FIELD_WIDTH+
											COUPON_TOTAL_AMT_FIELD_WIDTH+
											PANEL_CONTENT_V_GAP*6;
	public static final int LAYOUT_HEIGHT=COUPON_COUNT_FIELD_HEIGHT+PANEL_CONTENT_V_GAP*2;
	
	private IPosCouponPaymentControlListner mCouponControlListner;
	
	private BeanCoupon mCouponItem; 
	private int mIndex;
	private JDialog mParent;
	
	public PosCouponPaymentControl(BeanCoupon coupon, JDialog parent) {
		mCouponItem=coupon;
		mParent=parent;
		initControl();
	}
	
	public PosCouponPaymentControl(JDialog parent) {
		mParent=parent;
		initControl() ;
	}
	
	private void initControl() {
		setContentpanel();
		setCouponDetails();	
	}
	
	private void setCouponDetails(){
		setCouponSelectItemButton();
		setCouponAmountField();
		setCouponCountField();
		setTotalAmountField();
		reset();
	}
	
	private void setContentpanel(){
		setPreferredSize(new Dimension(LAYOUT_WIDTH, LAYOUT_HEIGHT));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP+1,PANEL_CONTENT_V_GAP));		
	}
		
	private void setCouponSelectItemButton(){
		
		mButtonCouponSelect = new PosToggleButton("None");	
		mButtonCouponSelect.setPreferredSize(new Dimension(COUPON_SELECT_FIELD_WIDTH, COUPON_SELECT_FIELD_HEIGHT));			
		mButtonCouponSelect.setOnToggleListner(new IPosToggleButtonListner() {
			@Override
			public void onToggle(PosToggleButton button, Boolean selected) {
				reset(selected);
				mTxtCouponCount.setEditable(selected);
				mTxtCouponAmt.setEditable((selected && mCouponItem.isOverridable()));
				if(selected){
					mTxtCouponCount.setText("1");
					setTotalValue();	
				}
			}
		});
		loadImages();
		mButtonCouponSelect.setImage(mImageSelectNormal);
		mButtonCouponSelect.setTouchedImage(mImageSelectTouched);
		mButtonCouponSelect.setSelectedImage(mImageSelectSelected);
//		mButtonCouponSelect.setFont(PosFormUtil.getTextFieldFont());
		mButtonCouponSelect.setEnabled(false);
		add(mButtonCouponSelect);
		
				
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
	
	private void setCouponAmountField(){		
		int left=mButtonCouponSelect.getX()+mButtonCouponSelect.getWidth()+PANEL_CONTENT_H_GAP*3;
		int top=mButtonCouponSelect.getY();		
		mTxtCouponAmt=new PosTouchableNumericField(mParent,COUPON_VALUE_FIELD_WIDTH);	
//		mTxtCouponAmt.setLocation(left, top);
		mTxtCouponAmt.setFont(PosFormUtil.getTextFieldFont());	
		mTxtCouponAmt.setListner(mFieldChangeListner);
		mTxtCouponAmt.setTitle("Enter the coupon's amount");
		mTxtCouponAmt.setEditable(false);
		mTxtCouponAmt.hideResetButton(true);
		add(mTxtCouponAmt);		
	}
	
	private void setCouponCountField(){		
		int left=mTxtCouponAmt.getX()+mTxtCouponAmt.getWidth()+PANEL_CONTENT_H_GAP*2;
		int top=mTxtCouponAmt.getY();
		mTxtCouponCount=new PosTouchableDigitalField(mParent,COUPON_COUNT_FIELD_WIDTH);
		mTxtCouponCount.setFiledDocument(PosNumberUtil.createNumericDocument(false));
		mTxtCouponCount.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtCouponCount.setFiledDocumentListner(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				setTotalValue();}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setTotalValue();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setTotalValue();}
		});
		mTxtCouponCount.setText("");
		mTxtCouponCount.setLocation(left, top);	
		mTxtCouponCount.setFont(PosFormUtil.getTextFieldFont());
		mTxtCouponCount.setTitle("Enter the coupon count");
		mTxtCouponCount.setListner(mFieldChangeListner);
		mTxtCouponCount.hideResetButton(true);
		mTxtCouponCount.setEditable(false);
		add(mTxtCouponCount);		

	}
	
	private  void setTotalAmountField()
	{				
		mTxtTotAmt=new JTextField();
		mTxtTotAmt.setEditable(false);
		mTxtTotAmt.setHorizontalAlignment(SwingConstants.RIGHT);		
		mTxtTotAmt.setPreferredSize(new Dimension(COUPON_TOTAL_AMT_FIELD_WIDTH, COUPON_TOTAL_AMT_FIELD_HEIGHT));	
		mTxtTotAmt.setFont(PosFormUtil.getLabelFont());	
		add(mTxtTotAmt);			
	}	
	
	private void setEnableControl(){
		mTxtCouponAmt.setEditable(true);
		mTxtCouponCount.setEditable(true);
//		mButtonCouponReset.setEnabled(true);
	}
	
	private void setDisableControl(){
		mTxtCouponAmt.setEditable(false);
		mTxtCouponCount.setEditable(false);
//		mButtonCouponReset.setEnabled(false);
	}

	private void setControls(BeanCoupon couponItem){
		if(mCouponItem==null) return;
		setDisableControl();
		mButtonCouponSelect.setEnabled(true);
		mCouponItem=couponItem;
		mButtonCouponSelect.setText(couponItem.getName());
		mTxtCouponAmt.setText(String.valueOf(mCouponItem.getValue()));
		mTxtCouponCount.setText("");
		mTxtTotAmt.setText("");
	}
	
	private void reset(boolean selected){
		mButtonCouponSelect.setSelected(selected);
		setControls(mCouponItem);
		setTotalValue();
	}
	
	public void actionPerformed(ActionEvent evt) {
		 JOptionPane.showMessageDialog(null, "Pressed");	 
	}
	
	private IPosTouchableFieldListner mFieldChangeListner=new PosTouchableFieldAdapter() {
		@Override
		public void onValueSelected(Object value) {
			setTotalValue();
		}
	};	
	
	private void setTotalValue(){
		mTxtTotAmt.setText(PosCurrencyUtil.format(getTotalAmount()));
		if(mCouponControlListner!=null)
			mCouponControlListner.onChange(getCouponItem(),mIndex);
	}
	
	/**
	 * @return the mCount
	 */
	public int getCount() {
		String value=mTxtCouponCount.getText();
		return (value.equals(""))?0:Integer.parseInt(value);
		
	}
	
	public double getTotalAmount(){
		return getValue()*getCount();
	}
	/**
	 * @return the mValue
	 */
	public double getValue() {
		String value=mTxtCouponAmt.getText();
		return (value.equals(""))?0:Double.parseDouble(value);
	}
	
	public BeanCoupon getCouponItem(){
		BeanCoupon coupon=null;
		if(mCouponItem!=null){
			coupon=mCouponItem.clone();
			coupon.setCount(getCount());
			coupon.setValue(getValue());
		}
		return coupon;
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
	
	public void setListner(IPosCouponPaymentControlListner listner){
		mCouponControlListner=listner;
	}
	/**
	 * @param posCouponObject
	 */
	public void setCouponItem(BeanCoupon posCouponObject) {
		setControls(posCouponObject);
	}

	/**
	 * 
	 */
	public void reset() {
		reset(false);
	}
	

}
