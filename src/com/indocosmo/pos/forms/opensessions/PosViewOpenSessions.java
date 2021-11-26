/**
 * 
 */
package com.indocosmo.pos.forms.opensessions;

import java.util.ArrayList;

import javax.swing.JPanel;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanLoginSessions;
import com.indocosmo.pos.data.providers.PosLoginSessionsProvider;
import com.indocosmo.pos.forms.PosBaseForm;
import com.indocosmo.pos.forms.opensessions.components.OpenSessionsTablePanel;
import com.indocosmo.pos.forms.orderlistquery.components.listeners.IOrderListQueryTablePanelListener;

/**
 * @author sandhya
 *
 */
public class PosViewOpenSessions extends PosBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PANEL_WIDTH = 825;
	private static final int PANEL_HEIGHT = 400;
	private static final int PANEL_CONTENT_V_GAP = 2;
	private static final int PANEL_CONTENT_H_GAP = 2;

	private OpenSessionsTablePanel openSessionListPanel;
	private ArrayList<BeanLoginSessions> mOpenSessionsList;
	private JPanel mContianerPanel;

	public PosViewOpenSessions() {
		super("Open Sessions", PANEL_WIDTH, PANEL_HEIGHT);

		setOkButtonCaption("Refresh");
		setCancelButtonCaption("Close");
		createOrderStatusReportList();

	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		
		mContianerPanel = panel;
		mContianerPanel.setLayout(null);

	}
	/**
	 * 
	 */
	private void createOrderStatusReportList(){
		
		int width = PANEL_WIDTH - PANEL_CONTENT_H_GAP * 2;
		int height = PANEL_HEIGHT - PANEL_CONTENT_V_GAP*2;
		int top =PANEL_CONTENT_V_GAP ;
		int left =PANEL_CONTENT_H_GAP;

		openSessionListPanel =new  OpenSessionsTablePanel(mContianerPanel ,width ,height );
		openSessionListPanel.setLocation(left ,top);
		openSessionListPanel.setListener(new IOrderListQueryTablePanelListener() {

			@Override
			public void onSelectionChanged(int index) {
				// TODO Auto-generated method stub

			}
		} );
		FillOpenSessionList();
	}
	/**
	 * 
	 */

	private void FillOpenSessionList()
	{
		try {

			PosLoginSessionsProvider sessionProvider=new PosLoginSessionsProvider();
			mOpenSessionsList = null;
			mOpenSessionsList = sessionProvider.getAllOpenSessions(PosEnvSettings.getInstance().getTillOpenCashierShiftInfo().getID());
			openSessionListPanel.SetOpenSessionList(mOpenSessionsList );
			mContianerPanel.add(openSessionListPanel);
		} catch (Exception e) {

			PosLog.write(this, "View open sessions.onclick", e);
			PosFormUtil.showErrorMessageBox(PosViewOpenSessions.this, e.getMessage());
		}
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		
		FillOpenSessionList();
		
		return false;
	}

}
