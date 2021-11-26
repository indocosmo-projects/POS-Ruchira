/**
 * 
 */
package com.indocosmo.pos.forms.split;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderSplit;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.forms.PosBaseForm;
import com.indocosmo.pos.forms.PosNumKeypadForm;
import com.indocosmo.pos.forms.PosPaymentForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.listners.IPosNumKeyPadFormListner;
import com.indocosmo.pos.forms.listners.IPosPaymentFormListner;
import com.indocosmo.pos.forms.split.components.SplitSelectionSummaryPanel;
import com.indocosmo.pos.forms.split.components.SplitSelectionTablePanel;
import com.indocosmo.pos.forms.split.components.SplitSelectionTablePanel.ISplitSelectionListener;
import com.indocosmo.pos.forms.split.listners.IPosSplitSelectFormListener;

/**
 * @author jojesh-13.2
 *
 */
public class PosSplitSelectForm extends PosBaseForm {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final int PANEL_CONTENT_V_GAP=8;
	protected static final int PANEL_CONTENT_H_GAP=8;

	private static final int PANEL_WIDTH = 680;
	private static final int PANEL_HEIGHT = 430;

	private static final int SPLIT_DETAIL_PANEL_HEIGHT = 174;
	private static final int ITEM_LIST_PANEL_HEIGHT = PANEL_HEIGHT-SPLIT_DETAIL_PANEL_HEIGHT-PANEL_CONTENT_V_GAP*3;
	private static final int ITEM_LIST_PANEL_WIDTH = PANEL_WIDTH-PANEL_CONTENT_H_GAP*2;
	private static final int SPLIT_DETAIL_PANEL_WIDTH=ITEM_LIST_PANEL_WIDTH;

	private JPanel mContentPanel;
	private SplitSelectionTablePanel splitItemListPanel;
	private SplitSelectionSummaryPanel splitSummaryPanel;

	private BeanOrderHeader orderHeader;
	private ArrayList<BeanOrderSplit> splitItems;
	private ArrayList<BeanOrderSplit> selectedSplitItems;
	
	private PosButton btnAdjustment;
	private PosButton btnPayment;

	
	private boolean allowAdjustAmount=true;

	public PosSplitSelectForm(){
		super("Select Splitting",PANEL_WIDTH,PANEL_HEIGHT);
		
		
		btnAdjustment=addButtonsToBottomPanel("Adjust", adjustButtonListener,0);
		btnPayment=addButtonsToBottomPanel("Pay", paymentButtonListener,0);
		setOkButtonVisible(false);
		
	}
	
	private IPosButtonListner paymentButtonListener=new IPosButtonListner() {
		

		@Override
		public void onClicked(PosButton button) {
			
			try{
			selectedSplitItems=splitItemListPanel.getSelectedItems();
			
			if(!validateSplits()) return ;
			if(!isSelectionValidForPayment()) return;
			
			PosPaymentForm mPaymentForm=new PosPaymentForm(PosSplitSelectForm.this,PaymentMode.Cash);
			mPaymentForm.setOrderWithSplit(orderHeader,selectedSplitItems);
//			mPaymentForm.setPartialPayment(true);
//			mPaymentForm.setSplitItems(selectedSplitItems);
			mPaymentForm.setListner(paymentListener);
			
			
//			mPaymentForm.setPaymentMode(PaymentMode.Partial);
			PosFormUtil.showLightBoxModal(PosSplitSelectForm.this, mPaymentForm);
			}catch (Exception e){
				PosLog.write(PosSplitSelectForm.this, "paymentButtonListener", e);
				PosFormUtil.showErrorMessageBox(PosSplitSelectForm.this, "Failed to process payment. Contact system admin.");
			}
		}


	};
	
	/**
	 * @return
	 */
	private boolean validateSplits() {
		
		boolean isValid=true;
		
		if(splitSummaryPanel.getTotalAdjustment()!=0){
			
			PosFormUtil.showInformationMessageBox(PosSplitSelectForm.this, "You had adjusted amounts in some splits. Please re-adjust the amounts to tally amounts.");
			isValid=false;
		}
		
		
		return isValid;
	}
	
	/**
	 * @return
	 */
	private boolean isSelectionValidForAdjustment(){
		
		boolean isValid=true;
		
		if(splitItemListPanel.getCurrentSelectedItem()==null){
			PosFormUtil.showInformationMessageBox(PosSplitSelectForm.this, "Please select a split to adjust the amount.");
			isValid=false;
		}
		
		return isValid;
	}
	
	/**
	 * @return
	 */
	private boolean isSelectionValidForPayment(){
		
		boolean isValid=true;
		
		if(splitItemListPanel.getSelectedItems()==null || splitItemListPanel.getSelectedItems().size()<=0 ){
			PosFormUtil.showInformationMessageBox(PosSplitSelectForm.this, "Please select split(s) to pay.");
			isValid=false;
		}
		
		return isValid;
	}
	
