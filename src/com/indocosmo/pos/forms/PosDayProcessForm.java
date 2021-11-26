/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.indocosmo.backuputil.PosBackup;
import com.indocosmo.pos.PosApplication;
import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosEnvSettings.ApplicationType;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.EnablePrintingOption;
import com.indocosmo.pos.common.enums.PosQueueNoResetPolicy;
import com.indocosmo.pos.common.utilities.HmsUtil;
import com.indocosmo.pos.common.utilities.PosAccessPermissionsUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosSyncUtil;
import com.indocosmo.pos.data.beans.BeanDayProcess;
import com.indocosmo.pos.data.beans.BeanPMSOrderPayment;
import com.indocosmo.pos.data.beans.BeanShop;
import com.indocosmo.pos.data.beans.BeanShopShift;
import com.indocosmo.pos.data.beans.BeanUser;
import com.indocosmo.pos.data.providers.PosLoginSessionsProvider;
import com.indocosmo.pos.data.providers.shopdb.PosAccessLogProvider;
import com.indocosmo.pos.data.providers.shopdb.PosAttendProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCashOutProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCashierShiftProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDayProcessProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShopProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDayProcessProvider.PosDayProcess;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderPaymentsProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderQueueProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShiftSummaryProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShopShiftProvider;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter;
import com.indocosmo.pos.reports.shift.PosShiftReport;

/**
 * @author deepak
 * @since 10.10.2012
 * 
 */
/**
 * @author deepak
 *
 */
