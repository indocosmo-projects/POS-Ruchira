/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.indocosmo.pos.common.PosConstants;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosSyncUtil;
import com.indocosmo.pos.data.providers.shopdb.PosDayProcessProvider.PosDayProcess;
import com.indocosmo.pos.process.sync.SynchronizeFromServer;
import com.indocosmo.pos.process.sync.listner.IPosSyncListener;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public final class PosDataSyncForm extends PosBaseForm {

	private static final int PANEL_WIDTH=500;
	private static final int PANEL_HEIGHT=300;
	private static final int PANEL_CONTENT_V_GAP=50;

	private static final int TEXT_FIELD_HEIGHT=30;

	private static final String CHK_BUTTON_NORMAL="checkbox_normal.png";
	private static final String CHK_BUTTON_SELECTED="checkbox_selected.png";

	private JCheckBox mSyncFromServer;
	private JCheckBox mSyncToServer;

	private ActionListener mchkActionListner;

	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosDataSyncForm() {
		super("Data Synchronization", PANEL_WIDTH, PANEL_HEIGHT);

	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		int left = 50;		
		int top = 100;

		initListner();

		mSyncFromServer=new JCheckBox();
		mSyncFromServer.setHorizontalAlignment(JTextField.LEFT);
		mSyncFromServer.setBounds(left, top, 400, TEXT_FIELD_HEIGHT);	
		mSyncFromServer.setFont(PosFormUtil.getTextFieldBoldFont());	
		mSyncFromServer.setText("Receive data from Server");
		mSyncFromServer.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mSyncFromServer.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mSyncFromServer.setSelected(true);
		mSyncFromServer.addActionListener(mchkActionListner);
		panel.add(mSyncFromServer);

		top=mSyncFromServer.getY()+mSyncFromServer.getHeight()+PANEL_CONTENT_V_GAP;

		mSyncToServer=new JCheckBox();
		mSyncToServer.setHorizontalAlignment(JTextField.LEFT);
		mSyncToServer.setBounds(left, top, 400, TEXT_FIELD_HEIGHT);	
		mSyncToServer.setFont(PosFormUtil.getTextFieldBoldFont());	
		mSyncToServer.setText("Send data to Server");
		mSyncToServer.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mSyncToServer.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mSyncToServer.setSelected(true);
		mSyncToServer.addActionListener(mchkActionListner);
		panel.add(mSyncToServer);
	}


	private void initListner(){
		mchkActionListner= new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setOkEnabled(mSyncFromServer.isSelected() || mSyncToServer.isSelected());
			}
		};
	}
	
	
//	private IPosSyncListenerExt syncListener=new IPosSyncListenerExt() {
//		
//		int syncCompleteCount=0;
//		boolean isToSyncActieve=false;
//		boolean isFromSyncActieve=false;
//		
//		/**
//		 * @param isActieve
//		 */
//		public void setToSyncActieve(boolean isActieve){
//			isToSyncActieve=isActieve;
//		}
//		
//		/**
//		 * @param isActieve
//		 */
//		public void setFromSyncActieve(boolean isActieve){
//			isFromSyncActieve=isActieve;
//		}
//		
//		/**
//		 * @return
//		 */
//		private int getActiveSyncCount(){
//			
//			int totalSyncCount=0;
//			
//			if(isToSyncActieve && isFromSyncActieve)
//				totalSyncCount=2;
//			else if(isToSyncActieve || isFromSyncActieve)
//				totalSyncCount=1;
//			else 
//				totalSyncCount=0;
//			
//			return totalSyncCount;
//		}
//		
//		@Override
//		public void onCompleted() {
//			
//			syncCompleteCount++;
//			
//			if(getActiveSyncCount()==syncCompleteCount){
//				
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//				}
//				PosFormUtil.closeBusyWindow();
//				
//				if(mSyncFromServer.isSelected()){
//					PosFormUtil.showInformationMessageBox(PosDataSyncForm.this,
//							"Synchronization from server completed. System needs to be restarted.");
////					System.exit(0);
//				}else{
//					
//					PosFormUtil.showInformationMessageBox(PosDataSyncForm.this,
//							"Synchronization completed.");
//				}
//				
////				PosFormUtil.closeBusyWindow();
//				
//				syncCompleteCount=0;
//			}
//			
//			
//		}
//	};

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		
//		syncListener.setFromSyncActieve(mSyncFromServer.isSelected());
//		syncListener.setToSyncActieve(mSyncToServer.isSelected());
//		
//		SynchronizeFromServer.setListener(new IPosSyncListener() {
//			@Override
//			public void onCompleted() {
//				PosFormUtil.closeBusyWindow();
//				if(mSyncFromServer.isSelected()){
//					PosFormUtil.showInformationMessageBox(PosDataSyncForm.this,
//							"Synchronization from server completed. System needs to be restarted.");
//					System.exit(PosConstants.RESTART_EXIT_CODE);
//				}
//			}
//		});
		
		SwingWorker<Boolean, String> swt = new SwingWorker<Boolean, String>() {
			
			@Override
			protected Boolean doInBackground() throws Exception {

				if(mSyncFromServer.isSelected()) {
					
					SynchronizeFromServer.setListener(new IPosSyncListener() {
						@Override
						public void onCompleted() {
							PosFormUtil.closeBusyWindow();
							PosFormUtil.showInformationMessageBox(null,
									"Synchronization completed. System needs to be restarted.");
							System.exit(PosConstants.RESTART_EXIT_CODE);
						}
					});
				}
				PosSyncUtil.sync(mSyncFromServer.isSelected(), mSyncToServer.isSelected());

				return true;
			}
			
			@Override
			protected void done() {

			
				if(!mSyncFromServer.isSelected()) 
					PosFormUtil.closeBusyWindow();
				
				
//				if(mSyncFromServer.isSelected()){
//					
//					PosFormUtil.showInformationMessageBox(PosDataSyncForm.this,
//							"Synchronization from server completed. System needs to be restarted.");
//					System.exit(PosConstants.RESTART_EXIT_CODE);
//				}

			}
		};
		swt.execute();

		PosFormUtil.showBusyWindow(this,"Synchronizing data. Please wait...");
		
		return false;
	}	
	
	

}
