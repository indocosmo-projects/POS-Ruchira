package com.indocosmo.pos.forms.components.objectbrowser;

import javax.swing.ImageIcon;

import com.indocosmo.pos.common.utilities.PosResUtil;


@SuppressWarnings("serial")
public final class PosObjectBrowserItemNormal extends PosObjectBrowserItemControlBase {
	
	public static final int LAYOUT_HEIGHT=87;
	public static final int LAYOUT_WIDTH=90;
	
	private static final String IMAGE_BUTTON_NORMAL="browse_normal_item_normal.png";
	private static final String IMAGE_BUTTON_TOUCHED="browse_normal_item_touch.png";

	private static ImageIcon mImageIconNormal=null;
	private static ImageIcon mImageIconTouched=null;
	
	public PosObjectBrowserItemNormal() {
		super();
	}
	
	@Override
	protected String getNormalImagePath() {
		return IMAGE_BUTTON_NORMAL;
	}

	@Override
	protected String getTouchImagePath() {
		return IMAGE_BUTTON_TOUCHED;
	}
	
	@Override
	protected int getControlHeight() {
		return LAYOUT_HEIGHT;
	}
	
	@Override
	protected int getControlWidth() {
		return  LAYOUT_WIDTH;
	}
	
	@Override
	protected ImageIcon getNormalImage() {
		if(mImageIconNormal==null)
			mImageIconNormal=PosResUtil.getImageIconFromResource(getNormalImagePath());
		return mImageIconNormal;
	}

	@Override
	protected ImageIcon getTouchImage() {
		if(mImageIconTouched==null)
			mImageIconTouched=PosResUtil.getImageIconFromResource(getTouchImagePath());
		return mImageIconTouched;
	}


}
