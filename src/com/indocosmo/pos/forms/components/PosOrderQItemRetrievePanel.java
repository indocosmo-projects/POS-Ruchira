package com.indocosmo.pos.forms.components;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.data.beans.BeanOrderQHeader;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.itemcontrols.PosOrderQueueItemControl;
import com.indocosmo.pos.forms.components.itemcontrols.listners.IPosOrderQueueItemControlListner;
import com.indocosmo.pos.forms.components.listners.IPosQueueItemContainerListener;
import com.indocosmo.pos.forms.components.tab.PosTab;

@SuppressWarnings("serial")
public class PosOrderQItemRetrievePanel extends PosTab {
	
	private enum ScrollTypes{
		NEXT,
		PREVIOUS
	}
	
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	public static final int PANEL_WIDTH=PosOrderEntryForm.LEFT_PANEL_WIDTH;
	
	public static final Color GRID_ITEM_BORDER_COLOR=Color.LIGHT_GRAY;
	
	private int mLeft, mTop;
	private int mHeight, mWidth;
	private JPanel mItemListPanel;
	private PosOrderQueueItemControl mSelectedItem;
//	private int mSelectedItemIndex;
	private int mItemsPerPage;
	private int mCurPage=0;
	private PosOrderQueueItemControl mButtonNext;
	private PosOrderQueueItemControl mButtonPrevious;
	private ArrayList<PosOrderQueueItemControl> mGridItems;
	private ArrayList<BeanOrderQHeader> mItemList;
	
	public PosOrderQItemRetrievePanel(RootPaneContainer parent,int left,int top, int width, int height) {
		super(parent,"Order Queue");
		mLeft=left;
		mTop=top;
		mHeight=height;
		mWidth=width;
		initComponent();
	}

	private void initComponent() {
		setBounds(mLeft,mTop,mWidth, mHeight);
		setBackground(Color.DARK_GRAY);
		setBorder(new LineBorder(GRID_ITEM_BORDER_COLOR));
		setLayout(null);
		createItemButtons();
	}
	
	private void createItemButtons(){
		mItemListPanel=new JPanel();
		setContentPanelLayout();
		mItemListPanel.setOpaque(false);
		mGridItems=new ArrayList<PosOrderQueueItemControl>();
		final int noCols=(int)getWidth()/(PosOrderQueueItemControl.LAYOUT_WIDTH+PANEL_CONTENT_H_GAP);
		final int noRows=(int)getHeight()/(PosOrderQueueItemControl.LAYOUT_HEIGHT+PANEL_CONTENT_V_GAP);
		final int contentPanelWidth=noCols*(PosOrderQueueItemControl.LAYOUT_WIDTH+PANEL_CONTENT_H_GAP+1);
		final int contentPanelHeight=noRows*(PosOrderQueueItemControl.LAYOUT_HEIGHT+PANEL_CONTENT_V_GAP+1);
		final int contentPanelLeft=(getWidth()-contentPanelWidth)/2;
		final int contentPanelTop=(getHeight()-contentPanelHeight)/2;
		final int noButtons=noCols*noRows;
		mItemListPanel.setBounds(contentPanelLeft,contentPanelTop, contentPanelWidth, contentPanelHeight);
		mButtonPrevious=new PosOrderQueueItemControl();
		mButtonPrevious.setListner(gridScrollButtonListner);
		mButtonPrevious.setText("◄");
		mButtonPrevious.setTag(ScrollTypes.PREVIOUS);
		mItemListPanel.add(mButtonPrevious);
		for(int index=0;index<noButtons;index++){
			PosOrderQueueItemControl item=createOrderItemControl();
//			item.setIndex(index);
			mGridItems.add(item);
			mItemListPanel.add(item);
		}
		mItemListPanel.setBounds(contentPanelLeft,contentPanelTop, contentPanelWidth, contentPanelHeight);
		mButtonNext=new PosOrderQueueItemControl();
		mButtonNext.setListner(gridScrollButtonListner);
		mButtonNext.setText("►");
		mButtonNext.setTag(ScrollTypes.NEXT);
		mItemListPanel.add(mButtonNext);
		add(mItemListPanel);
		mItemsPerPage=mGridItems.size();
	}
	
