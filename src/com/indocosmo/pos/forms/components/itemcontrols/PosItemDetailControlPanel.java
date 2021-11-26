/**
 * 
 */
package com.indocosmo.pos.forms.components.itemcontrols;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIItemListPanelSetting;
import com.indocosmo.pos.common.enums.PosItemDisplayStyle;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.data.beans.BeanSaleItem;

/**
 * @author anand
 *
 */
@SuppressWarnings("serial")
public class PosItemDetailControlPanel extends JPanel{

	private static BeanUIItemListPanelSetting uiSettings;
	private static final int ITM_DTLS_LABEL_HEIGHT = 50;

	protected static int PANEL_CONTENT_V_GAP;
	protected static int PANEL_CONTENT_H_GAP;
	protected static int parentCntrlWidth;
	protected static int parentCntrlHeight;
	protected static PosItemDisplayStyle displayMode;
	protected static Font itemDetailFont;
	protected static Font itemNameFont;

	protected static BeanSaleItem posSaleItem;


	private JLabel mLabelItemName;
	private JLabel mLabelItemPrice;
	private JLabel mLabelItemCode;
	private boolean isImgOnlyImgExst;
	public PosItemDetailControlPanel(){

	}

	public PosItemDetailControlPanel(PosItemControl parent){
		super();
		setOpaque(false);
		setLayout(null);
		initVariables(parent);
		intitControl();
	}

	/**
	 * @param parent
	 */
	@SuppressWarnings("static-access")
	private void initVariables(PosItemControl parent) {
	
		PANEL_CONTENT_H_GAP=parent.PANEL_CONTENT_H_GAP;
		PANEL_CONTENT_V_GAP=parent.PANEL_CONTENT_V_GAP;
		parentCntrlHeight=parent.BUTTON_HEIGHT;
		parentCntrlWidth=parent.BUTTON_WIDTH;
		uiSettings=parent.uiItemSetting;
		itemDetailFont=getItemLabelFont().deriveFont((float)uiSettings.getItemDetailFontSize());
		itemNameFont=getItemLabelFont().deriveFont(Font.BOLD, uiSettings.getItemNameFontSize());
		displayMode=uiSettings.getButtonType();
	}

	protected void designPanel(){
		switch(displayMode){
		case IMAGE_ONLY:
			setDimension(parentCntrlWidth-PANEL_CONTENT_H_GAP*2,parentCntrlHeight-PANEL_CONTENT_V_GAP*2);
			break;
		case TEXT_ONLY:
			setDimension(parentCntrlWidth-PANEL_CONTENT_H_GAP*2,parentCntrlHeight-PANEL_CONTENT_V_GAP*2);
			break;
		case BOTH:
			setDimension(parentCntrlWidth-PANEL_CONTENT_H_GAP*2-(parentCntrlHeight-PANEL_CONTENT_V_GAP*2),parentCntrlHeight-PANEL_CONTENT_V_GAP*2);
			break;
		}
	}

	public void setItem(BeanSaleItem item){
		posSaleItem=item;
		if(posSaleItem.getSaleItemImage()==null && displayMode==PosItemDisplayStyle.BOTH)
			changeStyletoTextOnly();
		else
			designPanel();
		poppulateLabels(); 
	}

	private void intitControl() {
		// TODO Auto-generated method stub

		mLabelItemCode=new JLabel();
		mLabelItemCode.setVerticalTextPosition(JLabel.TOP);
		mLabelItemCode.setVerticalAlignment(JLabel.TOP);
		mLabelItemCode.setHorizontalTextPosition(SwingConstants.LEFT);
		mLabelItemCode.setFont(itemDetailFont);
		if(uiSettings.showItemCode())
			add(mLabelItemCode);

		mLabelItemName=new JLabel();
		mLabelItemName.setVerticalTextPosition(SwingConstants.CENTER);
		mLabelItemName.setFont(itemNameFont);
		mLabelItemName.setHorizontalTextPosition(SwingConstants.LEFT);
		add(mLabelItemName);

		mLabelItemPrice=new JLabel("",SwingConstants.RIGHT);
		mLabelItemPrice.setVerticalTextPosition(JLabel.BOTTOM);
		mLabelItemPrice.setVerticalAlignment(JLabel.BOTTOM);
		mLabelItemPrice.setFont(itemDetailFont);
		mLabelItemPrice.setHorizontalTextPosition(SwingConstants.RIGHT);
		if(uiSettings.showItemPrice())
			add(mLabelItemPrice);

	}

