/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanDayProcess;
import com.indocosmo.pos.data.beans.BeanShopShift;
import com.indocosmo.pos.data.providers.shopdb.PosCashierShiftProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDayProcessProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDayProcessProvider.PosDayProcess;
import com.indocosmo.pos.data.providers.shopdb.PosShopShiftProvider;
import com.indocosmo.pos.forms.components.checkboxes.PosCheckBox;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.radiobuttons.PosRadioButton;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.reports.shift.PosShiftReport;
import com.indocosmo.pos.reports.shift.custom.PosShiftReportNihon;

/**
 * @author deepak
 *
 */
@SuppressWarnings("serial")
public class PosShiftReportForm extends PosBaseForm{

	private static final int PANEL_CONTENT_H_GAP = 8;
	private static final int PANEL_CONTENT_V_GAP = 8;
	private static final int LABEL_HEIGHT = 40;
	private static final int H_GAP_BTWN_CMPNTS=1;
	private static final int V_GAP_BTWN_CMPNTS=3;
	private static final int LABEL_HEIGHT_SMALL = 30;
	private static final int LABEL_WIDTH_BIG = 135;

	private static final int TEXT_FIELD_WIDTH = 438;
	private static final int TEXT_FIELD_WIDTH_DAY = 125;
	private static final int TEXT_FIELD_WIDTH_MONTH = 126;
	private static final int TEXT_FIELD_WIDTH_YEAR = 185;

	private static final int PANEL_HEIGHT = 436;
	private static final int PANEL_WIDTH = 580;

	private static final int CHECKBOX_WIDTH = 180;
	private static final int CHECKBOX_HEIGHT = 40;
	private static final String CHK_BUTTON_NORMAL="checkbox_normal.png";
	private static final String CHK_BUTTON_SELECTED="checkbox_selected.png";
	private static final Border labelPadding = new EmptyBorder(2, 2, 2, 2);

	private JPanel mContentPanel;
	private int left;
	private int top;
	private JLabel mReportType;
	private JLabel mlabelDate;
	private JLabel mlabelDay;
	private JLabel mlabelMonth;
	private JLabel mlabelYear;

	private PosTouchableDigitalField mTxtDay;
	private PosTouchableDigitalField mTxtMonth;
	private PosTouchableDigitalField mTxtYear;
	private JLabel mlabelTimeFrom;
	private JLabel mlabelHourFrom;
	private JLabel mlabelMinuteFrom;
	private PosTouchableDigitalField mTxtHourFrom;
	private PosTouchableDigitalField mTxtMinFrom;
	private JLabel mlabelTimeTo;
	private JLabel mlabelHourTo;
	private JLabel mlabelMinuteTo;
	private PosTouchableDigitalField mTxtHourTo;
	private PosTouchableDigitalField mTxtMinTo;
	private JLabel mlabelShift;
	private PosItemBrowsableField mTxtShift;
	
	private JLabel mlabelServiceType;

	private PosCheckBox mSummaryOnly;
	private PosCheckBox mPaymentDetails;

	//	private PosItemBrowsableField mTxtServiceType;
	private PosRadioButton mMidReportSelection;
	private PosRadioButton mEndReportSelection;
	private PosCheckBox mCheckWholeSaleService;
	private PosCheckBox mCheckTableService;
	private PosCheckBox mCheckHomeDelivery;
	private PosCheckBox mCheckTakeAway;
	private PosCheckBox mCheckSalesOrder;
	
	private JLabel mlabelPrintOptions;
	private BeanCashierShift shiftDetails;
	PosCashierShiftProvider cashierShiftProvider;
	
	private IPosSearchableItem mShiftAll;
	
	private String mHourFrom;
	private String mMinFrom;
	private String mHourTo;
	private String mMinTo;
	private String mDateTo;
	private String mDateFrom;
	/**
	 * 
	 */
	public PosShiftReportForm() {

		super("Shift Report",PANEL_WIDTH, PANEL_HEIGHT);
		initDayReportUiControls();
	}
	/**
	 * 
	 */
	private void initDayReportUiControls() {

		createReportType();
		createDateSelectionControl();
		createTimeFromSelectionControl();
		createTimeToSelectionControl();
		createShiftSelectionControl();
		createOrderServiceType();
		createPrintOptionControl();
		setDefaultDate();
		
		setEnableControls();
		setOkButtonCaption("Print");
		mButtonOk.setMnemonic('P');
		setCancelButtonCaption("Close");
		mMidReportSelection.setSelected(true);
		setTime();
		mShiftAll=PosShopShiftProvider.getItemForAllShift();
		
	}

	/*
	 * 
	 */
	private void setTime(){
		
		String posDate = (mTxtDay.getText().trim().length()!=0 || mTxtMonth.getText().trim().length()!=0
				|| mTxtYear.getText().trim().length()!=0)?PosDateUtil
						.buildStringDate(mTxtYear.getText()
								.trim(), mTxtMonth.getText().trim(), mTxtDay.getText()
								.trim(), PosDateUtil.DATE_SEPERATOR):null;
		if(posDate==null)
			posDate = PosEnvSettings.getInstance().getPosDate();
		
		if (mMidReportSelection.isSelected()) {
			setTimeFrom(posDate);
			setTimeTo(posDate);
		}
			
	}
	/*
	 * 
	 */
	private void setTimeFrom(){
		
		String posDate = (mTxtDay.getText().trim().length()!=0 || mTxtMonth.getText().trim().length()!=0
				|| mTxtYear.getText().trim().length()!=0)?PosDateUtil
						.buildStringDate(mTxtYear.getText()
								.trim(), mTxtMonth.getText().trim(), mTxtDay.getText()
								.trim(), PosDateUtil.DATE_SEPERATOR):null;
		if(posDate==null)
			posDate = PosEnvSettings.getInstance().getPosDate();
		
		setTimeFrom(posDate);
	}

	/**
	 * 
	 */
	
