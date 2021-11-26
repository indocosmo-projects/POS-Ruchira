package com.indocosmo.pos.forms.components.orderentry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;

import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.providers.shopdb.PosHotItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosHotItemProvider.HotItems;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.PosSelectButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosSelectButtonListner;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosHotItemListner;

/**
 * @author jojesh
 * This class will handle the HotItems panal operations
 */
@SuppressWarnings("serial")
public class PosHotItemPanel extends JPanel {

	private static final int PANEL_CONTENT_H_GAP=4;
	private static final int PANEL_CONTENT_V_GAP=3;
	
	
//	private static final int HI_BUTTON_HIEGHT=42;
	private static final int HI_BUTTON_HIEGHT=70;
	private static final int HI_BUTTON_WIDTH=(PosOrderEntryForm.RIGHT_PANEL_WIDTH-PANEL_CONTENT_H_GAP*4)/3;
	
	
	public static final int PANEL_HEIGHT=HI_BUTTON_HIEGHT+PANEL_CONTENT_V_GAP*2;
	public static final int PANEL_WIDTH=PosOrderEntryForm.RIGHT_PANEL_WIDTH;
//	private static final String IMAGE_HOT_ITEM_BUTTON="hot_items.png";
//	private static final String IMAGE_HOT_ITEM_TOUCHED="hot_items_touch.png";
//	private static final String IMAGE_HOT_ITEM_SELECTED="hot_items_selected.png";
	
//	private static final Color PANEL_BG_COLOR=new Color(0,255,255);
	public static final Color PANEL_BG_COLOR=PosOrderEntryForm.PANEL_BG_COLOR;
	
//	private int mLeft, mTop;
	private ArrayList<PosSelectButton> mHotItemButtons;
	private PosSelectButton mSelectedHotButton;
	
	private ArrayList<BeanSaleItem> mHotItemList; 
	private Map<Integer, ArrayList<BeanSaleItem>> mHotItemLists;
	private int mCurPage=0;
	
	private IPosHotItemListner mHotItemListner;

	private RootPaneContainer mParent;

//	public PosHotItemPanel(int left, int top) {
//		mLeft=left;
//		mTop=top;
//		mHotItemButtons=new ArrayList<PosSelectButton>();
//		mHotItemLists=new HashMap<Integer, ArrayList<PosSaleItemObject>>(); 
//		initComponent();
//		createHotitems();
//	}
	
	/**
	 * @param posOrderEntryForm
	 */
	public PosHotItemPanel(RootPaneContainer parent) {
		mParent=parent;
		mHotItemButtons=new ArrayList<PosSelectButton>();
		mHotItemLists=new HashMap<Integer, ArrayList<BeanSaleItem>>(); 
		initComponent();
		createHotitems();
	}

	public void createHotitems()
	{
		mHotItemLists=new PosHotItemProvider().getHotItemLists();
		for(HotItems hi:EnumSet.allOf(HotItems.class)){
			PosSelectButton mHotItemButton = new PosSelectButton("H" + String.valueOf(hi.getCode()));
			mHotItemButton.setButtonStyle(ButtonStyle.COLORED);
			mHotItemButton.setBackgroundColor(Color.MAGENTA.darker().darker());
			mHotItemButton.setBounds(0, 0, HI_BUTTON_WIDTH, HI_BUTTON_HIEGHT);		
			mHotItemButton.setHorizontalAlignment(SwingConstants.CENTER);			
//			mHotItemButton.setImage(IMAGE_HOT_ITEM_BUTTON);
//			mHotItemButton.setTouchedImage(IMAGE_HOT_ITEM_TOUCHED);	
//			mHotItemButton.setSelectedImage(IMAGE_HOT_ITEM_SELECTED);
			mHotItemButton.setTag(hi);
			mHotItemButton.setOnSelectListner(selectedListner);
			mHotItemButton.setEnabled(false);
			mHotItemButtons.add(mHotItemButton);
			add(mHotItemButton);
		}
	}
	
	private IPosSelectButtonListner selectedListner=new IPosSelectButtonListner() {
		@Override
		public void onSelected(PosSelectButton button) {
			if(mSelectedHotButton!=null)
				mSelectedHotButton.setSelected(false);
			mSelectedHotButton=button;
			final int index=((HotItems)button.getTag()).getCode();
		if(mHotItemListner!=null)
			mHotItemListner.onSelected(mHotItemLists.get(index));
		}
	};

	private void initComponent() {
		int height=PANEL_HEIGHT;
		int width=PANEL_WIDTH;
		setPreferredSize(new Dimension(width, height));
		setSize(width, height);
		setBackground(PANEL_BG_COLOR);
//		setBackground(Color.CYAN);
		setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		setOpaque(false);
	}


	public void setHotItemList(ArrayList<BeanSaleItem> hotItemList) {
		this.mHotItemList = hotItemList;
	}
	
	public ArrayList<BeanSaleItem>  getHotItemList() {
		return this.mHotItemList; 
	}

	public void setOnSelectedListner(IPosHotItemListner hotItemListner) {
		this.mHotItemListner = hotItemListner;
	}
	
	public void setSelected(Boolean selected){
		if(mSelectedHotButton!=null)
			mSelectedHotButton.setSelected(selected);
		else
			mHotItemButtons.get(0).setSelected(selected);
	}
	public ArrayList<PosSelectButton>  getHotItemButtons(){
		return mHotItemButtons;
	}
	
	
	
}
