package com.indocosmo.pos.forms.components.keypads;

import java.awt.Dimension;

import javax.swing.ImageIcon;

import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;

@SuppressWarnings("serial")
public final class PosVKeyPadButton extends PosButton {

	public static final int LAYOUT_HEIGHT=50;
	public static final int LAYOUT_WIDTH=50;

	private static final String NORMAL_IMAGE="vkb_key.png";
	private static final String THOUCH_IMAGE="vkb_key_touch.png";
	
	private static final String MULTI_NORMAL_IMAGE="vkb_key_multi.png";
	private static final String MULTI_THOUCH_IMAGE="vkb_key_multi_touch.png";

	private static ImageIcon mTouchImage, mNormalImage;
	private static ImageIcon mMultiTouchImage, mMultiNormalImage;

	public PosVKeyPadButton(){
		initComponent();
	}

	private void initComponent(){
		loadImages();
		setPreferredSize(new Dimension(LAYOUT_WIDTH,LAYOUT_HEIGHT));
		
	}
	
	private void setImages(boolean isMulti){
		if(isMulti){
			setImage(mMultiNormalImage);
			setTouchedImage(mMultiTouchImage);
		}else{
			setImage(mNormalImage);
			setTouchedImage(mTouchImage);
		}
	}

	private void loadImages(){
		if(mTouchImage==null)
			mTouchImage=PosResUtil.getImageIconFromResource(THOUCH_IMAGE);
		if(mNormalImage==null)
			mNormalImage=PosResUtil.getImageIconFromResource(NORMAL_IMAGE);
		if(mMultiTouchImage==null)
			mMultiTouchImage=PosResUtil.getImageIconFromResource(MULTI_THOUCH_IMAGE);
		if(mMultiNormalImage==null)
			mMultiNormalImage=PosResUtil.getImageIconFromResource(MULTI_NORMAL_IMAGE);
	}	
	
	@Override
	public void setText(String text) {
		super.setText(text);
		setImages((text=="--"));		
	}

}
