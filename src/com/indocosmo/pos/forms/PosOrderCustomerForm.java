/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.indocosmo.pos.data.beans.BeanCustomer;
import com.indocosmo.pos.data.beans.BeanOrderCustomer;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerProvider;
import com.indocosmo.pos.forms.components.PosOrderCustomerPanel;
import com.indocosmo.pos.forms.listners.IPosSelectCustomerFormListner;

/**
 * @author sandhya
 * 
 */
@SuppressWarnings("serial")
public final class PosOrderCustomerForm extends PosBaseForm {

	protected static final int MAIN_PANEL_CONTENT_H_GAP = 1;
	protected static final int MAIN_PANEL_CONTENT_V_GAP = 1;
	
	protected static final int PANEL_CONTENT_H_GAP = 2;
	protected static final int PANEL_CONTENT_V_GAP = 2;
	
 
	 
	private static final int LAYOUT_HEIGHT =  PosOrderCustomerPanel.CUSTOMER_PANEL_HEIGHT
			+ PANEL_CONTENT_V_GAP *5 ;
	
//	private static final int CUSTOMER_PANEL_WIDTH = CUSTOMER_PANEL_WIDTH   + PANEL_CONTENT_H_GAP * 2 + PANEL_BORDER_WIDTH *2;
					
	private static final int LAYOUT_WIDTH = PosOrderCustomerPanel.CUSTOMER_PANEL_WIDTH  
			+ PANEL_CONTENT_H_GAP *5 ;
	  
 	private JPanel mContentPanel;
 	private PosCustomerProvider mPosCustomerProvider;
 
	private BeanCustomer mSelectedCustomer;
	private BeanOrderCustomer mOrderCustomer;

	private PosOrderCustomerPanel orderCustomerPanel;
	private IPosSelectCustomerFormListner mPosSelectCustomerFormListner;
	private boolean mIsDirty=false;
	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosOrderCustomerForm(PosOrderEntryForm parent,BeanCustomer customer, BeanOrderCustomer orderCustomer) {

		super("Order Customer", LAYOUT_WIDTH, LAYOUT_HEIGHT  );
		
		mSelectedCustomer=customer;
		mOrderCustomer=orderCustomer;
	 
		mPosCustomerProvider=new PosCustomerProvider();
		orderCustomerPanel=new PosOrderCustomerPanel(PosOrderCustomerForm.this, customer,orderCustomer);
		mContentPanel.add( orderCustomerPanel);
		setResetButtonVisible(true);
	}
	 
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,MAIN_PANEL_CONTENT_H_GAP ,
				MAIN_PANEL_CONTENT_V_GAP ));
		mContentPanel = panel;
	}
	

	/* (non-Javadoc)l 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		
//		if(! validateInputs())
//			return false;
		
 
		final BeanOrderCustomer orderCustomer= orderCustomerPanel.getSelectedOrderCustomer();
		
		if(orderCustomer!=null){
			mSelectedCustomer=mPosCustomerProvider.getAllCustomerById(orderCustomer.getId());
			
			if(mPosSelectCustomerFormListner!=null){
				mPosSelectCustomerFormListner.onOkClicked(mSelectedCustomer, orderCustomer);
			}
			mIsDirty=true;
			return true;
		}else
			return false;
	}
	

	/**
	 * @param listner
	 */
	public void setListner(IPosSelectCustomerFormListner listner) {	
		mPosSelectCustomerFormListner=listner;
	}

 
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onCancelButtonClicked()
	 */
	@Override
	public boolean onCancelButtonClicked() {

		mIsDirty=false;
		return super.onCancelButtonClicked();
	}
	 
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onResetButtonClicked()
	 */
	@Override
	public void onResetButtonClicked() {
		super.onResetButtonClicked();
		
		 
		orderCustomerPanel.reset();
	}
 
	public boolean isDirty(){
		
		return mIsDirty;
	}
	 
		
 
	 
}