public final class PosDayProcessForm extends PosBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PANEL_WIDTH = 524;
	private static final int PANEL_HEIGHT = 260;
	private static final int PANEL_CONTENT_V_GAP = 3;
	private static final int PANEL_CONTENT_H_GAP = 10;
	private static final int LABEL_WIDTH = 120;
	private static final int LABEL_HEIGHT = 20;
	private static final int LABEL_CAPTION_WIDTH = 95;
	private static final int LABEL_CAPTION_HEIGHT = 40;

	private static final int TEXT_FIELD_HEIGHT = 40;
	private static final int TEXT_FIELD_WIDTH_DAY = 120;
	private static final int TEXT_FIELD_WIDTH_MONTH = 120;
	private static final int TEXT_FIELD_WIDTH_YEAR = 180;

	private static final int SYNC_PANEL_HEIGHT = TEXT_FIELD_HEIGHT*3  ;
	
	private static final int MESSAGE_PANEL_HEIGHT = 30;
	private static final Color LABEL_BG_COLOR = new Color(78, 128, 188);

	private static final String CHK_BUTTON_NORMAL = "checkbox_normal.png";
	private static final String CHK_BUTTON_SELECTED = "checkbox_selected.png";
	private static final Border LABEL_PADDING = new EmptyBorder(2, 2, 2, 2);

	private JPanel mContentPanel;
	private JLabel mlabelDay;
	private JLabel mlabelMonth;
	private JLabel mlabelYear;
	private JLabel mlabelPrevious;
	private JLabel mlabelCurrent;

	private JLabel mlabelMessageDate;
	private JLabel mlabelMessageSync;

	private PosTouchableDigitalField mTxtDayPrevious;
	private PosTouchableDigitalField mTxtMonthPrevious;
	private PosTouchableDigitalField mTxtYearPrevious;
	private PosTouchableDigitalField mTxtDayCurrent;
	private PosTouchableDigitalField mTxtMonthCurrent;
	private PosTouchableDigitalField mTxtYearCurrent;

	private JCheckBox mSyncFromServer;
	private JCheckBox mSyncToServer;
	private ActionListener mchkActionListener;

	private String mNextPosDate=null;
	private String mLastPosDate=null;
	private PosDayProcessProvider dayProcessProvider;
	private PosOrderHdrProvider mOrderHdrProvider;
	private PosDayProcess mDayProcessState;
	private PosDayProcessFormListener listener;
	protected Date currentdate = null;
	protected BeanUser mPosUserObject = null;
	private boolean valid;

	public void setlistener(PosDayProcessFormListener listener){
		this.listener = listener;
	}

	/**
	 * @param dayStart 
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosDayProcessForm(PosDayProcess day) {
		
		super((day.equals(PosDayProcess.DAY_START)?"Day Start":"Day End"), PANEL_WIDTH, PANEL_HEIGHT - (PosEnvSettings.getInstance().getApplicationType()==ApplicationType.Standard?0:SYNC_PANEL_HEIGHT));
		this.mDayProcessState = day;
		initDayProcesscontrols();
		if(mDayProcessState==PosDayProcess.DAY_START)
			 setDefaultComponent(mTxtDayCurrent);
		else
			setDefaultComponent(mSyncFromServer);
		
	}

	/**
	 * 
	 */
	private void initDayProcesscontrols() {
		initListener();
		setHeader();
		createPreviousDateControls();
		createCurrentDateControls();

	
		setSynch();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		mContentPanel = panel;
		mContentPanel.setLayout(null);
		dayProcessProvider = new PosDayProcessProvider();
		mlabelMessageDate = getShortMessagePanel("POS Date Settings");
		mlabelMessageDate.setLocation(0, 0);
		mContentPanel.add(mlabelMessageDate);
	}

	/**
	 * 
	 */
	private void initListener() {
		mchkActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				setOkEnabled(mSyncFromServer.isSelected()
//						|| mSyncToServer.isSelected());
			}
		};
	}

	/**
	 * Setting Synchronization options.
	 */
	private void setSynch() {
		
		mlabelMessageSync = getShortMessagePanel("POS Synchronization Settings");
		  int top = mTxtYearCurrent.getY() + TEXT_FIELD_HEIGHT
				+4;
		mlabelMessageSync.setLocation(0, top);
		
		
		final boolean isSyncEnabled=PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getMenuListPanelSettings().isSyncButtonVisible();
		int left = PANEL_CONTENT_H_GAP * 2 + LABEL_CAPTION_WIDTH;
		  top = mlabelMessageSync.getY() + mlabelMessageSync.getHeight()
				+ PANEL_CONTENT_V_GAP;

		mSyncFromServer = new JCheckBox();
		mSyncFromServer.setHorizontalAlignment(JTextField.LEFT);
		mSyncFromServer.setBounds(left, top, 400, TEXT_FIELD_HEIGHT);
		mSyncFromServer.setFont(PosFormUtil.getLabelFont());
		mSyncFromServer.setText("Receive data from Server");
		mSyncFromServer.setMnemonic('R');
		mSyncFromServer.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mSyncFromServer.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mSyncFromServer.setSelected(true);
//		mSyncFromServer.setSelected(mDayProcessState.equals(PosDayProcess.DAY_START)?true:false);
		mSyncFromServer.addActionListener(mchkActionListener);
		mSyncFromServer.setEnabled(isSyncEnabled);
	
		top = mSyncFromServer.getY() + mSyncFromServer.getHeight()
				+ PANEL_CONTENT_V_GAP;

		mSyncToServer = new JCheckBox();
		mSyncToServer.setHorizontalAlignment(JTextField.LEFT);
		mSyncToServer.setBounds(left, top, 400, TEXT_FIELD_HEIGHT);
		mSyncToServer.setFont(PosFormUtil.getLabelFont());
		mSyncToServer.setText("Send data to Server");
		mSyncToServer.setMnemonic('S');
		mSyncToServer.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mSyncToServer.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
//		mSyncToServer.setSelected(mDayProcessState.equals(PosDayProcess.DAY_END)?true:false);
		mSyncToServer.setSelected(true);
		mSyncToServer.addActionListener(mchkActionListener);
		mSyncToServer.setEnabled(isSyncEnabled);
		
		
		if(PosEnvSettings.getInstance().getApplicationType()==ApplicationType.Standard){
			mContentPanel.add(mlabelMessageSync);
			mContentPanel.add(mSyncFromServer);
			mContentPanel.add(mSyncToServer);
		}
	
	}

	/**
	 * Debug code for labels.
	 */
	// private void setBg(JComponent comp){
	// comp.setOpaque(true);
	// comp.setBackground(Color.RED);
	// }

	/**
	 * Setting the Header.
	 * 
	 */
	private void setHeader() {
		int left =3;
		int top = 3 + mlabelMessageDate.getHeight();

		mlabelDay = new JLabel();
		mlabelDay.setText("Day(DD)"+"    ");
		mlabelDay.setOpaque(true);
		mlabelDay.setBackground(Color.LIGHT_GRAY);
		mlabelDay.setBorder(LABEL_PADDING);
		mlabelDay.setFont(PosFormUtil.getLabelFont());
		mlabelDay.setHorizontalAlignment(SwingConstants.RIGHT);
		mlabelDay.setBounds(left, top, LABEL_WIDTH+96, LABEL_HEIGHT);
		// setBg(mlabelDay);
		mContentPanel.add(mlabelDay);

		left = left + mlabelDay.getWidth() + PANEL_CONTENT_H_GAP/10;

		mlabelMonth = new JLabel();
		mlabelMonth.setText("Month(MM)");
		mlabelMonth.setBorder(LABEL_PADDING);
		mlabelMonth.setBackground(Color.LIGHT_GRAY);
		mlabelMonth.setOpaque(true);
		mlabelMonth.setFont(PosFormUtil.getLabelFont());
		mlabelMonth.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelMonth.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
		// setBg(mlabelMonth);
		mContentPanel.add(mlabelMonth);

		left = left + mlabelMonth.getWidth() + PANEL_CONTENT_H_GAP/10;

		mlabelYear = new JLabel();
		mlabelYear.setText("Year(YYYY)");
		mlabelYear.setOpaque(true);
		mlabelYear.setBackground(Color.LIGHT_GRAY);
		mlabelYear.setBorder(LABEL_PADDING);
		mlabelYear.setFont(PosFormUtil.getLabelFont());
		mlabelYear.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelYear.setBounds(left, top, TEXT_FIELD_WIDTH_YEAR-1, LABEL_HEIGHT);
		// setBg(mlabelYear);PosFormUtil.showErroMessageBox(message)
		mContentPanel.add(mlabelYear);
	}

	/**
	 * Previous Date Control Settings.
	 */
	private void createPreviousDateControls() {


		int left = 3;
		int top = mlabelDay.getY() + LABEL_HEIGHT;

		mlabelPrevious = new JLabel();
		mlabelPrevious.setText("Previous :");
		mlabelPrevious.setOpaque(true);
		mlabelPrevious.setBackground(Color.LIGHT_GRAY);
		mlabelPrevious.setBorder(LABEL_PADDING);
		mlabelPrevious.setFont(PosFormUtil.getLabelFont());
		mlabelPrevious.setBounds(left, top+1, LABEL_CAPTION_WIDTH,
				LABEL_CAPTION_HEIGHT);
		// setBg(mlabelPrevious);
		mContentPanel.add(mlabelPrevious);

		left = left + LABEL_CAPTION_WIDTH + PANEL_CONTENT_H_GAP/10;
		mTxtDayPrevious = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_DAY);
		mTxtDayPrevious.setTitle("Day");
		mTxtDayPrevious.hideResetButton(true);
		mTxtDayPrevious.setEditable(false);
		mTxtDayPrevious.setLocation(left, top);
		mTxtDayPrevious.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mContentPanel.add(mTxtDayPrevious);

		left = left + TEXT_FIELD_WIDTH_DAY + PANEL_CONTENT_H_GAP/10;
		mTxtMonthPrevious = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_MONTH);
		mTxtMonthPrevious.setTitle("Month");
		mTxtMonthPrevious.setLocation(left, top);
		mTxtMonthPrevious.hideResetButton(true);
		mTxtMonthPrevious.setEditable(false);
		mTxtMonthPrevious.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mContentPanel.add(mTxtMonthPrevious);

		left = left + TEXT_FIELD_WIDTH_MONTH + PANEL_CONTENT_H_GAP/10;
		mTxtYearPrevious = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_YEAR);
		mTxtYearPrevious.setTitle("Year");
		mTxtYearPrevious.setEditable(false);
		mTxtYearPrevious.setLocation(left, top);
		mTxtYearPrevious.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtYearPrevious.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				// TODO A0.00uto-generated method stub

			}

			@Override
			public void onReset() {
				mTxtDayPrevious.reset();
				mTxtMonthPrevious.reset();
			}
		});
		mContentPanel.add(mTxtYearPrevious);
		try {
			setPrevoisDate();
		} catch (Exception e) {
			PosFormUtil.showErrorMessageBox(this,e.getMessage());
		}
	}

	private void setPrevoisDate() throws Exception{
		mLastPosDate = dayProcessProvider.getPreviousPosDate();
		if (mLastPosDate != null) {
			String[] dates=mLastPosDate.split("-");
			mTxtYearPrevious.setText(dates[0]);
			mTxtMonthPrevious.setText(dates[1]);
			mTxtDayPrevious.setText(dates[2]);
		}

	}

	/**
	 * Current Date control Setting.
	 */
	private void createCurrentDateControls() {
		int left = 3;
		int top = mlabelPrevious.getY() + LABEL_CAPTION_HEIGHT
				+2;

		mlabelCurrent = new JLabel();
		mlabelCurrent.setText("Current :");
		mlabelCurrent.setOpaque(true);
		mlabelCurrent.setBackground(Color.LIGHT_GRAY);
		mlabelCurrent.setBorder(LABEL_PADDING);
		mlabelCurrent.setFont(PosFormUtil.getLabelFont());
		mlabelCurrent.setBounds(left, top, LABEL_CAPTION_WIDTH,
				LABEL_CAPTION_HEIGHT);
		// setBg(mlabelCurrent);
		mContentPanel.add(mlabelCurrent);

		left = left + LABEL_CAPTION_WIDTH + PANEL_CONTENT_H_GAP/10;

		mTxtDayCurrent = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_DAY);
		mTxtDayCurrent.setTitle("Day");
		mTxtDayCurrent.setLocation(left, top-1);
		mTxtDayCurrent.hideResetButton(true);
		mTxtDayCurrent.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtDayCurrent.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				
				if(value!=null && String.valueOf(value).trim().length()>0){
					
					mTxtMonthCurrent.requestFocus();
				}
				
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mContentPanel.add(mTxtDayCurrent);

		left = left + TEXT_FIELD_WIDTH_DAY + PANEL_CONTENT_H_GAP/10;
		mTxtMonthCurrent = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_MONTH);
		mTxtMonthCurrent.setTitle("Month");
		mTxtMonthCurrent.setLocation(left, top-1);
		mTxtMonthCurrent.hideResetButton(true);
		mTxtMonthCurrent.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtMonthCurrent.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				
				if(value!=null && String.valueOf(value).trim().length()>0){
					
					mTxtYearCurrent.requestFocus();
				}
				
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mContentPanel.add(mTxtMonthCurrent);

		left = left + TEXT_FIELD_WIDTH_MONTH + PANEL_CONTENT_H_GAP/10;
		mTxtYearCurrent = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_YEAR);
		mTxtYearCurrent.setTitle("Year");
		mTxtYearCurrent.setLocation(left, top-1);
		mTxtYearCurrent.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtYearCurrent.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {

				doActionAccept();
			}

			@Override
			public void onReset() {
				
				try {
					setDayStart();
					mTxtDayCurrent.requestFocus();
				} catch (Exception e) {
					PosFormUtil.showErrorMessageBox(this,e.getMessage());
				}
				
//				mTxtMonthCurrent.reset();
//				mTxtDayCurrent.reset();
				//				mTxtYearCurrent.reset();
			}
		});
		mContentPanel.add(mTxtYearCurrent);
