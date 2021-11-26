package com.indocosmo.pos.forms;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosAccessPermissionsUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.terminal.shift.PosShiftBasePanel;
import com.indocosmo.pos.forms.components.terminal.shift.PosShiftClosePanel;

@SuppressWarnings("serial")
public  class PosShiftClosingForm extends PosShiftBaseForm { 
	
	private static   int CONTENT_PANEL_HEIGHT=(PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()?
			PosShiftClosePanel.LAYOUT_HEIGHT_SUMMARY  :PosShiftClosePanel.LAYOUT_HEIGHT_DET);
	
	private static   int CONTENT_PANEL_WIDTH=(PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()?
			PosShiftClosePanel.LAYOUT_WIDTH_SUMMARY  :PosShiftClosePanel.LAYOUT_WIDTH_DET );
	 	
	private PosShiftClosePanel shiftClosingPanel;
	 	

	public PosShiftClosingForm() {
		
		 super("Close Shift",CONTENT_PANEL_WIDTH,CONTENT_PANEL_HEIGHT);
	}

	public PosShiftClosingForm(String title) {
		super(title,CONTENT_PANEL_WIDTH,CONTENT_PANEL_HEIGHT);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#initControls()
	 */
	@Override
	protected void initControls() {
		
		super.initControls();
		addButtonsToBottomPanel("Print", new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
			
				if (PosAccessPermissionsUtil.validateAccess(PosShiftClosingForm.this, getActiveUser().getUserGroupId(),"till_summary_report") ){
					
					shiftClosingPanel.printSummaryReport(false);
				}else if (PosAccessPermissionsUtil.validateAccess(PosShiftClosingForm.this, false,"till_summary_report")!=null ){
					shiftClosingPanel.printSummaryReport(false);
				}
				
			}
		},0)	;
	}
	
	

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosShiftBaseForm#getContentPanel()
	 */
	@Override
	protected PosShiftBasePanel getContentPanel() {
		
		shiftClosingPanel=new PosShiftClosePanel(this);
		return shiftClosingPanel;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosShiftBaseForm#onOkButtonClicked()
	 */
}
