/**
 * 
 */
package com.indocosmo.pos.forms.components.keypads;

import java.awt.Dimension;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public class PosSoftKeyPadSingleLineInput extends PosSoftKeyPadInputBase {
	
	private final static int TEXT_AREA_ROW=1;
	private final static int TEXT_AREA_HEIGHT_MULTI=TEXT_AREA_HEIGHT_SINGLE*TEXT_AREA_ROW;
	public final static int LAYOUT_HEIGHT=TEXT_AREA_HEIGHT_MULTI+PosSoftKeyPad.LAYOUT_HEIGHT+PANEL_CONTENT_V_GAP*3;
	public final static int LAYOUT_WIDTH=PosSoftKeyPad.LAYOUT_WIDTH+PANEL_CONTENT_H_GAP*2;


	public PosSoftKeyPadSingleLineInput(){
		super(TEXT_AREA_ROW);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.keypads.PosSoftKeyPadInputBase#initTextArea()
	 */
	@Override
	protected void initTextArea() {

		super.initTextArea();

		mTextArea.setLineWrap(false);
		mTextArea.setPreferredSize(new Dimension(TEXT_AREA_WIDTH,TEXT_AREA_HEIGHT_MULTI));
		

	}

}
