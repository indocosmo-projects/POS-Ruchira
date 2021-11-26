/**
 * 
 */
package com.indocosmo.pos.forms.restaurant.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosSelectButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.IPosSelectButtonListner;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.restaurant.components.itemcontrols.PosServiceTabletemControl;
import com.indocosmo.pos.forms.search.PosExtSearchForm;
import com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter;

/**
 * @author jojesh
 *
 */


@SuppressWarnings("serial")
public class PosServiceTableSelectionPanel extends JPanel{
	
	private static final int SCROLL_BUTTON_WIDTH=90;
	private static final int SCROLL_BUTTON_HEIGHT=87;
	private static final int BORDER_WIDTH=2;
	
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	
	private static final String IMAGE_BUTTON_SCROLL_UP="dlg_buton_up.png";
	private static final String IMAGE_BUTTON_SCROLL_UP_TOUCH="dlg_buton_up_touch.png";
		
	private static final String IMAGE_BUTTON_SCROLL_DOWN="dlg_buton_down.png";	
	private static final String IMAGE_BUTTON_SCROLL_DOWN_TOUCH="dlg_buton_down_touch.png";

	private static final int IMAGE_SEARCH_BUTTON_WIDTH=90;
	private static final int IMAGE_SEARCH_BUTTON_HEIGHT=60;
	
	private static final String IMAGE_BUTTON_SEARCH="comp_search_normal.png";	
	private static final String IMAGE_BUTTON_SEARCH_TOUCH="comp_search_touch.png";
	
	private static final int TABLE_LIST_BUTTON_COLS=4;
	private static final int TABLE_LIST_BUTTON_ROWS=4;
	
	private static final int TABLE_LIST_PANEL_HEIGHT=PosServiceTabletemControl.LAYOUT_HEIGHT*TABLE_LIST_BUTTON_ROWS+PANEL_CONTENT_V_GAP*(TABLE_LIST_BUTTON_ROWS+1)+BORDER_WIDTH*2;
	private static final int TABLE_LIST_PANEL_WIDTH=PosServiceTabletemControl.LAYOUT_WIDTH*TABLE_LIST_BUTTON_COLS+PANEL_CONTENT_H_GAP*(TABLE_LIST_BUTTON_COLS+1)+BORDER_WIDTH*2;

	private static final int SCROLL_PANEL_WIDTH=SCROLL_BUTTON_WIDTH+PANEL_CONTENT_H_GAP*2;
	private static final int SCROLL_PANEL_HEIGHT=TABLE_LIST_PANEL_HEIGHT;
	
	public static final int LAYOUT_WIDTH= TABLE_LIST_PANEL_WIDTH+SCROLL_PANEL_WIDTH;
	public static final int LAYOUT_HEIGHT= TABLE_LIST_PANEL_HEIGHT;
	
	private static final int ITEMS_PER_PAGE=TABLE_LIST_BUTTON_COLS*TABLE_LIST_BUTTON_ROWS;
	
	private int mCurPage=0;
	private JPanel mTableListPanel;
	private JPanel mScrollPanel;
	
	private PosSelectButton mSelectedItemContrl;
	
	private PosButton imageButtonScrollUp;
	private PosButton imageButtonScrollDown;
	private PosButton imageButtonSearch;
	
	protected ArrayList<? extends PosSelectButton> mItemControlList;
	protected ArrayList<? extends BeanServingTable> mTableObjectList;
	
	/*
	 * 
	 */
	private RootPaneContainer mParent;
	public PosServiceTableSelectionPanel(RootPaneContainer parent) {
		super();
		
		this.mParent=parent;
		
		setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		setPreferredSize(new Dimension(LAYOUT_WIDTH,LAYOUT_HEIGHT));
		
		mTableListPanel=createTableListPanel();
		add(mTableListPanel);
		
		mScrollPanel=createScrollPanel();
		add(mScrollPanel);
		
		this.mItemControlList=createTableItemControls();
	}
	