	private void setTimeFrom(String posDate ) {
		
						
		final PosDayProcessProvider dpp = new PosDayProcessProvider();
	 	try {
			final BeanDayProcess dp = dpp.getDayProcess(posDate);
			final String dateTime = dp.getDoneAt().substring(10).trim();
			final String[] time = dateTime.split(":", -1);
			
			mDateFrom=PosDateUtil.format(PosDateUtil.DATE_FORMAT_YYYYMMDD,dp.getDoneAt());
			mTxtHourFrom.setText(PosStringUtil.paddLeft(time[0],2,'0'));
			mTxtMinFrom.setText(PosStringUtil.paddLeft(time[1],2,'0'));
			
			mHourFrom=mTxtHourFrom.getText();
			mMinFrom=mTxtMinFrom.getText();
		} catch (Exception e) {
			PosLog.write(this, "setDefaultTimeFrom", e);
			e.printStackTrace();
		}
	}

	/*
	 * 
	 */
	private void setTimeTo(){
		
		String posDate = (mTxtDay.getText().trim().length()!=0 || mTxtMonth.getText().trim().length()!=0
				|| mTxtYear.getText().trim().length()!=0)?PosDateUtil
						.buildStringDate(mTxtYear.getText()
								.trim(), mTxtMonth.getText().trim(), mTxtDay.getText()
								.trim(), PosDateUtil.DATE_SEPERATOR):null;
		if(posDate==null)
			posDate = PosEnvSettings.getInstance().getPosDate();
		
		setTimeTo(posDate);
	}
	/*
	 * 
	 */
	private void setTimeTo(String posDate){
		 
			final PosDayProcessProvider dpp = new PosDayProcessProvider();
		
			try {
				final BeanDayProcess dp = dpp.getDayProcess(posDate,PosDayProcess.DAY_END);
				if(dp==null){
					mDateTo=PosDateUtil.getDate();
					mTxtHourTo.setText(PosStringUtil.paddLeft(String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)),2,'0'));
					mTxtMinTo.setText(PosStringUtil.paddLeft(String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)),2,'0'));
					
				}else{
					mDateTo=PosDateUtil.format(PosDateUtil.DATE_FORMAT_YYYYMMDD,dp.getDoneAt());
					final String dateTime = dp.getDoneAt().substring(10).trim();
					final String[] time = dateTime.split(":", -1);
					mTxtHourTo.setText(PosStringUtil.paddLeft(time[0],2,'0'));
					mTxtMinTo.setText(PosStringUtil.paddLeft(time[1],2,'0'));
				}
			} catch (Exception e) {
				PosLog.write(this, "setDefaultTimeTo", e);
				e.printStackTrace();
			}
			 
			mHourTo=mTxtHourTo.getText();
			mMinTo=mTxtMinTo.getText();
	}
	
 
	private void setDateTo(String posDate){

		final PosDayProcessProvider dpp = new PosDayProcessProvider();
		try {
			final BeanDayProcess dp = dpp.getDayProcess(posDate,PosDayProcess.DAY_END);
			if(dp==null){
				mDateTo=PosDateUtil.getDate();
			}else{
				mDateTo=PosDateUtil.format(PosDateUtil.DATE_FORMAT_YYYYMMDD,dp.getDoneAt());
			}
		} catch (Exception e) {
			PosLog.write(this, "setDefaultTimeTo", e);
			e.printStackTrace();
		}
		 
	}
	/**
	 * 
	 */
	private void createReportType() {

		left = PANEL_CONTENT_H_GAP;
		top = PANEL_CONTENT_V_GAP;
		//		top = mPaymentDetails.getY() + mPaymentDetails.getHeight()
		//				+ PANEL_CONTENT_V_GAP;

		left=PANEL_CONTENT_H_GAP/4+1;
		top=PANEL_CONTENT_V_GAP/4+1;

		mReportType = new JLabel("Report :");
		mReportType.setOpaque(true);
		mReportType.setBackground(Color.LIGHT_GRAY);
		mReportType.setBorder(labelPadding);
		mReportType.setBounds(left, top, LABEL_WIDTH_BIG, LABEL_HEIGHT);
		mReportType.setFont(PosFormUtil.getLabelFont());
		//		panelReportTypeOptions.add(reportType);
		mContentPanel.add(mReportType);

		left=mReportType.getX()+mReportType.getWidth()+H_GAP_BTWN_CMPNTS;

		mMidReportSelection = new PosRadioButton();
		mMidReportSelection.setFocusable(false);
		mMidReportSelection.setTag("Mid report");
		mMidReportSelection.setHorizontalAlignment(JTextField.LEFT);
		mMidReportSelection.setBounds(left, top, 218, CHECKBOX_HEIGHT);
//		mMidReportSelection.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
//		mMidReportSelection.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mMidReportSelection.setFont(PosFormUtil.getTextFieldBoldFont());
		//		mMidReportSelection.setBackground(Color.BLUE);
		mMidReportSelection.setText("Mid Day Report");
		mMidReportSelection.setMnemonic('M');
		mMidReportSelection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//mEndReportSelection.setSelected(!mMidReportSelection.isSelected());
				setControlsOnReportTypeChange();
			}
		});
		mMidReportSelection.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				setEnableControls();
				
			}
		});
		//		panelReportTypeOptions.add(mMidReportSelection);
		mContentPanel.add(mMidReportSelection);

		left=mMidReportSelection.getX()+mMidReportSelection.getWidth();

		mEndReportSelection = new PosRadioButton();
		mEndReportSelection.setFocusable(false);
		mEndReportSelection.setTag("End report");
		mEndReportSelection.setHorizontalAlignment(JTextField.LEFT);
		mEndReportSelection.setBounds(left, top, 220, CHECKBOX_HEIGHT);
