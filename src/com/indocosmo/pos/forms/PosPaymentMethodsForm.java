/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosUtil;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.listners.IPosPaymentMetodsFormListner;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public final class PosPaymentMethodsForm extends JDialog {
	

	
	private static final int FORM_WIDTH=520;
	private static final int FORM_HEIGHT=140;
	
	private static final int IMAGE__BUTTON_WIDTH=90;
	private static final int IMAGE_BUTTON_HEIGHT=87;
	
	private static final int LABEL_TITLE_HEIGHT=20;

	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	
	private static final String PAYMENT_IMAGE_ITEM_BUTTON="dlg_buton.png";
	private static final String PAYMENT_IMAGE_ITEM_BUTTON_TOUCH="dlg_buton_touch.png";
	
//	private static final String IMAGE_BUTTON_CANCEL="cancel_button.png";
//	private static final String IMAGE_BUTTON_CANCEL_TOUCH="cancel_button_touch.png";	
	
	private static final String IMAGE_BUTTON_CANCEL="cancel_button.png";
	private static final String IMAGE_BUTTON_CANCEL_TOUCH="cancel_button_touch.png";	

	private JPanel mContentPane;	
	private IPosPaymentMetodsFormListner mPosPaymentFormListner;
	
	public PosPaymentMethodsForm(){
		initControls();
	}
	
	private void initControls(){
		Dimension dim =PosUtil.getScreenSize(PosEnvSettings.getInstance().getPrimaryScreen());
		int left = (dim.width-FORM_WIDTH)/2;
		int top = (dim.height-FORM_HEIGHT)/2;
		setUndecorated(true);
		setAlwaysOnTop(true);		
		setBounds(left,top,FORM_WIDTH, FORM_HEIGHT);
		mContentPane = new JPanel();
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mContentPane.setLayout(null);		
		setContentPane(mContentPane);
		setTitle();
		JPanel innerPanel=new JPanel();		
		innerPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		innerPanel.setLayout(createLayout());			
		innerPanel.setBounds(mContentPane.getX()+PANEL_CONTENT_H_GAP,LABEL_TITLE_HEIGHT+PANEL_CONTENT_H_GAP ,FORM_WIDTH-PANEL_CONTENT_V_GAP*2, FORM_HEIGHT-PANEL_CONTENT_H_GAP*4);			
		createPaymentItems(innerPanel);			
		
	}	
	private void setTitle(){
		JLabel labelTitle=new JLabel();		
		labelTitle.setText("Payment Methods");
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);		
		labelTitle.setBounds(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP, FORM_WIDTH, LABEL_TITLE_HEIGHT);		
		labelTitle.setFont(new Font("Dialog", Font.BOLD, 18));		
		add(labelTitle);		
		}
	
	private void createPaymentItems(JPanel innerPanel){	
		createCashButton(innerPanel);
		createCardButton(innerPanel);
		createCouponButton(innerPanel);
		createCompanyButton(innerPanel);
		createCancelButton(innerPanel);
	}
	
	private void createCashButton(JPanel innerPanel)
	{	
		PosButton buttonCash=new PosButton();		
		buttonCash.setText("Cash");
		buttonCash.setImage(PosResUtil.getImageIconFromResource(PAYMENT_IMAGE_ITEM_BUTTON));
		buttonCash.setTouchedImage(PosResUtil.getImageIconFromResource(PAYMENT_IMAGE_ITEM_BUTTON_TOUCH));	
		buttonCash.setHorizontalAlignment(SwingConstants.CENTER);		
		buttonCash.setBounds(0, 0, IMAGE__BUTTON_WIDTH,IMAGE_BUTTON_HEIGHT);
		buttonCash.setOnClickListner(payMentModeListener);
		innerPanel.add(buttonCash);
		add(innerPanel);
	}
	
	private void createCardButton(JPanel innerPanel)
	{	
		PosButton buttonCard=new PosButton();		
		buttonCard.setText("Card");
		buttonCard.setImage(PosResUtil.getImageIconFromResource(PAYMENT_IMAGE_ITEM_BUTTON));
		buttonCard.setTouchedImage(PosResUtil.getImageIconFromResource(PAYMENT_IMAGE_ITEM_BUTTON_TOUCH));	
		buttonCard.setHorizontalAlignment(SwingConstants.CENTER);		
		buttonCard.setBounds(0, 0, IMAGE__BUTTON_WIDTH,IMAGE_BUTTON_HEIGHT);
		buttonCard.setOnClickListner(payMentModeListener);
		innerPanel.add(buttonCard);
		add(innerPanel);
	}
	
	private void createCouponButton(JPanel innerPanel)
	{	
		PosButton buttonCoupon=new PosButton();		
		buttonCoupon.setText("Coupon");
		buttonCoupon.setImage(PosResUtil.getImageIconFromResource(PAYMENT_IMAGE_ITEM_BUTTON));
		buttonCoupon.setTouchedImage(PosResUtil.getImageIconFromResource(PAYMENT_IMAGE_ITEM_BUTTON_TOUCH));	
		buttonCoupon.setHorizontalAlignment(SwingConstants.CENTER);		
		buttonCoupon.setBounds(0, 0, IMAGE__BUTTON_WIDTH,IMAGE_BUTTON_HEIGHT);
		buttonCoupon.setOnClickListner(payMentModeListener);
		innerPanel.add(buttonCoupon);
		add(innerPanel);
	}
	
	private void createCompanyButton(JPanel innerPanel)
	{	
		PosButton buttonCompany=new PosButton();		
		buttonCompany.setText("Company");
		buttonCompany.setImage(PosResUtil.getImageIconFromResource(PAYMENT_IMAGE_ITEM_BUTTON));
		buttonCompany.setTouchedImage(PosResUtil.getImageIconFromResource(PAYMENT_IMAGE_ITEM_BUTTON_TOUCH));	
		buttonCompany.setHorizontalAlignment(SwingConstants.CENTER);		
		buttonCompany.setBounds(0, 0, IMAGE__BUTTON_WIDTH,IMAGE_BUTTON_HEIGHT);
		buttonCompany.setOnClickListner(payMentModeListener);
		innerPanel.add(buttonCompany);
		add(innerPanel);
	}
	
	private void createCancelButton(JPanel innerPanel)
	{	
		PosButton buttonCancel=new PosButton();		
		buttonCancel.setText("Cancel");
		buttonCancel.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		buttonCancel.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));	
		buttonCancel.setHorizontalAlignment(SwingConstants.CENTER);		
		buttonCancel.setBounds(0, 0, IMAGE__BUTTON_WIDTH,IMAGE_BUTTON_HEIGHT);
		buttonCancel.setOnClickListner(imgCancelButtonListner);
		innerPanel.add(buttonCancel);
		add(innerPanel);
	}

	
	private  IPosButtonListner payMentModeListener= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			PaymentMode paymentMode=null;
			switch(button.getText()){
				case "Cash":
					paymentMode=PaymentMode.Cash;							
					break;
				case "Card":
					paymentMode=PaymentMode.Card;
					break;
				case "Coupon":
					paymentMode=PaymentMode.Coupon;
					break;
				case "Company":
					paymentMode=PaymentMode.Company;
					break;
			}
			if(mPosPaymentFormListner!=null && paymentMode!=null){
				setVisible(false);
				mPosPaymentFormListner.onSelected(paymentMode);
				dispose();
			}
		}
	};
	
	
	private  IPosButtonListner imgCancelButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {			
			setVisible(false);
			dispose();			
		}
	};
	
	private FlowLayout createLayout(){
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setVgap(PANEL_CONTENT_V_GAP);
		flowLayout.setHgap(PANEL_CONTENT_H_GAP);
		flowLayout.setAlignment(FlowLayout.LEFT);
		return flowLayout;
	}
		
	public void setPaymentSelectedListner(
		IPosPaymentMetodsFormListner posPaymentFormListner) {
		this.mPosPaymentFormListner = posPaymentFormListner;
	}
	
	
}