	/*
	 * 
	 */
	private JPanel createTableListPanel(){
		
		int height=TABLE_LIST_PANEL_HEIGHT;
		int width=TABLE_LIST_PANEL_WIDTH;
		
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING, PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		panel.setPreferredSize(new Dimension(width, height));
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		return panel;
	}
	
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean isEnabled) {
		
		super.setEnabled(isEnabled);
		if(mItemControlList!=null)
			for(PosSelectButton ctrl:mItemControlList)
				ctrl.setEnabled(isEnabled);
		
		imageButtonScrollUp.setEnabled(isEnabled);
		imageButtonScrollDown.setEnabled(isEnabled);
		imageButtonSearch.setEnabled(isEnabled);
	}


	private void initControlList(){
		if(this.mTableObjectList!=null){
			this.mTableObjectList.clear();
			this.mTableObjectList=null;
		}
		mCurPage=0;
	}
	
	protected PosServiceTabletemControl createItemControl(){
		return  new PosServiceTabletemControl() ;
	}
	
	private ArrayList<? extends PosServiceTabletemControl> createTableItemControls(){
		ArrayList<PosServiceTabletemControl> itemControlList=new ArrayList<PosServiceTabletemControl>();
		for(int i=0;i<ITEMS_PER_PAGE;i++){
			PosServiceTabletemControl itemCtrl=createItemControl();
			itemControlList.add(itemCtrl);
			itemCtrl.setOnSelectListner(new IPosSelectButtonListner() {
				
				@Override
				public void onSelected(PosSelectButton button) {
					if(mSelectedItemContrl!=null)
						mSelectedItemContrl.setSelected(false);
					mSelectedItemContrl=button;

					if(mListner!=null)
						mListner.onServiceTableSelected(((PosServiceTabletemControl)mSelectedItemContrl).getServiceTable());
				}
			});
			mTableListPanel.add(itemCtrl);
		}
		return itemControlList;
	}
	
	public void setTableList(ArrayList<? extends BeanServingTable> list){
		initControlList();
		this.mTableObjectList=list;
		displayItems();
		final boolean isEnabled=(mItemControlList!=null && mItemControlList.size()>0);
		setEnabled(isEnabled);
		
	}
	
	public void resetSelection(){
		if(mSelectedItemContrl!=null)
			mSelectedItemContrl.setSelected(false);
		mSelectedItemContrl=null;
	}
	
	private void displayItems(){
		resetSelection();
		int itemStartIndex=mCurPage*ITEMS_PER_PAGE;
		int itemIndex=0;
		for(int i=0;i<ITEMS_PER_PAGE;i++){
			PosServiceTabletemControl ctrlItem=(PosServiceTabletemControl)mItemControlList.get(i);
			itemIndex=itemStartIndex+i;
			if(itemIndex<mTableObjectList.size()){
				BeanServingTable tableObject=(BeanServingTable)mTableObjectList.get(itemIndex);
				ctrlItem.setServiceTable(tableObject);
			}
			else
				ctrlItem.setServiceTable(null);
		}
	}
	
	public BeanServingTable getSelectedTable(){
		BeanServingTable table=null;
		if(mSelectedItemContrl!=null)
			table=((PosServiceTabletemControl)mSelectedItemContrl).getServiceTable();
		return table;
	}
	
	private JPanel createScrollPanel()
	{
		int scrollPaneHeight=SCROLL_PANEL_HEIGHT;
		int scrollPaneWidth=SCROLL_PANEL_WIDTH;
		int vGap=(SCROLL_PANEL_HEIGHT-(SCROLL_BUTTON_HEIGHT*2+IMAGE_SEARCH_BUTTON_HEIGHT))/4;
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, PANEL_CONTENT_H_GAP,vGap));
		panel.setPreferredSize(new Dimension(scrollPaneWidth, scrollPaneHeight));
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

		imageButtonScrollUp=new PosButton();
		imageButtonScrollUp.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_UP));
		imageButtonScrollUp.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_UP_TOUCH));		
		imageButtonScrollUp.setHorizontalAlignment(SwingConstants.CENTER);		
		imageButtonScrollUp.setBounds(0, 0, SCROLL_BUTTON_WIDTH,SCROLL_BUTTON_HEIGHT);
		imageButtonScrollUp.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				if(mCurPage>0){
					mCurPage--;
					displayItems();
				}
			}
		});
		imageButtonScrollUp.setEnabled(false);
		panel.add(imageButtonScrollUp);
		
	
		imageButtonScrollDown=new PosButton();
		imageButtonScrollDown.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_DOWN));
		imageButtonScrollDown.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_DOWN_TOUCH));		
		imageButtonScrollDown.setHorizontalAlignment(SwingConstants.CENTER);		
		imageButtonScrollDown.setBounds(0, 0, SCROLL_BUTTON_WIDTH,SCROLL_BUTTON_HEIGHT);
		imageButtonScrollDown.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {

				if((mCurPage*ITEMS_PER_PAGE)+ITEMS_PER_PAGE<=mTableObjectList.size()){
					mCurPage++;
					displayItems();
				}
			}
		});
		imageButtonScrollDown.setEnabled(false);
		panel.add(imageButtonScrollDown);
		
		imageButtonSearch=new PosButton();
		imageButtonSearch.setText("Search ");
		imageButtonSearch.setBounds(0, 0, IMAGE_SEARCH_BUTTON_WIDTH,IMAGE_SEARCH_BUTTON_HEIGHT);
		imageButtonSearch.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SEARCH));
		imageButtonSearch.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SEARCH_TOUCH));		
		imageButtonSearch.setHorizontalAlignment(SwingConstants.CENTER);		
		imageButtonSearch.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				
				if(mTableObjectList==null || mTableObjectList.size()==0) return;
				
				PosTableSearchForm serachForm=new PosTableSearchForm(mTableObjectList);
				
				serachForm.setListner(new PosItemExtSearchFormAdapter() {
					
					@Override
					public boolean onAccepted(Object sender, IPosSearchableItem item) {
//						addPosItem(String.valueOf(item.getItemCode()));
						setSelectedTable((BeanServingTable)item);
						return true;
					}
				});
				PosFormUtil.showLightBoxModal(mParent, serachForm);
			}
		});
		
		imageButtonSearch.setEnabled(false);
		panel.add(imageButtonSearch);
		
		return panel;
	}
	
	/**
	 * @param table
	 */
	public void setSelectedTable(final BeanServingTable table) {
		
		if(table==null) {

			if(mSelectedItemContrl!=null)
				mSelectedItemContrl.setSelected(false);
				mSelectedItemContrl=null;
			return;
		}
		
		final int itemContrlindex=getTableIndexFromList(table);
		if(itemContrlindex>=0){
			mCurPage=(int)Math.ceil(itemContrlindex/ITEMS_PER_PAGE);
			displayItems();
			PosSelectButton selectedItemContrl=mItemControlList.get(itemContrlindex-mCurPage*ITEMS_PER_PAGE);
			selectedItemContrl.setSelected(true);
		}
	}
	
	private int getTableIndexFromList(BeanServingTable table){
		BeanServingTable item=null;
		if(mTableObjectList!=null && mTableObjectList.size()>0){
			for(int i=0; i< mTableObjectList.size();i++){
				item=mTableObjectList.get(i);
				if(item.getId()==table.getId())
					return i;
			}
		}
		
		return -1;
	}
	private IPosServiceTableSelectionPanelListner mListner;
	
	public void setListner(IPosServiceTableSelectionPanelListner listner){
		this.mListner=listner;
	}
	public interface IPosServiceTableSelectionPanelListner{
		public void onServiceTableSelected(BeanServingTable table);
	}
	
	private class PosTableSearchForm extends PosExtSearchForm {
		/**
		 * @param searchList
		 */
		public PosTableSearchForm(ArrayList<? extends IPosSearchableItem> searchList) {
			super(searchList);
		}

	}
	
}