//		System.out.println(mDayProcessState);
		if(mDayProcessState.equals(PosDayProcess.DAY_END)){
			try {
				setDayEnd();
			} catch (Exception e) {
				PosFormUtil.showErrorMessageBox(this,e.getMessage());
			}
		}else{
			try {
				setDayStart();
			} catch (Exception e) {
				PosFormUtil.showErrorMessageBox(this,e.getMessage());
			}
		}
	}

	/**
	 * Setting the default current date.
	 * @throws Exception
	 */
	private void setDayStart() throws Exception {
		mNextPosDate = dayProcessProvider.getDefaultCurrentPosDate();
		if (mNextPosDate!= null) {
			String[] dates=mNextPosDate.split("-");
			mTxtYearCurrent.setText(dates[0]);
			mTxtMonthCurrent.setText(dates[1]);
			mTxtDayCurrent.setText(dates[2]);
		}
	}

	/**
	 * Setting the current date for day end
	 * @throws Exception 
	 * 
	 */
	private void setDayEnd() throws Exception {

		mTxtDayCurrent.setEditable(false);
		mTxtMonthCurrent.setEditable(false);
		mTxtYearCurrent.setEditable(false);
		mNextPosDate = dayProcessProvider.getCurrentPosDate();
		if (mNextPosDate != null) {
			String[] dates=mNextPosDate.split("-");
			mTxtYearCurrent.setText(dates[0]);

			mTxtMonthCurrent.setText(dates[1]);
			mTxtDayCurrent.setText(dates[2]);
		}
	}

	/**
	 * Setting the Title message.
	 * @param message
	 * @return labelMessage
	 */
	protected JLabel getShortMessagePanel(String message) {
		JLabel labelMessage = new JLabel();
		labelMessage.setText(message);
		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);
		labelMessage.setSize(new Dimension(getWidth()
				- (PANEL_CONTENT_H_GAP*2-3), MESSAGE_PANEL_HEIGHT));
		labelMessage.setOpaque(true);
		labelMessage.setBackground(LABEL_BG_COLOR);
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(PosFormUtil.getSubHeadingFont());
		// mContentPanel.add(labelMessage);//
		return labelMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */		

	@Override
	public boolean onOkButtonClicked() {

		valid = true;
		//		Date currentdate = null;
		Date previousdate = null;
		
		mNextPosDate = PosDateUtil.
				buildStringDate(mTxtYearCurrent.getText(), mTxtMonthCurrent.getText(),
						mTxtDayCurrent.getText(), PosDateUtil.DATE_SEPERATOR);
		currentdate = PosDateUtil.parse(mNextPosDate);

		if ((mTxtDayCurrent.getText().length() ==0)
				|| (mTxtDayCurrent.getText().length() >2)) {
			PosFormUtil.showErrorMessageBox(this,"Invalid day.");
			mTxtDayCurrent.requestFocus();
			valid = false;
		} else if ((mTxtMonthCurrent.getText().length() ==0)
				|| (mTxtMonthCurrent.getText().length() >2)) {
			PosFormUtil.showErrorMessageBox(this,"Invalid month.");
			mTxtMonthCurrent.requestFocus();
			valid = false;
		} else if ((mTxtYearCurrent.getText().length()) != 4) {
			PosFormUtil.showErrorMessageBox(this,"Invalid year.");
			mTxtYearCurrent.requestFocus();
			valid = false;
		} else if (!PosDateUtil.validateDate(mNextPosDate)) {
			PosFormUtil.showErrorMessageBox(this,"Date is not in proper format.");
			mTxtDayCurrent.requestFocus();
			valid = false;
//			System.out.println(mNextPosDate);
		}
		else if(mTxtYearPrevious.getText().length()!=0){
			mLastPosDate = PosDateUtil.
					buildStringDate(mTxtYearPrevious.getText(),mTxtMonthPrevious.getText(),
							mTxtDayPrevious.getText(), PosDateUtil.DATE_SEPERATOR);
			previousdate = PosDateUtil.parse(mLastPosDate);
			if (currentdate.compareTo(previousdate) <= 0) {
				PosFormUtil
				.showErrorMessageBox(this,"Current date should be after previous date.");
				mTxtDayCurrent.requestFocus();
				valid = false;
			}
		}
		if(mDayProcessState.equals(PosDayProcess.DAY_END) && PosMainMenuForm.getInstance().isShiftOpen()){
				PosFormUtil.showErrorMessageBox(this,"Shift is open. Please close shift.");
				valid = false;
		}else if(valid&&mDayProcessState.equals(PosDayProcess.DAY_END)){
			String nxtDate = PosDateUtil.getNextDate(mNextPosDate,1);
			
			PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNo, 
					"Are you sure you want to proceed with DAY END Process ? This will bring the POS date to "+nxtDate+".", 
					new PosMessageBoxFormListnerAdapter() {
				/* (non-Javadoc)
				 * @see com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter#onYesButtonPressed()
				 */
				@Override
				public void onYesButtonPressed() {
					valid = true;
				}
				/* (non-Javadoc)
				 * @see com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter#onCancelClicked()
				 */
				@Override
				public void onNoButtonPressed() {
					valid = false;
					super.onNoButtonPressed();
				}
			});
		}
		
		if(valid ){
			
			final Date today=PosDateUtil.parse(PosDateUtil.getDate());
			if(currentdate.compareTo(today)!=0){
				
				PosDateMismatchWarningForm form=new PosDateMismatchWarningForm();
				PosFormUtil.showLightBoxModal(PosDayProcessForm.this,form);
				valid=!form.isCancelled();
			}
		}
		
		BeanUser user=null;
		
		if(valid ){
			
			PosUserAuthenticateForm loginForm=new PosUserAuthenticateForm("Authenticate");
			PosFormUtil.showLightBoxModal(this,loginForm);
			user=loginForm.getUser();
			if(user != null){
				
				if(mDayProcessState.equals(PosDayProcess.DAY_END)&&!PosAccessPermissionsUtil.validateAccess(this,user.getUserGroupId(), "day_process")){
					
					valid=false;
					
				}else{
					
					
//					try{
//						
//						setDayProcessObject(PosDateUtil.format(PosDateUtil.DATE_FORMAT,currentdate),user,mDayProcessState);
//						PosEnvSettings.getInstance().setPosDate(null);
//						
//					} catch (Exception e) {
//						
//						PosFormUtil.showErrorMessageBox(this,"Failed to set day process information. Please contact Administrator.");
//						valid=false;
//					}
				}
			}else{
				valid=false;
			}
		}
		
		if(valid){

			final BeanUser validUser=user;

			SwingWorker<Boolean, String> swt = new SwingWorker<Boolean, String>() {
				@Override
				protected Boolean doInBackground() throws Exception {
					try {

						setDayProcessObject(PosDateUtil.format(PosDateUtil.DATE_FORMAT_YYYYMMDD,currentdate),validUser,mDayProcessState);
						PosEnvSettings.getInstance().setPosDate(null);

						PosSyncUtil.sync(mSyncFromServer.isSelected(), mSyncToServer.isSelected());

						if(mDayProcessState.equals(PosDayProcess.DAY_END)){
							try{	
								if(PosEnvSettings.getInstance().getApplicationType().equals(ApplicationType.StandAlone))
									addToHistory();
								
								doPurgeData();
							} catch (Exception e) {
								PosLog.write("DayEnd", "Backup & Purging", e.getMessage());
							}
 							PosBackup backup=new PosBackup();
							backup.createFilteredBackup();
							//			PosFormUtil.showInformationMessageBox(this, "Day End has been completed.");
							//			doPrintingAtDayEnd();
						}

					} catch (Exception e) {
						PosFormUtil.closeBusyWindow();
						throw new Exception(
								"Problem in purging old data. Please check the log for details.");
					}
 					return true;
				}
				@Override
				protected void done() {
					
					PosFormUtil.closeBusyWindow();
					//PosFormUtil.showInformationMessageBox(null, "Purging process completed.");
//					if(mDayProcessState.equals(PosDayProcess.DAY_END)){
						
//						doPrintingAtDayEnd();
//						PosFormUtil.showInformationMessageBox(getParent(), "Day End has been completed.");
						
//					}
					
					if(listener!=null)
							listener.onOkPressed(PosDateUtil.format(PosDateUtil.DATE_FORMAT_YYYYMMDD,currentdate));
						
				}
			};
			swt.execute();
			
			final String dayProcMessage=(mDayProcessState.equals(PosDayProcess.DAY_END))?"Doing Day End process. Please wait...":"Doing Day Start process. Please wait...";
			PosFormUtil.showBusyWindow(this,dayProcMessage);

		}
		
