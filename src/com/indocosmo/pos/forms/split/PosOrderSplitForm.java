/**
 * 
 */
package com.indocosmo.pos.forms.split;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.split.BillSplitMethod;
import com.indocosmo.pos.common.enums.split.SplitBasedOn;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderSplitUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderSplit;
import com.indocosmo.pos.data.beans.BeanOrderSplitDetail;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.forms.PosBaseForm;
import com.indocosmo.pos.forms.PosPaymentForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.listners.IPosPaymentFormListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.search.PosExtSearchForm;
import com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter;
import com.indocosmo.pos.forms.split.components.SplitBillSummaryPanel;
import com.indocosmo.pos.forms.split.components.SplitItemListTablePanel;
import com.indocosmo.pos.forms.split.components.SplitOperationPanel;
import com.indocosmo.pos.forms.split.components.TableSeatSplitMethodPanel.TableSplitBasedOn;
import com.indocosmo.pos.forms.split.listners.IPosSplitEditFormListener;
import com.indocosmo.pos.forms.split.listners.ISplitOperationPanelListener;
import com.indocosmo.pos.forms.split.listners.adapters.PosOrderSplitSelectionFormAdapter;

/**
 * @author jojesh-13.2
 *
 */
public class PosOrderSplitForm extends PosBaseForm {

	/**
	 * 
	 */
	public static final String DEF_BILL_PREFIX="BILL";
	private static final String UN_SPLI_ITEM_TITLE="UNSPLITTED ITEMS";
	private static final String UN_NAMED_SPLITS="UN-NAMED SPLITS";

	private static final long serialVersionUID = 1L;
	protected static final int PANEL_CONTENT_V_GAP=8;
	protected static final int PANEL_CONTENT_H_GAP=8;

	private static final int PANEL_WIDTH = 950;
	private static final int PANEL_HEIGHT = 571;

	private static final int SPLIT_METHODE_OPTION_PANEL_HEIGHT = 235;
	private static final int SPLIT_METHODE_OPTION_PANEL_WIDTH = 500;

	private static final int ITEM_LIST_PANEL_HEIGHT = PANEL_HEIGHT-SPLIT_METHODE_OPTION_PANEL_HEIGHT-PANEL_CONTENT_V_GAP*3;
	private static final int ITEM_LIST_PANEL_WIDTH = PANEL_WIDTH-PANEL_CONTENT_H_GAP*2;
	private static final int BILL_DETAIL_PANEL_WIDTH=PANEL_WIDTH-SPLIT_METHODE_OPTION_PANEL_WIDTH-PANEL_CONTENT_H_GAP*2;
	private static final int BILL_DETAIL_PANEL_HEIGHT=SPLIT_METHODE_OPTION_PANEL_HEIGHT;

	private JPanel mContentPanel;
	private SplitOperationPanel mSpliteMethodPanel;
	private SplitBillSummaryPanel splitBillDetailsPanel;
	private SplitItemListTablePanel splitItemListPanel;
	private PosButton btnSplit;
	private PosButton btnEditView;
	
	private BeanOrderHeader orderHeader;
	private ArrayList<BeanOrderSplitDetail> splitListItems;
	private double billTotal=0.0; 
	private double billPaid=0.0;
	private double billPartRec=0.0;
	private double billDiscount=0.0; 
	private PosOrderHdrProvider  mOrderBillProvider;
	private SplitBasedOn selectedSplitBasedOn;
	private PosOrderServiceTypes serviceType;
	

