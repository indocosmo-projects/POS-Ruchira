package com.indocosmo.pos.forms;

import java.awt.event.WindowEvent;

import com.indocosmo.pos.forms.components.terminal.shift.PosShiftBasePanel;
import com.indocosmo.pos.forms.components.terminal.shift.PosShiftOpenPanel;

@SuppressWarnings("serial")
public  class PosShiftOpeningForm extends PosShiftBaseForm {

	private static final int CONTENT_PANEL_HEIGHT=PosShiftOpenPanel.LAYOUT_HEIGHT;
	private static final int CONTENT_PANEL_WIDTH=PosShiftOpenPanel.LAYOUT_WIDTH;
	private PosShiftOpenPanel mShiftPanel;

	public PosShiftOpeningForm() {
		super("Open Shift",CONTENT_PANEL_WIDTH,CONTENT_PANEL_HEIGHT);
		setOkEnabled(false);
	}

	public PosShiftOpeningForm(String title) {
		super(title,CONTENT_PANEL_WIDTH,CONTENT_PANEL_HEIGHT);
		setOkEnabled(false);
	}

	@Override
	protected PosShiftBasePanel getContentPanel() {
		mShiftPanel=new PosShiftOpenPanel(this);
		return mShiftPanel;
	}

	public void setCashierCard(String cardNumber){
		mShiftPanel.setCashierCard(cardNumber);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onWindowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	protected void onWindowActivated(WindowEvent e) {
		mShiftPanel.setDefaultFocus();
	}
}
