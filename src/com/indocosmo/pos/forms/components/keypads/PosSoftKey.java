package com.indocosmo.pos.forms.components.keypads;

import java.awt.Dimension;

import javax.swing.ImageIcon;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;

@SuppressWarnings("serial")
public final class PosSoftKey extends PosButton {
	
	public final static int KEY_HEIGHT=56;
	public final static int KEY_WIDTH=63;
	
	private final static String SOFT_KEY_IMAGE="softkb_disp_key.png";
	private final static String SOFT_KEY_IMAGE_TOUCH="softkb_disp_key_touch.png";

	private static ImageIcon mKeyImage=null;
	private static ImageIcon mKeyImageTouch=null;
	
	public PosSoftKey() {
		super();
		initControl();
	}

	public PosSoftKey(String text) {
		super(text);
	
		initControl();
	}
	
	private void initControl(){
		setPreferredSize(new Dimension(KEY_WIDTH,KEY_HEIGHT));
//		setBackground(Color.GRAY);
//		setOpaque(true);
		initImages();
		setImage(mKeyImage);
		setTouchedImage(mKeyImageTouch);
		setFont(PosFormUtil.getButtonBoldFont().deriveFont(10));
		setAutoMnemonicEnabled(false);
	}
	
	private void initImages(){
		if(mKeyImage==null)
			mKeyImage=PosResUtil.getImageIconFromResource(SOFT_KEY_IMAGE);
		if(mKeyImageTouch==null)
			mKeyImageTouch=PosResUtil.getImageIconFromResource(SOFT_KEY_IMAGE_TOUCH);
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		setVisible(!text.equals(" "));
	}
}
