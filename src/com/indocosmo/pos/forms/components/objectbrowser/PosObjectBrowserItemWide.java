package com.indocosmo.pos.forms.components.objectbrowser;

import javax.swing.ImageIcon;

import com.indocosmo.pos.common.utilities.PosResUtil;

@SuppressWarnings("serial")
public class PosObjectBrowserItemWide extends PosObjectBrowserItemControlBase {
	
	private static final String IMAGE_BUTTON_NORMAL="browse_wider_item_normal.png";
	private static final String IMAGE_BUTTON_TOUCHED="browse_wider_item_touch.png";
	
	public static final int LAYOUT_WIDTH=150;
	public static final int LAYOUT_HEIGHT=87;
	
	private static ImageIcon mImageIconNormal=null;
	private static ImageIcon mImageIconTouched=null;

	public PosObjectBrowserItemWide() {
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