	private void setDimension(int width,int height){
		if(displayMode==PosItemDisplayStyle.BOTH&&posSaleItem.getSaleItemImage()!=null)
			width=width-PANEL_CONTENT_H_GAP;
		setSize(width, height);
		setLocation((width<parentCntrlWidth-PANEL_CONTENT_H_GAP*2?
				PANEL_CONTENT_H_GAP*2+height:PANEL_CONTENT_H_GAP),
				PANEL_CONTENT_V_GAP);
		setItemLabels(this.getWidth(), this.getHeight());
	}

	private void setItemLabels(int width,int height){
		
		mLabelItemCode.setSize(width,  height-2*(PANEL_CONTENT_V_GAP));
		mLabelItemCode.setLocation(0,PANEL_CONTENT_V_GAP);
		
//		mLabelItemCode.setOpaque(true);
//		mLabelItemCode.setBackground(Color.RED);

		mLabelItemName.setSize(width,height-2*(PANEL_CONTENT_V_GAP));
		mLabelItemName.setLocation(0, PANEL_CONTENT_V_GAP);
		mLabelItemName.setHorizontalAlignment(JLabel.CENTER);
		
//		mLabelItemName.setOpaque(true);
//		mLabelItemName.setBackground(Color.YELLOW);

		mLabelItemPrice.setSize(width, height-2*(PANEL_CONTENT_V_GAP));
		mLabelItemPrice.setLocation(0,PANEL_CONTENT_V_GAP);
		
//		mLabelItemPrice.setOpaque(true);
//		mLabelItemPrice.setBackground(Color.GREEN);
	}

	private void poppulateLabels(){
		
		resetItemNameLabel();
		if(uiSettings.useColoredButton())
			setItemForeColor();
		setItemCode(posSaleItem.getCode());
//		setItemName(((uiSettings.useAlternativeTitle())?
//				posSaleItem.getAlternativeName():
//					posSaleItem.getName()));
		setItemName(PosSaleItemUtil.getItemNameToDisplay(posSaleItem, uiSettings.useAlternativeTitle()));
		isImgOnlyImgExst=(displayMode==PosItemDisplayStyle.IMAGE_ONLY&&posSaleItem.getSaleItemImage()!=null);
		this.mLabelItemName.setVisible(!isImgOnlyImgExst);
		setItemPrice(posSaleItem.getDisplayPrice()); //To show price with tax
	}

	private void changeStyletoTextOnly(){
		setDimension(parentCntrlWidth-PANEL_CONTENT_H_GAP*2, parentCntrlHeight-PANEL_CONTENT_V_GAP*2);	
	}

	private Font getItemLabelFont(){
		
		return  (uiSettings.showItemCode() || uiSettings.showItemPrice())?
				new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 11):
					new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 14);
	}

	private void setItemName(String name){
		final int displayLenght=32;
		String strippedName=(name.length()<=displayLenght)?name:name.substring(0, displayLenght);
		mLabelItemName.setText("<html><center>"+strippedName+"</center></html>");
	}

	private void setItemCode(String code){
		mLabelItemCode.setText(code);
	}

	private void setItemPrice(double price){
		mLabelItemPrice.setText(PosEnvSettings.getInstance().getCurrencySymbol()+PosCurrencyUtil.format(price));
	}

	private void setItemForeColor(){
		try{
			final String fgColor=getColorCode(posSaleItem.getForeground());

			if(fgColor!=null && !fgColor.equals("")){
				this.setForeground(new Color(Integer.parseInt(fgColor,16)));
				mLabelItemCode.setForeground(new Color(Integer.parseInt(fgColor,16)));
				mLabelItemPrice.setForeground(new Color(Integer.parseInt(fgColor,16)));
				mLabelItemName.setForeground(new Color(Integer.parseInt(fgColor,16)));
			}

		}catch(Exception e){}
	}

	private String getColorCode(String color){
		return  (color.contains("#")?color.substring(1):color).trim();
	}

	private void resetItemNameLabel(){

		mLabelItemCode.setVisible(!posSaleItem.isGroupItem());
		mLabelItemPrice.setVisible(!posSaleItem.isGroupItem());
	}
}
