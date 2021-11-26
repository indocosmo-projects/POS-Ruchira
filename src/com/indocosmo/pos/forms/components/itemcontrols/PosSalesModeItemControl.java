package com.indocosmo.pos.forms.components.itemcontrols;

import javax.swing.ImageIcon;

import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanSalesMode;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.itemcontrols.listners.IPosSalesModeItemControlListner;

@SuppressWarnings("serial")
public final class PosSalesModeItemControl extends PosButton{
	
	public static final int BUTTON_HEIGHT=87;
	public static final int BUTTON_WIDTH=90;
	
	private static final String IMAGE_BUTTON_NORMAL="dlg_buton.png";
	private static final String IMAGE_BUTTON_TOUCHED="dlg_buton_touch.png";
		
	private IPosSalesModeItemControlListner mItemListner;	

	private static ImageIcon mImageIconNormal=null;
	private static ImageIcon mImageIconTouched=null;
	
	private BeanSalesMode mSalesModeItem;
	
	public PosSalesModeItemControl() {
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
	}
	
	private void loadItemImages(){
		if(mImageIconNormal==null)
			mImageIconNormal=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_NORMAL);
		if(mImageIconTouched==null)
			mImageIconTouched=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_TOUCHED);
	}
	
	public BeanSalesMode getSalesModeItem() {
		return mSalesModeItem;
	}

	public void setSalesModeItem(BeanSalesMode salesModeItem) {
		this.mSalesModeItem = salesModeItem;
		setText(mSalesModeItem.getName());
	}
		

	public void setItemSelectedListner(IPosSalesModeItemControlListner itemListner) {
		this.mItemListner = itemListner;
	}

	@Override
	protected void onClicked() {
		super.onClicked();
		if(mItemListner!=null)
			mItemListner.onSelected(mSalesModeItem);
	}

	

}
