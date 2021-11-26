package com.indocosmo.pos.forms.components.objectbrowser;

import javax.swing.ImageIcon;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.objectbrowser.listners.IPosObjectBrowserItemListner;

@SuppressWarnings("serial")
public abstract class PosObjectBrowserItemControlBase extends PosButton implements IPosObjectBrowserControl {

	protected IPosObjectBrowserItemListner mItemListner;	
	protected IPosBrowsableItem mItem;
	
	public PosObjectBrowserItemControlBase(){
		initControl();
	}
	
	protected void initControl() {
		setSize(getControlWidth(),getControlHeight());
		setImages();
		setTextWrap(true);
		setFont(PosFormUtil.getObjectBrowserItemFont());
		
	}
	
	protected void setImages(){
		setImage(getNormalImage());
		setTouchedImage(getTouchImage());
	}

	public IPosBrowsableItem getItem() {
		return mItem;
	}

	public void setItem(IPosBrowsableItem item) {
		this.mItem = item;
		setText(mItem.getDisplayText());
	}

	public void setListner(IPosObjectBrowserItemListner listner) {
		this.mItemListner = listner;
	}

	@Override
	protected void onClicked() {
		super.onClicked();
		if(mItemListner!=null)
			mItemListner.onSelected(mItem);
	}
	
	protected abstract String getNormalImagePath();
	protected abstract String getTouchImagePath();
	protected abstract int getControlHeight();
	protected abstract int getControlWidth();
	protected abstract ImageIcon getNormalImage();
	protected abstract ImageIcon getTouchImage();
	

}