	/**
	 * 
	 */
	public PosOrderSplitForm(PosOrderServiceTypes serviceType){
		super("Bill Splitting",PANEL_WIDTH,PANEL_HEIGHT);

		this.serviceType=serviceType;
		
		setComponenets();

		btnEditView=addButtonsToBottomPanel("Edit/View",editViewButtonListner,1);
		btnSplit=addButtonsToBottomPanel("Split",splitButtonListner,1);
		setOkButtonCaption("Make Payment");
		setResetButtonVisible(true);
		
		mSpliteMethodPanel.setSplitBasedOn(SplitBasedOn.Custom);
		mOrderBillProvider=new PosOrderHdrProvider();

		addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowLostFocus(WindowEvent arg0) {

			}

			@Override
			public void windowGainedFocus(WindowEvent arg0) {

				mSpliteMethodPanel.requestFocus();
			}
		});

	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {

		mContentPanel=panel;
		mContentPanel.setLayout(null);
	}

	/**
	 * Make payment button listener
	 */
	private IPosButtonListner splitButtonListner=new IPosButtonListner() {

		@Override
		public void onClicked(PosButton button) {

			switch (selectedSplitBasedOn) {
			case Custom:
				doCustomSplit();
				break;

			case Seat:
				doTableSeatSplit();
				break;
			default:
				//do nothing
				break;
			}
		}
	};

	/**
	 * 
	 */
	private void doTableSeatSplit(){

		for(BeanOrderSplitDetail item:splitListItems){
	
			String billName=DEF_BILL_PREFIX+item.getOrderDetailtem().getServingTable().getCode();

			if(mSpliteMethodPanel.getTableSeatSplitMethodPanel().getSelectedTableSplitBasedOn()==TableSplitBasedOn.Seat)
				billName+=PosStringUtil.paddLeft(String.valueOf(item.getOrderDetailtem().getServingSeat()),2,'0');

			item.setSplitName(billName);

		}
		
		splitItemListPanel.refresh();
	}

	/**
	 * 
	 */
	private void doCustomSplit(){
		
		final HashMap<String, ArrayList<BeanOrderSplitDetail>>  advancedSplits=getAdvancedSplitsItem();
		final ArrayList<BeanOrderSplitDetail> items=splitItemListPanel.getSelectedItemList();
		boolean resetSplit=false;

		if(items==null || items.size()==0){
			
			PosFormUtil.showErrorMessageBox(PosOrderSplitForm.this, "No items selected. Please select items.");
			return;
		}else{

			for(BeanOrderSplitDetail item:items){
				if(item.getSplitName()!=null && !item.getSplitName().isEmpty()){

					if(PosFormUtil.showQuestionMessageBox(PosOrderSplitForm.this, 
							MessageBoxButtonTypes.OkCancel,
							"The selection contains a items having split. Continuing will reset the splitting for those items.",
							null)==MessageBoxResults.Ok){

						resetSplit=true;
						break;
					}
					else
						return;
				}
			}
		}

		showSplitEditForm(advancedSplits.keySet(),items,resetSplit);
	}

	/**
	 * 
	 */
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onResetButtonClicked()
	 */
	@Override
	public void onResetButtonClicked() {
	
		if(PosFormUtil.showQuestionMessageBox(PosOrderSplitForm.this, 
				MessageBoxButtonTypes.YesNo,
				"Do you want to reset the current split settings?",
				null)==MessageBoxResults.Yes){
			
			resetSplits();
			mSpliteMethodPanel.reset();
			
	
		}
	}

	/**
	 * Edit/View button listener
	 */
	private IPosButtonListner editViewButtonListner=new IPosButtonListner() {

		@Override
		public void onClicked(PosButton button) {

			final HashMap<String, ArrayList<BeanOrderSplitDetail>>  advancedSplits=getAdvancedSplitsItem();
			ArrayList<BeanOrderSplit> splitItems=buildOrderSplitsFromAdvSplits(advancedSplits);

			if(splitItems==null || splitItems.size()<=0) {

				PosFormUtil.showErrorMessageBox(PosOrderSplitForm.this,"No split(s) defined to edit.");
				return;
			}

			PosExtSearchForm serachForm=new PosExtSearchForm(splitItems);
			serachForm.setListner(new PosItemExtSearchFormAdapter() {

				@Override
				public boolean onAccepted(Object sender,IPosSearchableItem item) {

					ArrayList<BeanOrderSplitDetail> items=advancedSplits.get(((BeanOrderSplit)item).getDescription());
					showSplitEditForm(advancedSplits.keySet(), items);

					return true;
				}
			});

			PosFormUtil.showLightBoxModal(PosOrderSplitForm.this,serachForm);

		}
	};
	private double billPartUsed;

	/**
	 * @param definedSplitsNames
	 * @param items
	 */
	private void showSplitEditForm(Set<String> definedSplitsNames, final ArrayList<BeanOrderSplitDetail> items){

		showSplitEditForm(definedSplitsNames,items,false);
	}

	/**
	 * @param definedSplitsNames
	 * @param items
	 * @param isReset
	 */
	private void showSplitEditForm(Set<String> definedSplitsNames, final ArrayList<BeanOrderSplitDetail> items, boolean isReset){

		final PosSplitEditForm form=new PosSplitEditForm();
		form.setSplitItems(items);
		form.setDefinedSplits(definedSplitsNames);
		form.setResetName(isReset);
		form.setListener(new IPosSplitEditFormListener() {

			@Override
			public void onEditingCompleted() {

				splitItemListPanel.refresh();

			}

			@Override
			public void onEditingCancelled() {
				// TODO Auto-generated method stub

			}
		});

		PosFormUtil.showLightBoxModal(PosOrderSplitForm.this,form);
	}


	/**
	 * 
	 */
	private void setComponenets() {

		createItemListTable();
		createSplitMethodPanel();
		createBillDetailPanel();

	}

	/**
	 * 
	 */
	private void createBillDetailPanel() {

		final int top=mSpliteMethodPanel.getY();
		final int left=mSpliteMethodPanel.getWidth()+mSpliteMethodPanel.getX()-1;

		splitBillDetailsPanel=new SplitBillSummaryPanel(this, BILL_DETAIL_PANEL_WIDTH+1, BILL_DETAIL_PANEL_HEIGHT);
		splitBillDetailsPanel.setLocation(left, top);
		mContentPanel.add(splitBillDetailsPanel);

	}

	/**
	 * 
	 */
	private void createItemListTable() {

		splitItemListPanel=new SplitItemListTablePanel(this, ITEM_LIST_PANEL_WIDTH, ITEM_LIST_PANEL_HEIGHT,true,serviceType==PosOrderServiceTypes.TABLE_SERVICE);
		splitItemListPanel.setLocation(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP);
		mContentPanel.add(splitItemListPanel);

	}

	/**
	 * 
	 */
	private void createSplitMethodPanel() {

		mSpliteMethodPanel=new SplitOperationPanel(this,SPLIT_METHODE_OPTION_PANEL_WIDTH,SPLIT_METHODE_OPTION_PANEL_HEIGHT);
		mSpliteMethodPanel.setLocation(PANEL_CONTENT_H_GAP, ITEM_LIST_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*2);
		mSpliteMethodPanel.setBorder(new EtchedBorder());
		mSpliteMethodPanel.setListener(new ISplitOperationPanelListener() {

			@Override
			public boolean onSplitMethodChanged(SplitBasedOn value) {

				return onSPlitMethodChanged((SplitBasedOn)value);
			}

			@Override
			public double onExactAmountRequested() {
				// TODO Auto-generated method stub
				return billTotal-billPaid-billDiscount;
			}

			@Override
			public void onResetRequested() {
				
				resetSplits();
				mSpliteMethodPanel.reset();
			}

			@Override
			public boolean hasSplit() {

				return hasAdanceSplit();
			}
		});

		mContentPanel.add(mSpliteMethodPanel);

	}

	/**
	 * @return
	 */
	private boolean hasAdanceSplit(){

		boolean res=false;
		for(BeanOrderSplitDetail item:splitListItems){

			if((item.getSplitName()!=null && !item.getSplitName().isEmpty()) || item.isSelected()){
				res=true;
				break;
			}
		}

		return res;
	}

	/**
	 * @param value
	 */
	protected boolean onSPlitMethodChanged(SplitBasedOn method) {

		if(!(this.selectedSplitBasedOn!=null && 
				selectedSplitBasedOn.getBillSplitMethod()==BillSplitMethod.Advance && 
				method.getBillSplitMethod()==BillSplitMethod.Advance)){ 
		
			if(this.selectedSplitBasedOn!=null && this.selectedSplitBasedOn!=method){

				if(hasAdanceSplit()){

					if(PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNo, 
							"This action will reset the current splittings.Do you want to continue?", 
							null)==MessageBoxResults.Yes){

						resetSplits();

					}else{

						return false;
					}
				}

			}
		}

		splitItemListPanel.setRowsSelectable(method==SplitBasedOn.Custom);
		splitBillDetailsPanel.setBillSplitMethod(method.getBillSplitMethod());
		selectedSplitBasedOn=method;
		btnSplit.setEnabled(selectedSplitBasedOn.getBillSplitMethod()==BillSplitMethod.Advance);
		btnEditView.setEnabled(selectedSplitBasedOn.getBillSplitMethod()==BillSplitMethod.Advance);

		return true;
	}

	/**
	 * 
	 */
	private void resetSplits() {

		for(BeanOrderSplitDetail splitItem:splitListItems){
			splitItem.setSelected(false);
			splitItem.setSplitName(null);
		}
		splitItemListPanel.refresh();

	}

	/**
	 * @return the orderHeader
	 */
	public BeanOrderHeader getOrderHeader() {
		return orderHeader;
	}

	/**
	 * @param orderHeader the orderHeader to set
	 * @throws CloneNotSupportedException 
	 */
	public void setOrderHeader(BeanOrderHeader orderHeader)  {

		this.orderHeader = orderHeader;

		billTotal=0.0; 
		billPaid=0.0;
		billPartRec=0.0;
		billDiscount=0.0;
		try {

			orderHeader.setOrderSplits(null);
			orderHeader.setOrderSplits(mOrderBillProvider.getOrderSPlits(orderHeader));
			orderHeader.setOrderPaymentItems(null);
			orderHeader.setOrderPaymentItems(mOrderBillProvider.getOrderPayments(orderHeader));

			if(orderHeader.getOrderSplits()!=null){

				orderHeader.setSplitItemIDList(PosOrderSplitUtil.createSplitItemIDList(orderHeader.getOrderSplits()));
			}
			billTotal=PosOrderUtil.getTotalAmount(orderHeader);
			splitListItems=new ArrayList<BeanOrderSplitDetail>();
			addToSplitList(orderHeader.getOrderDetailItems());
			splitItemListPanel.SetSplitItemList(splitListItems);
			updateAmountDetails();
			
			if(serviceType!=PosOrderServiceTypes.TABLE_SERVICE)
				mSpliteMethodPanel.setSplitMethodEnabled(SplitBasedOn.Seat, false);

		} catch ( Exception e) {

			PosLog.write(this,"setOrderHeader", e);
			PosFormUtil.showErrorMessageBox(this, "Failed to Split the order. Please contact administrator.");
		}
	}


	/**
	 * @param splitDetailItem
	 */
	private void updatePaidQuantity(BeanOrderSplitDetail splitDetailItem){

		double paiedQty=0.0;
		final HashMap<String, ArrayList<BeanOrderSplitDetail>> orderSplitDetails = orderHeader.getSplitItemIDList();
		final String detailItemID=splitDetailItem.getOrderDetailItemID();
		final String detailItemSubID=splitDetailItem.getOrderDetailSubID();

		if(orderSplitDetails!=null && orderSplitDetails.size()>0){

			if(orderSplitDetails.containsKey(detailItemID)){

				final ArrayList<BeanOrderSplitDetail> detailSplits = orderSplitDetails.get(detailItemID);

				for(BeanOrderSplitDetail item:detailSplits){

					if(item.getOrderDetailSubID().equals(detailItemSubID)){

						paiedQty+=item.getQuantity();
					}
				}
			}
		}

		final double qtyRemaining=splitDetailItem.getQuantity()-paiedQty;
		PosOrderSplitUtil.updateSplitItemQuantity(splitDetailItem,qtyRemaining);
	}

	/**
	 * @param itemList
	 * @throws CloneNotSupportedException 
	 */
	private void addToSplitList(ArrayList<BeanOrderDetail> itemList) throws CloneNotSupportedException{

		for(BeanOrderDetail dtlItem:itemList){

			if(!dtlItem.isVoid()) {

				final int splitCount=(int) Math.ceil(dtlItem.getSaleItem().getQuantity());
				final double itemQuatity=dtlItem.getSaleItem().getQuantity();
				BeanOrderSplitDetail spliteDetailItem=null;

				if(splitCount==1){

					spliteDetailItem=getSplitDetailItem(dtlItem,1,itemQuatity);

					if(spliteDetailItem!=null)
						splitListItems.add(spliteDetailItem);

				}else{
					for(int sii=1;sii<=splitCount;sii++){

						final double splitItemQty=((itemQuatity>=sii)?1:itemQuatity-(int)itemQuatity);

						spliteDetailItem=getSplitDetailItem(dtlItem,sii,splitItemQty);
						if(spliteDetailItem!=null)
							splitListItems.add(spliteDetailItem);

					}
				}

			}
		}

	}

	/**
	 * @param dtlItem
	 * @param orderDetailubID
	 * @param splitItemQty
	 * @return
	 * @throws CloneNotSupportedException
	 */
	private BeanOrderSplitDetail getSplitDetailItem(BeanOrderDetail dtlItem, int subID,double splitItemQty) throws CloneNotSupportedException{

		BeanOrderSplitDetail splitDetailItem=null;
		final String orderDetailSubID=PosOrderUtil.appendToId(dtlItem.getId(), subID);
		splitDetailItem=PosOrderSplitUtil.getSplitDetailItem(dtlItem,orderDetailSubID,splitItemQty);
		updatePaidQuantity(splitDetailItem);

		return (splitDetailItem!=null && splitDetailItem.getQuantity()>0)?splitDetailItem:null;
	}

	/**
	 * 
	 */
	private void updateAmountDetails(){

//		billPaid=PosOrderSplitUtil.getSplitTotalPaidAmount(orderHeader) - PosOrderSplitUtil.getSplitTotalAppliedDiscount(orderHeader);
		billPaid=PosOrderSplitUtil.getSplitTotalPaidAmountLessDiscount(orderHeader)  ;
		billPartRec=orderHeader.getSplitPartRecieved();
		billPartUsed=orderHeader.getSplitPartUsed();
		billDiscount=PosOrderUtil.getBillDiscount(orderHeader);
		splitBillDetailsPanel.setBillTotal(billTotal);
		splitBillDetailsPanel.setBillPaid(billPaid);
		splitBillDetailsPanel.setBillPart(billPartRec,billPartRec-billPartUsed);
		splitBillDetailsPanel.setTotalDiscount(billDiscount);
}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {

		try{
			if(!validateSplitCriteria()) return false;

			ArrayList<BeanOrderSplit> splits=splitBills();
			if(!validateSplits(splits)) return false;

			/**
			 * To speed up the split payment process
			 * If the split has only 2 items Then remove the UN_SPLI_ITEMS
			 * 
			 */
			if(splits.size()==2){

				for(int index=0;index<2;index++){

					if(splits.get(index).getDescription()==UN_SPLI_ITEM_TITLE){
						splits.remove(index);
						break;
					}

				}
			}

			/**
			 * Do direct payment for Amount Percentage and split having one item
			 */
			if(selectedSplitBasedOn==SplitBasedOn.Amount || selectedSplitBasedOn==SplitBasedOn.Percentage || splits.size()==1){

				/**
				 * Only first split split will be payed
				 */
				if(splits.get(0).getDescription()==UN_SPLI_ITEM_TITLE)
					showPaymentForm();
				else
					showPaymentForm(splits);

			}else{

				showSplitSelectionForm(splits);

			}

			//		}
		}catch(Exception e){
			PosLog.write(PosOrderSplitForm.this, "onOkButtonClicked", e);
			PosFormUtil.showSystemError(PosOrderSplitForm.this);
		}

		return false;
	}

	/**
	 * @param splits 
	 * 
	 */
	private void showSplitSelectionForm(final ArrayList<BeanOrderSplit> splits) {

		PosSplitSelectForm form=new PosSplitSelectForm();
		form.setAllowAdjustAmount(selectedSplitBasedOn==SplitBasedOn.Count || selectedSplitBasedOn==SplitBasedOn.Custom);
		form.setListner(new PosOrderSplitSelectionFormAdapter() {

			@Override
			public void onPaymentDone(ArrayList<BeanOrderSplit> splits) {

				updateAmountDetails();

				updateSplitList(splits);
			}

			@Override
			public void onCancelled() {

				setOrderHeader(orderHeader);

			}

			@Override
			public void onPaymentCompleted() {

				onSplitPaymentDone(splits);

			}
		} );

		form.setOrderWithSplit(orderHeader,splits);
		PosFormUtil.showLightBoxModal(this,form);

	}

	/**
	 * 
	 */
	private void showPaymentForm(){

		showPaymentForm(null);
	}

	/**
	 * @param splits 
	 * 
	 */

	private void showPaymentForm(final ArrayList<BeanOrderSplit> splits) {

		try {

			PosPaymentForm mPaymentForm=new PosPaymentForm(PosOrderSplitForm.this,PaymentMode.Cash);

			if(splits!=null)
				mPaymentForm.setOrderWithSplit(orderHeader,splits.get(0));
			else
				mPaymentForm.setOrderHeader(orderHeader);

			mPaymentForm.setListner(new IPosPaymentFormListner() {

				@Override
				public void onPaymentStatusChanged(Object sender) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onPaymentDone(Object sender) {

					onSplitPaymentDone(splits);
				}

				@Override
				public void onPaymentCancelled(Object sender) {
					// TODO Auto-generated method stub

				}
			});
			PosFormUtil.showLightBoxModal(PosOrderSplitForm.this, mPaymentForm);

		} catch (Exception e) {
			PosLog.write(this, "showPaymentForm", e);
			PosFormUtil.showSystemError(this);
		}
	}

	/**
	 * Update the payments
	 * @param splits
	 */
	private void onSplitPaymentDone(ArrayList<BeanOrderSplit> splits){

		if(splits!=null){
			updateAmountDetails();
			updateSplitList(splits);
		}

		if(orderHeader.getStatus()==PosOrderStatus.Closed || PosOrderUtil.getTotalAmount(orderHeader)<=billPaid){
			closeWindow();
		}else
			setOrderHeader(orderHeader);
	}


	/**
	 * Updates the splits
	 * @param splits
	 */
	private void updateSplitList(ArrayList<BeanOrderSplit> splits){

		for(BeanOrderSplit split:splits){

			if(split.getSplitDetails()!=null){

				for(BeanOrderSplitDetail payedlItem:split.getSplitDetails()){

					for(BeanOrderSplitDetail item:splitListItems){

						if(item.getOrderDetailSubID().equals(payedlItem.getOrderDetailSubID())){

							if(item.getPrice()==payedlItem.getPrice()){
								splitListItems.remove(item);
								item.setPaid(true);

							}else{
								item.setQuantity(item.getQuantity()-payedlItem.getQuantity());
								item.setPrice(item.getPrice()-payedlItem.getPrice());
								item.setPaid(false);
							}
							break;
						}
					}
				}
			}
		}
		splitItemListPanel.SetSplitItemList(splitListItems);

	}

	/**
	 * @return
	 */
	private boolean validateSplitCriteria(){

		boolean isValid=true;

		if(selectedSplitBasedOn.getBillSplitMethod()==BillSplitMethod.Simple){

			final double value=(double)mSpliteMethodPanel.getSplitValue();

			if(value<=0){

				PosFormUtil.showErrorMessageBox(this, "Invalid split value. Please check your spliting method selection and value.");
				isValid=false;

			}else if(selectedSplitBasedOn==SplitBasedOn.Count ||  selectedSplitBasedOn==SplitBasedOn.Percentage  ) {

				if(value<1 || value >100){

					PosFormUtil.showErrorMessageBox(this, "Invalid split value. Please input value between 1-100.");
					isValid=false;
				}
			}
		}

		return isValid;
	}

	/**
	 * @param splits
	 * @return
	 */
	private boolean validateSplits(ArrayList<BeanOrderSplit> splits) {

		boolean isValid=true;

		if(splits==null || splits.size()<=0){

			PosFormUtil.showErrorMessageBox(this, "No splits to pay. Define splits");
			isValid=false;
		}
//		else{
//
//			isValid=validateSplitAmounts(splits);
//		}

		return isValid;
	}
	/**
	 * @param splits
	 * @return
	 */