	private void setContentPanelLayout(){
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setVgap(PosOrderEntryForm.PANEL_CONTENT_V_GAP);
		flowLayout.setHgap(PosOrderEntryForm.PANEL_CONTENT_H_GAP);
		flowLayout.setAlignment(FlowLayout.LEFT);
		mItemListPanel.setLayout(flowLayout);
	}
	
	private PosOrderQueueItemControl createOrderItemControl(){
		PosOrderQueueItemControl posIC=new PosOrderQueueItemControl();
		posIC.setListner(gridItemListner);
		return posIC;
	}
	
	public void validateComponent() {
		// TODO Auto-generated method stub
		
	}
	
	private IPosOrderQueueItemControlListner gridScrollButtonListner=new IPosOrderQueueItemControlListner() {
		@Override
		public void onSelected(PosOrderQueueItemControl item) {
			ScrollTypes sbType=(ScrollTypes)item.getTag();
			switch(sbType){
				case NEXT:
					mCurPage++;
					break;
				case PREVIOUS:
					mCurPage--;
					break;
			}
			item.setSelected(false);
			bindControls();
			if(mQueueItemContainerListener!=null)
				mQueueItemContainerListener.onPageChanged(mCurPage);
		}
		
		@Override
		public void onDoubleClick(PosOrderQueueItemControl item) {
			// TODO Auto-generated method stub
			
		}
	};
	private IPosOrderQueueItemControlListner gridItemListner=new IPosOrderQueueItemControlListner() {
		@Override
		public void onSelected(PosOrderQueueItemControl item) {
			if(mSelectedItem!=null)
				mSelectedItem.setSelected(false);
//			mSelectedItemIndex=item.getIndex();
			mSelectedItem=item;
			if(mQueueItemContainerListener!=null)
				mQueueItemContainerListener.onItemSelected(item);
		}
		@Override
		public void onDoubleClick(PosOrderQueueItemControl gridItem) {
			if(mQueueItemContainerListener!=null)
				mQueueItemContainerListener.onItemSelected(gridItem);
		}
	};
	 
	private void bindControls(){
		mItemsPerPage=(mItemList.size()<=mGridItems.size())?mGridItems.size():mGridItems.size()-2;
		final int startIndex=mCurPage*mItemsPerPage;
		int itemIndex=startIndex;
//		final int endIndex=(mItemList.size()<=mGridItems.size())?mGridItems.size():mGridItems.size()-2;
		for(int index=0; index<mGridItems.size(); index++){
			PosOrderQueueItemControl itemControl=mGridItems.get(index);
			if(mItemList!=null && itemIndex<startIndex+mItemsPerPage && itemIndex<mItemList.size()){
				itemControl.setOrderQueItem(mItemList.get(itemIndex++));
			}
			else
				itemControl.setOrderQueItem(null);
			itemControl.setSelected(false);
		}
		setScrollButtonStatus();
		mSelectedItem=null;
	}
	
	private void setScrollButtonStatus(){
		final int totalDisplayeItems=(mCurPage+1)*mItemsPerPage;
		mButtonNext.setEnabled(!(totalDisplayeItems>=mItemList.size()));
		mButtonPrevious.setEnabled(!(totalDisplayeItems<=mItemsPerPage));
		mButtonNext.setVisible(mButtonNext.isEnabled());
		mButtonPrevious.setVisible((mButtonNext.isEnabled() || mButtonPrevious.isEnabled()));
	}
	
	public void setItemList(ArrayList<BeanOrderQHeader> itemList){
		mCurPage=0;
		mItemList=itemList;
		bindControls();
	}
	
	public ArrayList<BeanOrderQHeader>  getItemList(){
		return mItemList;
		
	}
	
	public void delete(BeanOrderQHeader item){
		mItemList.remove(item);
		mCurPage=(mItemList.size()>=mCurPage*mGridItems.size())?mCurPage:mCurPage-1;
		bindControls();
		if(mQueueItemContainerListener!=null)
			mQueueItemContainerListener.onItemDeleted();
	}
	
	private IPosQueueItemContainerListener mQueueItemContainerListener;
	
	public void setListner(IPosQueueItemContainerListener listner){
		mQueueItemContainerListener=listner;
	}
	

}
