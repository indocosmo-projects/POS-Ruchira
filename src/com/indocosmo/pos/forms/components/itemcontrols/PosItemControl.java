package com.indocosmo.pos.forms.components.itemcontrols;

import java.awt.Color;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIItemListPanelSetting;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.itemcontrols.listners.IPosItemControlListner;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public class PosItemControl extends PosButton {

	public static final int BUTTON_HEIGHT=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getItemListPanelSetting().getItemControlHeight();
	public static final int BUTTON_WIDTH=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getItemListPanelSetting().getItemControlWidth();

	public static final int PANEL_CONTENT_V_GAP=2;
	public static final int PANEL_CONTENT_H_GAP=3;

	private BeanSaleItem mPosItem;
	private int mItemIndex;

	private PosItemDetailControlPanel mItemDetailPanel;
	private PosItemImageControlPanel mItemImagePanel;
	public static final BeanUIItemListPanelSetting uiItemSetting=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getItemListPanelSetting();

	public PosItemControl() {
		super();

		initControl();
	}

	public BeanSaleItem getItem() {
		return mPosItem;
	}
	public void setItem(BeanSaleItem posItem) {

		this.mPosItem = posItem;
		if(mPosItem!=null){

			
			mItemDetailPanel.setItem(mPosItem);
			mItemImagePanel.setItem(mPosItem);
			if(uiItemSetting.useColoredButton())
				setBackgroundColor();
		 
			
			setVisible(true);
		}
		else
			setVisible(false);
	}

	private void setBackgroundColor(){
		try{
			final String bgColor=getColorCode(mPosItem.getBackground());

			if(bgColor!=null && !bgColor.equals(""))
				this.setBackground(new Color(Integer.parseInt(bgColor,16)));
			this.setBackgroundColor(new Color(Integer.parseInt(bgColor,16)));
		}catch(Exception e){}
	}

	private String getColorCode(String color){
		return  (color.contains("#")?color.substring(1):color).trim();
	}

	@Override
	protected void setLookNFeel() {

		if(!uiItemSetting.useColoredButton())
			super.setLookNFeel();
	};

	public int getItemIndex() {
		return mItemIndex;
	}

	public void setItemIndex(int itemIndex) {
		this.mItemIndex = itemIndex;
	}

	public void initControl() {

		setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
		setOpaque(true);
		setLayout(null);//new FlowLayout(FlowLayout.CENTER, PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP));
		setContainers();

	}

	private void setContainers(){
		mItemDetailPanel = new PosItemDetailControlPanel(this);
		mItemImagePanel = new PosItemImageControlPanel();
		add(mItemDetailPanel);
		add(mItemImagePanel);

	}
 

	@Override
	public void validateComponent() {
		if(mPosItem!=null){
			setText(mPosItem.getName());
		}
	}

	@Override
	protected void onClicked() {
		callListner(mPosItem);
	}

	private void callListner(BeanSaleItem item){
		if(mOnClickListner!=null)
			((IPosItemControlListner)mOnClickListner).onClicked(item);
	}





}