//	private boolean validateSplitAmounts(ArrayList<BeanOrderSplit> splits) {
//
//		boolean isValid=true;
//		double total=0;
//
//		for(BeanOrderSplit split:splits)
//			total+=Math.abs(split.getActualAmount());
//
//		/**
//		 * Verify the amounts in case of basedon is amount or percentage 
//		 */
////		if(selectedSplitBasedOn==SplitBasedOn.Amount && total!=(billTotal-billPaid) ){
////
////			PosFormUtil.showErrorMessageBox(this, "Invalid splits. Please check your spliting criteria.");
////			isValid = false;
////		}
//
//		return isValid;
//	}

	/**
	 * @return
	 * @throws CloneNotSupportedException 
	 */
	private ArrayList<BeanOrderSplit> splitBills() throws CloneNotSupportedException{

		ArrayList<BeanOrderSplit> splitItems=null;

		switch (selectedSplitBasedOn.getBillSplitMethod()) {
		case Simple:
			splitItems=doSimpleSplit();
			break;
		case Advance:
			splitItems=doAdvancedSplit();

			break;
		default:
			break;
		}

		return splitItems;
	}

	/**
	 * @param item
	 */
	private HashMap<String, ArrayList<BeanOrderSplitDetail>> getAdvancedSplitsItem() {

		final HashMap<String, ArrayList<BeanOrderSplitDetail>> advancedSplits=new HashMap<String, ArrayList<BeanOrderSplitDetail>>();

		for(BeanOrderSplitDetail item:splitListItems){

			if(item.getSplitName()!=null && !item.getSplitName().isEmpty()){

				final String splitTitle=item.getSplitName();
				ArrayList<BeanOrderSplitDetail> itemList=null;

				if(advancedSplits.containsKey(splitTitle))
					itemList=advancedSplits.get(splitTitle);
				else{
					itemList=new ArrayList<BeanOrderSplitDetail>();
					advancedSplits.put(splitTitle, itemList);
				}

				itemList.add(item);
			}
		}
		return advancedSplits;
	}

	/**
	 * @param item
	 */
	private ArrayList<BeanOrderSplit> getUnSplitItemsForAdvancedSplit() {

		final HashMap<String, ArrayList<BeanOrderSplitDetail>> splitList=new HashMap<String, ArrayList<BeanOrderSplitDetail>>();

		for(BeanOrderSplitDetail item:splitListItems){

			if((item.getSplitName()==null || item.getSplitName().isEmpty()) && !item.isSelected()){

				ArrayList<BeanOrderSplitDetail> itemList=null;

				if(splitList.containsKey(UN_SPLI_ITEM_TITLE))
					itemList=splitList.get(UN_SPLI_ITEM_TITLE);
				else{
					itemList=new ArrayList<BeanOrderSplitDetail>();
					splitList.put(UN_SPLI_ITEM_TITLE, itemList);
				}

				itemList.add(item);
			}
		}

		return buildOrderSplitsFromAdvSplits(splitList);
	}

	/**
	 * @param item
	 */
	private ArrayList<BeanOrderSplitDetail> getUnNamedSplits() {

		ArrayList<BeanOrderSplitDetail> itemList=null;

		for(BeanOrderSplitDetail item:splitListItems){

			if((item.getSplitName()==null || item.getSplitName().isEmpty()) && item.isSelected()){

				if(itemList==null)
					itemList=new ArrayList<BeanOrderSplitDetail>();

				itemList.add(item);
			}
		}

		return  itemList;
	}

	/**
	 * @return
	 * @throws CloneNotSupportedException 
	 */
	private ArrayList<BeanOrderSplit>  doAdvancedSplit() throws CloneNotSupportedException {

		ArrayList<BeanOrderSplit> splitItems=null;

		HashMap<String, ArrayList<BeanOrderSplitDetail>>  advancedSplits=getAdvancedSplitsItem();

		/**
		 * Check anything is selected.
		 * User can proceed to payment without clicking on split button
		 */
		final ArrayList<BeanOrderSplitDetail> unNamedSplits = getUnNamedSplits();

		if(unNamedSplits!=null && unNamedSplits.size()>0){

			if(advancedSplits==null)
				advancedSplits=new HashMap<String, ArrayList<BeanOrderSplitDetail>>();

			advancedSplits.put(UN_NAMED_SPLITS, unNamedSplits);

		}

		if(advancedSplits!=null)
			splitItems=buildOrderSplitsFromAdvSplits(advancedSplits);

		/**
		 * Get the un-split amount items
		 * 
		 */
		final ArrayList<BeanOrderSplit> usplitItems=getUnSplitItemsForAdvancedSplit();

		if(usplitItems!=null){

			if(splitItems==null)
				splitItems=new ArrayList<BeanOrderSplit>();

			final double splitEquallyBy=splitItems.size();


			if(mSpliteMethodPanel.isSplitEqually() && splitItems.size()>0){

				for(BeanOrderSplit usplitItem:usplitItems){

					for(BeanOrderSplitDetail dtlSplitItem:usplitItem.getSplitDetails()){

						final BeanOrderSplitDetail newItem=dtlSplitItem.clone();
						final double itemNewQty=dtlSplitItem.getQuantity()/splitEquallyBy;
						final double newItemPrice=dtlSplitItem.getPrice()/splitEquallyBy;

						newItem.setQuantity(itemNewQty);
						newItem.setPrice(newItemPrice);

						for(BeanOrderSplit splitItem:splitItems){

							splitItem.getSplitDetails().add(newItem);
							splitItem.setAmount(splitItem.getAmount()+newItemPrice);
						}

					}
				}

			}else{

				/**
				 * Add a new item as unsplited
				 */
				splitItems.addAll(usplitItems);
			}

		}

		//		}

		return splitItems;
	}

	/**
	 * Builds the OrderSplit array list 
	 * @param advancedSplits
	 * @return
	 */
	private ArrayList<BeanOrderSplit>  buildOrderSplitsFromAdvSplits( HashMap<String, ArrayList<BeanOrderSplitDetail>>  advancedSplits) {

		ArrayList<BeanOrderSplit> splitItems=null;

		if(advancedSplits!=null && advancedSplits.size()>0){

			splitItems=new ArrayList<BeanOrderSplit>();

			for(String key:advancedSplits.keySet()){

				double value=0.0;

				for(BeanOrderSplitDetail dtlItem:advancedSplits.get(key)){

					value+=dtlItem.getPrice();
				}

				final BeanOrderSplit orderSplit=new BeanOrderSplit();
				orderSplit.setDescription(key);
				orderSplit.setOrderID(orderHeader.getOrderId());
				orderSplit.setAmount(value);
				orderSplit.setBasedOn(selectedSplitBasedOn);
				orderSplit.setSplitDetails(advancedSplits.get(key));
				splitItems.add(orderSplit);
			}
		}

		return splitItems;

	}
	/**
	 * 
	 */
	private ArrayList<BeanOrderSplit> doSimpleSplit() {

		double[] values=null;
		double value=0.0;

		switch (selectedSplitBasedOn) {
		case Amount:

			value=(double)mSpliteMethodPanel.getSplitValue();
			values=new double[2];

			values[0]=value;
			values[1]=splitBillDetailsPanel.getBalanceAmount()-value;

			break;
		case Percentage: 

			final double perValue=mSpliteMethodPanel.getSplitValue()/100;
			value=(billTotal-billPaid)*perValue;

			values=new double[2];
			values[0]=value;
			values[1]=splitBillDetailsPanel.getBalanceAmount()-value;

			break;
		case Count:

			final int count=(int)mSpliteMethodPanel.getSplitValue();
			value=splitBillDetailsPanel.getBalanceAmount()/count;

			values=new double[count];
			for(int splitCount=0; splitCount<count;splitCount++)
				values[splitCount]=value;

			break;
		default:
			break;
		}

		final ArrayList<BeanOrderSplit> splitItems=new ArrayList<BeanOrderSplit>();

		int totalBillCount=((orderHeader.getOrderSplits()!=null)?orderHeader.getOrderSplits().size():0);

		for(int splitCount=0; splitCount<values.length;splitCount++){

			totalBillCount++;

			final BeanOrderSplit orderSplit=new BeanOrderSplit();
			final String billName=DEF_BILL_PREFIX+PosStringUtil.paddLeft(String.valueOf(totalBillCount), 2, '0' );

			orderSplit.setDescription(billName);
			orderSplit.setOrderID(orderHeader.getOrderId());
			orderSplit.setAmount(values[splitCount]);
			orderSplit.setBasedOn(selectedSplitBasedOn);
			splitItems.add(orderSplit);

		}

		return splitItems;
	}

}
