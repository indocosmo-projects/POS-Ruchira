package com.indocosmo.pos.forms.components.orderentry.itemedit;

import javax.swing.ImageIcon;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanSaleItemAttribute;
import com.indocosmo.pos.forms.components.buttons.PosSelectButton;

@SuppressWarnings("serial")
public final class PosOrderItemAttribControl extends PosSelectButton {
	
	public static final int LAYOUT_HEIGHT=70;
	public static final int LAYOUT_WIDTH=150;
	
	private static final String ITEM_IMAGE="item_attrib_name.png";
	private static final String ITEM_TOUCH_IMAGE="item_attrib_name_touch.png";
	private static final String ITEM_SELECTED_IMAGE="item_attrib_name_touch.png";
	
	private static ImageIcon mImageIconNormal=null;
	private static ImageIcon mImageIconTouched=null;
	private static ImageIcon mImageIconSelected=null;
	
	private BeanSaleItemAttribute mItemAttribute;			
	
	public PosOrderItemAttribControl(){
		
		initControl();
	}
	
	private void initControl() {
		int height=LAYOUT_HEIGHT;
		int width=LAYOUT_WIDTH;
		setSize(width,height);
		setImages();
		
		setTextWrap(true);
		setFont(PosFormUtil.getItemButtonFont());
	}
	
	public void setAttribute(BeanSaleItemAttribute itemAttribute){
		mItemAttribute=itemAttribute;
		if(mItemAttribute!=null){
			setVisible(true);
			setText(mItemAttribute.getArtributeName());
		}
		else
			setVisible(false);
	}
	
	
	private void setImages(){
		loadItemImages();
		setImage(mImageIconNormal);
		setTouchedImage(mImageIconTouched);
		setSelectedImage(mImageIconSelected);
	}
	
	private void loadItemImages(){
		if(mImageIconNormal==null)
			mImageIconNormal=PosResUtil.getImageIconFromResource(
					ITEM_IMAGE);
		if(mImageIconTouched==null)
			mImageIconTouched=PosResUtil.getImageIconFromResource(
					ITEM_TOUCH_IMAGE);
		if(mImageIconSelected==null)
			mImageIconSelected=PosResUtil.getImageIconFromResource(
					ITEM_SELECTED_IMAGE);
	}
	
	@Override
	protected void onSelected() {
//		if(mIsSelected)
			if(mItemAttributeControlListner!=null)
				mItemAttributeControlListner.onSelected(this);
	}
	
	public void setSelectedOption(int index){
		mItemAttribute.setSelectedOptionIndex(index);
	}
	
	public String[] getOptionList(){
		return mItemAttribute.getAttributeOptions();
	}
	
	public BeanSaleItemAttribute getAttribute(){
		return mItemAttribute;
	}
	
	private IPosItemAttributeControlListner mItemAttributeControlListner;
	public void setListner(IPosItemAttributeControlListner listner){
		mItemAttributeControlListner=listner;
	}
	public interface IPosItemAttributeControlListner{
		public void onSelected(PosOrderItemAttribControl item);
	}



}
