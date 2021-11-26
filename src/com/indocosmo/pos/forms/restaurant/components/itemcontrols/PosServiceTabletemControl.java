package com.indocosmo.pos.forms.restaurant.components.itemcontrols;

import java.awt.Color;

import javax.swing.ImageIcon;

import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.forms.components.buttons.PosSelectButton;

@SuppressWarnings("serial")
public  class PosServiceTabletemControl extends PosSelectButton{	
	
	private static final String IMAGE_BUTTON_NORMAL="service_table.png";
	private static final String IMAGE_BUTTON_TOUCHED="service_table_touch.png";
	private static final String IMAGE_BUTTON_SELECTED="service_table_touch.png";
	public static final int LAYOUT_WIDTH=150;
	public static final int LAYOUT_HEIGHT=60;	
	
	protected static ImageIcon mImageIconNormal=null;
	protected static ImageIcon mImageIconTouched=null;
	protected static ImageIcon mImageIconSelected=null;
	
	protected BeanServingTable mServiceTableItem;	
//	private boolean isUsed=false;
		
	public PosServiceTabletemControl() {
		initControl() ;
	}
	
	
	private void initControl() {
		int height=LAYOUT_HEIGHT;
		int width=LAYOUT_WIDTH;
		setSize(width,height);
		loadItemImages();
		setImages();
		setTextWrap(true);
		setSelectedTextColor(Color.WHITE);
	}
	
//	public void isUsed(boolean isused){
//		this.isUsed=isused;
//	}
//	
	protected void setImages(){
		setImage(getNormalImage());
		setTouchedImage(mImageIconTouched);
		setSelectedImage(mImageIconSelected);
	}
	
	protected ImageIcon getNormalImage(){
		return mImageIconNormal;
	}
	
	protected void loadItemImages(){
		if(mImageIconNormal==null)
			mImageIconNormal=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_NORMAL);
		if(mImageIconTouched==null)
			mImageIconTouched=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_TOUCHED);
		if(mImageIconSelected==null)
			mImageIconSelected=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SELECTED);
	}
	
	public BeanServingTable  getServiceTable() {
		return mServiceTableItem;
	}

	public void setServiceTable(BeanServingTable item) {
		
		this.mServiceTableItem = item;
		if(mServiceTableItem!=null){
			setText(item.getDisplayText());
			setVisible(true);
		}
		else{
			setText("");
			setVisible(false);
		}
		
		setImages();
	}
	

}
