/**
 * 
 */
package com.indocosmo.pos.forms.split;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JPanel;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderSplit;
import com.indocosmo.pos.data.beans.BeanOrderSplitDetail;
import com.indocosmo.pos.forms.PosBaseForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.split.components.EditSplitNamePanel;
import com.indocosmo.pos.forms.split.components.EditSplitSummaryPanel;
import com.indocosmo.pos.forms.split.components.SplitItemListTablePanel;
import com.indocosmo.pos.forms.split.listners.IPosSplitEditFormListener;
import com.indocosmo.pos.forms.split.listners.IPosSplitListItemPanelListener;
import com.indocosmo.pos.forms.split.listners.IPosSplitNameEditPanelListener;

/**
 * @author jojesh-13.2
 *
 */
public class PosSplitEditForm extends PosBaseForm {

	/**
	 * 
	 */
	private static final String DEF_BILL_PREFIX="BILL-";
	private static final long serialVersionUID = 1L;
	protected static final int PANEL_CONTENT_V_GAP=8;
	protected static final int PANEL_CONTENT_H_GAP=8;

	private static final int PANEL_WIDTH = 700;
	private static final int PANEL_HEIGHT = 485;

	private static final int SPLIT_OPTION_PANEL_HEIGHT = 109;
//	private static final int SPLIT_OPTION_PANEL_WIDTH = 420;//PANEL_WIDTH-PANEL_CONTENT_H_GAP*2;
	private static final int SPLIT_DETAIL_PANEL_WIDTH =  250;
	private static final int SPLIT_DETAIL_PANEL_HEIGHT =  SPLIT_OPTION_PANEL_HEIGHT;
	private static final int SPLIT_NAME_PANEL_HEIGHT = SPLIT_OPTION_PANEL_HEIGHT; // SPLIT_OPTION_PANEL_HEIGHT-PosTouchableFieldBase.LAYOUT_DEF_HEIGHT-PANEL_CONTENT_V_GAP*2;
	private static final int SPLIT_NAME_PANEL_WIDTH =  PANEL_WIDTH-SPLIT_DETAIL_PANEL_WIDTH-PANEL_CONTENT_V_GAP*3;
	
	private static final int ITEM_LIST_PANEL_HEIGHT = PANEL_HEIGHT-SPLIT_OPTION_PANEL_HEIGHT-PANEL_CONTENT_V_GAP*3;
	private static final int ITEM_LIST_PANEL_WIDTH = PANEL_WIDTH-PANEL_CONTENT_H_GAP*2;
//	private static final int BILL_DETAIL_PANEL_WIDTH=PANEL_WIDTH-SPLIT_OPTION_PANEL_WIDTH-PANEL_CONTENT_H_GAP*2;
//	private static final int BILL_DETAIL_PANEL_HEIGHT=SPLIT_OPTION_PANEL_HEIGHT;

//	private static final SplitBasedOn DEFAULT_SPLIT_BASED_ON=SplitBasedOn.Amount;

	private JPanel mContentPanel;
//	private PosItemBrowsableField mBillSplitBasedOn;
//	private JPanel mMethodePanel;
//	private AdvancedSplitMethodPanel mAdvancedSplitePanel;
//	private SimpleSplitMethodPanel mSimpleSplitePanel;
	private EditSplitSummaryPanel splitBillDetailsPanel;
	private SplitItemListTablePanel splitItemListPanel;
	private EditSplitNamePanel splitNamePanel;

//	private PosButton btnMakePayment;
//	private PosButton btnSplit;
	private PosButton btnApply;

	private BeanOrderHeader orderHeader;
//	private ;
	private ArrayList<BeanOrderSplitDetail> splitListItems;
	private ArrayList<BeanOrderSplitDetail> splitListItemsOrg;
	private Set<String> definedSplitsNames;

	private IPosSplitEditFormListener listener;
	
//	private HashMap<String, BeanOrderSplitDetail> advancedSplits=null;
	
