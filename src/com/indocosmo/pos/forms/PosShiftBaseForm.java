package com.indocosmo.pos.forms;

import javax.swing.JPanel;

import com.indocosmo.pos.forms.components.terminal.shift.PosShiftBasePanel;
import com.indocosmo.pos.forms.listners.adapters.PosShiftFormAdapter;


@SuppressWarnings("serial")
public abstract class PosShiftBaseForm extends PosBaseForm {
	
	private PosShiftBasePanel mShiftPanel;
	
	public PosShiftBaseForm(String title, int cPanelWidth, int cPanelHeight) {
		super(title,  cPanelWidth,  cPanelHeight);
	}
	
	@Override
	public boolean onOkButtonClicked() {
		
		if(mShiftPanel.onValidate())
			if( mShiftPanel.onSave()){
				if(mListner!=null)
					((PosShiftFormAdapter)mListner).onOkClicked(mShiftPanel.getCashierShiftDetails());
				return true;
			}
		else
			mShiftPanel.onGotFocus();
		return false;
	}
	
	@Override
	protected void setContentPanel(JPanel panel) {
		
		mShiftPanel=getContentPanel();
		mShiftPanel.setLocation(0, 0);
		panel.add(mShiftPanel);
	}
	
	public void hasPosCard(boolean has){
		
		mShiftPanel.hasPosCard(has);
	}
	 
	protected abstract PosShiftBasePanel getContentPanel();
}
