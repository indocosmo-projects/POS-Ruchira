/**
 * 
 */
package com.indocosmo.pos.forms.components.itemcontrols;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIClassItemListPanelSetting;
import com.indocosmo.pos.common.enums.ImageButtonTextPosition;
import com.indocosmo.pos.common.enums.PosItemDisplayStyle;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.data.beans.BeanItemClassBase;

/**
 * @author anand
 *
 */
@SuppressWarnings("serial")
public class PosSubClassDetailPanel extends JPanel{

	private  int containerHeight;
	private  int containerWidth;
	private  int panelContent_H_Gap;
	private  int panelContent_V_Gap;
	private  PosItemDisplayStyle displayMode;
	private  int minDimension;
	private static BeanUIClassItemListPanelSetting uiSettings;
	private static Font nameFont;

	private PosClassItemImageControl classImagePanel;

	private JLabel mName;
	private BufferedImage mImage;
	private BeanItemClassBase mItem;
	private String itemName;
	private boolean disableWrapping=false;


	public PosSubClassDetailPanel(PosClassItemControl parent){
		super();
		initVars(parent);
		initControl();
	}

	/**
	 * @param parent
	 */
	@SuppressWarnings("static-access")
	private void initVars(PosClassItemControl parent) {
		containerHeight=parent.CLASS_ITEM_BTN_HEIGHT;
		containerWidth=parent.CLASS_ITEM_BTN_WIDTH;
		panelContent_H_Gap=parent.PANEL_CONTENT_H_GAP;
		panelContent_V_Gap=parent.PANEL_CONTENT_V_GAP;
		uiSettings=parent.uiSetting;
		nameFont=new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(),Font.BOLD,uiSettings.getClassButtonTextFontSize()<13?13:uiSettings.getClassButtonTextFontSize());
		displayMode=uiSettings.getButtonType();

	}

	private void setClassImage(BufferedImage image){
		mImage=image;
	}
	public void setItemClass(BeanItemClassBase item)throws NullPointerException{
		mItem=item;
		setClassImage(mItem.getImage());

		classImagePanel.setVisible(false);
		mName.setVisible(false);
		setContainers();
		if(mImage==null){

			mName.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
			setClassItemLabelText(false);

		}
		else if(uiSettings.getButtonType()==PosItemDisplayStyle.BOTH){
			setClassItemLabelText(classImagePanel.drawClassImage(mImage));
		}
		else if(uiSettings.getButtonType()==PosItemDisplayStyle.IMAGE_ONLY){
			setClassItemLabelText(classImagePanel.drawClassImage(mImage));
		}
		else{
			setClassItemLabelText(false);
		}


	}

	public BeanItemClassBase getItem(){
		return mItem;
	}
	private void setItemName(String name){
		this.itemName = name;
	}

	private void setClassItemLabelText(boolean isImageDrawn){
		
//		setItemName((uiSettings.useAlternativeTitle()?
//				mItem.getAlternativeName():
//					mItem.getName()));
		
		setItemName(PosSaleItemUtil.getItemClassNameToDisplay(mItem, uiSettings.useAlternativeTitle()));
		
		mName.setHorizontalAlignment(JLabel.CENTER);
		mName.setText("<html><center>"+itemName+"</center></html>");
		if(displayMode==PosItemDisplayStyle.BOTH)
			if(!isImageDrawn||uiSettings.getClassItemButtonTextPosition()==ImageButtonTextPosition.UNDER_IMAGE){
				if(disableWrapping && isImageDrawn)
					mName.setText(itemName);
				mName.setHorizontalAlignment(JLabel.CENTER);
			}else
				mName.setHorizontalAlignment(JLabel.LEFT);

		if(isImageDrawn){
			mName.setVisible(false);
			classImagePanel.setVisible(true);
			if(uiSettings.getButtonType()==PosItemDisplayStyle.BOTH)
				mName.setVisible(true);
			else
				mName.setVisible(false);
		}
		else{
			classImagePanel.setVisible(false);
			mName.setVisible(true);
			mName.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
		}
	}

	private void initControl() {

		setSize(containerWidth-panelContent_H_Gap*2,containerHeight-panelContent_V_Gap*2);
		setLocation(panelContent_H_Gap, panelContent_V_Gap);
		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		minDimension = Math.min(this.getWidth(),this.getHeight());

		classImagePanel = new PosClassItemImageControl();
		classImagePanel.setOpaque(false);

		mName = new JLabel();
		mName.setOpaque(false);
		mName.setFont(nameFont);
		mName.setForeground(Color.WHITE);

		add(classImagePanel);
		add(mName);

	}

	private void setContainers(){		

		switch(displayMode){
		case IMAGE_ONLY:
			setContainersDimension(this.getWidth(),this.getHeight(),this.getWidth(),this.getHeight());
			break;
		case TEXT_ONLY:
			setContainersDimension(0, 0,this.getWidth(),this.getHeight());
			break;
		case BOTH:
			if(uiSettings.getClassItemButtonTextPosition()==ImageButtonTextPosition.ALONGSIDE_IMAGE)
				setContainersDimension(minDimension, minDimension, this.getWidth()-this.getHeight(), this.getHeight());
			else if(uiSettings.getClassItemButtonTextPosition()==ImageButtonTextPosition.UNDER_IMAGE){
				int paddingHeight=4;
				final int singleLineHeight=(int)PosFormUtil.getFontTextHeight("Gg", this)+paddingHeight*2;
				if(this.getHeight()+panelContent_V_Gap*2<=this.getWidth()+panelContent_H_Gap*2){
					disableWrapping=true;
					setContainersDimension(minDimension-singleLineHeight,minDimension-singleLineHeight, this.getWidth(),singleLineHeight);
				}
				else
					setContainersDimension(minDimension,minDimension, this.getWidth(),this.getHeight()-this.getWidth());
			}
			break;
		}
	}
	private void setContainersDimension(int imagePanelWidth,int imagePanelHight,int nameWidth,int nameHight){

		classImagePanel.setPreferredSize(new Dimension(imagePanelWidth,imagePanelHight));
		mName.setPreferredSize(new Dimension(nameWidth,nameHight));
	}
	public void setForeground(Color color) {

		if(mName!=null & color!=null)
			mName.setForeground(color);
	}
}