	private int itemCount=0; 
	private double totalAmout=0.0;
	
//	private PosOrderHdrProvider  mOrderBillProvider;
	
	
//	private SplitBasedOn selectedSplitBasedOn;
	
	
	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosSplitEditForm() {
		super("Edit Split",PANEL_WIDTH,PANEL_HEIGHT);
		
		addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowGainedFocus(WindowEvent arg0) {
			
				splitNamePanel.requestFocus();
				
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
		setComponenets();
	}
	
	
	/**
	 * Make payment button listener
	 */
	private IPosButtonListner applyButtonListner=new IPosButtonListner() {

		@Override
		public void onClicked(PosButton button) {
			// TODO Auto-generated method stub

		}
	};
	private boolean isForceResetName;
	
	
	
	/**
	 * 
	 */
	private void setComponenets() {

		createItemListTable();
		createSplitNamePanel();
		createSplitDetailPanel();
		
	}
	
	/**
	 * 
	 */
	private void createSplitNamePanel(){

		int left=PANEL_CONTENT_H_GAP;
		int top=ITEM_LIST_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*2;

		splitNamePanel=new EditSplitNamePanel(this, SPLIT_NAME_PANEL_WIDTH, SPLIT_NAME_PANEL_HEIGHT);
		splitNamePanel.setListener(new IPosSplitNameEditPanelListener() {
			
			@Override
			public void onNameSelected(String name) {
				
				mButtonOk.requestFocus();
				
			}
		});
//		mAdvancedSplitePanel=new AdvancedSplitMethodPanel(this,SPLIT_METHODE_CONTENT_PANEL_WIDTH, SPLIT_METHODE_CONTENT_PANEL_HEIGHT);
		splitNamePanel.setLocation(left, top);
		splitNamePanel.setFocusable(true);
		mContentPanel.add(splitNamePanel);
	}

	/**
	 * 
	 */
	private void createSplitDetailPanel() {

		final int top=splitItemListPanel.getY()+splitItemListPanel.getHeight()+PANEL_CONTENT_V_GAP;
		final int left=splitNamePanel.getX()+splitNamePanel.getWidth()+PANEL_CONTENT_H_GAP;

		splitBillDetailsPanel=new EditSplitSummaryPanel(this, SPLIT_DETAIL_PANEL_WIDTH, SPLIT_DETAIL_PANEL_HEIGHT);
		splitBillDetailsPanel.setLocation(left, top);
		mContentPanel.add(splitBillDetailsPanel);

	}
	
	/**
	 * 
	 */
	private void createItemListTable() {

		splitItemListPanel=new SplitItemListTablePanel(this, ITEM_LIST_PANEL_WIDTH, ITEM_LIST_PANEL_HEIGHT,false,false);
		splitItemListPanel.setLocation(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP);
		splitItemListPanel.setRowsSelectable(true);
		splitItemListPanel.setListener(new IPosSplitListItemPanelListener() {
			
			@Override
			public void onSelectionChanged(int index) {
				onItemSelected(index);
			}
		});
		
		mContentPanel.add(splitItemListPanel);

	}
	
	/**
	 * 
	 */
	private void onItemSelected(int index){
		
		updateSplitDetails();
	}
	
	/**
	 * 
	 */
	private void updateSplitDetails(){

		itemCount=0;
		totalAmout=0;
		for(BeanOrderSplitDetail item: splitListItems){
			
			if(item.isSelected()){
				itemCount+=1;
				totalAmout+=item.getPrice();
			}
		}
		
		splitBillDetailsPanel.setItemCount(itemCount);
		splitBillDetailsPanel.setTotalAmount(totalAmout);
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
	public void setSplitItems(ArrayList<BeanOrderSplitDetail> splitListItems)  {
		
		this.splitListItemsOrg=splitListItems;
		this.splitListItems=new ArrayList<BeanOrderSplitDetail>();
		
		try {
			
			if(!isForceResetName)
				splitNamePanel.setName(splitListItems.get(0).getSplitName());
			
			for(BeanOrderSplitDetail item: splitListItems){
				
				BeanOrderSplitDetail newItem=item.clone();
				newItem.setSplitName(null);
				newItem.setSelected(true);
				this.splitListItems.add(newItem);
			}
			
			splitItemListPanel.SetSplitItemList(this.splitListItems);
			updateSplitDetails();
			
		} catch (CloneNotSupportedException e) {

			PosLog.write(this, "setSplitItems", e);
			PosFormUtil.showSystemError(this);
		}
		
		splitNamePanel.requestFocus();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		
		if(!onValidate()) return false;
		
		for(int indx=0;indx<this.splitListItems.size();indx++){

			BeanOrderSplitDetail item=this.splitListItems.get(indx);

			this.splitListItemsOrg.get(indx).setSplitName(((item.isSelected())?this.splitNamePanel.getName():null));
			this.splitListItemsOrg.get(indx).setSelected(false);
		}
		
		if(this.listener!=null)
			this.listener.onEditingCompleted();
		
		return true;
	}
	
	/**
	 * @return
	 */
	private boolean onValidate() {
	
		boolean valid=true;
		
		if(!splitNamePanel.onValidate())
			valid=false;
		
		return valid;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onCancelButtonClicked()
	 */
	@Override
	public boolean onCancelButtonClicked() {
		
		if(this.listener!=null)
			this.listener.onEditingCancelled();
		
		return super.onCancelButtonClicked();
	}
	
	/**
	 * @return
	 */
	private boolean validateSplitCriteria(){
		
		boolean isValid=true;
//		
		return isValid;
	}
	
	/**
	 * @param splits
	 * @return
	 */
	private boolean validateSplits(ArrayList<BeanOrderSplit> splits) {

		boolean isValid=true;
		return isValid;
	}


	/**
	 * @param listener the listener to set
	 */
	public void setListener(IPosSplitEditFormListener listener) {
		this.listener = listener;
	}


	/**
	 * @return
	 */
	public String getSplitTitle() {
		
		return splitNamePanel.getName();
	}


	/**
	 * @param definedSplitsNames
	 */
	public void setDefinedSplits(Set<String> definedSplitsNames) {
		
		splitNamePanel.setDefinedSplitsNames(definedSplitsNames);
	}


	/**
	 * @param b
	 */
	public void setResetName(boolean reset) {
		
		this.isForceResetName=reset;
		if(reset)
			splitNamePanel.setName("");
		
		splitNamePanel.requestFocus();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Dialog#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visibility) {
		
		super.setVisible(visibility);
		
		if(visibility)
			splitNamePanel.requestFocus();
	}

}
