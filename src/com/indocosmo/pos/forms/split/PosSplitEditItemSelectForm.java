/**
 * 
 */
package com.indocosmo.pos.forms.split;

import java.util.ArrayList;

import javax.swing.JPanel;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderSplit;
import com.indocosmo.pos.forms.PosBaseForm;
import com.indocosmo.pos.forms.split.components.SplitSelectionTablePanel;
import com.indocosmo.pos.forms.split.components.SplitSelectionTablePanel.ISplitSelectionListener;
import com.indocosmo.pos.forms.split.components.SplitSelectionTablePanel.SelectionMode;

/**
 * @author jojesh-13.2
 *
 */
public class PosSplitEditItemSelectForm extends PosBaseForm {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final int PANEL_CONTENT_V_GAP=8;
	protected static final int PANEL_CONTENT_H_GAP=8;

	private static final int PANEL_WIDTH = 650;
	private static final int PANEL_HEIGHT =252;

	private static final int ITEM_LIST_PANEL_HEIGHT = PANEL_HEIGHT-PANEL_CONTENT_V_GAP*2;
	private static final int ITEM_LIST_PANEL_WIDTH = PANEL_WIDTH-PANEL_CONTENT_H_GAP*2;

	private JPanel mContentPanel;
	private SplitSelectionTablePanel splitItemListPanel;

	private BeanOrderHeader orderHeader;
	private ArrayList<BeanOrderSplit> splitItems;
	private BeanOrderSplit selectedSplitItem;
	

	public PosSplitEditItemSelectForm(){
		super("Select Splitting",PANEL_WIDTH,PANEL_HEIGHT);
		
		setOkButtonCaption("Edit/View");
		
	}
	
	/**
	 * @return
	 */
	private boolean isSelectionValid(){
		
		boolean isValid=true;
		
		if(splitItemListPanel.getCurrentSelectedItem()==null){
			PosFormUtil.showInformationMessageBox(PosSplitEditItemSelectForm.this, "Please select a split to edit.");
			isValid=false;
		}
		
		return isValid;
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
	 * 
	 * 
	 */
	private void setComponenets() {

		createItemListTable();

	}

	/**
	 * 
	 * 
	 */
	private void createItemListTable() {

		splitItemListPanel=new SplitSelectionTablePanel(this, ITEM_LIST_PANEL_WIDTH, ITEM_LIST_PANEL_HEIGHT,SelectionMode.Simple);
		splitItemListPanel.setLocation(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP);
		mContentPanel.add(splitItemListPanel);
		splitItemListPanel.setListener(new ISplitSelectionListener() {
			
			@Override
			public void onItemChanged() {
				
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
	 * @param orderHeader
	 * @param splits
	 */
	public void setSplits(ArrayList<BeanOrderSplit> splits) {
		
		this.splitItems=splits;
		splitItemListPanel.setSplitItemList(this.splitItems);
	}


}