//		mEndReportSelection.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
//		mEndReportSelection.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mEndReportSelection.setFont(PosFormUtil.getTextFieldBoldFont());
		//		mEndReportSelection.setBackground(Color.YELLOW);
		mEndReportSelection.setText("End Day Report");
		//		panelReportTypeOptions.add(mEndReportSelection);
		mEndReportSelection.setMnemonic('E');
		mEndReportSelection.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//mMidReportSelection.setSelected(!mEndReportSelection.isSelected());
				setControlsOnReportTypeChange();
			}
		});
		mContentPanel.add(mEndReportSelection);
		
		final ButtonGroup reporttype = new ButtonGroup();
		reporttype.add(mMidReportSelection);
		reporttype.add(mEndReportSelection);

	}
	
	
	/**
	 * 
	 */
	private void setDefaultDate() {

		String posDate = PosEnvSettings.getInstance().getPosDate();
		if(posDate != null){
			String dates[] = posDate.split("-");
			mTxtDay.setText(dates[2]);
			mTxtMonth.setText(dates[1]);
			mTxtYear.setText(dates[0]);
		}
	}
	/**
	 * 
	 */
	private void createDateSelectionControl() {
		setDateHeader();
		left = PANEL_CONTENT_H_GAP/4+1;
//		top = mlabelTime.getY()+mlabelTime.getHeight()+V_GAP_BTWN_CMPNTS;//+ mlabelDay.getY() + mlabelDay.getHeight();
		top = mReportType.getY()+mReportType.getHeight()+V_GAP_BTWN_CMPNTS;//+ mlabelDay.getY() + mlabelDay.getHeight();
		
		
		mlabelDate = new JLabel();
		mlabelDate.setText(PosFormUtil.getMnemonicString("POS Date :",'D'));
		mlabelDate.setBorder(new EmptyBorder(2,2,2,2));
		mlabelDate.setBackground(Color.LIGHT_GRAY);
		mlabelDate.setOpaque(true);
		mlabelDate.setFont(PosFormUtil.getLabelFont());
		mlabelDate.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelDate.setBounds(left, top, LABEL_WIDTH_BIG, LABEL_HEIGHT+30);
		mContentPanel.add(mlabelDate);

		left = mlabelDate.getX() + mlabelDate.getWidth() +H_GAP_BTWN_CMPNTS;
		top =  mlabelDay.getY() + mlabelDay.getHeight();

		mTxtDay = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_DAY);
		mTxtDay.setMnemonic('D');
		mTxtDay.setTitle("Day");
		mTxtDay.hideResetButton(true);
		mTxtDay.setLocation(left, top);
		mTxtDay.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtDay.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtMonth.requestFocus();
				setTime();
			}
			
			@Override
			public void onReset() {
			}
		});
		mContentPanel.add(mTxtDay);

		left = mlabelMonth.getX();

		mTxtMonth = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_MONTH);
		mTxtMonth.setTitle("Month");
		mTxtMonth.setLocation(left, top);
		mTxtMonth.hideResetButton(true);
		mTxtMonth.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtMonth.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				setTime();
				mTxtYear.requestFocus();
			}
			
			@Override
			public void onReset() {
				
			}
		});
		mContentPanel.add(mTxtMonth);

		left = mlabelYear.getX();

		mTxtYear = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_YEAR);
		mTxtYear.setTitle("Year");
		mTxtYear.setLocation(left, top);
		mTxtYear.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtYear.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				setTime();
				mTxtHourFrom.requestFocus();

			}

			@Override
			public void onReset() {
				setTime();
				setDefaultDate();
			}
		});
		mContentPanel.add(mTxtYear);


	}

	private void setDateHeader() {

//		left =mlabelTime.getX()+mlabelTime.getWidth()+H_GAP_BTWN_CMPNTS;
//		top =  mlabelTime.getY()+mlabelTime.getHeight()+V_GAP_BTWN_CMPNTS;
		left =mReportType.getX()+mReportType.getWidth()+H_GAP_BTWN_CMPNTS;
		top =  mReportType.getY()+mReportType.getHeight()+V_GAP_BTWN_CMPNTS;
		

		mlabelDay = new JLabel();
		mlabelDay.setText("Day(DD)");
		mlabelDay.setBorder(new EmptyBorder(2,2,2,2));
		mlabelDay.setBackground(Color.LIGHT_GRAY);
		mlabelDay.setOpaque(true);
		mlabelDay.setFont(PosFormUtil.getLabelFont());
		mlabelDay.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelDay.setBounds(left, top, TEXT_FIELD_WIDTH_DAY, LABEL_HEIGHT_SMALL);
		mContentPanel.add(mlabelDay);

		left = mlabelDay.getX() + mlabelDay.getWidth() + H_GAP_BTWN_CMPNTS;

		mlabelMonth = new JLabel();
		mlabelMonth.setText("Month(MM)");
		mlabelMonth.setBorder(new EmptyBorder(2,2,2,2));
		mlabelMonth.setBackground(Color.LIGHT_GRAY);
		mlabelMonth.setOpaque(true);
		mlabelMonth.setFont(PosFormUtil.getLabelFont());
		mlabelMonth.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelMonth.setBounds(left, top, TEXT_FIELD_WIDTH_MONTH, LABEL_HEIGHT_SMALL);
		mContentPanel.add(mlabelMonth);

		left = mlabelMonth.getX() + mlabelMonth.getWidth()
				+H_GAP_BTWN_CMPNTS;


		mlabelYear = new JLabel();
		mlabelYear.setText("Year(YYYY)");
		mlabelYear.setBorder(new EmptyBorder(2,2,2,2));
		mlabelYear.setBackground(Color.LIGHT_GRAY);
		mlabelYear.setOpaque(true);
		mlabelYear.setFont(PosFormUtil.getLabelFont());
		mlabelYear.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelYear.setBounds(left, top, TEXT_FIELD_WIDTH_YEAR-1, LABEL_HEIGHT_SMALL);
		mContentPanel.add(mlabelYear);
	}

	
	/**
	 * 
	 */
	private void createTimeFromSelectionControl() {
		setTimeHeaderFrom();
		left = PANEL_CONTENT_H_GAP/4+1;
//		top = mReportType.getY()+mReportType.getHeight()+V_GAP_BTWN_CMPNTS;//+ mlabelDay.getY() + mlabelDay.getHeight();
		top = mlabelDate.getY()+mlabelDate.getHeight()+V_GAP_BTWN_CMPNTS;//+ mlabelDay.getY() + mlabelDay.getHeight();

		mlabelTimeFrom = new JLabel();
		mlabelTimeFrom.setText(PosFormUtil.getMnemonicString("Time from :",'f'));
		mlabelTimeFrom.setBorder(new EmptyBorder(2,2,2,2));
		mlabelTimeFrom.setBackground(Color.LIGHT_GRAY);
		mlabelTimeFrom.setOpaque(true);
		mlabelTimeFrom.setFont(PosFormUtil.getLabelFont());
		mlabelTimeFrom.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelTimeFrom.setBounds(left, top, LABEL_WIDTH_BIG, LABEL_HEIGHT+30);
		mContentPanel.add(mlabelTimeFrom);

		left = mlabelTimeFrom.getX() + mlabelTimeFrom.getWidth() +H_GAP_BTWN_CMPNTS;
		top = mlabelHourFrom.getY() + mlabelHourFrom.getHeight();

		mTxtHourFrom = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_DAY);
		mTxtHourFrom.setMnemonic('f');
		mTxtHourFrom.setTitle("Hour");
		mTxtHourFrom.hideResetButton(true);
		mTxtHourFrom.setLocation(left, top);
		mTxtHourFrom.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtHourFrom.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtMinFrom.requestFocus();
			}
			
			@Override
			public void onReset() {
				
			}
		});
		mContentPanel.add(mTxtHourFrom);

		left = mlabelMinuteFrom.getX();

		mTxtMinFrom = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_MONTH+50);
		mTxtMinFrom.setTitle("Minute");
		mTxtMinFrom.setLocation(left, top);
		//		mTxtMin.hideResetButton(true);
		mTxtMinFrom.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtMinFrom.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {

				mTxtHourTo.requestFocus();
			}

			@Override
			public void onReset() {
				setTimeFrom();
			}
		});
		mContentPanel.add(mTxtMinFrom);
	}
	
	/**
	 * 
	 */
	private void setTimeHeaderFrom() {

		left =mlabelDate.getX()+mlabelDate.getWidth()+H_GAP_BTWN_CMPNTS;
		top =  mlabelDate.getY()+mlabelDate.getHeight()+V_GAP_BTWN_CMPNTS;

		mlabelHourFrom = new JLabel();
		mlabelHourFrom.setText("Hour(HH)");
		mlabelHourFrom.setBorder(new EmptyBorder(2,2,2,2));
		mlabelHourFrom.setBackground(Color.LIGHT_GRAY);
		mlabelHourFrom.setOpaque(true);
		mlabelHourFrom.setFont(PosFormUtil.getLabelFont());
		mlabelHourFrom.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelHourFrom.setBounds(left, top, TEXT_FIELD_WIDTH_DAY, LABEL_HEIGHT_SMALL);
		mContentPanel.add(mlabelHourFrom);

		left = mlabelHourFrom.getX() + mlabelHourFrom.getWidth() + H_GAP_BTWN_CMPNTS;

		mlabelMinuteFrom = new JLabel();
		mlabelMinuteFrom.setText("Minute(MM)");
		mlabelMinuteFrom.setBorder(new EmptyBorder(2,2,2,2));
		mlabelMinuteFrom.setBackground(Color.LIGHT_GRAY);
		mlabelMinuteFrom.setOpaque(true);
		mlabelMinuteFrom.setFont(PosFormUtil.getLabelFont());
		mlabelMinuteFrom.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelMinuteFrom.setBounds(left, top,175, LABEL_HEIGHT_SMALL);
		mContentPanel.add(mlabelMinuteFrom);
	}
		
	/**
	 * 
	 */
	private void createTimeToSelectionControl() {
		
		setTimeHeaderTo();
		left = PANEL_CONTENT_H_GAP/4+1;
//		top = mReportType.getY()+mReportType.getHeight()+V_GAP_BTWN_CMPNTS;//+ mlabelDay.getY() + mlabelDay.getHeight();
		top = mlabelTimeFrom.getY()+mlabelTimeFrom.getHeight()+V_GAP_BTWN_CMPNTS;//+ mlabelDay.getY() + mlabelDay.getHeight();

		mlabelTimeTo = new JLabel();
		mlabelTimeTo.setText(PosFormUtil.getMnemonicString("Time to :",'t'));
		mlabelTimeTo.setBorder(new EmptyBorder(2,2,2,2));
		mlabelTimeTo.setBackground(Color.LIGHT_GRAY);
		mlabelTimeTo.setOpaque(true);
		mlabelTimeTo.setFont(PosFormUtil.getLabelFont());
		mlabelTimeTo.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelTimeTo.setBounds(left, top, LABEL_WIDTH_BIG, LABEL_HEIGHT+30);
		mContentPanel.add(mlabelTimeTo);

		left = mlabelTimeTo.getX() + mlabelTimeTo.getWidth() +H_GAP_BTWN_CMPNTS;
		top = mlabelHourTo.getY() + mlabelHourTo.getHeight();

		mTxtHourTo = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_DAY);
		mTxtHourTo.setTitle("Hour");
		mTxtHourTo.setMnemonic('t');
		mTxtHourTo.hideResetButton(true);
		mTxtHourTo.setLocation(left, top);
		mTxtHourTo.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtHourTo.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtMinTo.requestFocus();
			}
			
			@Override
			public void onReset() {
			}
		});
		mContentPanel.add(mTxtHourTo);

		left = mlabelMinuteTo.getX();

		mTxtMinTo = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_MONTH+50);
		mTxtMinTo.setTitle("Minute");
		mTxtMinTo.setLocation(left, top);
		//		mTxtMin.hideResetButton(true);
		mTxtMinTo.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtMinTo.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				mTxtShift.requestFocus();

			}

			@Override
			public void onReset() {
				setTimeTo();
			}
		});
		mContentPanel.add(mTxtMinTo);

	}
	/**
	 * 
	 */
	private void setTimeHeaderTo() {

		left =mlabelTimeFrom.getX()+mlabelTimeFrom.getWidth()+H_GAP_BTWN_CMPNTS;
		top =  mlabelTimeFrom.getY()+mlabelTimeFrom.getHeight()+V_GAP_BTWN_CMPNTS;

		mlabelHourTo = new JLabel();
		mlabelHourTo.setText("Hour(HH)");
		mlabelHourTo.setBorder(new EmptyBorder(2,2,2,2));
		mlabelHourTo.setBackground(Color.LIGHT_GRAY);
		mlabelHourTo.setOpaque(true);
		mlabelHourTo.setFont(PosFormUtil.getLabelFont());
		mlabelHourTo.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelHourTo.setBounds(left, top, TEXT_FIELD_WIDTH_DAY, LABEL_HEIGHT_SMALL);
		mContentPanel.add(mlabelHourTo);

		left = mlabelHourTo.getX() + mlabelHourTo.getWidth() + H_GAP_BTWN_CMPNTS;

		mlabelMinuteTo = new JLabel();
		mlabelMinuteTo.setText("Minute(MM)");
		mlabelMinuteTo.setBorder(new EmptyBorder(2,2,2,2));
		mlabelMinuteTo.setBackground(Color.LIGHT_GRAY);
		mlabelMinuteTo.setOpaque(true);
		mlabelMinuteTo.setFont(PosFormUtil.getLabelFont());
		mlabelMinuteTo.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelMinuteTo.setBounds(left, top,175, LABEL_HEIGHT_SMALL);
		mContentPanel.add(mlabelMinuteTo);
	}
	
	private void createShiftSelectionControl() {

		left = PANEL_CONTENT_H_GAP/4+1;
		top = mlabelTimeTo.getY() + mlabelTimeTo.getHeight() +V_GAP_BTWN_CMPNTS;

		mlabelShift = new JLabel();
		mlabelShift.setText(PosFormUtil.getMnemonicString("Cashier Shift :",'S'));
		mlabelShift.setBorder(new EmptyBorder(2,2,2,2));
		mlabelShift.setBackground(Color.LIGHT_GRAY);
		mlabelShift.setOpaque(true);
		mlabelShift.setFont(PosFormUtil.getLabelFont());
		mlabelShift.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelShift.setBounds(left, top,LABEL_WIDTH_BIG, LABEL_HEIGHT);
		mContentPanel.add(mlabelShift);

		left = mlabelShift.getX() + mlabelShift.getWidth()
				+H_GAP_BTWN_CMPNTS;

		PosShopShiftProvider shiftProvider=new PosShopShiftProvider();
		Map<Integer,BeanShopShift> shopShifts=shiftProvider.getShopShifts(false);
		shiftProvider=null;

		mTxtShift = new PosItemBrowsableField(this, mlabelHourTo.getWidth()+mlabelMinuteTo.getWidth()+2);
		mTxtShift.setMnemonic('S');
		mTxtShift.setBrowseItemList(new ArrayList<BeanShopShift>(shopShifts.values()));
		mTxtShift.setBrowseWindowSize(3, 3);
		mTxtShift.setSelectedItem(shopShifts.entrySet().iterator().next().getValue());
		mTxtShift.setLocation(left, top);
		mTxtShift.setTitle("Select Shift.");
		mContentPanel.add(mTxtShift);
	}

	/**
	 * 
	 */
	private void createOrderServiceType() {


		left = PANEL_CONTENT_H_GAP/4+1;

		top = mlabelShift.getY() + mlabelShift.getHeight()
				+V_GAP_BTWN_CMPNTS;

		mlabelServiceType = new JLabel("Service :");
		mlabelServiceType.setOpaque(true);
		mlabelServiceType.setBackground(Color.LIGHT_GRAY);
		mlabelServiceType.setBorder(labelPadding);
		mlabelServiceType.setBounds(left, top, LABEL_WIDTH_BIG, LABEL_HEIGHT*2);
		mlabelServiceType.setFont(PosFormUtil.getLabelFont());
		mContentPanel.add(mlabelServiceType);
		
		left=mlabelServiceType.getX()+mlabelServiceType.getWidth() ;

		JPanel servicePanel=new JPanel();
		servicePanel.setBounds( left,top,PANEL_WIDTH-left-H_GAP_BTWN_CMPNTS*2,LABEL_HEIGHT*2);
//		servicePanel.setLayout(new FlowLayout( FlowLayout.LEFT));
		
		//servicePanel.setLayout(new  BoxLayout(servicePanel, BoxLayout.PAGE_AXIS));

		servicePanel.setLayout(new  GridLayout(2,2));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx =  GridBagConstraints.VERTICAL;
		gbc.gridy =0;
		gbc.weightx = 1.0;
		gbc.anchor = GridBagConstraints.NONE;
		gbc.fill = GridBagConstraints.VERTICAL;
		mContentPanel.add( servicePanel);
 
		mCheckTableService = new PosCheckBox();
		mCheckTableService.setFocusable(false);
		mCheckTableService.setTag(PosOrderServiceTypes.TABLE_SERVICE.getDisplayText());
		mCheckTableService.setHorizontalAlignment(JTextField.LEFT);
		//mCheckTableService.setBounds(left, top, CHECKBOX_SMALL_WIDTH-80, CHECKBOX_HEIGHT);
		mCheckTableService.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mCheckTableService.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mCheckTableService.setFont(PosFormUtil.getLabelFont());
		mCheckTableService.setText(PosOrderServiceTypes.TABLE_SERVICE.getDisplayText());
		mCheckTableService.setMnemonic('b');
		mCheckTableService.setSelected(true);
		if(PosOrderServiceTypes.TABLE_SERVICE.isVisibleInUI())
			   servicePanel.add(mCheckTableService ,gbc);  //servicePanel.add(mCheckTableService);
 

		mCheckHomeDelivery = new PosCheckBox();
		mCheckHomeDelivery.setFocusable(false);
		mCheckHomeDelivery.setOpaque(true);
		mCheckHomeDelivery.setTag(PosOrderServiceTypes.HOME_DELIVERY.getDisplayText());
		mCheckHomeDelivery.setHorizontalAlignment(JTextField.LEFT);
		//mCheckHomeDelivery.setBounds(left, top, CHECKBOX_SMALL_WIDTH, CHECKBOX_HEIGHT);
		mCheckHomeDelivery.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mCheckHomeDelivery.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mCheckHomeDelivery.setFont(PosFormUtil.getLabelFont());
		mCheckHomeDelivery.setText( PosOrderServiceTypes.HOME_DELIVERY.getDisplayText() );
		mCheckHomeDelivery.setMnemonic('R');
		mCheckHomeDelivery.setSelected(true);
		if (PosOrderServiceTypes.HOME_DELIVERY.isVisibleInUI()) 
			servicePanel.add(mCheckHomeDelivery,gbc );   //servicePanel.add(mCheckHomeDelivery);
	 
		mCheckTakeAway = new PosCheckBox();
		mCheckTakeAway.setFocusable(false);
		mCheckTakeAway.setOpaque(false);
		mCheckTakeAway.setTag(PosOrderServiceTypes.TAKE_AWAY.getDisplayText());
		mCheckTakeAway.setHorizontalAlignment(JTextField.LEFT);
		//mCheckTakeAway.setBounds(left, top, CHECKBOX_SMALL_WIDTH-20, CHECKBOX_HEIGHT);
		mCheckTakeAway.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mCheckTakeAway.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mCheckTakeAway.setFont(PosFormUtil.getLabelFont());
		mCheckTakeAway.setText(PosOrderServiceTypes.TAKE_AWAY.getDisplayText());
		mCheckTakeAway.setMnemonic('w');
		mCheckTakeAway.setSelected(true);
		if (PosOrderServiceTypes.TAKE_AWAY.isVisibleInUI())
			servicePanel.add(mCheckTakeAway ,gbc);   //servicePanel.add(mCheckTakeAway);
		
		mCheckWholeSaleService = new PosCheckBox();
		mCheckWholeSaleService.setFocusable(false);
		mCheckWholeSaleService.setTag(PosOrderServiceTypes.WHOLE_SALE.getDisplayText());
		mCheckWholeSaleService.setHorizontalAlignment(JTextField.LEFT);
		//mCheckWholeSaleService.setBounds(left, top, CHECKBOX_SMALL_WIDTH , CHECKBOX_HEIGHT);
		mCheckWholeSaleService.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mCheckWholeSaleService.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mCheckWholeSaleService.setFont(PosFormUtil.getLabelFont());
		mCheckWholeSaleService.setText(PosOrderServiceTypes.WHOLE_SALE.getDisplayText());
		mCheckWholeSaleService.setMnemonic('o');
		mCheckWholeSaleService.setSelected(true);
		if (PosOrderServiceTypes.WHOLE_SALE.isVisibleInUI())
			servicePanel.add(mCheckWholeSaleService ,gbc);   //servicePanel.add(mCheckWholeSaleService); 
 		
		mCheckSalesOrder = new PosCheckBox();
		mCheckSalesOrder.setFocusable(false);
		mCheckSalesOrder.setTag(PosOrderServiceTypes.SALES_ORDER.getDisplayText());
		mCheckSalesOrder.setHorizontalAlignment(JTextField.LEFT);
		//mCheckWholeSaleService.setBounds(left, top, CHECKBOX_SMALL_WIDTH , CHECKBOX_HEIGHT);
		mCheckSalesOrder.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mCheckSalesOrder.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mCheckSalesOrder.setFont(PosFormUtil.getLabelFont());
		mCheckSalesOrder.setText(PosOrderServiceTypes.SALES_ORDER.getDisplayText());
		mCheckSalesOrder.setMnemonic('s');
		mCheckSalesOrder.setSelected(true);
		if (PosOrderServiceTypes.SALES_ORDER.isVisibleInUI())
			servicePanel.add(mCheckSalesOrder ,gbc);   //servicePanel.add(mCheckWholeSaleService); 

	}


	/**
	 * 
	 */
	private void createPrintOptionControl() {
		left = PANEL_CONTENT_H_GAP/4+1;
		top =mlabelServiceType.getY() + mlabelServiceType.getHeight()
				+ V_GAP_BTWN_CMPNTS;

		mlabelPrintOptions = new JLabel("Print Options :");
		mlabelPrintOptions.setOpaque(true);
		mlabelPrintOptions.setBackground(Color.LIGHT_GRAY);
		mlabelPrintOptions.setBorder(labelPadding);
		mlabelPrintOptions.setFont(PosFormUtil.getLabelFont());
		mlabelPrintOptions.setBounds(left, top, LABEL_WIDTH_BIG, CHECKBOX_HEIGHT);
		mContentPanel.add(mlabelPrintOptions);

		left = mlabelShift.getX()+mlabelShift.getWidth()+H_GAP_BTWN_CMPNTS;
//		top=mlabelPrintOptions.getY()+mlabelPrintOptions.getHeight()+V_GAP_BTWN_CMPNTS;
		mSummaryOnly = new PosCheckBox();
		mSummaryOnly.setFocusable(false);
		mSummaryOnly.setTag("Summary Only");
		mSummaryOnly.setHorizontalAlignment(JTextField.LEFT);
		mSummaryOnly.setBounds(left, top, 210, CHECKBOX_HEIGHT);
		mSummaryOnly.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mSummaryOnly.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mSummaryOnly.setFont(PosFormUtil.getTextFieldBoldFont());
		mSummaryOnly.setText("Summary Only");
		mSummaryOnly.setMnemonic('u');
		mSummaryOnly.setOpaque(false);
		mSummaryOnly.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mPaymentDetails.setSelected(false);
				mPaymentDetails.setEnabled(!mSummaryOnly.isSelected());

			}
		});
		mContentPanel.add(mSummaryOnly);
		
		left = mSummaryOnly.getX()+mSummaryOnly.getWidth();
