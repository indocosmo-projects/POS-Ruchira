/**
 * 
 */
package com.indocosmo.pos.forms;

import javax.swing.RootPaneContainer;
import javax.swing.SwingWorker;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.messageboxes.PosWaitMessageBoxForm;

/**
 * @author jojesh
 *
 */
public final class PosProcessRunnerForm extends PosWaitMessageBoxForm {

	/**
	 * 
	 */
	private Runnable mBgProcess;
	private RootPaneContainer mParent;
	private SwingWorker<Boolean, String> mWorker;
	public PosProcessRunnerForm() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public PosProcessRunnerForm(RootPaneContainer parent, String message) {
		super(message);
		mParent=parent;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param string
	 */
	public PosProcessRunnerForm(String message) {
		super(message);
	}

	public void startProcess(Runnable runnable){
		mBgProcess=runnable;
		
		mWorker=new SwingWorker<Boolean, String>(){

			@Override
			protected Boolean doInBackground() throws Exception {
				mBgProcess.run();
				return true;
			}
			/* (non-Javadoc)
			 * @see javax.swing.SwingWorker#done()
			 */
			@Override
			protected void done() {
				finalizeRunnerForm();
				super.done();
			}
		};
//		mWorker.execute();
		setRunnerForm();
	}
	
	private void setRunnerForm(){
		PosFormUtil.showLightBoxModal(null, this);
	}
	
	private void finalizeRunnerForm(){
		PosProcessRunnerForm.this.setVisible(false);
		PosProcessRunnerForm.this.dispose();
	}

}