	private IPosPaymentFormListner paymentListener=new IPosPaymentFormListner() {
		
		@Override
		public void onPaymentDone(Object sender) {
			
			int splitIndex=splitItems.size()-1;
			ArrayList<BeanOrderSplit> splitsPayed=new ArrayList<BeanOrderSplit>();
			
			for(;splitIndex>=0;splitIndex--){
				
				BeanOrderSplit split=splitItems.get(splitIndex);
				if(split.isPayed()){
					
					splitsPayed.add(split);
//					splitItems.remove(split);
				}
			}
			
			
			splitItemListPanel.updateSplitItems();
			
			if(mListner!=null){
				
				((IPosSplitSelectFormListener)mListner).onPaymentDone(selectedSplitItems);

				if(splitItems.size()==splitsPayed.size()){

					((IPosSplitSelectFormListener)mListner).onPaymentCompleted();

					closeWindow();
				}
			}
		}
		
		@Override
		public void onPaymentCancelled(Object sender) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPaymentStatusChanged(Object sender) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private IPosButtonListner adjustButtonListener=new IPosButtonListner() {
		
		@Override
		public void onClicked(PosButton button) {
			
			if(!isSelectionValidForAdjustment()) return;
			
			PosNumKeypadForm numForm=new PosNumKeypadForm();
			numForm.setTitle("Adjust amount(+/-)");
			numForm.setFont(PosFormUtil.getLabelFont());
			numForm.setValue(PosCurrencyUtil.format(splitItemListPanel.getSelectedItemAdjustment()));
			numForm.setOnValueChanged(new IPosNumKeyPadFormListner() {
				
				@Override
				public void onValueChanged(String value) {
 
					splitItemListPanel.setSelectedItemAdjustment(Double.valueOf(value));
				}
				
				@Override
				public void onValueChanged(JTextComponent target, String oldValue) {
					// TODO Auto-generated method stub
					
				}
			});
			PosFormUtil.showLightBoxModal(PosSplitSelectForm.this,numForm);
			
		}
	};
	
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {

		mContentPanel=panel;
		mContentPanel.setLayout(null);
		setComponenets();

	}

	/**
	 * 
	 */
	private void setComponenets() {

		createItemListTable();
		createSplitSummaryPanel();

	}
	
	/**
	 * 
	 */
	private void createSplitSummaryPanel() {
		
		final int top=splitItemListPanel.getY()+splitItemListPanel.getHeight()+PANEL_CONTENT_V_GAP;
		final int left=PANEL_CONTENT_H_GAP;
		splitSummaryPanel=new SplitSelectionSummaryPanel(this, SPLIT_DETAIL_PANEL_WIDTH , SPLIT_DETAIL_PANEL_HEIGHT);
		splitSummaryPanel.setLocation(left, top);
		mContentPanel.add(splitSummaryPanel);
				
	}

	/**
	 * 
	 */
	private void createItemListTable() {

		splitItemListPanel=new SplitSelectionTablePanel(this, ITEM_LIST_PANEL_WIDTH, ITEM_LIST_PANEL_HEIGHT);
		splitItemListPanel.setLocation(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP);
		mContentPanel.add(splitItemListPanel);
		splitItemListPanel.setListener(new ISplitSelectionListener() {
			
			@Override
			public void onItemChanged() {
				splitSummaryPanel.setAmountToPay(splitItemListPanel.getAmountToPay());
				splitSummaryPanel.setTotalAdjustment(splitItemListPanel.getTotalAdjustment());
				splitSummaryPanel.setTotalAmount(splitItemListPanel.getTotalAmoount());
				splitSummaryPanel.setNetAmount(splitItemListPanel.getNetAmount());
			}
		});
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		
		return true;
	}

	/**
	 * @param allowAdjustAmount the allowAdjustAmount to set
	 */
	public void setAllowAdjustAmount(boolean allowAdjustAmount) {
		
		this.allowAdjustAmount =allowAdjustAmount;
		splitItemListPanel.setAllowAdjustAmount(this.allowAdjustAmount);
		btnAdjustment.setVisible(this.allowAdjustAmount);
	}

	/**
	 * @param orderHeader
	 * @param splits
	 */
	public void setOrderWithSplit(BeanOrderHeader orderHeader,ArrayList<BeanOrderSplit> splits) {
		
		this.orderHeader=orderHeader;
		this.splitItems=splits;//new ArrayList<BeanOrderSplit>();
		
//		for(BeanOrderSplit splitItem:splits)
//			splitItems.add(splitItem.clone());
		
		splitItemListPanel.setSplitItemList(this.splitItems);
	}
	

}
