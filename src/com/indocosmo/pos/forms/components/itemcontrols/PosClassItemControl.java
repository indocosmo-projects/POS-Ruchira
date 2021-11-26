package com.indocosmo.pos.forms.components.itemcontrols;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIClassItemListPanelSetting;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanItemClassBase;
import com.indocosmo.pos.forms.components.buttons.PosSelectButton;

@SuppressWarnings("serial")
public final class PosClassItemControl extends PosSelectButton {

	
	public static final BeanUIClassItemListPanelSetting uiSetting = PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getClassItemListPanelSetting();
	public static final int CLASS_ITEM_BTN_HEIGHT=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getClassItemListPanelSetting().getButtonHeight();
	public static final int CLASS_ITEM_BTN_WIDTH=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getClassItemListPanelSetting().getButtonWidth();
	public static final int CLASS_ITEM_BTN_FONT_SIZE=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getClassItemListPanelSetting().getClassButtonTextFontSize();
	public static final int PANEL_CONTENT_H_GAP=6;
	public static final int PANEL_CONTENT_V_GAP=6;
	
	private BeanItemClassBase mClassItem;
	private PosSubClassDetailPanel mClassDetails;
	private boolean mIsMainClassItem;

	public PosClassItemControl(Boolean isMainClassItem) {
		mIsMainClassItem=isMainClassItem;

		initControl();
	}

	public PosClassItemControl(String text) {
		super(text);
		setText(text);
		initControl();

	}

	public PosClassItemControl(Boolean isMainClassItem,String text) {
		super(text);
		mIsMainClassItem=isMainClassItem;
		initControl();
	}

	@Override
	public void setSelected(boolean isSelected) {
		if(mIsSelected==isSelected) return;
		mIsSelected = isSelected;
		mClassDetails.setForeground(((mIsSelected)?mSelectedTextColor:mNormalTextColor));
		setBackground(((mIsSelected)?mSelectedBGColor:normalBGColor));
		repaint();
		onSelected();
	}

	private void initControl() {

		int height=CLASS_ITEM_BTN_HEIGHT;
		int width=CLASS_ITEM_BTN_WIDTH;
		setSize(width,height);
		setVisible(false);
		setOpaque(true);
		mClassDetails = new PosSubClassDetailPanel(this);
		Font itemClassFont= PosFormUtil.getItemButtonFont().deriveFont(CLASS_ITEM_BTN_FONT_SIZE);
	 	setFont(itemClassFont);
		add(mClassDetails);
	}

	public BeanItemClassBase getClassItem() {
		return mClassItem;
	}

	public void setClassItem(BeanItemClassBase classItem) {

		this.mClassItem = classItem;
		if(mClassItem!=null){
			mClassDetails.setItemClass(mClassItem);
			if(mClassItem.getFgColor()!=null){
				mNormalTextColor=Color.decode(mClassItem.getFgColor());
				mSelectedTextColor=mNormalTextColor.brighter();
				mClassDetails.setForeground(mNormalTextColor);
			}

			if(mClassItem.getBgColor()!=null){
				normalBGColor=Color.decode(mClassItem.getBgColor()).darker();
				mSelectedBGColor=normalBGColor.darker().darker();
				setBackgroundColor(normalBGColor);
			}
			setVisible(true);
		}
		else
			setVisible(false);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
	
		super.paint(g);
		
		if(isSelected()){
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.CYAN);
			g2.setStroke(new BasicStroke(PANEL_CONTENT_H_GAP));
			g2.draw(new Rectangle(0, 0, this.getWidth(), this.getHeight()) );
		}
		
	}
	

}