//		top = mlabelPrintOptions.getY() + mlabelPrintOptions.getHeight()
//				+ V_GAP_BTWN_CMPNTS;

		mPaymentDetails = new PosCheckBox();
		mPaymentDetails.setFocusable(false);
		mPaymentDetails.setTag("Print payment details");
		mPaymentDetails.setHorizontalAlignment(JTextField.LEFT);
		mPaymentDetails.setBounds(left, top, 230, CHECKBOX_HEIGHT);
		mPaymentDetails.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mPaymentDetails.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mPaymentDetails.setFont(PosFormUtil.getTextFieldBoldFont());
		mPaymentDetails.setText( "Payment details" );
		mPaymentDetails.setMnemonic('y');
		//		mPaymentDetails.setBackground(Color.BLUE);
		mPaymentDetails.setOpaque(false);
		mPaymentDetails.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mSummaryOnly.setSelected(false);
				mSummaryOnly.setEnabled(!mPaymentDetails.isSelected());

			}
		});
		mContentPanel.add(mPaymentDetails);
	}

	/**
	 * 
	 */
	protected void setEnableControls() {
		
		mCheckTableService.setEnabled(mMidReportSelection.isSelected());
		mCheckHomeDelivery.setEnabled(mMidReportSelection.isSelected());
		mCheckTakeAway.setEnabled(mMidReportSelection.isSelected());
		mCheckWholeSaleService.setEnabled(mMidReportSelection.isSelected());
		mCheckSalesOrder.setEnabled(mMidReportSelection.isSelected());
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {

		mContentPanel = panel;
		mContentPanel.setLayout(null);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {

	
		boolean valid=true;
		String posDate;
		String orderTimeFrom;
		String orderTimeTo;
		StringBuilder string = new StringBuilder();
		String where;
		ArrayList<IPosBrowsableItem> selectedServices=new ArrayList<IPosBrowsableItem>();

		
		posDate = (mTxtDay.getText().trim().length()!=0 || mTxtMonth.getText().trim().length()!=0
				|| mTxtYear.getText().trim().length()!=0)?PosDateUtil
						.buildStringDate(mTxtYear.getText()
								.trim(), mTxtMonth.getText().trim(), mTxtDay.getText()
								.trim(), PosDateUtil.DATE_SEPERATOR):null;
		setDateTo(posDate);
		orderTimeFrom = (mTxtMinFrom.getText().trim().length()!=0 || mTxtHourFrom.getText().trim().length()!=0
								)?PosDateUtil
								.buildStringTime(mTxtHourFrom.getText()
								.trim(), mTxtMinFrom.getText().trim(), "00",
								PosDateUtil.TIME_SEPERATOR):null;
		orderTimeTo = (mTxtMinTo.getText().trim().length()!=0 || mTxtHourTo.getText().trim().length()!=0)?PosDateUtil
								.buildStringTime(mTxtHourTo.getText().trim(), mTxtMinTo
										.getText().trim(), "00", PosDateUtil.TIME_SEPERATOR):null;

		 if(mEndReportSelection.isSelected()){
			 	PosDayProcessProvider dayProcessProvider=new PosDayProcessProvider();
			 	try{
			 		if (!dayProcessProvider.isDayProcessed(posDate)){
						PosFormUtil.showInformationMessageBox(this , "Day End is not done for this given POS Date.");
						valid=false;
					}
			 	}catch(Exception ex){
			 		PosLog.write(this, "day end report", ex);
			 		PosFormUtil.showErrorMessageBox(this, ex.getMessage());
			 	}
					
			 
			}

		if ((mTxtDay.getText().trim().length() == 0)
				|| (mTxtDay.getText().trim().length() > 2)) {
			PosFormUtil.showErrorMessageBox(this,"Invalid day.");
			mTxtDay.requestFocus();
			valid = false;
		} else if ((mTxtMonth.getText().trim().length() == 0)
				|| (mTxtMonth.getText().trim().length() > 2)) {
			PosFormUtil.showErrorMessageBox(this,"Invalid month.");
			mTxtMonth.requestFocus();
			valid = false;
		} else if ((mTxtYear.getText().trim().length()) != 4) {
			PosFormUtil.showErrorMessageBox(this,"Invalid year.");
			mTxtYear.requestFocus();
			valid = false;
		} else if (posDate!=null && (!PosDateUtil.validateDate(posDate))) {
			PosFormUtil.showErrorMessageBox(this,"Date is not in proper format.");
			mTxtDay.requestFocus();
			valid = false;
		}else if(!validateSearchCriteria(orderTimeFrom,orderTimeTo)){
			valid = false;
		}else if (mTxtShift.getText().trim().length() == 0) {
			PosFormUtil.showErrorMessageBox(this,"Please select shift.");
			mTxtShift.requestFocus();
			valid = false;
		}else if(!checkAndSetShift(posDate,((BeanShopShift)mTxtShift.getSelectedValue()).getId())){
			PosFormUtil.showErrorMessageBox(this,"The shift is not used in the Date .");
			mTxtDay.requestFocus();
			valid = false;
		}else{

			 
			string.append(" closing_date ='" + posDate + "' and ");
			
//			if (mTxtShift.getText().trim().length() != 0) {
//				cashierShift = ((BeanShopShift)mTxtShift.getSelectedValue()).getId();
//				if (cashierShift>0)
//					string.append(" shift_id ='" + cashierShift + "' and ");
//			}
			
		
			
			if (mMidReportSelection.isSelected()&&(mCheckTableService.isSelected()||mCheckHomeDelivery.isSelected()||mCheckTakeAway.isSelected())) {
				String prefix ="";
				string.append("service_type in (");
				if(mCheckTableService.isSelected()){
					string.append(prefix);
					string.append(PosOrderServiceTypes.TABLE_SERVICE.getCode());
					prefix=",";
					selectedServices.add(PosOrderServiceTypes.TABLE_SERVICE);
				}
				if(mCheckHomeDelivery.isSelected()){
					string.append(prefix);
					string.append(PosOrderServiceTypes.HOME_DELIVERY.getCode());
					prefix=",";
					selectedServices.add(PosOrderServiceTypes.HOME_DELIVERY);
				}
				if(mCheckTakeAway.isSelected()){
					string.append(prefix);
					string.append(PosOrderServiceTypes.TAKE_AWAY.getCode());
					prefix=",";
					selectedServices.add(PosOrderServiceTypes.TAKE_AWAY);
				}
				if(mCheckWholeSaleService.isSelected()){
					string.append(prefix);
					string.append(PosOrderServiceTypes.WHOLE_SALE.getCode());
					prefix=",";
					selectedServices.add(PosOrderServiceTypes.WHOLE_SALE);
				}
				if(mCheckSalesOrder.isSelected()){
					string.append(prefix);
					string.append(PosOrderServiceTypes.SALES_ORDER.getCode());
					prefix=",";
					selectedServices.add(PosOrderServiceTypes.SALES_ORDER);
				}
				string.append(") and ");
			}

		}

		if(valid){
			where = ((string.length() != 0) ? string.substring(0,
					string.length() - 4) : string).toString();
			
			String orderFrom =(orderTimeFrom!=null)?(mDateFrom+" "+orderTimeFrom):shiftDetails.getOpeningTime();
			String orderTo =(orderTimeTo!=null)?(mDateTo+" "+orderTimeTo):(shiftDetails.getClosingTime()!=null)?shiftDetails.getClosingTime():PosDateUtil.getDateTime();
			doPrintReport(where,posDate,orderFrom,orderTo,selectedServices);
		}
		return false;

	}
	/**
	 * @param orderDate
	 * @param id
	 * @return
	 */
	private boolean checkAndSetShift(String orderDate, int shiftId) {
		if (shiftId==0) 
			return true;
		
		cashierShiftProvider = new PosCashierShiftProvider();
		shiftDetails = cashierShiftProvider.getShiftDetails(orderDate,shiftId);
		if(shiftDetails!=null){
			return true;
		}else{
			return false;
		}
	}
	/**	ArrayList<IPosBrowsableItem> selectedPayMode=new ArrayList<IPosBrowsableItem>();
	 * @param orderTimeFrom
	 * @param orderTimeTo
	 * @return
	 */
	private boolean validateSearchCriteria(String orderTimeFrom,
			String orderTimeTo) {
		boolean valid = true;
		 if ((mTxtHourFrom.getText().trim().length() != 0)
					&& (mTxtHourFrom.getText().trim().length() != 2)) {
			 PosFormUtil.showErrorMessageBox(this, "Invalid Hour.");
				valid = false;
				mTxtHourFrom.requestFocus();
			} else if ((mTxtMinFrom.getText().trim().length() != 0)
					&& (mTxtMinFrom.getText().trim().length() != 2)) {
				PosFormUtil.showErrorMessageBox(this, "Invalid Minute.");
				valid = false;
				mTxtMinFrom.requestFocus();
			} 
			else if (orderTimeFrom !=null && (!PosDateUtil.validateTime(orderTimeFrom))) {
				PosFormUtil.showErrorMessageBox(this, "Time is not in proper format.");
				valid = false;
				mTxtHourFrom.requestFocus();
			}
			else if ((mTxtHourTo.getText().trim().length() != 0)
					&& (mTxtHourTo.getText().trim().length() != 2)) {
				 PosFormUtil.showErrorMessageBox(this, "Invalid Hour.");
					valid = false;
					mTxtHourTo.requestFocus();
			} else if ((mTxtMinTo.getText().trim().length() != 0)
					&& (mTxtMinTo.getText().trim().length() != 2)) {
				PosFormUtil.showErrorMessageBox(this, "Invalid Minute.");
				valid = false;
				mTxtMinTo.requestFocus();
			} 
			else if (orderTimeTo != null && (!PosDateUtil.validateTime(orderTimeTo))) {
				PosFormUtil.showErrorMessageBox(this, "Time is not in proper format.");
				valid = false;
				mTxtHourTo.requestFocus();
			}
		 
		
		 
		return valid;
	}
	/**
	 * @param where
	 * @param timeCriteria 
	 * @param orderTo 
	 * @param selected
	 * @param text
	 * @param salesReportOnly 
	 */
	private void doPrintReport(final String where,final String orderDate ,
			final String orderFrom, final String orderTo,
			ArrayList<IPosBrowsableItem> selectedServices) {

		final PosShiftReport report;
		
	switch(PosEnvSettings.getInstance().getPrintSettings().getPrintingFormat()){
		
		case "NIHON":
			report=new PosShiftReportNihon();
			break;
	 	default:
	 		report =new PosShiftReport();
			break;
			
		}
		report.setCriteria(where);
		report.setTimeCriteria(orderFrom,orderTo);
		if (shiftDetails != null )	
			report.setPosDate(shiftDetails.getOpeningDate());
		else
			report.setPosDate(orderDate);
		
		report.setSummaryOnly(mSummaryOnly.isSelected());
		report.setShift((BeanShopShift) mTxtShift.getSelectedValue());
		report.setPrintPaymentDtls(mPaymentDetails.isSelected());
		report.setSalesReportOnly(mMidReportSelection.isSelected());
		report.setSelectedServices(selectedServices);
		try{
			PosShiftReport.doPrintDayEndReport(this, report);
		} catch (Exception e) {
			
			PosFormUtil.showErrorMessageBox(this, e.getMessage());
		}

	}

	
	/**
	 * 
	 */
	private void setControlsOnReportTypeChange(){
		
		mTxtHourFrom.setEnabled(!mEndReportSelection.isSelected());
		mTxtMinFrom.setEnabled(!mEndReportSelection.isSelected());
		mTxtHourTo.setEnabled(!mEndReportSelection.isSelected());
		mTxtMinTo.setEnabled(!mEndReportSelection.isSelected());
		mTxtShift.setEnabled(!mEndReportSelection.isSelected());
		
		if (mEndReportSelection.isSelected()){
			
			mHourFrom=mTxtHourFrom.getText();
			mMinFrom=mTxtMinFrom.getText();
			mHourTo=mTxtHourTo.getText();
			mMinTo=mTxtMinTo.getText();
			
			mTxtHourFrom.setText("01");
			mTxtMinFrom.setText("00");
			mTxtHourTo.setText("23");
			mTxtMinTo.setText("59");
			
			mTxtShift.setSelectedItem(mShiftAll );
			
		}else{
			
			mTxtHourFrom.setText(mHourFrom);
			mTxtMinFrom.setText(mMinFrom);
			
			mTxtHourTo.setText(mHourTo);
			mTxtMinTo.setText(mMinTo);
 
			
		}
			
		
	}
}
