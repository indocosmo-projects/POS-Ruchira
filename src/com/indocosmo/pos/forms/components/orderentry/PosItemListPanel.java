/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.itemcontrols.PosItemControl;
import com.indocosmo.pos.forms.components.itemcontrols.listners.IPosItemControlListner;
import com.indocosmo.pos.forms.components.listners.PosItemControlListnerAdapter;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosItemListPanelListner;


/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public final class PosItemListPanel extends JPanel{
	
	private enum ScrollButtonsTypes{
		NEXT,
		PREVIOUS
	}
	private static final int PANEL_CONTENT_H_GAP=3;
	private static final int PANEL_CONTENT_V_GAP=3;
	
	private static final Color PANEL_BG_COLOR=PosOrderEntryForm.PANEL_BG_COLOR;
	
	private static final String IMAGE_PREVIOUS_BUTTON_NORMAL="item_button_prev.png";
//	private static final String IMAGE_PREVIOUS_BUTTON_TOUCHED="item_button_prev_touch.png";
	
	private static final String IMAGE_NEXT_BUTTON_NORMAL="item_button_next.png";
//	private static final String IMAGE_NEXT_BUTTON_TOUCHED="item_button_next_touch.png";

	public static final int SCROLL_BUTTON_HEIGHT=PosItemControl.BUTTON_HEIGHT;
	public static final int MIN_SCROLL_WIDTH=150;
	public static final int SCROLL_BUTTON_WIDTH=(PosItemControl.BUTTON_WIDTH<MIN_SCROLL_WIDTH)?PosItemControl.BUTTON_WIDTH:(PosItemControl.BUTTON_WIDTH-PANEL_CONTENT_H_GAP)/2;
	public static final boolean IS_SCROLL_BUTTONS_COMBINED=(PosItemControl.BUTTON_WIDTH>=MIN_SCROLL_WIDTH);
	
	
	private ArrayList<BeanSaleItem> mItemList;
	private ArrayList<BeanSaleItem> mDisplayItemList;
	private int mWidth, mHeight;
	private ArrayList<PosItemControl> mPosItemControls;
	private JPanel mContentPanel;
	private int mItemsPerPage;
	private int mCurPage=0;
	
	private PosButton mButtonPrevious;
	private PosButton mButtonNext;
	
	private IPosItemListPanelListner posItemSelectedListner;

	
	private RootPaneContainer mParent;
	public PosItemListPanel(RootPaneContainer parent,int width,int height) {
		mParent=parent;
		mHeight=height;
		mWidth=width;
		initComponent();
	}
	
	
	public ArrayList<BeanSaleItem> getItemList() {
		return mItemList;
	}

	public void setItemList(ArrayList<BeanSaleItem> itemList) {
		this.mItemList = itemList;
		mCurPage=0;
		setDisplayItemList();
		setScrollButtons();
		bindControls();
	}
	
	public void refreshItemList() {
		//mCurPage=0;
		setDisplayItemList();
		setScrollButtons();
		bindControls();
	}
	 
	private void setDisplayItemList(){
		mDisplayItemList=null;
		if(mItemList!=null){
			mDisplayItemList=new ArrayList<BeanSaleItem>();
			for(int index=0; index<mItemList.size(); index++){
				if(mItemList.get(index).getGroupID()==0){
					mDisplayItemList.add(mItemList.get(index));
				}
			}
		}
		
	}
	
	private void setScrollButtons(){
		Boolean scrollEnabled= mPosItemControls.size()<=mDisplayItemList.size();
		mButtonNext.setVisible(scrollEnabled);
		mButtonPrevious.setVisible(scrollEnabled);
		mItemsPerPage=(!scrollEnabled)?mPosItemControls.size():mPosItemControls.size()-(IS_SCROLL_BUTTONS_COMBINED?1:2);
	}
	
	private void bindControls(){
		final int startIndex=mCurPage*mItemsPerPage;
		int itemIndex=startIndex;
		for(int index=0; index<mPosItemControls.size(); index++){
			PosItemControl posItemControl=mPosItemControls.get(index);
			if(mDisplayItemList!=null && itemIndex<startIndex+mItemsPerPage && itemIndex<mDisplayItemList.size()){
				if(mDisplayItemList.get(itemIndex).getGroupID()==0){
					posItemControl.setItem(mDisplayItemList.get(itemIndex));
				}else{
					posItemControl.setItem(null);
				}
				itemIndex++;
			}
			else
				posItemControl.setItem(null);
		}
		setScrollButtonStatus();
	}
	
	
	public final void setOnItemSelectedListner(
			IPosItemListPanelListner posItemSelectedListner) {
		this.posItemSelectedListner = posItemSelectedListner;
	}
	
	private void setScrollButtonStatus(){
		final int totalDisplayeItems=(mCurPage+1)*mItemsPerPage;
		mButtonNext.setEnabled(!(totalDisplayeItems>=mDisplayItemList.size()));
		mButtonPrevious.setEnabled(!(totalDisplayeItems<=mItemsPerPage));
	}
	
	
	private void initComponent() {
		
		setPreferredSize(new Dimension(mWidth, mHeight));
		setSize(mWidth, mHeight);
		setBackground(PANEL_BG_COLOR);
		setLayout(null);
		createScrollButtons();
		createItemButtons();
	}

	private void setContentPanelLayout(){
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setVgap(PANEL_CONTENT_V_GAP);
		flowLayout.setHgap(PANEL_CONTENT_H_GAP);
		flowLayout.setAlignment(FlowLayout.LEFT);
		mContentPanel.setLayout(flowLayout);
	}
	
	private void createScrollButtons(){
		
		mButtonNext=new PosButton();
		mButtonNext.registerKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.CTRL_DOWN_MASK);
		mButtonNext.setButtonStyle(ButtonStyle.BOTH);
		mButtonNext.setBackgroundColor(PosFormUtil.SCROLL_BUTTON_BG_COLOR);
		mButtonNext.setBounds(0, 0, SCROLL_BUTTON_WIDTH, SCROLL_BUTTON_HEIGHT);
		mButtonNext.setImage(PosResUtil.getImageIconFromResource(IMAGE_NEXT_BUTTON_NORMAL));
		mButtonNext.setOnClickListner(scrollButtonClickListner);
		mButtonNext.setTag(ScrollButtonsTypes.NEXT);
		mButtonNext.setVisible(false);
		
		mButtonPrevious=new PosButton();
		mButtonPrevious.registerKeyStroke(KeyEvent.VK_LEFT, KeyEvent.CTRL_DOWN_MASK);
		mButtonPrevious.setButtonStyle(ButtonStyle.BOTH);
		mButtonPrevious.setBackgroundColor(PosFormUtil.SCROLL_BUTTON_BG_COLOR);
		mButtonPrevious.setBounds(0, 0, SCROLL_BUTTON_WIDTH, SCROLL_BUTTON_HEIGHT);
		mButtonPrevious.setImage(PosResUtil.getImageIconFromResource(IMAGE_PREVIOUS_BUTTON_NORMAL));
		mButtonPrevious.setOnClickListner(scrollButtonClickListner);
		mButtonPrevious.setTag(ScrollButtonsTypes.PREVIOUS);
		mButtonPrevious.setVisible(false);
	}
	
	private IPosButtonListner scrollButtonClickListner=new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			ScrollButtonsTypes sbType=(ScrollButtonsTypes)((PosButton) button).getTag();
			switch(sbType){
				case NEXT:
					mCurPage++;
					break;
				case PREVIOUS:
					mCurPage--;
					break;
			}
			bindControls();
		}
	};
	
	private void createItemButtons(){
		mContentPanel=new JPanel();
		setContentPanelLayout();
		mContentPanel.setOpaque(false);
		mPosItemControls=new ArrayList<PosItemControl>();
		final int noCols=(int)getWidth()/(PosItemControl.BUTTON_WIDTH+PANEL_CONTENT_H_GAP);
		final int noRows=(int)getHeight()/(PosItemControl.BUTTON_HEIGHT+PANEL_CONTENT_V_GAP);
		final int contentPanelWidth=noCols*(PosItemControl.BUTTON_WIDTH+PANEL_CONTENT_H_GAP+1);
		final int contentPanelHeight=noRows*(PosItemControl.BUTTON_HEIGHT+PANEL_CONTENT_V_GAP+1);
		final int contentPanelLeft=(getWidth()-contentPanelWidth)/2;
		final int contentPanelTop=(getHeight()-contentPanelHeight)/2;
		final int noButtons=noCols*noRows;
		mContentPanel.setBounds(contentPanelLeft,contentPanelTop, contentPanelWidth, contentPanelHeight);
		for(int index=0;index<noButtons;index++){
			PosItemControl posIC=createPosItemControl();
			mPosItemControls.add(posIC);
			mContentPanel.add(posIC);
		}
		mContentPanel.add(mButtonPrevious);
		mContentPanel.add(mButtonNext);
		add(mContentPanel);
	}
	
	private IPosItemControlListner itemClickListner=new PosItemControlListnerAdapter() {
		@Override
		public void onClicked(BeanSaleItem item) {
			if(posItemSelectedListner!=null)
				posItemSelectedListner.onItemSelected(item);
		}
	};
	
	private PosItemControl createPosItemControl(){
		PosItemControl posIC=new PosItemControl();
		posIC.setOnClickListner(itemClickListner);
		return posIC;
	}

}
