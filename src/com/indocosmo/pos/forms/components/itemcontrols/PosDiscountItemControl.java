/**
 * 
 */
package com.indocosmo.pos.forms.components.itemcontrols;

import javax.swing.ImageIcon;

import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.forms.components.buttons.PosSelectButton;

/**
 * @author deepak
 *
 */
public class PosDiscountItemControl extends PosSelectButton{

	private static final String IMAGE_BUTTON_NORMAL="company_pay.png";
	private static final String IMAGE_BUTTON_TOUCHED="company_pay_touch.png";
	private static final String IMAGE_BUTTON_SELECTED="company_pay_selected.png";
	public static final int BUTTON_WIDTH=150;
	public static final int BUTTON_HEIGHT=81;	
	
	private static ImageIcon mImageIconNormal=null;
	private static ImageIcon mImageIconTouched=null;
	private static ImageIcon mImageIconSelected=null;
	
	private BeanDiscount mDiscountItem;	

	public PosDiscountItemControl() {
		initControl() ;
	}
	
	private void initControl() {
		int height=BUTTON_HEIGHT;
		int width=BUTTON_WIDTH;
		setSize(width,height);
		setImages();
		setTextWrap(true);
	}
	
	private void setImages(){
		loadItemImages();
		setImage(mImageIconNormal);
		setTouchedImage(mImageIconTouched);
		setSelectedImage(mImageIconSelected);
	}
	
	private void loadItemImages(){
		if(mImageIconNormal==null)
			mImageIconNormal=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_NORMAL);
		if(mImageIconTouched==null)
			mImageIconTouched=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_TOUCHED);
		if(mImageIconSelected==null)
			mImageIconSelected=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SELECTED);
	}
	
	public BeanDiscount  getDiscountItem() {
		return mDiscountItem;
	}

	public void setDiscountItem(BeanDiscount mDiscountItem) {
		this.mDiscountItem = mDiscountItem;
		setText(mDiscountItem.getName());
	}
}
