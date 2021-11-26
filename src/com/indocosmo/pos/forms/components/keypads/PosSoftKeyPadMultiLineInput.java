package com.indocosmo.pos.forms.components.keypads;

import java.awt.Dimension;

@SuppressWarnings("serial")
public final class PosSoftKeyPadMultiLineInput extends PosSoftKeyPadInputBase {
	

	private final static int TEXT_AREA_ROW=3;
	private final static int TEXT_AREA_HEIGHT_MULTI=TEXT_AREA_HEIGHT_SINGLE*TEXT_AREA_ROW;
	public final static int LAYOUT_HEIGHT=TEXT_AREA_HEIGHT_MULTI+PosSoftKeyPad.LAYOUT_HEIGHT+PANEL_CONTENT_V_GAP*3;
	public final static int LAYOUT_WIDTH=PosSoftKeyPad.LAYOUT_WIDTH+PANEL_CONTENT_H_GAP*2;

	public PosSoftKeyPadMultiLineInput(){
		super(TEXT_AREA_ROW);
	}

	@Override
	protected void initTextArea() {
		super.initTextArea();
		mTextArea.setLineWrap(true);
		mTextArea.setPreferredSize(new Dimension(TEXT_AREA_WIDTH,TEXT_AREA_HEIGHT_MULTI));
		
	}

}