//		if(valid ){
			
//			if(mDayProcessState.equals(PosDayProcess.DAY_END)){
//			PosFormUtil.showInformationMessageBox(this, "Day End has been completed.");
//			doPrintingAtDayEnd();
//			}
//			if(listener!=null)
//				listener.onOkPressed(PosDateUtil.format(PosDateUtil.DATE_FORMAT,currentdate));
//			
//		}

		return valid;
	}
	
	private void resetDayTransaction() throws SQLException{
		
		final PosOrderQueueProvider orderQProvider=new PosOrderQueueProvider();
		
		/*
		 * Reset the order que number every dayend if configured to do so.
		 */
		if(PosEnvSettings.getInstance().getOrderQueueResetPolicy()==PosQueueNoResetPolicy.DAILY)
			orderQProvider.resetQueueNumber();
		/*
		 * Reset the kitchen que number every dayend if configured to do so.
		 */
		if(PosEnvSettings.getInstance().getKitchenQueueResetPolicy()==PosQueueNoResetPolicy.DAILY)
			orderQProvider.resetQueueNumber();
		
	}

	
	
	/**
	 * @throws SQLException 
	 * 
	 */
	private void addToHistory() throws Exception {
		
		mOrderHdrProvider = new PosOrderHdrProvider();
		final PosCashierShiftProvider cashierShiftProvider=new PosCashierShiftProvider();
		final PosShiftSummaryProvider shiftSummaryProvider=new PosShiftSummaryProvider();
		final PosCashOutProvider cashOutProvider=new PosCashOutProvider();
		
		int days = (PosEnvSettings.getInstance().getNumberOfDaysToKeepTXNData()<=3)?3:PosEnvSettings.getInstance().getNumberOfDaysToKeepTXNData();
		final String posDate= PosDateUtil.getNextDate(mNextPosDate, -days);

		StringBuffer sqlStatements = new StringBuffer();
		
		
		sqlStatements.append(mOrderHdrProvider.addTransactionDataToHistoryTable(posDate));
		sqlStatements.append(cashOutProvider.addToHistory(posDate));
		sqlStatements.append(cashierShiftProvider.addToHistory(posDate));
		sqlStatements.append(shiftSummaryProvider.addToHistory(posDate));
		
		
		PosShopProvider shopDB = new PosShopProvider();
		shopDB.executeNonQuery(sqlStatements.toString());
		
 		
	}

	/**
	 * @throws SQLException 
	 * 
	 */
	private void doPurgeData() throws SQLException {
		
		mOrderHdrProvider = new PosOrderHdrProvider();
		final PosAccessLogProvider accessLogProvider=new PosAccessLogProvider();
		final PosCashierShiftProvider cashierShiftProvider=new PosCashierShiftProvider();
		final PosLoginSessionsProvider loginSessionsProvider=new PosLoginSessionsProvider();
		final PosShiftSummaryProvider shiftSummaryProvider=new PosShiftSummaryProvider();
		final PosCashOutProvider cashOutProvider=new PosCashOutProvider();
		final PosAttendProvider attendProvider=new PosAttendProvider();
		
		final String dateTo=PosDateUtil.getNextDate(mNextPosDate, - PosEnvSettings.getPosEnvSettings().getNumberOfDaysToKeepTXNData());
		
//		SwingWorker<Boolean, String> swt = new SwingWorker<Boolean, String>() {
//			@Override
//			protected Boolean doInBackground() throws Exception {
//				try {
					
					resetDayTransaction();
					
					
					mOrderHdrProvider.clearTransactionData(mNextPosDate);
					
					accessLogProvider.purgeData(dateTo);
					cashierShiftProvider.purgeData(dateTo);
					loginSessionsProvider.purgeData(dateTo);
					shiftSummaryProvider.purgeData(dateTo);
					cashOutProvider.purgeData(dateTo);
					attendProvider.purgeData(dateTo);
					
//				} catch (Exception e) {
//					PosFormUtil.closeBusyWindow();
//					throw new Exception(
//							"Problem in purging old data. Please check the log for details.");
//				}
//				return true;
//			}
//			@Override
//			protected void done() {
//				PosFormUtil.closeBusyWindow();
////				PosFormUtil.showInformationMessageBox(null, "Purging process completed.");
//			}
//		};
//		swt.execute();
//		PosFormUtil.showBusyWindow(this,
//				"Purging the old data. This process may take a few minutes to complete. Please wait...");
		
	}

	/**
	 * Setting the Posdayprocessobject and process
	 * @param date
	 * @param dayStatus
	 */
	private void setDayProcessObject(String date, BeanUser user, PosDayProcess dayStatus) throws Exception {
		//		PosShopProvider shopProvider=new PosShopProvider();
		//		PosTerminalSettingsProvider settingProvider=new PosTerminalSettingsProvider();
		BeanShop shop=PosEnvSettings.getInstance().getShop();
		PosDayProcessProvider dayProcessProvider = new PosDayProcessProvider();
		BeanDayProcess dayProcess = new BeanDayProcess();
		dayProcess.setShopId(shop.getId());
		dayProcess.setStationId(PosEnvSettings.getInstance().getStation().getId());
		dayProcess.setPosDate(date); 
		dayProcess.setDoneBy(user.getId()); //To do .. fetch id from user login form
		dayProcess.setDoneAt(PosDateUtil.getDateTime());
		dayProcess.setStatus(dayStatus);
		dayProcess.setSynchUp(mSyncToServer.isSelected());
		dayProcess.setSynchDown(mSyncFromServer.isSelected());
		try {
			dayProcessProvider.updateDayProcess(dayProcess, dayStatus);
		} catch (Exception e) {
			PosFormUtil.showErrorMessageBox(this,e.getMessage());
		}

	}

	/**
	 * Interface PosDayProcessFormListener 
	 * @author deepak
	 *
	 */
	public interface PosDayProcessFormListener{
		public void onOkPressed(String date);
	}
	
	/*
	 * 
	 */
//	private void doPrintingAtDayEnd(){
//
//		if (PosEnvSettings.getInstance().getPrintSettings().getPrintDayEndReportAtDayEnd()==EnablePrintingOption.NO) 
//			return;
//
//		boolean doPrint=true;
//
//		if (PosEnvSettings.getInstance().getPrintSettings().getPrintDayEndReportAtDayEnd()==EnablePrintingOption.ASK) 
//			doPrint=PosFormUtil.showQuestionMessageBox(this  ,
//					MessageBoxButtonTypes.YesNo,
//					"  Do you want to print the Day End report?",
//					null)==MessageBoxResults.Yes;
//
//		if(doPrint){
//
//			IPosSearchableItem mShiftAll;
//			try{
//				mShiftAll=PosShopShiftProvider.getItemForAllShift();
//	
//				final PosShiftReport report=new PosShiftReport();
//				report.setCriteria(" closing_date ='" + mNextPosDate + "'");
//				report.setPosDate(mNextPosDate);
//	
//				report.setSummaryOnly(false);
//				report.setShift((BeanShopShift) mShiftAll);
//				report.setPrintPaymentDtls(false);
//				report.setSalesReportOnly(false);
//				report.setIsDayEndReport(true);
//				PosShiftReport.doPrintDayEndReport(this, report);
//			} catch (Exception e) {
//				
//				PosFormUtil.showErrorMessageBox(null, e.getMessage());
//			}
//
//		}
//	}
	

}
